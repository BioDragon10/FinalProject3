package flap.controller;

import flap.model.Bird;
import flap.view.FlapPanel;
import flap.view.Frame;
import flap.view.MainPanel;

public class Controller 
{
	private Frame frame;
	private FlapPanel panel;
	private int birdsAlive;
	private MainPanel mainPanel;
	
	private Bird bird;
	
	public Controller()
	{
		this.bird = new Bird(this);
		frame = new Frame(this);
		mainPanel = frame.getPanel();
		panel = mainPanel.getFlapCanvas();
		this.birdsAlive = 1;
	}
	
	public void start()
	{
		while(birdsAlive > 0)
		{
			if(bird.checkJump())
			{
				birdMove();
			}
			panel.move();
			panel.pause();
		}
	}
	
	public void birdDies()
	{
		this.birdsAlive -= 1;
	}
	
	private void birdMove()
	{
		panel.moveBird();
	}

}
