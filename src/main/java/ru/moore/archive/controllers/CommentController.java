package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.response.CommentsResponseDTO;
import ru.moore.archive.services.CommentService;

import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "inventory/{id}")
    public List<CommentsResponseDTO> findCommentInventoryByInventory(Authentication authentication, @PathVariable("id") @Min(1) long id) {
        return commentService.findCommentInventoryByInventory(authentication, id);
    }

    @GetMapping(value = "case/{id}")
    public List<CommentsResponseDTO> findCommentCaseByInventory(Authentication authentication, @PathVariable("id") @Min(1) long id) {
        return commentService.findCommentCaseByInventory(authentication, id);
    }

}
