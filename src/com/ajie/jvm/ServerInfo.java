package com.ajie.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * 获取关于jvm的基本信息（内存 cpu数目 内存使用量和剩余量）
 * 
 * 找到一篇文章 解释的非常好：maxMemory freeMemory totalMemory这3个方法可以获取虚拟机中的内存分配情况，
 * 
 * 1.maxMemory()这个方法返回的是java虚拟机（这个进程）能构从操纵系统那里挖到的最大的内存
 * 
 * 2.totalMemory：程序运行的过程中，内存总是慢慢的从操纵系统那里挖的，基本上是用多少挖多少，直
 * 挖到maxMemory()为止，所以totalMemory()是慢慢增大的
 * 
 * 3.freeMemory：挖过来而又没有用上的内存，实际上就是
 * freeMemory()，所以freeMemory()的值一般情况下都是很小的（totalMemory一般比需要用得多一点
 * ，剩下的一点就是freeMemory）
 * 
 * 计算可用jvm内存： maxMemory - totalMemory (并不能使用freeMemory)；
 * 
 * @author niezhenjie
 * 
 */
public class ServerInfo {
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		System.out.println("JVM可以使用宿主机最大内存:" + run.maxMemory() / 1024 / 1024 + "M");
		System.out.println("JVM空闲内存:" + run.freeMemory() / 1024 / 1024 + "M");
		System.out.println("此刻宿主机分配给JVM的内存（宿主机并不是一次行把JVM最大内存分配过来 都是一点一点的分配）:" + run.totalMemory()
				/ 1024 / 1024 + "M");
		System.out.println("可用处理器的数目:" + run.availableProcessors());
		
		System.out.println("===========================楚河=======================================");
		// OperatingSystemMXBean可以获取到宿主机的信息
		OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
		System.out.println("操作系统的架构："+system.getArch());
		System.out.println("cpu数量"+system.getAvailableProcessors());
		System.out.println("操作系统的名称:"+system.getName());
		System.out.println("最后一分钟内系统加载平均值:"+system.getSystemLoadAverage());
		System.out.println("操作系统的版本:"+system.getVersion());
		
		
	}
}
