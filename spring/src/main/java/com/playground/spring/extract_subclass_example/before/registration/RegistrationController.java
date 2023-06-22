package com.playground.spring.extract_subclass_example.before.registration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {
    @Autowired RegistrationService registrationService;

    @GetMapping("/registration")
    @ResponseBody
    public ResponseEntity<Object> index() {
        List<Registration> registrations = registrationService.findAllRegistrations();
        return new ResponseEntity<Object>(registrations, HttpStatus.OK);
    }
}
