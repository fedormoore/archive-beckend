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
import ru.moore.archive.models.dtos.request.AcquisitionCaseRequestDTO;
import ru.moore.archive.models.dtos.request.AcquisitionInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.*;
import ru.moore.archive.repositories.AcquisitionCaseRepository;
import ru.moore.archive.repositories.AcquisitionInventoryRepository;
import ru.moore.archive.security.dto.SecurityDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcquisitionService {

    private final HistoryService historyService;
    private final CommentService commentService;

    private final AcquisitionInventoryRepository acquisitionInventoryRepository;
    private final AcquisitionCaseRepository acquisitionCaseRepository;
    private final MapperUtils mapperUtils;

    private Account getAuthor(Authentication authentication) {
        if (authentication == null) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Нет секретного ключа");
        }
        return mapperUtils.map(((SecurityDTO) authentication.getPrincipal()).getAccount(), Account.class);
    }

    public Page<InventoryResponseDTO> findAllAcquisition(Specification<Inventory> spec, int page, int pageSize, Authentication authentication) {
        return acquisitionInventoryRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDtoInventory(inventory, authentication));
    }

    @Transactional
    public InventoryResponseDTO saveAcquisitionInventory(Authentication authentication, AcquisitionInventoryRequestDTO inventoryRequestDTO) {
        Inventory inventorySave = mapperUtils.map(inventoryRequestDTO, Inventory.class);
        Inventory inventory = acquisitionInventoryRepository.save(inventorySave);

        commentService.saveCommentsInventory(inventoryRequestDTO.getComment(), getAuthor(authentication), "Отдел комплектования", inventory);
        historyService.saveHistoryInventory(mapperUtils.map(inventoryRequestDTO.getStatusRecord(), StatusRecord.class), "Создали запись", getAuthor(authentication), inventory.getId());

        return mapperUtils.map(inventory, InventoryResponseDTO.class);
    }

    @Transactional
    public InventoryResponseDTO updateAcquisitionInventory(Authentication authentication, AcquisitionInventoryRequestDTO inventoryRequestDTO) {
        Optional<Inventory> inventory = acquisitionInventoryRepository.findById(inventoryRequestDTO.getId());
        if (!inventory.get().getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus()) && !inventory.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_STORAGE.getStatus())) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Запись нельзя редактировать");
        }

        if (inventoryRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus())) {
            historyService.saveHistoryInventory(mapperUtils.map(inventoryRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), inventoryRequestDTO.getId());
        } else {
            historyService.saveHistoryInventory(mapperUtils.map(inventoryRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), inventoryRequestDTO.getId());
        }

        commentService.saveCommentsInventory(inventoryRequestDTO.getComment(), getAuthor(authentication), "Отдел комплектования", inventory.get());

        acquisitionInventoryRepository.save(mapperUtils.map(inventoryRequestDTO, Inventory.class));
        return mapperUtils.map(inventoryRequestDTO, InventoryResponseDTO.class);
    }

    @Transactional
    public InventoryResponseDTO deleteInventory(Authentication authentication, Long id) {
        Optional<Inventory> inventory = acquisitionInventoryRepository.findById(id);
        inventory.get().setDeleted(true);
        return mapperUtils.map(acquisitionInventoryRepository.save(inventory.get()), InventoryResponseDTO.class);
    }

    private InventoryResponseDTO toDtoInventory(Inventory inventory, Authentication authentication) {
        inventory.setComment(commentService.loadCommentsInventory(inventory.getId(), getAuthor(authentication), "Отдел комплектования"));
        return mapperUtils.map(inventory, InventoryResponseDTO.class);
    }

    public Page<CaseResponseDTO> findCaseByInventory(Specification<Case> spec, int page, int pageSize, long id, Authentication authentication) {
        Specification<Case> whereInventoryId = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("inventoryId"), id);
        spec = spec.and(whereInventoryId);
        return acquisitionCaseRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDtoCase(inventory, authentication));
    }

    private CaseResponseDTO toDtoCase(Case aCase, Authentication authentication) {
        aCase.setComment(commentService.loadCommentsCase(aCase.getId(), getAuthor(authentication), "Отдел комплектования"));
        return mapperUtils.map(aCase, CaseResponseDTO.class);
    }

    @Transactional
    public CaseResponseDTO saveAcquisitionCase(Authentication authentication, AcquisitionCaseRequestDTO acquisitionCaseRequestDTO) {
        Case acquisitionCase = mapperUtils.map(acquisitionCaseRequestDTO, Case.class);
        Optional<Inventory> acquisitionInventory = acquisitionInventoryRepository.findById(acquisitionCaseRequestDTO.getInventoryId());
        acquisitionCase.setAcquisitionInventory(acquisitionInventory.get());

        Case acquisitionCaseSave = acquisitionCaseRepository.save(acquisitionCase);

        commentService.saveCommentsCase(acquisitionCaseRequestDTO.getComment(), getAuthor(authentication), "Отдел комплектования", acquisitionCaseSave);
        historyService.saveHistoryCase(mapperUtils.map(acquisitionCaseRequestDTO.getStatusRecord(), StatusRecord.class), "Создали запись", getAuthor(authentication), acquisitionCaseSave.getId());

        return mapperUtils.map(acquisitionCaseSave, CaseResponseDTO.class);
    }

    @Transactional
    public CaseResponseDTO updateAcquisitionCase(Authentication authentication, AcquisitionCaseRequestDTO acquisitionCaseRequestDTO) {
        Optional<Case> acquisitionCase = acquisitionCaseRepository.findById(acquisitionCaseRequestDTO.getId());
        if (!acquisitionCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus()) && !acquisitionCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_STORAGE.getStatus())) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Запись нельзя редактировать");
        }

        if (acquisitionCaseRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus())) {
            historyService.saveHistoryCase(mapperUtils.map(acquisitionCaseRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), acquisitionCaseRequestDTO.getId());
        } else {
            historyService.saveHistoryCase(mapperUtils.map(acquisitionCaseRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), acquisitionCaseRequestDTO.getId());
        }

        commentService.saveCommentsCase(acquisitionCaseRequestDTO.getComment(), getAuthor(authentication), "Отдел комплектования", acquisitionCase.get());

        Optional<Inventory> acquisitionInventory = acquisitionInventoryRepository.findById(acquisitionCaseRequestDTO.getInventoryId());
        Case acquisitionCaseSave = mapperUtils.map(acquisitionCaseRequestDTO, Case.class);
        acquisitionCaseSave.setAcquisitionInventory(acquisitionInventory.get());
        acquisitionCaseRepository.save(acquisitionCaseSave);
        return mapperUtils.map(acquisitionCaseRequestDTO, CaseResponseDTO.class);

    }
}
