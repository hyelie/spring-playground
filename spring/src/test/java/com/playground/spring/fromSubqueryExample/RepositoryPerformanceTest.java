package com.playground.spring.fromSubqueryExample;

import java.util.ArrayList;
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
public class RepositoryPerformanceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired ConsumptionRepository consumptionRepository;
    @Autowired CoordinateRepository coordinateRepository;

    private PageDto pageDto;
    private Logger logger = LoggerFactory.getLogger(RepositoryPerformanceTest.class);
    private StopWatch stopWatch;
    private List<Consumption> consumptions = new ArrayList<>();
    private List<Coordinate> coordinates = new ArrayList<>();

    @BeforeAll
    public void setup(){        
        pageDto = new PageDto(10, 1);
        stopWatch = new StopWatch();
        logger.info("Repository Performance Benchmark Start!");
    }

    @Test
    @Transactional
    public void runSingleQueryBenchmark(){
        stopWatch.start();
        List<ConsumptionCoordinateDTO> result = consumptionRepository.findComsuptionsWithCoordinatesWithPaging(pageDto);
        stopWatch.stop();
        logger.info("Repository - runSingleQueryBenchmark - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    @Test
    @Transactional
    public void runTwoQeuryBenchmark(){
        stopWatch.start();
        List<Consumption> consumptions = consumptionRepository.findConsumptionsWithPaging(pageDto);
        List<Coordinate> coordinates = coordinateRepository.findCoordinatesByConsumptions(consumptions);
        stopWatch.stop();
        logger.info("Repository - runTwoQeuryBenchmark - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    public void generateTestData(int consumptionSize, int coordinateSize){
        for(int i = 1; i<=consumptionSize; i++){
            Consumption consumption = new Consumption(i, i);
            consumptions.add(consumption);
            for(int j = 1; j<=coordinateSize; j++){
                Coordinate coordinate = new Coordinate((Integer)(i * consumptionSize + j+1), (Integer)j, (double)i, (double)j, consumption);
                coordinates.add(coordinate);
            }
        }
    }

    @Transactional
    public void bulkInsertCoordinates(int batchSize, List<Coordinate> coordinates){
        for(int i = 0; i<coordinates.size(); i+= batchSize){
            List<Coordinate> batch = coordinates.subList(i, Math.min(i + batchSize, coordinates.size()));

            for(Coordinate coordinate : batch){
                if(coordinate.getId() == null) em.persist(coordinate);
                else em.merge(coordinate);
            }

            em.flush();
            em.clear();
        }
    }

    
    @Transactional
    public void bulkInsertConsumpitons(int batchSize, List<Consumption> consumptions){
        for(int i = 0; i<consumptions.size(); i+= batchSize){
            List<Consumption> batch = consumptions.subList(i, Math.min(i + batchSize, consumptions.size()));

            for(Consumption consumption : batch){
                if(consumption.getId() == null) em.persist(consumption);
                else em.merge(consumption);
            }

            em.flush();
            em.clear();
        }
    }
}
