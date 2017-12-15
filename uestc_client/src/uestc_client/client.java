package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.awt.*;

public class client{
	   //UI的组件
	   public static JFrame one;
	   private JLabel author_one;
       private JButton btn1;
       private JButton btn2;
       private JButton btt1;
       private JButton btt2;
	   private JPanel div;
	   private JTextField name;
	   private JPasswordField pass;
	   private JPasswordField repass;
	   private JLabel p1;
	   private JLabel p2;
	   private JLabel p3;
	   private JLabel info;
	   private JPanel ico;
	   //listUI的组件
	   private JFrame two;
	   private JLabel author_two;
       public static JPanel span;
       private JTextField findText;
       private JButton find;
       private JButton clear;
       private JLabel title;
       public static JButton[] peo ;
     //chatUI的组件
       private JFrame three;
       private JLabel author_three;
       private JPanel talk;
       private JButton send;
       private JTextField say;
       private JLabel txtname;
       private JButton selecttxt;
       private JButton sendtxt;
       public static JFileChooser select;
       public static JLabel[] txt;
       public static JTextArea[] jTextAreas ;
       public static File f = null;
       
       // 服务器IP和端口
       public static String serverip;
       public static final int serverport = 7777;
       public static String save;
       // 客户端IP和端口
       public static String ip;
       public static int port;
       
       public static String msgout;
       public static int[] o;
   	   public static int[] p;
       //全局变量
       public static String username;
       public static String password;
       public static String repassword;
       public static String chatpeo;
       public static ServerSocket caidong = null;
       public static Socket clientsocket;
       public static String respond = "";
       public static int index = 0;
       public static String[] oth;
       public static ServerSocket filesocket;
       
       public static int inter = 100;
       public static client list;
       
       public static void main(String[] args){
//     	  SwingUtilities.invokeLater()方法使事件派发线程上的可运行对象排队。
    	   javax.swing.SwingUtilities.invokeLater(new Runnable() {
          		public void run() {
          			new client("index");
          		}
          	});
       }
       
