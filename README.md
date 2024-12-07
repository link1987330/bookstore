一、项目分为两个层级，1是app层（bookstore-app中的项目)，负责接收前端请求，并进行权限验证、参数初步校验等；2是api层（bookstore-api中的项目），可直接链接数据库，并进行核心业务的处理。

二、在正式生产环境中app和api的项目应该分成不同的微服务，微服务的粒度可以由业务的发展进行扩展，如前期业务量小，可将bookstore-api-auth、bookstore-api-book、bookstore-api-user等都引入一个服务中发布为一个微服务，
后期业务量上来之后，可拆分成各自的微服务。（当前项目中为了快速实现，将所有服务直接引入到bookstore-app-c项目中做为单体项目发布，如在生产环境中会拆分，并引入dubbo进行微服务调用）

三、bookstore-base、model、bookstore-api-remote做为公共的基础模块，每个项目都需要引用它们。其中bookstore-api-remote为app和api之间远程接口调用的接口类封装，app层可通过它快速调用api层接口。

四、tools中为快速生成代码的工具包，可通过表直接快速生成api层基础代码。
