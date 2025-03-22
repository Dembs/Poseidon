package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "trade")
@Data
@AllArgsConstructor
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotEmpty(message = "Account is mandatory")
    private String account;

    @NotEmpty(message = "Type is mandatory")
    private String type;

    @NotNull(message = "Enter a number")
    @Min(value = 1, message = "Enter a number higher than 0")
    private Double buyQuantity;

    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(){

    }
    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
