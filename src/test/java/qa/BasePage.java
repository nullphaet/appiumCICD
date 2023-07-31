package qa;

import com.aventstack.extentreports.Status;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reports.ExtentReport;
import java.time.Duration;

import static qa.Base.*;
import static qa.Logger.log;

public class BasePage {

    public BasePage() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    public static void waitForVisibility(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public static void click(WebElement element, String logMsg) {
        waitForVisibility(element);
        element.click();

        log().info(logMsg);
        ExtentReport.getTest().log(Status.INFO, logMsg);
    }
    public static void sendKeys(WebElement element, String keys) {
        waitForVisibility(element);
        element.sendKeys(keys);
    }

    public static void sendKeys(WebElement element, String keys, String logMsg) {
        waitForVisibility(element);
        element.sendKeys(keys);

        log().info(logMsg);
        ExtentReport.getTest().log(Status.INFO, logMsg);
    }
    public static String getAttribute (WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

/*    public static void scrollToElement(WebElement scrollable, WebElement child, String logMsg) {
        String scrollableClass = scrollable.getAttribute("class");
        String childId = child.getAttribute("accessibility id");

        log().info(logMsg);
        ExtentReport.getTest().log(Status.INFO, logMsg);

        *//*driver.findElement(AppiumBy.androidUIAutomator(
               "new UiScrollable(new UiSelector().classNameMatches(" + scrollableClass + "))." +
                       "scrollIntoView(new UiSelector().description(" + childId + "))"));*//*

        driver.get().findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))." +
                        "scrollIntoView(new UiSelector().description(" + childId + "))"));
    }*/
    public String getText (WebElement element) {
        waitForVisibility(element);

        return switch (getPlatform()) {
            case "iOS" -> element.getAttribute("label");
            case "Android" -> element.getAttribute("text");
            default -> null;
        };
    }

    public static void clear(WebElement element) {
        waitForVisibility(element);
        element.clear();
    }

}
