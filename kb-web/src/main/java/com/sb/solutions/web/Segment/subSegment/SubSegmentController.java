package com.sb.solutions.web.Segment.subSegment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import com.sb.solutions.api.segments.subSegment.service.SubSegmentService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/subSegment")
public class SubSegmentController {

    private final SubSegmentService subSegmentSetvice;

    @PostMapping
    public ResponseEntity<?> saveSubSegment(@RequestBody SubSegment subSegment) {
        System.out.println(subSegment);
        return new RestResponseDto().successModel(subSegmentSetvice.save(subSegment));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(subSegmentSetvice
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getSubSegmentStatusCount() {
        return new RestResponseDto().successModel(subSegmentSetvice.subSegmentStatusCount());
    }
}
