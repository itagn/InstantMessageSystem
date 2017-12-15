package uestc_client;

import javax.swing.*;

public class repaint extends Thread{
    
	JPanel jp;
	client c;
	public repaint(JPanel a,client b){
		jp = a;
		c = b;
	}
	
	public void run(){
		   jp.removeAll();
    	   c.relist();
    	   jp.repaint();
	}
}
