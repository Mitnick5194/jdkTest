package com.ajie.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 使用Runtime.exec执行命令 但是在Linux上不能成功执行
 * 
 * @author niezhenjie
 * 
 */
public class ProcessTest2 {

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		String base = System.getenv("JAVA_HOME");
		System.out.println("base=" + base);
		String javaHome = base.endsWith(File.separator) ? base : base + File.separator;
		String cmdHome = javaHome + "bin";
		System.out.println("command=" + cmdHome);
		try {
			// Linux执行命令需要使用多个命令 -c 意思是执行完成自动关闭，这里多条linux命令通过&&连接到一起
			Process proc = runtime.exec(new String[] { "/bin/sh", "-c", "cd " + cmdHome + "&&ls" });
			InputStream inputStream = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int retVal;
			retVal = proc.waitFor();
			System.out.println("结果码=" + retVal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
