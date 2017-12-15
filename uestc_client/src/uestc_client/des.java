package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.security.*;
import javax.crypto.*;
import java.io.*;
import sun.misc.*;

public class des extends Thread{
	SecretKey key;
	//生成密钥
	public  void setKey() throws Exception{
		String secretKey = "37d5aed075525d4fa0fe635231cba44737d5aed075525d4fa0fe635231cba447"; 
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
	    secureRandom.setSeed(secretKey.getBytes());  
	    KeyGenerator kg = null;  
	    try {  
	        kg = KeyGenerator.getInstance("DESede");  
	    } catch (NoSuchAlgorithmException e) {  
	    	e.printStackTrace();
	    }  
	    kg.init(secureRandom);
	    key = kg.generateKey(); 
	}
	
	//加密函数  source : mingwen
	public  String jiami(String source) throws Exception{
		setKey();
		ObjectInputStream obis = new ObjectInputStream(new FileInputStream("keyFile"));
		SecretKey key = (SecretKey) obis.readObject();
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bt = source.getBytes();
		byte[] bw = cipher.doFinal(bt);
		BASE64Encoder encoder = new BASE64Encoder();
		obis.close();
		return encoder.encode(bw);
	}
   
	//解密函数 dest : miwen
	public String jiemi(String dest) throws Exception{
		setKey();
		ObjectInputStream obis= new ObjectInputStream(new FileInputStream("keyFile"));
		SecretKey key = (SecretKey) obis.readObject();
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, key);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bw = decoder.decodeBuffer(dest);
		byte[] bt = cipher.doFinal(bw);
		obis.close();
		return new String(bt);
	}
}

