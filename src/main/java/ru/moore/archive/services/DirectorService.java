package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.dtos.request.DirectorRequestDTO;
import ru.moore.archive.models.dtos.response.DirectorResponseDTO;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.models.entity.Inventory;
import ru.moore.archive.models.entity.StatusRecord;
import ru.moore.archive.repositories.DirectorRepository;
import ru.moore.archive.repositories.AcquisitionInventoryRepository;
import ru.moore.archive.security.dto.SecurityDTO;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final HistoryService historyService;
    private final DirectorRepository directorRepository;
    private final AcquisitionInventoryRepository inventoryRepository;
    private final MapperUtils mapperUtils;

    private Account getAuthor(Authentication authentication) {
        if (authentication == null) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Нет секретного ключа");
        }
        return mapperUtils.map(((SecurityDTO) authentication.getPrincipal()).getAccount(), Account.class);
    }

    public List<DirectorResponseDTO> findAll() {
        return mapperUtils.mapAll(directorRepository.findAll(), DirectorResponseDTO.class);
    }


    public DirectorResponseDTO saveDirector(Authentication authentication, DirectorRequestDTO directorRequestDTO) {
        Optional<Inventory> inventory = inventoryRepository.findById(directorRequestDTO.getId());
        if (!inventory.get().getStatusRecord().getStatus().equals(StatusRecordEnum.IS_DIRECTOR.getStatus())) {
            throw new ErrorTemplate(HttpStatus.BAD_REQUEST, "Запись нельзя редактировать");
        }
        inventory.get().setStatusRecord(mapperUtils.map(directorRequestDTO.getStatusRecord(), StatusRecord.class));

        historyService.saveHistoryInventory(mapperUtils.map(directorRequestDTO.getStatusRecord(), StatusRecord.class), "", getAuthor(authentication), directorRequestDTO.getId());
        inventoryRepository.save(inventory.get());
        return mapperUtils.map(directorRepository.findById(directorRequestDTO.getId()).get(), DirectorResponseDTO.class);
    }

}
