package flap.model;

import flap.controller.Controller;

/**
 * Is a bird with two nodes in the hidden layer instead of the default one.
 * @author rlaw7457
 * @date May 3, 2022
 * @version 1.0
 */
public class AdvancedBird extends Bird 
{
	/**
	 * Holds the operation of the second hidden node.
	 */
	private int hiddenOperation2;
	
	/**
	 * Holds the bottomBias of the second hidden node.
	 */
	private double hiddenBottomBias2;
	
	/**
	 * Holds the topBias of the second hidden node.
	 */
	private double hiddenTopBias2;
	
	/**
	 * Holds the operation of the output node.
	 */
	private int outputOperation;
	
	/**
	 * Holds the topBias of the output node.
	 */
	private double outputTopBias;
	
	/**
	 * Holds the bottomBias of the output node.
	 */
	private double outputBottomBias;
	
	/**
	 * 
	 */
	private int weight;
	
	/**
	 * 
	 */
	private double power;
	
	/**
	 * Sets up all of the default values.
	 * @param app
	 * 			 Holds the reference to the Controller.
	 */			 
	public AdvancedBird (Controller app)
	{
		super(app);
		this.hiddenBottomBias2 = 0.5;
		this.hiddenTopBias2 = 0.5;
		this.outputTopBias = 0.5;
		this.outputBottomBias = 0.5;
		this.weight = 10;
		this.power = 1.0;
	}
	
	/**
	 * Same as regular bird checkJump, but has the other hidden node.
	 */
	@Override
	public boolean checkJump()
	{
		boolean isJump = false;
		
		int topDistance = super.birdPosition - super.topY;
		int bottomDistance = super.bottomY - super.birdPosition;
		
		double first = super.firstNode(topDistance, bottomDistance);
		double second = bottomHiddenNode(topDistance, bottomDistance);
		
		isJump = outputNode(first, second);
		
		//System.out.println("Advanced bird!");
		
		return isJump;
	}
	
	
	/**
	 * Sets the thresholds for the biases and thresholds.
	 * @param hidTop1
	 * 				 bias for the hidden top node's top pipe value.
	 * @param hidBot1
	 * 				 bias for the hidden top node's bottom pipe value.
	 * @param hidTop2
	 * 				 bias for the hidden bottom node's top pipe value.
	 * @param hidBot2
	 * 				 bias for the hidden bottom node's bottom pipe value.
	 * @param outThres
	 * 				 threshold for whether or not the final node will output true.
	 * @param outTop
	 * 				 bias for the output node's top value.
	 * @param outBot
	 * 				bias for the output node's bottom value.
	 */
	public void setThresholds(double hidTop1, double hidBot1, double hidTop2, double hidBot2,
							  double outThres, double outTop, double outBot, int weight, double power, 
							  boolean oldBird)
	{
		if (oldBird == false)
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
			
			int coinFlip5 = coinFlip() ? -1 : 1;
			this.weight = weight + coinFlip5 * ((int)(Math.random() * 5) + 1);
			if (this.weight > 20)
			{
				this.weight = 20;
			}
			
			if (this.weight < 1)
			{
				this.weight = 1;
			}
			
			int coinFlip6 = coinFlip() ? -1 : 1;
			this.power = power + coinFlip6 * (Math.random());
			
			if (this.power <= 0.0)
			{
				this.power = 0.1;
			}
			
			if (this.power > 2.0)
			{
				this.power = 2.0;
			}
		}
		else
		{
			this.hiddenTopBias = hidTop1;
			this.hiddenBottomBias = hidBot1;
			this.hiddenTopBias2 = hidTop2;
			this.hiddenBottomBias2 = hidBot2;
			this.outputThreshold = outThres;
			this.outputTopBias = outTop;
			this.outputBottomBias = outBot;
			this.weight = weight;
			this.power = power;
			
		}
		
		
	}
	
	/**
	 * Does the stuff for the hidden bottom node.
	 * @param topDistance
	 * 					 Takes in the bird's distance from the top pipe.
	 * @param bottomDistance
	 * 					 Takes in the bird's distance from the bottom pipe.
	 * @return
	 * 		  Returns a number between 0 and 1 that will be sent to the output node.
	 */
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
	
	/**
	 * Handles the output node's logic.
	 * @param topIn
	 *		Handles the top node's output.
	 * @param botIn
	 *		Handles the bottom node's output.
	 * @return
	 * 		Returns true if the input's pass the threshold, false otherwise.
	 */
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
	
	//
	// Getters
	//
	
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
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public double getPower()
	{
		return this.power;
	}
}
