package pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;


import static qa.Base.*;
import static qa.Logger.log;

public class ProductDetailsPage extends MenuPage {

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"Sauce Labs Backpack\"")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    private WebElement itemTitle;

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.\"")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    private WebElement itemDescription;

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"$29.99\"")
    @AndroidFindBy (accessibility = "test-Price")
    public WebElement itemPrice;

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"BACK TO PRODUCTS\" AND name == \"test-BACK TO PRODUCTS\"")
    @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
    private WebElement backBtn;

    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Image Container\"]/XCUIElementTypeOther/XCUIElementTypeImage")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Image Container\"]/android.widget.ImageView\n")
    private WebElement itemImage;


    public String getItemTitle() {
        return getText(itemTitle);
    }


    public String getItemDescription() {
        return getText(itemDescription);
    }

    public String getItemPrice() {
        return getText(itemPrice);
    }

/*    public void slbSwipe(String direction, double percent) {
        BasePage.waitForVisibility(itemImage);
        String swipeElementId = ((RemoteWebElement)itemImage).getId();
        Base.driver.get().executeScript("mobile: swipeGesture", ImmutableMap.of(
//                "left", 100, "top", 100, "width", 200, "height", 200,
                "elementId", swipeElementId,
                "direction", direction,
                "percent", percent
        ));
    }*/

    public ProductsPage pressBackBtn() {
        click(backBtn, "Click back-button");
        return new ProductsPage();
    }

    private String getPriceId() {
        return ((RemoteWebElement)itemPrice).getId();
    }

    public void scrollToPrice () {
        log().info("Performing scroll to price");
        switch (getPlatform()) {
            case "Android" ->
                     getDriver().executeScript("mobile: scroll", ImmutableMap.of(
                            "strategy", "accessibility id",
                            "selector", "test-Price"
                    ));

            case "iOS" -> {
                String priceId = getPriceId();
                getDriver().executeScript("mobile: scrollToElement", ImmutableMap.of(
                        "elementId", priceId
                ));
            }
        }
    }

}
