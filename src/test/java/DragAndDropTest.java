import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DragAndDrop {
    WebDriver driver = new ChromeDriver();
    Actions actions = new Actions(driver);
    WebDriverWait wait;

    @Test
    void drag() {
        driver.get("https://demo.aspnetawesome.com/DragAndDropDemo");
        List<WebElement> dragSources = driver.findElements(By.cssSelector(".dropZone .ditem"));
        WebElement dropTarget = driver.findElement(By.cssSelector(".dropZone:nth-child(2)"));
        int xOffset = 0;
        int yOffset = 100;

        for (WebElement dragSource : dragSources) {
            actions.clickAndHold(dragSource)
                    //.moveToElement(dropTarget)
                    .moveByOffset(xOffset,yOffset)
                    .release()
                    .build()
                    .perform();
        }

    }

    private static boolean isElementVisible(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
