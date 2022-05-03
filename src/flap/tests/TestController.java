package flap.tests;

import flap.controller.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestController
{
	private Controller testedController;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.testedController = new Controller();
	}
	
	@AfterEach
	void tearDown() throws Exception
	{
		this.testedController = null;
	}
	
	@Test
	void testBirdDies()
	{
		Method methods [] = testedController.getClass().getMethods();
		
		boolean birdDies = false;
		
		for (Method current : methods)
		{
			if (current.getName().equals("birdDies"))
			{
				birdDies = true;
			}
		}
		
		assertTrue(birdDies, "There must be a birdDies method");
	}
	
	@Test
	void testGetBirdMap()
	{
		Object birdMap = testedController.getBirdMap();
		
		assertTrue(birdMap != null, "Birdmap must be set up");
	}
	
	@Test
	void testSaveText()
	{
		Method methods [] = testedController.getClass().getMethods();

		
		boolean singleParam = false;
		
		for (int index = 0; index < methods.length; index++)
		{

			if (methods[index].getName().equals("saveBird"))
			{
				int paramCount = methods[index].getParameterCount();
				if (paramCount == 0)
				{
					singleParam = true;
				}
			}
		}
		
		assertTrue(singleParam, "saveBird not have any parameters.");
	}
}
