# README

## 一、项目结构

项目结构清晰地分为两个主要层级，具体如下所示：

- **App层**（位于`bookstore-app`项目中）：
  - 负责接收前端请求。
  - 进行权限验证和参数初步校验。
  - 调用API层进行核心业务处理。

- **API层**（位于`bookstore-api`项目中）：
  - 直接链接数据库。
  - 负责核心业务的处理。
  - **注意**：API层被拆分为多个jar包，以便在业务初期能够灵活调整。初期可以将多个模块整合到一个微服务中部署，节省服务器资源和运维工作量。随着业务扩展，不同模块可直接各自进行微服务部署，提高服务可用性。拆分服务时，只需将`remote`目录下的实现类中的`@Service`注解更改为Dubbo注解，并在配置文件中增加Dubbo和Zookeeper的配置。

项目结构图示：


bookstore
├── bookstore-base
├── bookstore-app
│ └── bookstore-app-c // build as a service
├── bookstore-api
│ ├── bookstore-api-remote
│ ├── bookstore-api-user
│ ├── bookstore-api-auth
│ ├── bookstore-api-book
│ └── bookstore-api-order
│ ...


## 二、公共基础模块

`bookstore-base`、`model`、`bookstore-api-remote`是项目中的公共基础模块，每个项目都需要引用它们。其中，`bookstore-api-remote`封装了APP层和API层之间的远程接口调用，使得APP层能够快速调用API层的接口。此外，它还包含业务异常类、DTO、枚举、国际化文件等，是微服务之间接口调用的桥梁。

## 三、代码生成工具

`tools`目录中包含快速生成代码的工具包，可以通过数据库表直接快速生成API层的基础代码。

## 四、Jar包版本控制

Jar包的版本控制应在根目录的`pom.xml`文件中通过`dependencyManagement`来管理，以防止版本冲突。当前为了快速实现，并未严格按照规范执行。

## 五、Redis配置

当前项目的登录模块依赖Redis。Redis地址可以通过在本地服务器的hosts文件中配置`redis.linkun.com`来指定，例如`127.0.0.1 redis.linkun.com`。默认Redis为无密码登录，如果需要添加密码，则需要在`bookstore -> bookstore-app -> bookstore-app-c -> src/main/resources -> application.yaml`文件中取消`spring.redis.password`的注释并设置密码。

## 六、数据库配置

项目SQL文件位于`bookstore -> sql`目录下。数据库当前连接的是阿里云RDS云数据库，代码中使用的是公网地址访问，无需修改。

## 七、项目版本说明

当前项目技术选型的第一优先级是短期内完成项目研发。因此，很多技术版本并非最新流行版本。在生产环境中，Spring Boot、JDK、MyBatis、Redis等版本的选择需综合考虑当前最新版本、版本间依赖关系、技术部门的当前技术选型以及未来业务扩展情况等因素。

当前版本号：
- JDK 1.8
- MySQL 5.7
- Spring Boot 2.3.1.RELEASE
- Redis 6.2.6

## 八、代码完成情况

当前已完成的功能包括：
- 用户信息管理（user）
- 注册登录（auth）
- 图书管理（book）
- 库存管理（API在book下的inventory中）
- 订单管理（order）

已完成的测试用例包括：
- 用户信息管理（user）
- 注册登录（auth）

其他功能的测试用例尚未完成。测试用例代码位于`bookstore -> bookstore-app -> bookstore-app-c -> src/test`目录下。

## 九、打包发布

- 1.当前真正可打包发布的是bookstore-app-c项目，它的pom.xml中引入了其它所有基础模块以及api模块。
- 2.通过在bookstore根目录下执行mvn clean package可进行打包，完成后将bookstore-app-c的target目录下的 bookstore-app-c.jar 拷贝出来进行部署，使用的默认tomcat端口号8080
- 3.当前bookstore-app-c的application.yaml文件中配置的spring.profiles.active写死为local，影响的是log4j.xml文件生效情况，local：控制台输出日志，prod：文件形式输出到/Users/linkun/logs/${appName}/${appName}.log 。可通过bookstore-app-c -> src -> resource -> log4j -> log4j2-prod.xml 修改文件输出位置。
