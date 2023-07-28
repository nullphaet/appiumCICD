package tests;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.LoginPage;
import qa.Base;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

import static qa.TestUtils.closeApp;
import static qa.TestUtils.launchApp;

public class ProductTests extends Base {
    LoginPage loginPage;
    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws Exception {

        InputStream dataIs = null;
        try {
            String dataFileName = "data/loginUsers.json";
            dataIs = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(Objects.requireNonNull(dataIs));
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
//        CommonAppMethods.launchApp();
    }

    @AfterClass
    public void afterClass() {
//        CommonAppMethods.closeApp();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        launchApp();
        loginPage = new LoginPage();

//        System.out.println("-----> Starting test: " + method.getName() + "\n");
    }

    @AfterMethod
    public void afterMethod() {
        closeApp();
    }

    @Test
    public void validateProductItem() {
        SoftAssert sa = new SoftAssert();
        productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
                loginUsers.getJSONObject("validUser").getString("password"));

        String actualItemTitle = productsPage.getFirstItemTitle();
        String expectedItemTitle = strings.get().get("slb_title");
        sa.assertEquals(actualItemTitle, expectedItemTitle);

        String actualItemPrice = productsPage.getFirstItemPrice();
        String expectedItemPrice = strings.get().get("slb_price") + "bla";
        sa.assertEquals(actualItemPrice, expectedItemPrice);

//        loginPage = productsPage.logout();
        sa.assertAll();
    }

    @Test
    public void validateProductItemDetails() {
        SoftAssert sa = new SoftAssert();
        productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
                loginUsers.getJSONObject("validUser").getString("password"));

        productDetailsPage = productsPage.pressFirstItemTitle();
        productDetailsPage.scrollToPrice(); /*(productDetailsPage.getChild());*/

        String actualItemTitle = productDetailsPage.getItemTitle();
        String expectedItemTitle = strings.get().get("slb_title");
        sa.assertEquals(actualItemTitle, expectedItemTitle);


        String actualItemDesc = productDetailsPage.getItemDescription();
        String expectedItemDesc = strings.get().get("slb_description");
        sa.assertEquals(actualItemDesc, expectedItemDesc);

        String actualItemPrice = productDetailsPage.getItemPrice();
        String expectedItemPrice = strings.get().get("slb_price");
        System.out.println(expectedItemPrice);
        sa.assertEquals(actualItemPrice, expectedItemPrice);

        productsPage = productDetailsPage.pressBackBtn();
//        loginPage = productsPage.logout();

        sa.assertAll();
    }
}
