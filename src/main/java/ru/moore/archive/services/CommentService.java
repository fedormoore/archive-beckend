package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.moore.archive.models.dtos.request.CommentsRequestDTO;
import ru.moore.archive.models.dtos.response.CommentsResponseDTO;
import ru.moore.archive.models.entity.*;
import ru.moore.archive.repositories.CommentsCaseRepository;
import ru.moore.archive.repositories.CommentsInventoryRepository;
import ru.moore.archive.services.mappers.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentsInventoryRepository commentsInventoryRepository;
    private final CommentsCaseRepository commentsCaseRepository;
    private final MapperUtils mapperUtils;

    public CommentsInventory saveCommentsInventory(CommentsRequestDTO commentsInventoryIn, Account account, String department, Inventory inventory) {
        if (commentsInventoryIn != null) {
            Optional<CommentsInventory> commentsInventorySearch = commentsInventoryRepository.findByInventoryIdAndAuthorIdAndDepartment(inventory.getId(), account.getId(), department);
            if (commentsInventorySearch.isPresent()) {
                commentsInventorySearch.get().setText(commentsInventoryIn.getText());
                commentsInventoryRepository.save(commentsInventorySearch.get());
                return commentsInventorySearch.get();
            } else {
                CommentsInventory commentsInventory = CommentsInventory.builder()
                        .author(account)
                        .department(department)
                        .inventoryId(inventory.getId())
                        .text(commentsInventoryIn.getText())
                        .build();
                commentsInventoryRepository.save(commentsInventory);
                return commentsInventory;
            }
        }
        return null;
    }

    public CommentsInventory loadCommentsInventory(Long inventoryId, Account account, String department) {
        Optional<CommentsInventory> commentsInventory =  commentsInventoryRepository.findByInventoryIdAndAuthorIdAndDepartment(inventoryId, account.getId(), department);
        if (commentsInventory.isPresent()){
            return commentsInventory.get();
        }else{
            return null;
        }
    }

    public List<CommentsResponseDTO> findCommentInventoryByInventory(Authentication authentication, long id) {
        return mapperUtils.mapAll(commentsInventoryRepository.findAllByInventoryId(id), CommentsResponseDTO.class);
    }

    public CommentsCase saveCommentsCase(CommentsRequestDTO commentsCaseIn, Account account, String department, Case acquisitionCaseSave) {
        if (commentsCaseIn != null) {
            Optional<CommentsCase> commentsCaseSearch = commentsCaseRepository.findByCaseIdAndAuthorIdAndDepartment(acquisitionCaseSave.getId(), account.getId(), department);
            if (commentsCaseSearch.isPresent()) {
                commentsCaseSearch.get().setText(commentsCaseIn.getText());
                commentsCaseRepository.save(commentsCaseSearch.get());
                return commentsCaseSearch.get();
            } else {
                CommentsCase commentsCase = CommentsCase.builder()
                        .author(account)
                        .department(department)
                        .caseId(acquisitionCaseSave.getId())
                        .text(commentsCaseIn.getText())
                        .build();
                commentsCaseRepository.save(commentsCase);
                return commentsCase;
            }
        }
        return null;
    }

    public CommentsCase loadCommentsCase(Long caseId, Account account, String department) {
        Optional<CommentsCase> commentsCase =  commentsCaseRepository.findByCaseIdAndAuthorIdAndDepartment(caseId, account.getId(), department);
        if (commentsCase.isPresent()){
            return commentsCase.get();
        }else{
            return null;
        }
    }

    public List<CommentsResponseDTO> findCommentCaseByInventory(Authentication authentication, long id) {
        return mapperUtils.mapAll(commentsCaseRepository.findAllByCaseId(id), CommentsResponseDTO.class);
    }
}
