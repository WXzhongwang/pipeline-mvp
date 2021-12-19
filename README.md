# 流式数据处理框架

1. 仿照flume、netty等框架的设计思路
2. 快速开发框架
3. 业务、框架分离
4. 统一资源管理

## 依赖

```
<commons-collections.version>3.2.2</commons-collections.version>
<log4j.version>1.2.17</log4j.version>
<slf4j-log4j.version>1.7.30</slf4j-log4j.version>
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
```

## 注意

```
usage: pipeline [-c] [-h] [-r]
-c,--config   config file path for program, required param
-h,--help     help information for program pipeline app
-r,--res      res file path for program, required param

如果启动看到该信息，说明添加启动参数
-c --config 流程配置文件路径
-r --res    资源文件路径
```
