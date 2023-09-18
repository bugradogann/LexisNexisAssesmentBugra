package lexisNexisAssessment.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lexisNexisAssessment.utilities.BrowserUtils;
import lexisNexisAssessment.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.PublicKey;

public class MessageSubmissionStepDefs {

    WebDriver driver = Driver.get();

    // initialized web elements in class level in order to user them in all methods. (this could have been done under pages package by implement POM design pattern)
    public MessageSubmissionStepDefs() {
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "#name")
    public WebElement senderName;
    @FindBy(css = "#email")
    public WebElement senderEmail;
    @FindBy(css = "#phone")
    public WebElement senderPhone;
    @FindBy(css = "#subject")
    public WebElement messageSubject;
    @FindBy(css = "#description")
    public WebElement messageDesc;
    @FindBy(css = "#submitContact")
    public WebElement messageSubmit;

    @Given("the user is on home page")
    public void the_user_is_on_home_page() {
        // this method left empty because framework automatically navigates to home page via Hooks class
        BrowserUtils.waitFor(1);
    }

    @When("the user sends a valid message with his {string}, {string}, {string}, {string}, {string}")
    public void the_user_sends_a_valid_message_with_his(String name, String email, String phoneNumber, String subject, String messageDescription) {


        senderName.sendKeys(name);
        senderEmail.sendKeys(email);
        senderPhone.sendKeys(phoneNumber);
        messageSubject.sendKeys(subject);
        messageDesc.sendKeys(messageDescription);
        messageSubmit.click();
        BrowserUtils.waitFor(1);
    }

    @Then("the user should be able to see message confirmation with his {string} and {string}")
    public void the_user_should_be_able_to_see_message_confirmation_with_his_and(String name, String subject) {
        String expectedAppreciation = "Thanks for getting in touch " + name + "!";
        String expectedSubject = subject;

        String actualAppreciation = driver.findElement(By.xpath("//h2[.='Thanks for getting in touch " + name + "!']")).getText();
        String actualSubject = driver.findElement(By.xpath("//p[.='" + subject + "']")).getText();

        Assert.assertEquals(expectedAppreciation, actualAppreciation);
        Assert.assertEquals(expectedSubject, actualSubject);
    }

    @When("the user navigates to inbox")
    public void the_user_navigates_to_inbox() {
        WebElement inbox = driver.findElement(By.xpath("//i[@class='fa fa-inbox']"));
        inbox.click();
        BrowserUtils.waitFor(1);

    }

    @Then("the user should be able to see message received from the customer with his {string}, {string} and {string}")
    public void the_user_should_be_able_to_see_message_received_from_the_customer_with_his(String expName, String expSubject, String expMsgDesc) {
        driver.findElement(By.xpath("//p[.='"+ expName + "']")).click();
        BrowserUtils.waitFor(1);
        String actMsgDesc = driver.findElement(By.xpath("//p[.='" + expMsgDesc + "']")).getText();
        String actualName = driver.findElement(By.xpath("//p[.='" + expName + "']")).getText();
        String actualSubject = driver.findElement(By.xpath("//p[.='" + expSubject + "']")).getText();

        Assert.assertEquals(expName,actualName);
        Assert.assertEquals(expSubject,actualSubject);
        Assert.assertEquals(expMsgDesc,actMsgDesc);
    }

}
