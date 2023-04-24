package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.response.HistoryResponseDTO;
import ru.moore.archive.services.HistoryService;

import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/history")
@RequiredArgsConstructor
@Validated
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping(value = "/inventory/{id}")
    @PreAuthorize("hasRole('STORAGE')")
    public List<HistoryResponseDTO> findByInventoryId(@PathVariable("id") @Min(1) long inventoryId) {
        return historyService.findByInventoryId(inventoryId);
    }

    @GetMapping(value = "/case/{id}")
    @PreAuthorize("hasRole('STORAGE')")
    public List<HistoryResponseDTO> findByCaseId(@PathVariable("id") @Min(1) long caseId) {
        return historyService.findByCaseId(caseId);
    }

}
