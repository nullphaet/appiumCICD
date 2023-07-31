package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends MenuPage {
    @iOSXCUITFindBy (iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"PRODUCTS\"`]")
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]" +
            "/android.view.ViewGroup/android.widget.TextView")
    private WebElement products;

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"Sauce Labs Backpack\"")
    @AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
    private WebElement firstItemTitle;

    @iOSXCUITFindBy (iOSNsPredicate = "label == \"$29.99\"")
    @AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    private WebElement firstItemPrice;


    public String getPageTitle() {
        return getText(products);
    }

    public String getFirstItemTitle() {
        return getText(firstItemTitle);
    }

    public String getFirstItemPrice() {
        return getText(firstItemPrice);
    }

    public ProductDetailsPage pressFirstItemTitle(){
        click(firstItemTitle, "Clicking first item title");
        return new ProductDetailsPage();
    }
}

