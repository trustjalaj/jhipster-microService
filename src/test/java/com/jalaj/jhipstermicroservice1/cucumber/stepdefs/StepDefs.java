package com.jalaj.jhipstermicroservice1.cucumber.stepdefs;

import com.jalaj.jhipstermicroservice1.JhipsterMicroService1App;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JhipsterMicroService1App.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
