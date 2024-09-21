package SimulationObjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 
 * @author Aleck
 * Traffic Light:
 *  -> 3 states
 *     -> green
 *     -> yellow
 *     -> red
 * 	Requirements:
 */
public class TrafficLight {
	
	public static  enum STATE {GREEN, YELLOW, RED, OFF};
	public static Image redlight, greenlight, yellowlight;
	
	public Image currentImg;
	public int x, y, width, height;
	private STATE currentState;
	private long lastUpdateTime;
	
	public int yellow_period, red_period, green_period;
	
	public TrafficLight(int x, int y, int period) {
		currentState = STATE.RED;
		currentImg = redlight;
		this.x = x;
		this.y = y;
		this.green_period = period;
		this.red_period = period;
		this.yellow_period = 2;
	} // default constructor
	
	// return true if updates
	public boolean updateState(long currentTime) {
		long elapsedTime = currentTime - lastUpdateTime;
		if(currentState == STATE.GREEN) {
			if(elapsedTime >= green_period) {
				currentState = STATE.YELLOW;
				currentImg = yellowlight;
				return true;
			}
		}else if(currentState == STATE.YELLOW) {
			if(elapsedTime >= yellow_period) {
				currentState = STATE.RED;
				currentImg = redlight;
				return true;
			}
		}else if(currentState == STATE.RED) {
			if(elapsedTime >= red_period) {
				currentState = STATE.GREEN;
				currentImg = greenlight;
				return true;
			}
		}
		
		return false;
	} // updateState
	
	public STATE getState() {
		return this.currentState;
	} // getState
	
    public static void loadImages() {
    	try {
    		redlight = ImageIO.read(new File("res/trafficlight_red.png"));
    		greenlight = ImageIO.read(new File("res/trafficlight_green.png"));
    		yellowlight = ImageIO.read(new File("res/trafficlight_yellow.png"));
    		
    	}catch(Exception e) {
    		System.out.println("path not found");
    	}
 
    
    }
    
    public void setLastUpdateTime(long timeInSec) {
    	lastUpdateTime = timeInSec;
    }
    public int getPeriod() {
    	return green_period;
    }
    
    public void draw(Graphics2D g, int xpos, int ypos) {
    	if(currentImg == null)
    		return;
    	
    	g.drawImage(currentImg,xpos, ypos, null);
    } // draw
	
	public String toString() {
		return "x: "  +x + "," + currentState.toString();
	}
	
} // TrafficLight
