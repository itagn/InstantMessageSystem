package uestc_client;

/*
 *   uestc --> caidong -->  2017-1-19
 *   作者：蔡东-UESTC-2013-计算机
*/

import java.awt.*;
import javax.swing.*;

//弹窗提醒
public class alert{
	//alertUI组件
    private JFrame four;
    private JPanel alert;
    private JLabel alertinfo;
    
    public alert(String msg){
           alertUI(msg);	
    }
    //提醒alert
    public void alertUI(String msg){
       int len = msg.length();
   	   four = new JFrame("提示");
   	   four.setSize(22*len,150);
   	   four.setLocationRelativeTo(null);
   	   four.setResizable(false);
   	   {
   		   alert = new JPanel();
   		   four.add(alert);
   		   alert.setBackground(Color.white);
   		   alert.setLayout(null);
   		   {
   			   alertinfo = new JLabel();
   			   alert.add(alertinfo);
   			   alertinfo.setText(msg);
   			   alertinfo.setForeground(Color.black);
   			   alertinfo.setFont(null);
   			   if(len>5)
   			   alertinfo.setBounds(5*len,30,25*len,40);
   			   else
   				alertinfo.setBounds(45,30,100,40);
   		   }
   	   }
   	   four.setVisible(true);
    }
}
