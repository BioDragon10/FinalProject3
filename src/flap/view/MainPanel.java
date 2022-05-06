package flap.view;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import flap.controller.Controller;

/**
 * Holds the data for the birds and the flapPanel.
 * @author rlaw7457
 * @version 1.0
 * @date 5/5/22
 */
public class MainPanel extends JPanel
{
	/**
	 * Holds the reference to the Controller.
	 */
	private Controller app;
	
	/**
	 * Holds the reference to the FlapPanel.
	 */
	private FlapPanel flapCanvas;
	
	/**
	 * Holds a SpringLayout for the panel.
	 */
	private SpringLayout layout;
	
	/**
	 * A label that will hold the score.
	 */
	private JLabel scoreLabel;
	
	/**
	 * A label that will hold the number of birds alive.
	 */
	private JLabel aliveLabel;
	
	/**
	 * A scrollPane that will allow you to scroll through the history.
	 */
	private JScrollPane fitnessPane;
	
	/**
	 * Text that will hold the history.
	 */
	private JTextArea fitnessText;
	
	/**
	 * Button that saves.
	 */
	private JButton saveButton;
	
	/**
	 * Button that loads
	 */
	private JButton loadButton;
	
	/**
	 * Holds the generation number.
	 */
	private int generationNum;
	
	/**
	 * Holds all of the Strings from the history.
	 */
	private ArrayList<String> fitnessHistory;
	
	/**
	 * Holds the score.
	 */
	private double score;
	
	/**
	 * 
	 */
	private JLabel roundLabel;
	
	/**
	 * Initializes all the components of the panel and sets up the layout and such.
	 * @param app
	 * 		A parameter that holds a reference to the Controller.
	 */
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
		
		this.roundLabel = new JLabel("Round: 0");
		
		
		
		
		setupFitnessPane();
		setupPanel();
		setupListeners();
		setupLayout();
	}
	
	/**
	 * Sets up the FitnessPane.
	 */
	private void setupFitnessPane()
	{
		fitnessText.setLineWrap(true);
		fitnessText.setWrapStyleWord(true);
		fitnessText.setEnabled(false);
		
		fitnessPane.setViewportView(fitnessText);
		fitnessPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fitnessPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	/**
	 * Adds the components to the panel.
	 */
	private void setupPanel()
	{
		this.setLayout(layout);
		this.add(flapCanvas);
		//this.add(scoreLabel);
		this.add(aliveLabel);
		this.add(fitnessPane);
		this.add(saveButton);
		this.add(loadButton);
		this.add(roundLabel);
	}
	
	/**
	 * Adds the listeners for the save and load buttons.
	 */
	private void setupListeners()
	{
		saveButton.addActionListener(click -> app.saveBird());
		loadButton.addActionListener(click -> app.loadBird());
	}
	
	/**
	 * Sets up the layout of the panel.
	 */
	private void setupLayout()
	{
		layout.putConstraint(SpringLayout.NORTH, aliveLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, aliveLabel, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.NORTH, flapCanvas, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, flapCanvas, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, flapCanvas, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, flapCanvas, -200, SpringLayout.EAST, this);
		//layout.putConstraint(SpringLayout.NORTH, scoreLabel, 10, SpringLayout.NORTH, this);
		//layout.putConstraint(SpringLayout.WEST, scoreLabel, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.WEST, fitnessPane, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.EAST, fitnessPane, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, fitnessPane, -10, SpringLayout.NORTH, saveButton);
		layout.putConstraint(SpringLayout.WEST, saveButton, 10, SpringLayout.EAST, flapCanvas);
		layout.putConstraint(SpringLayout.SOUTH, saveButton, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.SOUTH, loadButton, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, loadButton, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, fitnessPane, 10, SpringLayout.SOUTH, roundLabel);
		layout.putConstraint(SpringLayout.NORTH, roundLabel, 10, SpringLayout.SOUTH, aliveLabel);
		layout.putConstraint(SpringLayout.WEST, roundLabel, 10, SpringLayout.EAST, flapCanvas);
	}
	
	/**
	 * Adds to the score.
	 */
	public void addScore()
	{
		score += 0.5;
		scoreLabel.setText("Score: " + score);
	}
	
	/**
	 * Resets the score.
	 */
	public void resetScore()
	{
		score = 0;
	}
	
	/**
	 * Updates the aliveLabel.
	 * @param bird
	 * 		Grabs the number of birds alive.
	 */
	public void updateBirdCount(int bird)
	{
		aliveLabel.setText("Birds Alive: " + bird);
	}
	
	public void updateRound(int round)
	{
		roundLabel.setText("Round: " + round);
	}
	
	/**
	 * Adds to the history.
	 * @param newest
	 * 		Adds the newest history to be added.
	 */
	public void changeHistory(String newest, int weight, double power)
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
		
		fitnessHistory.add("\nWeight: " + weight);
		fitnessHistory.add("\nPower: " + power);
		
		fitnessText.setText(null);
		for (int index = 0; index < fitnessHistory.size(); index++)
		{
			fitnessText.append(fitnessHistory.get(index));
			
		}
	}
	
	/**
	 * Updates the history with the loaded values.
	 * @param top
	 * 		The topBias value.
	 * @param bot
	 * 		The bottomBias value.
	 * @param out
	 * 		The outputThreshold value.
	 */
	public void loadedText(double top, double bot, double out)
	{
		fitnessHistory.add("\nHidden Top Bias was set to: " + top);
		fitnessHistory.add("\nHidden Bottom Bias was set to: " + bot);
		fitnessHistory.add("\nOutput Threshold was set to: " + out);
	}
	
	public FlapPanel getFlapCanvas()
	{
		return flapCanvas;
	}
	
}
