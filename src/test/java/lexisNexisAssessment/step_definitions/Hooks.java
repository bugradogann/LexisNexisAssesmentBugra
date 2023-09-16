package lexisNexisAssessment.step_definitions;

import lexisNexisAssessment.utilities.ConfigurationReader;
import lexisNexisAssessment.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


public class Hooks {

    @Before
    public void setUp(){
        Driver.get().manage().window().maximize();
        Driver.get().get(ConfigurationReader.get("url"));
    }

    @After
    public void tearDown(Scenario scenario){
        //takes screenshot if the scenario is failed and adds it into report under target file
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","screenshot");
        }
        Driver.closeDriver();
    }


}