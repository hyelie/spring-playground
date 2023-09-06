package com.playground.spring.fromSubqueryExample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionCoordinateDTO {
    private Integer consumptionId;
    private Integer cost;
    private Integer coordinateId;
    private Integer sequence;
    private double longitude;
    private double latitude;
}
