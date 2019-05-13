package com.sb.solutions.web.loan;

import com.sb.solutions.core.utils.JsonFileMaker.JsonMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rujan Maharjan on 5/10/2019
 */

@RestController
@RequestMapping("/v1/loanDocument")
public class LoanController {

    @Autowired
    JsonMaker jsonMaker;

    @GetMapping
    public void testJson() {
        jsonMaker.jsonObjectToFile(null, null);
    }
}
