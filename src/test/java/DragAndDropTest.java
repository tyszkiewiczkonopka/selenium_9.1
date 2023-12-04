import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class DragAndDropTest {
    WebDriver driver = new FirefoxDriver();
    Actions actions = new Actions(driver);


    @Test
    void elements_from_drag_source_should_be_moved_to_drop_target() {
        driver.get("https://demo.aspnetawesome.com/DragAndDropDemo");
        List<WebElement> dragElements = driver.findElements(By.cssSelector(".dropZone .ditem"));
        WebElement dropTarget = driver.findElement(By.cssSelector("main#maincont > div:nth-of-type(2) > div:nth-of-type(2)"));

        int xOffset = 10;
        int yOffset = 0;

        for (WebElement dragElement : dragElements) {
            actions.clickAndHold(dragElement)
                    .moveByOffset(xOffset, yOffset)
                    .moveToElement(dropTarget)
                    .release()
                    .build()
                    .perform();
        }

        List<WebElement> itemsInSecondDropZone = dropTarget.findElements(By.cssSelector(".ditem"));
        assertEquals(3, itemsInSecondDropZone.size(),"Number of ditem divs in the second dropZone should be 3");
        takeScreenshot(driver, "dragAndDropScreenshot");
    }

    private void takeScreenshot(WebDriver driver, String fileName) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destDirectory = new File("target/screenshots");
            if (!destDirectory.exists()) {
                destDirectory.mkdirs();
            }
            String destFilePath = destDirectory + "/" + fileName + "_" + System.currentTimeMillis() + ".png";
            File destFile = new File(destFilePath);
            Files.copy(srcFile.toPath(), destFile.toPath());
            log.info("Screenshot saved at: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            log.error("Error taking screenshot: " + e.getMessage());
        }
    }
}
