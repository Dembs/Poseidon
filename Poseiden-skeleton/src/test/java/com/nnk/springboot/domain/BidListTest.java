package com.nnk.springboot.domain;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import jakarta.validation.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BidListTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validationTest() {
        // Test avec des valeurs valides
        BidList bidList = new BidList();
        bidList.setAccount("Account Test");
        bidList.setType("Type Test");
        bidList.setBidQuantity(10.0);

        Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
        assertTrue(violations.isEmpty());

        // Test avec des valeurs invalides
        bidList.setAccount("");
        bidList.setType("");

        violations = validator.validate(bidList);
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 2);
    }

    @Test
    public void bidListCreationTest() {

        BidList bidList = new BidList("Account Test", "Type Test", 10.0);

        Set<ConstraintViolation<BidList>> violations = validator.validate(bidList);
        assertTrue(violations.isEmpty());

        assertTrue("Account Test".equals(bidList.getAccount()));
        assertTrue("Type Test".equals(bidList.getType()));
        assertTrue(10.0 == bidList.getBidQuantity());
    }
}