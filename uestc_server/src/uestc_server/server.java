package uestc_server;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import java.sql.*;

public class server {
	//JDBC 驱动名及数据库URL
	public static final String JDBC = "com.mysql.jdbc.Driver";
	//数据库的用户名与密码
	public static final String USER = "root";
	public static final String PASS = "1234";
	//服务器
	private JFrame jf;
	private JPanel jp;
	private JButton jb;
	private JLabel author;
	private JPanel ico;
	//服务器
	private JFrame datajf;
	private JPanel datajp;
	private JLabel dataserver;
	private JLabel ipname;
	private JLabel portname;
	private JLabel usernum;
	private JLabel online;
	private JLabel outline;
	
	public static String serverip;
	public static final int serverport = 7777;
	
	public static String func;
	public static String content; 
	public static String[] name;
	public static String[] onname;
	public static String[] outname;
	
	public static server adc;
	public static String save;
	// 服务器端口
    
	public static void main(String[] args) {
		  new server("index");
	}
	
	public server(String method){
		try {
			InetAddress[] inetAdds = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			serverip = inetAdds[0].getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} 
		switch (method) {
		case "index":
			UI();
			break;
        case "data":
			dataUI();
			break;
		default:
			break;
		}
    }
	
	public void UI(){
    	  jf = new JFrame("服务器");
    	  jf.setSize(300,280);
 	      jf.setResizable(false);
 	      jf.setLocationRelativeTo(null);
 	      {
 	    	  jp = new JPanel();
 	    	  jp.setLayout(null);
 	    	  jp.setBackground(Color.white);
 	    	  jf.add(jp);
 	    	  {   
 	    		 {
 	    		  author = new JLabel("作者：uestc-2013计算机-蔡东 ©");
 	  			  author.setBounds(10,225,200,20);
 	  			  author.setFont(null);
 	  			  author.setForeground(Color.black);
 	  			  jp.add(author);
 	  		     }
 	    		 
 	    		 {
 	    		ico = new JPanel() {
 	   			private static final long serialVersionUID = -5535845385677821269L;
 	   		protected void paintComponent(Graphics g) {  
 	                 ImageIcon icon = new ImageIcon("img/ico.png"); 
 	                 Image img = icon.getImage();  
 	                 g.drawImage(img, 0, 0, 30, 28, icon.getImageObserver());
 	             }  
 	         };
 	            ico.setBounds(230, 30, 30, 30);
 	            jp.add(ico);
 	    	  }
 	    	  
 	    		  jb = new JButton("启动");
 	    		  jb.setBackground(Color.lightGray);
 			      jb.setBounds(100,100, 80, 50);
 			      jp.add(jb);
 			      jb.addActionListener(new ActionListener() {
 		            public void actionPerformed(ActionEvent e) {
 		            	//多线程操作
 		            	if( !isPortAvailable(serverport) ){
 		   				new alert("服务器已经启动！不要重复！");
 		   			    }
 		            	else {
 		            		jf.dispose();
 		            		adc = new server("data");
 	 		            	serverThread it = new serverThread(serverip,adc);
 	 		       		    it.start();
						}
 					}
 			    });
 	    	  }
 	      }
 	     jf.addWindowListener(new WindowAdapter(){
		       public void windowClosing(WindowEvent e) {
		    	   System.exit(0);
		         }
		       });
 	     jf.setVisible(true);
      }
	
