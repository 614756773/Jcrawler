package huoguo.common;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Date: 2019/7/16
 * @Author: qinzhu
 * selenium java API: https://blog.csdn.net/qq_22003641/article/details/79137327
 */
public abstract class AbstractCrawler implements Closeable {

    private static final String DRIVER_NAME = "webdriver.chrome.driver";

    private static final String DRIVER_PATH = "C:\\Users\\HotPot\\Desktop\\chromedriver.exe";

    // url的缓存,每当打开新的标签页,就把其url和handleName缓存起来,便于下次直接切换窗口
    private Map<String, String> urlCache = new HashMap<>();

    protected WebDriver driver;

    private ChromeOptions options = new ChromeOptions();

    protected Object result;

    protected AbstractCrawler() {
        System.setProperty(DRIVER_NAME, DRIVER_PATH);
        options.addArguments("--headless", "--disable-gpu");
        driver = new ChromeDriver(options);
    }

    public void setOptions(ChromeOptions options) {
        this.options = options;
    }

    public Object getResult() {
        return result;
    }

    /**
     * 打开新页面
     */
    protected void openPage(String url) throws Exception {
        if (urlCache.get(url) != null) {
            // A. 已经有这个标签页了
            driver.switchTo().window(urlCache.get(url));
        } else if ("".equals(driver.getTitle())) {
            // B.浏览器现在还没有任何标签页
            driver.get(url);
            urlCache.put(url, driver.getWindowHandle());
        } else {
            // C.需要打开新的标签页
            openTab(url);
            Object[] handles = driver.getWindowHandles().toArray();
            urlCache.put(url, (String) handles[handles.length - 1]);
        }
    }

    /**
     * 打开新的标签页 `使用js打开`
     */
    private void openTab(String url) {
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

    /**
     * 切换标签页
     *
     * @param index 从0开始
     */
    protected void switchWindow(int index) throws NoSuchWindowException {
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        int current = 0;
        while (iterator.hasNext()) {
            if (current == index) {
                driver.switchTo().window(iterator.next());
                break;
            }
        }
    }

    /**
     * 调用js
     */
    private void trigger(String script, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    /**
     * 调用js
     */
    private Object trigger(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    @Override
    public void close() {
        driver.quit();
    }

    public void startup() {
        driver = new ChromeDriver(options);
    }
}
