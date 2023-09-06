package com.playground.spring.fromSubqueryExample.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.playground.spring.fromSubqueryExample.dto.ConsumptionCoordinateDTO;
import com.playground.spring.fromSubqueryExample.dto.PageDto;
import com.playground.spring.fromSubqueryExample.entity.Consumption;

@Repository
public class ConsumptionRepository {
    @PersistenceContext
    private EntityManager em;
    
    public List<Consumption> findConsumptionsWithPaging(PageDto pageDto){
        String consumptionPagingQuery = "SELECT c FROM Consumption c ORDER BY c.id DESC";
        return em.createQuery(consumptionPagingQuery, Consumption.class)
                 .setFirstResult((pageDto.getPageNum() - 1) * pageDto.getPageSize())
                 .setMaxResults(pageDto.getPageSize())
                 .getResultList();
    }

    public List<ConsumptionCoordinateDTO> findComsuptionsWithCoordinatesWithPaging(PageDto pageDto){
        String consumptionWithCoordinateQuery = "SELECT cresult.consumption_id, cresult.cost, co.coordinate_id, co.sequence, co.longitude, co.latitude " + 
                                                "FROM (SELECT * FROM consumption c ORDER BY c.consumption_id DESC LIMIT :pageSize OFFSET :pageNum) cresult " +
                                                "LEFT JOIN coordinate co ON cresult.consumption_id = co.consumption_id";
        List<Object[]> resultList = em.createNativeQuery(consumptionWithCoordinateQuery)
                                        .setParameter("pageSize", pageDto.getPageSize())
                                        .setParameter("pageNum", pageDto.getPageSize() * (pageDto.getPageNum() - 1))
                                        .getResultList();
        return resultList.stream()
                            .map(row -> new ConsumptionCoordinateDTO((Integer) row[0], (Integer) row[1], (Integer) row[2], (Integer) row[3], (Double) row[4], (Double) row[5]))
                            .collect(Collectors.toList());
    }
}
