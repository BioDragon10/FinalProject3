package flap.model;

import flap.controller.Controller;

public class Bird
{
	private Controller app;
	
	private int topY;
	private int bottomY;
	private double[] thresholds;
	private double hiddenTopBias;
	private double hiddenBottomBias;
	private int hiddenOperation;
	private double outputThreshold;
	
	private double mutationRate;
	
	private int birdPosition;
	
	private int fitness;
	
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
	
	private boolean coinFlip()
	{
		return (int) (Math.random() * 2) == 0;
	}
	
	public void setThresholds(double hidTop, double hidBot, double outThres)
	{
		//DO TO: Coinflip for negatives.
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
	
	private boolean outputNode(double input)
	{
		boolean jump = false;
		
		if (input > this.outputThreshold)
		{
			jump = true;
		}
		
		return jump;
	}
	
	public void addFitness()
	{
		this.fitness += 1;
	}
	
	public void resetFitness()
	{
		this.fitness = 0;
	}
	
	public int getFitness()
	{
		return this.fitness;
	}
	

	
	
	
}
