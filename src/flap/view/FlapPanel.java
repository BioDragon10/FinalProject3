package flap.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import flap.controller.Controller;

public class FlapPanel extends JPanel
{
	private Controller app;
	private BufferedImage canvasImage;
	
	public FlapPanel(Controller app)
	{
		this.app = app;
		
		this.canvasImage = new BufferedImage(1200, 850, BufferedImage.TYPE_INT_ARGB);
		
		setupPanel();
		
		setupListeners();
		
		setupLayout();
	}
	
	private void setupPanel()
	{
		this.setMinimumSize(new Dimension(800, 800));
		this.setBackground(Color.blue);
		updateCanvas();
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
	}
	
	private void updateCanvas()
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
}
