package resources;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;


public class FrmOpForno extends JInternalFrame {
	private JTextField tfTemp;
	private JTextField textField_1;
	private JTextField tfVel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmOpForno frame = new FrmOpForno();
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
	public FrmOpForno() {
		setTitle("Forno");
		setBounds(100, 100, 180, 300);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 123, 144, 137);
		getContentPane().add(panel);
		
		JRadioButton radioButton = new JRadioButton("Dispon\u00EDvel");
		radioButton.setBounds(6, 17, 109, 23);
		panel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Opera\u00E7\u00E3o");
		radioButton_1.setBounds(6, 57, 109, 23);
		panel.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("Conclu\u00EDdo");
		radioButton_2.setBounds(6, 97, 109, 23);
		panel.add(radioButton_2);
		
		tfTemp = new JTextField();
		tfTemp.setColumns(10);
		tfTemp.setBounds(10, 92, 63, 20);
		getContentPane().add(tfTemp);
		
		JLabel lblTc = new JLabel("Vel(mm/s)");
		lblTc.setBounds(78, 67, 57, 14);
		getContentPane().add(lblTc);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 36, 86, 20);
		getContentPane().add(textField_1);
		
		JLabel label_1 = new JLabel("Produto");
		label_1.setBounds(10, 11, 46, 14);
		getContentPane().add(label_1);
		
		tfVel = new JTextField();
		tfVel.setColumns(10);
		tfVel.setBounds(78, 92, 63, 20);
		getContentPane().add(tfVel);
		
		JLabel label = new JLabel("T(\u00BAC)");
		label.setBounds(10, 67, 63, 14);
		getContentPane().add(label);

	}

}
