package flap.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import flap.model.Bird;
import flap.view.FlapPanel;
import flap.view.Frame;
import flap.view.MainPanel;

public class Controller 
{
	/**
	 * Frame instance for the entire project.
	 */
	private Frame frame;
	
	/**
	 * Sets up the FlapPanel where the game plays out
	 */
	private FlapPanel panel;
	
	/**
	 * Counts how many birds are alive
	 */
	private int birdsAlive;
	
	/**
	 * Sets up the MainPanel where the FlapPanel exists, and where there will eventually be a fitness list.
	 */
	private MainPanel mainPanel;
	
	/**
	 * Is the mutation rate for the birds' genetics.
	 */
	private double mutationRate;
	
	/**
	 * Tracks the maxFitness to be used for the future birds
	 */
	private int maxFitness;
	
	/**
	 * Grabs the bird that had the best genes
	 */
	private Bird maxBird;
	
	/**
	 * Sets up a HashMap that will store every bird.
	 */
	private HashMap<Integer, Bird> birdMap;
	
	/**
	 * Shows how many birds are to be displayed.
	 */
	private int birdAmount;
	
	private int[] deadKeys;
	
	/**
	 * Initializes all the data members, sets up the birdMap, and creates the Frame.
	 */
	public Controller()
	{
		this.birdMap = new HashMap<Integer, Bird>();
		setupBirdMap(10000);
		this.birdAmount = birdMap.size();
		
		frame = new Frame(this);
		mainPanel = frame.getPanel();
		panel = mainPanel.getFlapCanvas();
		this.birdsAlive = birdMap.size();
		this.mutationRate = .99;
		this.maxFitness = 0;
		this.deadKeys = new int[birdMap.size()];
	}
	
	/**
	 * The main method that runs the simulation/game.
	 */
	public void start()
	{
		while (true)
		{
			
			while(birdsAlive > 0)
			{
				for (Map.Entry<Integer, Bird> currentBird : birdMap.entrySet())
				{
					currentBird.getValue().setTopY(panel.getUpperPipe());
					currentBird.getValue().setBottomY(panel.getLowerPipe());
					currentBird.getValue().setBirdPosition(panel.getBirdPosition(currentBird.getKey()));
					
					if(currentBird.getValue().checkJump())
					{
						birdMove(currentBird.getKey());
					}
				}
				
				System.out.println(birdsAlive);
				panel.move();
				panel.pause();
			}
			deadKeys = new int[birdMap.size()];
			for (Map.Entry<Integer, Bird> currentBird : birdMap.entrySet())
			{
				currentBird.getValue().setMutationRate(mutationRate); //:)
				currentBird.getValue().setThresholds(maxBird.getHiddenTopBias(), maxBird.getHiddenBottomBias(), maxBird.getOutputThreshold());
				currentBird.getValue().resetFitness();
			}
			birdsAlive = birdMap.size();
			maxFitness = 0;
			panel.reset();
		} 
	}
	
	/**
	 * This is what happens when a bird dies, and sees if the bird had a good fitness.
	 * @param key
	 * 			 The key of the bird that died for the HashMap.
	 */
	public void birdDies(int key)
	{
		int equal = 0;
		for (int current : deadKeys)
		{
			if (current == key + 1)
			{
				equal += 1;
			}
		}
		if (!(equal > 0))
		{
			deadKeys[key] = key + 1;
			this.birdsAlive -= 1;
			
			if (birdMap.get(key).getFitness() > maxFitness)
			{
				maxFitness = birdMap.get(key).getFitness();
				maxBird = birdMap.get(key);
			}
		}
		
	}
	
	/**
	 * Lets the birds move themselves on the panel
	 * @param key
	 * 			 The key of the bird that wants to move.
	 */			 
	private void birdMove(int key)
	{
		panel.moveBird(key);
	}
	
	/**
	 * Adds up the fitness for the birds (gains a fitness every movement of the panel)
	 * @param key
	 * 			 The bird that will gain the fitness.
	 */
	public void fitness(int key)
	{
		birdMap.get(key).addFitness();
	}
	
	/**
	 * Creates the birdMap with keys and new Birds.
	 * @param numBird
	 * 				 The number of birds the user wants to have on the screen.
	 */
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
