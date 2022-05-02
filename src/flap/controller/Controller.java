package flap.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import flap.model.AdvancedBird;
import flap.model.Bird;
import flap.view.FlapPanel;
import flap.view.Frame;
import flap.view.MainPanel;

public class Controller 
{
	/**
	 * Frame instance for the entire project.
	 */
	private Frame frame;
	
	/**
	 * Sets up the FlapPanel where the game plays out
	 */
	private FlapPanel panel;
	
	/**
	 * Counts how many birds are alive
	 */
	private int birdsAlive;
	
	/**
	 * Sets up the MainPanel where the FlapPanel exists, and where there will eventually be a fitness list.
	 */
	private MainPanel mainPanel;
	
	/**
	 * Is the mutation rate for the birds' genetics.
	 */
	private double mutationRate;
	
	/**
	 * Tracks the maxFitness to be used for the future birds
	 */
	private int maxFitness;
	
	/**
	 * Grabs the bird that had the best genes
	 */
	private Bird maxBird;
	
	private AdvancedBird maxAdvancedBird;
	
	/**
	 * Sets up a HashMap that will store every bird.
	 */
	private HashMap<Integer, Bird> birdMap;
	
	/**
	 * Shows how many birds are to be displayed.
	 */
	private int birdAmount;
	
	private int[] deadKeys;
	
	private boolean isAdvancedBirds;
	
	/**
	 * Initializes all the data members, sets up the birdMap, and creates the Frame.
	 */
	public Controller()
	{
		this.birdMap = new HashMap<Integer, Bird>();
		setupBirdMap(50);
		this.birdAmount = birdMap.size();
		
		frame = new Frame(this);
		mainPanel = frame.getPanel();
		panel = mainPanel.getFlapCanvas();
		this.birdsAlive = birdMap.size();
		this.mutationRate = .99;
		this.maxFitness = 0;
		this.deadKeys = new int[birdMap.size()];
	}
	
	/**
	 * The main method that runs the simulation/game.
	 */
	public void start()
	{
//		for (String current : loadText())
//		{
//			System.out.println(current);
//		}
		while (true)
		{
			
			while(birdsAlive > 0)
			{
				for (Map.Entry<Integer, Bird> currentBird : birdMap.entrySet())
				{
					currentBird.getValue().setTopY(panel.getUpperPipe());
					currentBird.getValue().setBottomY(panel.getLowerPipe());
					currentBird.getValue().setBirdPosition(panel.getBirdPosition(currentBird.getKey()));
					
					if(currentBird.getValue().checkJump())
					{
						birdMove(currentBird.getKey());
					}
				}
				
				panel.move();
				panel.pause();
				mainPanel.updateBirdCount(birdsAlive);
			}
			mainPanel.changeHistory("" + maxFitness);
			deadKeys = new int[birdMap.size()];
			for (Map.Entry<Integer, Bird> currentBird : birdMap.entrySet())
			{
				currentBird.getValue().setMutationRate(mutationRate);
				if (isAdvancedBirds)
				{
					AdvancedBird advanced = (AdvancedBird) currentBird.getValue();
					advanced.setThresholds(maxAdvancedBird.getHiddenTopBias(), maxAdvancedBird.getHiddenBottomBias(), maxAdvancedBird.getHiddenTopBias2(), 
														 maxAdvancedBird.getHiddenBottomBias2(), maxAdvancedBird.getOutputThreshold(), maxAdvancedBird.getOutputTopBias(),
														 maxAdvancedBird.getOutputBottomBias());
				}
				else
				{
					currentBird.getValue().setThresholds(maxBird.getHiddenTopBias(), maxBird.getHiddenBottomBias(), maxBird.getOutputThreshold());
				}
				 //:)
				
				currentBird.getValue().resetFitness();
			}
			birdsAlive = birdMap.size();
			maxFitness = 0;
			panel.reset();
		} 
	}
	
	/**
	 * This is what happens when a bird dies, and sees if the bird had a good fitness.
	 * @param key
	 * 			 The key of the bird that died for the HashMap.
	 */
	public void birdDies(int key)
	{
		int equal = 0;
		for (int current : deadKeys)
		{
			if (current == key + 1)
			{
				equal += 1;
			}
		}
		if (!(equal > 0))
		{
			deadKeys[key] = key + 1;
			this.birdsAlive -= 1;
			
			if (birdMap.get(key).getFitness() > maxFitness)
			{
				maxFitness = birdMap.get(key).getFitness();
				if (isAdvancedBirds)
				{
					maxAdvancedBird = (AdvancedBird) birdMap.get(key);
				}
				maxBird = birdMap.get(key);
			}
		}
		
	}
	
