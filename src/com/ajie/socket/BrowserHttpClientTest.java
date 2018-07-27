package com.ajie.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 模拟浏览器请求服务器
 * 
 * 成功的访问了/wx/control/param.jspx 并把它返回的jsp页面打印出来 如果cookie错误 或没有传cookie 将报HTTP/1.1
 * 302 Found
 * 
 * @author niezhenjie
 * 
 */
public class BrowserHttpClientTest {
	public static void main(String[] args) {
		Socket s = null;
		try {
			s = new Socket("192.168.0.50", 8081);
			PrintStream out = new PrintStream(s.getOutputStream());
			out.println("GET /drt/journeys.jspx HTTP/1.1");
			out.println("Host: localhost:8080");
			out.println("Connection: Close");
			out.println("Cookie:omni-ssss=c21ae7fc01638a7c6f5a003a");
			out.println();
			InputStream in = s.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			StringBuffer sb = new StringBuffer();
			do {
				n = in.read(buf);
				sb.append(new String(buf));
			} while (n > 0);
			System.out.println(sb.toString());
		} catch (UnknownHostException e) {
			System.out.println("404");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
