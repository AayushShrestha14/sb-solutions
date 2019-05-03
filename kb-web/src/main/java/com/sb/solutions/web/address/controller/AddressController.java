package com.sb.solutions.web.address.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.district.service.DistrictService;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.municipalityVdc.service.MunicipalityVdcService;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.api.address.province.service.ProvinceService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/address")
public class AddressController {

    private final DistrictService districtService;
    private final ProvinceService provinceService;
    private final MunicipalityVdcService municipality_vdcService;

    @GetMapping("/province")
    public ResponseEntity<?> getProvince() {
        return new RestResponseDto().successModel(provinceService.findAll());
    }

    @GetMapping("/district")
    public ResponseEntity<?> getDistrict() {
        return new RestResponseDto().successModel(districtService.findAll());
    }

    @GetMapping("/municipalityVdc")
    public ResponseEntity<?> getMunicipalityVdc() {
        return new RestResponseDto().successModel(municipality_vdcService.findAll());
    }

    @PostMapping("/district")
    public ResponseEntity<?> saveDistrict(@RequestBody District district) {
        return new RestResponseDto().successModel(districtService.save(district));
    }

    @PostMapping("/province")
    public ResponseEntity<?> saveProvince(@RequestBody Province province) {
        return new RestResponseDto().successModel(provinceService.save(province));
    }

    @PostMapping("/municipalityVdc")
    public ResponseEntity<?> saveMunicipality_VDC(@RequestBody MunicipalityVdc municipality_vdc) {
        return new RestResponseDto().successModel(municipality_vdcService.save(municipality_vdc));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/getDistrict")
    public ResponseEntity<?> getDistrictPage(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
            districtService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/getProvince")
    public ResponseEntity<?> getProvincePage(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
            provinceService.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/getMunicipalityVdc")
    public ResponseEntity<?> getMunicipalityVdcPage(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(municipality_vdcService
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @PostMapping(value = "/districtByProvince")
    public ResponseEntity getDistrictByProvince(@RequestBody Province province) {
        return new RestResponseDto().successModel(districtService.findAllByProvince(province));

    }

    @PostMapping(value = "/municipalityVdcByDistrict")
    public ResponseEntity getMunicipalityVdcByDistrict(@RequestBody District district) {
        return new RestResponseDto()
            .successModel(municipality_vdcService.findAllByDistrict(district));
    }
}
