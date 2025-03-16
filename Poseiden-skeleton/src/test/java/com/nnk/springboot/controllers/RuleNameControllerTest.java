package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RuleNameService ruleNameService;

    @InjectMocks
    private RuleNameController ruleNameController;

    private RuleName ruleName1;
    private RuleName ruleName2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();

        ruleName1 = new RuleName("Rule 1", "Description 1", "Json 1", "Template 1", "SQL 1", "SQL Part 1");
        ruleName1.setId(1);

        ruleName2 = new RuleName("Rule 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        ruleName2.setId(2);
    }

    @Test
    public void testHome() throws Exception {
        when(ruleNameService.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));

        mockMvc.perform(get("/ruleName/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("ruleName/list"))
               .andExpect(model().attributeExists("ruleNames"))
               .andExpect(model().attribute("ruleNames", Arrays.asList(ruleName1, ruleName2)));

        verify(ruleNameService, times(1)).findAll();
    }

    @Test
    public void testAddRuleForm() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("ruleName/add"))
               .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    public void testValidate() throws Exception {
        RuleName validRule = new RuleName("Rule Test", "Description Test", "Json Test", "Template Test", "SQL Test", "SQL Part Test");

        mockMvc.perform(post("/ruleName/validate")
                       .flashAttr("ruleName", validRule))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleName.class));
    }


    @Test
    public void testShowUpdateForm() throws Exception {

        when(ruleNameService.findById(1)).thenReturn(Optional.of(ruleName1));

        mockMvc.perform(get("/ruleName/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("ruleName/update"))
               .andExpect(model().attributeExists("ruleName"))
               .andExpect(model().attribute("ruleName", ruleName1));

        verify(ruleNameService, times(1)).findById(1);
    }


    @Test
    public void testUpdateRuleName() throws Exception {

        RuleName updatedRule = new RuleName("Rule 1 Updated", "Description 1", "Json 1", "Template 1", "SQL 1", "SQL Part 1");
        updatedRule.setId(1);

        mockMvc.perform(post("/ruleName/update/1")
                       .flashAttr("ruleName", updatedRule))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).update(any(RuleName.class));
    }


    @Test
    public void testDeleteRuleName() throws Exception {

        when(ruleNameService.findById(1)).thenReturn(Optional.of(ruleName1));
        doNothing().when(ruleNameService).delete(1);

        mockMvc.perform(get("/ruleName/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).findById(1);
        verify(ruleNameService, times(1)).delete(1);
    }

}