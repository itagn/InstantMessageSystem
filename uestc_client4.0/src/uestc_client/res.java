package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class res extends Thread{
    
//  添加好友请求
	private JFrame jf;
	private JPanel jp;
	private JLabel title;
	private JButton accept;
	private JButton refuse;
	private JLabel user;
	
	public static String serverip;
    public static final int serverport = 7777;
    public static String respond;
    // 参数
	String fi;
	client list;
	String yes;
	String no;
	String txt;
	int func;
	Socket socket;
	int y1;
	int y2;
	public res(String a,client b,String c,String d,String e,int f,Socket g,int h,int i){
		fi = a;
		list = b;
		yes = c;
		no = d;
		txt = e;
		func = f;
		this.socket = g;
		y1 = h;
		y2 = i;
	}
	
	public void run(){
		  getip();
		  jf = new JFrame("好友请求");
   	      jf.setSize(300,280);
	      jf.setResizable(false);
	      jf.setLocationRelativeTo(null);
	      {
	    	  jp = new JPanel();
	    	  jp.setLayout(null);
	    	  jp.setBackground(Color.white);
	    	  jf.add(jp);
	    	 {
	    		  int len = fi.length();
	    		  user = new JLabel(fi);
	    		  user.setFont(null);
	    		  user.setForeground(Color.red);
	    		  user.setBounds((300-10*len)/2,y1,200,20);
	  			  jp.add(user);
	    	  }
	    	  {
	    		  title = new JLabel(txt);
	    		  title.setFont(null);
	    		  title.setBounds(100,y2,200,20);
	  			  jp.add(title);
	    	  }
	    	  {
	    		  accept = new JButton("同意");
	    		  accept.setFont(null);
	    		  accept.setBounds(30,180,100,30);
	    		  jp.add(accept);
	    		  accept.setBackground(Color.white);
	    		  accept.addActionListener(new ActionListener() {
	 		            public void actionPerformed(ActionEvent e) {
	 		            	//多线程操作
	 		            	if(func == 1){
	 		            		socket(yes);
		 		            	list.add();
	 		            	}else if(func == 2){
	 		            		try {
	 		            			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		 							out.writeUTF(yes);
		 							out.flush();
		 							out.close();
								} catch (Exception e2) {
									e2.printStackTrace();
								}
	 		            	}
	 		            	
	 		            	jf.dispose();
	 					}
	 			    });
	    	  }
	    	 {
	    		  refuse = new JButton("拒绝");
	    		  refuse.setFont(null);
	    		  refuse.setBounds(170,180,100,30);
	    		  jp.add(refuse);
	    		  refuse.setBackground(Color.white);
	    		  refuse.addActionListener(new ActionListener() {
	 		            public void actionPerformed(ActionEvent e) {
	 		            	//多线程操作
	 		            	if(func == 1){
	 		            		socket(no);
	 		            	}else if(func == 2){
	 		            		try {
	 		            			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		 							out.writeUTF(no);
		 							out.flush();
		 							out.close();
								} catch (Exception e2) {
									e2.printStackTrace();
								}
	 		            		
	 		            	}
	 		            	jf.dispose();
	 					}
	 			    });
	    	  }
	  }
	   jf.setVisible(true);
	}
	
	// 获取txt内容
	public void getip(){
	    try {
			   File file = new File("serverip.txt");
	       	   InputStreamReader read = new InputStreamReader(new FileInputStream(file),"utf-8"); 
	       	   BufferedReader reader = new BufferedReader(read);
	       	   String line;
	       	   String s = "";
	       	   while( (line = reader.readLine()) != null) {
	       		 s += line; 
	       	   }
	       	   reader.close();
	       	   serverip = s;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// socket
	public void socket(String msg){
		try {
			Socket clientsocket = new Socket(serverip, serverport);
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientsocket.getOutputStream()));
			out.writeUTF(msg);
			out.flush();
			clientsocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
