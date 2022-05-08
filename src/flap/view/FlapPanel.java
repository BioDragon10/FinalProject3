package flap.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import flap.controller.Controller;

/**
 * Holds the game panel where the pipes and birds exist.
 * 
 * @author rlaw7457
 * @date May 3, 2022
 * @Version 1.0
 */
public class FlapPanel extends JPanel
{
	/*
	 * Holds a reference to the Controller
	 */
	private Controller app;
	
	/*
	 * Holds a reference to the MainPanel
	 */
	private MainPanel panel;
	
	/*
	 * The image that holds all of the polygons.
	 */
	private BufferedImage canvasImage;
	
	/**
	 * Holds the top pipe's shape.
	 */
	private Polygon topPipe;
	
	/**
	 * Holds the bottom pipe's shape.
	 */
	private Polygon bottomPipe;
	
	/**
	 * Holds what layout the pipes will be in.
	 */
	private int pipeLayout;
	
	/*
	 * Holds a list of bird polygons with their keys.
	 */
	private HashMap<Integer, Polygon> birdMap;
	
	/*
	 * Holds a list of Colors for each key.
	 */
	private HashMap<Integer, Color> colorMap;
	
	/**
	 * Holds a size value for making the pipes harder.
	 */
	private double size;
	
	
	/**
	 * Holds a value for the pipeColor.
	 */
	private Color pipeColor;
	
	/**
	 * Holds a value for the pipe speed
	 */
	private int pipeSpeed;
	
	/**
	 * Holds the current round
	 */
	private int round;

	/**
	 * Sets up the FlapPanel and the pipes, as well as the birdMap with its ColorMap.
	 * @param app
	 * 		Grabs the Controller reference.
	 * @param panel
	 * 		Grabs the MainPanel reference.
	 */
	public FlapPanel(Controller app, MainPanel panel)
	{
		this.panel = panel;
		this.app = app;
		this.pipeLayout = 1;
	
		this.canvasImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		
		this.topPipe = drawTopPipe();
		this.bottomPipe = drawBottomPipe();
		this.birdMap = new HashMap<Integer, Polygon>();
		this.colorMap = new HashMap<Integer, Color>();
		this.size = 1.0;
		this.pipeColor = Color.green;
		
		this.pipeSpeed = -10;
		
		this.round = 0;
	
		setupBirdMap();
		setupColorMap();
		
		setupPanel();
		
		setupListeners();
		
		setupLayout();
	}
	
	/**
	 * Sets up the panel with a backGround and draws the Canvas.
	 */
	private void setupPanel()
	{
		this.setMinimumSize(new Dimension(800, 800));
		this.setBackground(Color.blue);
		repaint();
	}
	
	/**
	 * Placeholder for if I need to have listeners.
	 */
	private void setupListeners()
	{
		
	}
	 
	/**
	 * Placeholder for windowBuilder stuff.
	 */
	private void setupLayout()
	{
	
	}
	
	
	/**
	 * Paints the birds and the pipes.
	 * @Override
	 */
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		graphics.drawImage(canvasImage, 0, 0, null);
		
		Graphics2D drawingGraphics = (Graphics2D) graphics;
		
		for (int index = 0; index < birdMap.size(); index++)
		{
			//System.out.println("Drew bird");
			drawingGraphics.setColor(colorMap.get(index));
			drawingGraphics.setStroke(new BasicStroke(2));
			if (birdMap.get(index) != null)
			{
				drawingGraphics.fill(birdMap.get(index));
				drawingGraphics.draw(birdMap.get(index));
			}
			
		}
		
		drawingGraphics.setColor(pipeColor);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(this.topPipe);
		drawingGraphics.fill(this.topPipe);
		
