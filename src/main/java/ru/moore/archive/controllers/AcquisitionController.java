package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.AcquisitionCaseRequestDTO;
import ru.moore.archive.models.dtos.request.AcquisitionInventoryRequestDTO;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.dtos.response.InventoryResponseDTO;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.AcquisitionCaseSpecifications;
import ru.moore.archive.repositories.specifications.AcquisitionInventorySpecifications;
import ru.moore.archive.services.AcquisitionService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/acquisition")
@RequiredArgsConstructor
@Validated
public class AcquisitionController {

    private final AcquisitionService acquisitionService;

    @GetMapping(value = "/inventory")
    @PreAuthorize("hasRole('ACQUISITION')")
    public Page<InventoryResponseDTO> findAllAcquisition(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, Authentication authentication) {
        if (page < 1) {
            page = 1;
        }
        return acquisitionService.findAllAcquisition(AcquisitionInventorySpecifications.build(params), page, limit, authentication);
    }

    @PostMapping(value = "/inventory")
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public InventoryResponseDTO saveAcquisitionInventory(Authentication authentication, @Valid @RequestBody AcquisitionInventoryRequestDTO inventoryRequestDTO) {
        return acquisitionService.saveAcquisitionInventory(authentication, inventoryRequestDTO);
    }

    @PutMapping(value = "/inventory")
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public InventoryResponseDTO updateAcquisitionInventory(Authentication authentication, @Valid @RequestBody AcquisitionInventoryRequestDTO acquisitionRequestDTO) {
        return acquisitionService.updateAcquisitionInventory(authentication, acquisitionRequestDTO);
    }

    @DeleteMapping(value = "/inventory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDTO deleteInventory(Authentication authentication, @PathVariable("id") @Min(1) long id) {
        return acquisitionService.deleteInventory(authentication, id);
    }

    @GetMapping(value = "/case/{id}")
    @PreAuthorize("hasRole('ACQUISITION')")
    public Page<CaseResponseDTO> findCaseByInventory(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit, @PathVariable("id") @Min(1) long id, Authentication authentication) {
        if (page < 1) {
            page = 1;
        }
        return acquisitionService.findCaseByInventory(AcquisitionCaseSpecifications.build(params), page, limit, id, authentication);
    }

    @PostMapping(value = "/case")
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public CaseResponseDTO saveAcquisitionCase(Authentication authentication, @Valid @RequestBody AcquisitionCaseRequestDTO acquisitionCaseRequestDTO) {
        return acquisitionService.saveAcquisitionCase(authentication, acquisitionCaseRequestDTO);
    }

    @PutMapping(value = "/case")
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public CaseResponseDTO updateAcquisitionCase(Authentication authentication, @Valid @RequestBody AcquisitionCaseRequestDTO acquisitionCaseRequestDTO) {
        return acquisitionService.updateAcquisitionCase(authentication, acquisitionCaseRequestDTO);
    }
}
