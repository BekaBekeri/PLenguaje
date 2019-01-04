package moomaui.presentation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import moomaui.domain.IState;
import moomaui.domain.MachineController;
import moomaui.domain.MachineSimulator;
import moomaui.domain.MooreMachine;
import moomaui.presentation.drawing.DrawableState;
import moomaui.presentation.drawing.DrawableTransition;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.DebugGraphics;
import javax.swing.DefaultListModel;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MachinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtInput;
	private MachineCanvas lblMachineCanvas;
	private JPanel pnlOutput;
	private JSeparator separator;
	private JPanel pnlSpacers;
	private Component horizontalStrut;
	private JPanel pnlTextOutput;
	private JLabel lblMachineOutput;
	private Component horizontalStrut_1;
	private JPanel pnlMachine;
	
	private DrawableState lastPaintedState;
	private JList<String> lstInputs;
	DefaultListModel<String> lstInputModel = new DefaultListModel<>();
	private JPanel pnlBottom;
	private JPanel pnlInteract;
	private JButton btnAddInput;
	private JButton btnRemoveInput;
	private JPanel pnlButtons;
	private JLabel lblMachineInputs;

	/**
	 * Create the panel.
	 */
	private MachinePanel() {
		setLayout(new BorderLayout(0, 0));
		
		pnlMachine = new JPanel();
		add(pnlMachine, BorderLayout.CENTER);
		pnlMachine.setLayout(new BorderLayout(0, 0));
		
		pnlOutput = new JPanel();
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
		
		lblMachineInputs = new JLabel("Machine inputs");
		lblMachineInputs.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTextOutput.add(lblMachineInputs, BorderLayout.NORTH);
		//pnlTextOutput.add(lblMachineOutput, BorderLayout.NORTH);
		//pnlTextOutput.add(pnlBottomOutput, BorderLayout.CENTER);
		
		lstInputs = new JList<>(lstInputModel);
		lstInputs.setPreferredSize(new Dimension(175, 0));
		pnlTextOutput.add(lstInputs, BorderLayout.CENTER);
		
		pnlBottom = new JPanel();
		pnlTextOutput.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		pnlInteract = new JPanel();
		pnlBottom.add(pnlInteract, BorderLayout.CENTER);
		pnlInteract.setLayout(new BorderLayout(0, 0));
		
		txtInput = new JTextField();
		txtInput.addKeyListener(new TxtInputKeyListener());
		pnlInteract.add(txtInput);
		txtInput.setColumns(10);
		
		pnlButtons = new JPanel();
		pnlInteract.add(pnlButtons, BorderLayout.SOUTH);
		
		btnAddInput = new JButton("Add Input");
		btnAddInput.addActionListener(new BtnAddInputActionListener());
		pnlButtons.add(btnAddInput);
		
		btnRemoveInput = new JButton("Remove Input");
		btnRemoveInput.addActionListener(new BtnRemoveInputActionListener());
		pnlButtons.add(btnRemoveInput);
		txtInput.getDocument().addDocumentListener(new TextChangeListener());
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(4, 0));
		pnlTextOutput.add(horizontalStrut_1, BorderLayout.EAST);
		lblMachineCanvas = null; //new MachineCanvas(new MachineController(new MooreMachine()));
	}
	
	public MachinePanel(MachineCanvas canvas) {
		this();
		this.lblMachineCanvas = canvas;
		pnlMachine.add(lblMachineCanvas, BorderLayout.CENTER);
		lblMachineCanvas.addMouseMotionListener(new CanvasMouseMotionListener());
		lblMachineCanvas.addMouseListener(new CanvasMouseListener());
		
		lblMachineCanvas.getController().getCurrentState().getOutput().accept(lblMachineCanvas.getController().getEnvironment());
	}
	
	public MachineCanvas getMachineCanvas() {
		return lblMachineCanvas;
	}
	
	public Component getTxtInput() {
		return txtInput;
	}
		
	public void resetState() {
		if (lastPaintedState != null && !lblMachineCanvas.getCurrentState().equals(lastPaintedState)) { 
			lastPaintedState.setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_DEFAULT);
		} else if (lastPaintedState != null) {
			lastPaintedState.setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_SELECTED);
		}
		
		lastPaintedState = null;			
	}
	
	public void updateCandidateState(String input) {
		IState currentState = lblMachineCanvas.getController().getCurrentState();
		IState destination = lblMachineCanvas.getController().getTransitionDestination(
				currentState.getName(), lblMachineCanvas.getController().translate(input));
		
		if (destination != null) {
			for (DrawableState state : lblMachineCanvas.getStates()) {
				if (state.equals(new DrawableState(destination.getName()))) {
					state.setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_CANDIDATE);
					lastPaintedState = state;
				}
			}
		}
	}
	
	public void updateCurrentState(DrawableState newCurrentState) {
		lblMachineCanvas.getCurrentState().setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_DEFAULT);
		
		for (DrawableState state : lblMachineCanvas.getStates()) {
			if (state.equals(newCurrentState)) {
				state.setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_SELECTED);
				lblMachineCanvas.setCurrentState(state);
			}
		}
	}
	
	private class CanvasMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			DrawableState st = lblMachineCanvas.getStateInPosition(e.getX(), e.getY());
			if (st != null)
				lblMachineCanvas.setSelectedState(st);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			lblMachineCanvas.setSelectedState(null);
		}
	}
	private class CanvasMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (lblMachineCanvas.getSelectedState() != null) {
				if (e.getX() > 0 && e.getX() < MachinePanel.this.pnlMachine.getSize().getWidth()) {
					lblMachineCanvas.getSelectedState().setCoords(e.getX(), lblMachineCanvas.getSelectedState().getY());
				}
				if (e.getY() > 0 && e.getY() < MachinePanel.this.pnlMachine.getSize().getHeight()) {
					lblMachineCanvas.getSelectedState().setCoords(lblMachineCanvas.getSelectedState().getX(), e.getY());
				}
				lblMachineCanvas.repaint();
			}
		}
	}
	private class TextChangeListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			//System.out.println("Insertion, index: " + e.getOffset());
			changed(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("Removal, index: " + e.getOffset());
			changed(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			//System.out.println("Changed, index: " + e.getOffset());
			changed(e);
		}
		
		public void changed(DocumentEvent e) {
			resetState();			
			if (txtInput.getText().length() > 0) {
				updateCandidateState(txtInput.getText());
			}
			lblMachineCanvas.repaint();
		}		
	}
	private class BtnAddInputActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			IState nextState = lblMachineCanvas.getController().addNewInput(txtInput.getText().trim());
			if (nextState != null) {
				lstInputModel.addElement(String.format("%s,%s -> %s", lblMachineCanvas.getController().getPreviousState().getName(), txtInput.getText().trim(), nextState.getName()));
				MachinePanel.this.updateCurrentState(new DrawableState(nextState.getName()));
				lastPaintedState = null;
				nextState.getOutput().accept(lblMachineCanvas.getController().getEnvironment());
				
				txtInput.setText("");
			} else {
			}
			lblMachineCanvas.repaint();
		}
	}
	private class BtnRemoveInputActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!lstInputModel.isEmpty()) {
				IState newCurrentState = lblMachineCanvas.getController().removeInput();
				lstInputModel.removeElementAt(lstInputModel.getSize() - 1);
				MachinePanel.this.updateCurrentState(new DrawableState(newCurrentState.getName()));
				lastPaintedState = null;			
			}
			lblMachineCanvas.repaint();
		}
	}
	private class TxtInputKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnAddInput.doClick();
			}
		}
	}

}
