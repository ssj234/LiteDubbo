# LiteDubbo
学习dubbo后自己编写的RPC框架，非常基础

* 只实现了自己定义的dubbo协议
* dubbo默认使用动态代理框架为Javassist，此处使用了JDK动态代理
* dubbo默认的序列化框架为Hessian2，此处使用了JDK的序列化