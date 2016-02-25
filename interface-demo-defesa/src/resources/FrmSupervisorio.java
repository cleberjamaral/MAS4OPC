package resources;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class FrmSupervisorio extends JInternalFrame {
	private JTextPane taUnloader;
	private JTextPane taAOI;
	private JTextPane taOven;
	private JTextPane taPastePrinter;
	private JTextPane taLoader;
	private JTextPane taPickPlace;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSupervisorio frame = new FrmSupervisorio();
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
	public FrmSupervisorio() {
		setResizable(true);
		setClosable(true);
		setBounds(100, 100, 1038, 725);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1022,696));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(164, 362, 158, 119);
		panel.add(scrollPane_1);
		
		taLoader = new JTextPane();
		scrollPane_1.setViewportView(taLoader);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(497, 362, 158, 119);
		panel.add(scrollPane_2);
		
		taPickPlace = new JTextPane();
		scrollPane_2.setViewportView(taPickPlace);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(164, 545, 158, 119);
		panel.add(scrollPane_3);
		
		taPastePrinter = new JTextPane();
		scrollPane_3.setViewportView(taPastePrinter);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(729, 545, 265, 119);
		panel.add(scrollPane_4);
		
		taOven = new JTextPane();
		scrollPane_4.setViewportView(taOven);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(836, 362, 158, 119);
		panel.add(scrollPane_5);
		
		taAOI = new JTextPane();
		scrollPane_5.setViewportView(taAOI);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(836, 184, 158, 119);
		panel.add(scrollPane_6);
		
		taUnloader = new JTextPane();
		scrollPane_6.setViewportView(taUnloader);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 10, 10);
		panel.add(panel_1);
		
		JButton btnNewButton = new JButton("Limpar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taAOI.setText("");
				taLoader.setText("");
				taOven.setText("");
				taPastePrinter.setText("");
				taPickPlace.setText("");
				taUnloader.setText("");
			}
		});
		btnNewButton.setBounds(566, 280, 89, 23);
		panel.add(btnNewButton);
		
		JLabel fundo = new JLabel("");
		fundo.setPreferredSize(new Dimension(1022, 696));
		fundo.setBounds(0, 0, 1022, 696);
		fundo.setIcon(new ImageIcon("novo-supervisorio.jpg"));		
		panel.add(fundo);
	}

	public void updateStatus(int i, String status) {
		taLoader.setText(status);
		//updateUI();
		System.out.println(status);
	}
	protected JTextPane getTaLoader() {
		return taLoader;
	}

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
    
	public void update(MensagemSupervisorio ms) {
		Color cor = Color.BLACK;
		String status;
		switch (ms.status) {
			case 0:
				status = "STOPPED";
				cor = Color.RED;
				break;
			case 1:
				status = "IDLE";
				break;
			case 2:
				status = "WAIT";
				cor = Color.GREEN;
				break;
			case 3:
				status = "LOADED";
				break;
			case 4:
				status = "READY";
				break;
			case 5:
				status = "PAUSE";
				break;
			case 6:
				status = "DEFECT";
				break;
	
			default:
				status = "Status não reconhecido";
				break;
		}
		status += "\n";
		JTextPane painelFocado = null;
		if (ms.maquina == 1)
		{
			painelFocado = taLoader;
		}
		else if (ms.maquina == 2)
		{
			painelFocado = taPastePrinter;
		}
		else if (ms.maquina == 3)
		{
			painelFocado = taPickPlace;	
		}
		else if (ms.maquina == 4)
		{
			painelFocado = taOven;			
		}
		else if (ms.maquina == 5)
		{
			painelFocado = taAOI;
		}
		else if (ms.maquina == 6)
		{
			painelFocado = taUnloader;			
		}
		if (painelFocado != null)
		{
			appendToPane(painelFocado, status,  cor);
			painelFocado.setCaretPosition(painelFocado.getDocument().getLength());
		}
		System.out.println(status);
		
	}
}
