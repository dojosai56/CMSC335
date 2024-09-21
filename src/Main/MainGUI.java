package Main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import SimulationObjects.CarNetworkCallbackFunction;
import SimulationObjects.ClockCallbackFunction;
import SimulationObjects.Simulation;
import SimulationObjects.TrafficLight;
import SimulationObjects.TrafficNetworkCallbackFunction;

public class MainGUI {
	
	/* GUI components */
	private JFrame frame;
	// main panel
	private JPanel main_panel;
	private GridLayout main_layout;
	
	// Time Stamp Panel
	private JPanel timestamp_panel;
	private JLabel timestamp_label;
	// Traffic Light Panel
	private TrafficLightsPanel trafficLights_panel;
	// Car Speed/Position Data Panel
	private JPanel carData_panel;
	// Add Data Panel
	private JPanel addSimulationObjects_panel;
	private JTextField newCarX_field, newCarSpeed_field;
	private JLabel newCarName_label, newIntersection_name;
	private JButton addCar, addLight, removeLight;
	private JButton start, stop, resume, pause;
	/*Simulation*/
	Simulation trafficSimualtion;
	
	public MainGUI() {
		TrafficLight.loadImages();
		createAndShowGUI();
		initSimulation();
	}// constructor
	
	private void initSimulation(){
		// initialize Simulation object
		trafficSimualtion = new Simulation();
		trafficSimualtion.initialize((ClockCallbackFunction) new ClockCallback(),
				(TrafficNetworkCallbackFunction)new TrafficLightNetworkCallback(), (CarNetworkCallbackFunction)new CarNetworkCallback());
	} // initSimulation
	
	
	private void createAndShowGUI() {
		frame = new JFrame("Traffic Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main_panel = new JPanel();
		main_layout = new GridLayout(2,2);
		main_panel.setLayout(main_layout);
		
		
		createTimeStampPanel();
		createTrafficLightsPanel();
		createCarDataPanel();
		createAddSimulationObjectsPanel();
		
		main_panel.add(timestamp_label);
		main_panel.add(trafficLights_panel);
		main_panel.add(carData_panel);
		main_panel.add(addSimulationObjects_panel);
		
		frame.add(main_panel);
		frame.pack();
		//frame.setSize(800,600);
		frame.setVisible(true);
	}
	/**
	 * Creates/Configures the timestamp panel and its dependent GUI components
	 * 
	 */
	private void createTimeStampPanel() {
		
		timestamp_label = new JLabel("2021/14/2021 12:11:ss PM");
		
		timestamp_panel = new JPanel();
		timestamp_panel.setPreferredSize(new Dimension(400,300));
		timestamp_panel.add(timestamp_label);
		
	} // createTimeStampPanel
	
	/**
	 * Creates/Configures the panel displaying the traffic light of each major intersection.
	 * Capabilities:
	 * 	-> display a image of each traffic light that updates in realtime
	 */
	private void createTrafficLightsPanel() {
		trafficLights_panel = new TrafficLightsPanel(400, 300);
		
	} // createTrafficLightsPanel
	
	/**
	 * Creates/Configures the panel for car speed and positional info.
	 * Capabilities:
	 * 	-> view list of cars in simulation with position/speed data
	 *  -> Add/Remove cars from above list. 
	 *  	-> Add: x-pos, y-pos?, speed, name(or number)
	 *  	-> remove car from list
	 */
	private void createCarDataPanel() {
		carData_panel = new JPanel();
		carData_panel.setPreferredSize(new Dimension(400,300));
	} // createCarDataPanel
	
	
	/*
	 * 	// Add Data Panel
	private JPanel addSimulationObjects_panel;
	private JTextField newCarX_field, newCarSpeed_field;
	private JLabel newCarName_label, newIntersection_name;
	 */
	private void createAddSimulationObjectsPanel() {
		addSimulationObjects_panel = new JPanel();
		addSimulationObjects_panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		//addSimulationObjects_panel.setPreferredSize(new Dimension(400,300));
		
		newCarName_label = new JLabel("New Car:");
		c.gridx = 0;
		c.gridy = 0;
		addSimulationObjects_panel.add(newCarName_label,c);
		
		newCarX_field = new JTextField("x-pos", 10);
		c.gridx = 1;
		c.gridy = 0;
		addSimulationObjects_panel.add(newCarX_field,c);
		
		newCarSpeed_field = new JTextField("speed", 10);
		c.gridx = 2;
		c.gridy = 0;
		addSimulationObjects_panel.add(newCarSpeed_field,c);
		
		addCar = new JButton("Add");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		addSimulationObjects_panel.add(addCar,c);
		c.gridwidth = 1;
		newIntersection_name = new JLabel("Intersection: ");
		c.gridx = 0;
		c.gridy = 2;
		addSimulationObjects_panel.add(newIntersection_name,c);
		
		addLight = new JButton("Add Intersection");
		c.gridx = 1;
		c.gridy = 2;
		addSimulationObjects_panel.add(addLight,c);
		
		removeLight = new JButton("Remove Intersection");
		c.gridx = 2;
		c.gridy = 2;
		addSimulationObjects_panel.add(removeLight,c);
		
		addCar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int speed  = (int) Double.parseDouble(newCarSpeed_field.getText());
					int xPos = (int) Double.parseDouble(newCarX_field.getText());
					trafficSimualtion.addCar(xPos, speed);
				}catch(Exception e) {
					//showErrorDialog("Coordinates must be positive real number!");
				}
				
			} // actionPerformed
			
		});
		
		addLight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.addIntersection(20,20,5, 1000);//red,gree,yellow,xPos
			} // actionPerformed
			
		});
		
		addLight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.removeIntersection();
			} // actionPerformed
			
		});
		
		stop = new JButton("stop");
		c.gridx = 0;
		c.gridy = 3;
		addSimulationObjects_panel.add(stop, c);
		
		start = new JButton("start");
		c.gridx = 1;
		c.gridy = 3;
		addSimulationObjects_panel.add(start, c);
		
		resume = new JButton("resume");
		c.gridx = 2;
		c.gridy = 3;
		addSimulationObjects_panel.add(resume, c);
		
		pause = new JButton("pause");
		c.gridx = 3;
		c.gridy = 3;
		addSimulationObjects_panel.add(pause, c);
		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.removeIntersection();
				trafficSimualtion.start((ClockCallbackFunction) new ClockCallback(),
						(TrafficNetworkCallbackFunction)new TrafficLightNetworkCallback(), (CarNetworkCallbackFunction)new CarNetworkCallback());
			} // actionPerformed
			
		});
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.removeIntersection();
				trafficSimualtion.stop();
			} // actionPerformed
			
		});
		resume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.removeIntersection();
				trafficSimualtion.resume();
			} // actionPerformed
			
		});
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//trafficSimualtion.removeIntersection();
				trafficSimualtion.pause();
			} // actionPerformed
			
		});
		
	} // createAddSimulationObjectePanel
	
	public static void main(String[] args) {
		MainGUI myGuI = new MainGUI();
		
	} // main
	
	// class for Clock callback function
	// This will get called from a non-UI thread
	class ClockCallback implements ClockCallbackFunction {
		public ClockCallback(){
			
		}
		// this gets called from non-UI thread therefore must use invokeLater
		@Override
		public void updateGUI(String date) {
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			      // Here, we can safely update the GUI
			      // because we'll be called from the
			      // event dispatch thread
			    	timestamp_label.setText(date);
			    }
			  });
			
		} //updateGUI
	} // ClockCallback
	
	// class for TrafficLight callback function
	// This will get called from a non-UI thread
	class TrafficLightNetworkCallback implements TrafficNetworkCallbackFunction {

		@Override
		public void updateGUI(List<TrafficLight> lights) {
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			      // Here, we can safely update the GUI
			      // because we'll be called from the
			      // event dispatch thread
			    trafficLights_panel.drawLights(lights);
			    }
			  });
		} // updateGUI
		
	} // TrafficLightNetworkCallback
	
	class CarNetworkCallback implements CarNetworkCallbackFunction {
		@Override
		public void updateGUI(String[] cars) {
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			      // Here, we can safely update the GUI
			      // because we'll be called from the
					JList<String> myList = new JList<String>(cars);
					 
					 JScrollPane scrollPane = new JScrollPane(myList);
					 
					 carData_panel.removeAll();
					 carData_panel.add(scrollPane);
			    }
			  });
		}
	}

} // MainGUI
