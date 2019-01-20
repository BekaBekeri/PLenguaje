package moomaui.presentation;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import moomaui.domain.IMooreMachine;
import moomaui.domain.MachineController;
import moomaui.domain.Machines;
import moomaui.domain.game.Character;
import moomaui.domain.game.Enemy;
import moomaui.domain.game.World;

public class MainWindow {
	private static final int N_PAWNS = 4;
	private static final int SAFE_MARGINS = 40;
	private static final int[] CHARACTER_POS = new int[] {290, 250};

	private JFrame frame;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) throws IOException {
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
	
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public MainWindow() {
		initialize();
		initializeWorld();
		
		frame.setTitle("Mooma UI");
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowFocusListener(new FrameWindowFocusListener());
		frame.setBounds(100, 100, 966, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	private void initializeWorld() {
		Random rand = new Random();
		World world = new World();
		for (int i = 0; i < N_PAWNS; i++) {
			int x = rand.nextInt(500 - SAFE_MARGINS) + SAFE_MARGINS;
			int y = rand.nextInt(400 - SAFE_MARGINS) + SAFE_MARGINS;
			if (WorldCanvas.euclideanDistance(x, CHARACTER_POS[0], y, CHARACTER_POS[1]) <= Enemy.ATTACK_RANGE) {
				i--; // Current machine cant handle being spawned near the character, ensure this doesnt happen
				continue;
			}
				
			IMooreMachine machine = Machines.MeleeEnemy();
					
			Enemy p = (Enemy) world.spawnPawn(Enemy.class);
			p.setX(x);
			p.setY(y);
			p.setHealth(100);
			p.setMachine(new MachineController(machine));
		}
		
		Character ch = (Character) world.spawnPawn(Character.class);
		ch.setX(CHARACTER_POS[0]); ch.setY(CHARACTER_POS[1]);
		ch.setHealth(100);
		ch.setAngle(0);

		MachinePanel pnl = new MachinePanel(new WorldCanvas(world));
		frame.getContentPane().add(pnl, BorderLayout.CENTER);
	}
	
	private boolean requestFocusOnCurrentTextInput() {
		return true;
		//return ((MachinePanel)pnlMachines.getSelectedComponent()).getTxtInput().requestFocusInWindow();
	}
	private class FrameWindowFocusListener implements WindowFocusListener {
		public void windowGainedFocus(WindowEvent e) {
			requestFocusOnCurrentTextInput();
		}
		public void windowLostFocus(WindowEvent e) {
		}
	}
}
