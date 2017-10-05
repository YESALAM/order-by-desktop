package Formularios;

import java.awt.EventQueue;

import javax.security.auth.Destroyable;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import bd.Conexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfirmarCadastroFunc extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	public static Boolean libera=false;

	private static ConfirmarCadastroFunc dialog;
	private static Conexao conexao =Login.conexao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					dialog = new ConfirmarCadastroFunc();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ConfirmarCadastroFunc() {
		setBounds(100, 100, 343, 263);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
	
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(23, 15, 284, 192);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel Ususenha = new JPanel();
		Ususenha.setBounds(28, 58, 226, 82);
		panel.add(Ususenha);
		Ususenha.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Ususenha.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usu\u00E1rio: ");
		lblNewLabel.setBounds(10, 18, 43, 14);
		Ususenha.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha: ");
		lblNewLabel_1.setBounds(10, 46, 37, 14);
		Ususenha.add(lblNewLabel_1);
		
		textField = new JTextField("");
		textField.setBounds(57, 15, 152, 20);
		Ususenha.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField("");
		textField_1.setBounds(57, 43, 152, 20);
		Ususenha.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			if(textField.getText().equals(FramePai.usuarioatual.getCPF().toString()) && textField_1.getText().equals(FramePai.usuarioatual.getSenha().toString())){//verifica se as informações fornecidas coincidem com o usuario logado, caso sim libera o cadastramento
				libera=true;

					setVisible(false);
					dispose();
					//volta a confirmação do funcionario
			
				
			}
			}
		});
		btnNewButton.setBounds(95, 151, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("<html><center>Reconfirme seu Usu\u00E1rio e Senha para continuar<br> seu cadastro.</center></html>");
		lblNewLabel_2.setBounds(20, 22, 236, 25);
		panel.add(lblNewLabel_2);

	}
	
	
}
