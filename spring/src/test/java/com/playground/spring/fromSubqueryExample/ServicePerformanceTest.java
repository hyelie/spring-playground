package com.playground.spring.fromSubqueryExample;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import com.playground.spring.fromSubqueryExample.dto.PageDto;
import com.playground.spring.fromSubqueryExample.service.ConsumptionService;

@SpringBootTest
@Transactional
public class ServicePerformanceTest {
    @Autowired ConsumptionService consumptionService;
    private static PageDto pageDto;

    private static Logger logger = LoggerFactory.getLogger(RepositoryPerformanceTest.class);
    private static StopWatch stopWatch;

    @BeforeAll
    public static void setup(){        
        pageDto = new PageDto(10, 1);
        stopWatch = new StopWatch();
        logger.info("Service Performance Benchmark Start!");
    }

    @Test
    public void runSingleQueryBenchmark(){
        stopWatch.start();
        consumptionService.findConsumptionWithNativeQuery(pageDto);
        stopWatch.stop();
        logger.info("Service - runSingleQueryBenchmark - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }

    @Test
    public void runTwoQeuryBenchmark(){
        stopWatch.start();
        consumptionService.findConsumptionsWithTwoQuery(pageDto);
        stopWatch.stop();
        logger.info("Service - runSingleQueryBenchmark - Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
