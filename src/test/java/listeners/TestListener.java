package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import qa.TestUtils;
import reports.ExtentReport;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static qa.Base.getPlatform;
import static qa.Logger.log;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        TestUtils.startScreencast();

        ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                .assignCategory(getPlatform())
                        .assignAuthor("phaet");

        log().info("Test " + result.getName() + " started at " +
                TestUtils.getCurrentTime());

        ExtentReport.getTest().log(Status.INFO, "Test " + result.getName() + " started at " +
                TestUtils.getCurrentTime());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        log().info("Test " + result.getName() + " passed at " +
                TestUtils.getCurrentTime());

        ExtentReport.getTest().log(Status.PASS, "Test " + result.getName() + " passed at " +
                TestUtils.getCurrentTime());

        TestUtils.stopScreencast();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        byte[] screenshot = TestUtils.makeScreenshot(result);
        log().info("Test " + result.getName() + " failed at " +
                TestUtils.getCurrentTime());
        if (result.getThrowable() != null) {
            log().error("Error message: " + result.getThrowable().getMessage());
            ExtentReport.getTest().fail(result.getThrowable());
        }

        ExtentReport.getTest().fail("Test " + result.getName() + " failed at " + TestUtils.getCurrentTime(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(
                        new String(screenshot, StandardCharsets.US_ASCII)).build());

        TestUtils.saveScreencast(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        ExtentReport.getReporter().flush();
    }

}
