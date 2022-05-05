package flap.view;

import javax.swing.JFrame;

import flap.controller.Controller;

/**
 * Creates the Frame that will hold the panels.
 * @author rlaw7457
 * @version 1.0
 * @date 5/5/22
 */
public class Frame extends JFrame 
{
	/**
	 * Holds a reference to the Controller
	 */
	private Controller app;
	
	/**
	 * Holds a reference to the main panel.
	 */
	private MainPanel panel;
	
	/**
	 * Creates and sets up the frame.
	 * @param app
	 * 		Gets the Controller.
	 */
	public Frame(Controller app)
	{
		this.app = app;
		this.panel = new MainPanel(app);
		
		setupFrame();
	}
	
	/**
	 * Sets up the frame.
	 */
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
	
	public MainPanel getPanel()
	{
		return this.panel;
	}
	
	
	
}
