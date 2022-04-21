package flap.view;

import javax.swing.JPanel;

import flap.controller.Controller;

public class MainPanel extends JPanel
{
	private Controller app;
	private FlapPanel flapCanvas;
	
	public MainPanel(Controller app)
	{
		this.app = app;
		
		this.flapCanvas = new FlapPanel(app);
		setupPanel();
		setupListeners();
		setupLayout();
	}
	
	private void setupPanel()
	{
		this.add(flapCanvas);
	}
	private void setupListeners()
	{
		
	}
	private void setupLayout()
	{
		
	}
	
	public FlapPanel getFlapCanvas()
	{
		return flapCanvas;
	}
}
