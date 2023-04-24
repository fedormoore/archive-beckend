package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.DirectorRequestDTO;
import ru.moore.archive.models.dtos.response.DirectorResponseDTO;
import ru.moore.archive.models.entity.Director;
import ru.moore.archive.services.DirectorService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/director")
@RequiredArgsConstructor
@Validated
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping()
    @PreAuthorize("hasRole('DIRECTOR')")
    public List<DirectorResponseDTO> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit) {
        if (page < 1) {
            page = 1;
        }
//        return directorService.findAll(DirectorSpecifications.build(params), page, limit);
        return directorService.findAll();
    }

    @PutMapping()
    @PreAuthorize("hasRole('DIRECTOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public DirectorResponseDTO saveDirector(Authentication authentication, @Valid @RequestBody DirectorRequestDTO directorRequestDTO) {
        return directorService.saveDirector(authentication, directorRequestDTO);
    }

}
