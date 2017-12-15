package uestc_server;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.io.*;
import java.net.*;
import java.sql.*;

//服务器主线程
public class serverThread extends Thread{
	//JDBC 驱动名及数据库URL
	public static final String JDBC = "com.mysql.jdbc.Driver";
	//数据库的用户名与密码
	public static final String USER = "root";
	public static final String PASS = "1234";
	
	public static String func = "";
	public static String content = "";
	   
	   public static String oneip;
       public static String twoip;
       public static int oneport;
       public static int twoport;
       
       public static String serverip;
       public static final int serverport = 7777;
       
       public static String[] ggname = {};
       public static String username;
	   
	   public static DataInputStream inputStream = null;
	   public static DataOutputStream outputStream = null;
	   public static ServerSocket cai = null;
	   public static Socket dong;
	   public static String save;
	   public static String respond;
	   
	   public static server data;
	   public serverThread(String a,server b){
		   serverip = a;
		   data = b;
	   }
//  创建服务器socket接收信息
    public void run() {
		try {
			if(cai == null){
			cai = new ServerSocket(serverport);
			}
			
			 while(true){
				   dong = cai.accept();
				   //  创建线程
				   try {
						inputStream = new DataInputStream(new BufferedInputStream(dong.getInputStream()));
						outputStream = new DataOutputStream(new BufferedOutputStream(dong.getOutputStream()));
						
				        String info = inputStream.readUTF();
				        String[] msg = info.split("[?]");
				    	func = msg[0];
				    	content = msg[1];
				    	switch(func){
				    	   
			 		    case "reg":
			 		    	reg();
			 		    	break;
			 		    case "log":
			 		    	log();
			 		    	break;
			 		    case "online":
			 		    	online();
			 		    	break;
			 		    case "outline":
			 		    	outline();
			 		    	break;
			 		   case "ip":
					    	ipset();
					    	break;
			 		   case "portfind":
					    	portfind();
					    	break;
			 		    case "othername":
			 		    	othername();
			 		    	break;
			 		    case "addPY":
			 		    	addPY();
			 		    	break;
			 		    case "clearPY":
			 		    	clearPY();
			 		    	break;
			 		    case "chat":
					    	chat();
					    	break;
			 		   case "msg":
					    	msg();
					    	break;
			 		  case "closechat":
					    	closechat();
					    	break;
			 		 case "openchat":
					    	openchat();
					    	break;
			 		case "chatstatus":
				    	    chatstatus();
				    	    break;
			 		case "ready":
			    	        ready();
			    	        break;
			 		case "filedata":
				        filedata();
				        break;
			 		case "tosend":
				        tosendfile();
				        break;
			 		case "add":
				        add();
				        break;
			 		case "setdata":
			 			setdata();
			 			break;
			 		default:
			 		    	break;
			 		 }
				    	} catch (Exception e) {
							e.printStackTrace();
						}
				   }
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
    //  给客户端转发文件
	public void tosendfile(){
		try {
			DataOutputStream ps = new DataOutputStream(dong.getOutputStream());
			DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(content)));
			
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
	         
	         File f = new File(content);
	         if(f.exists())
	        	    f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void filedata(){
		try {
			
		String[] arr = content.split("[_]");
    	username = arr[0];
    	String other = arr[1];
    	String url = arr[2];
    	File file = new File(url);
    	String filename = file.getName();
		
    	String mysql = "select * from user where name = '" + username + "' ";
		String clientip = conn(mysql, 1, "ip");
		int clientport = Integer.parseInt(conn(mysql, 1, "port"));
		
    	String yousql = "select * from user where name = '" + other + "' ";
		String filetoip = conn(yousql, 1, "ip");
		int filetoport = Integer.parseInt(conn(yousql, 1, "port"));
		
		String qsql = "select * from user where name = '" + other + "' ";
	    int q = Integer.parseInt(conn(qsql, 1, "status"));
	    
	    //  保存客户端发过来的文件
	    getsave();
	    String dir = save +username+"^"+other+"/";
        File f = new File(dir);
        if(!f.exists())
        	f.mkdirs();
        String savePath = dir + filename;	
        Socket clsocket = new Socket(clientip, clientport);
		DataInputStream clin = new DataInputStream(new BufferedInputStream(clsocket.getInputStream()));
        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
        DataOutputStream clout = new DataOutputStream(new BufferedOutputStream(clsocket.getOutputStream()));
        clout.writeUTF("file"+"?"+"get"+"_"+url);
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
        clout.close();
        clsocket.close();
        outputStream.writeUTF("sendok");
        outputStream.flush();
		
	//  在线发送文件
	    if(q == 1){
	     String che = "select * from _" + other + " where friends = '" + username + "' ";
       	 String aStr = conn(che, 1, "chating");
        	if(new String(aStr).equals("1")){
        		fun("file" + "?" + "ready" + "_" + filename, filetoip, filetoport);
        		String yes = new String("yes");
        		String no = new String("no");
       	     if(respond.equals(yes)){
       	    	 write("file" + "?" + "send" + "_" + filename + "#" + savePath + "#" + username, filetoip, filetoport);
       	     }else if(respond.equals(no)){
    	    	 File f2 = new File(savePath);
    	    	 if(f2.exists())
    	    		 f2.delete();
    	     }
          	}
       	else if(new String(aStr).equals("0")){
       		 String zsql = "select * from _" + other + " where friends = '" + username + "' ";
    		 String files = conn(zsql, 1, "file");
    		 files  = filename + "@" + files  ;
    		 String zzsql = "update _"+ other +" set file ='"+ files  +"' , readmsg = '0'   where friends = '"+ username  +"' ";
    	     conn(zzsql, 2,"a");
    	     write("file" + "?" + "send" + "_" + filename + "#" + savePath + "#" + username, filetoip, filetoport);
       	 }
	  }else if (q == 0) {
		  //  离线操作
		    String zsql = "select * from _" + other + " where friends = '" + username + "' ";
		    String files = conn(zsql, 1, "file");
		    if(files.equals(new String(""))){
		    	files = filename;
		    	String msql = "update _"+other+" set file = '" + files + "' , readmsg = '0' where friends = '"+ username  +"' ";
				conn(msql, 2,"a");
		    }else{
		    	files  = filename + "@" + files  ;
			    String msql = "update _"+other+" set file = '" + files + "' , readmsg = '0' where friends = '"+ username  +"' ";
				conn(msql, 2,"a");
		    }
			
	}
	    } catch (Exception e) {
            e.printStackTrace();
            
        }
	}
	
    //注册
    public void reg(){
   	 try {
   	    String[] arr = content.split("[_]");
    	username = arr[0];
    	String password = arr[1];
    	String ip = arr[2];
    	String port = arr[3];
    	String msql = "select * from user where name = '"+username+"' ";
    	String aString  = conn(msql,1,"name");
    	String pap = new String(aString);
    	String no = new String("");
    	if(pap.equals(no)){    
    	      String asql = " insert into user (name,password,status,ip,port,addpy)  values ('"+username+"' , '"+password+"' , '0' , '"+ip+"' , '"+port+"' , '') ";
    	      conn(asql,2,"a");
    	      String zsql = "create table _"+ username +" ( friends varchar(50) , msg varchar(7777) , readmsg varchar(2) , chating int(2) ,file varchar(7777) ) ";
    	 	  conn(zsql, 3,"a");
    	      outputStream.writeUTF("ok");
    	      outputStream.flush();
    	      changServer();
        }else{
             outputStream.writeUTF("peat");
             outputStream.flush();
        }
        } catch (Exception e) {
				e.printStackTrace();
			}
    }
    
    //登录验证
    public void log(){
   	 try {
        
      	String[] arr = content.split("[_]");
        username = arr[0];
     	String password = arr[1];
     	
    	String pa = new String(password);
    	String sql = "select * from user where name = '"+username+"' ";
    	String aString  = conn(sql,1,"password");
    	String pap = new String(aString);
    	String no = new String("");
    	
    	if(!pap.equals(no)){
    	      if(pap.equals(pa)){
        		   outputStream.writeUTF("1");
        		   outputStream.flush();
    	     }
    	     else{
    		       outputStream.writeUTF("密码错误");
    		       outputStream.flush();
    	          }
                  }else{
    	          outputStream.writeUTF("用户名不存在");
    	          outputStream.flush();
                 }
       } catch (Exception e) {
			e.printStackTrace();
		}  
    }
    
    public void ipset(){
    	String qsql = "update user set ip = '" + content + "' where name = '"+ username  +"' ";
		   conn(qsql, 2,"a");
    }
    
    public void portfind(){
		try {
    	    String sql = "select * from user where name = '" + content + "' ";
		    String uport =  conn(sql, 1,"port");
		 	outputStream.writeUTF(uport);
		 	outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void chatstatus(){
		try {
	    	String[] arr = content.split("[_]");  
		    username = arr[0];
		    String other = arr[1];
			String adsql = "select * from _"+username+" where friends = '"+ other +"' ";
			String reString = conn(adsql,1,"chating");
			outputStream.writeUTF(reString);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
   		
    }
    
    public void setdata(){
    	String[] arr = content.split("[_]");
    	username = arr[0];
    	String email = arr[1];
    	String phone = arr[2];
    	String url = arr[3];
    	
    	String sql = "update user set email = '" + email +"' , phone ='" + phone + "' where name ='" + username +"' ";
    	conn(sql, 2, "a");
    	if(!url.equals("no")){
    		String[] srr = url.split("[.]");
    		String type = srr[srr.length-1];
    		getsave();
    		String filedir = save + username + "." + type;
    		File file = new File(filedir);
    		file.mkdirs();
    	}
    	try {
			outputStream.writeUTF("ok");
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void ready(){
	    	String[] arr = content.split("[_]");  
		    username = arr[0];
		    String other = arr[1];
		    String asql = "update _"+username+" set msg = '' , readmsg = '1' where friends = '"+ other +"' ";
		    conn(asql, 2, "a");
    }
    
    //上线提示
    public void online(){
    	   
   	       String qsql = "update user set status = '1' where name = '"+ content  +"' ";
		   conn(qsql, 2,"a");
		   changServer();
		   String frisql = "select * from _"+content;
           String str = conn(frisql, 4,"friends");
           if(!str.equals("")){
        	   String[] sta = str.split("[_]");
           for(int i=0;i<sta.length;i++){
        	   String sql = "select * from user where name = '" + sta[i] + "' ";
      	       String otherip = conn(sql, 1, "ip");
      	       int otherport = Integer.parseInt(conn(sql, 1, "port"));
      	       int a = Integer.parseInt(conn(sql, 1, "status"));
      	       if(a == 1){
      	    	 write("add" + "?" + "remove" + "#" + content, otherip, otherport);
      	       }     
           }
           }
    }
    
    //下线提示
    public void outline(){
    	 String qsql = "update _"+ content +" set chating = '0' where chating = '1' ";
		 conn(qsql, 2,"a");
   	     String osql = "update user set status = '0' where name = '"+ content +"' ";
	     conn(osql, 2,"a");
		 changServer();
		 
		 String frisql = "select * from _"+content;
         String str = conn(frisql, 4,"friends");
         
         if(!str.equals("")){
        	 String[] sta = str.split("[_]");
         for(int i=0;i<sta.length;i++){
      	   String sql = "select * from user where name = '" + sta[i] + "' ";
    	       String otherip = conn(sql, 1, "ip");
    	       int otherport = Integer.parseInt(conn(sql, 1, "port"));
    	       int a = Integer.parseInt(conn(sql, 1, "status"));
    	       if(a == 1){
    	    	 write("add" + "?" + "remove" + "#" + content, otherip, otherport);
    	       }     
         }
         }
    }
    
    public void openchat(){
    	try{
    	 String[] arr = content.split("[_]");  
	     username = arr[0];
	     String other = arr[1];
	     
	     String sql = "update _"+username + " set chating = '1' where friends = '"+other+"' " ;
	     conn(sql, 2, "a");
	     String mysql = "select * from _"+ username +" where friends = '"+other+"' ";
	     String mString = conn(mysql, 1, "msg");
	     
	     outputStream.writeUTF(mString);
	     outputStream.flush();
	   	 
	   	 String ysql = "select * from _" + username + " where friends ='"+other+"' ";
		 String files = conn(ysql, 1, "file");
		    if(!new String(files).equals(new String(""))){
		    	
		    	String[] arr3 = files.split("[@]");
		    	String asql = "select * from user where name = '" + username + "' ";
		   	    String ip = conn(asql, 1, "ip");
		   	    String bsql = "select * from user where name = '" + username + "' ";
		   	    int port = Integer.parseInt(conn(bsql, 1, "port"));
		   	    getsave();
		   	    String dir = save + other+"^"+username+"/";
		        File f = new File(dir);
		        if(!f.exists())
		        	f.mkdirs();
		        
		        for(int i= 0;i<arr3.length;i++){
		        	
		        	String savePath = dir + arr3[i];
		        	String filename = new File(savePath).getName();
		        	 fun("file" + "?" + "ready" + "_" + filename, ip, port); 
		    	     if(respond.equals("yes")){
		    	    	 write("file" + "?" + "send" + "_" + arr3[i] + "#" + savePath + "#" + other, ip, port);
		    	     }
		    	     else if(respond.equals("no")){
		    	    	 File f2 = new File(savePath);
		    	    	 if(f2.exists())
		    	    		 f2.delete();
		    	     }
		        }
		    }
		    
	   	String osql = "update _"+username+" set msg = '', file = '' , readmsg = '1' where friends = '"+ other  +"' ";
	   	conn(osql, 2,"a");
	   	 
    } catch (Exception e1) {
		e1.printStackTrace();
  }
}
    
    public void closechat(){
    	 String[] arr = content.split("[_]");  
	     username = arr[0];
	     String other = arr[1];
	     String sql = "update _"+username + " set chating = '0' where friends = '"+other+"' " ;
	     conn(sql, 2, "a");
    }
    
    //获取他人名字
    public void othername(){
    	try {
            String frisql = "select * from _"+content;
            String str = conn(frisql, 5,"readmsg");
            String[] sta = str.split("[_]");
            String a = "";
            if(!str.equals("")){
            for(int i=0;i<sta.length;i++){
            	String sql = "select * from user where name = '" + sta[i].split("[@]")[0] + "' ";
            	a = sta[i].split("[@]")[0] + "#" + conn(sql, 1, "status") + "#" + sta[i].split("[@]")[1] + "_" + a;
            }
            }
			outputStream.writeUTF(a);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void add(){
    	 String[] arr = content.split("[_]");  
	     username = arr[0];
	     String other = arr[1];
	     String res = arr[2];
	     
	     String sql = "select * from user where name = '" + other + "' ";
	     String otherip = conn(sql, 1, "ip");
	     int otherport = Integer.parseInt(conn(sql, 1, "port"));
	     int status = Integer.parseInt(conn(sql, 1, "status"));
	     if(res.equals("yes")){
				String mysql = "insert into _" + username + " ( friends,msg,readmsg,chating,file ) values ( '" + other + "' , '' , '1' , '0' , '' ) " ;
				conn(mysql, 2,"a");
			    String yousql = "insert into _" + other + " ( friends,msg,readmsg,chating,file ) values ( '" + username + "' , '' , '1' , '0' , '' ) " ;
				conn(yousql, 2,"a");
				
				if(status == 1)
			    	write("add" + "?" + "update" + "#" + "caidong", otherip, otherport);
				else if(status == 0){
	                
				}
	     }
	     else if (res.equals("no")) {
			    
		}
    }
    //添加好友
    public void addPY(){
   	 try {
         
		 String[] arr = content.split("[_]");  
	     username = arr[0];
	     String other = arr[1];
      	
   	     String sql = "select * from user where name = '"+ other +"'  ";
		 String a = conn(sql,1,"password");
		 
    	 String qqsql = "select * from _"+ username + " where friends = '" + other +"'  ";
    	 String c = conn(qqsql, 1,"chating");
    	 
			if( !a.equals("")){
				if( c.equals("") ){
					String otherip = conn(sql, 1, "ip");
			    	 int otherport = Integer.parseInt(conn(sql, 1, "port"));
			    	 int status = Integer.parseInt(conn(sql, 1, "status"));
				//  在线和离线请求
					if(status == 1)
						write("add" + "?" + "alert" + "#" + username + "_" + other, otherip, otherport);
					else if(status == 0){
						String mString = conn(sql, 1, "addpy");
						mString = username + "_" + mString;
						String asql = "update user set addpy ='"+ mString  +"'   where name = '"+ other  +"' ";
		     	        conn(asql, 2,"a");
					}
					
				outputStream.writeUTF("ok");
				outputStream.flush();
				}
				else{
						outputStream.writeUTF("你们是好友！");
						outputStream.flush();
				}
				}
			else{
						outputStream.writeUTF("用户不存在！");
						outputStream.flush();
			     }
				} catch (IOException e) {
					e.printStackTrace();
				}
    }
    
    //删除好友
    public void clearPY(){
   	 try {
			String[] arr = content.split("[_]");  
		     username = arr[0];
		     String other = arr[1];
        
   	        String sql = "select * from user where name = '"+ other +"'  ";
			String a = new String(conn(sql,1,"password"));

    	    String qqsql = "select * from  _"+ username + " where friends = '"+ other +"' ";
    	    String c = new String(conn(qqsql, 1,"chating"));
    	   
    	    
			if(!a.equals("")){
				if( !c.equals("") ){
				
				String otherip = conn(sql, 1, "ip");
		       	int otherport = Integer.parseInt(conn(sql, 1, "port"));
		       	int status = Integer.parseInt(conn(sql, 1, "status"));
				String mysql = "delete from _" + username + " where friends = '" + other + "' " ;
				conn(mysql, 2,"a");
				String yousql = "delete from _" + other + " where friends = '" + username + "' " ;
				conn(yousql, 2,"a");
				outputStream.writeUTF("删除成功！");
				outputStream.flush();
				
				//  在线和离线删除
				if(status == 1)
				    write("add" + "?" + "remove" + "#" + username, otherip, otherport);
				else if(status == 0){
					
				}
				}else{
						outputStream.writeUTF("你们不是好友！");
						outputStream.flush();
				}
				}else{
						outputStream.writeUTF("用户不存在！");
						outputStream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    
    //聊天
    public void chat(){
    	String[] arr = content.split("[_]");
    	String other = arr[0];
        username = arr[1];
     	String mString = arr[2];
	    String qsql = "select * from user where name = '" + other + "' ";
	    String q = new String(conn(qsql, 1, "status"));
	    String one = new String("1");
	    String zero = new String("0");
   	    String newmsg = mString;
	    String asql = "select * from user where name = '" + username + "' ";
   	    oneip = conn(asql, 1, "ip");
   	    oneport = Integer.parseInt(conn(asql, 1, "port"));
   	 
   	    String csql = "select * from user where name = '" + other + "' " ;
   	    twoip = conn(csql, 1, "ip");
   	    twoport = Integer.parseInt(conn(csql, 1, "port"));
   	 
        if( q.equals(one) ){  //在线发送消息
	         String che = "select * from _" + other + " where friends = '" + username + "' ";
        	 String aStr = conn(che, 1, "chating");
         	if(new String(aStr).equals("1")){
           	 write("chat"+"?"+mString,oneip, oneport);
   	         write("chat"+"?"+mString,twoip, twoport);
           	}
        	else if(new String(aStr).equals("0")){
        		 write("chat"+"?"+mString,oneip, oneport);
        		 String zsql = "select * from _" + other + " where friends = '" + username + "' ";
     		     String cstr = conn(zsql, 1, "msg");
     		     mString  = mString + "_" + cstr;
     		     String zzsql = "update _"+ other +" set msg ='"+ mString  +"' , readmsg = '0'  where friends = '"+ username  +"' ";
     	         conn(zzsql, 2,"a");
     	         write("chat"+"?"+newmsg,twoip, twoport);
        	 }
        }else if( q.equals(zero) ){  //离线发送消息
        	write("chat"+"?"+mString,oneip, oneport);
	        String zsql = "select * from _" + other + " where friends = '" + username + "' ";
		    String hui = conn(zsql, 1, "msg");
		    mString  = mString + "_" + hui ;
		    String abcsql = "update _"+ other +" set msg ='"+ mString  +"' , readmsg = '0'  where friends = '"+ username  +"' ";
	        conn(abcsql, 2,"a");
		 } 
      }
    
    //  离线处理
    public void msg(){
    	String[] arr = content.split("[_]");
    	username = arr[0];
        
    	String ysql = "select * from _" + username+" where readmsg = '0' ";
	    String stg = conn(ysql, 1, "msg");
	    
	    if(!new String(stg).equals(new String(""))){
	    	
	    	String[] arr2 = stg.split("[_]");
	    	String asql = "select * from user where name = '" + username + "' ";
	   	    String ip = conn(asql, 1, "ip");
	   	    int port = Integer.parseInt(conn(asql, 1, "port"));
	        for(int i= 0;i<arr2.length;i++){
	    	    write("chat"+"?"+arr2[i],ip, port);
	        }   
	    }
	    
		    String files = conn(ysql, 1, "file");
		    if(!new String(files).equals(new String(""))){
		    	String other = conn(ysql, 1, "friends");
		    	
		    	String[] arr3 = files.split("[@]");
		    	String bsql = "select * from user where name = '" + username + "' ";
		   	    String ip = conn(bsql, 1, "ip");
		   	    int port = Integer.parseInt(conn(bsql, 1, "port"));
		   	    getsave();
		   	    String dir = save + other + "^" + username + "/";
		        File f = new File(dir);
		        if(!f.exists())
		        	f.mkdirs();
		        for(int i= 0;i<arr3.length;i++){
		        	String savePath = dir + arr3[i];
		    	    write("file" + "?" + "send" + "_" + arr3[i] + "#" + savePath + "#" + username, ip, port);
		        }
		    }
		   
		    String qqsql = "select * from user where name = '" + username + "' ";
		    String qq = conn(qqsql, 1, "addpy");
		    
		    if(!new String(qq).equals(new String(""))){
		    	
		    	String[] arr4 = qq.split("[_]");
		    	String csql = "select * from user where name = '" + username + "' ";
		   	    String ip = conn(csql, 1, "ip");
		   	    int port = Integer.parseInt(conn(csql, 1, "port"));
		        for(int i= 0;i<arr4.length;i++){
		    	    write("add" + "?" + "alert" + "#" + arr4[i] + "_" + username  ,ip, port);
		        }
		        String msql = "update user set  addpy = '' where name = '" + username + "' ";
		        conn(msql, 2, "a");
		    }
	    
    }
    
    public void write(String msg,String ip,int port){
    	try {
    		Socket newsocket = new Socket(ip, port);
    		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(newsocket.getOutputStream()));
    		out.writeUTF(msg);
    		out.flush();
    		out.close();
    		newsocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public void fun(String msg,String ip,int port){
    	try {
    		Socket newsocket = new Socket(ip, port);
    		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(newsocket.getOutputStream()));
    		DataInputStream in = new DataInputStream(new BufferedInputStream(newsocket.getInputStream()));
    		out.writeUTF(msg);
    		out.flush();
    		respond = in.readUTF();
    		out.close();
    		newsocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
	//连接数据库
    public String conn(String sql,int a ,String str){
 	    Connection con = null;
       	Statement stmt = null;
       	try {
       		String DB = "jdbc:mysql://"+serverip+":3306/qq?characterEncoding=utf8";
 	    //注册驱动
			Class.forName(JDBC);
			//打开连接
			con = DriverManager.getConnection(DB,USER,PASS);
			//默认事务不自动提交
			con.setAutoCommit(false);
			//执行查询
			stmt = con.createStatement();
			switch(a){
			    case 1:
			    	ResultSet rs = stmt.executeQuery(sql);
			    	con.commit();
					String result = "";
					while(rs.next()){
						result = rs.getString(str);
					}
					 rs.close();
					 stmt.close();
			         con.close();
			         if(!stmt.isClosed())
			             stmt.close();
			    		if(!con.isClosed())
			             con.close();
					 return result;
			    case 2:
			    	stmt.executeUpdate(sql);
					con.commit();
			    	stmt.close();
			        con.close();
			        if(!stmt.isClosed())
			            stmt.close();
			   		if(!con.isClosed())
			            con.close();
			    	return str;
			    case 3:
			    	stmt.execute(sql);
					con.commit();
			    	stmt.close();
			        con.close();
			        if(!stmt.isClosed())
			            stmt.close();
			   		if(!con.isClosed())
			            con.close();
			   		return str;
			    case 4:
			    	ResultSet aa = stmt.executeQuery(sql);
			    	con.commit();
					String bb = "";
					while(aa.next()) {    
					    bb =  aa.getString(str) + "_" + bb;   
					}
					 aa.close();
					 stmt.close();
			         con.close();
			         if(!stmt.isClosed())
			             stmt.close();
			    		if(!con.isClosed())
			             con.close();
					 return bb;
			    case 5:
			    	ResultSet al = stmt.executeQuery(sql);
			    	con.commit();
					String bl = "";
					while(al.next()) {    
					    bl =  al.getString("friends") + "@" + al.getString(str) + "_" + bl ;   
					}
					 al.close();
					 stmt.close();
			         con.close();
			         if(!stmt.isClosed())
			             stmt.close();
			    		if(!con.isClosed())
			             con.close();
					 return bl;
			}

			 // 完成后关闭
		if(!stmt.isClosed())
         stmt.close();
		if(!con.isClosed())
         con.close();
    }catch(SQLException se){
        // 处理 JDBC 错误
        se.printStackTrace();
    }catch(Exception e){
        // 处理 Class.forName 错误
        e.printStackTrace();
    }
    return "";
   }

  //  实时更新server
    public void changServer(){
    	data.change();
    }

 // 获取txt内容
    
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
