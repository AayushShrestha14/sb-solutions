package com.sb.solutions.entity;

import com.sb.solutions.api.stage.entity.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author : Rujan Maharjan on  12/4/2020
 **/
@Entity
@Data
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class CadStage extends Stage {
}
