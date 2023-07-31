package tests;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import qa.Base;

import static qa.TestUtils.closeApp;
import static qa.TestUtils.openAppWith;

public class ProductTests extends Base {
//    LoginPage loginPage;
    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;

    @BeforeMethod
    public void beforeMethod() {
        openAppWith("swaglabs://swag-overview/0,1");
    }


    @AfterMethod
    public void afterMethod() {
        closeApp();
    }

    @Test
    public void validateProductItem() {
        SoftAssert sa = new SoftAssert();

        productsPage = new ProductsPage();

        String actualItemTitle = productsPage.getFirstItemTitle();
        String expectedItemTitle = strings.get().get("slb_title");
        sa.assertEquals(actualItemTitle, expectedItemTitle);

        String actualItemPrice = productsPage.getFirstItemPrice();
        String expectedItemPrice = strings.get().get("slb_price");
        sa.assertEquals(actualItemPrice, expectedItemPrice);

        sa.assertAll();
    }

    @Test
    public void validateProductItemDetails() {
        SoftAssert sa = new SoftAssert();

        productsPage = new ProductsPage();
        productDetailsPage = productsPage.pressFirstItemTitle();
        productDetailsPage.scrollToPrice();

        String actualItemTitle = productDetailsPage.getItemTitle();
        String expectedItemTitle = strings.get().get("slb_title");
        sa.assertEquals(actualItemTitle, expectedItemTitle);

        String actualItemDesc = productDetailsPage.getItemDescription();
        String expectedItemDesc = strings.get().get("slb_description");
        sa.assertEquals(actualItemDesc, expectedItemDesc);

        String actualItemPrice = productDetailsPage.getItemPrice();
        String expectedItemPrice = strings.get().get("slb_price");

        sa.assertEquals(actualItemPrice, expectedItemPrice);

        sa.assertAll();
    }
}
