package com.sb.solutions.web.Segment.subSegment;

import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import com.sb.solutions.api.segments.subSegment.repository.SubSegmentRepository;
import com.sb.solutions.api.segments.subSegment.service.SubSegmentService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/subSegment")
public class SubSegmentController {
    private final SubSegmentService subSegmentSetvice;

    @PostMapping
    public ResponseEntity<?> saveSubSegment(@RequestBody SubSegment subSegment) {
        return new RestResponseDto().successModel(subSegmentSetvice.save(subSegment));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SubSegment subSegment, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(subSegmentSetvice.findAllPageable(subSegment,new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getSubSegmentStatusCount() {
        return new RestResponseDto().successModel(subSegmentSetvice.subSegmentStatusCount());
    }
}