       public client(String method){
    	   try {
   			getip();
   		} catch (Exception e1) {
   			e1.printStackTrace();
   		}
    	   switch (method) {
		      case "index":
		    	  UI();
			     break;
		      case "list":
		    	  listUI();
		    	  break;
		      case "chat":
		    	  chatUI();
		    	  break;
		      default:
			     break;
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
     
       
	//  主界面
	public void UI(){
		
		one = new JFrame();
	    one.setTitle("登录注册");
	    one.setSize(300,280);
	    one.setLocationRelativeTo(null);
	    one.setResizable(false);
	     
	  {    
		  div = new JPanel();
		  one.add(div);
		  div.setBackground(Color.white);
		  div.setLayout(null);//清空布局管理器
		  {
			  author_one = new JLabel("作者：uestc-2013计算机-蔡东 ©");
			  author_one.setBounds(10,225,200,20);
			  div.add(author_one);
		  }
		  {
			  ico = new JPanel() {
		    private static final long serialVersionUID = 4195061240193696950L;
			protected void paintComponent(Graphics g) {  
	               ImageIcon icon = new ImageIcon("img/ico.png");  
	               Image img = icon.getImage();  
	               g.drawImage(img, 0, 0, 30, 28, icon.getImageObserver());
	           }  
	       };
	          ico.setBounds(230, 10, 30, 30);
	          div.add(ico);
		  }
		  {
			btn1 = new JButton();
			div.add(btn1);
			btn1.setText("登录");
			btn1.setBackground(Color.lightGray);
			btn1.setBounds(90,50, 100, 60);
	        btn1.addActionListener(new ActionListener() {
	        	    
	            public void actionPerformed(ActionEvent e) {
	            		log();
	            }
	        });
		  }    
		  {
				btn2 = new JButton();
				div.add(btn2);
				btn2.setText("注册");
				btn2.setBackground(Color.lightGray);
				btn2.setBounds(90, 150, 100, 60);
				btn2.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	 reg();
			            }
			        });
		  }
	  }
	  one.addWindowListener(new WindowAdapter(){  
	       public void windowClosing(WindowEvent e) { 
	    	    	System.exit(0);
	    	    }
	       });
	  one.setVisible(true);	
	}
	 //画注册框
    public void reg(){
           
    	   div.removeAll();
    	{
    		  author_one = new JLabel("作者：uestc-2013计算机-蔡东 ©");
 			  author_one.setBounds(10,225,200,20);
 			  div.add(author_one);
 		}
    	
    	{
			  ico = new JPanel() {
			private static final long serialVersionUID = -7634344163773598089L;
			protected void paintComponent(Graphics g) {  
	               ImageIcon icon = new ImageIcon("img/ico.png");  
	               Image img = icon.getImage();  
	               g.drawImage(img, 0, 0, 30, 28, icon.getImageObserver());
	           }  
	       };
	          ico.setBounds(230, 10, 30, 30);
	          div.add(ico);
		  }
    	
    	{
				p1 = new JLabel();
				p1.setText("用户名");
				div.add(p1);
				p1.setBounds(50,35,50,50);
			}
    	
    	
			{
				name = new JTextField();
			    name.setText("");
			    name.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
			    div.add(name);
			    name.setBounds(120, 50, 100, 20);
			}
			
			
			{
				p2 = new JLabel();
				p2.setText("密码");
				div.add(p2);
				p2.setBounds(55,85,50,50);
			}
			
			
			{
				pass = new JPasswordField();
			    pass.setText("");
			    div.add(pass);
			    pass.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
			    pass.setBounds(120, 100, 100, 20);
			}
			
			
			{
				p3 = new JLabel();
				p3.setText("确认密码");
				div.add(p3);
				p3.setBounds(45,135,80,50);
			}
			
			{
				repass = new JPasswordField();
			    repass.setText("");
			    div.add(repass);
			    repass.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
			    repass.setBounds(120, 150, 100, 20);
			}
			
			{
				btt1 = new JButton();
			    btt1.setText("注册");
			    btt1.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
			    div.add(btt1);
			    btt1.setBackground(Color.lightGray);
			    btt1.setBounds(70, 200, 70, 20);
			    btt1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
	                	//注册操作
		            	username = name.getText();
		            	String pa = new String(pass.getPassword());
		            	password = pa.toString();
		            	String repa = new String(repass.getPassword());
		            	repassword = repa.toString();
		            	
		            	try {
		            		InetAddress[] inetAdds = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName()); 
		  					ip = inetAdds[0].getHostAddress();
		  				 } catch (UnknownHostException e1) {
		  					e1.printStackTrace();
		  				}
		          
		         if(!username.equals("")){
		            if(!password.equals("")){
		            	if(pa.equals(repa)){
		            		port =  ((int)Math.floor(Math.random()*5) + 5)*1000 + (int)Math.floor(Math.random()*1000) ;
		            		
					    fun("reg"+"?"+username+"_"+password+"_"+ip+"_"+ port);
					    String ok = new String("ok");
					    String peat = new String("peat");
					    if(new String(respond).equals(ok) ){
					    
					    
		                name.setText("");
		       	        pass.setText("");
		       	        repass.setText("");
		       	      // 个人的文件夹
		       	        getsave();
		       	        String dirname = save + username;
		       	        File d = new File(dirname);
		       	      // 现在创建目录
		       	        d.mkdirs();
		       	        if(!d.exists()){
		       	        	d.mkdirs();
		       	        }
		       	        new alert("注册成功！");
		       	      
					    }
					    else if( new String(respond).equals(peat) ) {
					    	new alert("用户名被注册");
						}
		            	}
		            	else{
		            		new alert("密码不同");
		            	}
		            	}
		            	else{
		            		new alert("密码为空");
		            	}
		            	}
		                else{
		                	new alert("用户名为空");
		            	}
		            	}
		        });
			 }
			
			
			{
				btt2 = new JButton();
			    btt2.setText("登录");
			    div.add(btt2);
			    btt2.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
			    btt2.setBackground(Color.lightGray);
			    btt2.setBounds(150, 200, 70, 20);
			    btt2.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
	                	 log();
		            }
		        });
			}
			  div.updateUI();
			  div.repaint();
    }

 //画登录框
 public void log(){

    div.removeAll();
     {
    	 author_one = new JLabel("作者：uestc-2013计算机-蔡东 ©");
		  author_one.setBounds(10,225,200,20);
		  div.add(author_one);
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
         ico.setBounds(230, 10, 30, 30);
         div.add(ico);
	  }
     
 {
		p1 = new JLabel();
		p1.setText("用户名");
		div.add(p1);
		p1.setBounds(50,35,50,50);
	}
	{
		name = new JTextField();
	    name.setText("");
	    div.add(name);
	    name.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
	    name.setBounds(120, 50, 100, 20);
	}
	{
		p2 = new JLabel();
		p2.setText("密码");
		div.add(p2);
		p2.setBounds(55,85,50,50);
	}
	{
		pass = new JPasswordField();
	    pass.setText("");
	    pass.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
	    div.add(pass);
	    pass.setBounds(120, 100, 100, 20);
	}
	{
	   info =new JLabel("");
	   div.add(info);
	   info.setBounds(120,120,100,50); 
	}
	{
		btt1 = new JButton();
	    btt1.setText("注册");
	    div.add(btt1);
	    btt1.setBackground(Color.lightGray);
	    btt1.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
	    btt1.setBounds(70, 200, 70, 20);
	    btt1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
      	        reg();
         }
     });
	}
	{
		btt2 = new JButton();
	    btt2.setText("登录");
	    btt2.setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
	    div.add(btt2);
	    btt2.setBackground(Color.lightGray);
	    btt2.setBounds(150, 200, 70, 20);
	    btt2.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
      	          //登录操作
        	 try {
        	 username = name.getText();
         	String pa = new String(pass.getPassword());
         	password = pa.toString();
         	String fail1 = new String("用户名不存在");
         	String fail2 = new String("密码错误");
         	String success = new String("1");
			
	         if(!username.equals("")){
		        if(!password.equals("")){
		           fun("log"+"?"+username+"_"+password);
		           String res = new String(respond);
		           info.setText("");
         	if(fail1.equals(res)){
                 info.setText(respond);
         	}else if(fail2.equals(res)){
         		 info.setText(respond);
         	}
         	else if(success.equals(respond)){
         		
         		try {
         			InetAddress[] inetAdds = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName()); 
  					ip = inetAdds[0].getHostAddress();
  				 } catch (UnknownHostException e1) {
  					e1.printStackTrace();
  				}
         		    socket("ip"+"?"+ip);
         		    fun("portfind"+"?"+ username);
         		    port = Integer.parseInt(respond);
         		    socket("online"+"?"+ username);
         		    name.setText("");
	         		pass.setText("");
	         		if( !isPortAvailable(port) ){
	        			new alert("重复登录！");
	        	  }else{
	        		  one.dispose();
	         		  list = new client("list");
	         		   
	         		 if(caidong == null)
	         			caidong = new ServerSocket(port);
	         		 clientThread b = new clientThread(caidong,username,port,list);
	         		 b.start();
	        	  }
         	}
		    }else{
		    	 info.setText("密码为空");
	             }
	         }else{
	        	 info.setText("用户名为空");
	            	}
        	} catch (Exception e1) {
					e1.printStackTrace();
			}
        	 
         }
     });
	}
	
	  div.updateUI();
	  div.repaint();
}
 
 //好友界面
 public void listUI() {
	      
	   two = new JFrame();
	   two.setTitle("好友列表");
	   two.setSize(600,600);
	   two.setLocationRelativeTo(null);
	   two.setResizable(false);
	   
	   span = new JPanel();
	   two.add(span);
	   span.setBackground(Color.white);
	   span.setLayout(null);
	   
	   relist();
	   
	   two.addFocusListener(new FocusListener(){ 
		    public void focusGained(FocusEvent e){
		    	repaint re = new repaint(span,list);
		    	re.start();
		    }
			public void focusLost(FocusEvent e) {
				
			} 
		  });
		  
		  two.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
			}
		});
	  two.addWindowListener(new WindowAdapter(){  
		       public void windowClosing(WindowEvent e) { 
		    	    	socket("outline"+"?"+username); 
		    	    	System.exit(0); 
		    	    }
		       });
	  two.setVisible(true);
	  socket("msg"+"?"+username);
 }
 
 // 重绘
 public void relist(){
	 
     {
    	author_two = new JLabel("作者：uestc-2013计算机-蔡东 ©");
		author_two.setBounds(10,5,200,20);
		span.add(author_two);
	 }
	  {
		  findText = new JTextField("");
		  span.add(findText);
		  findText.setBounds(20,520,400,30);
	  }
	  {
		  title = new JLabel();
		  span.add(title);
		  title.setText("欢迎  "+username);
		  title.setBounds(280, 5, 100, 20);
	  }
	  createPY();
	 {
		  find = new JButton("添加");
		  span.add(find);
		  find.setBounds(440,520,60,30);
		  find.setBackground(Color.lightGray);
		  find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPY();
			}
			
		});
	  }
	{
		  clear = new JButton("删除");
		  span.add(clear);
		  clear.setBackground(Color.lightGray);
		  clear.setBounds(510,520,60,30);
		  clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearPY();
			}
		});
	  }
 }
