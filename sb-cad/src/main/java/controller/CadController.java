package controller;

import com.sb.solutions.core.dto.RestResponseDto;
import constant.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

@RestController
@RequestMapping(value = ApiConstants.CAD_BASE_API)
public class CadController {

    @PostMapping(value = ApiConstants.UNASSIGNED_LOAN)
    public ResponseEntity<?> getUnassignedLoanForCadAdmin(){
        return new RestResponseDto().successModel("ello");
    }
}
