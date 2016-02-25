package resources;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;


public class FrmConfiguracaoProducao extends JInternalFrame implements ActionListener{
	private JTextField tfQuantidade;
	private JComboBox cbProduto;
	private JButton btnGerarConfigurao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmConfiguracaoProducao frame = new FrmConfiguracaoProducao();
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
	@SuppressWarnings("unchecked")
	public FrmConfiguracaoProducao() {
		setClosable(true);
		setTitle("Configura\u00E7\u00E3o de Produ\u00E7\u00E3o");
		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sele\u00E7\u00E3o dos itens a produzir", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 464, 211);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		cbProduto = new JComboBox();
		cbProduto.setModel(new DefaultComboBoxModel(new String[] {"", "Produto 1", "Produto 2", "Produto 3"}));
		cbProduto.setBounds(10, 52, 114, 20);
		panel.add(cbProduto);
		
		tfQuantidade = new JTextField();
		tfQuantidade.setBounds(134, 52, 86, 20);
		panel.add(tfQuantidade);
		tfQuantidade.setColumns(10);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 27, 46, 14);
		panel.add(lblProduto);
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(134, 27, 92, 14);
		panel.add(lblQuantidade);
		
		btnGerarConfigurao = new JButton("Gerar configura\u00E7\u00E3o");
		btnGerarConfigurao.setBounds(10, 91, 125, 23);
		panel.add(btnGerarConfigurao);
		btnGerarConfigurao.addActionListener(this);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Configura\u00E7\u00E3o do comportamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 233, 464, 127);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox cbHabilitaEspecialista = new JCheckBox("Habilitar o agente especialista - Controle de Qualidade");
		cbHabilitaEspecialista.setBounds(20, 32, 438, 23);
		panel_1.add(cbHabilitaEspecialista);
		
		JCheckBox cbVisualizaJade = new JCheckBox("Visualizar a interface JADE");
		cbVisualizaJade.setBounds(20, 61, 438, 23);
		panel_1.add(cbVisualizaJade);
		
		JCheckBox cbVisualizaJason = new JCheckBox("Visualizar a interface Jason");
		cbVisualizaJason.setBounds(20, 87, 438, 23);
		panel_1.add(cbVisualizaJason);

	}
	public JComboBox getComboBox() {
		return cbProduto;
	}
	public JTextField getTextField() {
		return tfQuantidade;
	}
	public JButton getBtnGerarConfigurao() {
		return btnGerarConfigurao;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnGerarConfigurao == e.getSource())
		{
            Replacer.replace("codigoproduto", Integer.toString(cbProduto.getSelectedIndex()), "qtdproduto", tfQuantidade.getText());
			
		}
		
	}
}
