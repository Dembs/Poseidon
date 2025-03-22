package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "Id")
    private Integer Id;

    @NotNull(message = "Must not be null")
    @Min(value = 1, message = "Enter a number higher than 0")
    private Integer curveId;

    private Timestamp asOfDate;

    @NotNull(message = "Enter a number")
    @Min(value = 1, message = "Enter a number higher than 0")
    private Double term;

    @NotNull(message = "Enter a number")
    @Min(value = 1, message = "Enter a number higher than 0")
    private Double value;

    private Timestamp creationDate;


    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }
}
