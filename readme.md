# 通用的通知中心设计

## 背景简介

在平常的项目中经常需要用到消息通知功能，比如邮件通知、短信通知、公众号通知等等，但是如果在每个项目中独立实现则非常麻烦，而且重复造轮子，极大程度地降低了开发效率，为了简化开发流程，降低重复工作量，故此将通知中心这一通用模型独立出来，作为一个单独的系统搭建。

## 主要模块简介

#### 环境

+ 操作系统

    开发环境为Windows 10 专业版 20H1 (内部版本19043.1526)

    服务器部署环境为Ubuntu 20.04.4 LTS

+ Java

    开发环境为oracle JDK 17.0.2 LTS

    服务器部署环境为Open JDK 17.0.1

+ MySQL

    开发环境为MySQL server 8.0.26

    服务器部署环境为MySQL server 8.0.28

+ Nginx

    开发环境直接访问8001端口

    服务器部署环境为Nginx 1.18.0

+ 邮箱

    这里选择的是腾讯企业邮箱，可以根据实际情况选择合适的邮箱，只要是支持smtp服务的邮箱即可。

#### Spring Boot基础框架

Spring Boot是当下最流行的Java web服务框架，是为了简化Spring MVC过度复杂的配置而实现的Spring引导框架，简单讲就是遵循“契约式编程”思想，约定一套规则，通过牺牲项目的自由度来减少配置的复杂度，把这些框架都自动配置集成好，从而达到“开箱即用”的目的。当然，Spring Boot同时也支持自由配置，本质上他还是一个Maven项目，可以通过在`pom.xml`中添加依赖的方式添加自己所需的配置，如Spring Data JPA、Spring Boot starter web、 fastjson等常用功能。

另外，Spring Boot中支持`HandlerInterceptor`拦截器，通过实现自己所需的拦截器可以实现在请求到达之前的预处理操作，在本项目中自定义了`LoginInterceptor`来进行登录鉴权，在收到前端请求时从请求头中取出`Authorization`数据，正常情况下该数据应该为系统签发的JWT(Json Web Token)，其中包含了用户的相关信息，关于JWT的详细信息会在下一小节讲解，鉴权通过后放行，鉴权失败则直接返回认证失败。对于一些无需登录的接口，本项目实现了免登陆注解`LoginExcept`，在拦截器中检查对应的接口是否添加了`LoginExcept`注解，如果添加则跳过身份认证，否则继续执行。



#### 使用JWT进行系统间鉴权

JWT全称是Json Web Token，是为了在网络应用环境间传递声明而执行的一种基于Json的开放式标准，这个token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景。JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源，也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。与传统的session和cookies相比，session经过认证后需要在服务端做一次记录，以便用户下次请求的鉴权，这样服务器端的开销会明显增大，而基于cookies的鉴权也有很大的问题，但是如果cookies被截获就会很容易收到跨站请求伪造的攻击。而JWT则是和http一样是无状态的，他不需要在服务端存储用户的认证信息或者会话信息，极大程度地降低服务器的消耗，另外，JWT中的`playload`部分可以存放一些可以公开的信息，比如将用户的基础信息存储进去，这样就可以在认证时直接获取到用户的信息，可以减少从数据库查询用户信息的次数，以此降低数据库压力。

#### Fluent MyBatis配置数据库

MyBatis是Spring Boot下一个高效的数据库操作工具，但是因为配置太过复杂，所以这里选择MyBatis的语法增强框架Fluent MyBatis实现数据库的增删改查。Fluent MyBatis的配置非常简单，只需要配置相应的生成类，这里给出相应的配置类，

```java
package general.notification_center;

import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityGeneratorTests {

    // 数据源 url
    static final String url = "jdbc:mysql://localhost:3306/notify_center?useUnicode=true&characterEncoding=utf8";
    // 数据库用户名
    static final String username = "root";
    // 数据库密码
    static final String password = "root";

    @Test
    public void generate() {
        // 引用配置类，build方法允许有多个配置类
        FileGenerator.build(Empty.class);
    }

    @Tables(
            // 设置数据库连接信息
            url = url,
            username = username,
            password = password,
            // 设置entity类生成src目录, 相对于 user.dir
            srcDir = "src/main/java",
            // 设置entity类的package值
            basePack = "general.notification_center",
            // 设置dao接口和实现的src目录, 相对于 user.dir
            daoDir = "src/main/java/",
            // 设置哪些表要生成Entity文件
            tables = {@Table(value = {"hello_world", "apps"})}
    )
    static class Empty { //类名随便取, 只是配置定义的一个载体
    }

}
```



## 结果展示

#### 在线测试

#### 部署方法

## API文档

域名：https://document.codeclub.fun/

> 不支持http，仅支持https请求

# 通知中心

## POST 发送文本邮件

```
POST /email/text
```

发送文本邮件

> Body 请求参数示例

```
receiver: 1791781644@qq.com
subject: 测试主题
context: 测试内容
```

### 请求参数

| 名称          | 位置   | 类型   | 必选 | 说明                            |
| ------------- | ------ | ------ | ---- | ------------------------------- |
| Authorization | header | string | true | 身份验证token，由系统管理员签发 |
| receiver      | body   | string | true | 收件人                          |
| subject       | body   | string | true | 邮件主题                        |
| context       | body   | string | true | 邮件正文                        |

> 邮件示例

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功 | Inline   |

### 返回数据结构

## POST 发送html邮件

POST /email/html

发送文本邮件

> Body 请求参数示例

```
receiver: 1791781644@qq.com
subject: HTML邮件测试
context: "<div style=\"margin: 0 auto; padding: 40px; text-align: center;
  height: 100%; overflow: hidden; box-sizing: border-box; max-width: 800px\">\r
  \    <h2 style=\"font-size: 2em\">\r
  \        邮件测试\r
  \    </h2>\r
  \    <div style=\"font-size: 1.2em;\">\r
  \        这是一封html测试邮件，整个项目的代码在\r
  \        <a style=\"color: #177EE6\" href=\"https://github.com/404-error-404/notification_center\">\r
  \            Github\r
  \        </a>\r
  \    </div>\r
  \    <div style=\"width: 100%; margin: 0 auto;\">\r
  \        <img style=\"width: 100%; height: auto\" src=\"https://smartpublic-10032816.file.myqcloud.com/custom/20211202202931/22735/20211202202931/--08ef28251a40e164b98dc57caf85c83c.png\"  alt=\"装饰图\"/>\r
  \    </div>\r
  </div>"
```

### 请求参数

| 名称          | 位置   | 类型   | 必选  | 说明 |
| ------------- | ------ | ------ | ----- | ---- |
| Authorization | header | string | false | none |
| body          | body   | object | false | none |
| » receiver    | body   | string | true  | none |
| » subject     | body   | string | true  | none |
| » context     | body   | string | true  | none |

> 返回示例

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功 | Inline   |

### 返回数据结构