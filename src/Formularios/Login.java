package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Panel;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import bd.Conexao;
import bd.NoticiasBD;

import java.awt.Window.Type;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField campousuario;
	private JTextField camposenha;
	public static Login Login ;
	public static String nome;
	public static String cargo;
	public FramePai frameprincipal;
	public static Conexao conexao = new Conexao("localhost","root","vertrigo","escola");
	public static NoticiasBD conexaonoticia = new NoticiasBD("localhost","root","vertrigo","escola");
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
			
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Login = new Login();
			Login.setVisible(true);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public Login() {
		
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 545);
		contentPane = new JPanel();
		contentPane.setLocation(-33, -211);
		contentPane.setBorder(null);
		setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
	
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(391, 326, 312, 93);
		contentPane.add(panel);
		panel.setLayout(null);
		
		campousuario = new JTextField("");
		campousuario.setBounds(114, 23, 143, 18);
		panel.add(campousuario);
		campousuario.setColumns(10);
		
		camposenha = new JPasswordField("");
		camposenha.setBounds(114, 52, 143, 18);
		panel.add(camposenha);
		camposenha.setColumns(10);
		
		JLabel usuario = new JLabel("Us\u00FAario : ");
		usuario.setBounds(54, 23, 60, 18);
		panel.add(usuario);
		usuario.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel senha = new JLabel("Senha : ");
		senha.setBounds(54, 52, 50, 18);
		panel.add(senha);
		senha.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JButton btnNewButton = new JButton("Conectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			


				
				if(conexao.buscarFuncionarios(campousuario.getText().toString())==null)//verifica se o usuario existe
				{
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha errado(s)");//caso não, ele apaga o que foi digitado 
					campousuario.setText("");
					 camposenha.setText("");
					 campousuario.requestFocus();
				}
				else
				{
					if(conexao.buscarFuncionarios(campousuario.getText().toString()).getSenha().equals(camposenha.getText()))//verifica o campo senha corresponde ao usuario digitado
					{
						 frameprincipal.usuarioatual = conexao.buscarFuncionarios(campousuario.getText().toString());//salva o usuario cadastrado
						 frameprincipal=new FramePai();
						 frameprincipal.setVisible(true);//mostra a frame pai
						 Login.dispose();//descarta a form login

					}
					else
					{
						 JOptionPane.showMessageDialog(null, "Usuário e/ou senha errado(s)");//caso não, ele apaga o que foi digitado
						 campousuario.setText("");
						 camposenha.setText("");
						 campousuario.requestFocus();
						
					}
				}
				
							}
		});
		btnNewButton.setBounds(463, 430, 76, 25);
		contentPane.add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("Fechar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(548, 430, 76, 25);
		contentPane.add(btnNewButton_1);
		

		
		JLabel lblSejaBemvindo = new JLabel("Seja bem-vindo");
	
		lblSejaBemvindo.setFont(new Font("Arial", Font.PLAIN, 30));
		lblSejaBemvindo.setBounds(54, 358, 213, 25);
		contentPane.add(lblSejaBemvindo);
		
		JLabel Mensagem = new JLabel("Identifique-se por favor para utilizar o programa");
		Mensagem.setFont(new Font("Arial", Font.PLAIN, 11));
		Mensagem.setBounds(46, 405, 235, 14);
		contentPane.add(Mensagem);
		
		JLabel fundologin = new JLabel("New label");
		fundologin.setIcon(new ImageIcon(Login.class.getResource("/FundoLogin3.png")));
		fundologin.setBounds(0, -1, 994, 521);
		contentPane.add(fundologin);
		
		setLocationRelativeTo(null);
		
	}
}


