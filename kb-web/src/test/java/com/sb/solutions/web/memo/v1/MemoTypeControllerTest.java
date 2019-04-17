package com.sb.solutions.web.memo.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.solutions.api.memo.service.MemoTypeService;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class MemoTypeControllerTest {

    private MockMvc mvc;

    @Mock
    private MemoTypeService service;

    @InjectMocks
    private MemoTypeController controller;

    private ObjectMapper mapper = new ObjectMapper();

    public MemoTypeControllerTest() {
        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator())
            .build();
    }

    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Test
    public void save() throws Exception {
        /*final MemoType type = new MemoType();
        type.setName("memo type 1");
        type.setStatus(Status.ACTIVE);

        *//*mvc.perform(post(MemoTypeController.URL).contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(type)))
            .andDo(print());*//*

        mvc.perform(get(MemoTypeController.URL).contentType(MediaType.APPLICATION_JSON))
            .andDo(print());*/
    }

    @Test
    public void getAll() {
    }
}