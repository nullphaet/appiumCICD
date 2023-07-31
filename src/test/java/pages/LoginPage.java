package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import qa.BasePage;

import static qa.Logger.log;

public class LoginPage extends BasePage {

    @iOSXCUITFindBy(id = "test-Username")
    @AndroidFindBy (accessibility = "test-Username")
    private WebElement usernameField;

    @iOSXCUITFindBy (id = "test-Password")
    @AndroidFindBy (accessibility = "test-Password")
    private WebElement passwordField;

    @iOSXCUITFindBy (id = "test-LOGIN")
    @AndroidFindBy (accessibility = "test-LOGIN")
    private WebElement loginBtn;

    @iOSXCUITFindBy (id = "Username and password do not match any user in this service.")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView\n")
    private WebElement errMsg;

    public void enterUsername(String username) {
        clear(usernameField);
        sendKeys(usernameField, username, "Login is: " + username);
    }

    public void enterPassword(String password) {
        clear(passwordField);
        sendKeys(passwordField, password, "Password is: " + password);
    }

    public ProductsPage pressLoginBtn() {
        click(loginBtn, "Login button clicked");
        return new ProductsPage();
    }

    public ProductsPage login(String username, String password) {
        log().info("Login with username/password: " + username + "/" + password);

        enterUsername(username);
        enterPassword(password);
        return pressLoginBtn();
    }

    public String getErrTxt() {
        log().error("Error text: " + errMsg);
        return getText(errMsg);
    }

}

