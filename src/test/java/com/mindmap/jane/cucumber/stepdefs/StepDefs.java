package com.mindmap.jane.cucumber.stepdefs;

import com.mindmap.jane.ParserApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ParserApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
