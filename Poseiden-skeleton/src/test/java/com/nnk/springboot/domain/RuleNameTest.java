package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleNameTest {



    @Test
    public void testRuleNameSettersAndGetters() {
        // Create an empty RuleName and set values using setters
        RuleName ruleName = new RuleName();

        ruleName.setId(1);
        ruleName.setName("Updated Name");
        ruleName.setDescription("Updated Description");
        ruleName.setJson("Updated Json");
        ruleName.setTemplate("Updated Template");
        ruleName.setSqlStr("Updated SQL");
        ruleName.setSqlPart("Updated SQL Part");

        // Verify values using getters
        assertEquals(Integer.valueOf(1), ruleName.getId());
        assertEquals("Updated Name", ruleName.getName());
        assertEquals("Updated Description", ruleName.getDescription());
        assertEquals("Updated Json", ruleName.getJson());
        assertEquals("Updated Template", ruleName.getTemplate());
        assertEquals("Updated SQL", ruleName.getSqlStr());
        assertEquals("Updated SQL Part", ruleName.getSqlPart());
    }

}