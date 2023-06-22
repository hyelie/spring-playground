package com.playground.spring.extract_subclass_example.before.judgement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="judgement")
public class Judgement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="judgement_id")
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date judgeDate;
}