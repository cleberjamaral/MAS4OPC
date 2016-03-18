package resources;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;


public class FrmPrincipal extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnSupervisrio;
	private JButton btnConfigurao;
	FrmSupervisorio supervisorio;
	FrmConfiguracaoProducao configuracao;
	private JScrollPane scrollPane;
	private JDesktopPane desktopPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("Criando interface principal...");  

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal frame = new FrmPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("Interface principal criada.");  
	}

	/**
	 * Create the frame.
	 */
	public FrmPrincipal() {
		setTitle("LabElectron - Linha de Pequenas S\u00E9ries - SMD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnSupervisrio = new JButton("Supervis\u00F3rio");
		btnSupervisrio.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		toolBar.add(btnSupervisrio);
		
		btnConfigurao = new JButton("Configura\u00E7\u00E3o");
		//btnConfigurao.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/com/sun/javafx/scene/web/skin/UnorderedListBullets_16x16_JFX.png")));
		toolBar.add(btnConfigurao);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		desktopPane = new JDesktopPane();
		desktopPane.setPreferredSize(new Dimension(1800,1024));
		desktopPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(desktopPane);
		btnSupervisrio.addActionListener(this);
		btnConfigurao.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSupervisrio)
		{
			supervisorio = new FrmSupervisorio();
			desktopPane.add(supervisorio);
			supervisorio.show();
		}
		else if (e.getSource() == btnConfigurao)
		{
			configuracao = new FrmConfiguracaoProducao();
			desktopPane.add(configuracao);
			configuracao.show();
		}
		
	}

	protected JButton getBtnSupervisrio() {
		return btnSupervisrio;
	}
	protected JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public synchronized  void  setAgentStatus(int i, String status) {
		if (supervisorio != null)
		{
			supervisorio.updateStatus(i, status);
		}
	}

	public synchronized void setAgentStatus(MensagemSupervisorio ms) {
		supervisorio.update(ms);
	}
}
