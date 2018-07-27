package com.ajie.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 使用Runtime.exec执行命令 但是在Linux上不能成功执行
 * 
 * @author niezhenjie
 * 
 */
public class ProcessTest {

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		try {
			// 相当于在命令窗口执行该命令 但不知为什么 在Linux不能运行
			Process process = runtime.exec(new String[] { "/bin/sh", "-c", "top" });
			// 执行上面命令时的输出流(即执行上面命令时 输出在命令窗口上的流) 注意 是input而不是output
			InputStream inputStream = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while (br.readLine() != null) {
				System.out.println(br.readLine());
			}
			int exitVal = process.waitFor(); // 正常结束返回0
			System.out.println("exitVal=" + exitVal);
			if (exitVal != 0) {
				// 错误信息的流
				InputStream errorStream = process.getErrorStream();
				br = new BufferedReader(new InputStreamReader(errorStream));
				while (null != br.readLine()) {
					System.out.println(br.readLine());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
