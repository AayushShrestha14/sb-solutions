package com.sb.solutions.api.dms.dmsloanfile.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;

import com.sb.solutions.api.loanDocument.entity.LoanDocument;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Priority;
import com.sb.solutions.core.enums.Securities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DmsLoanFile extends BaseEntity<Long> {

    private @NotNull String customerName;
    private String citizenshipNumber;
    private String contactNumber;
    private double interestRate;
    private double proposedAmount;
    private String security;
    @Column(columnDefinition = "text")
    private String documentPath;
    @Transient
    private List<String> documentMap;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LoanDocument> documents;
    @Transient
    private List<Securities> securities;
    @Transient
    private List<Map<Object, Object>> documentPathMaps;
    private Date tenure;
    private int tenureDuration;
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

    @PrePersist
    public void prePersist() {
        String mapSecurity = "";
        for (Securities securities : this.getSecurities()) {
            mapSecurity += securities.ordinal() + ",";
        }
        mapSecurity = mapSecurity.substring(0, mapSecurity.length() - 1);
        this.setSecurity(mapSecurity);
        this.setDocumentPath(new Gson().toJson(this.getDocumentMap()));
        this.setCreatedAt(new Date());
    }


}
