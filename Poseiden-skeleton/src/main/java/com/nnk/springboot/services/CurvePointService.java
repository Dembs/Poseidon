package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> findById(Integer id) {
        return curvePointRepository.findById(id);
    }

    @Transactional
    public CurvePoint save(CurvePoint curvePoint) {
        if (curvePoint.getCreationDate() == null) {
            curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        }
        return curvePointRepository.save(curvePoint);
    }

    @Transactional
    public CurvePoint update(CurvePoint curvePoint) {
        CurvePoint existingPoint = curvePointRepository.findById(curvePoint.getId())
                                                       .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + curvePoint.getId()));

        existingPoint.setCurveId(curvePoint.getCurveId());
        existingPoint.setTerm(curvePoint.getTerm());
        existingPoint.setValue(curvePoint.getValue());
        existingPoint.setAsOfDate(curvePoint.getAsOfDate());

        return curvePointRepository.save(existingPoint);
    }

    @Transactional
    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }
}