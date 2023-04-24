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
import ru.moore.archive.models.dtos.request.StorageCaseRequestDTO;
import ru.moore.archive.models.dtos.request.StorageInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.*;
import ru.moore.archive.repositories.CommentsCaseRepository;
import ru.moore.archive.repositories.StorageCaseRepository;
import ru.moore.archive.repositories.AcquisitionInventoryRepository;
import ru.moore.archive.security.dto.SecurityDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final HistoryService historyService;
    private final CommentService commentService;

    private final AcquisitionInventoryRepository acquisitionInventoryRepository;
    private final StorageCaseRepository storageCaseRepository;
    private final MapperUtils mapperUtils;

    private Account getAuthor(Authentication authentication) {
        if (authentication == null) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Нет секретного ключа");
        }
        return mapperUtils.map(((SecurityDTO) authentication.getPrincipal()).getAccount(), Account.class);
    }

    public Page<InventoryResponseDTO> findAllStorage(Specification<Inventory> spec, int page, int pageSize, Authentication authentication) {
        return acquisitionInventoryRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDto(inventory, authentication));
    }

    @Transactional
    public InventoryResponseDTO saveStorageInventory(Authentication authentication, StorageInventoryRequestDTO storageRequestDTO) {
        Optional<Inventory> storage = acquisitionInventoryRepository.findById(storageRequestDTO.getId());
        if (!storage.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_STORAGE.getStatus()) && !storage.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_SCANNING.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        storage.get().setCountSheets(storageRequestDTO.getCountSheets());
        storage.get().setStatusRecord(mapperUtils.map(storageRequestDTO.getStatusRecord(), StatusRecord.class));

        historyService.saveHistoryInventory(mapperUtils.map(storageRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), storageRequestDTO.getId());
        storage.get().setComment(commentService.saveCommentsInventory(storageRequestDTO.getComment(), getAuthor(authentication), "Отдел хранилище", storage.get()));

        return mapperUtils.map(acquisitionInventoryRepository.save(storage.get()), InventoryResponseDTO.class);
    }

    private InventoryResponseDTO toDto(Inventory inventory, Authentication authentication) {
        inventory.setComment(commentService.loadCommentsInventory(inventory.getId(), getAuthor(authentication), "Отдел хранилище"));
        return mapperUtils.map(inventory, InventoryResponseDTO.class);
    }

    public Page<CaseResponseDTO> findCaseByInventory(Specification<Case> spec, int page, int pageSize, long id, Authentication authentication) {
        Specification<Case> whereInventoryId = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("inventoryId"), id);
        spec = spec.and(whereInventoryId);
        return storageCaseRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDtoCase(inventory, authentication));
    }

    private CaseResponseDTO toDtoCase(Case aCase, Authentication authentication) {
        aCase.setComment(commentService.loadCommentsCase(aCase.getId(), getAuthor(authentication), "Отдел хранилище"));
        return mapperUtils.map(aCase, CaseResponseDTO.class);
    }

    @Transactional
    public CaseResponseDTO saveStorageCase(Authentication authentication, StorageCaseRequestDTO storageCaseRequestDTO) {
        Optional<Case> storageCase = storageCaseRepository.findById(storageCaseRequestDTO.getId());
        if (!storageCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_STORAGE.getStatus()) && !storageCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_SCANNING.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        if (storageCaseRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus())) {
            historyService.saveHistoryCase(mapperUtils.map(storageCaseRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), storageCaseRequestDTO.getId());
        } else {
            historyService.saveHistoryCase(mapperUtils.map(storageCaseRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), storageCaseRequestDTO.getId());
        }

        Optional<Inventory> acquisitionInventory = acquisitionInventoryRepository.findById(storageCaseRequestDTO.getInventoryId());
        storageCase.get().setAcquisitionInventory(acquisitionInventory.get());
        storageCase.get().setStatusRecord(mapperUtils.map(storageCaseRequestDTO.getStatusRecord(), StatusRecord.class));
        storageCase.get().setComment(commentService.saveCommentsCase(storageCaseRequestDTO.getComment(), getAuthor(authentication), "Отдел хранилище", storageCase.get()));

        storageCase.get().setCountSheets(storageCaseRequestDTO.getCountSheets());

        return mapperUtils.map(storageCaseRepository.save(storageCase.get()), CaseResponseDTO.class);
    }
}
