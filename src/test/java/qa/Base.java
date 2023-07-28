package qa;

import Utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import static qa.Logger.log;
import static qa.Logger.logFileSetup;

public class Base {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<String> platform = new ThreadLocal<>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<>();
    protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<>();

    private static AppiumDriverLocalService server;

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static String getPlatform() {
        return platform.get();
    }

    public static Properties getProps() {
        return props.get();
    }
    private void setDriver(AppiumDriver driver1) {
        driver.set(driver1);
    }
    private void setPlatform(String platform1) {
        platform.set(platform1);
    }
    private void setProps(Properties props1) {
        props.set(props1);
    }
    private void setStrings(HashMap<String, String> strings1) {
        strings.set(strings1);
    }

    @BeforeSuite
    public void beforeSuite(){
        server = AppiumDriverLocalService.buildDefaultService();
        if (!server.isRunning()) {
            server.start();
            server.clearOutPutStreams();
        }
//        log().info("Appium server started.");
    }

    @AfterSuite
    public void afterSuite(){
        server.stop();
//        log().info("Appium server stopped");
    }


    @Parameters({"platformName", "systemPort", "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeTest
    public void setUp(String platformName, @Optional("androidOnly") String systemPort,
                      @Optional("androidOnly") String chromeDriverPort, @Optional("iOSOnly") String wdaLocalPort,
                      @Optional("iOSOnly") String webkitDebugProxyPort) throws Exception {
        InputStream inputStream = null;
        InputStream xmlStrings = null;
        Properties props = new Properties();

        logFileSetup(platformName);

        try {
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            String xmlFilePath = "strings/strings.xml";
            xmlStrings = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
            setStrings(new Utils().parseStringXml(xmlStrings));
            setPlatform(platformName);

            URL url = new URL(getProps().getProperty("appiumUrl"));
            log().info("Building session with URL: " + url);

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("newCommandTimeout", 60);
            caps.setCapability("autoAcceptAlerts", "true");

            switch (platformName) {
                case "iOS" -> {
//                    String iosAppUrl = Objects.requireNonNull(getClass().getClassLoader().
//                            getResource(getProps().getProperty("iOSAppLocation"))).getPath();
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProps().getProperty("iOSAutomationName"));
//                    caps.setCapability(MobileCapabilityType.APP, iosAppUrl);
                    caps.setCapability("bundleId", getProps().getProperty("bundleId"));
                    caps.setCapability("webviewConnectTimeout", 30000);
                    caps.setCapability("wdaLocalPort", wdaLocalPort);
                    caps.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);
                    caps.setCapability("includeSafariInWebviews", true);
                    caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone_SE");
                    caps.setCapability(MobileCapabilityType.UDID, "6A2A7817-6A53-4CFE-8978-2CE882F74DED");

                    setDriver(new IOSDriver(url, caps));
                    log().info("iOS driver set up successfully");
                }
                case "Android" -> {
//                    String androidAppUrl = Objects.requireNonNull(getClass().getClassLoader().
//                            getResource(props.getProperty("androidAppLocation"))).getPath();
//
//                    caps.setCapability(MobileCapabilityType.APP, androidAppUrl);
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProps().getProperty("androidAutomationName"));
//                    caps.setCapability("avd", avd);
                    caps.setCapability("systemPort", systemPort);
                    caps.setCapability("chromeDriverPort", chromeDriverPort);
                    caps.setCapability("appPackage", getProps().getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", getProps().getProperty("androidAppActivity"));
                    caps.setCapability("avd", "Nexus_6");

                    setDriver(new AndroidDriver(url, caps));
                    log().info("Android driver set up successfully");
                }
                default -> throw new Exception("Incorrect platform name: " + platformName);
            }

        } catch(Exception e) {
            log().error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(xmlStrings != null){
                xmlStrings.close();
            }
        }
    }

    @AfterTest
    public void tearDown() {
        getDriver().quit();
        log().info("Driver session terminated.");
    }
}
