package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.StorageCaseRequestDTO;
import ru.moore.archive.models.dtos.request.StorageInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.AcquisitionCaseSpecifications;
import ru.moore.archive.repositories.specifications.StorageCaseSpecifications;
import ru.moore.archive.repositories.specifications.StorageInventorySpecifications;
import ru.moore.archive.services.StorageService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/storage")
@RequiredArgsConstructor
@Validated
public class StorageController {

    private final StorageService storageService;

    @GetMapping(value = "/inventory")
    @PreAuthorize("hasRole('STORAGE')")
    public Page<InventoryResponseDTO> findAllStorage(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, Authentication authentication) {
        if (page < 1) {
            page = 1;
        }
        return storageService.findAllStorage(StorageInventorySpecifications.build(params), page, limit, authentication);
    }

    @PutMapping(value = "/inventory")
    @PreAuthorize("hasRole('STORAGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public InventoryResponseDTO saveStorageInventory(Authentication authentication, @Valid @RequestBody StorageInventoryRequestDTO storageRequestDTO) {
        return storageService.saveStorageInventory(authentication, storageRequestDTO);
    }

    @GetMapping(value = "/case/{id}")
    @PreAuthorize("hasRole('STORAGE')")
    public Page<CaseResponseDTO> findCaseByInventory(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, @PathVariable("id") @Min(1) long id, Authentication authentication) {
        return storageService.findCaseByInventory(StorageCaseSpecifications.build(params), page, limit, id, authentication);
    }

    @PutMapping(value = "/case")
    @PreAuthorize("hasRole('STORAGE')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public CaseResponseDTO saveStorageCase(Authentication authentication, @Valid @RequestBody StorageCaseRequestDTO storageCaseRequestDTO) {
        return storageService.saveStorageCase(authentication, storageCaseRequestDTO);
    }

}
