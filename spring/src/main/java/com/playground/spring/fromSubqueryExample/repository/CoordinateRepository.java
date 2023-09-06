package com.playground.spring.fromSubqueryExample.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.playground.spring.fromSubqueryExample.entity.Consumption;
import com.playground.spring.fromSubqueryExample.entity.Coordinate;

@Repository
public class CoordinateRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Coordinate> findCoordinatesByConsumptions(List<Consumption> consumptions){
        String coordinatesByConsumptionsQuery = "SELECT co FROM Coordinate co WHERE co.consumption IN :consumptions";
        return em.createQuery(coordinatesByConsumptionsQuery, Coordinate.class)
                 .setParameter("consumptions", consumptions)
                 .getResultList();
    }
}
