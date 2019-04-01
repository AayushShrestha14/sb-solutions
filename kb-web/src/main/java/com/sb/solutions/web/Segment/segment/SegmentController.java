package com.sb.solutions.web.Segment.segment;

import com.sb.solutions.api.segments.segment.entity.Segment;
import com.sb.solutions.api.segments.segment.service.SegmentService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/segment")
public class SegmentController {

    private final SegmentService segmentService;
    @PostMapping
    public ResponseEntity<?> saveSegment(@RequestBody Segment segment) {
        return new RestResponseDto().successModel(segmentService.save(segment));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAllPage(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(segmentService.findAllPageable(searchDto,new CustomPageable().pageable(page, size)));
    }
    @GetMapping(value = "/getList")
    public ResponseEntity<?> getAllList() {
        return new RestResponseDto().successModel(segmentService.findAll());
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getSegmentStatusCount() {
        return new RestResponseDto().successModel(segmentService.segmentStatusCount());
    }
}
