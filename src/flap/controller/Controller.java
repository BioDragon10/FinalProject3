package flap.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	private int maxFitness;
	
	private Bird maxBird;
	
	private HashMap<Integer, Bird> birdMap;
	
	private int birdAmount;
	
	public Controller()
	{
		this.birdMap = new HashMap<Integer, Bird>();
		setupBirdMap(2);
		this.birdAmount = birdMap.size();
		
		this.bird = new Bird(this);
		frame = new Frame(this);
		mainPanel = frame.getPanel();
		panel = mainPanel.getFlapCanvas();
		this.birdsAlive = 1;
		this.mutationRate = .99;
		this.maxFitness = 0;
		
		
		
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
			
			bird.setThresholds(maxBird.getHiddenTopBias(), maxBird.getHiddenBottomBias(), maxBird.getOutputThreshold());
			maxFitness = 0;
			
			panel.reset();
			bird.resetFitness();
			
		} 
	}
	
	public void birdDies()
	{
		this.birdsAlive -= 1;
		
		if (bird.getFitness() > maxFitness)
		{
			maxFitness = bird.getFitness();
			maxBird = bird;
		}
	}
	
	private void birdMove()
	{
		panel.moveBird();
	}
	
	public void fitness(int key)
	{
		birdMap.get(key).addFitness();
	}
	
	private void setupBirdMap(int numBird)
	{
		for (int index =0; index < numBird; index++)
		{
			birdMap.put(index, new Bird(this));
		}
	}
	
	public int getBirdAmount()
	{
		return this.birdAmount;
	}
	
	public HashMap<Integer, Bird> getBirdMap()
	{
		return this.birdMap;
	}

}
