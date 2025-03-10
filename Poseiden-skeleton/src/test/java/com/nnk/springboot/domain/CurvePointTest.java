package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class CurvePointTest {

    @Test
    public void testCurvePointSettersAndGetters() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // When
        curvePoint.setId(1);
        curvePoint.setCurveId(2);
        curvePoint.setTerm(30.0);
        curvePoint.setValue(300.0);
        curvePoint.setAsOfDate(now);
        curvePoint.setCreationDate(now);

        // Then
        assertEquals(1, curvePoint.getId());
        assertEquals(2, curvePoint.getCurveId());
        assertEquals(30.0, curvePoint.getTerm());
        assertEquals(300.0, curvePoint.getValue());
        assertEquals(now, curvePoint.getAsOfDate());
        assertEquals(now, curvePoint.getCreationDate());
    }
}