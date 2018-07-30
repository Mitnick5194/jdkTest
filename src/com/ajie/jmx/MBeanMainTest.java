package com.ajie.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * @author niezhenjie
 * 
 */
public class MBeanMainTest {

	public static void main(String[] args) {
		// 从工厂中获取MBean服务
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		HelloJmx hello = new HelloJmx();
		hello.setName("ajie");
		ObjectName name;
		try {
			name = new ObjectName("suibian:name=hello");
			try {
				server.registerMBean(hello, name);
			} catch (InstanceAlreadyExistsException e) {
				e.printStackTrace();
			} catch (MBeanRegistrationException e) {
				e.printStackTrace();
			} catch (NotCompliantMBeanException e) {
				e.printStackTrace();
			}
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(60 * 60 * 1000); // 长时间睡眠，防止线程指向完成后退出程序
		} catch (InterruptedException e) {
			// Ignore
		}
	}

}
