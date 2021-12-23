# 流式数据处理框架

## 目标

通过提供初始输入并传递处理后的输出以供下一阶段使用，从而允许在一系列阶段中进行数据处理。 Pipeline模式为管道模式，也称为流水线模式。 通过预先设定好的一系列的阶段来处理输入的数据，每个阶段的输出即是下一个阶段的输入。 模型图如下：

![image](https://user-images.githubusercontent.com/27359059/147108654-5965f699-338d-44cc-b1b5-a8d6899d7550.png)

Flume:

![image](https://user-images.githubusercontent.com/27359059/147108997-5030466a-f465-465a-b67c-c3f1f6afa24d.png)

从图中可以看出，整个流水线内数据流转是从上游到下游，上游的输出是下游的输入，按阶段依次执行。

- Source: 表示数据来源，比如：KafkaSource。
- Channel：表示对数据进行处理的组件，比如：JsonChannel，对数据进行json转换和处理。
- Sink：表示数据落地或下沉的地方，比如：KafkaSink，表示数据发送到指定的kafka；DbSInk表示数据落地到DB。

可以看出，Pipeline是由Source（必须有），Channel（不一定需要），Sink（必须有）三种类型的组件自由组合而成的。

> 后续考虑到，在某些特定场景下，数据可能并不一定会有有个特定的下沉，同时在每个channel中， 我们可以做的事情更多， 所以在实现的版本中，并没有要求一定需要在pipeline的最后需要接入一个sink,channel也可以不需要结束

## 特性

1. 仿照flume、netty等框架的设计思路
2. 快速开发框架，启动快速
3. 业务、框架关注点分离
4. 统一资源管理，采用池化思想
5. 插件关注点完全分离，高度可拓展
6. 提供框架级别的日志采集，完成Metric分析
7. 内置告警机制（可通过dingtalk告警配置）
8. 欢迎fork或提出宝贵的修改经验

## 架构

![image](https://user-images.githubusercontent.com/27359059/147110143-5fa42019-ecb8-40ce-a3df-8ea25d00ab09.png)

## 何时该用

![image](https://user-images.githubusercontent.com/27359059/147256234-66ceb51f-a624-4df6-bfa0-15cb79d442f2.png)

1. 多元的数据接入需求
2. 复杂的算法调用过程，冗长的链路流程
3. 存在多种数据中间状态，多个数据处理分支
4. 处理计算量大
5. 数据分发
6. 可覆盖较多链路场景

那么欢迎你👏🏻使用它，fork && advice

## 模块简要介绍

```
公共层
<module>pipeline-common</module>
框架层
<module>pipeline-framework</module>
插件层（囊括基础的source,channel,sink），可按需定制
<module>pipeline-plugin</module>
```

## 启动

如果启动看到该信息，说明需要添加启动参数:

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
-c="/Users/dick/workspace/mine/pipeline-mvp/pipeline-framework/src/main/resources/app.json" -r="/Users/dick/workspace/mine/pipeline-mvp/pipeline-framework/src/main/resources/resource.json"
```

VM options:

```
-Dlogging.config=logback.xml 
```

### 应用流程配置文件

资源文件格式，采用json。 框架resource资源路径下存在一个 app.json (样例代码)

具体实际应用场景，可通过 -c 命令参数指定其他流程定义文件

```
{
  "app": {
    "name": "pipe-first-demo",
    "emergency": "dick"
  },
  "sls": {
    "enable": true,
    "loggerKeys": [
      "count"
    ]
  },
  "monitor": {
    "enable": "true",
    "dingTalkUrl": "",
    "dingTalkSecret": "",
    "alertName": "pipeline告警"
  },
  "process": {
    "sources": [
      {
        "name": "fake_source",
        "className": "com.rany.ops.framework.core.source.FakeSource",
        "config": {
          "timeIntervalMs": 3000
        },
        "next": [
          "fake_channel"
        ]
      }
    ],
    "channels": [
      {
        "name": "fake_channel",
        "className": "com.rany.ops.framework.core.channel.DummyChannel",
        "config": {},
        "next": []
      }
    ],
    "sinks": [
        {
          "name": "fake_sink",
          "className": "xxxx",
          "config": {}
        }
    ]
  }
}
```

## 资源配置文件

资源文件格式，采用json,每个资源的name需要独一无二。 框架resource资源路径下存在一个 resource.json (样例代码)

具体实际应用场景，可通过 -r 命令参数指定其他资源文件

```
[
  {
    "name": "counter",
    "className": "com.rany.ops.framework.resource.DummyResource",
    "configMap": {
      "initValue": 20
    },
    "instanceNum": 1
  }
]

```




