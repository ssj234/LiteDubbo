# LiteDubbo

LiteDubbo是学习dubbo源码后编写的一个极其简单的RPC通信框架，使用了dubbo的provider、consumer、protocol、registry等概念。

dubbo源码分析地址 https://www.kancloud.cn/ssj234/dubbo_source_study/471380

* 只实现了自己定义的dubbo协议，与标准的dubbo只是名字相同
* dubbo默认使用动态代理框架为Javassist，此处使用了JDK动态代理
* dubbo默认的序列化框架为Hessian2，此处使用了JDK的序列化
* dubbo非常优秀，源码使用了大量的优化，这里没有


# 使用

## provider端
```
// 创建Provider
Provider provider = new Provider();

// 获取注册中心，这里使用zookeeper作为注册中心
Registry registry = new ZookeeperRegistry("127.0.0.1", 2181);
provider.addRegistry(registry);

// 创建dubbo协议，这里的协议是自己定义的，与标准的dubbo协议只是名字一样
Protocol expProtocol = new DubboProtocol("127.0.0.1",3309);
provider.addProtocol(expProtocol);
// 发布服务
provider.export(ISearchPrice.class,new SearchPriceFromInet());
```

## consumer端

```
// 创建consumer
Consumer consumer = new Consumer();

// 获取注册中心，这里使用zookeeper作为注册中心
Registry registry = new ZookeeperRegistry("127.0.0.1", 2181);
consumer.addRegistry(registry);

// 引用服务，返回一个动态代理实例
ISearchPrice searcher = (ISearchPrice) consumer.refer(ISearchPrice.class);
// 调用方法
String rs = searcher.getPrice("Python First Head");
System.out.println("rs is " + rs);
```

# 流程


## 服务提供者发布服务流程

![图1. 服务提供者发布服务流程](http://www.uxiaowo.com/dubbo/lite_provider.png)

## 服务引用者引用服务流程

![图2. 服务引用者引用服务流程](http://www.uxiaowo.com/dubbo/lite_consumer.png)

## 数据传输流程

![图3. 数据传输流程](http://www.uxiaowo.com/dubbo/lite_transport.png)

