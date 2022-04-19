package flap.view;

import javax.swing.JPanel;

import flap.controller.Controller;

public class FlapPanel extends JPanel
{
	private Controller app;
	
	public FlapPanel(Controller app)
	{
		this.app = app;
	}
}
