# Blog
## 肖健辰

一个基于SpringBoot+MyBatis+MySQL的个人博客项目

## 项目概述

博客采用MVC架构，持久层使用MyBatis，采用Thymeleaf后端渲染，尽可能前后端代码分离，前端使用SemanticUI封装的组件：http://www.semantic-ui.cn/

前端借助了多个美化插件，具体有：

Markdown编辑：https://pandao.github.io/editor.md/

页面排版：https://github.com/sofish/typo.css

定制动画效果：https://animate.style/

代码高亮：https://github.com/PrismJS/prism   https://prismjs.com/

自动生成目录：https://tscanlin.github.io/tocbot/

根据url自动生成二维码：https://davidshimjs.github.io/qrcodejs/

页面平滑滚动：https://github.com/flesler/jquery.scrollTo


## 功能实现

访客：根据分类，标签，时间线分页查看博客，查看博客详情页，对博客进行留言，对其他访客的留言进行回复，打赏博客，全局搜索博客，快速查看最新推荐的博客，使用微信扫码阅读博客内容。

管理员：删除分类，标签，博客。编辑分类，标签，博客。新增分类，标签，博客。删除评论。博主评论会显式显示。根据复合条件搜索管理的所有博客。后台支持MarkDown编辑功能。

## 使用该项目需要手动设置的内容

### application-dev application-pro

datasourec数据源

logging.file.name日志输出路径

sourceconfig资源路径配置


### MySQL数据表

项目entity包下的所有实体类的都在数据库对应一张表，实体类属性名要跟字段名统一，由于存在多个关联，外键，注意根据注释进行建表。
推荐可以使用一下A.CTable基于注解自动根据实体类创建数据表的工具。

user表存放的管理员用户信息的password使用了MD5加密，所以需要先利用util包下的MD5Util将明码形式的密码加密，然后放到数据表中。user默认username必须是root。


### Maven依赖中有3个依赖只放在了我的本地仓库
这三个依赖是将markdown编辑文本转换成HTML文本必须的

```xml
<dependency>
        <dependency>
            <groupId>org.commonmark</groupId>
            <artifactId>body</artifactId>
            <version>0.17.1</version>
        </dependency>
        <dependency>
            <groupId>org.commonmark</groupId>
            <artifactId>table</artifactId>
            <version>0.17.0</version>
        </dependency>
        <dependency>
            <groupId>org.commonmark</groupId>
            <artifactId>heading</artifactId>
            <version>0.15.2</version>
        </dependency>
</dependency>
```
这三个依赖由于中央Maven仓库无法获取，所以我下了jar包，将这3个Maven放到了我的本地仓库。
所以你们想要使用想去下载jar然后按照我的步骤操作：

jar包名字分别是：
commonmark-0.17.1.jar
commonmark-ext-gfm-tables-0.17.0.jar
commonmark-ext-heading-anchor-0.15.2.jar

### 对数据库进行优化

看xxxMapper.xml文件中的SQL语句进行索引优化等




