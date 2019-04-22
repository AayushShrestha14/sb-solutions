package com.sb.solutions.web.Sector.SectorController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.sector.sector.entity.Sector;
import com.sb.solutions.api.sector.sector.service.SectorService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/sector")
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<?> saveSector(@RequestBody Sector sector) {
        return new RestResponseDto().successModel(sectorService.save(sector));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAllPage(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(sectorService.findAllPageable(searchDto, PaginationUtils
                .pageable(page, size)));
    }

    @GetMapping(value = "/getList")
    public ResponseEntity<?> getAllList() {
        return new RestResponseDto().successModel(sectorService.findAll());
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getSectorStatusCount() {
        return new RestResponseDto().successModel(sectorService.sectorStatusCount());
    }
}
