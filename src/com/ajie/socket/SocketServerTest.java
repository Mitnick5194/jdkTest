package com.ajie.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 浏览器其实就是一个httpClient 而httpClient其实就是socket 所以在浏览器直接输入127.0.0.1:9999 可以链接成功
 * 在浏览器输入127.0.0.1:9999/test/index.jsp?p=param&type=1将输出：
 * 
 * GET：/test/index.jsp?p=param&type=1 HTTP/1.1 Host: 127.0.0.1:9999 Connection:
 * keep-alive Cache-Control: max-age=0 Upgrade-Insecure-Requests: 1 User-Agent:
 * Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko)
 * Chrome/64.0.3282.140 Safari/537.36 Accept:
 * text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image
 * apng,.... //Accept-Encoding: gzip, deflate, br //Accept-Language:
 * en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7 //Cookie: er_u=004302c02e00400....... /*
 * 
 * @author niezhenjie
 * 
 */
public class SocketServerTest {

	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket s = null;
		boolean repeat = true; // 尝试重连标记
		try {
			ss = new ServerSocket(9999);
			System.out.println("正在本地9999端口监听");
			ss.setSoTimeout(2000); // 设置accept的超时值
			s = ss.accept();
			System.out.println("连接成功 " + s.getLocalAddress());
			InputStream in = s.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			do {
				n = in.read(buf);
				System.out.println(new String(buf));
			} while (n != -1);
		} catch (IOException e) {
			if (repeat) {
				try {
					System.out.println("超时 正在进行重试");
					s = ss.accept();
				} catch (IOException e1) {
					System.out.println("重试accept失败");
				} finally {
					repeat = false;
				}
			}
		} finally {

		}
	}
}
