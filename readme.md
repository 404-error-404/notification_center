# 通用的通知中心设计

## 背景简介

在平常的项目中经常需要用到消息通知功能，比如邮件通知、短信通知、公众号通知等等，但是如果在每个项目中独立实现则非常麻烦，而且重复造轮子，极大程度地降低了开发效率，为了简化开发流程，降低重复工作量，故此将通知中心这一通用模型独立出来，作为一个单独的系统搭建。

## 技术简介

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



#### Fluent MyBatis配置数据库

#### 使用JWT进行系统间鉴权



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