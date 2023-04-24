package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.ScanningCaseRequestDTO;
import ru.moore.archive.models.dtos.request.ScanningInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.ScanningCaseSpecifications;
import ru.moore.archive.repositories.specifications.ScanningInventorySpecifications;
import ru.moore.archive.repositories.specifications.StorageCaseSpecifications;
import ru.moore.archive.services.ScanningService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/scanning")
@RequiredArgsConstructor
@Validated
public class ScanningController {

    private final ScanningService scanningService;

    @GetMapping(value = "/inventory")
    @PreAuthorize("hasRole('SCANNING')")
    public Page<InventoryResponseDTO> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, Authentication authentication) {
        if (page < 1) {
            page = 1;
        }
        return scanningService.findAll(ScanningInventorySpecifications.build(params), page, limit, authentication);
    }

    @PutMapping(value = "/inventory")
    @PreAuthorize("hasRole('SCANNING')")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO saveScanning(Authentication authentication, @Valid @RequestBody ScanningInventoryRequestDTO scanningRequestDTO) {
        return scanningService.saveScanningInventory(authentication, scanningRequestDTO);
    }

    @GetMapping(value = "/case/{id}")
    @PreAuthorize("hasRole('SCANNING')")
    public Page<CaseResponseDTO> findCaseByInventory(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, @PathVariable("id") @Min(1) long id, Authentication authentication) {
        return scanningService.findCaseByInventory(ScanningCaseSpecifications.build(params), page, limit, id, authentication);
    }

    @PutMapping(value = "/case")
    @PreAuthorize("hasRole('SCANNING')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public CaseResponseDTO saveScanningCase(Authentication authentication, @Valid @RequestBody ScanningCaseRequestDTO scanningCaseRequestDTO) {
        return scanningService.saveScanningCase(authentication, scanningCaseRequestDTO);
    }
}
