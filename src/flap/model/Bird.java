package flap.model;

import java.awt.Polygon;
import java.util.Map;

import flap.controller.Controller;

public class Bird
{
	/**
	 * Provides the reference to the Controller
	 */
	private Controller app;
	
	/**
	 * Holds the y position of the top pipe
	 */
	private int topY;
	
	/**
	 * Holds the y position of the bottom pipe
	 */
	private int bottomY;
	
	/**
	 * (UNUSED FOR NOW) meant to hold all of the thresholds in an array.
	 */
	private double[] thresholds;
	
	/**
	 * Holds the bias for the top pipe.
	 */
	private double hiddenTopBias;
	
	/**
	 * Holds the bias for the bottom pipe.
	 */
	private double hiddenBottomBias;
	
	/**
	 * Holds the operation for the hidden node.
	 */
	private int hiddenOperation;
	
	/**
	 * Holds the threshold for the output node
	 */
	private double outputThreshold;
	
	/**
	 * Holds the mutation rate for changes in the biases
	 */
	private double mutationRate;
	
	/**
	 * Holds the bird's position
	 */
	private int birdPosition;
	
	/**
	 * Holds the fitness for the bird
	 */
	private int fitness;
	
	/**
	 * Sets up all of the datamembers with default values
	 * @param app
	 * 			 Allows the bird to know about the controller.
	 */
	public Bird(Controller app)
	{
		this.app = app;
		this.hiddenTopBias = 0.5;
		this.hiddenBottomBias = 0.5;
		this.hiddenOperation = 1;
		this.outputThreshold = 0;
		this.thresholds = new double[4];
		this.mutationRate = 1;
		this.fitness = 0;
	}
	
	/**
	 * This is where the bird decides if it will job or not based on its thresholds and biases.
	 * @return
	 * 		  Returns true or false depending on if it wants to jump.
	 */
	public boolean checkJump()
	{
		boolean isJump = false;
		
		int topDistance = birdPosition - topY;
		int bottomDistance = bottomY - birdPosition;
		
		double first = firstNode(topDistance, bottomDistance);
		
		isJump = outputNode(first);
		
		System.out.println(isJump);
		return isJump;
	}
	
	public void setTopY(int top)
	{
		this.topY = top;
	}
	
	public void setBottomY(int bottom)
	{
		this.bottomY = bottom;
	}
	
	public double getHiddenTopBias()
	{
		return this.hiddenTopBias;
	}
	
	public double getHiddenBottomBias()
	{
		return this.hiddenBottomBias;
	}
	
	public int getHiddenOperation()
	{
		return this.hiddenOperation;
	}
	
	public double getOutputThreshold()
	{
		return this.outputThreshold;
	}
	
	/**
	 * Simulates a coinFlip.
	 * @return
	 * 			returns true if 0, false otherwise.
	 */
	private boolean coinFlip()
	{
		return (int) (Math.random() * 2) == 0;
	}
	
	/**
	 * Sets up the top and bottom biases, the output threshold, and the hidden operation.
	 * @param hidTop
	 * 				Grabs the previous best hiddenTop threshold.
	 * @param hidBot
	 * 				Grabs the previous best hiddenBottom threshold.
	 * @param outThres
	 * 				Grabs the previous best output threshold.
	 */
	public void setThresholds(double hidTop, double hidBot, double outThres)
	{
		int coinFlip = coinFlip() ? -1 : 1;
		double mutationBias = (Math.random() / mutationRate);
		this.hiddenTopBias = (hidTop + (mutationBias * coinFlip));
		this.hiddenBottomBias = (hidBot + (mutationBias * coinFlip * -1));
		int coinFlip2 = coinFlip() ? 1 : 2;
		this.hiddenOperation = coinFlip2;
		this.outputThreshold = outThres + mutationRate;
	}
	
	public void setMutationRate(double mutRate)
	{
		this.mutationRate = mutRate;
	}
	
	public void setBirdPosition(int pos)
	{
		this.birdPosition = pos;
	}
	
	/**
	 * Handles the first hidden node.
	 * @param top
	 * 			Grabs the distance between the bird and the top pipe.
	 * @param bot
	 * 			Grabs the distance between the bird and the bottom pipe.
	 * @return
	 * 			Returns an output between 0 and 1.
	 */
	private double firstNode(int top, int bot)
	{
		double output = 0;
		double topIn = top * hiddenTopBias;
		double bottomIn = bot * hiddenBottomBias;
		
		if(this.hiddenOperation == 1)
		{
			output = topIn + bottomIn;
		}
		if (this.hiddenOperation == 2)
		{
			if (topIn > bottomIn)
			{
				output = topIn - bottomIn;
			}
			else
			{
				output = bottomIn - topIn;
			}
			
		}
		
		
		return output / 100;
	}
	
	
	/**
	 * Takes the input from the first node and sees if it is past its threshold.
	 * @param input
	 * 			input from the hidden node.
	 * @return
	 * 		True if the threshold is reached.
	 */
	private boolean outputNode(double input)
	{
		boolean jump = false;
		
		if (input > this.outputThreshold)
		{
			jump = true;
		}
		
		return jump;
	}
	
	/**
	 * Adds to the bird's fitness.
	 */
	public void addFitness()
	{
		this.fitness += 1;
	}
	
	/**
	 * Resets the birds fitness.
	 */
	public void resetFitness()
	{
		this.fitness = 0;
	}
	
	public int getFitness()
	{
		return this.fitness;
	}
	

	
	
	
}
