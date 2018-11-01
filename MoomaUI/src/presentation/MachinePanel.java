package presentation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import domain.DrawableState;

import javax.swing.JScrollPane;
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
	
	private class CanvasMouseListener extends MouseDoubleClickListener {
		@Override
		public void mouseClickedNormal(MouseEvent e) {
			DrawableState st = lblMachineCanvas.getStateInPosition(e.getX(), e.getY());
			if (st != null) {
				lblMachineCanvas.setText("Single Click  State: " + st.getName());
				lblMachineCanvas.setSelectedState(st);
			} else {
				lblMachineCanvas.setText("Single Click  " + e.getX() + "  " + e.getY());
			}
		}
		@Override
		public void mouseDoubleClicked(MouseEvent e) {
			lblMachineCanvas.setText("Double Click");
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
				lblMachineCanvas.getSelectedState().setCoords(e.getX(), e.getY());
				lblMachineCanvas.repaint();
			}
		}
	}

}
