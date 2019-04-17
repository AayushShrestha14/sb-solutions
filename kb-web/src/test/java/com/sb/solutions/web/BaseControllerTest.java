package com.sb.solutions.web;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@RunWith(MockitoJUnitRunner.class)
public class BaseControllerTest {

    protected LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
