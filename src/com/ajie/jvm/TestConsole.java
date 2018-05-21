package com.ajie.jvm;

/**
 * 测试使用jconsole监控jvm使用情况
 * 
 * @author niezhenjie
 * 
 */
public class TestConsole {

	public static void main(String[] args) throws InterruptedException {
		final Runtime run = Runtime.getRuntime();
		System.out.println("JVM可以使用宿主机最大内存:" + run.totalMemory() / 1024 / 1024 + "M/"
				+ run.freeMemory() / 1024 / 1024 + "M");
		/*String str = "hello";
		for(int i=0;i<24;i++){
			str += str;
		}*/
		for (int j = 0; j < 100; j++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					StringBuffer sb = new StringBuffer();
					sb.append("hello");
					for (int i = 0; i < 24; i++) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sb.append(sb.toString());
					}
					System.out.println("JVM可以使用宿主机最大内存:" + run.totalMemory() / 1024 / 1024 + "M/"
							+ run.freeMemory() / 1024 / 1024 + "M");
					run.gc();
					System.out.println("GC后JVM可以使用宿主机最大内存:" + run.totalMemory() / 1024 / 1024
							+ "M/" + run.freeMemory() / 1024 / 1024 + "M");

				}
			}).start();
		}

	}

}
