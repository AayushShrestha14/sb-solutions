package com.sb.solutions.api.document.validator;


import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.document.service.DocumentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Data
@AllArgsConstructor
public class DocumentValidator {
    private DocumentService documentService;

    public String isValid(Document document) {
        if (StringUtils.isEmpty(document.getName())) {
            return "Document name field is empty";
        }
        if (StringUtils.isEmpty(document.getUrl())){
            return "Document url field is empty";
        }
        if (StringUtils.isEmpty(documentService.findByName(document))) {
            return "Valid";
        }
        else{
            return "Document of name " + document.getName() + " exists";
        }
    }
}
