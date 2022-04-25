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
	
	private int mutationRate;
	
	public Bird(Controller app)
	{
		this.app = app;
		this.hiddenTopBias = 0.5;
		this.hiddenBottomBias = 0.5;
		this.hiddenOperation = 0;
		this.outputThreshold = 0;
		this.thresholds = new double[4];
		this.mutationRate = 1;
	}
	
	public boolean checkJump()
	{
		boolean isJump = false;
		
		double random = Math.random();
		
		if(random < 0.5)
		{
			isJump = true;
		}
		
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
	
	public void setThresholds(double hidTop, double hidBot, int hidOp, double outThres)
	{
		//DO TO: Coinflip for negatives.
		double mutationBias = (Math.random() * mutationRate);
		this.hiddenTopBias = hidTop + mutationBias;
		this.hiddenBottomBias = hidBot - mutationBias;
		this.hiddenOperation = hidOp;
		this.outputThreshold = outThres;
	}
	
	public void setMutationRate(int mutRate)
	{
		this.mutationRate = mutRate;
	}
	

	
	
	
}
