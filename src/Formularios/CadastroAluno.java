package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JButton;

import model.AcaoRecente;
import model.Aluno;
import model.Materia;
import model.Responsavel;
import model.Endereco;
import model.Turma;
import bd.Conexao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;

public class CadastroAluno extends JFrame {

	
	public JLabel Nome;
	public JTextField nome_al;
	public JLabel CPF;
	public JTextField cpf_al;
	public JLabel RM;
	public JTextField rm_al;
	public JLabel Telefone;
	public JTextField telefone_al;
	public JLabel Celular;
	public JTextField celular_al;
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
	private JTextField cpf_res;
	private JTextField nome_res;
	private JTextField cel_res;
	private JLabel lblRg;
	private JTextField rg_res;
	private JLabel lblTelefoneComercial;
	private JTextField tel_res;
	private JLabel lblRamal;
	private JTextField ramal_res;
	private JTextField buscacampo;
	private JLabel lblinsiraORm;
	private JButton button_1;

	private Conexao conexao = Login.conexao;
	private JLabel lblGrau;
	
	private List<Turma> lista_grau;
	private JTextField rg_al;
	private Turma turma_al;
	private Integer nu_al;
	
	private String local_foto;
	private Image imgtemp;
	private JTextField numero_al;
	private JLabel lblNumero;
	private JLabel lblSituao;
	private JTextField situação_al;
	private JButton btnPreencher;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					CadastroAluno frame = new CadastroAluno();
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
	public CadastroAluno() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Foto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(559, 110, 134, 196);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel foto_aluno = new JLabel("");
		foto_aluno.setBounds(10, 15, 114, 144);
		foto_aluno.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		panel.add(foto_aluno);
		
