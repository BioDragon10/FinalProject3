package flap.view;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import flap.controller.Controller;

public class MainPanel extends JPanel
{
	private Controller app;
	private FlapPanel flapCanvas;
	private SpringLayout layout;
	private JLabel scoreLabel;
	private JLabel aliveLabel;
	private JScrollPane fitnessPane;
	private JTextArea fitnessText;
	private JButton saveButton;
	private JButton loadButton;
	private int generationNum;
	
	private ArrayList<String> fitnessHistory;
	
	private double score;
	
	public MainPanel(Controller app)
	{
		this.app = app;
		score = 0;
		this.flapCanvas = new FlapPanel(app, this);
		this.layout = new SpringLayout();
		this.scoreLabel = new JLabel("Score: " + score);
		this.aliveLabel = new JLabel("Birds Alive: 0");
		
		this.fitnessPane = new JScrollPane();
		
		this.fitnessText = new JTextArea(20, 0);
		
		this.fitnessHistory = new ArrayList<String>();
		
		this.saveButton = new JButton("Save");
		
		this.loadButton = new JButton("Load");
		this.generationNum = 0;
		
		
		
		setupFitnessPane();
		setupPanel();
		setupListeners();
		setupLayout();
	}
	
	private void setupFitnessPane()
	{
		fitnessText.setLineWrap(true);
		fitnessText.setWrapStyleWord(true);
		fitnessText.setEnabled(false);
		
		fitnessPane.setViewportView(fitnessText);
		fitnessPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fitnessPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	private void setupPanel()
	{
		this.setLayout(layout);
		this.add(flapCanvas);
		this.add(scoreLabel);
		this.add(aliveLabel);
		this.add(fitnessPane);
		this.add(saveButton);
		this.add(loadButton);
	}
	private void setupListeners()
	{
		saveButton.addActionListener(click -> app.saveBird());
		loadButton.addActionListener(click -> app.loadBird());
	}
	private void setupLayout()
	{
		layout.putConstraint(SpringLayout.NORTH, aliveLabel, 10, SpringLayout.SOUTH, scoreLabel);
		layout.putConstraint(SpringLayout.WEST, aliveLabel, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.NORTH, flapCanvas, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, flapCanvas, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, flapCanvas, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, flapCanvas, -200, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scoreLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, scoreLabel, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.NORTH, fitnessPane, 10, SpringLayout.SOUTH, aliveLabel);
		layout.putConstraint(SpringLayout.WEST, fitnessPane, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.EAST, fitnessPane, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, fitnessPane, -10, SpringLayout.NORTH, saveButton);
		layout.putConstraint(SpringLayout.WEST, saveButton, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.SOUTH, saveButton, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.SOUTH, loadButton, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, loadButton, -10, SpringLayout.EAST, this);
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
	
	public void resetScore()
	{
		score = 0;
	}
	
	public void updateBirdCount(int bird)
	{
		aliveLabel.setText("Birds Alive: " + bird);
	}
	
	public void changeHistory(String newest)
	{
		generationNum += 1;
		if (fitnessHistory.size() == 0)
		{
			fitnessHistory.add("Generation " + (generationNum) + ": " + newest);
		}
		else
		{
			fitnessHistory.add("\nGeneration " + (generationNum) + ": " + newest);
		}
		
		fitnessText.setText(null);
		for (int index = 0; index < fitnessHistory.size(); index++)
		{
			fitnessText.append(fitnessHistory.get(index));
			
		}
	}
	
	public void loadedText(double top, double bot, double out)
	{
		fitnessHistory.add("\nHidden Top Bias was set to: " + top);
		fitnessHistory.add("\nHidden Bottom Bias was set to: " + bot);
		fitnessHistory.add("\nOutput Threshold was set to: " + out);
	}
	
}
