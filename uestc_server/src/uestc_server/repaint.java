package uestc_server;

import javax.swing.*;

public class repaint extends Thread{
    
	JPanel jp;
	server s;
	public repaint(JPanel a,server b){
		jp = a;
		s = b;
	}
	
	public void run(){
		   jp.removeAll();
    	   s.redata();
    	   jp.repaint();
	}
}