		JButton btnEscolherFoto = new JButton("Escolher...");
		btnEscolherFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnEscolherFoto.getText().equals("Escolher..."))
				{
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes()));
					if (jFileChooser.showOpenDialog(CadastroAluno.this) == JFileChooser.APPROVE_OPTION)
					{
						ImageIcon ii = new ImageIcon(jFileChooser.getSelectedFile().getPath());
						Image im = ii.getImage();
						Image myImg = im.getScaledInstance(foto_aluno.getWidth(), foto_aluno.getHeight(), Image.SCALE_SMOOTH);

						foto_aluno.setIcon(new ImageIcon(myImg));
						local_foto = jFileChooser.getSelectedFile().getPath();
						imgtemp = im;
						btnEscolherFoto.setText("Remover");
					}
				}
				else
				{
					foto_aluno.setIcon(null);
					local_foto = "";
					btnEscolherFoto.setText("Escolher...");
				}
			}
		});
		btnEscolherFoto.setBounds(10, 162, 114, 23);
		panel.add(btnEscolherFoto);
		JPanel InformacoesPessoais = new JPanel();
		InformacoesPessoais.setBorder(new TitledBorder(null, "Informações Pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		InformacoesPessoais.setBounds(55, 110, 494, 148);
		contentPane.add(InformacoesPessoais);
		InformacoesPessoais.setLayout(null);
		
		Nome = new JLabel("Nome: ");
		Nome.setBounds(20, 31, 61, 18);
		InformacoesPessoais.add(Nome);
		
		CPF = new JLabel("CPF: ");
		CPF.setBounds(303, 31, 42, 18);
		InformacoesPessoais.add(CPF);
		
		RM = new JLabel("RM: ");
		RM.setBounds(20, 58, 42, 18);
		InformacoesPessoais.add(RM);
		
		Telefone = new JLabel("Telefone: ");
		Telefone.setBounds(325, 58, 61, 18);
		InformacoesPessoais.add(Telefone);
		
		Celular = new JLabel("Celular:");
		Celular.setBounds(174, 58, 55, 18);
		InformacoesPessoais.add(Celular);
		
		nome_al = new JTextField ("");
		nome_al.setBounds(66, 31, 225, 18);
		InformacoesPessoais.add(nome_al);
		
		cpf_al = new JTextField ("");
		cpf_al.setBounds(331, 31, 145, 18);
		InformacoesPessoais.add(cpf_al);
		
		rm_al = new JTextField ("");
		rm_al.setBounds(66, 58, 100, 18);
		InformacoesPessoais.add(rm_al);
		
		telefone_al = new JTextField ("");
		telefone_al.setBounds(376, 58, 100, 18);
		InformacoesPessoais.add(telefone_al);
		
		celular_al = new JTextField ("");
		celular_al.setBounds(216, 58, 100, 18);
		InformacoesPessoais.add(celular_al);
		
		lblGrau = new JLabel("Grau: ");
		lblGrau.setBounds(20, 87, 46, 18);
		InformacoesPessoais.add(lblGrau);
		//-------------------------------------------------
		
		lista_grau=conexao.buscarTurmas();
		
		JComboBox comboBoxgrau = new JComboBox();
		comboBoxgrau.setBounds(66, 87, 100, 18);
		
		lista_grau=conexao.buscarTurmas();
		
		 comboBoxgrau.addItem("Selecione um Grau...");
		  for(int i=0;i<lista_grau.size();i++)
		  {
		  comboBoxgrau.addItem(lista_grau.get(i).getGrau());
		  }
		  InformacoesPessoais.add(comboBoxgrau);
		  
		  JComboBox comboBoxclasse = new JComboBox();
			comboBoxclasse.setBounds(219, 87, 100, 18);
			
			
			
			 comboBoxclasse.addItem("Selecione um Classe...");
			  for(int i=0;i<lista_grau.size();i++)
			  {
			  comboBoxclasse.addItem(lista_grau.get(i).getClasse());
			  }
			InformacoesPessoais.add(comboBoxclasse);
			
			
			JComboBox comboBoxcurso = new JComboBox();
			comboBoxcurso.setBounds(376, 87, 100, 18);
			 comboBoxcurso.addItem("Selecione um Curso...");
			  for(int i=0;i<lista_grau.size();i++)
			  {
			  comboBoxcurso.addItem(lista_grau.get(i).getCurso());
			  }
			InformacoesPessoais.add(comboBoxcurso);
		  //-------------------------------------------------
		
		JLabel lblNewLabel_1 = new JLabel("Classe: ");
		lblNewLabel_1.setBounds(177, 87, 46, 18);
		InformacoesPessoais.add(lblNewLabel_1);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Curso: ");
		lblNewLabel_2.setBounds(335, 87, 46, 18);
		InformacoesPessoais.add(lblNewLabel_2);
		
		rg_al = new JTextField();
		rg_al.setBounds(66, 112, 86, 18);
		InformacoesPessoais.add(rg_al);
		rg_al.setColumns(10);
		
		JLabel lblRg_1 = new JLabel("RG:");
		lblRg_1.setBounds(20, 112, 46, 14);
		InformacoesPessoais.add(lblRg_1);
		
		numero_al = new JTextField();
		numero_al.setBounds(205, 112, 86, 18);
		InformacoesPessoais.add(numero_al);
		numero_al.setColumns(10);
		
		lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(162, 112, 46, 18);
		InformacoesPessoais.add(lblNumero);
		
		lblSituao = new JLabel("Situa\u00E7\u00E3o:");
		lblSituao.setBounds(303, 112, 46, 18);
		InformacoesPessoais.add(lblSituao);
		
		situação_al = new JTextField();
		situação_al.setText("");
		situação_al.setBounds(359, 112, 86, 18);
		InformacoesPessoais.add(situação_al);
		situação_al.setColumns(10);
		
	
		

		EnderecoGrupo = new JPanel();
		EnderecoGrupo.setBounds(55, 260, 494, 123);
		contentPane.add(EnderecoGrupo);
		EnderecoGrupo.setBorder(new TitledBorder(null, "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		EnderecoGrupo.setLayout(null);
		
		Endereco = new JLabel("Endere\u00E7o:");
		Endereco.setBounds(20, 31, 72, 18);
		EnderecoGrupo.add(Endereco);
		
		Numero = new JLabel("N\u00FAmero: ");
		Numero.setBounds(20, 58, 61, 18);
		EnderecoGrupo.add(Numero);
		
		Complemento = new JLabel("Complemento: ");
		Complemento.setBounds(158, 58, 97, 18);
		EnderecoGrupo.add(Complemento);
		
		CEP = new JLabel("CEP: ");
		CEP.setBounds(349, 31, 36, 18);
		EnderecoGrupo.add(CEP);
		
		Bairro = new JLabel("Bairro: ");
		Bairro.setBounds(20, 85, 61, 18);
		EnderecoGrupo.add(Bairro);
		
		Cidade = new JLabel("Cidade: ");
		Cidade.setBounds(229, 85, 61, 18);
		EnderecoGrupo.add(Cidade);
		
		Estado = new JLabel("Estado: ");
		Estado.setBounds(371, 58, 61, 18);
		EnderecoGrupo.add(Estado);
		
		estado = new JTextField ("");
		estado.setBounds(416, 58, 61, 18);
		EnderecoGrupo.add(estado);
		
		endereco = new JTextField ("");
		endereco.setBounds(75, 31, 264, 18);
		EnderecoGrupo.add(endereco);
		
		numero = new JTextField ("");
		numero.setBounds(75, 58, 61, 18);
		EnderecoGrupo.add(numero);
		
		complemento = new JTextField ("");
		complemento.setBounds(235, 58, 126, 18);
		EnderecoGrupo.add(complemento);
		
		cep = new JTextField ("");
		cep.setBounds(380, 31, 97, 18);
		EnderecoGrupo.add(cep);
		
		bairro = new JTextField ("");
		bairro.setBounds(75, 85, 144, 18);
		EnderecoGrupo.add(bairro);
		
		cidade = new JTextField ("");
		cidade.setBounds(275, 85, 202, 18);
		EnderecoGrupo.add(cidade);
		
		JPanel Responsavel = new JPanel();
		Responsavel.setLayout(null);
		Responsavel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Respons\u00E1vel", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Responsavel.setBounds(55, 385, 494, 123);
		contentPane.add(Responsavel);
		
		JLabel lblNewLabel = new JLabel("CPF: ");
		lblNewLabel.setBounds(303, 31, 46, 18);
		Responsavel.add(lblNewLabel);
		
		cpf_res = new JTextField();
		cpf_res.setBounds(331, 31, 145, 18);
		Responsavel.add(cpf_res);
		cpf_res.setColumns(10);
		
		JLabel label = new JLabel("Nome: ");
		label.setBounds(23, 31, 61, 18);
		Responsavel.add(label);
		
		nome_res = new JTextField("");
		nome_res.setBounds(66, 31, 225, 18);
		Responsavel.add(nome_res);
		
		JLabel label_2 = new JLabel("Celular:");
		label_2.setBounds(20, 85, 61, 18);
		Responsavel.add(label_2);
		
		cel_res = new JTextField("");
		cel_res.setBounds(66, 85, 100, 18);
		Responsavel.add(cel_res);
		
		lblRg = new JLabel("RG: ");
		lblRg.setBounds(23, 58, 46, 18);
		Responsavel.add(lblRg);
		
		rg_res = new JTextField();
		rg_res.setBounds(66, 58, 95, 18);
		Responsavel.add(rg_res);
		rg_res.setColumns(10);
		
		lblTelefoneComercial = new JLabel("Telefone Comercial:");
		lblTelefoneComercial.setBounds(171, 58, 103, 18);
		Responsavel.add(lblTelefoneComercial);
		
		tel_res = new JTextField();
		tel_res.setBounds(271, 58, 101, 18);
		Responsavel.add(tel_res);
		tel_res.setColumns(10);
		
		lblRamal = new JLabel("Ramal:");
		lblRamal.setBounds(381, 58, 46, 18);
		Responsavel.add(lblRamal);
		
		ramal_res = new JTextField();
		ramal_res.setBounds(421, 58, 55, 18);
		Responsavel.add(ramal_res);
		ramal_res.setColumns(10);
		
		
		
		
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setOpaque(false);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cidade.getText().equals("")
						|| nome_al.getText().equals("")
						|| cpf_al.getText().equals("")
						|| rm_al.getText().equals("")
						|| rg_al.getText().equals("")
						|| telefone_al.getText().equals("")
						|| celular_al.getText().equals("")
						|| endereco.getText().equals("")
						|| numero.getText().equals("")
						|| cep.getText().equals("")
						|| bairro.getText().equals("")
						|| estado.getText().equals("")
						|| nome_res.getText().equals("")
						|| cpf_res.getText().equals("")
						|| rg_res.getText().equals("")
						|| cel_res.getText().equals("")
						|| tel_res.getText().equals("")
						|| ramal_res.getText().equals("")
						|| numero_al.getText().equals("")) {  // Verifica se não há campos em branco.
					
				} else {
					if(verificarnumerico(numero.getText())&&verificarnumerico(ramal_res.getText())){ // Verifica se ambos são numericos 
					
					
						Endereco e1 = new Endereco();	//Cria um novo endereço.
						e1.setBairro(bairro.getText()); // Adiciona bairro.
						e1.setCEP(cep.getText());	// Adiciona CEP.
						e1.setCidade(cidade.getText());	// Adiciona cidade.
						e1.setComplemento(complemento.getText());	// Adiciona complemento.
						e1.setEstado(estado.getText());		// Adiciona estado.
						e1.setNumero(Integer.parseInt(numero.getText()));	// Adiciona numero.
						e1.setRua(endereco.getText());	// Adiciona rua.
						e1.setTipo("Residencial");	// Adiciona tipo.
						
						Responsavel r =new Responsavel();	//Cria um novo responsavel.
						r.setNome(nome_res.getText());		//Adiciona nome.
						r.setCPF(cpf_res.getText());	// Adiciona bairro.
						r.setRg(rg_res.getText());		// Adiciona bairro.
						r.setCelular(cel_res.getText());	// Adiciona celular.
						r.setTel_com(tel_res.getText());	//Adiciona telefone.
						r.setRamal(Integer.parseInt(ramal_res.getText()));		//Adiciona ramal.
						r.setEndereco(e1);
											
						
						Aluno f = new Aluno();		//Cria um novo aluno.
						f.setNome(nome_al.getText());	//Adiciona nome.
						f.setCelular(celular_al.getText());		//Adiciona celular.
						f.setCPF(cpf_al.getText());		//Adiciona CPF.
						f.setRg(rg_al.getText());		//Adiciona RG.
						f.setTelefone(telefone_al.getText());	//Adiciona telefone.
						f.setRM(rm_al.getText());		//Adiciona RM.
						f.setEndereco(e1);		//Adiciona o endereço do responsavel no aluno.
						f.setResponsavel(r);	//Adiciona o responsavel.
						
						
						
						if (!local_foto.equals(""))
						{
							try
							{
								Image img = ImageIO.read(new File(local_foto));
								f.setFoto(img);
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						
                        int a=-1;
				  		for(int i=0;i<lista_grau.size();i++)
						  {
				  			if(lista_grau.get(i).getGrau()==Integer.parseInt(comboBoxgrau.getSelectedItem().toString()) && (lista_grau.get(i).getClasse().equals(comboBoxclasse.getSelectedItem().toString()) && (lista_grau.get(i).getCurso().equals(comboBoxcurso.getSelectedItem().toString()) )))
				  			{
						   a=i;
						  }}
				  	
				  		conexao.inserir(f);
				  		
				  		if(a!=-1)
				  		{
				  		Turma t = new Turma();
				  		t=conexao.buscarTurmas(lista_grau.get(a).getIdTurma());
				  		
				  		conexao.inserir(t, f, Integer.parseInt(numero_al.getText()), situação_al.getText());
		  				
				  		}
				  		AcaoRecente a1 =new AcaoRecente();
						a1.setFuncionario(FramePai.usuarioatual);
						a1.setAcao("Aluno "+f.getRM()+" Adicionado");
						conexao.inserir(a1);
						FramePai.a.add(a1);
						Principal.AtualizarAcoes();//insere nas ações recentes
						FramePai.Confirma(CadastroAluno.this);
				  	
						
						
						
			
		}}}});
		btnCadastrar.setBounds(780, 332, 114, 23);
		contentPane.add(btnCadastrar);
		
		buscacampo = new JTextField();
		buscacampo.setColumns(10);
		buscacampo.setBounds(728, 194, 193, 20);
		contentPane.add(buscacampo);
		
		lblinsiraORm = new JLabel("<html>Insira o RM de um aluno j\u00E1 cadastrado para visualizar e/ou alterar o registro do mesmo.</html>");
		lblinsiraORm.setForeground(Color.WHITE);
		lblinsiraORm.setBounds(728, 225, 226, 59);
		contentPane.add(lblinsiraORm);
		
		situação_al.setEnabled(false);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				button_1.setEnabled(true);
				if(conexao.buscarAlunos(buscacampo.getText().toString())==null)  // Verifica se o campo CPF é nulo.
				{
					JOptionPane.showMessageDialog(null, "Aluno não cadastrado");
				}
				else
				{
					Aluno a = new Aluno();		//Cria um aluno.
					
					if(conexao.buscarAlunos(buscacampo.getText().toString())==null)
					{
						JOptionPane.showMessageDialog(null, "CPF não cadastrado");
					}
					else
					{
						
					a=conexao.buscarAlunos(buscacampo.getText().toString());
					nome_al.setText(a.getNome());//preenche os campos com o resultado da pesquisa
					cpf_al.setText(a.getCPF());
					rm_al.setText(a.getRM());
					celular_al.setText(a.getCelular());
					telefone_al.setText(a.getTelefone());
					if(a.getFoto()!=null){
					foto_aluno.setIcon(new ImageIcon(a.getFoto().getScaledInstance(foto_aluno.getWidth(), foto_aluno.getHeight(), Image.SCALE_SMOOTH)));
					
					imgtemp = a.getFoto();
					local_foto="";
					}
					rg_al.setText(a.getRg());
					btnEscolherFoto.setText("Remover");
				   
				    endereco.setText(a.getEndereco().getRua());
				    cep.setText(a.getEndereco().getCEP());
				    numero.setText(String.valueOf(a.getEndereco().getNumero()));
				    complemento.setText(a.getEndereco().getComplemento());
				    estado.setText(a.getEndereco().getEstado());
				    bairro.setText(a.getEndereco().getBairro());
				    cidade.setText(a.getEndereco().getCidade());
				    
				    nome_res.setText(a.getResponsavel().getNome());
					cpf_res.setText(a.getResponsavel().getCPF());
					rg_res.setText(a.getResponsavel().getRg());
					tel_res.setText(a.getResponsavel().getTel_com());
					ramal_res.setText(String.valueOf(a.getResponsavel().getRamal()));
					cel_res.setText(a.getResponsavel().getCelular());
					situação_al.setText((conexao.buscarTurmas_Situacao(a.getRM())==null ? "" : conexao.buscarTurmas_Situacao(a.getRM())));
					numero_al.setText(String.valueOf((conexao.buscarTurmas_Numero(a.getRM())==null ? "" : conexao.buscarTurmas_Numero(a.getRM()))));
					Turma t=conexao.buscarTurmas_Alunos(a.getRM());
					turma_al=conexao.buscarTurmas_Alunos(a.getRM());
					if(t!=null){
					comboBoxgrau.setSelectedItem(t.getGrau());
					comboBoxclasse.setSelectedItem(t.getClasse());
					comboBoxcurso.setSelectedItem(t.getCurso());
					}
					}
				    btnCadastrar.setEnabled(false);
				    situação_al.setEnabled(true);
				    nu_al=conexao.buscarTurmas_Numero(a.getRM());
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
		
		button_1 = new JButton("Alterar");
		button_1.setEnabled(false);
		button_1.setOpaque(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cidade.getText().equals("")
					      || nome_al.getText().equals("")
					      || cpf_al.getText().equals("")
					      || rm_al.getText().equals("")
					      || telefone_al.getText().equals("")
					      || celular_al.getText().equals("")
					      || endereco.getText().equals("")
					      || numero.getText().equals("")
					      || cep.getText().equals("")
					      || bairro.getText().equals("")
					      || estado.getText().equals("")
					      || nome_res.getText().equals("")
					      || cpf_res.getText().equals("")
					      || rg_res.getText().equals("")
					      || cel_res.getText().equals("")
					      || tel_res.getText().equals("")
					      || ramal_res.getText().equals("")
					      || numero_al.getText().equals("") ) {
					     JOptionPane.showMessageDialog(CadastroAluno.this,"Existe(m) campo(s) em branco!" );
					    } else {
					     if(verificarnumerico(numero.getText())&&verificarnumerico(ramal_res.getText())){
					    	 if(JOptionPane.showConfirmDialog(CadastroAluno.this, "Tem certeza que deseja alterar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
								{
					     
					      Endereco e1 = new Endereco();
					      e1.setBairro(bairro.getText());
					      e1.setCEP(cep.getText());
					      e1.setCidade(cidade.getText());
					      e1.setComplemento(complemento.getText());
					      e1.setEstado(estado.getText());
					      e1.setNumero(Integer.parseInt(numero.getText()));
					      e1.setRua(endereco.getText());
					      e1.setTipo("Residencial");
					      e1.setIdEndereco(conexao.buscarAlunos(rm_al.getText()).getEndereco().getIdEndereco());
					      
					      Responsavel r =new Responsavel();
					      r.setNome(nome_res.getText());
					      r.setCPF(cpf_res.getText());
					      r.setRg(rg_res.getText());
					      r.setCelular(cel_res.getText());
					      r.setTel_com(tel_res.getText());
					      r.setRamal(Integer.parseInt(ramal_res.getText()));
					      r.setEndereco(e1);
					      
					      conexao.Alterar(r);
					      
					      
					      Aluno f = new Aluno();
					      f.setRg(rg_al.getText());
					      f.setNome(nome_al.getText());
					      f.setCelular(celular_al.getText());
					      f.setCPF(cpf_al.getText());
					      f.setTelefone(telefone_al.getText());
					      f.setRM(rm_al.getText());
					      f.setEndereco(e1);
					      f.setResponsavel(r);
					      f.setFoto(imgtemp);
					      
					      
					      
					      conexao.Alterar(f);//altera o aluno com as devidas informações
					      FramePai.Confirma(CadastroAluno.this);
					      AcaoRecente a =new AcaoRecente();
							a.setFuncionario(FramePai.usuarioatual);
							a.setAcao("Aluno "+f.getRM()+" Alterado");
							conexao.inserir(a);
							FramePai.a.add(a);
							Principal.AtualizarAcoes();//insere nas ações recentes
					      imgtemp=null;
					      local_foto="";
					      
					      Turma t=new Turma();
					      t.setGrau(Integer.parseInt(comboBoxgrau.getSelectedItem().toString()));
					      t.setCurso(comboBoxcurso.getSelectedItem().toString());
					      t.setClasse(comboBoxclasse.getSelectedItem().toString());
					      
					      if(t.getGrau()!=turma_al.getGrau()||!t.getCurso().equals(turma_al.getCurso())||!t.getClasse().equals(turma_al.getClasse())){
					    	  conexao.Alterar(turma_al, f, situação_al.getText(),nu_al );
					    	  conexao.inserir(t, f, Integer.parseInt(numero_al.getText()), "");
					    	  
					    	  
					      }
					   
					  }
					     situação_al.setEnabled(false);
					     } }
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		button_1.setBounds(780, 366, 114, 23);
		contentPane.add(button_1);
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
				
				cadastroaluno.setForeground(Color.WHITE);
				cadastroaluno.setFont(new Font("Arial", Font.BOLD, 14));
				cadastroaluno.setBounds(434, 79, 54, 14);
				contentPane.add(cadastroaluno);
		   
				
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
		
		btnPreencher = new JButton("Preencher");
		btnPreencher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				endereco.setText("Rua Doutor Arnaldo");
				cep.setText("12345878");
				numero.setText("98");
				cidade.setText("Diadema");
				estado.setText("SP");
				bairro.setText("Demarchi");
				
				nome_al.setText("Rafael Antonio");
				cpf_al.setText("12345678998");
				rg_al.setText("323456789");
				celular_al.setText("911111111");
				telefone_al.setText("11111111");
				numero_al.setText("36");
				rm_al.setText("L131187");
				
				nome_res.setText("José Antonio");
				cpf_res.setText("12345678975");
				rg_res.setText("223456789");
				cel_res.setText("911111111");
				tel_res.setText("11111111");
				ramal_res.setText("3");
				
				btnEscolherFoto.setText("Remover");
				
				ImageIcon ii = new ImageIcon(new File("C:/foto.jpg").getPath());
				Image im = ii.getImage();
				Image myImg = im.getScaledInstance(foto_aluno.getWidth(), foto_aluno.getHeight(), Image.SCALE_SMOOTH);

				
				foto_aluno.setIcon(new ImageIcon(myImg));
				local_foto = new File("C:/foto.jpg").getPath();
				imgtemp = im;
				
			}
		});
		btnPreencher.setBounds(57, 536, 89, 23);
		contentPane.add(btnPreencher);
						
		//------------------
		
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroAluno.class.getResource("/FundoCadastroAluno2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
	}
				public Boolean verificarnumerico (String a){
					Boolean c=false;
					int d;
					try{
						
						d=Integer.parseInt(a);
						c=true;
					}catch(Exception e){
						c=false;
						
					}finally{
					return c;
					}
				}
}
