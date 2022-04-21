package flap.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import flap.controller.Controller;

public class MainPanel extends JPanel
{
	private Controller app;
	private FlapPanel flapCanvas;
	private SpringLayout layout;
	private JLabel scoreLabel;
	
	private double score;
	
	public MainPanel(Controller app)
	{
		this.app = app;
		score = 0;
		this.flapCanvas = new FlapPanel(app, this);
		this.layout = new SpringLayout();
		this.scoreLabel = new JLabel("Score: " + score);
		
		
		setupPanel();
		setupListeners();
		setupLayout();
	}
	
	private void setupPanel()
	{
		this.setLayout(layout);
		this.add(flapCanvas);
		this.add(scoreLabel);
	}
	private void setupListeners()
	{
		
	}
	private void setupLayout()
	{
		layout.putConstraint(SpringLayout.NORTH, flapCanvas, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, flapCanvas, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, flapCanvas, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, flapCanvas, -200, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scoreLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, scoreLabel, 10, SpringLayout.EAST, flapCanvas);
	}
	
	public FlapPanel getFlapCanvas()
	{
		return flapCanvas;
	}
	
	public void addScore()
	{
		score += 0.5;
		scoreLabel.setText("Score: " + score);
	}
	
}
