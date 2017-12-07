package com.github.ssj234.invoker;

import java.io.Serializable;

public class Invocation implements Serializable {
	Class interfaceClass;
	String methodName;
	Class[] argTypes;
	Object[] args;
	
	public Invocation(Class interfaceClass,String methodName, Class[] argTypes, Object[] args) {
		super();
		this.interfaceClass = interfaceClass;
		this.methodName = methodName;
		this.argTypes = argTypes;
		this.args = args;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class[] getArgTypes() {
		return argTypes;
	}
	public void setArgTypes(Class[] argTypes) {
		this.argTypes = argTypes;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Class getInterfaceClass() {
		return interfaceClass;
	}
	public void setInterfaceClass(Class interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	
	
}
