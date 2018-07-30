package com.ajie.jmx;

/**
 * 约定俗成，名字由对象名+MBean <br>
 * 这里面的方法就是要交给MBean管理的方法，到时在jconsole里能查看的方法就是这里的方法
 * 
 * @author niezhenjie
 * 
 */
public interface HelloJmxMBean {

	public String getName();

	public void setName(String name);

	public void sayHello();
}
