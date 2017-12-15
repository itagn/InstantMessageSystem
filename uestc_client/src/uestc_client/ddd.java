package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.io.*;
import sun.audio.*;

public class ddd extends Thread{
    //语音提醒
    public void run(){
    	try {
	    	 FileInputStream file;
	    	 BufferedInputStream buf;
	         file=new FileInputStream("ddd.mid");
	         buf=new BufferedInputStream(file);
	         AudioStream audio=new AudioStream(buf);
	         AudioPlayer.player.start(audio);
	      }
	    catch (Exception e) {
	    	e.printStackTrace();
	    	new alert("ddd出现错误");
	    }
    }
}
