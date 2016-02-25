package resources;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;


public class FrmOpAOI extends JInternalFrame {
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmOpAOI frame = new FrmOpAOI();
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
	public FrmOpAOI() {
		setTitle("AOI");
		setBounds(100, 100, 180, 289);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 66, 144, 55);
		getContentPane().add(panel);
		
		JRadioButton radioButton = new JRadioButton("Dispon\u00EDvel");
		radioButton.setBounds(6, 23, 109, 23);
		panel.add(radioButton);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 36, 86, 20);
		getContentPane().add(textField_1);
		
		JLabel label_1 = new JLabel("Produto");
		label_1.setBounds(10, 11, 46, 14);
		getContentPane().add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Defeito", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 128, 144, 216);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox chckbxAprovada = new JCheckBox("Aprovada");
		chckbxAprovada.setBounds(6, 23, 97, 23);
		panel_1.add(chckbxAprovada);
		
		JCheckBox chckbxPlaca = new JCheckBox("Placa");
		chckbxPlaca.setBounds(6, 49, 97, 23);
		panel_1.add(chckbxPlaca);
		
		JCheckBox chckbxComponente = new JCheckBox("Componente");
		chckbxComponente.setBounds(6, 75, 97, 23);
		panel_1.add(chckbxComponente);
		
		JCheckBox chckbxSolda = new JCheckBox("Solda");
		chckbxSolda.setBounds(6, 101, 97, 23);
		panel_1.add(chckbxSolda);

	}

}
