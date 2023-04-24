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
import ru.moore.archive.models.dtos.request.LoadingCaseRequestDTO;
import ru.moore.archive.models.dtos.request.LoadingInventoryRequestDTO;
import ru.moore.archive.models.dtos.request.LoadingCaseRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.*;
import ru.moore.archive.repositories.AcquisitionInventoryRepository;
import ru.moore.archive.repositories.CommentsCaseRepository;
import ru.moore.archive.repositories.LoadingCaseRepository;
import ru.moore.archive.repositories.LoadingInventoryRepository;
import ru.moore.archive.security.dto.SecurityDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadingService {

    private final HistoryService historyService;
    private final CommentService commentService;

    private final AcquisitionInventoryRepository acquisitionInventoryRepository;
    private final LoadingInventoryRepository loadingInventoryRepository;
    private final LoadingCaseRepository loadingCaseRepository;
    private final MapperUtils mapperUtils;

    private Account getAuthor(Authentication authentication) {
        if (authentication == null) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Нет секретного ключа");
        }
        return mapperUtils.map(((SecurityDTO) authentication.getPrincipal()).getAccount(), Account.class);
    }

    public Page<InventoryResponseDTO> findAll(Specification<Inventory> spec, int page, int pageSize, Authentication authentication) {
        return loadingInventoryRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDto(inventory, authentication));
    }

    @Transactional
    public InventoryResponseDTO saveLoadingInventory (Authentication authentication, LoadingInventoryRequestDTO loadingRequestDTO) {
        Optional<Inventory> loading = loadingInventoryRepository.findById(loadingRequestDTO.getId());
        if (!loading.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_LOADING.getStatus()) && !loading.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_DIRECTOR.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        loading.get().setStatusRecord(mapperUtils.map(loadingRequestDTO.getStatusRecord(), StatusRecord.class));
        loading.get().setCreatedCardsCase(loadingRequestDTO.getCreatedCardsCase());

        historyService.saveHistoryInventory(mapperUtils.map(loadingRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), loadingRequestDTO.getId());
        loading.get().setComment(commentService.saveCommentsInventory(loadingRequestDTO.getComment(), getAuthor(authentication), "Отдел загрузки", loading.get()));

        return mapperUtils.map(loadingInventoryRepository.save(loading.get()), InventoryResponseDTO.class);
    }

    private InventoryResponseDTO toDto(Inventory inventory, Authentication authentication) {
        inventory.setComment(commentService.loadCommentsInventory(inventory.getId(), getAuthor(authentication), "Отдел загрузки"));
        return mapperUtils.map(inventory, InventoryResponseDTO.class);
    }

    public Page<CaseResponseDTO> findCaseByInventory(Specification<Case> spec, int page, int pageSize, long id, Authentication authentication) {
        Specification<Case> whereInventoryId = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("inventoryId"), id);
        spec = spec.and(whereInventoryId);
        return loadingCaseRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"))).map(inventory -> toDtoCase(inventory, authentication));
    }

    private CaseResponseDTO toDtoCase(Case aCase, Authentication authentication) {
        aCase.setComment(commentService.loadCommentsCase(aCase.getId(), getAuthor(authentication), "Отдел загрузки"));
        return mapperUtils.map(aCase, CaseResponseDTO.class);
    }

    @Transactional
    public CaseResponseDTO saveLoadingCase(Authentication authentication, LoadingCaseRequestDTO loadingCaseRequestDTO) {
        Optional<Case> loadingCase = loadingCaseRepository.findById(loadingCaseRequestDTO.getId());
        if (!loadingCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IN_LOADING.getStatus()) && !loadingCase.get().getStatusRecord().getStatus().equals(StatusRecordEnum.REFINE_DIRECTOR.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }

        if (loadingCaseRequestDTO.getStatusRecord().getStatus().equals(StatusRecordEnum.DRAFT.getStatus())) {
            historyService.saveHistoryCase(mapperUtils.map(loadingCaseRequestDTO.getStatusRecord(), StatusRecord.class), "Обновили запись", getAuthor(authentication), loadingCaseRequestDTO.getId());
        } else {
            historyService.saveHistoryCase(mapperUtils.map(loadingCaseRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), loadingCaseRequestDTO.getId());
        }

        Optional<Inventory> acquisitionInventory = acquisitionInventoryRepository.findById(loadingCaseRequestDTO.getInventoryId());
        loadingCase.get().setAcquisitionInventory(acquisitionInventory.get());
        loadingCase.get().setComment(commentService.saveCommentsCase(loadingCaseRequestDTO.getComment(), getAuthor(authentication), "Отдел загрузки", loadingCase.get()));
        loadingCase.get().setStatusRecord(mapperUtils.map(loadingCaseRequestDTO.getStatusRecord(), StatusRecord.class));

        loadingCase.get().setCreatedCardsCase(loadingCaseRequestDTO.getCreatedCardsCase());
        loadingCase.get().setLoadSheet(loadingCaseRequestDTO.getLoadSheet());
        loadingCase.get().setLoadFile(loadingCaseRequestDTO.getLoadFile());

        return mapperUtils.map(loadingCaseRepository.save(loadingCase.get()), CaseResponseDTO.class);
    }
}
