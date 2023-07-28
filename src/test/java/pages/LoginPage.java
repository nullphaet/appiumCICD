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
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error " +
            "message\"]/android.widget.TextView\n")
    private WebElement errMsg;

    public void enterUsername(String username) {
        clear(usernameField);
//        log().info("Username is: " + username);
        sendKeys(usernameField, username, "Login is: " + username);
//        return this;
    }
    public void enterPassword(String password) {
        clear(passwordField);
//        log().info("Password is: " + password);
        sendKeys(passwordField, password, "Password is: " + password);
//        return this;
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
        String err = getText(errMsg);
        log().error("Error text is: " + err);
        return err;
    }

}

