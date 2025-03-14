package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName1;
    private RuleName ruleName2;

    @BeforeEach
    public void setUp() {
        ruleName1 = new RuleName("Rule 1", "Description 1", "Json 1", "Template 1", "SQL 1", "SQL Part 1");
        ruleName1.setId(1);

        ruleName2 = new RuleName("Rule 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        ruleName2.setId(2);
    }

    @Test
    public void testFindAll() {
        // Given
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));

        List<RuleName> result = ruleNameService.findAll();

        assertEquals(2, result.size());
        assertEquals("Rule 1", result.get(0).getName());
        assertEquals("Rule 2", result.get(1).getName());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_WhenRuleExists() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName1));

        Optional<RuleName> result = ruleNameService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Rule 1", result.get().getName());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_WhenRuleDoesNotExist() {

        when(ruleNameRepository.findById(3)).thenReturn(Optional.empty());

        Optional<RuleName> result = ruleNameService.findById(3);

        assertFalse(result.isPresent());
        verify(ruleNameRepository, times(1)).findById(3);
    }

    @Test
    public void testSave() {
        RuleName newRule = new RuleName("New Rule", "New Description", "New Json",
                "New Template", "New SQL", "New SQL Part");
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(newRule);

        RuleName result = ruleNameService.save(newRule);

        assertEquals("New Rule", result.getName());
        verify(ruleNameRepository, times(1)).save(newRule);
    }

    @Test
    public void testUpdate() {
        RuleName updatedRule = new RuleName("Updated Rule", "Updated Description", "Updated Json",
                "Updated Template", "Updated SQL", "Updated SQL Part");
        updatedRule.setId(1);
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(updatedRule);

        RuleName result = ruleNameService.update(updatedRule);

        assertEquals("Updated Rule", result.getName());
        verify(ruleNameRepository, times(1)).save(updatedRule);
    }

    @Test
    public void testDelete() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.delete(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}