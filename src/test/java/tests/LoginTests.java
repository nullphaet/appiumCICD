package tests;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.ProductsPage;
import pages.LoginPage;
import qa.Base;
import pages.MenuPage;

import java.io.InputStream;
import java.lang.reflect.Method;
import static org.testng.Assert.*;
import static qa.Logger.log;
import static qa.TestUtils.closeApp;

public class LoginTests extends Base {
    LoginPage loginPage;
    ProductsPage productsPage;
    JSONObject loginUsers;


    @BeforeClass
    public void beforeClass() throws Exception {
        InputStream dataIs = null;
        try {

            String dataFileName = "data/loginUsers.json";
            dataIs = getClass().getClassLoader().getResourceAsStream(dataFileName);
            assert dataIs != null;
            JSONTokener tokener = new JSONTokener(dataIs);
            loginUsers = new JSONObject(tokener);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(dataIs != null){
                dataIs.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        loginPage = new LoginPage();
    }

    @AfterClass
    public void afterClass() {
        closeApp();
    }

    @Test
    public void invalidUsername() {
        String username = loginUsers.getJSONObject("invalidUser").getString("username");
        String password = loginUsers.getJSONObject("invalidUser").getString("password");
//        loginPage.enterUsername(loginUsers.getJSONObject("invalidUser").getString("username"));
//        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
//        loginPage.pressLoginBtn();
        loginPage.login(username, password);

        String actualErrMsg = loginPage.getErrTxt();
        String expectedErrMsg = strings.get().get("err_invalid_username_or_password");

        assertEquals(actualErrMsg, expectedErrMsg);
    }

    @Test
    public void invalidPassword() {

        loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.pressLoginBtn();

        String actualErrMsg = loginPage.getErrTxt();
        String expectedErrMsg = strings.get().get("err_invalid_username_or_password");
//                + "bla";

        assertEquals(actualErrMsg, expectedErrMsg);
    }

    @Test
    public void successfulLogin() throws InterruptedException {

        loginPage.enterUsername(loginUsers.getJSONObject("validUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
        productsPage = loginPage.pressLoginBtn();

        String actualTitle = productsPage.getPageTitle();
        String expectedTitle = strings.get().get("products_page_title");

        assertEquals(actualTitle, expectedTitle);
        loginPage = new MenuPage().logout();
    }
}
