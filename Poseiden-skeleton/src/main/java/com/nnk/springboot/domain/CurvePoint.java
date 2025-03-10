package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;


import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer curveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;


    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }
}
