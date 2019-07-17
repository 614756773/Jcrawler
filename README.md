# Jcrawler
### 项目介绍
- 使用selenium和Robot完成一些自动化的操作
- 功能清单
    - [x] 使用selenium + 谷歌翻译做一个翻译机器人<br>`主要是平时在翻译java文档会有一些特殊符号需要去掉才能翻译的更准确,所以有了这个机器人`
    - [ ] 使用selenium做一个页游的小辅助,自动刷一些简单的图<br>`这个游戏叫做'出发吧冒险家',好玩不氪的Roguelike游戏`
    - [ ] 其他功能待定......
### 如何运行
#### 方式一
- 下载源码
- 使用IDE运行Application的main方法
- 访问localhost:6147/Jcrawler/admin
- 使用swagger调用
#### 方式二
- 下载源码
- 进入到项目根目录,执行mvn clean package编译
- 执行java -jar Jcrawler-1.0-SNAPSHOT.jar
    - 务必保持Jcrawler-1.0-SNAPSHOT.jar和chromedriver.exe在同一目录
### 运行前的准备工作
- JDK8+
- maven
- chromedriver.exe<br>
该工程根目录下有一个,如果需要其他版本请访问 [点这儿](https://sites.google.com/a/chromium.org/chromedriver/downloads)
