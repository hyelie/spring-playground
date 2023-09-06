package com.playground.spring.fromSubqueryExample;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import com.playground.spring.fromSubqueryExample.dto.ConsumptionCoordinateDTO;
import com.playground.spring.fromSubqueryExample.dto.PageDto;
import com.playground.spring.fromSubqueryExample.entity.Consumption;
import com.playground.spring.fromSubqueryExample.entity.Coordinate;
import com.playground.spring.fromSubqueryExample.repository.ConsumptionRepository;
import com.playground.spring.fromSubqueryExample.repository.CoordinateRepository;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PerformanceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired ConsumptionRepository consumptionRepository;
    @Autowired CoordinateRepository coordinateRepository;

    private PageDto pageDto;
    private Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

    @BeforeAll
    public void setup(){        
        pageDto = new PageDto(10, 1);
        logger.info("Repository Performance Benchmark Start!");
    }

    @Test
    @Transactional
    public void benchmarkLargeQueryRepositoryTime(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<ConsumptionCoordinateDTO> result = consumptionRepository.findComsuptionsWithCoordinatesWithPaging(pageDto);
        stopWatch.stop();
        logger.info("LargeQueryRepositoryTime - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    @Test
    @Transactional
    public void benchmarkSmallQueriesRepositoryTime(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Consumption> consumptions = consumptionRepository.findConsumptionsWithPaging(pageDto);
        List<Coordinate> coordinates = coordinateRepository.findCoordinatesByConsumptions(consumptions);
        stopWatch.stop();
        logger.info("SmallQueriesRepositoryTime - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
