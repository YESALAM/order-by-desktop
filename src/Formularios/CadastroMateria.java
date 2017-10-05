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
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import model.AcaoRecente;
import model.Aluno;
import model.Materia;
import model.Turma;
import bd.Conexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CadastroMateria extends JFrame {

	private JPanel contentPane;
	private JTextField nome;
	private JTextField sigla;
	private Conexao conexao = Login.conexao;
	private JTextField buscatext;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private RelacionaMateriaProfessor relacionamateriaprof;
    private List<Materia> id_materia;
    private int id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					CadastroMateria frame = new CadastroMateria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public CadastroMateria() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setOpaque(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Materia> lista_materia;
				lista_materia=conexao.buscarMaterias();
				int a=-1;
				  for(int controle=0;controle<lista_materia.size();controle++)
				  {
					 if(lista_materia.get(controle).getNome().equals(nome.getText()) || lista_materia.get(controle).getNome().equals(sigla.getText()) )        //Coleta dados da lista que como base o nome selecionado na combobox.
					 {
					  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
					 }
				  }
				  if(a==-1)
				  {
				if(nome.getText()!=""&&sigla.getText()!=""){//verifica se os campos estão preenchidos corretamente
					if(sigla.getText().length()<=3){
						Materia m = new Materia();
						m.setNome(nome.getText());
						m.setSigla(sigla.getText());//preenche a classe materia
						conexao.inserir(m);//insere a matéria no banco de dados	
						AcaoRecente a1 =new AcaoRecente();
						a1.setFuncionario(FramePai.usuarioatual);
						a1.setAcao("Matéria "+m.getNome()+" Adicionada");
						conexao.inserir(a1);
						FramePai.a.add(a1);
						Principal.AtualizarAcoes();//insere nas ações recentes
						FramePai.Confirma(CadastroMateria.this);
						nome.setText("");
						sigla.setText("");
						
					}else{
						JOptionPane.showMessageDialog(null,"Sigla tem que ser menor que 3 caracteres");
					}
				}else{
					JOptionPane.showMessageDialog(null,"Dados incompletos");
				}
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null,"Materia ja existente");
				  }
			}
		});
		
		
		btnAdicionar.setBounds(780, 332, 114, 23);
		contentPane.add(btnAdicionar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Informações da Matéria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(92, 137, 550, 98);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Nome: ");
		label.setBounds(43, 31, 61, 18);
		panel.add(label);
		
		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setBounds(43, 58, 43, 18);
		panel.add(lblSigla);
		
		nome = new JTextField("");
		nome.setBounds(89, 31, 411, 18);
		panel.add(nome);
		
		sigla = new JTextField("");
		sigla.setBounds(89, 58, 184, 18);
		panel.add(sigla);
				
				
				buscatext = new JTextField();
				buscatext.setColumns(10);
				buscatext.setBounds(728, 194, 193, 20);
				contentPane.add(buscatext);
				
				JLabel lblinsiraASigla = new JLabel("<html>Insira a sigla de uma mat\u00E9ria j\u00E1 cadastrada para visualizar e/ou alterar o registro da mesma.</html>");
				lblinsiraASigla.setForeground(Color.WHITE);
				lblinsiraASigla.setBounds(728, 225, 226, 59);
				contentPane.add(lblinsiraASigla);
				
				btnAlterar = new JButton("Alterar");
				btnAlterar.setOpaque(false);
				btnAlterar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(JOptionPane.showConfirmDialog(CadastroMateria.this, "Tem certeza que deseja alterar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
						{
						if(nome.getText()!=""&&sigla.getText()!=""){//verifica se os campos estão preenchidos corretamente
						       if(sigla.getText().length()<=3){
						        Materia m = new Materia();
						        m.setNome(nome.getText());
						        m.setSigla(sigla.getText());//preenche a classe materia
						        m.setIdMateria(conexao.buscarMaterias(id).getIdMateria());
						        conexao.Alterar(m);//insere a matéria no banco de dados
						        AcaoRecente a =new AcaoRecente();
								a.setFuncionario(FramePai.usuarioatual);
								a.setAcao("Matéria "+m.getNome()+" Alterada");
								conexao.inserir(a);
								FramePai.a.add(a);
								Principal.AtualizarAcoes();//insere nas ações recentes
						        JOptionPane.showMessageDialog(null,"Materia alterada com sucesso.");
						        
						       }else{
						        JOptionPane.showMessageDialog(null,"Sigla tem que ser menor que 3 caracteres");
						       }
						      }else{
						       JOptionPane.showMessageDialog(null,"Dados incompletos");
						      }
						}
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
							
							cadastromateria.setForeground(Color.WHITE);
							cadastromateria.setFont(new Font("Arial", Font.BOLD, 14));
							cadastromateria.setBounds(592, 79, 61, 14);
							contentPane.add(cadastromateria);
					       
							
							
							 JLabel cadastrosala = new JLabel("Salas");
								
								cadastrosala.setForeground(Color.BLACK);
								cadastrosala.setFont(new Font("Arial", Font.BOLD, 14));
								cadastrosala.setBounds(768, 79, 44, 14);
								contentPane.add(cadastrosala);
						        JLabel acaoCadastroSala = new JLabel("");
								
						        acaoCadastroSala.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseEntered(MouseEvent arg0) {
										cadastrosala.setForeground(new Color(95,93,93));
										acaoCadastroSala.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseNovarConta.png")));
												
									}
											
									@Override
									public void mouseExited(MouseEvent e) {
										
										cadastrosala.setForeground(new Color(0,0,0));
										acaoCadastroSala.setIcon(null);
											
									}
									@Override
									public void mouseClicked(MouseEvent e) {
										FramePai.AbrirCadastroSala();
									}
								});
						        acaoCadastroSala.setBounds(704, 73, 163, 26);
								contentPane.add(acaoCadastroSala);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setOpaque(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Materia> lista_materia;
				if(JOptionPane.showConfirmDialog(CadastroMateria.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
				{
				lista_materia=conexao.buscarMaterias();
				int b=-1;
		  		for(int i=0;i<lista_materia.size();i++)
				  {
		  			if(lista_materia.get(i).getNome().equals(nome.getText().toString()))  //Coleta dados da lista que como base o nome selecionado na combobox.
		  			{ 
				  b=i;
				  }}
				if(b==-1)
				{
					
					JOptionPane.showMessageDialog(null, "Matéria não existe.");
				}
				else
				{
		  		Materia m=new Materia();
		  		m=conexao.buscarMaterias(lista_materia.get(b).getIdMateria());
				conexao.Excluir(m);
				AcaoRecente a =new AcaoRecente();
				a.setFuncionario(FramePai.usuarioatual);
				a.setAcao("Matéria "+m.getNome()+" Excluida");
				conexao.inserir(a);
				FramePai.a.add(a);
				Principal.AtualizarAcoes();//insere nas ações recentes
				JOptionPane.showMessageDialog(null, "Matéria excluida com sucesso.");
				}
			}
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		btnExcluir.setBounds(780, 398, 114, 23);
		contentPane.add(btnExcluir);
		
		JButton btnRelacionar = new JButton("Relacionar");
		btnRelacionar.setOpaque(false);
		btnRelacionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RelacionaMateriaTurma relacionarm = new RelacionaMateriaTurma();
				relacionarm.setVisible(true);
			}
		});
		btnRelacionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btnRelacionar.setBounds(780, 432, 114, 23);
		contentPane.add(btnRelacionar);
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!buscatext.getText().equals("")){
						
				if(conexao.buscarMaterias(buscatext.getText().toString())==null)//verifica se a matéria consta no banco de dados
				{
					JOptionPane.showMessageDialog(null, "Matéria não cadastrada");
				}
				else
				{
					
					btnAdicionar.setEnabled(false);
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					nome.setText(conexao.buscarMaterias(buscatext.getText().toString()).getNome());//preenche os text fields com os resultados
					sigla.setText(conexao.buscarMaterias(buscatext.getText().toString()).getSigla());
					id_materia=conexao.buscarMaterias();
					
					  for(int controle=0;controle<id_materia.size();controle++)
					  {
						 if(id_materia.get(controle).getNome().equals(nome.getText().toString())  ||   id_materia.get(controle).getSigla().equals(sigla.getText().toString()))    //Coleta dados da lista que como base o nome selecionado na combobox.
						 {
						  	 id=controle;                                                                            //Atribui o valor achado na lista na varialvel a.
						 }
					  }
					
				}	
						
						}else{
							JOptionPane.showMessageDialog(CadastroMateria.this, "Sem parametro para pesquisa");
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
		
		JButton button = new JButton("Preencher");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nome.setText("Desenvolvimento de Software");
				sigla.setText("DES");
			}
		});
		button.setBounds(57, 536, 89, 23);
		contentPane.add(button);
								
				//------------------
		
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroMateria.class.getResource("/FundoCadastroMateria2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
	}
}
