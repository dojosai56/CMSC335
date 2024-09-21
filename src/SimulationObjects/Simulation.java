package SimulationObjects;

/**
 * Class the controls the traffic intersection 
 */
public class Simulation{
	/* simulation state */
	static enum State {STOPPED, RUNNING, PAUSED}
	private State state;
	
	// simulation threads 
	private Thread trafficLightNetwork_thread;
	private Thread clock_thread;
	private Thread carNetwork_thread;
	
	// simulation objects
	private Clock clock;
	private TrafficLightNetWork trafficLights;
	private CarNetwork cars;
	private int numCars, numLights;
	public void initialize(ClockCallbackFunction updateGUI_clock, TrafficNetworkCallbackFunction updateGUI_trafficLights, CarNetworkCallbackFunction updateGUI_cars) {
		
		numCars = 3;
		numLights = 3;
		clock = new Clock(updateGUI_clock);
		trafficLights = new TrafficLightNetWork(updateGUI_trafficLights, clock);
		cars = new CarNetwork(numCars, clock, trafficLights, updateGUI_cars);
		
		clock_thread = new Thread(clock);
		trafficLightNetwork_thread = new Thread(trafficLights);
		carNetwork_thread = new Thread(cars);
		
		clock_thread.start();
		
		trafficLightNetwork_thread.start();
		carNetwork_thread.start();
	} // initialize
	
	public void start(ClockCallbackFunction updateGUI_clock, TrafficNetworkCallbackFunction updateGUI_trafficLights, CarNetworkCallbackFunction updateGUI_cars) {
		initialize(updateGUI_clock, updateGUI_trafficLights, updateGUI_cars);
		
	} // start
	
	public void pause() {
		trafficLights.setState(State.PAUSED);
		cars.setState(State.PAUSED);
		clock.setState(State.PAUSED);
	} // pause
	
	public void stop() {
		trafficLights.setState(State.STOPPED);
		cars.setState(State.STOPPED);
		clock.setState(State.STOPPED);
	} // stop
	
	public void resume() {
		trafficLights.setState(State.RUNNING);
		cars.setState(State.RUNNING);
		clock.setState(State.RUNNING);
	} // resume
	
	public void addCar(int xPos, int topSpeed) {
		cars.addCar(numCars +"",xPos, topSpeed);
		numCars++;
	}
	
	public synchronized void addIntersection(int redPeriod, int greenPeriod, int yellowPeriod, int xPos) {
		numLights++;
		trafficLights.addIntersection(numLights+"", xPos, redPeriod, greenPeriod, yellowPeriod);
	}

	public synchronized void removeIntersection() {
		trafficLights.removeIntersection((numLights-1) + "");
	}
} // Simulation
