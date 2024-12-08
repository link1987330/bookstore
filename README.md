一、项目结构如下图，主要分为两个层级，
  1是app层（bookstore-app中的项目)，负责接收前端请求，进行权限验证、参数初步校验后调用api层进行核心业务处理；
  2是api层（bookstore-api中的项目），可直接链接数据库，并进行核心业务的处理。
  
  注意：api层之所以拆分多个jar包，目的在于业务线通常在前期体量较小，此时可将多个模块引入一个微服务中进行部署，
  节约大量服务器资源以及运维工作量；后期业务扩展后可直接将不同的模块各自进行微服务部署，扩展服务可用性。拆分服务时只需
  将remote下的实现类中@Service注解由spring注解改为dubbo的注解即可，同时配置文件增加dubbo和zookeeper的配置。

bookstore
	bookstore-base
	bookstore-app
		bookstore-app-c // build as a service
	bookstore-api
		bookstore-api-remote
		bookstore-api-user
		bookstore-api-auth
		bookstore-api-book
		bookstore-api-order
		...
  
二、bookstore-base、model、bookstore-api-remote做为公共的基础模块，每个项目都需要引用它们。其中bookstore-api-remote为app和api之间远程接口调用的接口类封装，app层可通过它快速调用api层接口；同时也存放了业务异常类、dto、枚举、国际化文件等，它是微服务之间接口调用的桥梁。

三、tools中为快速生成代码的工具包，可通过表直接快速生成api层基础代码。

四、jar包版本控制应该在根目录的pom.xml文件中通过dependencyManagement来控制，以防止jar包版本冲突，目前为了快速实现并未按规范严格执行。

五、当前项目的登录模块依赖了redis，redis地址可直接在本地服务器通过hosts中配置redis.linkun.com即可，如 127.0.0.1 redis.linkun.com
  默认redis为无密码登录，如果需要添加密码则需要在 bookstore -> bookstore-app -> bookstore-app-c -> src/main/resource ->application.yaml
  下将 spring.redis.password注释放开。

六、项目sql存在于bookstore -> sql 目录下，数据库当前连接的阿里云RDS云数据库，代码中使用的公网地址访问，无需修改。

