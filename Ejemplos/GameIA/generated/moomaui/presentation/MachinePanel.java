package moomaui.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import moomaui.domain.game.Character;
import moomaui.domain.game.Direction;
import moomaui.domain.game.Pawn;

public class MachinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private WorldCanvas lblWorldCanvas;
	private JPanel pnlOutput;
	private JSeparator separator;
	private JPanel pnlSpacers;
	private Component horizontalStrut;
	private JPanel pnlTextOutput;
	private JLabel lblMachineOutput;
	private Component horizontalStrut_1;
	private JPanel pnlMachine;
	
	DefaultListModel<String> lstInputModel = new DefaultListModel<>();
	DefaultListModel<String> lstLogModel = new DefaultListModel<>();
	private JPanel pnlBottom;
	private JTabbedPane pnlOutputTab;
	private JList<String> lstOutputLog;
	public JPanel pnlControls;
	private JButton btnMoveUp;
	private JButton btnMoveLeft;
	private JButton btnMoveDown;
	private JButton btnMoveRight;
	private JSeparator separator_1;
	private JLabel lblCharacterMovement;
	private JLabel lblCharacterPov;
	private JButton btnRotateRight;
	private JButton btnRotateLeft;
	private JSeparator separator_2;
	private JLabel lblWorldSettings;
	private JButton btnTick;
	private JLabel lblTickTime;
	private JTextField txtTickTime;
	
	private Queue<Direction> directions = new LinkedList<>();
	private int rotationStep = 15;

	/**
	 * Create the panel.
	 */
	private MachinePanel() {
		setLayout(new BorderLayout(0, 0));
		
		pnlMachine = new JPanel();
		add(pnlMachine, BorderLayout.CENTER);
		pnlMachine.setLayout(new BorderLayout(0, 0));
		
		pnlOutput = new JPanel();
		pnlOutput.setPreferredSize(new Dimension(325, 325));
		add(pnlOutput, BorderLayout.EAST);
		pnlOutput.setLayout(new BorderLayout(0, 0));
		
		pnlSpacers = new JPanel();
		pnlSpacers.setPreferredSize(new Dimension(5, 5));
		pnlOutput.add(pnlSpacers, BorderLayout.WEST);
		pnlSpacers.setLayout(new BorderLayout(0, 0));
		
		separator = new JSeparator();
		pnlSpacers.add(separator, BorderLayout.WEST);
		separator.setSize(new Dimension(3, 3));
		separator.setMinimumSize(new Dimension(3, 3));
		separator.setOrientation(SwingConstants.VERTICAL);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		pnlSpacers.add(horizontalStrut, BorderLayout.NORTH);
		
		pnlTextOutput = new JPanel();
		pnlOutput.add(pnlTextOutput, BorderLayout.CENTER);
		pnlTextOutput.setLayout(new BorderLayout(0, 0));
		
		//pnlBottomOutput = new JBottomToTopTextAreaPanel();
		
		lblMachineOutput = new JLabel("Machine Output");
		lblMachineOutput.setHorizontalAlignment(SwingConstants.CENTER);
		
		pnlOutputTab = new JTabbedPane(JTabbedPane.TOP);
		pnlTextOutput.add(pnlOutputTab, BorderLayout.CENTER);
		
		pnlControls = new JPanel();
		pnlOutputTab.addTab("Character controls", null, pnlControls, null);
		GridBagLayout gbl_pnlControls = new GridBagLayout();
		gbl_pnlControls.columnWidths = new int[]{77, 50, 34, 0, 76};
		gbl_pnlControls.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 0, 94, 0};
		gbl_pnlControls.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0};
		gbl_pnlControls.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlControls.setLayout(gbl_pnlControls);
		
		btnMoveUp = new JButton("↑");
		btnMoveUp.addActionListener(new BtnMoveUpActionListener());
		btnMoveUp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		lblCharacterMovement = new JLabel("Character movement");
		GridBagConstraints gbc_lblCharacterMovement = new GridBagConstraints();
		gbc_lblCharacterMovement.gridwidth = 3;
		gbc_lblCharacterMovement.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharacterMovement.gridx = 1;
		gbc_lblCharacterMovement.gridy = 0;
		pnlControls.add(lblCharacterMovement, gbc_lblCharacterMovement);
		GridBagConstraints gbc_btnMoveUp = new GridBagConstraints();
		gbc_btnMoveUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveUp.gridx = 2;
		gbc_btnMoveUp.gridy = 1;
		pnlControls.add(btnMoveUp, gbc_btnMoveUp);
		
		btnMoveLeft = new JButton("←");
		btnMoveLeft.addActionListener(new BtnMoveLeftActionListener());
		btnMoveLeft.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnMoveLeft = new GridBagConstraints();
		gbc_btnMoveLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveLeft.gridx = 1;
		gbc_btnMoveLeft.gridy = 2;
		pnlControls.add(btnMoveLeft, gbc_btnMoveLeft);
		
		btnMoveRight = new JButton("→");
		btnMoveRight.addActionListener(new BtnMoveRightActionListener());
		btnMoveRight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnMoveRight = new GridBagConstraints();
		gbc_btnMoveRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveRight.gridx = 3;
		gbc_btnMoveRight.gridy = 2;
		pnlControls.add(btnMoveRight, gbc_btnMoveRight);
		
		btnMoveDown = new JButton("↓");
		btnMoveDown.addActionListener(new BtnMoveDownActionListener());
		btnMoveDown.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnMoveDown = new GridBagConstraints();
		gbc_btnMoveDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveDown.gridx = 2;
		gbc_btnMoveDown.gridy = 3;
		pnlControls.add(btnMoveDown, gbc_btnMoveDown);
		
		separator_1 = new JSeparator();
		separator_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		separator_1.setSize(new Dimension(3, 3));
		separator_1.setMinimumSize(new Dimension(3, 3));
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.gridwidth = 5;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 4;
		pnlControls.add(separator_1, gbc_separator_1);
		
		lblCharacterPov = new JLabel("Character POV");
		GridBagConstraints gbc_lblCharacterPov = new GridBagConstraints();
		gbc_lblCharacterPov.gridwidth = 3;
		gbc_lblCharacterPov.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharacterPov.gridx = 1;
		gbc_lblCharacterPov.gridy = 5;
		pnlControls.add(lblCharacterPov, gbc_lblCharacterPov);
		
		btnRotateLeft = new JButton("←");
		btnRotateLeft.addActionListener(new BtnRotateLeftActionListener());
		btnRotateLeft.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnRotateLeft = new GridBagConstraints();
		gbc_btnRotateLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnRotateLeft.gridx = 1;
		gbc_btnRotateLeft.gridy = 6;
		pnlControls.add(btnRotateLeft, gbc_btnRotateLeft);
		
		btnRotateRight = new JButton("→");
		btnRotateRight.addActionListener(new BtnRotateRightActionListener());
		btnRotateRight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnRotateRight = new GridBagConstraints();
		gbc_btnRotateRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnRotateRight.gridx = 3;
		gbc_btnRotateRight.gridy = 6;
		pnlControls.add(btnRotateRight, gbc_btnRotateRight);
		
		separator_2 = new JSeparator();
		separator_2.setSize(new Dimension(3, 3));
		separator_2.setMinimumSize(new Dimension(3, 3));
		separator_2.setAlignmentX(1.0f);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.gridwidth = 5;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 7;
		pnlControls.add(separator_2, gbc_separator_2);
		
		lblWorldSettings = new JLabel("World Settings");
		GridBagConstraints gbc_lblWorldSettings = new GridBagConstraints();
		gbc_lblWorldSettings.gridwidth = 3;
		gbc_lblWorldSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorldSettings.gridx = 1;
		gbc_lblWorldSettings.gridy = 8;
		pnlControls.add(lblWorldSettings, gbc_lblWorldSettings);
		
		btnTick = new JButton("Tick World");
		btnTick.addActionListener(new BtnTickWorldActionListener());
		
		lblTickTime = new JLabel("Tick time");
		GridBagConstraints gbc_lblTickTime = new GridBagConstraints();
		gbc_lblTickTime.anchor = GridBagConstraints.EAST;
		gbc_lblTickTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTickTime.gridx = 1;
		gbc_lblTickTime.gridy = 9;
		pnlControls.add(lblTickTime, gbc_lblTickTime);
		
		txtTickTime = new JTextField();
		txtTickTime.setText("0.5");
		GridBagConstraints gbc_txtTickTime = new GridBagConstraints();
		gbc_txtTickTime.gridwidth = 2;
		gbc_txtTickTime.insets = new Insets(0, 0, 5, 5);
		gbc_txtTickTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTickTime.gridx = 2;
		gbc_txtTickTime.gridy = 9;
		pnlControls.add(txtTickTime, gbc_txtTickTime);
		txtTickTime.setColumns(10);
		GridBagConstraints gbc_btnTick = new GridBagConstraints();
		gbc_btnTick.anchor = GridBagConstraints.NORTH;
		gbc_btnTick.gridwidth = 3;
		gbc_btnTick.insets = new Insets(0, 0, 5, 5);
		gbc_btnTick.gridx = 1;
		gbc_btnTick.gridy = 10;
		pnlControls.add(btnTick, gbc_btnTick);
		
		lstOutputLog = new JList<String>(lstLogModel);
		lstOutputLog.setPreferredSize(new Dimension(175, 0));
		pnlOutputTab.addTab("Log", null, lstOutputLog, null);
		
		pnlBottom = new JPanel();
		pnlTextOutput.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		horizontalStrut_1 = Box.createHorizontalStrut(-128);
		horizontalStrut_1.setPreferredSize(new Dimension(4, 0));
		pnlTextOutput.add(horizontalStrut_1, BorderLayout.EAST);
		lblWorldCanvas = null; //new MachineCanvas(new MachineController(new MooreMachine()));
	}
	
	public MachinePanel(WorldCanvas canvas) {
		this();
		this.lblWorldCanvas = canvas;
		this.lblWorldCanvas.machinePanel = this;
		pnlMachine.add(lblWorldCanvas, BorderLayout.CENTER);
		canvas.getWorld().getPawnsFromClass(Character.class).forEach((Pawn ch) -> ((Character) ch).addDamageListener(this::logDamageToPlayer));
	}
	
	private void logDamageToPlayer(Pawn instigator, int damage) {
		lstLogModel.addElement(String.format("Player has suffered %d damage from Pawn %d", damage, instigator.getId()));
	}
	
	public void logDeath(Pawn deadPawn, Pawn killerPawn) {
		lstLogModel.addElement(String.format("Player has been killed by pawn %d\n", deadPawn.getId(), killerPawn.getId()));
		
		if (deadPawn instanceof Character) {
			lstLogModel.addElement("Your character has been killed!");
			lstLogModel.addElement("Game stopped!");
		}
	}
	
	public WorldCanvas getMachineCanvas() {
		return lblWorldCanvas;
	}
	
	private class BtnTickWorldActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double time = Double.parseDouble(txtTickTime.getText());
			Character ch = (Character) lblWorldCanvas.getWorld().getPawnsFromClass(Character.class).get(0);

			ch.moveInDirection(directions.toArray(new Direction[0]));
			directions.clear();
			lblWorldCanvas.getWorld().tick(time);
			lblWorldCanvas.updatePawns();
			
			btnMoveUp.setEnabled(true);
			btnMoveDown.setEnabled(true);
			btnMoveRight.setEnabled(true);
			btnMoveLeft.setEnabled(true);
		}
	}
	
	private void offerDirection(Direction dir) {
		if (directions.size() >= 2) {
			Direction oldDirection = directions.poll();
			directions.add(dir);
			
			switch (oldDirection) {
			case UP:
				btnMoveUp.setEnabled(true);
				break;
			case DOWN:
				btnMoveDown.setEnabled(true);
				break;
			case RIGHT:
				btnMoveRight.setEnabled(true);
				break;
			case LEFT:
				btnMoveLeft.setEnabled(true);
				break;
			}
		} else {
			directions.add(dir);
		}
		
		switch (dir) {
		case UP:
			btnMoveUp.setEnabled(false);
			break;
		case DOWN:
			btnMoveDown.setEnabled(false);
			break;
		case RIGHT:
			btnMoveRight.setEnabled(false);
			break;
		case LEFT:
			btnMoveLeft.setEnabled(false);
			break;
		}
	}
	
	private class BtnMoveUpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			offerDirection(Direction.UP);
		}
	}
	private class BtnMoveLeftActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			offerDirection(Direction.LEFT);
		}
	}
	private class BtnMoveDownActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			offerDirection(Direction.DOWN);
		}
	}
	private class BtnMoveRightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			offerDirection(Direction.RIGHT);
		}
	}
	
	private void rotateCharacter(int degrees) {
		Character ch = (Character) lblWorldCanvas.getWorld().getPawnsFromClass(Character.class).get(0);
		
		ch.setAngle(ch.getAngle() + Math.toRadians(degrees));
		lblWorldCanvas.updatePawns();
	}
	
	private class BtnRotateLeftActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rotateCharacter(rotationStep);
		}
	}
	private class BtnRotateRightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rotateCharacter(rotationStep * -1);			
		}
	}
}
