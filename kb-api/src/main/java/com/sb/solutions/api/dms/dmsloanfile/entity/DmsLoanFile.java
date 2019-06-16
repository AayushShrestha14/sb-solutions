package com.sb.solutions.api.dms.dmsloanfile.entity;

import com.google.gson.Gson;
import com.sb.solutions.api.loanDocument.entity.LoanDocument;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Priority;
import com.sb.solutions.core.enums.Securities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DmsLoanFile extends BaseEntity<Long> {
    @NotNull
    private String customerName;
    private String citizenshipNumber;
    private String contactNumber;
    private double interestRate;
    private double proposedAmount;


    private String security;
    @Column(columnDefinition = "text")
    private String documentPath;
    @Transient
    private List<String> documentMap;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LoanDocument> documents;
    @Transient
    private Set<Securities> securities;
    @Transient
    private List<Map<Object, Object>> documentPathMaps;
    private Date tenure;
    private Priority priority;
    private String recommendationConclusion;
    private String waiver;


    public List<Map<Object, Object>> getDocumentPathMaps() {
        String tempPath = null;
        Gson gson = new Gson();
        List<Map<Object, Object>> mapList = new ArrayList<>();
        try {
            tempPath = this.getDocumentPath();
            List tempList = gson.fromJson(tempPath, List.class);
            List<String> documentNames = new ArrayList<>();
            List<String> documentPaths = new ArrayList<>();
            int count = 0;
            for (Object list : tempList) {
                String toString = list.toString();
                String[] arrayOfString = toString.split(":");
                documentNames.add(arrayOfString[0]);
                documentPaths.add(arrayOfString[1]);
            }
            for (String documentPath : documentPaths) {
                Map<Object, Object> map = new LinkedHashMap<>();
                map.put(documentNames.get(count), documentPath);
                count++;
                mapList.add(map);
            }
            this.setDocumentPathMaps(mapList);
        } catch (Exception e) {
        }

        return this.documentPathMaps;
    }


}