	/**
	 * Lets the birds move themselves on the panel
	 * @param key
	 * 			 The key of the bird that wants to move.
	 */			 
	private void birdMove(int key)
	{
		panel.moveBird(key);
	}
	
	/**
	 * Adds up the fitness for the birds (gains a fitness every movement of the panel)
	 * @param key
	 * 			 The bird that will gain the fitness.
	 */
	public void fitness(int key)
	{
		birdMap.get(key).addFitness();
	}
	
	/**
	 * Creates the birdMap with keys and new Birds.
	 * @param numBird
	 * 				 The number of birds the user wants to have on the screen.
	 */
	private void setupBirdMap(int numBird)
	{
		String answer = JOptionPane.showInputDialog("Would you like to use advanced birds (y/n)", this);
		
		if (answer.equalsIgnoreCase("y"))
		{
			for (int index =0; index < numBird; index++)
			{
				birdMap.put(index, new AdvancedBird(this));
				this.isAdvancedBirds = true;
			}
		}
		else
		{
			for (int index =0; index < numBird; index++)
			{
				birdMap.put(index, new Bird(this));
				this.isAdvancedBirds = false;

			}
		}
		
	}
	
	public int getBirdAmount()
	{
		return this.birdAmount;
	}
	
	public HashMap<Integer, Bird> getBirdMap()
	{
		return this.birdMap;
	}
	
	private void saveText(String text)
	{

		JFileChooser saver = new JFileChooser();
		saver.showSaveDialog(frame);
		String path = saver.getSelectedFile().getPath();
		
		if(!path.endsWith(".txt"))
		{
			path += ".txt";
		}
		
		try (PrintWriter saveText = new PrintWriter(path))
		{	
			
			saveText.println(text);
		}
		catch (IOException errorFromIO)
		{
			JOptionPane.showMessageDialog(frame, errorFromIO.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);;
		}
		catch (Exception genericError)
		{
			JOptionPane.showMessageDialog(frame, genericError.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);;
		}
	}
	
//	private ArrayList<String> loadTextToList(String filename)
//	{
//		ArrayList<String> fileContents = new ArrayList<String>();
//		
//		File source = new File(filename);
//				
//		try (Scanner fileScanner = new Scanner(source))
//		{
//			while (fileScanner.hasNextLine())
//			{
//				fileContents.add(fileScanner.nextLine());
//			}
//		}
//		catch (IOException fileError)
//		{
//			handleError(fileError);
//		}
//		catch (Exception error)
//		{
//			handleError(error);
//		}
//		
//		return fileContents;
//	}
	
//	public void loadImage()
//	{
//		BufferedImage source = null;
//		
//		try
//		{
//			JFileChooser picker = new JFileChooser();
//			picker.setAcceptAllFileFilterUsed(false);
//			picker.addChoosableFileFilter(new FileNameExtensionFilter("Pictures!", "png"));
//			int valid = picker.showOpenDialog(this);
//			
//			if(valid == JFileChooser.APPROVE_OPTION)
//			{
//				String filePath = picker.getSelectedFile().getPath();
//				source = ImageIO.read(new File(filePath));
//				canvasImage = source;
//				repaint();
//			}
//		}
//		catch (IOException exception)
//		{
//			controller.handleError(exception);
//		}
//	}
	
	private ArrayList<String> loadText()
	{
		ArrayList<String> fileContents = new ArrayList<String>();
		
		try
		{
			JFileChooser picker = new JFileChooser();
			picker.setAcceptAllFileFilterUsed(false);
			picker.addChoosableFileFilter(new FileNameExtensionFilter("Old Birds", "txt"));
			int valid = picker.showOpenDialog(frame);
			
			if(valid == JFileChooser.APPROVE_OPTION)
			{
				String filePath = picker.getSelectedFile().getPath();
				File source = new File(filePath);
				try (Scanner fileScanner = new Scanner(source))
				{
					while (fileScanner.hasNextLine())
					{
						fileContents.add(fileScanner.nextLine());
					}
				}
//				catch (IOException fileError)
//				{
//					JOptionPane.showMessageDialog(frame, fileError.getMessage(), "Load Error!", JOptionPane.ERROR_MESSAGE);;
//				}
				catch (Exception error)
				{
					JOptionPane.showMessageDialog(frame, error.getMessage(), "Load Error!", JOptionPane.ERROR_MESSAGE);;
				}
			}
		}
		catch (Exception exception)
		{
			JOptionPane.showMessageDialog(frame, exception.getMessage(), "Load Error!", JOptionPane.ERROR_MESSAGE);;
		}
		
		return fileContents;
	}
	
	

}
