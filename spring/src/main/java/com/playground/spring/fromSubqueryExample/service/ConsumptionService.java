package com.playground.spring.fromSubqueryExample.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playground.spring.fromSubqueryExample.dto.ConsumptionCoordinateDTO;
import com.playground.spring.fromSubqueryExample.dto.PageDto;
import com.playground.spring.fromSubqueryExample.entity.Consumption;
import com.playground.spring.fromSubqueryExample.entity.Coordinate;
import com.playground.spring.fromSubqueryExample.repository.ConsumptionRepository;
import com.playground.spring.fromSubqueryExample.repository.CoordinateRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ConsumptionService {
    @Autowired ConsumptionRepository consumptionRepository;
    @Autowired CoordinateRepository coordinateRepository;

    @Transactional
    public LinkedHashMap<Consumption, List<Coordinate>> findConsumptionsWithTwoQuery(PageDto pageDto){
        List<Consumption> consumptions = consumptionRepository.findConsumptionsWithPaging(pageDto);
        List<Coordinate> coordinates = coordinateRepository.findCoordinatesByConsumptions(consumptions);

        LinkedHashMap<Consumption, List<Coordinate>> consumptionMap = new LinkedHashMap<Consumption, List<Coordinate>>();
        for(Coordinate coordinate : coordinates){
            Consumption consumption = coordinate.getConsumption();

            insertCoordinateIntoConsumptionMap(consumptionMap, consumption, coordinate);
        }
        return consumptionMap;
    }

    @Transactional
    public LinkedHashMap<Consumption, List<Coordinate>> findConsumptionWithNativeQuery(PageDto pageDto){
        List<ConsumptionCoordinateDTO> result = consumptionRepository.findComsuptionsWithCoordinatesWithPaging(pageDto);

        LinkedHashMap<Consumption, List<Coordinate>> consumptionMap = new LinkedHashMap<Consumption, List<Coordinate>>();
        for(ConsumptionCoordinateDTO consumptionCoordinateDTO : result){
            Consumption consumption = new Consumption(consumptionCoordinateDTO.getConsumptionId(), consumptionCoordinateDTO.getCost());
            Coordinate coordinate = new Coordinate(consumptionCoordinateDTO.getCoordinateId(), consumptionCoordinateDTO.getSequence(), consumptionCoordinateDTO.getLongitude()
            , consumptionCoordinateDTO.getLatitude(), consumption);

            insertCoordinateIntoConsumptionMap(consumptionMap, consumption, coordinate);
        }
        return consumptionMap;
    }

    private void insertCoordinateIntoConsumptionMap(LinkedHashMap<Consumption, List<Coordinate>> consumptionMap, Consumption consumption, Coordinate coordinate){
        if(!consumptionMap.containsKey(consumption)){
            consumptionMap.put(consumption, new ArrayList<Coordinate>());
        }

        consumptionMap.get(consumption).add(coordinate);
    }
}
