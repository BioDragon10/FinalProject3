package flap.model;

import flap.controller.Controller;

public class Bird
{
	private Controller app;
	
	public Bird(Controller app)
	{
		this.app = app;
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
	
	
	
}
