package flap.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import flap.controller.Controller;

public class FlapPanel extends JPanel
{
	private Controller app;
	private BufferedImage canvasImage;
	
	private Polygon bird;
	private int birdX;
	private int birdY;
	
	public FlapPanel(Controller app)
	{
		this.app = app;
		
		this.canvasImage = new BufferedImage(1200, 850, BufferedImage.TYPE_INT_ARGB);
		
		this.setBird(drawBird());
		
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
				bird.translate(0, -10);
				System.out.println("Click");
				for (int current : bird.ypoints)
				{
					System.out.println(current);
				}
				moveBird();
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
		drawingGraphics.setColor(Color.green);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.fill(this.bird);
		drawingGraphics.draw(this.bird);
	}
	
	private void moveBird()
	{
		bird.translate(0, -10);
		drawCanvas();
	}
	
	private void drawCanvas()
	{
		Graphics2D drawingGraphics = (Graphics2D) canvasImage.getGraphics();
		
		Polygon topPipe = drawTopPipe();
		drawingGraphics.setColor(Color.green);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(topPipe);
		drawingGraphics.fill(topPipe);
		
		Polygon bottomPipe = drawBottomPipe();
		drawingGraphics.setColor(Color.green);
		drawingGraphics.setStroke(new BasicStroke(2));
		drawingGraphics.draw(bottomPipe);
		drawingGraphics.fill(bottomPipe);
		
//		Polygon bird = drawBird();


//		System.out.println("drw bird");
//		

		
		repaint();
	}
	
	private Polygon drawTopPipe()
	{
		int[] xPoints = {800, 800, 790, 790, 910, 910, 900, 900, 800};
		int[] yPoints = {0, 100, 100, 110, 110, 100, 100, 0, 0};
		
		Polygon polygon = new Polygon(xPoints, yPoints, xPoints.length);
		
		return polygon;
	}
	
	private Polygon drawBottomPipe()
	{
		int[] xPoints = {800, 800, 790, 790, 910, 910, 900, 900, 800};
		int[] yPoints = {800, 700, 700, 690, 690, 700, 700, 800, 800};
		
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
	
}
