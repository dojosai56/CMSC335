package SimulationObjects;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car {
	public int x, y;
	public int speed, topspeed;
	public String name;
	
	public Car(String name,int x, int y, int topspeed) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.topspeed = topspeed;
		this.speed = 0;
	}
	public String toString() {
		return name + "|" + "x: " + x + "m, y: " + y + "m, speed: " + speed + "mps.";
	}
}
