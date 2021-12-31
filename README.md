# 流式数据处理框架

## 目标

通过提供初始输入并传递处理后的输出以供下一阶段使用，从而允许在一系列阶段中进行数据处理。 Pipeline模式为管道模式，也称为流水线模式。 通过预先设定好的一系列的阶段来处理输入的数据，每个阶段的输出即是下一个阶段的输入。 模型图如下：

![image](https://user-images.githubusercontent.com/27359059/147108654-5965f699-338d-44cc-b1b5-a8d6899d7550.png)

Flume:

![image](https://user-images.githubusercontent.com/27359059/147108997-5030466a-f465-465a-b67c-c3f1f6afa24d.png)

从图中可以看出，整个流水线内数据流转是从上游到下游，上游的输出是下游的输入，按阶段依次执行。

- Source: 表示数据来源，比如：RcoketSource、 RabbitSource, KafkaSource， HttpSource等。
- Channel：表示对数据进行处理的组件，比如：JsonChannel，对数据进行json转换和处理。
- Sink：表示数据落地或下沉的地方，比如：KafkaSink，表示数据发送到指定的kafka；DbSInk表示数据落地到DB。

可以看出，Pipeline是由Source（必须有），Channel（不一定需要），Sink（必须有）三种类型的组件自由组合而成的。

> 后续考虑到，在某些特定场景下，数据可能并不一定会有有个特定的下沉，同时在每个channel中， 我们可以做的事情更多， 所以在实现的版本中，并没有要求一定需要在pipeline的最后需要接入一个sink

## 特性

1. 参考flume、netty(内部存在pipeline)等框架的底层实现及设计思路
2. 灵活的DAG编排流程
3. 快速开发框架，启动快速
4. 业务、框架关注点分离
5. 统一资源管理，采用池化思想
6. 插件关注点完全分离，高度可拓展
7. 提供框架级别的日志采集，完成Metric分析
8. 内置告警机制（可通过dingtalk告警配置）
9. 可通过系统框架级别日志，快速构建各种业务监控大盘，TPS, QPS, 吞吐等核心性能指标快速可视。
10. 欢迎fork或提出宝贵的修改经验

## 架构

![image](https://user-images.githubusercontent.com/27359059/147110143-5fa42019-ecb8-40ce-a3df-8ea25d00ab09.png)

## 何时该用

![image](https://user-images.githubusercontent.com/27359059/147256234-66ceb51f-a624-4df6-bfa0-15cb79d442f2.png)

1. 多元的数据接入需求
2. 复杂的算法调用过程，冗长的链路流程
3. DAG编排流程
4. 存在多种数据中间状态，多个数据处理分支
5. 处理计算量大
6. 数据分发
7. 可覆盖较多链路场景
8. 千万级别的数据量下验证

🧨🧨🧨🧨🧨🧨🧨🧨🧨 如果你的设计，需满足以上场景，那么欢迎你👏🏻使用它，fork && advice, 🌈🌈🌈🌈🌈🌈 🧨🧨🧨🧨🧨🧨🧨

## 快速使用

可直接使用该工程开发，但是建议使用脚手架快速创建流程进行开发，这样能做到框架和业务分离，十分友好。

[骨架工程](https://github.com/WXzhongwang/pipeline-mvp-archetype)

## 模块简要介绍

```
<module>pipeline-common</module><!--公共层-->
<module>pipeline-framework</module><!--框架层-->
<module>pipeline-plugin</module><!--插件层-->
<module>pipeline-resource</module><!--资源层-->
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

## 告警模板

框架层面内置告警通知：

![image](https://user-images.githubusercontent.com/27359059/147672416-0aa6c235-232b-405f-94ba-9e7d73983110.png)

机器人接入：

[钉钉自定义机器人](https://open.dingtalk.com/document/robots/custom-robot-access)

需配置dingTalkUrl && dingTalkSecret 即可。

```
  "monitor": {
    "enable": "true", #开关
    "dingTalkUrl": "",
    "dingTalkSecret": "",
    "mobiles": [
    "1866848xxxx"
    ],
    "alertName": "pipeline告警"
  }
```



