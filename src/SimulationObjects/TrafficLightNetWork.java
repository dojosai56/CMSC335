package SimulationObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import SimulationObjects.Simulation.State;

/**
 * 
 * Maintains a network of traffic lights 
 * 
 * Features
 *  -> 1D network of TrafficLights
 *  -> RealTime 
 *  Requirements:
 *   -> 
 * 
 *     
 */
public class TrafficLightNetWork implements Runnable{
	private TrafficNetworkCallbackFunction callback;
	private Simulation.State state;
	private Clock clock;
	private List<TrafficLight> lights, lightsToAdd;
	private ReadWriteLock lock;
	boolean inLoop;
	public TrafficLightNetWork(TrafficNetworkCallbackFunction f, Clock clock) {
		callback = f;
		this.clock = clock;
		
		ArrayList<TrafficLight> l = new ArrayList<TrafficLight>();
		//for(int i = 0; i < 3;i++) {
		l.add(new TrafficLight(1000 + (1000 * 0),0,10));
		l.add(new TrafficLight(1000 + (1000 * 1),0,5));
		l.add(new TrafficLight(1000 + (1000 * 2),0,15));
		//}
		lights = Collections.synchronizedList(l);
			lock = new ReentrantReadWriteLock();
	}
	@Override
	public void run() {
		System.out.println("traffic network started");
		state = Simulation.State.RUNNING;
		
		long currentTime, elapsedTime;
		long lastUpdateTime = clock.getTimeInSeconds();
		boolean updateSuccessful = false;
		while(state != State.STOPPED) {
			if(state == Simulation.State.PAUSED) {
				Thread.yield();
				continue;
			}
			currentTime = clock.getTimeInSeconds();
			//elapsedTime = currentTime - lastUpdateTime;
			//System.out.println(elapsedTime + "," + currentTime + "," + lastUpdateTime);
			//System.out.println(currentTime);
	
			for(TrafficLight light : lights) {
				//inLoop = true;
				if(light.updateState(currentTime)) {
					updateSuccessful = true;
					light.setLastUpdateTime(clock.getTimeInSeconds());
					//lastUpdateTime = clock.getTimeInSeconds();
					//System.out.println(light.getState().toString());
				} 
			} // loop through each light
			//inLoop = false;
		//	if(lightsToAdd != null && lightsToAdd.size() >0) {
	//			lights.addAll(lightsToAdd);
	//		}
			if(updateSuccessful) {
				lock.readLock().lock();
				callback.updateGUI(lights);
				lock.readLock().unlock();
				updateSuccessful = false;
			}
			
		}// timing loop to update each traffic light's state
		
	}
	
	public List<TrafficLight> getLights() {
		List<TrafficLight> l = new ArrayList<TrafficLight>();
		for(TrafficLight light : lights) {
			l.add(new TrafficLight(light.x, light.y, light.green_period));
		}
		return l;
	}
	
	public void setState(Simulation.State s) {
		this.state = s;
	}
	public void addIntersection(String string, int xPos, int redPeriod, int greenPeriod, int yellowPeriod) {	
		//lock.writeLock().lock();
		lightsToAdd = new ArrayList<TrafficLight>();
		System.out.println("adding intersection");
		lightsToAdd.add(new TrafficLight(xPos,0,greenPeriod));
		//System.out.println(lights.size());
		//lock.writeLock().unlock();
	}
	public synchronized void removeIntersection(String string) {
		lights.remove(lights.size()-1);
		
	}
} // TrafficLightThread
