package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.FondRequestDTO;
import ru.moore.archive.models.dtos.response.FondResponseDTO;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.FondSpecifications;
import ru.moore.archive.services.FondService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1/app/fond")
@RequiredArgsConstructor
@Validated
public class FondController {

    private final FondService fondService;

    @GetMapping()
    @PreAuthorize("hasRole('ACQUISITION')")
    public Page<FondResponseDTO> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "20") int limit) {
        if (page < 1) {
            page = 1;
        }
        return fondService.findAll(FondSpecifications.build(params), page, limit);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public FondResponseDTO saveFond(Authentication authentication, @Valid @RequestBody FondRequestDTO account) {
        return fondService.saveFond(authentication, account);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public FondResponseDTO updateFond(Authentication authentication, @Valid @RequestBody FondRequestDTO account) {
        return fondService.updateFond(authentication, account);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ACQUISITION')")
    @ResponseStatus(HttpStatus.CREATED)
    public FondResponseDTO deleteFond(Authentication authentication, @PathVariable("id") @Min(1) long id) {
        return fondService.deleteFond(authentication, id);
    }

    @GetMapping("/spr")
    @PreAuthorize("hasRole('ACQUISITION')")
    public List<FondResponseDTO> allFondSpr() {
        return fondService.allFondSpr();
    }
}
