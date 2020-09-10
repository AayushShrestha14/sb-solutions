package com.sb.solutions.api.crg.controller;

import com.sb.solutions.api.crg.entity.CrgGroup;
import com.sb.solutions.api.crg.service.CrgGroupService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
@RestController
@RequestMapping("/v1/crg-group")
@RequiredArgsConstructor
public class CrgGroupController {
    private final CrgGroupService crgGroupService;

    @PostMapping
    public final ResponseEntity<?> addGroup(@Valid @RequestBody CrgGroup group) {

        final CrgGroup crgGroup = crgGroupService.save(group);
        return new RestResponseDto().successModel(crgGroup);
    }

    @GetMapping
    public final ResponseEntity<?> findAllGroup() {
        return new RestResponseDto().successModel(crgGroupService.findAll());
    }


    @GetMapping("/{statusId}")
    public final ResponseEntity<?> findGroupsByStatus(@PathVariable Status status) {
        return new RestResponseDto().successModel(crgGroupService.findByStatus(status));
    }
}
