package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.dtos.request.ScanningCaseRequestDTO;
import ru.moore.archive.models.dtos.request.ScanningInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.*;
import ru.moore.archive.repositories.AcquisitionInventoryRepository;
import ru.moore.archive.repositories.CommentsCaseRepository;
import ru.moore.archive.repositories.ScanningCaseRepository;
import ru.moore.archive.repositories.ScanningInventoryRepository;
import ru.moore.archive.security.dto.SecurityDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScanningService {

    private final HistoryService historyService;
    private final CommentService commentService;

    private final AcquisitionInventoryRepository acquisitionInventoryRepository;
    private final ScanningInventoryRepository scanningInventoryRepository;
    private final ScanningCaseRepository scanningCaseRepository;
    private final MapperUtils mapperUtils;

    private Account getAuthor(Authentication authentication) {
        if (authentication == null) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Нет секретного ключа");
        }
        return mapperUtils.map(((SecurityDTO) authentication.getPrincipal()).getAccount(), Account.class);
    }

    public Page<InventoryResponseDTO> findAll(Specification<Inventory> spec, int page, int pageSize, Authentication authentication) {
        return scanningInventoryRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDto(inventory, authentication));
    }

    @Transactional
    public InventoryResponseDTO saveScanningInventory (Authentication authentication, ScanningInventoryRequestDTO scanningRequestDTO) {
        Optional<Inventory> scanning = scanningInventoryRepository.findById(scanningRequestDTO.getId());
        if (!scanning.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_SCANNING.getStatus()) && !scanning.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IS_SCANNING.getStatus()) && !scanning.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_LOADING.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        scanning.get().setStatusRecord(mapperUtils.map(scanningRequestDTO.getStatusRecord(), StatusRecord.class));
        scanning.get().setFolderScan(scanningRequestDTO.getFolderScan());
        scanning.get().setCountSheetScan(scanningRequestDTO.getCountSheetScan());
        scanning.get().setCountJpg(scanningRequestDTO.getCountJpg());
        scanning.get().setCountPdf(scanningRequestDTO.getCountPdf());

        if (scanningRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.IS_SCANNING.getStatus())) {
            historyService.saveHistoryInventory(mapperUtils.map(scanningRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), scanningRequestDTO.getId());
        } else {
            historyService.saveHistoryInventory(mapperUtils.map(scanningRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), scanningRequestDTO.getId());
        }
        scanning.get().setComment(commentService.saveCommentsInventory(scanningRequestDTO.getComment(), getAuthor(authentication), "Отдел сканирования", scanning.get()));

        return mapperUtils.map(scanningInventoryRepository.save(mapperUtils.map(scanning.get(), Inventory.class)), InventoryResponseDTO.class);
    }

    private InventoryResponseDTO toDto(Inventory inventory, Authentication authentication) {
        inventory.setComment(commentService.loadCommentsInventory(inventory.getId(), getAuthor(authentication), "Отдел сканирования"));
        return mapperUtils.map(inventory, InventoryResponseDTO.class);
    }

    public Page<CaseResponseDTO> findCaseByInventory(Specification<Case> spec, int page, int pageSize, long id, Authentication authentication) {
        Specification<Case> whereInventoryId = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("inventoryId"), id);
        spec = spec.and(whereInventoryId);
        return scanningCaseRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDtoCase(inventory, authentication));
    }

    private CaseResponseDTO toDtoCase(Case aCase, Authentication authentication) {
        aCase.setComment(commentService.loadCommentsCase(aCase.getId(), getAuthor(authentication), "Отдел сканирования"));
        return mapperUtils.map(aCase, CaseResponseDTO.class);
    }

    @Transactional
    public CaseResponseDTO saveScanningCase(Authentication authentication, ScanningCaseRequestDTO scanningCaseRequestDTO) {
        Optional<Case> scanningCase = scanningCaseRepository.findById(scanningCaseRequestDTO.getId());
        if (!scanningCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_SCANNING.getStatus()) && !scanningCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IS_SCANNING.getStatus()) && !scanningCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_LOADING.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        if (scanningCaseRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus())) {
            historyService.saveHistoryCase(mapperUtils.map(scanningCaseRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), scanningCaseRequestDTO.getId());
        } else {
            historyService.saveHistoryCase(mapperUtils.map(scanningCaseRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), scanningCaseRequestDTO.getId());
        }

        Optional<Inventory> acquisitionInventory = acquisitionInventoryRepository.findById(scanningCaseRequestDTO.getInventoryId());
        scanningCase.get().setAcquisitionInventory(acquisitionInventory.get());
        scanningCase.get().setComment(commentService.saveCommentsCase(scanningCaseRequestDTO.getComment(), getAuthor(authentication), "Отдел сканирования", scanningCase.get()));
        scanningCase.get().setStatusRecord(mapperUtils.map(scanningCaseRequestDTO.getStatusRecord(), StatusRecord.class));

        scanningCase.get().setCountDoc(scanningCaseRequestDTO.getCountDoc());
        scanningCase.get().setCountJpg(scanningCaseRequestDTO.getCountJpg());
        scanningCase.get().setCountPdf(scanningCaseRequestDTO.getCountPdf());
        scanningCase.get().setSheetUsage(scanningCaseRequestDTO.getSheetUsage());
        scanningCase.get().setSheetWitness(scanningCaseRequestDTO.getSheetWitness());
        scanningCase.get().setCountSheetScan(scanningCaseRequestDTO.getCountSheetScan());
        scanningCase.get().setFolderScan(scanningCaseRequestDTO.getFolderScan());

        return mapperUtils.map(scanningCaseRepository.save(scanningCase.get()), CaseResponseDTO.class);
    }
}
