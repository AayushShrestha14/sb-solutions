package com.sb.solutions.web.Sector.SubSectorController;

import com.sb.solutions.api.sector.subsector.entity.SubSector;
import com.sb.solutions.api.sector.subsector.service.SubSectorService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/subSector")
public class SubSectorController {
    private final SubSectorService subSectorService;

    @PostMapping
    public ResponseEntity<?> saveSubSector(@RequestBody SubSector subSector) {
        return new RestResponseDto().successModel(subSectorService.save(subSector));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SubSector subSector, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(subSectorService.findAllPageable(subSector,new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getSubSectorStatusCount() {
        return new RestResponseDto().successModel(subSectorService.subSectorStatusCount());
    }
}
