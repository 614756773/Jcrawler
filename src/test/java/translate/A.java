package translate;

import huoguo.translate.Translator;
import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Scanner;
import java.util.Set;

/**
 * @Date: 2019/7/17
 * @Author: qinzhu
 */
public class A {
    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HotPot\\Desktop\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "disable-gpu");
        driver = new ChromeDriver();
        driver.get("https://translate.google.cn/");
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(d -> d.findElement(By.xpath("//*[@id=\"sugg-item-en\"]"))).click();
        wait.until(d -> d.findElement(By.xpath("//*[@id=\"source\"]"))).sendKeys("hello world,my name is tiger");
        Thread.sleep(2000);
        WebElement result = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span"));
        System.out.println(result.getText());

        openTab("https://blog.csdn.net/aPiFen/article/details/79397100");
        switchWindow();
        System.out.println(driver.getTitle());

        System.out.println(driver.getWindowHandles());
        Scanner s = new Scanner(System.in);
        s.nextInt();
        driver.quit();
    }

    private static void switchWindow() throws NoSuchWindowException {
        Set<String> handles = driver.getWindowHandles();
        String current = driver.getWindowHandle();
        handles.remove(current);
        String newTab = handles.iterator().next();
        driver.switchTo().window(newTab);
    }

    private static void openTab(String url) {
        String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
        Object element = trigger(String.format(script, url));
        if (element instanceof WebElement) {
            WebElement anchor = (WebElement) element;
            anchor.click();
            trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
        } else {
            throw new JavaScriptException(element, "Unable to open tab", 1);
        }
    }

    private static void trigger(String script, WebElement element) {
        ((JavascriptExecutor)driver).executeScript(script, element);
    }

    private static Object trigger(String script) {
        return ((JavascriptExecutor)driver).executeScript(script);
    }
}