	public void dataUI(){
		
		datajf = new JFrame();
		datajf.setTitle("服务器");
  	    datajf.setSize(600,300);
	    datajf.setResizable(false);
	    datajf.setLocationRelativeTo(null);
	    
	    datajp = new JPanel();
  	    datajp.setLayout(null);
  	    datajp.setBackground(Color.white);
  	    datajf.add(datajp);
  	    
	    redata();
	    
  	datajf.addFocusListener(new FocusListener(){ 
	    public void focusGained(FocusEvent e){
	    	repaint re = new repaint(datajp,adc);
	    	re.start();
	    }
		public void focusLost(FocusEvent e) {
			
		} 
	  });
	    
	    datajf.addWindowListener(new WindowAdapter(){  
		       public void windowClosing(WindowEvent e) { 
		    	   String sql = "update user set status = '0' where name != '' ";
		    	   conn(sql, 2, "a");
		    	   System.exit(0);
		    	    }
		       }); 
	    datajf.setVisible(true);
	    //  创建保存的文件夹
	    
	    getsave();
        File f = new File(save);
        if(!f.exists())
        	f.mkdirs();
	}
	
	
	public void redata(){
  
	    	  {
	    		  author = new JLabel("作者：uestc-2013计算机-蔡东 ©");
	  			  author.setBounds(10,235,200,20);
	  			  author.setFont(null);
	  			  author.setForeground(Color.black);
	  			  datajp.add(author);
	  		  }
	    	  {
	 	    		ico = new JPanel() {
	 	   			private static final long serialVersionUID = -5535845385677821269L;
	 	   		protected void paintComponent(Graphics g) {  
	 	                 ImageIcon icon = new ImageIcon("img/ico.png"); 
	 	                 Image img = icon.getImage();  
	 	                 g.drawImage(img, 0, 0, 30, 28, icon.getImageObserver());
	 	             }  
	 	         };
	 	            ico.setBounds(530, 30, 30, 30);
	 	            datajp.add(ico);
	 	    	  }
	    	  {
	    		  dataserver = new JLabel("服务器资料");
	    		  dataserver.setBounds(260, 20, 200, 20);
	    		  dataserver.setFont(null);
	    		  dataserver.setForeground(Color.black);
	    		  datajp.add(dataserver);
	    	  }

	    	  {
	    		  ipname = new JLabel("服务器ip："+serverip);
	    		  ipname.setBounds(100, 50, 200, 20);
	    		  ipname.setFont(null);
	    		  ipname.setForeground(Color.black);
	    		  datajp.add(ipname);
	    	  }
	    	  {
	    		  portname = new JLabel("服务器端口："+serverport);
	    		  portname.setBounds(320, 50, 200, 20);
	    		  portname.setFont(null);
	    		  portname.setForeground(Color.black);
	    		  datajp.add(portname);
	    	  }
	    	  String sql = "select * from user ";
	          name = conn(sql, 4,"name").split("_");
	          if(!conn(sql, 4,"name").equals(new String("")))
	        	  usernum = new JLabel("用户人数："+name.length);
	          else
	        	  usernum = new JLabel("用户人数：0");
	    		  
	    		  usernum.setBounds(100,80, 200, 20);
	    		  usernum.setFont(null);
	    		  usernum.setForeground(Color.black);
	    		  datajp.add(usernum);
	    	  {
	    	  String onsql = "select * from user where status = '1' ";
	          onname = conn(onsql, 4,"name").split("[_]");
	          if(!conn(onsql, 4,"name").equals(new String("")))
	              online = new JLabel("在线人数："+onname.length);
	          else
	        	  online = new JLabel("在线人数：0");
	    		  online.setBounds(100,110, 200, 20);
	    		  online.setFont(null);
	    		  online.setForeground(Color.black);
	    		  datajp.add(online);
	    	  }
	    	  String outsql = "select * from user where status = '0' ";
	          outname = conn(outsql, 4,"name").split("_");
	          if(!conn(outsql, 4,"name").equals(new String("")))
	        	  outline = new JLabel("离线人数："+outname.length);
	          else
	        	  outline = new JLabel("离线人数：0");
	          
	    	  {
	    		  outline.setBounds(320,110, 200, 20);
	    		  outline.setFont(null);
	    		  outline.setForeground(Color.black);
	    		  datajp.add(outline);
	    	  }
	    }
	
	public void change(){
		datajp.removeAll();
		redata();
		datajp.updateUI();
		datajp.repaint();
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
					    bb = bb + aa.getString(str)+ "_" ;   
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
    
    // 检查端口是否被占用
    private void bindPort(String host, int port) throws Exception { 
        Socket s = new Socket(); 
        s.bind(new InetSocketAddress(host, port)); 
        s.close(); 
    } 
    @SuppressWarnings({ "unused", "resource" })
	public boolean isPortAvailable(int port) { 
        Socket s = new Socket(); 
        try { 
            bindPort("0.0.0.0", port); 
            bindPort(InetAddress.getLocalHost().getHostAddress(), port); 
            return true; 
        } catch (Exception e) { 
            return false; 
        } 
    }
}