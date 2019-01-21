package moomaui.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JBottomToTopTextAreaPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea txtArea;
	private PrintStream txtStream;

    public JBottomToTopTextAreaPanel() {
    	this.txtArea = new JTextArea();
    	this.txtStream = new PrintStream(new StreamableTextArea(this.txtArea));
    	
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel filler = new JPanel();
        filler.setBackground(Color.WHITE);
        add(filler, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        txtArea = new JTextArea(1, 20);
        add(txtArea, gbc);
    }

	public JTextArea getTxtArea() {
		return txtArea;
	}

	public void setTxtArea(JTextArea txtArea) {
		this.txtArea = txtArea;
	}

	public PrintStream getTxtStream() {
		return txtStream;
	}

	public void setTxtStream(PrintStream txtStream) {
		this.txtStream = txtStream;
	}
}
