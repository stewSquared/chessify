import java.util.*;

import java.awt.*; //window handling
import javax.swing.*; //window drawing

public class HelloWorld{
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				SimpleFrame frame = new SimpleFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//if multi window project, this would not be set to exit
				frame.setVisible(true);//frame comes invisible
			}
		});
			
	}
}

class SimpleFrame extends JFrame{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public SimpleFrame(){
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenw=screenSize.width;
		int screenh=screenSize.height;
	
		setSize(screenw/2,screenh/2);
		setLocationByPlatform(true);
		setTitle("I'm a jew!");
	}
	
	
}