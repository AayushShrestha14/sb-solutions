package com.sb.solutions.api.openingForm.service;

import java.util.Map;

import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.core.service.BaseService;

public interface OpeningFormService extends BaseService<OpeningForm> {

    Map<Object, Object> getStatus();

}
