package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CurveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurvePointService curvePointService;

    @InjectMocks
    private CurveController controller;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        curvePoint = new CurvePoint(1, 10.0, 100.0);
        curvePoint.setId(1);
    }

    @Test
    public void testHome() throws Exception {
        when(curvePointService.findAll()).thenReturn(Arrays.asList(curvePoint));

        mockMvc.perform(get("/curvePoint/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("curvePoint/list"))
               .andExpect(model().attributeExists("curvePoints"));

        verify(curvePointService).findAll();
    }

    @Test
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void testValidate() throws Exception {
        when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/validate")
                       .param("curveId", "1")
                       .param("term", "10.0")
                       .param("value", "100.0"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).save(any(CurvePoint.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(curvePoint));

        mockMvc.perform(get("/curvePoint/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("curvePoint/update"))
               .andExpect(model().attributeExists("curvePoint"));

        verify(curvePointService).findById(1);
    }

    @Test
    public void testShowUpdateFormNotFound() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/curvePoint/update/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).findById(1);
    }

    @Test
    public void testUpdateCurvePoint() throws Exception {
        when(curvePointService.update(any(CurvePoint.class))).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/update/1")
                       .param("id", "1")
                       .param("curveId", "1")
                       .param("term", "20.0")
                       .param("value", "200.0"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).update(any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        when(curvePointService.findById(1)).thenReturn(Optional.of(curvePoint));
        doNothing().when(curvePointService).delete(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).findById(1);
        verify(curvePointService).delete(1);
    }
}