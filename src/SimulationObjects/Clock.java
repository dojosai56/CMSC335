package SimulationObjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock implements Runnable{
	
	private LocalDateTime currentDate;
	private Simulation.State state;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	private ClockCallbackFunction callback;
	
	public Clock(ClockCallbackFunction c) {
		callback = c;
		state = Simulation.State.STOPPED;
		
		currentDate = LocalDateTime.now();
		
	} // constructor
	
	@Override
	public void run() {
		System.out.println("Clock thread started");
		long lastUpdateTime, elapsedTime, sleepTime=1000000000L;
		
		state = Simulation.State.RUNNING;
		
		// initialize the clock
		currentDate = LocalDateTime.now();
		lastUpdateTime = System.nanoTime();
		
		while(state != Simulation.State.STOPPED) {
			if(state == Simulation.State.PAUSED) {
				Thread.yield();
				continue;
			}
			
			elapsedTime = System.nanoTime() - lastUpdateTime;
			
			if(elapsedTime >= sleepTime) {
				// update once every ~sec
				currentDate = LocalDateTime.now();
				callback.updateGUI((getCurrentTime())); // update GUI with callback
				lastUpdateTime = System.nanoTime();
			}else {
				// otherwise sleep remaining time to let other threads do work
				try {
					Thread.sleep((sleepTime-elapsedTime)/ 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
					
		} // Clock Update loop
		
	} // run
	public void setState(Simulation.State s) {
		this.state = s;
	}
	public synchronized int getYear() {
		return currentDate.getYear();
	} // getYear
	
	public synchronized int getMonthHand() {
		return currentDate.getMonth().getValue();
	} // getMonth
	
	public synchronized int getDay() {
		return currentDate.getDayOfYear();
	} // getMonth
	
	public synchronized int getHourHand() {
		return currentDate.getHour();
	} // getMonth
	
	public synchronized int getMinuteHand() {
		return currentDate.getMinute();
	} // getMonth
	
	public synchronized int getSecondHand() {
		return currentDate.getSecond();
	} // getMonth
	
	public synchronized long getTimeInSeconds() {
		return (currentDate.getHour() * 60 * 60) + (currentDate.getMinute() * 60) + currentDate.getSecond();
	}
	
	public synchronized String getCurrentTime() {
		return currentDate.format(DATE_FORMAT);
	} // getCurrentTime
} // Clock
