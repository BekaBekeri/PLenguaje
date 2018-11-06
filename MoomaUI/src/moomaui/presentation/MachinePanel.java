package moomaui.presentation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import moomaui.domain.DrawableState;

import javax.swing.JLabel;

public class MachinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtInput;
	private MachineCanvas lblMachineCanvas;
	private JLabel lblOla;

	/**
	 * Create the panel.
	 */
	private MachinePanel() {
		setLayout(new BorderLayout(0, 0));
		
		txtInput = new JTextField();
		add(txtInput, BorderLayout.SOUTH);
		txtInput.setColumns(10);
		
		lblOla = new JLabel("ola");
		add(lblOla, BorderLayout.CENTER);
		lblMachineCanvas = new MachineCanvas();
	}
	
	public MachinePanel(MachineCanvas canvas) {
		this();
		this.lblMachineCanvas = canvas;
		add(lblMachineCanvas, BorderLayout.CENTER);
		lblMachineCanvas.addMouseMotionListener(new CanvasMouseMotionListener());
		lblMachineCanvas.addMouseListener(new CanvasMouseListener());
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
				if (e.getX() > 0 && e.getX() < MachinePanel.this.getSize().getWidth()) {
					lblMachineCanvas.getSelectedState().setCoords(e.getX(), lblMachineCanvas.getSelectedState().getY());
				}
				if (e.getY() > 0 && e.getY() < MachinePanel.this.getSize().getHeight()) {
					lblMachineCanvas.getSelectedState().setCoords(lblMachineCanvas.getSelectedState().getX(), e.getY());
				}
				lblMachineCanvas.repaint();
			}
		}
	}

}
