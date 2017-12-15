package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.io.*;
import java.net.*;

//客户端线程
public class clientThread extends Thread{
	
	String msg;
    public static String bieren;
    public static String respond;
 // 服务器IP和端口
    public static String serverip;
    public static final int serverport = 7777;
	public static Socket socket = null;
	public static int port;
	public static String name;
    public static ServerSocket serverSocket;
    public static String save;
    public static client list;
    
	public clientThread(ServerSocket a,String b,int c,client d){
		serverSocket = a;
		name = b;
		port = c;
		list = d;
	}
	//  客户端线程
	public void run(){
	try {
	  if(  serverSocket == null )
		serverSocket = new ServerSocket(port);
		while (true) {
			    socket = serverSocket.accept();
			    getip();
				DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		        String  strread = in.readUTF();
		        String[] fun = strread.split("[?]");
		        String file = new String("file");
		        String chat = new String("chat");
		        String add = new String("add");
		       if(fun[0].equals(file)){
		    		    String func = new String( fun[1].split("[_]")[0] );
			    	    String send = new String("send");
			    	    String get = new String("get");
						String ready = new String("ready");
						String touxiang = new String("touxiang");
			    	    if(func.equals(get)){
			        	//   发送文件到服务器
			    	    	String url = fun[1].split("[_]")[1];
				            DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
							DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(url)));
							
							int sendSize = 8192;
					        byte[] sendbuf = new byte[sendSize];
					         while (true) {
					             int read = 0;
					             if (fis != null) {
					                 read = fis.read(sendbuf);
					             }

					             if (read == -1) {
					                 break;
					             }
					             ps.write(sendbuf, 0, read);
					         }
					         ps.flush();
					         ps.close();
					         fis.close();
					         
			    	    }else if(func.equals(send)){
			    	    	 //   从服务器接收文件
			    	    	String rese =  fun[1].split("[_]")[1];
			    	    	
				            String filename = rese.split("[#]")[0];
			    	    	String aburl = rese.split("[#]")[1];
			    	    	String people = rese.split("[#]")[2];
			    	    	
			    	    	fun("chatstatus"+"?"+name+"_"+people);
			    	    	String re = new String(respond);
			    		    getsave();
			    	    	String dir = save +name+"/";
				            File f = new File(dir);
				            if(!f.exists())
				            f.mkdirs();
				            
			    	    	String address = dir + filename;
			 				String first = new String("1");
			 				
			    	    	if(re.equals(first)){
			    	    		 Socket clsocket = new Socket(serverip, serverport);
						    	 DataInputStream clin = new DataInputStream(new BufferedInputStream(clsocket.getInputStream()));
						         DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(address))));
						         DataOutputStream clout = new DataOutputStream(new BufferedOutputStream(clsocket.getOutputStream()));
						         clout.writeUTF("tosend"+"?"+aburl);
						         clout.flush();
						            int getSize = 8192;
						            byte[] getbuf = new byte[getSize];
						            while (true) {
						                int read = 0;
						                if (clin != null) {
						                    read = clin.read(getbuf);
						                }
						                if (read == -1) {
						                    break;
						                }
						                fileOut.write(getbuf, 0, read);
						            }
						            fileOut.close();
						    		clsocket.close();
						        
						        new alert("接收文件,保存到："+address);
		 				    }
		 				else{
		 					// 语音
		 					 ddd d = new ddd();
		 					 d.start();
		 					 list.setcolor(people);
		 				}
			    	    }
			    	    else if(func.equals(ready)){
			    	    	  String filename = fun[1].split("[_]")[1];
			    	    	  
							  res res = new res(filename,list,"yes","no","请求您接收文件:",2,socket,100,50);
							  res.start();
			    	    }else if(func.equals(touxiang)){
			    	    	 //   从服务器接收文件
			    	    	String rese =  fun[1].split("[_]")[1];
			    	    	
				            name = rese.split("[#]")[0];
			    	    	String aburl = rese.split("[#]")[1];
			    		    getsave();
			    	    	String dir = save +name+"/img/";
				                 getip();
			    	    	     String address = dir + "photo.png";
			    	    		 Socket clsocket = new Socket(serverip, serverport);
						    	 DataInputStream clin = new DataInputStream(new BufferedInputStream(clsocket.getInputStream()));
						         DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(address))));
						         DataOutputStream clout = new DataOutputStream(new BufferedOutputStream(clsocket.getOutputStream()));
						         clout.writeUTF("tosend"+"?"+aburl);
						         clout.flush();
						         
						            int getSize = 8192;
						            byte[] getbuf = new byte[getSize];
						            while (true) {
						                int read = 0;
						                if (clin != null) {
						                    read = clin.read(getbuf);
						                }
						                if (read == -1) {
						                    break;
						                }
						                fileOut.write(getbuf, 0, read);
						            }
						            fileOut.close();
						    		clsocket.close();
						    		
						    		list.remove();
			    	    }
		        }else if (fun[0].equals(chat)) {
		        	String[] arr = fun[1].split("[@]");
		 	        String a = arr[0];
		 	     	String b = arr[1];
		 	     	
		 	        des s = new des();
		 			msg = a + s.jiemi(b);
		 			
		 			String[] user =  a.split(" ");
		 			bieren = user[0];
		 			String na = new String(bieren);
		 			String we = new String(name);
		 			
		 			if(!na.equals(we)){
		 				fun("chatstatus"+"?"+name+"_"+bieren);
		 				String re = new String(respond);
		 				String first = new String("1");
		 				
		 				if(re.equals(first)){
		            			  list.yourmsg(msg);
		            			  list.socket("ready"+"?"+name+"_"+bieren);
		 				}
		 				else{
		 					// 语音
		 					 ddd d = new ddd();
		 					 d.start();
		 					 list.setcolor(na);
		 				}
		 			}else{
		 				    list.mymsg(msg);
		 			}
				  }else if(fun[0].equals(add)){
					  String[] arr = fun[1].split("[#]");
					  String from = arr[0].toString();
					  String come = arr[1].toString();
					  
					  String alert = new String("alert");
					  String update = new String("update");
					  String remove = new String("remove");
					  if(from.equals(alert)){
						  String other = come.split("[_]")[0];
						  String my = come.split("[_]")[1];
						 
						  res res = new res(other, list,"add"+"?"+my+"_"+other+"_"+"yes","add"+"?"+my+"_"+other+"_"+"no","请求添加您为好友",1,socket,50,100);
						  res.start();
				 	      ddd d = new ddd();
	 				      d.start();
					  }
					  else if (from.equals(update)) {
						    list.add();
					}
					  else if (from.equals(remove)) {
						    list.remove();
						}
		        }
		        
		    }
				} catch (Exception e) {
					e.printStackTrace();
	             }
          
	}
// socket
public void fun(String msg){
			try {
				Socket clientsocket = new Socket(serverip, serverport);
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientsocket.getOutputStream()));
				out.writeUTF(msg);
				out.flush();
				InputStream input = clientsocket.getInputStream();
				DataInputStream in = new DataInputStream(input);
				respond = in.readUTF();
				clientsocket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				new alert("read出现错误");
			} catch (IOException e) {
				new alert("服务器未启动");
			}
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
public void getsave(){
    try {
		   File file = new File("savePath.txt");
       	   InputStreamReader read = new InputStreamReader(new FileInputStream(file),"utf-8"); 
       	   BufferedReader reader = new BufferedReader(read);
       	   String line;
       	   String s = "";
       	   while( (line = reader.readLine()) != null) {
       		 s += line; 
       	   }
       	   reader.close();
       	   save = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
}
