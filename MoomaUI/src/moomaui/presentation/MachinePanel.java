package moomaui.presentation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import moomaui.domain.MachineSimulator;
import moomaui.presentation.drawing.DrawableState;
import moomaui.presentation.drawing.DrawableTransition;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.DebugGraphics;
import java.awt.Component;
import javax.swing.Box;

public class MachinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtInput;
	private MachineCanvas lblMachineCanvas;
	private MachineSimulator<DrawableState, DrawableTransition> simulator;
	private JPanel pnlOutput;
	private JBottomToTopTextAreaPanel pnlBottomOutput;
	private JSeparator separator;
	private JPanel pnlSpacers;
	private Component horizontalStrut;
	private JPanel pnlTextOutput;
	private JLabel lblMachineOutput;
	private Component verticalStrut;
	private Component horizontalStrut_1;
	private JPanel pnlMachine;

	/**
	 * Create the panel.
	 */
	private MachinePanel() {
		setLayout(new BorderLayout(0, 0));
		
		pnlMachine = new JPanel();
		add(pnlMachine, BorderLayout.CENTER);
		pnlMachine.setLayout(new BorderLayout(0, 0));
		
		txtInput = new JTextField();
		pnlMachine.add(txtInput, BorderLayout.SOUTH);
		txtInput.setColumns(10);
		txtInput.getDocument().addDocumentListener(new TextChangeListener());
		
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
		
		pnlBottomOutput = new JBottomToTopTextAreaPanel();
		
		lblMachineOutput = new JLabel("Machine Output");
		lblMachineOutput.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTextOutput.add(lblMachineOutput, BorderLayout.NORTH);
		pnlTextOutput.add(pnlBottomOutput, BorderLayout.CENTER);
		
		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 4));
		pnlTextOutput.add(verticalStrut, BorderLayout.SOUTH);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(4, 0));
		pnlTextOutput.add(horizontalStrut_1, BorderLayout.EAST);
		lblMachineCanvas = new MachineCanvas();
		
		simulator = new MachineSimulator<DrawableState, DrawableTransition>(lblMachineCanvas);
	}
	
	public MachinePanel(MachineCanvas canvas) {
		this();
		this.lblMachineCanvas = canvas;
		this.simulator.setMachine(canvas);
		pnlMachine.add(lblMachineCanvas, BorderLayout.CENTER);
		lblMachineCanvas.addMouseMotionListener(new CanvasMouseMotionListener());
		lblMachineCanvas.addMouseListener(new CanvasMouseListener());
		simulator.setCurrentState(lblMachineCanvas.getStates().get(0));
	}
	
	public MachineCanvas getMachineCanvas() {
		return lblMachineCanvas;
	}
	
	private class CanvasMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			DrawableState st = lblMachineCanvas.getStateInPosition(e.getX(), e.getY());
			if (st != null && st.getAction() != null) { // TODO: Remove code
				st.getAction().output();
			}
			if (st != null) {
				lblMachineCanvas.setText("Single Click  State: " + st.getName());
				lblMachineCanvas.setSelectedState(st);
			} else {
				lblMachineCanvas.setText("Single Click  " + e.getX() + "  " + e.getY());
			}
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
			changed();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("Removal, index: " + e.getOffset());
			changed();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			//System.out.println("Changed, index: " + e.getOffset());
			changed();
		}
		
		public void changed() {
			if (txtInput.getText().equals(""))
				simulator.setCurrentState(lblMachineCanvas.getStates().get(0));
			else if (txtInput.getText().endsWith(" ")) {
				String[] inputs = txtInput.getText().split(" ");
				for (String input : inputs) {
					if (!simulator.addNewInput(input)) {
						System.out.format("No available transition from %s with input %s, aborting\n", simulator.getCurrentState().getName(), input);
						break;
					}
				}
			}
		}
		
	}

}
