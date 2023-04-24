package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.LoadingCaseRequestDTO;
import ru.moore.archive.models.dtos.request.LoadingInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.LoadingCaseSpecifications;
import ru.moore.archive.repositories.specifications.LoadingInventorySpecifications;
import ru.moore.archive.repositories.specifications.StorageCaseSpecifications;
import ru.moore.archive.services.LoadingService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/loading")
@RequiredArgsConstructor
@Validated
public class LoadingController {

    private final LoadingService loadingService;

    @GetMapping(value = "/inventory")
    @PreAuthorize("hasRole('LOADING')")
    public Page<InventoryResponseDTO> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, Authentication authentication) {
        if (page < 1) {
            page = 1;
        }
        return loadingService.findAll(LoadingInventorySpecifications.build(params), page, limit, authentication);
    }

    @PutMapping(value = "/inventory")
    @PreAuthorize("hasRole('LOADING')")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO saveLoading(Authentication authentication, @Valid @RequestBody LoadingInventoryRequestDTO loadingRequestDTO) {
        return loadingService.saveLoadingInventory(authentication, loadingRequestDTO);
    }

    @GetMapping(value = "/case/{id}")
    @PreAuthorize("hasRole('LOADING')")
    public Page<CaseResponseDTO> findCaseByInventory(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, @PathVariable("id") @Min(1) long id, Authentication authentication) {
        return loadingService.findCaseByInventory(LoadingCaseSpecifications.build(params), page, limit, id, authentication);
    }

    @PutMapping(value = "/case")
    @PreAuthorize("hasRole('LOADING')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public CaseResponseDTO saveLoadingCase(Authentication authentication, @Valid @RequestBody LoadingCaseRequestDTO loadingCaseRequestDTO) {
        return loadingService.saveLoadingCase(authentication, loadingCaseRequestDTO);
    }
}
