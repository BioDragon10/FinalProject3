package flap.view;

import javax.swing.JFrame;

import flap.controller.Controller;

public class Frame extends JFrame 
{
	private Controller app;
	private FlapPanel panel;
	
	public Frame(Controller app)
	{
		this.app = app;
		this.panel = new FlapPanel(app);
		
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Flap");
		this.setSize(1200, 825);
		this.setResizable(false);
		this.setVisible(true);
	}
	
}
