package com.qmoney.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


/**
 * This is the runner class to trigger test cases
 * change tags as per testcase run is required.
 * 
 * @author nitin
 *
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features" }, glue = { "com.qmoney.impl.api" },
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" } )
public class RuncukesTest {	

}
