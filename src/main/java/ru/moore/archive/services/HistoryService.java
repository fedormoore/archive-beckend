package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.moore.archive.models.dtos.response.HistoryResponseDTO;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.models.entity.HistoryCase;
import ru.moore.archive.models.entity.HistoryInventory;
import ru.moore.archive.models.entity.StatusRecord;
import ru.moore.archive.repositories.HistoryCaseRepository;
import ru.moore.archive.repositories.HistoryInventoryRepository;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryInventoryRepository historyInventoryRepository;
    private final HistoryCaseRepository historyCaseRepository;
    private final MapperUtils mapperUtils;

    public List<HistoryResponseDTO> findByInventoryId(long inventoryId) {
        return mapperUtils.mapAll(historyInventoryRepository.findAllByInventoryId(inventoryId, Sort.by(Sort.Direction.ASC, "dateRecord")), HistoryResponseDTO.class);
    }

    public List<HistoryResponseDTO> findByCaseId(long caseId) {
        return mapperUtils.mapAll(historyCaseRepository.findAllByCaseId(caseId, Sort.by(Sort.Direction.ASC, "dateRecord")), HistoryResponseDTO.class);
    }

    public void saveHistoryInventory(StatusRecord status, String text, Account author, long inventoryId){
        HistoryInventory history = HistoryInventory.builder()
                .dateRecord(new Date())
                .statusRecord(status)
                .text(text)
                .author(author)
                .inventoryId(inventoryId)
                .build();
        historyInventoryRepository.save(history);
    }

    public void saveHistoryCase(StatusRecord status, String text, Account author, long caseId){
        HistoryCase history = HistoryCase.builder()
                .dateRecord(new Date())
                .statusRecord(status)
                .text(text)
                .author(author)
                .caseId(caseId)
                .build();
        historyCaseRepository.save(history);
    }

}
