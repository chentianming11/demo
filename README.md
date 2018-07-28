# demo

### redis

- 启动redis

`redis-server /usr/local/etc/redis.conf`

- 关闭redis

`redis-cli shutdown`

### zookeeper

- 启动zookeeper

`sudo /Users/mac/Downloads/zookeeper-3.4.10/bin/zkServer.sh start`

- 查看启动状态
`sudo /Users/mac/Downloads/zookeeper-3.4.10/bin/zkServer.sh status`

- 关闭zookeeper

`sudo /Users/mac/Downloads/zookeeper-3.4.10/bin/zkServer.sh stop`

### rabbit

﻿- 进入目录

`cd /usr/local/Cellar/rabbitmq/3.7.7/sbin`

- 启动

`/usr/local/Cellar/rabbitmq/3.7.7/sbin/rabbitmq-server`

- 停止

`/usr/local/Cellar/rabbitmq/3.7.7/sbin/rabbitmqctl stop`



## 构建一个dva项目
### 1. 全局安装dva

`sudo npm install -g dva-cli`
### 2. 创建一个dva项目

`sudo dva new dva-quickstart`



