package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import SimulationObjects.TrafficLight;

public class TrafficLightsPanel extends JPanel{
	private int panel_height, panel_width;
	BufferedImage canvas; 
	
	public TrafficLightsPanel(int width, int height) {
		super();
		this.setPreferredSize(new Dimension(width, height));
	} // constructor

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(canvas == null) {
			int w = this.getWidth();
			int h = this.getHeight();
			
			canvas = (BufferedImage)(this.createImage(w, h));
			
			Graphics2D bImgG2d = canvas.createGraphics();
			bImgG2d.setColor(Color.WHITE);
			bImgG2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		} // create canvas
		
		// call to draw canvas onto this panels Graphics obj
		draw(g2d);
	} // paintComponent
	
	private void draw(Graphics2D g2d) {
		if(canvas == null)
			return;
		g2d.drawImage(canvas, panel_width, panel_height, this);
	} // draw
	
	public void drawLights(List<TrafficLight> lights) {
		if(canvas == null) {
			return;
		}
		
		// code to draw onto canvas BufferedImage
		Graphics2D bImgG2d = canvas.createGraphics();
		bImgG2d.setColor(Color.WHITE);
		bImgG2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		bImgG2d.setColor(Color.BLACK);
				
		repaint(); // calls the paintComponent to repaint
		int i =0;
		System.out.println(lights.size());
		
		synchronized(this) {
		for(TrafficLight light : lights) {
				//int firstX, secX = this., thirdX;
				light.draw(bImgG2d, panel_width/2 + (74 * i++), panel_height/2);
				//System.out.println(light.toString());
			}
			//System.out.println("-----------------");	
		}
		
	} // drawLights
	
}
