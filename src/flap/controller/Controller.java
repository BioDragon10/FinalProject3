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
	
	private double mutationRate;
	
	public Controller()
	{
		this.bird = new Bird(this);
		frame = new Frame(this);
		mainPanel = frame.getPanel();
		panel = mainPanel.getFlapCanvas();
		this.birdsAlive = 1;
		this.mutationRate = .99;
	}
	
	public void start()
	{
		while (true)
		{
			while(birdsAlive > 0)
			{
				bird.setMutationRate(mutationRate); //:)
				
				bird.setTopY(panel.getUpperPipe());
				bird.setBottomY(panel.getLowerPipe());
				bird.setBirdPosition(panel.getBirdPosition());
				
				if(bird.checkJump())
				{
					birdMove();
				}
				panel.move();
				panel.pause();
			}
			
			birdsAlive = 1;
			
			panel.reset();
			
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
