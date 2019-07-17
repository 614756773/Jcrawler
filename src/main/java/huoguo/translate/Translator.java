package huoguo.translate;

import huoguo.common.AbstractCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

/**
 * @Date: 2019/7/16
 * @Author: qinzhu
 */
@Component
public class Translator extends AbstractCrawler {

    private final String URL = "https://translate.google.cn/#view=home&op=translate";

    /**
     * 翻译java注释
     */
    public AbstractCrawler translateJavaComment(String text) throws Exception {
        String replace = text.replaceAll("\\*|\\/|<p>|</p>|<b>|</b>|<code>|</code>|&lt;", "");
        return translate(replace, true);
    }

    public AbstractCrawler translate(String text, Boolean isEntoCn) throws Exception {
        String url = isEntoCn ? URL + "&sl=en&tl=zh-CN" : URL + "&sl=zh-CN&tl=en";
        openPage(url);

        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(d -> d.findElement(By.xpath("//*[@id=\"source\"]")));
        element.clear();
        element.sendKeys(text);
        Thread.sleep(350);
        WebElement result = wait.until(d -> d.findElement(By.xpath("/html/body/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span")));

        this.result = result.getText();
        return this;
    }
}
