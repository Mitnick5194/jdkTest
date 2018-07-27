package com.ajie.testclient;

import java.util.Date;

/**
 * 可以捕捉进行处理的异常并不会导致线程中断 不可捕捉的和运行时异常将会导致中断
 * 
 * 如果 1/0 outMemoryError 没有空判断而出现了空指针
 * 
 * @author niezhenjie
 * 
 */
public class ThreadErr {

	public static void main(String[] args) {
		new Thread(new Thread1()).start();
		new Thread(new Thread2()).start();
	}
}

class Thread1 implements Runnable {

	public static Date getDate() {
		return null;
	}

	@Override
	public void run() {
		int t = 0;
		while (t++ < 10) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("线程1正在运行");
			if (t == 3) {
				// 这里发生了异常 线程1将结束运行 但不会影响线程2 这里没有异常捕捉 所以并不能通过try catch 处理
				// 如果有异常可以捕捉 则正常捕捉了异常则线程1不会被中断 正常往下执行 如File例子
				/*	int i = 1 / 0;
					System.out.println(i);*/
				/*File f = new File("xxx:ss");
				try {
					FileInputStream fis = new FileInputStream(f);// 会抛出FileNotFoundException
				} catch (FileNotFoundException e) {
					e.printStackTrace(); // 异常被捕捉 即异常被正常处理 不会导致线程中断
				}*/
				try {
					// date为null 并没有进行空判断 且这里没有异常可以捕捉 就会出错 线程中断
					// 但是可以手动进行捕捉 这样线程就不会中断
					Date date = Thread1.getDate();
					long time = date.getTime();
					System.out.println(time);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}
}

class Thread2 implements Runnable {
	@Override
	public void run() {
		int i = 0;
		while (i++ < 10) {
			System.out.println("线程2正在运行");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