//聊天界面
 public void chatUI(){
	   index = index + 1;
		//聊天内容y轴坐标
	    o = new int[index];
		p = new int[index];
	    for(int i = 0;i<index;i++){
	    	o[i] = 1;
	    	p[i] = 1;
	    }
	  three = new JFrame();
	  three.setTitle("聊天");
	  three.setSize(600,600);
	  three.setLocationRelativeTo(null);
	  three.setResizable(false);
      {
    	  talk = new JPanel();
    	  three.add(talk);
		  talk.setLayout(null);
      }
      {
    	author_three = new JLabel("作者：uestc-2013计算机-蔡东 ©");
  		author_three.setBounds(10,545,200,20);
  		talk.add(author_three);
  	  }
		  
		  {
		  jTextAreas = new JTextArea[index];
		  for(int i=0;i<index;i++){
			  jTextAreas[i] = new JTextArea();
		  }
	      jTextAreas[index-1].setBackground(Color.white);
	      jTextAreas[index-1].setBounds(50, 50, 500, 400);
		  talk.add(jTextAreas[index-1]);
		  } 
		  
		  {
		  oth = new String[index];
		  txt = new JLabel[index];
		  for(int i=0;i<index;i++){
			  oth[i] = "";
			}
		  oth[index-1] = chatpeo;
		  for(int i=0;i<index;i++){
			  txt[i] = new JLabel(username +" 正在与 "+ oth[i] +" 聊天");
		  }
		  txt[index-1].setBounds(180, 20, 140, 20);
		  jTextAreas[index-1].add(txt[index-1]);
          }
			{
			    say = new JTextField();
				say.setBackground(Color.white);
				say.setBounds(50, 480, 400, 50);
				talk.add(say);
		    }
			
			{
				txtname = new JLabel();
				talk.add(txtname);
				txtname.setText("传文件");
				txtname.setBounds(180, 15, 100, 20);
			}
		//  选择文件按钮
			{
				selecttxt = new JButton("选择文件");
				selecttxt.setBounds(240, 12, 100, 25);
				selecttxt.setBackground(Color.lightGray);
				talk.add(selecttxt);
				selecttxt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//   选择文件
						
					    select = new JFileChooser();
					    select.setCurrentDirectory(new File("D:/"));
						inter = select.showOpenDialog(null);						
						 if( inter == JFileChooser.APPROVE_OPTION){
				             //获得该文件    
				            f=select.getSelectedFile();
				            if(f != null){
				            	selecttxt.setText("已选择");
				            }
				         }
					}
				});
			}
		//  传文件按钮
			{
					sendtxt = new JButton("发送文件");
					sendtxt.setBounds(370, 12, 100, 25);
					sendtxt.setBackground(Color.lightGray);
					talk.add(sendtxt);
				    sendtxt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//  传文件
						if( inter == JFileChooser.APPROVE_OPTION ){
								
								fun("filedata"+"?"+username+"_"+chatpeo+"_"+f.getAbsolutePath());
							    if(new String(respond).equals("sendok")){
							    	new alert("发送成功");
							    }
						        inter = 100;
								selecttxt.setText("选择文件");
					}else {
							new alert("选择文件");
						}
					}
		});
	}
			
			 //发送按钮
			{
				send = new JButton("发送");
				send.setBounds(460, 480, 100, 50);
				talk.add(send);
				send.setBackground(Color.lightGray);
				send.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						msgout = say.getText();
						say.setText("");
						// 加密
							try {
								des sas = new des();
								String msgmsg = sas.jiami(msgout);
								
								socket("chat" + "?" + chatpeo + "_" + username + "_" + username + " : " + "@" +  msgmsg);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							

					}
				});
      }
     three.addWindowListener(new WindowAdapter(){  
		       public void windowClosing(WindowEvent e) {  
		    	   socket("closechat"+"?"+username+"_"+chatpeo); 
		    	    }
		       });
    three.setVisible(true);
    
    fun("openchat"+"?"+username+"_"+chatpeo);
    
    if(!new String(respond).equals("") ){
		try {
			String[] aString = respond.split("[_]");
		for(int i=aString.length-1;i>=0;i--){
			String[] arr1 = aString[i].split("[@]");
	        String a = arr1[0];
	     	String b = arr1[1];
	        des ss = new des();
			String msg = a + ss.jiemi(b);
			yourmsg(msg);
		}
		} catch (Exception e1) {
			e1.printStackTrace();
      }
    }
 }

 public void add(){
	 createPY();
	 span.validate();
 }
 
 public void remove(){
	 span.removeAll();
	 relist();
	 span.updateUI();
	 span.repaint();
 }
 
 public void mymsg(String str){
	 String[] arr = new String[index];
	 for(int i=0;i<index;i++){
		 arr[i] = txt[i].getText().split(" ")[0];
		 if(new String(arr[i]).equals(new String(username)) ||  new String(arr[i]).equals(new String(chatpeo))){
			   		JLabel me = new JLabel(str);
			   		jTextAreas[i].add(me);
			   		me.setBounds(270,20+30*o[i],250,20);
			   		o[i]++;
		 }
	 }
  }
    
    public void yourmsg(String str){
    	String[] arr = new String[index];
   	 for(int i=0;i<index;i++){
   		 arr[i] = txt[i].getText().split(" ")[0];
   		 if(new String(arr[i]).equals(new String(username)) || new String(arr[i]).equals(new String(chatpeo)) ){
   	   		 JLabel your = new JLabel(str);
   	   		 jTextAreas[i].add(your);
   	   		 your.setBounds(20,20+30*p[i],250,20);
   	   	     p[i]++;
   		 }
   	 }
    }
 
    //朋友按钮显示
	 public void  createPY(){
		 fun("othername"+"?"+username);
		 String nu = new String("");
		 String re = new String(respond);
	  if(!re.equals(nu)){
		 String[] arr = respond.split("[_]");
		 
		 String[] ggname = new String[arr.length];
		 String[] status = new String[arr.length];
		 String[] ggread = new String[arr.length];
    	 
    	 peo= new JButton[arr.length];
    	 for(int i=0;i<arr.length;i++){
            ggname[i] = arr[i].split("[#]")[0];
            status[i] = arr[i].split("[#]")[1];
            ggread[i] = arr[i].split("[#]")[2];
    	 }
	            for(int i=0;i<arr.length;i++){
	            	peo[i] = new JButton();
		             int k  = i;
				     peo[i].setBounds(45,55*(i+1), 500, 50);
				     span.add(peo[i]);
				     //  消息人提醒
				     if(new String(status[i]).equals(new String("1") )){
				    	  peo[k].setText(ggname[k]+" 在线");
				     }else if(new String(status[i]).equals(new String("0") )){
						  peo[k].setText(ggname[k]+ " 离线");
					 }
				     
				     if( new String(ggread[i]).equals(new String("0") ) ){
				    	 peo[i].setBackground(Color.green);
				     }
				     else if( new String(ggread[i]).equals(new String("1") ) ){
				    	 peo[i].setBackground(Color.lightGray);
				     }
				     
				  peo[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							chatpeo = ggname[k];
							new client("chat");
	                        peo[k].setBackground(Color.lightGray);
						} 
				  });	  
	            }
	        } 
	}
	 
	public void setcolor(String id){
	  for(int i=0;i<peo.length;i++){
		if(new String(peo[i].getText().split("")[0]).equals(new String(id))){
			peo[i].setBackground(Color.green);
		}
	  }
	}

 //添加好友按钮
 public void addPY(){
 	String userd = findText.getText();
 	if(!userd.equals(new String(""))){
 	if( !userd.equals(username) ){
 	fun("addPY"+"?"+username+"_"+userd);
 	String success = new String("ok");
 	String fail1 = new String("你们是好友！");
 	String fail2 = new String("用户不存在！");
 	String res = new String(respond);
		    if(success.equals(res)){
		    	new alert("等待对方确认请求！");
		    	findText.setText("");
		    }else if (fail1.equals(res)) {
		    	findText.setText(respond);
			}else if (fail2.equals(res)) {
				findText.setText(respond);
			}
		}else {
			findText.setText("不能添加自己！");
		}
 	}else{
 		findText.setText("请输入名字！");
 	}
 }
 
 //删除好友按钮
 public void clearPY(){
 	String userd = findText.getText();
 	if(!userd.equals(new String(""))){
 	if(!userd.equals(username)){
 	fun("clearPY"+"?"+username+"_"+userd);
 	String success = new String("删除成功！");
 	String fail1 = new String("你们不是好友！");
 	String fail2 = new String("用户不存在！");
 	String res = new String(respond);
 	
 		if(success.equals(res)){
 			   span.removeAll();
 			   relist();
 			   createPY();
 			   span.updateUI();
			   span.repaint();
 			    new alert("删除成功！");
		    }else if (fail1.equals(res)) {
		    	findText.setText(respond);
			}else if (fail2.equals(res)) {
				findText.setText(respond);
			}
		}else {
			    findText.setText("不能删除自己！");
		}
        }else{
		findText.setText("请输入名字！");
	}
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
 
	//个人数据表
	    public void createFriend(){
	  	    try {
				socket("createFriend"+"?"+username);
			} catch (Exception e) {
				e.printStackTrace();
			}
	  	 }
	 
	 public String getres(){
		 return respond;
	 }
	 
	 //  socket
	 public void socket(String msg){
				try {
					clientsocket = new Socket(serverip, serverport);
					DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientsocket.getOutputStream()));
					out.writeUTF(msg);
					out.flush();
				} catch (UnknownHostException e) {
					e.printStackTrace();
					new alert("通信连接出现错误");
				} catch (IOException e) {
					e.printStackTrace();
					new alert("服务器未启动或者网络连接超时");
				}
	 }
	 
	 // socket
	 public void fun(String msg){
				try {
						clientsocket = new Socket(serverip, serverport);
						DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientsocket.getOutputStream()));
						out.writeUTF(msg);
						out.flush();
						DataInputStream in = new DataInputStream(new BufferedInputStream(clientsocket.getInputStream()));
						respond = in.readUTF();
					} catch (UnknownHostException e) {
						e.printStackTrace();
						new alert("通信连接出现错误");
					} catch (IOException e) {
						e.printStackTrace();
						new alert("服务器未启动或者网络连接超时");
					}
	 }
	 
}