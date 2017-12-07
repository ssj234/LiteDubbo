package com.github.ssj234.invoker;

import java.lang.reflect.Method;

/**
 * 里面用了JDK的反射 dubbo默认使用的是Javassist将target包装为一个Wrapper类，有invokerMethod方法
 * 内部会直接调用target的类
 * @author shisj
 *
 */
public class ProviderInvoker implements Invoker {
	Object target;
	Class interfaceClass;
	public ProviderInvoker(Class clazz,Object target) {
		this.interfaceClass = clazz;
		this.target = target;
	}

	public Result doInvoker(Invocation invocation) {
		Object ret;
		Result result = new Result();
		try {
			String name = invocation.getMethodName();
			Method method = target.getClass().getMethod(name, invocation.getArgTypes());
			if (method != null) {
				ret = method.invoke(target, invocation.getArgs());
				result.setValue(ret);
			}
		} catch (Exception e) {
			result.setException(e);
		}
		return result;
	}

	public Class getInterfaceClass() {
		return interfaceClass;
	}

}
