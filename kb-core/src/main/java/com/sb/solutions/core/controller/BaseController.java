package com.sb.solutions.core.controller;

import java.util.Map;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.solutions.core.dto.BaseMapper;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.service.Service;
import com.sb.solutions.core.utils.PaginationUtils;

/**
 * Base Controller which expose CRUD api
 *
 * @param <E> Entity
 * @param <D> DTO
 * @param <ID> ID type
 */
public abstract class BaseController<E, D, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final Service<E, ID> service;

    private final BaseMapper<E, D> mapper;

    public BaseController(Service<E, ID> service, BaseMapper<E, D> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    protected abstract Logger getLogger();

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody D dto) {

        final E saved = service.save(mapper.mapDtoToEntity(dto));

        if (null == saved) {
            logger.error("Error while saving memo {}", dto);
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + dto);
        }

        return new RestResponseDto().successModel(mapper.mapEntityToDto(saved));
    }

    @GetMapping
    public ResponseEntity<?> getPageable(
        @RequestParam(required = false) Map<String, String> requestParams,
        @RequestParam("page") int page, @RequestParam("size") int size) {

        final Pageable pageable = PaginationUtils.pageable(page, size);
        final Map<String, String> filters = PaginationUtils
            .excludePageableProperties(requestParams);

        final Page<E> entities = service.findPageableBySpec(filters, pageable);
        final Page<D> dtos = new PageImpl<>(mapper.mapEntitiesToDtos(entities.getContent()),
            pageable, entities.getTotalElements());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {
        return new RestResponseDto().successModel(mapper.mapEntityToDto(service.findOne(id).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody D dto) {

        final E saved = service.save(mapper.mapDtoToEntity(dto));

        if (null == saved) {
            return new RestResponseDto()
                .failureModel("Error occurred while saving Memo " + dto);
        }

        return new RestResponseDto().successModel(mapper.mapEntityToDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(mapper.mapEntitiesToDtos(service.findAll()));
    }
}
