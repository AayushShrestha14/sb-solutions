package com.sb.solutions.core.enitity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 * <p>
 * Base entity class, every entity class should extend this
 */

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AbstractBaseEntity<PK extends Serializable> extends AbstractPersistable<PK> {


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastModified;




    //@Version
    //private int version;

}
