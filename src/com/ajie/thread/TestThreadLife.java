package com.ajie.thread;

/**
 * 测试线程的的生命周期 
 * 
 * @author niezhenjie
 * 
 */
public class TestThreadLife {
	
	public static void main(String[] args) {
		MyThread mt = new MyThread();
		Thread t = new Thread(mt);
		t.start();  //会执行线程
		t.start(); //这个会抛 java.lang.IllegalThreadStateException异常 因为线程已经执行完毕 线程已经被销毁
		Thread t2 = new Thread(mt);  //如果要再次执行线程 可以在实例化一次Runnable实例
		t2.start();
	}
}

class MyThread implements Runnable{

	@Override
	public void run() {
		System.out.println("执行线程任务");
	}
	
}
