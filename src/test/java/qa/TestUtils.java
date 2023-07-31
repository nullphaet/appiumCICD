package qa;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
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
        File sourceFile = getDriver().getScreenshotAs(OutputType.FILE);
        byte[] encoded;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
        } catch (IOException e) {
            log().error(e.getMessage());
            throw new RuntimeException();
        }

        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String imagePath = "Test Screenshots" + File.separator + params.get("platformName") +
                File.separator + params.get("deviceName") + File.separator + TestUtils.getCurrentTime() +
                File.separator + result.getTestClass().getRealClass().getSimpleName() +
                File.separator + result.getName() + ".png";

        File destFile = new File(imagePath);

        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            log().error(e.getMessage());
        }
        return encoded;
    }

    public static void startScreencast() {
        ((CanRecordScreen)driver.get()).startRecordingScreen();
        log().info("Recording started");
    }

    public static void stopScreencast() {
        ((CanRecordScreen)driver.get()).stopRecordingScreen();
        log().info("Recording stopped");
    }

    public static void saveScreencast(ITestResult result) {
        String media = ((CanRecordScreen)driver.get()).stopRecordingScreen();

        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String videoPath = "Test Videos" + File.separator + params.get("platformName") +
                File.separator + params.get("deviceName") + File.separator + TestUtils.getCurrentTime() +
                File.separator + result.getTestClass().getRealClass().getSimpleName() +
                File.separator + result.getName() + ".mp4";

        try (FileOutputStream stream = FileUtils.openOutputStream(new File(videoPath))) {
            stream.write(Base64.decodeBase64(media));
            log().info("Screencast saved");
        }
        catch (Exception e) {
            log().error(e.getMessage());
        }
    }

    public static void closeApp() {
        log().info("Closing application");

        switch (getPlatform()) {
            case "Android" -> getDriver().executeScript("mobile: terminateApp", ImmutableMap.of(
                    "appId", getDriver().getCapabilities().getCapability("appPackage")
            ));
            case "iOS" -> getDriver().executeScript("mobile: terminateApp", ImmutableMap.of(
                    "bundleId", getProps().getProperty("bundleId")));
        }
    }

    public static void launchApp() {
        log().info("Launching application");

        switch (getPlatform()) {
            case "Android" -> getDriver().executeScript("mobile: activateApp", ImmutableMap.of(
                    "appId", getDriver().getCapabilities().getCapability("appPackage")
            ));
            case "iOS" -> getDriver().executeScript("mobile: launchApp", ImmutableMap.of(
                    "bundleId", getProps().getProperty("bundleId")));
        }
    }

    public static void openAppWith(String deepUrl) {
        log().info("Launching app from deeplink: " + deepUrl);

        switch (getPlatform()) {
            case "Android" -> getDriver().executeScript("mobile: deepLink", ImmutableMap.of(
                    "url", deepUrl,
                    "package", getProps().getProperty("androidAppPackage")));

            case "iOS" -> getDriver().executeScript("mobile: deepLink", ImmutableMap.of(
                            "url", deepUrl,
                            "package", getProps().getProperty("bundleId")));
        }
    }

}
