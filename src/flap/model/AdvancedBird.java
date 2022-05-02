package flap.model;

import flap.controller.Controller;

public class AdvancedBird extends Bird 
{
	private int hiddenOperation2;
	private double hiddenBottomBias2;
	private double hiddenTopBias2;
	private int outputOperation;
	private double outputTopBias;
	private double outputBottomBias;
	
	public AdvancedBird (Controller app)
	{
		super(app);
		this.hiddenBottomBias2 = 0.5;
		this.hiddenTopBias2 = 0.5;
		this.outputTopBias = 0.5;
		this.outputBottomBias = 0.5;
	}
	
	@Override
	public boolean checkJump()
	{
		boolean isJump = false;
		
		int topDistance = super.birdPosition - super.topY;
		int bottomDistance = super.bottomY - super.birdPosition;
		
		double first = super.firstNode(topDistance, bottomDistance);
		double second = bottomHiddenNode(topDistance, bottomDistance);
		
		isJump = outputNode(first, second);
		
		System.out.println("Advanced bird!");
		
		return isJump;
	}
	
	public void setThresholds(double hidTop1, double hidBot1, double hidTop2, double hidBot2, double outThres, double outTop, double outBot)
	{
		int coinFlip = coinFlip() ? -1 : 1;
		double mutationBias = (Math.random() / mutationRate);
		this.hiddenTopBias = (hidTop1 + (mutationBias * coinFlip));
		this.hiddenBottomBias = (hidBot1 + (mutationBias * coinFlip * -1));
		this.hiddenTopBias2 = (hidTop2 + (mutationBias * coinFlip));
		this.hiddenBottomBias2 = (hidBot2 + (mutationBias * coinFlip * -1));
		int coinFlip2 = coinFlip() ? 1 : 2;
		this.hiddenOperation = coinFlip2;
		int coinFlip3 = coinFlip() ? 1 : 2;
		this.hiddenOperation2 = coinFlip3;
		this.outputThreshold = outThres + mutationRate;
		int coinFlip4 = coinFlip() ? 1 : 2;
		this.outputOperation = coinFlip4;
		this.outputBottomBias = (outBot + (mutationBias * coinFlip * -1));
		this.outputTopBias = (outTop + (mutationBias * coinFlip));
	}
	
	private double bottomHiddenNode(int topDistance, int bottomDistance)
	{
		double output = 0;
		double topIn = topDistance * hiddenTopBias2;
		double bottomIn = bottomDistance * hiddenBottomBias2;
		
		if(this.hiddenOperation2 == 1)
		{
			output = topIn + bottomIn;
		}
		if (this.hiddenOperation2 == 2)
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
	
	private boolean outputNode(double topIn, double botIn)
	{
		boolean jump = false;
		
		double input = 0;
		
		if (outputOperation == 1)
		{
			input = topIn + botIn;
		}
		else if (this.hiddenOperation2 == 2)
		{
			if (topIn > botIn)
			{
				input = topIn - botIn;
			}
			else
			{
				input = botIn - topIn;
			}
			
		}
		
		if (input > this.outputThreshold)
		{
			jump = true;
		}
				
		return jump;
	}
	
	public double getOutputTopBias()
	{
		return this.outputTopBias;
	}
	
	public double getOutputBottomBias()
	{
		return this.outputBottomBias;
	}
	
	public double getHiddenTopBias2()
	{
		return this.hiddenTopBias2;
	}
	
	public double getHiddenBottomBias2()
	{
		return this.hiddenBottomBias2;
	}
}
