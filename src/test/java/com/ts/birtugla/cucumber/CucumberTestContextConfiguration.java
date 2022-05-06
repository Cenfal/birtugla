package com.ts.birtugla.cucumber;

import com.ts.birtugla.BirtuglaApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = BirtuglaApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
