package resources;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;


public class FrmOpInsersora extends JInternalFrame {
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmOpInsersora frame = new FrmOpInsersora();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmOpInsersora() {
		setTitle("Insersora");
		setBounds(100, 100, 180, 300);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 123, 144, 137);
		getContentPane().add(panel);
		
		JRadioButton radioButton = new JRadioButton("Dispon\u00EDvel");
		buttonGroup.add(radioButton);
		radioButton.setBounds(6, 17, 109, 23);
		panel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Opera\u00E7\u00E3o");
		buttonGroup.add(radioButton_1);
		radioButton_1.setBounds(6, 57, 109, 23);
		panel.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("Conclu\u00EDdo");
		buttonGroup.add(radioButton_2);
		radioButton_2.setBounds(6, 97, 109, 23);
		panel.add(radioButton_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 36, 86, 20);
		getContentPane().add(textField_1);
		
		JLabel label_1 = new JLabel("Produto");
		label_1.setBounds(10, 11, 46, 14);
		getContentPane().add(label_1);

	}

}
