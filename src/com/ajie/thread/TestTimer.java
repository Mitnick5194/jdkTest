package com.ajie.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * java实现定时器的几种方式
 * 
 * @author niezhenjie
 * 
 */
public class TestTimer {

	public static void main(String[] args) {
		System.out.println("线程数："+Runtime.getRuntime().availableProcessors());
		final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		/*Timer1 tm1 = new Timer1();
		Thread t = new Thread(tm1);
		t.start();*/

		// TimerTask方式
		/*TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("定时器任务执行时间: "+ fmt.format(new Date()));
				System.out.println("执行定时器里面的内容");
				
			}
		};
		Timer timer = new Timer();
		//使用一个定时器 启动一个任务调度 1秒后开始执行 往后没3秒执行一次
		
		System.out.println("任务执行前的时间: "+fmt.format(new Date()));
		timer.schedule(task, 1000, 3000);*/

		/*下面是利用ScheduledExecutorService工具实现 相对其他两个 这个有一下的有点
		1>相比于Timer的单线程，它是通过线程池的方式来执行任务的  
		2>可以很灵活的去设定第一次执行任务delay时间 
		3>提供了良好的约定，以便设定执行的时间间隔 
		*/
		
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("定时器任务执行时间: "+fmt.format(new Date()));
				System.out.println("执行定时器里面的");
			}
		};
		
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		System.out.println("定时器任务执行之前的时间: "+fmt.format(new Date()));
		//三秒之后开始执行 往后每5秒执行一次  最后一个参数值致命使用的时间单位 这里用的是秒
		service.scheduleAtFixedRate(run, 3, 5, TimeUnit.SECONDS);

	}
}

/**
 * 这是使用Runnable结合线程休眠的方式实现
 * 
 * @author mitnick
 *
 */
class Timer1 implements Runnable {

	int timeInterval = 5000;

	@Override
	public void run() {
		while (true) {
			System.out.println("执行定时任务");
			try {
				Thread.sleep(timeInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}