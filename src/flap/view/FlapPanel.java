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

public class FlapPanel extends JPanel
{
	private Controller app;
	private MainPanel panel;
	private BufferedImage canvasImage;
	
	private Polygon topPipe;
	private Polygon bottomPipe;
	
	private int pipeLayout;
	
	private HashMap<Integer, Polygon> birdMap;
	private HashMap<Integer, Color> colorMap;
	
	private double size;
	
	public FlapPanel(Controller app, MainPanel panel)
	{
		this.panel = panel;
		this.app = app;
		this.pipeLayout = 1;
		
		this.size = 1.0;
		
		this.canvasImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		
		this.topPipe = drawTopPipe();
		this.bottomPipe = drawBottomPipe();
		this.birdMap = new HashMap<Integer, Polygon>();
		this.colorMap = new HashMap<Integer, Color>();
	
		setupBirdMap();
		setupColorMap();
		
		setupPanel();
		
		setupListeners();
		
		setupLayout();
	}
	
	private void setupPanel()
	{
		this.setMinimumSize(new Dimension(800, 800));
		this.setBackground(Color.blue);
		drawCanvas();
	}
	
	private void setupListeners()
	{
		
	}
	 
	private void setupLayout()
	{
	
	}
	
	@Override
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
		
		drawingGraphics.setColor(Color.green);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(this.topPipe);
		drawingGraphics.fill(this.topPipe);
		
		drawingGraphics.setColor(Color.green);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(this.bottomPipe);
		drawingGraphics.fill(this.bottomPipe);
	}
	
	public void moveBird(int key)
	{
		if (birdMap.get(key) != null)
		{
			birdMap.get(key).translate(0, -30);
			repaint();
		}
		 
	}
	
	private void drawCanvas()
	{
		Graphics2D drawingGraphics = (Graphics2D) canvasImage.getGraphics();
		
		
		
		
		
//		Polygon bird = drawBird();


//		System.out.println("drw bird");
//		

		
		repaint();
	}
	
	private Polygon drawTopPipe()
	{
		int lengthPoints = 0;
		if (pipeLayout == 1)
		{
			lengthPoints = 250 + 25;
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
			lengthPoints = 350 + 50;
		}
		else if (pipeLayout == 5)
		{
			lengthPoints = 450 + 50;
		}

		int[] xPoints = {1000, 1000, 990, 990, 1110, 1110, 1100, 1100, 1000};
		int[] yPoints = {0, lengthPoints, lengthPoints, lengthPoints + 10, lengthPoints + 10, lengthPoints, lengthPoints, 0, 0};
		
		
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
		
		return polygon;
	}
	
	private Polygon drawBottomPipe()
	{
		int lengthPoints = 0;
		if (pipeLayout == 1)
		{
			lengthPoints = 530 - 25;
		}
		else if (pipeLayout == 2)
		{
			lengthPoints = 430 - 50;
		}
		else if (pipeLayout == 3)
		{
			lengthPoints = 330 - 50;
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
	
	//Placeholder, still needs polishing.
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
	
	public void move()
	{
		
		for (Map.Entry<Integer, Polygon> currentBird : birdMap.entrySet())
		{
			if (currentBird.getValue() != null)
			{
				currentBird.getValue().translate(0, 7);
				app.fitness(currentBird.getKey());
			}
		}
		
		
		topPipe.translate(-10, 0);
		bottomPipe.translate(-10, 0);
		
		for(int current : topPipe.xpoints)
		{
			if (current <= 0 )
			{
				topPipe = drawTopPipe();
				bottomPipe = drawBottomPipe();
				pipeLayout = (int) (Math.random() * 5 + 1);
				panel.addScore();
				
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
		
		
		
		
		repaint();
	}
	
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
	
	public void reset()
	{
		pipeLayout = 1;
		
		this.panel.resetScore();
		
		for (Map.Entry<Integer, Polygon> currentBird : birdMap.entrySet())
		{
			currentBird.setValue(drawBird());
		}
		
		this.topPipe = drawTopPipe();
		
		this.bottomPipe = drawBottomPipe();
		
		repaint();
	}
	
	private void setupBirdMap()
	{
		for (int index = 0; index < app.getBirdAmount(); index++)
		{
			Polygon bird = this.drawBird();
			birdMap.put(index, bird);
		}
	}
	
	private Color randomColor()
	{
		int redValue = (int) (Math.random() * 255);
		int greenValue = (int) (Math.random() * 255);
		int blueValue = (int) (Math.random() * 255);
		
		return new Color(redValue, greenValue, blueValue);
	}
	
	private void setupColorMap()
	{
		for (int index = 0; index < app.getBirdAmount(); index++)
		{
			Color color = randomColor();
			colorMap.put(index, color);
		}
		
	}

	
}
