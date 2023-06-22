package com.playground.spring.extract_subclass_example.before.registration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.playground.spring.extract_subclass_example.before.judgement.Judgement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registration_id")
    private Integer id;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false, length = 50)
    private String department;
    
    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = true)
    private Integer numHotSix;

    @Column(nullable = true, length = 50)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgement_id")
    private Judgement judgement;
}