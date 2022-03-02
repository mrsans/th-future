# sharind-jdbc

## 简介：
    1. Apache ShardingSphere 是一套开源的分布式数据库插件
    2. 可进行读写分离
    3. 可进行分库分表
    4. 可进行分布式事务
    5. 仅仅支持java

官方文档 : https://shardingsphere.apache.org/index_zh.html

## 配置文档

### maven 配置：

      <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.1.1</version>
      </dependency>

### yml配置

#### 读写分离
  
```yaml
spring:
  application:
    name: frame-shardingjdbc  # 服务的名称
  shardingsphere:   # 配置sharding
    enabled: true   # 是否启动
    props:          
      sql:
        show: true   
    datasource:     # 配置数据源
      # 配置数据源的名称 
      names: master,slave
      # 第一个数据源
      master:
        # 初始化连接数
        initialSize: 50
        # 连接池最小连接数
        minIdle: 50
        # 最大连接数
        maxActive: 200
        # 数据库连接池
        type: com.zaxxer.hikari.HikariDataSource
        # 数据库驱动
        driver-class-name: com.mysql.cj.jdbc.Driver
        # 数据库链接
        jdbcUrl: jdbc:mysql://localhost:3306/sharding_test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8
        # 数据库用户名
        username: root
        # 数据库密码
        password: root
      # 第二个数据源
      slave:
        # 初始化连接数
        initialSize: 50
        # 连接池最小连接数
        minIdle: 50
        # 最大连接数
        maxActive: 200
        # 数据库连接池
        type: com.zaxxer.hikari.HikariDataSource
        # 数据库驱动
        driver-class-name: com.mysql.cj.jdbc.Driver
        # 数据库链接
        jdbcUrl: jdbc:mysql://172.18.12.198:3306/sharding_test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8
        # 数据库用户名
        username: root
        # 数据库密码
        password: root
    masterslave:
      # 读写分离配置规则
      load-balance-algorithm-type: round_robin
      # 最终的数据源名称
      name: dataSource
      # 主库数据源名称
      master-data-source-name: master
      # 从库数据源名称列表，多个逗号分隔
      slave-data-source-names: slave
    # springboot的配置，是否覆盖原有的bean
  main:
    allow-bean-definition-overriding: true
```

```yaml
 # 分库分表配置
 spring:
   application:
     name: frame-shardingjdbc
   shardingsphere:
     enabled: true
     props:
       sql:
         show: true
     datasource:
       # 配置数据源的名称
       names: ds0,ds1
       # 第一个数据源
       ds0:
         # 初始化连接数
         initialSize: 50
         # 连接池最小连接数
         minIdle: 50
         # 最大连接数
         maxActive: 200
         # 数据库连接池
         type: com.zaxxer.hikari.HikariDataSource
         # 数据库驱动
         driver-class-name: com.mysql.cj.jdbc.Driver
         # 数据库链接
         jdbcUrl: jdbc:mysql://localhost:3306/sharding_test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8
         # 数据库用户名
         username: root
         # 数据库密码
         password: root
       # 第二个数据源
       ds1:
         # 初始化连接数
         initialSize: 50
         # 连接池最小连接数
         minIdle: 50
         # 最大连接数
         maxActive: 200
         # 数据库连接池
         type: com.zaxxer.hikari.HikariDataSource
         # 数据库驱动
         driver-class-name: com.mysql.cj.jdbc.Driver
         # 数据库链接
         jdbcUrl: jdbc:mysql://172.18.12.198:3306/sharding_test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8
         # 数据库用户名
         username: root
         # 数据库密码
         password: root
     sharding:
       # 分表
       tables:
         # 针对哪个表进行分离
         t_user:
           # 哪些库对应的哪些表   【ds0   ds1】对应上面的数据源   t_user1  t_user2    共4个
           actualDataNodes: ds${0..1}.t_user${1..2}
           # 数据库策略
           databaseStrategy:
             # 行内表达式
             inline:
               # 根据哪个字段进行分割
               shardingColumn: part
               # 分割的库
               algorithmExpression: ds${part % 2}
           # 分表策略
           tableStrategy:
             # 行内表达式
             inline:
               # 分割字段
               shardingColumn: id
               # 表达式
               algorithmExpression: t_user${id % 2 + 1}
```
sharding配置官网:https://shardingsphere.apache.org/document/legacy/3.x/document/cn/manual/sharding-proxy/configuration/

## sharding 原理



















