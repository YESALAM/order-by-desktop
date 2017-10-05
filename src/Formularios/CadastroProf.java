package Formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;

import bd.Conexao;
import model.AcaoRecente;
import model.Aluno;
import model.Horario;
import model.Professor;
import model.Endereco;
import model.Turma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroProf extends JFrame {
	public Boolean verificarnumerico (String a){
		Boolean c=false;
		int d;//verifica se a string se encaixa no formato de numero inteiro
		try{
			
			d=Integer.parseInt(a);
			c=true;
		}catch(Exception e){
			c=false;
			
		}finally{
		return c;
		}
	}

	public JLabel Endereco;
	public JTextField endereco;
	public JLabel Numero;
	public JTextField numero;
	public JLabel Complemento;
	public JTextField complemento;
	public JLabel CEP;
	public JTextField cep;
	public JLabel Bairro;
	public JTextField bairro;
	public JLabel Cidade;
	public JTextField cidade;
	public JLabel Estado;
	public JTextField estado;
	private JPanel EnderecoGrupo;
	private JPanel contentPane;
	private JTextField nome;
	private JTextField cpf;
	private JTextField rg;
	private JTextField tel;
	private JTextField cel;
	private JTextField senha;
	private JTextField sigla;
	private JTextField busca;
	private JButton btnAlterar;
	private JFormattedTextField segini,segfim,terini,terfim,quaini,quafim,quiini,quifim,sexini,sexfim;
	
	private Conexao conexao = Login.conexao;
	private JButton btnExcluir;
	private JButton btnRelacionar;

	
	public MaskFormatter Mascara(String Mascara){  
        
	       MaskFormatter F_Mascara = new MaskFormatter();  
	       try{  
	           F_Mascara.setMask(Mascara); //Atribui a mascara  
	           F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento   
	       }  
	       catch (Exception excecao) {  
	       excecao.printStackTrace();  
	       }   
	       return F_Mascara;  
	}  
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					CadastroProf frame = new CadastroProf();
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
	public CadastroProf() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setOpaque(false);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cidade.getText().equals("")
						|| nome.getText().equals("")
						|| cpf.getText().equals("")
						|| rg.getText().equals("")
						|| tel.getText().equals("")
						|| cel.getText().equals("")
						|| endereco.getText().equals("")//verifica se os campos estão preenchidos
						|| numero.getText().equals("")
						|| cep.getText().equals("")
						|| bairro.getText().equals("")
						|| estado.getText().equals("")
						|| senha.getText().equals("")
						|| sigla.getText().equals("")                  
						|| segini.getText().equals("")
						|| segfim.getText().equals("")
						|| terini.getText().equals("")
						|| terfim.getText().equals("")
						|| quaini.getText().equals("")
						|| quafim.getText().equals("")
						|| quiini.getText().equals("")
						|| quifim.getText().equals("")
						|| sexini.getText().equals("")
						|| sexfim.getText().equals("")) 
				{
					JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco.");
				}
				else {
					if(verificarnumerico(numero.getText())){
					
						
						Professor f = new Professor();
						f.setNome(nome.getText());
						f.setCelular(cel.getText());
						f.setCPF(cpf.getText());
						f.setRg(rg.getText());
						f.setSenha(senha.getText());
						f.setTelefone(tel.getText());
						f.setSigla(sigla.getText());
						Endereco e1 = new Endereco();
						e1.setBairro(bairro.getText());
						e1.setCEP(cep.getText());
						e1.setCidade(cidade.getText());
						e1.setComplemento(complemento.getText());
						e1.setEstado(estado.getText());
						e1.setNumero(Integer.parseInt(numero.getText()));
						e1.setRua(endereco.getText());
						e1.setTipo("Residencial");
						f.setEndereco(e1);
						
						conexao.inserir(f);//adiciona o professor no banco de dados
						AcaoRecente a =new AcaoRecente();
						a.setFuncionario(FramePai.usuarioatual);
						a.setAcao("Professor "+f.getCPF()+" Adicionado");
						conexao.inserir(a);//insere nas ações recentes
						FramePai.a.add(a);
						Principal.AtualizarAcoes();
						
					    int seginihI=Integer.parseInt(segini.getText().substring(0,2));
					    int seginimI=Integer.parseInt(segini.getText().substring(3,5));
					    
					    int segfimhI=Integer.parseInt(segfim.getText().substring(0,2));
					    int segfimmI=Integer.parseInt(segfim.getText().substring(3,5));
					    
					    int terinihI=Integer.parseInt(terini.getText().substring(0,2));
					    int terinimI=Integer.parseInt(terini.getText().substring(3,5));
					    
					    int terfimhI=Integer.parseInt(terfim.getText().substring(0,2));
					    int terfimmI=Integer.parseInt(terfim.getText().substring(3,5));
					    
					    int quainihI=Integer.parseInt(quaini.getText().substring(0,2));
					    int quainimI=Integer.parseInt(quaini.getText().substring(3,5));
					    
					    int quafimhI=Integer.parseInt(quafim.getText().substring(0,2));//definem os horarios de entrada e saida de cada dia no formato de numero inteiro
					    int quafimmI=Integer.parseInt(quafim.getText().substring(3,5));
					    
					    int quiinihI=Integer.parseInt(quiini.getText().substring(0,2));
					    int quiinimI=Integer.parseInt(quiini.getText().substring(3,5));
					    
					    int quifimhI=Integer.parseInt(quifim.getText().substring(0,2));
					    int quifimmI=Integer.parseInt(quifim.getText().substring(3,5));
					    
					    int sexinihI=Integer.parseInt(sexini.getText().substring(0,2));
					    int sexinimI=Integer.parseInt(sexini.getText().substring(3,5));
					    
					    int sexfimhI=Integer.parseInt(sexfim.getText().substring(0,2));
					    int sexfimmI=Integer.parseInt(sexfim.getText().substring(3,5));
					    
                        Horario hseg= new Horario();
                        hseg.setEntrada(Time.valueOf(String.valueOf(seginihI)+":"+String.valueOf(seginimI)+":00"));
                        hseg.setSaida(Time.valueOf(String.valueOf(segfimhI)+":"+String.valueOf(segfimmI)+":00"));
                        hseg.setDia("Segunda");
                        hseg.setProfessor(f);
                        conexao.inserir_p(hseg);
                        
                        Horario hter= new Horario();
                        hter.setEntrada(Time.valueOf(String.valueOf(terinihI)+":"+String.valueOf(terinimI)+":00"));
                        hter.setSaida(Time.valueOf(String.valueOf(terfimhI)+":"+String.valueOf(terfimmI)+":00"));
                        hter.setDia("Terça");
                        hter.setProfessor(f);
                        conexao.inserir_p(hter);
                        
                        Horario hqua= new Horario();
                        hqua.setEntrada(Time.valueOf(String.valueOf(quainihI)+":"+String.valueOf(quainimI)+":00"));
                        hqua.setSaida(Time.valueOf(String.valueOf(quafimhI)+":"+String.valueOf(quafimmI)+":00"));
                        hqua.setDia("Quarta");
                        hqua.setProfessor(f);
                        conexao.inserir_p(hqua);
                        
                        Horario hqui= new Horario();
                        hqui.setEntrada(Time.valueOf(String.valueOf(quiinihI)+":"+String.valueOf(quiinimI)+":00"));
                        hqui.setSaida(Time.valueOf(String.valueOf(quifimhI)+":"+String.valueOf(quifimmI)+":00"));
                        hqui.setDia("Quinta");
                        hqui.setProfessor(f);
                        conexao.inserir_p(hqui);//adicionam os horarios ao banco
                        
                        Horario hsex= new Horario();
                        hsex.setEntrada(Time.valueOf(String.valueOf(sexinihI)+":"+String.valueOf(sexinimI)+":00"));
                        hsex.setSaida(Time.valueOf(String.valueOf(sexfimhI)+":"+String.valueOf(sexfimmI)+":00"));
                        hsex.setDia("Sexta");
                        hsex.setProfessor(f);
                        conexao.inserir_p(hsex);
                        
                        
						JOptionPane.showMessageDialog(null,"Cadastro realizado com sucesso");
						cidade.setText("");
						nome.setText("");
						cpf.setText("");
						rg.setText("");
						tel.setText("");
						cel.setText("");
						endereco.setText("");//verifica se os campos estão preenchidos
						numero.setText("");
						cep.setText("");
						bairro.setText("");
						estado.setText("");
						senha.setText("");
						sigla.setText("");                  
						segini.setText("");
						segfim.setText("");
						terini.setText("");
						terfim.setText("");
						quaini.setText("");
						quafim.setText("");
						quiini.setText("");
						quifim.setText("");
						sexini.setText("");
						sexfim.setText("");
					}}
			}
		});
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				}
			
		});
		btnCadastrar.setBounds(780, 332, 114, 23);
		contentPane.add(btnCadastrar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null,
						"Informações Pessoais", TitledBorder.LEADING, TitledBorder.TOP,
						null, null));
		panel.setBounds(100, 104, 550, 134);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Nome: ");
		label.setBounds(43, 31, 61, 18);
		panel.add(label);
		
		JLabel label_1 = new JLabel("CPF: ");
		label_1.setBounds(326, 31, 42, 18);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("RG: ");
		label_2.setBounds(43, 58, 61, 18);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Telefone: ");
		label_3.setBounds(348, 58, 61, 18);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Celular:");
		label_4.setBounds(197, 58, 61, 18);
		panel.add(label_4);
		
		nome = new JTextField("");
		nome.setBounds(89, 31, 225, 18);
		panel.add(nome);
		
		cpf = new JTextField("");
		cpf.setBounds(354, 31, 145, 18);
		panel.add(cpf);
		
		rg = new JTextField("");
		rg.setBounds(89, 58, 100, 18);
		panel.add(rg);
		
		tel = new JTextField("");
		tel.setBounds(399, 58, 100, 18);
		panel.add(tel);
		
		cel = new JTextField("");
		cel.setBounds(239, 58, 100, 18);
		panel.add(cel);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(43, 85, 46, 18);
		panel.add(lblSenha);
		
		senha = new JTextField();
		senha.setColumns(10);
		senha.setBounds(89, 85, 120, 18);
		panel.add(senha);
		
		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setBounds(219, 85, 46, 18);
		panel.add(lblSigla);
		
		sigla = new JTextField();
		sigla.setBounds(249, 85, 66, 18);
		panel.add(sigla);
		sigla.setColumns(10);
		
		EnderecoGrupo = new JPanel();
		EnderecoGrupo.setBounds(100, 243, 550, 134);
		contentPane.add(EnderecoGrupo);
		EnderecoGrupo.setBorder(new TitledBorder(null, "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		EnderecoGrupo.setLayout(null);
		
		Endereco = new JLabel("Endere\u00E7o:");
		Endereco.setBounds(43, 31, 72, 18);
		EnderecoGrupo.add(Endereco);
		
		Numero = new JLabel("N\u00FAmero: ");
		Numero.setBounds(43, 58, 61, 18);
		EnderecoGrupo.add(Numero);
		
		Complemento = new JLabel("Complemento: ");
		Complemento.setBounds(181, 58, 97, 18);
		EnderecoGrupo.add(Complemento);
		
		CEP = new JLabel("CEP: ");
		CEP.setBounds(372, 31, 36, 18);
		EnderecoGrupo.add(CEP);
		
		Bairro = new JLabel("Bairro: ");
		Bairro.setBounds(43, 85, 61, 18);
		EnderecoGrupo.add(Bairro);
		
		Cidade = new JLabel("Cidade: ");
		Cidade.setBounds(252, 85, 61, 18);
		EnderecoGrupo.add(Cidade);
		
		Estado = new JLabel("Estado: ");
		Estado.setBounds(394, 58, 61, 18);
		EnderecoGrupo.add(Estado);
		
		estado = new JTextField ("");
		estado.setBounds(439, 58, 61, 18);
		EnderecoGrupo.add(estado);
		
		endereco = new JTextField ("");
		endereco.setBounds(98, 31, 264, 18);
		EnderecoGrupo.add(endereco);
		
		numero = new JTextField ("");
		numero.setBounds(98, 58, 61, 18);
		EnderecoGrupo.add(numero);
		
		complemento = new JTextField ("");
		complemento.setBounds(258, 58, 126, 18);
		EnderecoGrupo.add(complemento);
		
		cep = new JTextField ("");
		cep.setBounds(403, 31, 97, 18);
		EnderecoGrupo.add(cep);
		
		bairro = new JTextField ("");
		bairro.setBounds(98, 85, 144, 18);
		EnderecoGrupo.add(bairro);
		
		cidade = new JTextField ("");
		cidade.setBounds(298, 85, 202, 18);
		EnderecoGrupo.add(cidade);
		
		busca = new JTextField();
		busca.setBounds(728, 194, 193, 20);
		contentPane.add(busca);
		busca.setColumns(10);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setOpaque(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cidade.getText().equals("")
						|| nome.getText().equals("")
						|| cpf.getText().equals("")
						|| rg.getText().equals("")
						|| tel.getText().equals("")
						|| cel.getText().equals("")
						|| endereco.getText().equals("")
						|| numero.getText().equals("")
						|| cep.getText().equals("")
						|| bairro.getText().equals("")
						|| estado.getText().equals("")
						|| senha.getText().equals("")
						|| sigla.getText().equals("")                  
						|| segini.getText().equals("")
						|| segfim.getText().equals("")
						|| terini.getText().equals("")
						|| terfim.getText().equals("")
						|| quaini.getText().equals("")//verifica se todos os campos estão preenchidos
						|| quafim.getText().equals("")
						|| quiini.getText().equals("")
						|| quifim.getText().equals("")
						|| sexini.getText().equals("")
						|| sexfim.getText().equals("")) 
				{
					JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco.");
				}
				else {
					if(verificarnumerico(numero.getText())){
					
						if(JOptionPane.showConfirmDialog(CadastroProf.this, "Tem certeza que deseja alterar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
						{
						Professor f = new Professor();
						f.setNome(nome.getText());
						f.setCelular(cel.getText());
						f.setCPF(cpf.getText());
						f.setRg(rg.getText());
						f.setSenha(senha.getText());
						f.setTelefone(tel.getText());
						f.setSigla(sigla.getText());
						Endereco e1 = new Endereco();
						e1.setBairro(bairro.getText());
						e1.setCEP(cep.getText());
						e1.setCidade(cidade.getText());
						e1.setComplemento(complemento.getText());
						e1.setEstado(estado.getText());
						e1.setNumero(Integer.parseInt(numero.getText()));
						e1.setRua(endereco.getText());
						e1.setTipo("Residencial");
						f.setEndereco(e1);
						
						conexao.Alterar(f);//altera o professor no banco
						AcaoRecente a =new AcaoRecente();
						a.setFuncionario(FramePai.usuarioatual);
						a.setAcao("Professor "+f.getCPF()+" Alterado");
						conexao.inserir(a);//insere nas ações recentes
						FramePai.a.add(a);
						Principal.AtualizarAcoes();
						
					    int seginihI=Integer.parseInt(segini.getText().substring(0,2));
					    int seginimI=Integer.parseInt(segini.getText().substring(3,5));
					    
					    int segfimhI=Integer.parseInt(segfim.getText().substring(0,2));
					    int segfimmI=Integer.parseInt(segfim.getText().substring(3,5));
					    
					    int terinihI=Integer.parseInt(terini.getText().substring(0,2));
					    int terinimI=Integer.parseInt(terini.getText().substring(3,5));
					    
					    int terfimhI=Integer.parseInt(terfim.getText().substring(0,2));
					    int terfimmI=Integer.parseInt(terfim.getText().substring(3,5));
					    
					    int quainihI=Integer.parseInt(quaini.getText().substring(0,2));
					    int quainimI=Integer.parseInt(quaini.getText().substring(3,5));
					    
					    int quafimhI=Integer.parseInt(quafim.getText().substring(0,2));
					    int quafimmI=Integer.parseInt(quafim.getText().substring(3,5));
					    
					    int quiinihI=Integer.parseInt(quiini.getText().substring(0,2));
					    int quiinimI=Integer.parseInt(quiini.getText().substring(3,5));
					    
					    int quifimhI=Integer.parseInt(quifim.getText().substring(0,2));
					    int quifimmI=Integer.parseInt(quifim.getText().substring(3,5));
					    
					    int sexinihI=Integer.parseInt(sexini.getText().substring(0,2));
					    int sexinimI=Integer.parseInt(sexini.getText().substring(3,5));
					    
					    int sexfimhI=Integer.parseInt(sexfim.getText().substring(0,2));
					    int sexfimmI=Integer.parseInt(sexfim.getText().substring(3,5));
					    
                        Horario hseg= new Horario();
                        List<Horario> h;
    				    h=conexao.buscarHorarios_Professor(f);
    				    hseg.setIdHorario(h.get(0).getIdHorario());//pega o id do horario a ser alterado
                        hseg.setEntrada(Time.valueOf(String.valueOf(seginihI)+":"+String.valueOf(seginimI)+":00"));
                        hseg.setSaida(Time.valueOf(String.valueOf(segfimhI)+":"+String.valueOf(segfimmI)+":00"));
                        hseg.setDia("Segunda");
                        hseg.setProfessor(f);
                        conexao.Alterar_p(hseg);//faz a alteração
                        
                        Horario hter= new Horario();
    				    hter.setIdHorario(h.get(1).getIdHorario());
                        hter.setEntrada(Time.valueOf(String.valueOf(terinihI)+":"+String.valueOf(terinimI)+":00"));
                        hter.setSaida(Time.valueOf(String.valueOf(terfimhI)+":"+String.valueOf(terfimmI)+":00"));
                        hter.setDia("Terça");
                        hter.setProfessor(f);
                        conexao.Alterar_p(hter);
                        
                        Horario hqua= new Horario();
                        hqua.setIdHorario(h.get(2).getIdHorario());
                        hqua.setEntrada(Time.valueOf(String.valueOf(quainihI)+":"+String.valueOf(quainimI)+":00"));
                        hqua.setSaida(Time.valueOf(String.valueOf(quafimhI)+":"+String.valueOf(quafimmI)+":00"));
                        hqua.setDia("Quarta");
                        hqua.setProfessor(f);
                        conexao.Alterar_p(hqua);
                        
                        Horario hqui= new Horario();
                        hqui.setIdHorario(h.get(3).getIdHorario());
                        hqui.setEntrada(Time.valueOf(String.valueOf(quiinihI)+":"+String.valueOf(quiinimI)+":00"));
                        hqui.setSaida(Time.valueOf(String.valueOf(quifimhI)+":"+String.valueOf(quifimmI)+":00"));
                        hqui.setDia("Quinta");
                        hqui.setProfessor(f);
                        conexao.Alterar_p(hqui);
                        
                        Horario hsex= new Horario();
                        hsex.setIdHorario(h.get(4).getIdHorario());
                        hsex.setEntrada(Time.valueOf(String.valueOf(sexinihI)+":"+String.valueOf(sexinimI)+":00"));
                        hsex.setSaida(Time.valueOf(String.valueOf(sexfimhI)+":"+String.valueOf(sexfimmI)+":00"));
                        hsex.setDia("Sexta");
                        hsex.setProfessor(f);
                        conexao.Alterar_p(hsex);
                        
                        
						JOptionPane.showMessageDialog(null,"Cadastro atualizado com sucesso");
					}}}
			}
		});
		btnAlterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		btnAlterar.setBounds(780, 366, 114, 23);
		contentPane.add(btnAlterar);
		btnAlterar.setEnabled(false);
		
		JLabel lblInsiraOCpf = new JLabel("<html>Insira o CPF de um professor j\u00E1 cadastrado para visualizar e/ou alterar o registro do mesmo.</html>");
		lblInsiraOCpf.setForeground(Color.WHITE);
		lblInsiraOCpf.setBounds(728, 225, 226, 59);
		contentPane.add(lblInsiraOCpf);
		
	//---------------
		
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
				
				cadastroprofessor.setForeground(Color.WHITE);
				cadastroprofessor.setFont(new Font("Arial", Font.BOLD, 14));
				cadastroprofessor.setBounds(257, 79, 77, 14);
				contentPane.add(cadastroprofessor);
		        
				
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Horario do professor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(100, 383, 550, 134);
		contentPane.add(panel_1);
		
		segini = new JFormattedTextField (Mascara("##:##"));
		segini.setBounds(109, 58, 70, 18);
		segini.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(segini);
		
		segfim = new JFormattedTextField (Mascara("##:##"));
		segfim.setBounds(109, 85, 70, 18);
		segfim.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(segfim);
		
		terini = new JFormattedTextField (Mascara("##:##"));
		terini.setBounds(189, 58, 70, 18);
		terini.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(terini);
		
		terfim = new JFormattedTextField (Mascara("##:##"));
		terfim.setBounds(189, 85, 70, 18);
		terfim.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(terfim);
		
		quaini = new JFormattedTextField (Mascara("##:##"));
		quaini.setBounds(269, 58, 70, 18);
		quaini.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(quaini);
		
		quafim = new JFormattedTextField (Mascara("##:##"));
		quafim.setBounds(269, 85, 70, 18);
		quafim.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(quafim);
		
		quiini = new JFormattedTextField (Mascara("##:##"));
		quiini.setBounds(349, 58, 70, 18);
		quiini.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(quiini);
		
		quifim = new JFormattedTextField (Mascara("##:##"));
		quifim.setBounds(349, 85, 70, 18);
		quifim.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(quifim);
		
		sexini = new JFormattedTextField (Mascara("##:##"));
		sexini.setBounds(429, 58, 70, 18);
		sexini.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(sexini);
		
		sexfim = new JFormattedTextField (Mascara("##:##"));
		sexfim.setBounds(429, 85, 70, 18);
		sexfim.setInputVerifier(new InputVerifier() {
			
			//Cria e instancia uma pattern, responsável pela verificação (Regular Expressions)
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// Retorna a verificação
					return verify(arg0);
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				//Obtem a JFormattedTextField do evento
				JFormattedTextField t = (JFormattedTextField)arg0;
				//Cria um matcher, que verifica se o texto bate com a pattern
				Matcher m = p.matcher(t.getText());
				//Retorna se bateu ou não.
				return m.matches();
			}
		});
		panel_1.add(sexfim);
		
		JLabel lblSegunda = new JLabel("Segunda");
		lblSegunda.setBounds(121, 31, 46, 18);
		panel_1.add(lblSegunda);
		
		JLabel lblNewLabel = new JLabel("Ter\u00E7a");
		lblNewLabel.setBounds(208, 31, 36, 18);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Quarta");
		lblNewLabel_1.setBounds(285, 31, 36, 18);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Quinta");
		lblNewLabel_2.setBounds(367, 31, 36, 18);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Sexta");
		lblNewLabel_3.setBounds(450, 31, 28, 18);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Entrada: ");
		lblNewLabel_4.setBounds(43, 58, 46, 18);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Sa\u00EDda: ");
		lblNewLabel_5.setBounds(43, 87, 46, 14);
		panel_1.add(lblNewLabel_5);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setOpaque(false);
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cpf.getText().equals(""))//verifica se o campo cpf está vazio
				{				
					JOptionPane.showMessageDialog(null, "O campo cpf está nulo. Impossível completar a operação.");
				}
				else
				{
					if(JOptionPane.showConfirmDialog(CadastroProf.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
					{
					conexao.Excluir(conexao.buscarProfessores(cpf.getText()));//exclui o professor
					AcaoRecente a =new AcaoRecente();
					a.setFuncionario(FramePai.usuarioatual);
					a.setAcao("Professor "+cpf.getText()+" Excluido");
					conexao.inserir(a);//insere nas ações recentes
					FramePai.a.add(a);
					Principal.AtualizarAcoes();
					JOptionPane.showMessageDialog(null, "Professor excluido com sucesso.");
				}
				}
			}
		});
		btnExcluir.setBounds(780, 400, 114, 23);
		contentPane.add(btnExcluir);
		
		btnRelacionar = new JButton("Relacionar");
		btnRelacionar.setOpaque(false);
		btnRelacionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RelacionaMateriaProfessor relacionapm= new RelacionaMateriaProfessor();
				relacionapm.setVisible(true);
			}
		});
		btnRelacionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		btnRelacionar.setBounds(780, 433, 114, 23);
		contentPane.add(btnRelacionar);
		
		JLabel lblNewLabel_31 = new JLabel("New label");
		lblNewLabel_31.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!busca.getText().equals("")){
				if(conexao.buscarProfessores(busca.getText().toString())==null)//verifica se a pesquisa teve resultado
				{
					JOptionPane.showMessageDialog(null, "CPF não cadastrado");
				}
				else
				{
					btnExcluir.setEnabled(true);
					btnAlterar.setEnabled(true);
					Professor p= new Professor();
					p=conexao.buscarProfessores(busca.getText().toString());
					nome.setText(p.getNome());
					cpf.setText(p.getCPF());
					rg.setText(p.getRg());
					cel.setText(p.getCelular());
					tel.setText(p.getTelefone());
					cel.setText(p.getCelular());
					senha.setText(p.getSenha());
				    sigla.setText(p.getSigla());
				   
				    endereco.setText(p.getEndereco().getRua());//preenche os campos com o resultado da busca
				    cep.setText(p.getEndereco().getCEP());
				    numero.setText(String.valueOf(p.getEndereco().getNumero()));
				    complemento.setText(p.getEndereco().getComplemento());
				    estado.setText(p.getEndereco().getEstado());
				    bairro.setText(p.getEndereco().getBairro());
				    cidade.setText(p.getEndereco().getCidade());
				    
				    btnCadastrar.setEnabled(false);
				    List<Horario> h;
				    h=conexao.buscarHorarios_Professor(p);//pega os horarios desse professor
				    segini.setText(h.get(0).getEntrada().toString());
				    segfim.setText(h.get(0).getSaida().toString());
				    
				    terini.setText(h.get(1).getEntrada().toString());
				    terfim.setText(h.get(1).getSaida().toString());
				    
				    quaini.setText(h.get(2).getEntrada().toString());
				    quafim.setText(h.get(2).getSaida().toString());
				    
				    quiini.setText(h.get(3).getEntrada().toString());
				    quifim.setText(h.get(3).getSaida().toString());
				    
				    sexini.setText(h.get(4).getEntrada().toString());//preenche os text fields com os horarios correspondentes
				    sexfim.setText(h.get(4).getSaida().toString());
				}
				
				 
				}else{
					JOptionPane.showMessageDialog(CadastroProf.this, "Sem parametro para pesquisa");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_31.setIcon( new ImageIcon(CadastroAluno.class.getResource("/fundomousepesquisar.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_31.setIcon( new ImageIcon(CadastroAluno.class.getResource("/fundobotaopesquisar.png")));
			}
		});
		lblNewLabel_31.setIcon(new ImageIcon(CadastroAluno.class.getResource("/fundobotaopesquisar.png")));
		lblNewLabel_31.setBounds(928, 188, 32, 32);
		contentPane.add(lblNewLabel_31);
		
		JButton btnPreencher = new JButton("Preencher");
		btnPreencher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nome.setText("Ulisses Claudio Pereira Arias");
				cpf.setText("12345678936");
				rg.setText("123456789");
				cel.setText("911111111");
				tel.setText("11111111");
				sigla.setText("UCP");
				senha.setText("12345");
				
				endereco.setText("Rua Tapaxana");
				cep.setText("12345678");
				numero.setText("98");
				cidade.setText("Diadema");
				estado.setText("SP");
				bairro.setText("Demarchi");
				
				segini.setText("0730");
				terini.setText("0730");
				quaini.setText("0730");
				quiini.setText("0730");
				sexini.setText("0730");
				
				segfim.setText("1230");
				terfim.setText("1230");
				quafim.setText("1230");
				quifim.setText("1230");
				sexfim.setText("1230");
			}
		});
		btnPreencher.setBounds(57, 536, 89, 23);
		contentPane.add(btnPreencher);
		
		//---------------
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroProf.class.getResource("/FundoCadastroProfessor2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
	}
}
