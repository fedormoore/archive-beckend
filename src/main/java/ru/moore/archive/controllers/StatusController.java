package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.moore.archive.models.dtos.response.StatusInventoryResponseDTO;
import ru.moore.archive.models.dtos.response.StatusRecordResponseDTO;
import ru.moore.archive.services.StatusService;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping(value = "/inventory")
    @PreAuthorize("hasRole('ACQUISITION')")
    public List<StatusInventoryResponseDTO> findAllAcquisition() {
        return statusService.findAllInventory();
    }

    @GetMapping(value = "/acquisition/inventory")
    @PreAuthorize("hasRole('ACQUISITION')")
    public List<StatusRecordResponseDTO> findAllAcquisitionInventory() {
        return statusService.findAllAcquisitionInventory();
    }

    @GetMapping(value = "/storage/inventory")
    @PreAuthorize("hasRole('STORAGE')")
    public List<StatusRecordResponseDTO> findAllStorageInventory() {
        return statusService.findAllStorageInventory();
    }

    @GetMapping(value = "/scanning/inventory")
    @PreAuthorize("hasRole('SCANNING')")
    public List<StatusRecordResponseDTO> findAllScanningInventory() {
        return statusService.findAllScanningInventory();
    }

    @GetMapping(value = "/loading/inventory")
    @PreAuthorize("hasRole('SCANNING')")
    public List<StatusRecordResponseDTO> findAllLoadingInventory() {
        return statusService.findAllLoadingInventory();
    }

    @GetMapping(value = "/director")
    @PreAuthorize("hasRole('DIRECTOR')")
    public List<StatusRecordResponseDTO> findAllDirector() {
        return statusService.findAllDirector();
    }
}
