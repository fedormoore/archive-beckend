package ru.moore.archive.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moore.archive.models.dtos.request.AccountRequestDTO;
import ru.moore.archive.models.dtos.response.AccountResponseDTO;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.repositories.specifications.AccountSpecifications;
import ru.moore.archive.services.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/v1/app/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AccountResponseDTO> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
        if (page < 1) {
            page = 1;
        }
        return accountService.findAll(AccountSpecifications.build(params), page, limit);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public AccountResponseDTO saveAccount(Authentication authentication, @Valid @RequestBody AccountRequestDTO account) {
        return accountService.saveAccount(authentication, account);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnUpdate.class)
    public AccountResponseDTO updateAccount(Authentication authentication, @Valid @RequestBody AccountRequestDTO account) {
        return accountService.updateAccount(authentication, account);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO deleteAccount(Authentication authentication, @PathVariable("id") @Min(1) long id) {
        return accountService.deleteAccount(authentication, id);
    }

}
