# midi-bundle

MiDi组件包

## 包组成
一个组件包--fat-jar,包含多个Processor

## 包部署
引擎提供上传接口发布组件

## 组件启动

引擎提供实例化组件接口
引擎提供组件实例启动和停止接口

## 组件实例

运行中的组件实例能消费分发给它的消息，可对消息进行一定的处理后路由到下一个或多个组件实例进行处理，形成数据流处理逻辑。
组件可运行多个副本，通过负载均衡算法同时处理消息。

## 消息路由
引擎提供基于事件驱动的消息路由分发功能






