package flap.view;

import javax.swing.JFrame;

import flap.controller.Controller;

public class Frame extends JFrame 
{
	private Controller app;
	private MainPanel panel;
	
	public Frame(Controller app)
	{
		this.app = app;
		this.panel = new MainPanel(app);
		
		setupFrame();
	}
	
	public MainPanel getPanel()
	{
		return this.panel;
	}
	
	private void setupFrame()
	{
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Flappy Bird AI");
		this.setSize(1200, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
}
