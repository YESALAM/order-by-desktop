package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;











import bd.Conexao;
import model.AcaoRecente;
import model.Endereco;
import model.Funcionario;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlterarFunc extends JFrame {
	
	public JLabel Nome;
	public JTextField nome;
	public JLabel CPF;
	public JTextField cpf;
	public JLabel RG;
	public JTextField rg;
	public JLabel Telefone;
	public JTextField telefone;
	public JLabel Celular;
	public JTextField celular;
	public JLabel Logradouro;
	public JTextField logradouro;
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
	public JLabel Tipo;
	public JComboBox tipo;
	public JLabel Senha;
	public JTextField senha;
	public JButton confirmar;

	private JPanel contentPane;
	private JPanel EnderecoGrupo;
	private JPanel InformacoesConta;
	private JLabel lblNewLabel_1;
	private JLabel lblAlterarConta;
	private JLabel lblConfiguraes;
	private JLabel acaoNovoUsuario;
	
	
	private Conexao conexao = Login.conexao;
	private JComboBox tipoendereco;
	private JTextField resenha;
	private JLabel Cargo;
	private JTextField cargo;
	
	public static ConfirmarCadastroFunc confirmarcadastrofunc;
	private JComboBox comboBox;
	
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					AlterarFunc frame = new AlterarFunc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

	public AlterarFunc() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		confirmar =  new JButton ("Salvar");
		confirmar.setOpaque(false);
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cidade.getText().equals("")
						|| nome.getText().equals("")
						|| cpf.getText().equals("")
						|| rg.getText().equals("")
						|| telefone.getText().equals("")
						|| celular.getText().equals("")
						|| logradouro.getText().equals("")
						|| numero.getText().equals("")
						|| cep.getText().equals("")
						|| bairro.getText().equals("")
						|| comboBox.getSelectedItem().toString().equals("")
						|| senha.getText().equals("")|| cargo.getText().equals("")
						|| tipo.getSelectedItem().toString()
								.equals("Escolha um tipo")) // Verifica se não há campos em branco.
				{
					JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco.");
				} 
				else 
				{
					if(verificarnumerico(numero.getText()))//Inicia uma confirmação de consistencia numerica e caso retorne true , realiza as funções abaixo.
					{
						if(JOptionPane.showConfirmDialog(AlterarFunc.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
						{
					confirmarcadastrofunc = new ConfirmarCadastroFunc();
					confirmarcadastrofunc.setModal(true); //Verefica o cadastro abrindo um modal de confirmação
					confirmarcadastrofunc.setVisible(true);
					if (ConfirmarCadastroFunc.libera == true &&senha.getText().equals(resenha.getText())) // Caso o cadastro essa confirmado com sucesso as informações são inseridas.
				
				
				{
					
				
				Funcionario f = new Funcionario();   //Cria uma novo funcionario
				f.setNome(nome.getText());    //Altera nome.
				f.setCPF(cpf.getText());   //Altera cpf.
				f.setCelular(celular.getText());    //Altera celular.
				f.setTelefone(telefone.getText());    //Altera telefone.
				f.setTipo(tipo.getSelectedItem().toString());    //Altera tipo.
				f.setRg(rg.getText());	    //Altera RG.
				f.setSenha(senha.getText());    //Altera senha.
				f.setCargo(cargo.getText());    //Altera cargo.
				f.setRg(rg.getText());   //Altera RG.
				Endereco v = new Endereco(); //Cria uma novo endereço.
				v.setIdEndereco(FramePai.usuarioatual.getEndereco().getIdEndereco()); //Pega o id ja recebido na hora do login.
				v.setRua(logradouro.getText());   //Altera logradouro.
				v.setNumero(Integer.parseInt(numero.getText()));	//Altera numero da casa.
				v.setComplemento(complemento.getText());	//Altera complemento.
				v.setCEP(cep.getText());	//Altera Cep.
				v.setEstado(comboBox.getSelectedItem().toString());		//Altera Estado.
				v.setBairro(bairro.getText());		//Altera bairro.
				v.setCidade(cidade.getText());		//Altera cidade.
				v.setTipo(tipoendereco.getSelectedItem().toString());	//Altera tipo.
				f.setEndereco(v); 	//coloca o endereço no funcionario.
			    conexao.Alterar(f);
			    conexao.Alterar(v);
			    FramePai.Confirma(AlterarFunc.this);
			    AcaoRecente a =new AcaoRecente();
				a.setFuncionario(FramePai.usuarioatual);
				a.setAcao("Funcionario "+f.getCPF()+" Alterado");
				conexao.inserir(a);
				FramePai.a.add(a);
				Principal.AtualizarAcoes();//insere nas ações recentes
				FramePai.usuarioatual=f;  //Altera informações recebidas no login.
				}
				else
				{
					JOptionPane.showMessageDialog(null, "As senhas não são iguais.");
				}
			}
					}
					}
			}
		});
		
		confirmar.setBounds(461, 488, 103, 27);
		contentPane.add(confirmar);
		
		lblConfiguraes = new JLabel("Configura\u00E7\u00F5es");
		lblConfiguraes.setForeground(Color.WHITE);
		lblConfiguraes.setFont(new Font("Arial", Font.BOLD, 14));
		lblConfiguraes.setBounds(56, 39, 121, 14);
		contentPane.add(lblConfiguraes);
		
		lblAlterarConta = new JLabel("Alterar Conta");
		lblAlterarConta.setForeground(Color.WHITE);
		lblAlterarConta.setFont(new Font("Arial", Font.BOLD, 14));
		lblAlterarConta.setBounds(79, 79, 121, 14);
		contentPane.add(lblAlterarConta);
		
		lblNewLabel_1 = new JLabel("Novo Us\u00FAario");
		
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setBounds(245, 79, 95, 14);
		contentPane.add(lblNewLabel_1);
		
		acaoNovoUsuario = new JLabel("");
		
		acaoNovoUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblNewLabel_1.setForeground(new Color(95,93,93));
				acaoNovoUsuario.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseNovarConta.png")));
						
			}
					
			@Override
			public void mouseExited(MouseEvent e) {
				
				lblNewLabel_1.setForeground(new Color(0,0,0));
				acaoNovoUsuario.setIcon(null);
					
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FramePai.AbrirConfiguracaoNovaConta();
			}
		});
		acaoNovoUsuario.setBounds(210, 73, 163, 26);
		contentPane.add(acaoNovoUsuario);
		
		JPanel InformacoesPessoais = new JPanel();
		InformacoesPessoais.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es Pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		InformacoesPessoais.setBounds(250, 124, 550, 127);
		contentPane.add(InformacoesPessoais);
		InformacoesPessoais.setLayout(null);
		
		Nome = new JLabel("Nome: ");
		Nome.setBounds(43, 31, 61, 18);
		InformacoesPessoais.add(Nome);
		
		CPF = new JLabel("CPF: ");
		CPF.setBounds(281, 58, 43, 18);
		InformacoesPessoais.add(CPF);
		
		RG = new JLabel("RG: ");
		RG.setBounds(43, 58, 61, 18);
		InformacoesPessoais.add(RG);
		
		Telefone = new JLabel("Telefone: ");
		Telefone.setBounds(269, 85, 61, 18);
		InformacoesPessoais.add(Telefone);
		
		Celular = new JLabel("Celular:");
		Celular.setBounds(43, 85, 61, 18);
		InformacoesPessoais.add(Celular);
		
		nome = new JTextField (FramePai.usuarioatual.getNome().toString()); // Pega nome recebido do login.
		nome.setBounds(89, 31, 205, 18);
		InformacoesPessoais.add(nome);
		
		cpf = new JTextField (FramePai.usuarioatual.getCPF().toString()); // Pega cpf recebido do login.
		cpf.setBounds(316, 58, 184, 18);
		InformacoesPessoais.add(cpf);
		
		rg = new JTextField (FramePai.usuarioatual.getRg().toString());
		rg.setBounds(89, 58, 184, 18);
		InformacoesPessoais.add(rg);
		
		telefone = new JTextField (FramePai.usuarioatual.getTelefone().toString()); // Pega telefone recebido do login.
		telefone.setBounds(326, 85, 174, 18);
		InformacoesPessoais.add(telefone);
		
		celular = new JTextField (FramePai.usuarioatual.getCelular().toString()); // Pega celular recebido do login.
		celular.setBounds(89, 85, 174, 18);
		InformacoesPessoais.add(celular);
		
		Cargo = new JLabel("Cargo: ");
		Cargo.setBounds(304, 33, 46, 14);
		InformacoesPessoais.add(Cargo);
		
		cargo = new JTextField(FramePai.usuarioatual.getCargo().toString());
		cargo.setBounds(344, 31, 156, 18);
		InformacoesPessoais.add(cargo);
		cargo.setColumns(10);
		
		EnderecoGrupo = new JPanel();
		EnderecoGrupo.setBounds(250, 249, 550, 127);
		contentPane.add(EnderecoGrupo);
		EnderecoGrupo.setBorder(new TitledBorder(null, "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		EnderecoGrupo.setLayout(null);
		
		Logradouro = new JLabel("Endere\u00E7o:");
		Logradouro.setBounds(43, 31, 72, 18);
		EnderecoGrupo.add(Logradouro);
		
		Numero = new JLabel("N\u00FAmero: ");
		Numero.setBounds(284, 31, 50, 18);
		EnderecoGrupo.add(Numero);
		
		Complemento = new JLabel("Complemento: ");
		Complemento.setBounds(181, 58, 97, 18);
		EnderecoGrupo.add(Complemento);
		
		CEP = new JLabel("CEP: ");
		CEP.setBounds(43, 58, 36, 18);
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
		
		logradouro = new JTextField (FramePai.usuarioatual.getEndereco().getRua().toString()); // Pega logradouro recebido do login.
		logradouro.setBounds(98, 31, 176, 18);
		EnderecoGrupo.add(logradouro);
		
		numero = new JTextField (String.valueOf(FramePai.usuarioatual.getEndereco().getNumero())); // Pega numero recebido do login.
		numero.setBounds(329, 31, 36, 18);
		EnderecoGrupo.add(numero);
		
		complemento = new JTextField ((FramePai.usuarioatual.getEndereco().getComplemento()==null? "" : FramePai.usuarioatual.getEndereco().getComplemento())); // Pega complemento recebido do login.
		complemento.setBounds(258, 58, 126, 18);
		EnderecoGrupo.add(complemento);
		
		cep = new JTextField (FramePai.usuarioatual.getEndereco().getCEP().toString()); // Pega cep recebido do login.
		cep.setBounds(98, 58, 73, 18);
		EnderecoGrupo.add(cep);
		
		bairro = new JTextField (FramePai.usuarioatual.getEndereco().getBairro());  // Pega bairro recebido do login.
		bairro.setBounds(98, 85, 144, 18);
		EnderecoGrupo.add(bairro);
		
		cidade = new JTextField (FramePai.usuarioatual.getEndereco().getCidade());  // Pega cidade recebido do login.
		cidade.setBounds(298, 85, 202, 18);
		EnderecoGrupo.add(cidade);
		
		JLabel TipoEndereco = new JLabel("Tipo: ");
		TipoEndereco.setBounds(373, 33, 46, 14);
		EnderecoGrupo.add(TipoEndereco);
		
		tipoendereco = new JComboBox();
		tipoendereco.setModel(new DefaultComboBoxModel(new String[] {"Residencial", "Comercial"}));
		tipoendereco.setSelectedIndex((FramePai.usuarioatual.getEndereco().getTipo().equals("Residencial") ? 0 : 1));
		tipoendereco.setBounds(403, 30, 97, 20);
		EnderecoGrupo.add(tipoendereco);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "TA", "PB", "PR", "PE", "PI", "RJ", "RM", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		comboBox.setBounds(439, 57, 61, 18);
		comboBox.setSelectedItem(FramePai.usuarioatual.getEndereco().getEstado());
		
		EnderecoGrupo.add(comboBox);
		
		InformacoesConta = new JPanel();
		InformacoesConta.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es da Conta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		InformacoesConta.setBounds(250, 375, 550, 102);
		contentPane.add(InformacoesConta);
		InformacoesConta.setLayout(null);
		
		Tipo = new JLabel("Tipo: ");
		Tipo.setBounds(43, 31, 61, 18);
		InformacoesConta.add(Tipo);
		
		tipo = new JComboBox();
		tipo.setBounds(98, 31, 120, 20);
		InformacoesConta.add(tipo);
		
		tipo.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Usuário"}));
		tipo.setSelectedIndex((FramePai.usuarioatual.getTipo().equals("Administrador") ? 0 : 1)); // Pega tipo de usuario recebido do login.
		
		Senha = new JLabel("Senha: ");
		Senha.setBounds(43, 58, 61, 18);
		InformacoesConta.add(Senha);
		
		senha = new JTextField ("");
		senha.setBounds(98, 58, 120, 18);
		InformacoesConta.add(senha);
		
		resenha = new JTextField("");
		resenha.setBounds(321, 58, 120, 18);
		InformacoesConta.add(resenha);
		
		JLabel Resenha = new JLabel("Redigite a senha: ");
		Resenha.setBounds(228, 58, 100, 18);
		InformacoesConta.add(Resenha);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AlterarFunc.class.getResource("/FundoAlterarconfigADM.png")));
		lblNewLabel.setBounds(0, 0, 1018, 611);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(210, 67, 163, 26);
		contentPane.add(lblNewLabel_3);
		
		
		
	}
}
