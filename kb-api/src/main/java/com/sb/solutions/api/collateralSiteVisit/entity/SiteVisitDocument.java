package com.sb.solutions.api.collateralSiteVisit.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.File;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Mohammad Hussain on Jun, 2021
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SiteVisitDocument extends BaseEntity<Long> {
    private String docName;
    private String isPrintable;
    private String docPath;
}