		drawingGraphics.setColor(pipeColor);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(this.bottomPipe);
		drawingGraphics.fill(this.bottomPipe);
	}
	
	/**
	 * Moves the bird based on its key.
	 * @param key
	 * 		The key of the bird that wants to move.
	 */
	public void moveBird(int key)
	{
		if (birdMap.get(key) != null)
		{
			birdMap.get(key).translate(0, (int)(-35));
			repaint();
		}
		 
	}
	
	/**
	 * Draws the top pipe.
	 * @return
	 * 		Returns a Polygon for the top pipe.
	 */
	private Polygon drawTopPipe()
	{
		int lengthPoints = 0;
		if (pipeLayout == 1)
		{
			lengthPoints = 250 + (int)(25 * size);
		}
		else if (pipeLayout == 2)
		{
			lengthPoints = 150;
		}
		else if (pipeLayout == 3)
		{
			lengthPoints = 50;
		}
		else if (pipeLayout == 4)
		{
			lengthPoints = 350 + (int)(50 * size);
		}
		else if (pipeLayout == 5)
		{
			lengthPoints = 450 + (int)(50 * size);
		}

		int[] xPoints = {1000, 1000, 990, 990, 1110, 1110, 1100, 1100, 1000};
		int[] yPoints = {0, lengthPoints, lengthPoints, lengthPoints + 10, lengthPoints + 10, lengthPoints, lengthPoints, 0, 0};
		
		
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
		
		return polygon;
	}
	
	/**
	 * Draws the bottom pipe.
	 * @return
	 * 		A polygon for the bottom pipe.
	 */
	private Polygon drawBottomPipe()
	{
		int lengthPoints = 0;
		if (pipeLayout == 1)
		{
			lengthPoints = 530 - (int)(25 * size);
		}
		else if (pipeLayout == 2)
		{
			lengthPoints = 430 - (int)(50 * size);
		}
		else if (pipeLayout == 3)
		{
			lengthPoints = 330 - (int)(50 * size);
		}
		else if (pipeLayout == 4)
		{
			lengthPoints = 630;
		}
		else if (pipeLayout == 5)
		{
			lengthPoints = 730;
		}
		
		int[] xPoints = {1000, 1000, 990, 990, 1110, 1110, 1100, 1100, 1000};
		int[] yPoints = {800, lengthPoints + 10, lengthPoints + 10, lengthPoints, lengthPoints, lengthPoints + 10, lengthPoints + 10, 800, 800};
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
		
		return polygon;
	}
	
	/**
	 * Draws a bird.
	 * @return
	 * 		A polygonal bird.
	 */
	private Polygon drawBird()
	{
		int[] xPoints = {100, 150, 150, 175, 175, 200, 250, 200, 200, 150, 150, 125, 125, 75, 75, 25, 50, 75, 75, 50, 50,75, 75, 100, 100};
		int[] yPoints = {400, 400, 375, 375, 350, 325, 325, 275, 250, 250, 275, 275, 325, 275, 325, 325, 350, 350, 375, 375, 400, 400, 375, 375, 400};
		
		for (int index = 0; index < xPoints.length; index++)
		{
			xPoints[index] /= 2;
		}
		for (int index = 0; index < yPoints.length; index++)
		{
			yPoints[index] /= 2;
		}
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
				
		return polygon;
	}

	/**
	 * Pauses the Thread.
	 */
	public void pause()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			
		}
	}
	
	/**
	 * Moves the bird down and the pipes left. Also checks if there is collision.
	 */
	public void move()
	{
		panel.updateRound(round);
		if (app.getIsAdvancedBirds())
		{
			for (int index = 0; index < birdMap.size(); index++)
			{
				if (birdMap.get(index) != null)
				{
					Polygon bird = birdMap.get(index);
					bird.translate(0, 14);
					app.fitness(index);
				}
			}
		}
		else
		{
			for (Map.Entry<Integer, Polygon> currentBird : birdMap.entrySet())
			{
				if (currentBird.getValue() != null)
				{
					currentBird.getValue().translate(0, 14);
					app.fitness(currentBird.getKey());
				}
			}
		}
		
		
		
		
		
		topPipe.translate(pipeSpeed, 0);
		bottomPipe.translate(pipeSpeed, 0);
		
		boolean adjust = true;
		for(int current : topPipe.xpoints)
		{
			
			if (current <= 0 )
			{
				size = Math.random() + 1;
				System.out.println(size + "");
				if (size <= 1.33)
				{
					pipeColor = Color.green;
				}
				else if (size <= 1.66)
				{
					pipeColor = Color.yellow;
				}
				else
				{
					pipeColor = Color.red;
				}
				size += 1;
				
				pipeSpeed = ((int)(Math.random() * 20) + 15) * -1;
				System.out.println(pipeSpeed);
				topPipe = drawTopPipe();
				bottomPipe = drawBottomPipe();
				pipeLayout = (int) (Math.random() * 5 + 1);
				panel.addScore();
				if (adjust)
				{
					round++;
				}
				adjust = false;
				
				
			}
		}
		for (Map.Entry<Integer, Polygon> currentBird : birdMap.entrySet())
		{
			if (currentBird.getValue() != null)
			{
				if (currentBird.getValue().intersects(topPipe.getBounds2D()))
				{
					currentBird.setValue(null);
					app.birdDies(currentBird.getKey());
				}
				else if (currentBird.getValue().intersects(bottomPipe.getBounds2D()))
				{
					currentBird.setValue(null);
					app.birdDies(currentBird.getKey());
				}
				else
				{
					for (int point : currentBird.getValue().ypoints)
					{
						if (point > 800 || point < 0)
						{
							currentBird.setValue(null);
							app.birdDies(currentBird.getKey());
						}
					}
				}
			}
		}
		
		
		
		if (round == 10)
		{
			app.reset();
			
		}
		repaint();
	}
	
	
	/**
	 * Grabs the lowest point on the pipe.
	 * @return
	 * 		The lowest point on the pipe.
	 */
	public int getUpperPipe()
	{
		int max = 0;
		
		for (int point : topPipe.ypoints)
		{
			if (point > max)
			{
				max = point;
			}
		}
		
		return max;
	}
	
	/**
	 * Grabs the highest point on the bottom pipe.
	 * @return
	 * 		Highest point.
	 */
	public int getLowerPipe()
	{
		
		int min = 0;
		
		for (int point : bottomPipe.ypoints)
		{
			if (point < min)
			{
				min = point;
			}
		}
		
		return min;
	}
	
	/**
	 * Grabs the average yPoint of the bird for its position.
	 * @param key
	 * 		The key of the bird that you want to grab its position.
	 * @return
	 * 		Returns the y value of its position.
	 */
	public int getBirdPosition(int key)
	{
		if (birdMap.get(key) != null)
		{
			int sum = 0;
			for (int point : birdMap.get(key).ypoints)
			{
				sum += point;
			}
			
			return sum / birdMap.get(key).ypoints.length;
		}
		else
			return 0;
			
	}
	
	/**
	 * Resets the pipes and redraws the birds.
	 */
	public void reset()
	{
		round = 0;
		pipeLayout = (int) (Math.random() * 5 + 1);
		
		this.panel.resetScore();
		
		for (Map.Entry<Integer, Polygon> currentBird : birdMap.entrySet())
		{
			currentBird.setValue(drawBird());
		}
		
		this.topPipe = drawTopPipe();
		
		this.bottomPipe = drawBottomPipe();
		
		repaint();
	}
	
	/**
	 * sets up the bird map.
	 */
	private void setupBirdMap()
	{
		for (int index = 0; index < app.getBirdAmount(); index++)
		{
			Polygon bird = this.drawBird();
			birdMap.put(index, bird);
		}
	}
	
	/**
	 * Gets a random Color.
	 * @return
	 * 		Returns a random color.
	 */
	private Color randomColor()
	{
		int redValue = (int) (Math.random() * 255);
		int greenValue = (int) (Math.random() * 255);
		int blueValue = (int) (Math.random() * 255);
		
		return new Color(redValue, greenValue, blueValue);
	}
	
	/**
	 * Sets up the HashMap of the colors.
	 */
	private void setupColorMap()
	{
		for (int index = 0; index < app.getBirdAmount(); index++)
		{
			Color color = randomColor();
			colorMap.put(index, color);
		}
		
	}
	

	
}
