package com.sb.solutions.api.Loan;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sb.solutions.api.stage.entity.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@NoArgsConstructor
public class LoanStage extends Stage {

}