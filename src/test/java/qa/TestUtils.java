package qa;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.Reporter;
import reports.ExtentReport;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static qa.Base.*;
import static qa.Logger.log;

public class TestUtils {
    public static final long WAIT = 10;

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static byte[] makeScreenshot(ITestResult result) {
        File sourceFile = driver.get().getScreenshotAs(OutputType.FILE);
        byte[] encoded;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String imagePath = "Screenshot" + File.separator + params.get("platformName") + File.separator +
                TestUtils.getCurrentTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() +
                File.separator + result.getName() + ".png";

        File destFile = new File("test-output" + File.separator + imagePath);

        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return encoded;
    }

    public static void startScreencast() {
        ((CanRecordScreen)driver.get()).startRecordingScreen();
    }

    public static void stopScreencast() {
        ((CanRecordScreen)driver.get()).stopRecordingScreen();
    }

    public static void saveScreencast(ITestResult result) {
        String media = ((CanRecordScreen)driver.get()).stopRecordingScreen();

        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String videoPath = "test-output" + File.separator + "Screencast" + File.separator + params.get("platformName") +
                File.separator + TestUtils.getCurrentTime() + File.separator +
                result.getTestClass().getRealClass().getSimpleName() +
                File.separator + result.getName() + ".mp4";

        try (FileOutputStream stream = FileUtils.openOutputStream(new File(videoPath))) {
            stream.write(Base64.decodeBase64(media));
        }
        catch (Exception e) {
            throw new RuntimeException();
        }


    }

    public static void closeApp() {
        log().info("Closing application");
        ExtentReport.getTest().log(Status.INFO, "Closing application");

        switch (getPlatform()) {
            case "Android" -> getDriver().executeScript("mobile: terminateApp", ImmutableMap.of(
                    "appId", driver.get().getCapabilities().getCapability("appPackage")
            ));
            case "iOS" -> getDriver().executeScript("mobile: terminateApp", ImmutableMap.of(
                    "bundleId", getProps().getProperty("bundleId")));
        }
    }

    public static void launchApp() {
        log().info("Launching application");
        ExtentReport.getTest().log(Status.INFO, "Launching application");

        switch (Base.platform.get()) {
            case "Android" -> driver.get().executeScript("mobile: activateApp", ImmutableMap.of(
                    "appId", driver.get().getCapabilities().getCapability("appPackage")
            ));
            case "iOS" -> driver.get().executeScript("mobile: launchApp", ImmutableMap.of(
                    "bundleId", Base.props.get().getProperty("bundleId")));
        }
    }

}
