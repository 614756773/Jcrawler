package huoguo.web;

import huoguo.ApplicationContextHelper;
import huoguo.common.AbstractCrawler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import huoguo.translate.Translator;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Date: 2019/7/16
 * @Author: qinzhu
 */
@RestController
public class CommonController {

    @Autowired
    private Translator translator;

    /**
     *
     * @param text 带翻译的文字
     * @param enToZh true表示英译汉,false表示汉译英
     */
    @PostMapping("/translate")
    @ApiOperation("翻译机器人")
    public String translate(String text, Boolean enToZh) throws Exception {
        return translator.translate(text, enToZh)
                .getResult().toString();
    }

    @PostMapping("/translate/java")
    @ApiOperation("翻译java注释")
    public String translateJavaComment(String text) throws Exception {
        return translator.translateJavaComment(text)
                .getResult().toString();
    }

    @GetMapping("/shutdown")
    @ApiOperation("关闭爬虫机器人")
    public void closeWebDriver(String crawlerClassName) throws ClassNotFoundException, IOException {
        Object crawler = ApplicationContextHelper.getBean(Class.forName(crawlerClassName));
        Closeable closeable = (Closeable) crawler;
        closeable.close();
    }

    @GetMapping("/startup")
    @ApiOperation("启动爬虫机器人")
    public void openWebDriver(String crawlerClassName) throws ClassNotFoundException, IOException {
        Object crawler = ApplicationContextHelper.getBean(Class.forName(crawlerClassName));
        AbstractCrawler crawler1 = (AbstractCrawler) crawler;
        crawler1.startup();
    }
}
