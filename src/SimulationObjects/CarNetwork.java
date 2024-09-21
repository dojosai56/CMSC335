package SimulationObjects;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import SimulationObjects.Simulation.State;

public class CarNetwork implements Runnable{

	private ArrayList<Car> cars;
	private Clock clock;
	private Simulation.State state;
	private TrafficLightNetWork trafficLights;
	private CarNetworkCallbackFunction callback;
	private ReadWriteLock lock;
	public CarNetwork(int numCars, Clock clock, TrafficLightNetWork netrwork,CarNetworkCallbackFunction c) {
		cars = new ArrayList<Car>(numCars);
		for(int i = 0; i < numCars; i++) {
			cars.add(new Car(i+"", 0 + (i*50), 0, 5));
		}
		this.clock = clock;
		trafficLights = netrwork;
		callback = c;
		lock = new ReentrantReadWriteLock();
	} // CarNetwork
	
	public void updateNetwork() {
		for(Car car : cars) {
			for(TrafficLight light : trafficLights.getLights()) {
				if(car.x <= light.x && car.x >= light.x-10) {
					if(light.getState() == TrafficLight.STATE.RED) {
						car.x = car.x;
						car.speed = 0;
					}else if(light.getState() == TrafficLight.STATE.YELLOW) {
						car.speed = car.topspeed;
						car.x = car.x + car.speed;
					}else if(light.getState() == TrafficLight.STATE.GREEN) {
						car.speed = car.topspeed;
						car.x = car.x + car.speed;
					}
				}else {
					car.speed = car.topspeed;
					car.x += car.speed;
					
				}
			}
		}
	}
	
	@Override
	public void run() {
		state = Simulation.State.RUNNING;
		
		long currentTime, elapsedTime;
		long lastUpdateTime = clock.getTimeInSeconds();
		
		while(state != State.STOPPED) {
			if(state == Simulation.State.PAUSED) {
				Thread.yield();
				continue;
			}
			currentTime = clock.getTimeInSeconds();
			elapsedTime = currentTime - lastUpdateTime;
			
			if(elapsedTime >= 1) {
				updateNetwork();
				callback.updateGUI(this.getData());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	} // run
	public void setState(Simulation.State s) {
		this.state = s;
	}
	public String[] getData() {
		lock.readLock().lock();
		String[] data = new String[cars.size()];
		for(int i = 0; i < cars.size();i++) {
			data[i] = cars.get(i).toString();
		}
		lock.readLock().unlock();
		return data;
		
	}
	
	public void addCar(String n, int x, int speed) {
		cars.add(new Car(n, x, 0, speed));
	}

} // CarNetwork
