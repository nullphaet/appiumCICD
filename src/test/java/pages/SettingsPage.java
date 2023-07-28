package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class SettingsPage extends MenuPage {

    @iOSXCUITFindBy(accessibility = "test-LOGOUT")
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGOUT\"]/android.widget.TextView")
    private WebElement logoutBtn;

    public LoginPage pressLogoutBtn() {
        click(logoutBtn, "Clicking logout button");
        return new LoginPage();
    }
}
