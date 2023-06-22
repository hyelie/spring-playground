package com.playground.spring.extract_subclass_example.before.registration;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    @Transactional
    public List<Registration> findAllRegistrations(){
        return registrationRepository.findAll();
    }
}
