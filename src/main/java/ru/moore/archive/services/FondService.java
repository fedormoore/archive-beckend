package ru.moore.archive.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.moore.archive.exceptions.ErrorTemplate;
import ru.moore.archive.models.dtos.request.FondRequestDTO;
import ru.moore.archive.models.dtos.response.FondResponseDTO;
import ru.moore.archive.models.entity.Fond;
import ru.moore.archive.repositories.FondRepository;
import ru.moore.archive.services.mappers.MapperUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FondService {

    private final FondRepository fondRepository;
    private final MapperUtils mapperUtils;
    private final ModelMapper modelMapper;

    public Page<FondResponseDTO> findAll(Specification<Fond> spec, int page, int pageSize) {
        return fondRepository.findAll(spec, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "numberFond"))).map(this::toDto);
    }

    @Transactional
    public FondResponseDTO saveFond(Authentication authentication, FondRequestDTO fondRequestDTO) {
        Optional<Fond> fondByName = fondRepository.findByName(fondRequestDTO.getName());
        if (fondByName.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Наименование фонда уже существует");
        }
        Optional<Fond> fondByNumber = fondRepository.findByNumberFond(fondRequestDTO.getNumberFond());
        if (fondByNumber.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Номер фонда уже существует");
        }

        Fond fondSave = mapperUtils.map(fondRequestDTO, Fond.class);
        return mapperUtils.map(fondRepository.save(fondSave), FondResponseDTO.class);
    }

    @Transactional
    public FondResponseDTO updateFond(Authentication authentication, FondRequestDTO fondRequestDTO) {
        Optional<Fond> fondByName = fondRepository.findByNameAndIdIsNot(fondRequestDTO.getName(), fondRequestDTO.getId());
        if (fondByName.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Наименование фонда уже существует");
        }
        Optional<Fond> fondByNumber = fondRepository.findByNumberFondAndIdIsNot(fondRequestDTO.getNumberFond(), fondRequestDTO.getId());
        if (fondByNumber.isPresent()) {
            throw new ErrorTemplate(HttpStatus.NOT_FOUND, "Номер фонда уже существует");
        }

        Fond accountSave = mapperUtils.map(fondRequestDTO, Fond.class);
        return mapperUtils.map(fondRepository.save(accountSave), FondResponseDTO.class);
    }

    @Transactional
    public FondResponseDTO deleteFond(Authentication authentication, Long id) {
        Optional<Fond> fond = fondRepository.findById(id);
        fond.get().setDeleted(true);

        return mapperUtils.map(fondRepository.save(fond.get()), FondResponseDTO.class);
    }

    private FondResponseDTO toDto(Fond fond) {
        return modelMapper.map(fond, FondResponseDTO.class);
    }

    public List<FondResponseDTO> allFondSpr() {
        return mapperUtils.mapAll(fondRepository.findAll(), FondResponseDTO.class);
    }
}
