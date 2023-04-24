package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.dtos.request.AccountRequestDTO;
import ru.moore.archive.models.dtos.response.AccountResponseDTO;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.repositories.AccountRepository;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final MapperUtils mapperUtils;
    private final ModelMapper modelMapper;

//    public Optional<AccountResponseDTO> findById(long id) {
//        Optional<AccountResponseDTO> accountOptional = Optional.of(mapperUtils.map(accountRepository.findById(id), AccountResponseDTO.class));
//        return accountOptional;
//    }

    public Page<AccountResponseDTO> findAll(Specification<Account> spec, int page, int pageSize) {
        return accountRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(this::toDto);
    }

    @Transactional
    public AccountResponseDTO saveAccount(Authentication authentication, AccountRequestDTO accountRequestDTO) {

        Optional<Account> accountLogin = accountRepository.findByLogin(accountRequestDTO.getLogin());
        if (accountLogin.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Логин занят");
        }
        accountRequestDTO.setPassword(passwordEncoder.encode(accountRequestDTO.getPassword()));

        Account accountSave = mapperUtils.map(accountRequestDTO, Account.class);
        return mapperUtils.map(accountRepository.save(accountSave), AccountResponseDTO.class);
    }

    @Transactional
    public AccountResponseDTO updateAccount(Authentication authentication, AccountRequestDTO accountRequestDTO) {
        Optional<Account> accountLogin = accountRepository.findByLoginAndIdIsNot(accountRequestDTO.getLogin(), accountRequestDTO.getId());
        if (accountLogin.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Логин занят");
        }
        Optional<Account> accountPassword = accountRepository.findById(accountRequestDTO.getId());
        accountRequestDTO.setPassword(accountPassword.get().getPassword());

        Account accountSave = mapperUtils.map(accountRequestDTO, Account.class);
        return mapperUtils.map(accountRepository.save(accountSave), AccountResponseDTO.class);
    }

    @Transactional
    public AccountResponseDTO deleteAccount(Authentication authentication, Long id) {
        Optional<Account> accountPassword = accountRepository.findById(id);
        accountPassword.get().setPassword(accountPassword.get().getPassword());
        accountPassword.get().setDeleted(true);

        return mapperUtils.map(accountRepository.save(accountPassword.get()), AccountResponseDTO.class);
    }

    private AccountResponseDTO toDto(Account product) {
        return modelMapper.map(product, AccountResponseDTO.class);
    }
}
