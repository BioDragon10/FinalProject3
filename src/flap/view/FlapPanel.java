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

import javax.swing.JPanel;

import flap.controller.Controller;

public class FlapPanel extends JPanel
{
	private Controller app;
	private MainPanel panel;
	private BufferedImage canvasImage;
	
	private Polygon bird;
	private int birdX;
	private int birdY;
	
	private Polygon topPipe;
	private Polygon bottomPipe;
	
	private int pipeLayout;
	
	public FlapPanel(Controller app, MainPanel panel)
	{
		this.panel = panel;
		this.app = app;
		this.pipeLayout = 1;
		
		this.canvasImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		
		this.setBird(drawBird());
		this.topPipe = drawTopPipe();
		this.bottomPipe = drawBottomPipe();
	
		
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
		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent click)
			{
				if (bird != null)
				{
					bird.translate(0, -10);
					System.out.println("Click");
					for (int current : bird.ypoints)
					{
						System.out.println(current);
					}
					moveBird();
				}
				
			}
			
			public void mousePressed(MouseEvent press)
			{
				
			}
			
			public void mouseReleased(MouseEvent release)
			{
				
			}
			
			public void mouseEntered(MouseEvent enter)
			{
				
			}
			
			public void mouseExited(MouseEvent exit)
			{
				
			}
		});
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
		
		if(this.bird != null)
		{
			
			drawingGraphics.setColor(Color.green);
			drawingGraphics.setStroke(new BasicStroke(2));
			drawingGraphics.fill(this.bird);
			drawingGraphics.draw(this.bird);
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
	
	public void moveBird()
	{
		bird.translate(0, -30);
		repaint();
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
		//int[] xPoints = {800, 800, 790, 790, 910, 910, 900, 900, 800};
		int[] xPoints = {1000, 1000, 990, 990, 1110, 1110, 1100, 1100, 1000};
		//int[] yPoints = {0, 100, 100, 110, 110, 100, 100, 0, 0};
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
		int[] xPoints = {100, 150, 150, 170, 170, 150, 150, 120};
		int[] yPoints = {400, 400, 370, 370, 340, 340, 310, 310};
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
				
		return polygon;
	}
	
	public void setBird(Polygon polygon)
	{
		this.bird = polygon;
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
		if (this.bird != null)
		{
			bird.translate(0, 7);
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
		if (this.bird != null)
		{
			if (bird.intersects(topPipe.getBounds2D()))
			{
				this.bird = null;
				app.birdDies();
			}
			else if (bird.intersects(bottomPipe.getBounds2D()))
			{
				this.bird = null;
				app.birdDies();
			}
			else
			{
				for (int point : bird.ypoints)
				{
					if (point > 800 || point < 0)
					{
						System.out.println(point);
						this.bird = null;
						app.birdDies();
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
	
	public int getBirdPosition()
	{
		int sum = 0;
		for (int point : bird.ypoints)
		{
			sum += point;
		}
		
		return sum / bird.ypoints.length;
	}
	
}
