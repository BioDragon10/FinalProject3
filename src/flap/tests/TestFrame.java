package flap.tests; //Change!

/**
 * Project imports
 */
import flap.controller.Controller; //Change!
import flap.view.FlapPanel; //Change!
import flap.view.Frame; //Change!
import flap.view.MainPanel;

import javax.swing.*;


/**
 * Reflection imports
 */
import java.lang.reflect.*;
/**
 * Testing imports
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestFrame
{
	private Controller testedController; //Change!
	private Frame testedFrame; //Change!

	@BeforeEach
	void setUp() throws Exception
	{
		this.testedController = new Controller();
		this.testedFrame = new Frame(testedController);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.testedController = null;
		this.testedFrame = null;
	}

	@Test
	void testFrame()
	{
		assertTrue(testedFrame instanceof JFrame, "... needs to extend JFrame");
		Method [] methods = testedFrame.getClass().getDeclaredMethods();
		assertTrue(methods.length >= 1, "You need at least 1 method in the ChatFrame");
		assertTrue(testedFrame.getTitle().length() > 5, "Your title needs at least 6 letters");
		assertTrue(testedFrame.getContentPane() instanceof MainPanel, "Your Frame needs to have a MainPanel inside");  //Change!
	}
}
