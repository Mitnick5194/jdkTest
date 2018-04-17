package com.ajie.thread;

/**
 * @author niezhenjie
 * 
 */
public class GetStackTrace {

	public static void main(String[] args) {
		Thread thread = new Thread(Thread.currentThread());
		System.out.println(thread.getName());
		StackTraceElement[] stackTrace = thread.getStackTrace();
		System.out.println(stackTrace.length);
		SecurityManager sm = System.getSecurityManager();
		ThreadGroup threadGroup = sm.getThreadGroup();
		//System.out.println(threadGroup.getName());
	}
}
