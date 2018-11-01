package moomaui.presentation;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JToolBar;

import moomaui.domain.DrawableState;
import moomaui.domain.DrawableTransition;
import moomaui.domain.MooreMachine;

import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;

public class MainWindow {

	private JFrame frame;
	private JToolBar toolBar;
	private JButton button;
	private LinkedList<MachinePanel> machinePanels = new LinkedList<MachinePanel>();
	private JTabbedPane pnlMachines;
	private final static Logger ROOT_LOGGER = Logger.getLogger("moomaui");
	private final static Logger LOGGER = Logger.getLogger("moomaui.presentation.MainWindow");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setUpLoggers();
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void setUpLoggers() throws SecurityException, IOException {
		Handler consoleHandler = new ConsoleHandler();
		Handler fileHandler = new FileHandler("./mooma.log", false);
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);

		ROOT_LOGGER.addHandler(consoleHandler);
		ROOT_LOGGER.addHandler(fileHandler);

		consoleHandler.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		
		LOGGER.log(Level.INFO, "Mooma loggers initialized");
	}
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		initializeMachines();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		button = new JButton("New button");
		frame.getContentPane().add(button, BorderLayout.SOUTH);
		
		pnlMachines = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(pnlMachines, BorderLayout.CENTER);
	}
	
	private void initializeMachines() {
		Method[] machineGenerators = Machines.class.getDeclaredMethods();
		for (Method machineGenerator : machineGenerators) {
			MachineCanvas machine = null;
			try {
				// Check if method returns a MachineCanvas
				if (machineGenerator.getReturnType() == MachineCanvas.class)
					machine = (MachineCanvas) machineGenerator.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
			if (machine instanceof MachineCanvas) {
				MachinePanel pnl = new MachinePanel(machine);
				machinePanels.add(pnl);
				pnlMachines.addTab(machine.getMachineName(), pnl);
				LOGGER.log(Level.INFO, String.format("%s added with method %s", machine.getMachineName(), machineGenerator.getName()));
				
			}
		}
	}
}
