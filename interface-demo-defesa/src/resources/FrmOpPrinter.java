package resources;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class FrmOpPrinter extends JInternalFrame {
	private JTextField tfProduto;
	private JTextField tfPressao;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmOpPrinter frame = new FrmOpPrinter();
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
	public FrmOpPrinter() {
		setTitle("Printer");
		setBounds(100, 100, 180, 300);
		getContentPane().setLayout(null);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 11, 46, 14);
		getContentPane().add(lblProduto);
		
		tfProduto = new JTextField();
		tfProduto.setBounds(10, 36, 86, 20);
		getContentPane().add(tfProduto);
		tfProduto.setColumns(10);
		
		JLabel lblPressopa = new JLabel("Press\u00E3o (Pa)");
		lblPressopa.setBounds(10, 67, 86, 14);
		getContentPane().add(lblPressopa);
		
		tfPressao = new JTextField();
		tfPressao.setBounds(10, 92, 86, 20);
		getContentPane().add(tfPressao);
		tfPressao.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 123, 144, 137);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton rdbtnDisponvel = new JRadioButton("Dispon\u00EDvel");
		buttonGroup.add(rdbtnDisponvel);
		rdbtnDisponvel.setBounds(6, 17, 109, 23);
		panel.add(rdbtnDisponvel);
		
		JRadioButton rdbtnOperao = new JRadioButton("Opera\u00E7\u00E3o");
		buttonGroup.add(rdbtnOperao);
		rdbtnOperao.setBounds(6, 57, 109, 23);
		panel.add(rdbtnOperao);
		
		JRadioButton rdbtnConcludo = new JRadioButton("Conclu\u00EDdo");
		buttonGroup.add(rdbtnConcludo);
		rdbtnConcludo.setBounds(6, 97, 109, 23);
		panel.add(rdbtnConcludo);

	}
}
