package com.nnk.springboot.domain;

import jakarta.persistence.*;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "rulename")
@Data

public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Must not be empty")
    private String name;

    @NotEmpty(message = "Must not be empty")
    private String description;

    @NotEmpty(message = "Must not be empty")
    private String json;

    @NotEmpty(message = "Must not be empty")
    private String template;

    @NotEmpty(message = "Must not be empty")
    private String sqlStr;

    @NotEmpty(message = "Must not be empty")
    private String sqlPart;

    public RuleName() {

    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
