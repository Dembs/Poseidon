package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint(1, 10.0, 100.0);
        curvePoint.setId(1);
    }

    @Test
    public void testFindAll() {
        // Given
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));

        // When
        List<CurvePoint> result = curvePointService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(curvePointRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // When
        Optional<CurvePoint> result = curvePointService.findById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(curvePoint.getId(), result.get().getId());
        verify(curvePointRepository).findById(1);
    }

    @Test
    public void testSave() {
        // Given
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When
        CurvePoint result = curvePointService.save(new CurvePoint(1, 10.0, 100.0));

        // Then
        assertNotNull(result);
        assertNotNull(result.getCreationDate());
        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    public void testUpdate() {
        // Given
        CurvePoint updatedPoint = new CurvePoint(1, 20.0, 200.0);
        updatedPoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(updatedPoint);

        // When
        CurvePoint result = curvePointService.update(updatedPoint);

        // Then
        assertNotNull(result);
        assertEquals(20.0, result.getTerm());
        assertEquals(200.0, result.getValue());
        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    public void testUpdateNotFound() {
        // Given
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            curvePointService.update(curvePoint);
        });
        verify(curvePointRepository).findById(1);
        verify(curvePointRepository, never()).save(any(CurvePoint.class));
    }

    @Test
    public void testDelete() {
        // Given
        doNothing().when(curvePointRepository).deleteById(1);

        // When
        curvePointService.delete(1);

        // Then
        verify(curvePointRepository).deleteById(1);
    }
}