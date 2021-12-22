# 流式数据处理框架

1. 仿照flume、netty等框架的设计思路
2. 快速开发框架
3. 业务、框架分离
4. 统一资源管理


## 流程概要图
流程说明：
![image](https://user-images.githubusercontent.com/27359059/147108654-5965f699-338d-44cc-b1b5-a8d6899d7550.png)

## 依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.rany.ops</groupId>
    <artifactId>pipeline-mvp</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>

    <modules>
        <module>pipeline-common</module>
        <module>pipeline-framework</module>
        <module>pipeline-plugin</module>
    </modules>

    <properties>
        <java.version>1.8</java.version>
        <jdk.version>1.8</jdk.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.abstractSource>8</maven.compiler.abstractSource>
        <maven.compiler.target>8</maven.compiler.target>

        <commons-collections.version>3.2.2</commons-collections.version>
        <slf4j-api.version>1.7.26</slf4j-api.version>
        <logback.version>1.2.3</logback.version>
        <fastjson.version>1.2.78</fastjson.version>
        <commons-lang3.version>3.12.0</commons-lang3.version>
        <junit.version>4.12</junit.version>
        <dubbo.version>2.6.2</dubbo.version>
        <curator.version>2.12.0</curator.version>
        <zkclient.version>0.11</zkclient.version>
        <commons.pool2.version>2.6.2</commons.pool2.version>
        <commons.io.version>2.4</commons.io.version>
        <commons.codec.version>1.10</commons.codec.version>
        <commons.cli.version>1.3</commons.cli.version>
        <httpclient.version>4.5.2</httpclient.version>
        <guava.version>23.0</guava.version>
        <okhttp.version>3.14.9</okhttp.version>
        <guava-retry.version>2.0.0</guava-retry.version>
        <reflections.version>0.9.12</reflections.version>
        <jmh.version>1.32</jmh.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.rany.ops</groupId>
                <artifactId>pipeline-common</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.rany.ops</groupId>
                <artifactId>pipeline-framework</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.rany.ops</groupId>
                <artifactId>pipeline-plugin</artifactId>
                <version>${project.version}</version>
            </dependency>

            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>dubbo</artifactId>
                <version>${dubbo.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-pool2</artifactId>
                <version>${commons.pool2.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-cli</groupId>
                <artifactId>commons-cli</artifactId>
                <version>${commons.cli.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons.io.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-codec</groupId>
                <artifactId>commons-codec</artifactId>
                <version>${commons.codec.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.httpcomponents</groupId>
                <artifactId>httpclient</artifactId>
                <version>${httpclient.version}</version>
            </dependency>
            <dependency>
                <groupId>com.squareup.okhttp3</groupId>
                <artifactId>okhttp</artifactId>
                <version>${okhttp.version}</version>
            </dependency>
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>
            <dependency>
                <groupId>com.github.rholder</groupId>
                <artifactId>guava-retrying</artifactId>
                <version>${guava-retry.version}</version>
            </dependency>

            <!-- slf4j 日志门面 -->
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-api</artifactId>
                <version>${slf4j-api.version}</version>
            </dependency>

            <!--logback 日志实现-->
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-core</artifactId>
                <version>${logback.version}</version>
            </dependency>
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-classic</artifactId>
                <version>${logback.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-collections</groupId>
                <artifactId>commons-collections</artifactId>
                <version>${commons-collections.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>

            <!-- 反射包 -->
            <dependency>
                <groupId>org.reflections</groupId>
                <artifactId>reflections</artifactId>
                <version>${reflections.version}</version>
            </dependency>
            <!-- 基准测试 -->
            <dependency>
                <groupId>org.openjdk.jmh</groupId>
                <artifactId>jmh-core</artifactId>
                <version>${jmh.version}</version>
            </dependency>
            <dependency>
                <groupId>org.openjdk.jmh</groupId>
                <artifactId>jmh-generator-annprocess</artifactId>
                <version>${jmh.version}</version>
            </dependency>

            <dependency>
                <groupId>com.101tec</groupId>
                <artifactId>zkclient</artifactId>
                <version>${zkclient.version}</version>
                <exclusions>
                    <exclusion>
                        <groupId>org.apache.zookeeper</groupId>
                        <artifactId>zookeeper</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-recipes</artifactId>
                <version>${curator.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>${jdk.version}</source>
                    <target>${jdk.version}</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 注意

如果启动看到该信息，说明需要添加启动参数

```
usage: pipeline [-c] [-h] [-r]
-c,--config   config file path for program, required param
-h,--help     help information for program pipeline app
-r,--res      res file path for program, required param


-c --config 流程配置文件路径
-r --res    资源文件路径
```

启动参数:

```
-c="/Users/zhongshengwang/workspace/mine/pipeline-mvp/pipeline-framework/src/main/resources/app.json" -r="/Users/zhongshengwang/workspace/mine/pipeline-mvp/pipeline-framework/src/main/resources/resource.json"
```

VM options:

```
-Dlogging.config=logback.xml 
```



