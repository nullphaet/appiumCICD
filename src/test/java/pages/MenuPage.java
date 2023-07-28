package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import qa.BasePage;

import static qa.Logger.log;


public class MenuPage extends BasePage {
    SettingsPage settingsPage;

    @iOSXCUITFindBy (iOSClassChain = "**/XCUIElementTypeOther[`name == \"test-Menu\"`]/XCUIElementTypeOther")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/" +
            "android.view.ViewGroup/android.widget.ImageView")
    private WebElement menuBtn;

    public SettingsPage pressMenuBtn() {
        click(menuBtn, "Menu button clicked");
        return new SettingsPage();
    }

    public LoginPage logout() {
        log().info("Logging out");
        settingsPage = pressMenuBtn();
        settingsPage.pressLogoutBtn();
        return new LoginPage();
    }

}
