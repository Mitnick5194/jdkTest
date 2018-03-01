package com.ajie.test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * ByteArrayInputStream里面的buf是一个已经有数据的数据 其实就是对byte数组（有数据）进行位置和长度的操作 
 * 
 * 而不是对流进行操作  这个类应该主要业务不是读操作 如果是读操作 感觉没必要 因为在构造buf时 已经是拿到数据了 
 * 
 * 所以应该是其他的操作 读操作不是主要业务 具体是什么还有待学习
 * 
 * 
 * 
 * @author niezhenjie
 * 
 */
public class TestByteInputStream {
	public static void main(String[] args) throws IOException {
		String s = new String("hello world 世界你好， 中国你好！！！");
		byte[] buf = s.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		byte read = (byte) bais.read();
		byte[] bs = { read };
		System.out.println(new String(bs)); //输出h
		
		bais.mark(0); //在当前指针位置做个记号 参数在ByteArrayInputStream无用（因为父类有带参数 但是在这个类无需参数）
		byte res = (byte)bais.read(); //读取一个字节 如果是中文 则会乱码 
		byte[] bs2 = {res};
		System.out.println(new String(bs2)); //输出e
		
		bais.reset(); //指针回到mark处（即1处）
		byte res2 = (byte)bais.read();
		byte[] bs3 = {res2};
		System.out.println(new String(bs3)); //输出e
		
		//下面直接使用文件流缓存作为操作的buf
		InputStream is = new FileInputStream("/home/mitnick/test.txt");
		byte[] bytes = new byte[1024];
		is.read(bytes); //将io流中读入的二进制数据转换成byte 保存在byte数组中
		ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);  //其实就是对保存文件流的缓存进一步的封装
		byte res3 = (byte)bais2.read();
		byte[] bs4 = {res3};
		System.out.println(new String(bs4)); //输出e
		bais2.close();
		is.close();
	}

}
