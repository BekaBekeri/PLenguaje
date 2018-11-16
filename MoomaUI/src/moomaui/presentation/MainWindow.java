package moomaui.presentation;

import java.awt.BorderLayout;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import moomaui.domain.IMooreMachine;
import moomaui.domain.MachineController;
import moomaui.domain.Machines;

public class MainWindow {

	private JFrame frame;
	private LinkedList<MachinePanel> machinePanels = new LinkedList<MachinePanel>();
	private JTabbedPane pnlMachines;
	private final static Logger ROOT_LOGGER = Logger.getLogger("moomaui");
	private final static Logger LOGGER = Logger.getLogger("moomaui.presentation.MainWindow");

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) throws IOException {
		setUpLoggers();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
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
		ROOT_LOGGER.setUseParentHandlers(false);
		
		LOGGER.log(Level.INFO, "Mooma loggers initialized");
	}
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public MainWindow() {
		initialize();
		initializeMachines();
		
		/*if (machinePanels.size() == 0) {
			ROOT_LOGGER.log(Level.SEVERE, "No machines could be added, exiting.");
			System.exit(1);
		}*/
		
		frame.setTitle("Mooma UI");
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlMachines = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(pnlMachines, BorderLayout.CENTER);
	}
	
	private void initializeMachines() {
		Method[] machineGenerators = Machines.class.getDeclaredMethods();
		for (Method machineGenerator : machineGenerators) {
			IMooreMachine machine = null;
			try {
				// Check if method is static
				if (!Modifier.isStatic(machineGenerator.getModifiers())) {
					ROOT_LOGGER.log(Level.WARNING, String.format("Method %s is not static and its machine will not be added.", machineGenerator.getName()));
					continue;
				} // Check if method returns a MachineCanvas
				else if (machineGenerator.getReturnType() == IMooreMachine.class)
					machine = (IMooreMachine) machineGenerator.invoke(null);
				else
					continue;
			} catch (NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				ROOT_LOGGER.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			
			MachineController<String> mController = new MachineController<String>(machine);

			MachinePanel pnl = new MachinePanel(new MachineCanvas(mController));
			machinePanels.add(pnl);
			pnlMachines.addTab(machine.getMachineName(), pnl);
			LOGGER.log(Level.INFO, String.format("%s added with method %s", machine.getMachineName(), machineGenerator.getName()));
		}
	}
}
