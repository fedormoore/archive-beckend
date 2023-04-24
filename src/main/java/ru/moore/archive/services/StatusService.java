package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.moore.archive.models.dtos.response.StatusInventoryResponseDTO;
import ru.moore.archive.models.dtos.response.StatusRecordResponseDTO;
import ru.moore.archive.repositories.StatusInventoryRepository;
import ru.moore.archive.repositories.StatusRecordRepository;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRecordRepository statusRecordRepository;
    private final StatusInventoryRepository statusInventoryRepository;
    private final MapperUtils mapperUtils;

    public List<StatusInventoryResponseDTO> findAllInventory() {
        return mapperUtils.mapAll(statusInventoryRepository.findAll(), StatusInventoryResponseDTO.class);
    }

    public List<StatusRecordResponseDTO> findAllAcquisitionInventory() {
        return mapperUtils.mapAll(statusRecordRepository.findAllBySelectionContains("ACQUISITION"), StatusRecordResponseDTO.class);
    }

    public List<StatusRecordResponseDTO> findAllStorageInventory() {
        return mapperUtils.mapAll(statusRecordRepository.findAllBySelectionContains("STORAGE"), StatusRecordResponseDTO.class);
    }

    public List<StatusRecordResponseDTO> findAllScanningInventory() {
        return mapperUtils.mapAll(statusRecordRepository.findAllBySelectionContains("SCANNING"), StatusRecordResponseDTO.class);
    }

    public List<StatusRecordResponseDTO> findAllLoadingInventory() {
        return mapperUtils.mapAll(statusRecordRepository.findAllBySelectionContains("LOADING"), StatusRecordResponseDTO.class);
    }

    public List<StatusRecordResponseDTO> findAllDirector() {
        return mapperUtils.mapAll(statusRecordRepository.findAllBySelectionContains("DIRECTOR"), StatusRecordResponseDTO.class);
    }
}
