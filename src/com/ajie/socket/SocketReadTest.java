package com.ajie.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 如果发送方（socket）已经关闭了，接收方（serverSocket）的读方法read()会返回-1<br>
 * 如果没有关闭，则read()会一直阻塞到有数据读取
 * 
 * @author niezhenjie
 * 
 */
public class SocketReadTest {
	public static void main(String[] args) {
		final SocketReadTest srt = new SocketReadTest();
		new Thread(new Runnable() {
			@Override
			public void run() {
				srt.server();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				srt.socket();
			}
		}).start();
	}

	public void server() {
		try (ServerSocket ss = new ServerSocket();) {
			ss.bind(new InetSocketAddress("localhost", 9999));
			StringBuilder sb = new StringBuilder();
			while (true) {
				Socket s = ss.accept();
				s.setSoTimeout(10 * 1000); // Ten seconds
				InputStream is = s.getInputStream();
				while (true) {
					int ch = is.read();
					if (-1 == ch) {
						continue;
					}
					if (ch == 'a') {
						break;
					}
					sb.append((char) ch);
				}
				System.out.println(sb.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void socket() {
		Socket s = null;
		try {
			s = new Socket("localhost", 9999);
			OutputStream out = s.getOutputStream();
			out.write("helloworld\n".getBytes());
			out.flush();
			out.write("this is a".getBytes());
			out.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
