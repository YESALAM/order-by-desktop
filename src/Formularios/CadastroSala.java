package Formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;

import model.AcaoRecente;
import model.Aluno;
import model.Sala;
import model.Turma;
import bd.Conexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CadastroSala extends JFrame {

	private JPanel contentPane;
	private JTextField numero;
	private JTextField tipo;
	private Conexao conexao = Login.conexao;
	private JTextField buscacampo;
	private JButton btnExcluir;
	private JButton btnAlterar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CadastroSala frame = new CadastroSala();
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
	public CadastroSala() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(92, 137, 494, 103);
		panel.setBorder(new TitledBorder(null, "Informações da Sala", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNmeroDaSala = new JLabel("N\u00FAmero da Sala");
		lblNmeroDaSala.setBounds(10, 32, 75, 14);
		panel.add(lblNmeroDaSala);
		
		numero = new JTextField();
		numero.setBounds(97, 29, 169, 20);
		panel.add(numero);
		numero.setColumns(10);
		
		JLabel lblTipoDaSala = new JLabel("Tipo da Sala");
		lblTipoDaSala.setBounds(10, 63, 75, 14);
		panel.add(lblTipoDaSala);
		
		tipo = new JTextField();
		tipo.setBounds(97, 60, 169, 20);
		panel.add(tipo);
		tipo.setColumns(10);
		
		JButton btnInserir = new JButton("Cadastrar");
		btnInserir.setOpaque(false);
		btnInserir.setBounds(780, 332, 114, 23);
		contentPane.add(btnInserir);
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(numero.getText().equals("")||tipo.getText().equals("")){//verifica se existem campos vazios
					JOptionPane.showMessageDialog(null, "Existem campos não preenchidos");
				}else{
				Sala s = new Sala();
				s.setIdSala(numero.getText());
				s.setTipo(tipo.getText());
				conexao.inserir(s);//insere a sala no banco de dados
				AcaoRecente a =new AcaoRecente();
				a.setFuncionario(FramePai.usuarioatual);
				a.setAcao("Sala "+s.getIdSala()+" Adicionada");
				conexao.inserir(a);
				FramePai.a.add(a);
				Principal.AtualizarAcoes();//insere nas ações recentes
				FramePai.Confirma(CadastroSala.this);
				numero.setText("");
				tipo.setText("");
				}
				}
		});
		
		buscacampo = new JTextField();
		buscacampo.setColumns(10);
		buscacampo.setBounds(728, 194, 193, 20);
		contentPane.add(buscacampo);
		
		JLabel lblinsiraONmero = new JLabel("<html>Insira o n\u00FAmero de uma sala j\u00E1 cadastrada para visualizar e/ou alterar o registro da mesma.</html>");
		lblinsiraONmero.setForeground(Color.WHITE);
		lblinsiraONmero.setBounds(728, 225, 226, 59);
		contentPane.add(lblinsiraONmero);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setOpaque(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(numero.getText().equals("")||tipo.getText().equals(""))//verifica se os campos estão vazios
				{
					JOptionPane.showMessageDialog(null, "Existem campos não preenchidos");
				}	
				else
				{
					if(JOptionPane.showConfirmDialog(CadastroSala.this, "Tem certeza que deseja alterar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
					{
			        Sala s = new Sala();
			     s.setIdSala(numero.getText());
			     s.setTipo(tipo.getText());
			     conexao.Alterar(s);//altera o regiswtro da sala no banco de dadaos
			     AcaoRecente a =new AcaoRecente();
					a.setFuncionario(FramePai.usuarioatual);
					a.setAcao("Sala "+s.getIdSala()+" Alterada");
					conexao.inserir(a);
					FramePai.a.add(a);
					Principal.AtualizarAcoes();//insere nas ações recentes
					FramePai.Confirma(CadastroSala.this);
				}}
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			    
			}
		});
		btnAlterar.setBounds(780, 366, 114, 23);
		contentPane.add(btnAlterar);
		
		//------------------
		
		JLabel lblGerenciamento = new JLabel("Gerenciamento");
		lblGerenciamento.setForeground(Color.WHITE);
		lblGerenciamento.setFont(new Font("Arial", Font.BOLD, 14));
		lblGerenciamento.setBounds(56, 39, 121, 14);
		contentPane.add(lblGerenciamento);
		
		JLabel cadastrohorario = new JLabel("Horario");
		cadastrohorario.setForeground(Color.BLACK);
		cadastrohorario.setFont(new Font("Arial", Font.BOLD, 14));
		cadastrohorario.setBounds(100, 79, 61, 14);
		contentPane.add(cadastrohorario);
		
		JLabel acaoCadastroHorario = new JLabel("");
        acaoCadastroHorario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				cadastrohorario.setForeground(new Color(95,93,93));
				acaoCadastroHorario.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseAlterarConta.png")));
						
			}
					
			@Override
			public void mouseExited(MouseEvent e) {
				
				cadastrohorario.setForeground(new Color(0,0,0));
				acaoCadastroHorario.setIcon(null);
					
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FramePai.AbrirCadastroHorario();
			}
		});
        acaoCadastroHorario.setBounds(45, 73, 163, 26);
		contentPane.add(acaoCadastroHorario);
		
		JLabel cadastroprofessor = new JLabel("Professor");
		
		cadastroprofessor.setForeground(Color.BLACK);
		cadastroprofessor.setFont(new Font("Arial", Font.BOLD, 14));
		cadastroprofessor.setBounds(257, 79, 77, 14);
		contentPane.add(cadastroprofessor);
        JLabel acaoCadastroProfessor = new JLabel("");
		
        acaoCadastroProfessor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				cadastroprofessor.setForeground(new Color(95,93,93));
				acaoCadastroProfessor.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseNovarConta.png")));
						
			}
					
			@Override
			public void mouseExited(MouseEvent e) {
				
				cadastroprofessor.setForeground(new Color(0,0,0));
				acaoCadastroProfessor.setIcon(null);
					
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FramePai.AbrirCadastroProfessor();
			}
		});
        acaoCadastroProfessor.setBounds(210, 73, 163, 26);
		contentPane.add(acaoCadastroProfessor);
				
		JLabel cadastroaluno = new JLabel("Alunos");
		
		cadastroaluno.setForeground(Color.BLACK);
		cadastroaluno.setFont(new Font("Arial", Font.BOLD, 14));
		cadastroaluno.setBounds(434, 79, 54, 14);
		contentPane.add(cadastroaluno);
        JLabel acaoCadastroAluno = new JLabel("");
		
        acaoCadastroAluno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				cadastroaluno.setForeground(new Color(95,93,93));
				acaoCadastroAluno.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseNovarConta.png")));
						
			}
					
			@Override
			public void mouseExited(MouseEvent e) {
				
				cadastroaluno.setForeground(new Color(0,0,0));
				acaoCadastroAluno.setIcon(null);
					
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FramePai.AbrirCadastroAluno();
			}
		});
        acaoCadastroAluno.setBounds(374, 73, 163, 26);
		contentPane.add(acaoCadastroAluno);
		   
				
		 JLabel cadastromateria = new JLabel("Matérias");
			
			cadastromateria.setForeground(Color.BLACK);
			cadastromateria.setFont(new Font("Arial", Font.BOLD, 14));
			cadastromateria.setBounds(592, 79, 61, 14);
			contentPane.add(cadastromateria);
	        JLabel acaoCadastroMateria = new JLabel("");
			
	        acaoCadastroMateria.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					cadastromateria.setForeground(new Color(95,93,93));
					acaoCadastroMateria.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseNovarConta.png")));
							
				}
						
				@Override
				public void mouseExited(MouseEvent e) {
					
					cadastromateria.setForeground(new Color(0,0,0));
					acaoCadastroMateria.setIcon(null);
						
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					FramePai.AbrirCadastroMateria();
				}
			});
	        acaoCadastroMateria.setBounds(539, 73, 163, 26);
			contentPane.add(acaoCadastroMateria);
			       
					
					
					 JLabel cadastrosala = new JLabel("Salas");
						
						cadastrosala.setForeground(Color.WHITE);
						cadastrosala.setFont(new Font("Arial", Font.BOLD, 14));
						cadastrosala.setBounds(768, 79, 44, 14);
						contentPane.add(cadastrosala);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setOpaque(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!numero.getText().equals("")){
					
						if(JOptionPane.showConfirmDialog(CadastroSala.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
						{
					Sala s = new Sala();
					s.setIdSala(numero.getText());
					s.setTipo(tipo.getText());
					conexao.Excluir(s);
					AcaoRecente a =new AcaoRecente();
					a.setFuncionario(FramePai.usuarioatual);
					a.setAcao("Sala "+s.getIdSala()+" Excluida");
					conexao.inserir(a);
					FramePai.a.add(a);
					Principal.AtualizarAcoes();//insere nas ações recentes
					FramePai.Confirma(CadastroSala.this);
						}
				
					}else{
						JOptionPane.showMessageDialog(null, "Existem dados não preenchidos");
					}
			}
		});
		btnExcluir.setBounds(780, 400, 114, 23);
		btnExcluir.setEnabled(false);
		contentPane.add(btnExcluir);
				        
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!buscacampo.getText().equals("")){
				
				if(conexao.buscarSalas(buscacampo.getText().toString())==null)//verifica se a pesquisa deu resultado
				{
					JOptionPane.showMessageDialog(null, "Sala não cadastrado");
				}
				else
				{
					btnAlterar.setEnabled(true);
					btnInserir.setEnabled(false);
					btnExcluir.setEnabled(true);
					numero.setText(String.valueOf(conexao.buscarSalas(buscacampo.getText().toString()).getIdSala()));//preenche os devidos campos com a pesquisa correspondente
					tipo.setText(conexao.buscarSalas(buscacampo.getText().toString()).getTipo());
					
				}	
			}else{
				JOptionPane.showMessageDialog(CadastroSala.this, "Sem parametros para pesquisa");
			}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_3.setIcon( new ImageIcon(CadastroAluno.class.getResource("/fundomousepesquisar.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_3.setIcon( new ImageIcon(CadastroAluno.class.getResource("/fundobotaopesquisar.png")));
			}
		});
		lblNewLabel_3.setIcon(new ImageIcon(CadastroAluno.class.getResource("/fundobotaopesquisar.png")));
		lblNewLabel_3.setBounds(928, 188, 32, 32);
		contentPane.add(lblNewLabel_3);
		
		JButton btnPreencher = new JButton("Preencher");
		btnPreencher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numero.setText("351");
				tipo.setText("Comum");
			}
		});
		btnPreencher.setBounds(57, 536, 89, 23);
		contentPane.add(btnPreencher);
						
		//------------------
		
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroSala.class.getResource("/FundoCadastroSala2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
	}
}
