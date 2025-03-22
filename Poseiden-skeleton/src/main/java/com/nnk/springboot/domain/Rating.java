package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rating")
@Data
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Must not be empty")
    private String moodysRating;

    @NotEmpty(message = "Must not be empty")
    private String sandPRating;

    @NotEmpty(message = "Must not be empty")
    private String fitchRating;

    @NotNull(message = "Enter a number")
    @Min(value = 1, message = "Enter a number higher than 0")
    private Integer orderNumber;

    public Rating() {

    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
