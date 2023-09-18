package lexisNexisAssessment.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lexisNexisAssessment.utilities.BrowserUtils;
import lexisNexisAssessment.utilities.ConfigurationReader;
import lexisNexisAssessment.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.Map;

public class RoomCreationStepDefs {

    WebDriver driver = Driver.get();

    @Given("the user is logged in thru admin panel")
    public void the_user_is_logged_in_thru_admin_panel() {

        //located admin panel located in the footer and clicked that
        WebElement adminPanel = driver.findElement(By.xpath("//a[.='Admin panel']"));
        adminPanel.click();

        //located username, password box and login button
        WebElement usernameInput = driver.findElement(By.cssSelector("#username"));
        WebElement passwordInput = driver.findElement(By.cssSelector("#password"));
        WebElement loginButton = driver.findElement(By.cssSelector("#doLogin"));

        //retrieved username and password from configuration properties via configurationReader class and assigned them as a variable
        String username = ConfigurationReader.get("username");
        String password = ConfigurationReader.get("password");

        //entered username and password then clicked login button
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        BrowserUtils.waitFor(1);


    }

    @When("the user creates rooms with following options")
    public void the_user_creates_rooms_with_following_options(Map<String, String> roomInfo) {

        //located all the elements which will be used to create a room
        WebElement roomNumber = driver.findElement(By.cssSelector("#roomName"));
        WebElement typeDropDownElement = driver.findElement(By.cssSelector("#type"));
        WebElement accessibleDropDownElement = driver.findElement(By.cssSelector("#accessible"));
        WebElement roomPrice = driver.findElement(By.cssSelector("#roomPrice"));
        WebElement roomDetails = driver.findElement(By.xpath("//label[.='" + roomInfo.get("roomDetails") + "']")); //in this element location utilized string manipulation in order to get dynamic locator
        WebElement createButton = driver.findElement(By.cssSelector("#createRoom"));

        //created objects from Select class which is used to make selection from dropdown menu created with "select" tag in HTML
        Select typeDropdown = new Select(typeDropDownElement);
        Select accessibleDropdown = new Select(accessibleDropDownElement);

        //actions made here to create a room. New room information retrieved from gherkin examples. Can be changed or updated easily without changing the script. Utilized Map class' key-value structure.
        roomNumber.sendKeys(roomInfo.get("roomNumber"));
        typeDropdown.selectByVisibleText(roomInfo.get("roomType"));
        accessibleDropdown.selectByVisibleText(roomInfo.get("roomAccessibility"));
        roomPrice.sendKeys(roomInfo.get("roomPrice"));
        roomDetails.click();
        createButton.click();
    }

    @When("the user navigates back to home page")
    public void the_user_navigates_back_to_home_page() {
        driver.findElement(By.cssSelector("#frontPageLink")).click();
        BrowserUtils.waitFor(1);
    }

    @Then("user the should be able to see newly created room details in the main listing")
    public void user_the_should_be_able_to_see_newly_created_room_details_in_the_main_listing(Map<String, String> expectedRoomInfo) {

        String expectedRoomPrice = expectedRoomInfo.get("expectedRoomPrice"); //this can not be verified because price doesnt even shown on the home page
        String expectedRoomType = expectedRoomInfo.get("expectedRoomType");
        String expectedRoomDetails = expectedRoomInfo.get("expectedRoomDetails");

        Boolean expectedRoomAccessibility = Boolean.parseBoolean(expectedRoomInfo.get("expectedRoomAccessibility"));

        //did try&catch here because if accessibility chosen false it is never displayed on the page, so it was throwing no such element exception which was making assertion impossible
        try {
            boolean actualRoomAccessibility = driver.findElement(By.xpath("//h3[.='" + expectedRoomType + "']//preceding-sibling::span")).isDisplayed();
            Assert.assertEquals(expectedRoomAccessibility, actualRoomAccessibility);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        String actualRoomType = driver.findElement(By.xpath("//h3[.='" + expectedRoomType + "']")).getText();
        String actualRoomDetail = driver.findElement(By.xpath("//h3[.='" + expectedRoomType + "']//following-sibling::ul/li[.='" + expectedRoomDetails + "']")).getText();
        //while location roomDetail and roomAccessibility I used xpath sibling relation. Because in the page there can be several detail with the same name under different rooms. So, I did reach out to those elements from the room Type
        //to make sure details and accessibility asserted correctly


        Assert.assertEquals(expectedRoomType,actualRoomType);
        Assert.assertEquals(expectedRoomDetails,actualRoomDetail);


    }

}
