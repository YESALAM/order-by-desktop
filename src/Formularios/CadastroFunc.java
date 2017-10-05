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

import model.AcaoRecente;
import model.Funcionario;
import model.Endereco;
import bd.Conexao;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CadastroFunc extends JFrame {

	public JLabel Nome;
	public JTextField nome;
	public JLabel CPF;
	public static JTextField cpf;
	public JLabel RG;
	public JTextField rg;
	public JLabel Telefone;
	public JTextField telefone;
	public JLabel Celular;
	public JTextField celular;
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
	public JLabel Tipo;
	public JComboBox tipo;
	public JLabel Senha;
	public static JTextField senha;
	public JButton confirmar;
	private JPanel contentPane;
	private JPanel EnderecoGrupo;
	private JPanel InformacoesConta;
	private JLabel lblNovoUsu;
	private JLabel lblAlterarConta;
	private JLabel lblConfiguracoes;
	Conexao c =Login.conexao;

	public static ConfirmarCadastroFunc confirmarcadastrofunc;
	private JTextField cargo;
	private JComboBox comboBox;

	public Boolean verificarnumerico (String a){
		Boolean c=false;//verifica se a informação fornecida é compativel com o tipo inteiro
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
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					CadastroFunc frame = new CadastroFunc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CadastroFunc() {
		
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		confirmar = new JButton("Confirmar");
		confirmar.setOpaque(false);
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cidade.getText().equals("")
						|| nome.getText().equals("")
						|| cpf.getText().equals("")
						|| rg.getText().equals("")
						|| telefone.getText().equals("")
						|| celular.getText().equals("")
						|| endereco.getText().equals("")
						|| numero.getText().equals("")
						|| cep.getText().equals("")
						|| bairro.getText().equals("")
						|| comboBox.getSelectedItem().toString().equals("")
						|| senha.getText().equals("")|| cargo.getText().equals("")//verifica se todos os campos estão preenchidos
						|| tipo.getSelectedItem().toString()
								.equals("Escolha um tipo")) {
					JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco");
				} else {
					if(verificarnumerico(numero.getText())){//verifica se o numero é inteiro
					confirmarcadastrofunc = new ConfirmarCadastroFunc();
					confirmarcadastrofunc.setModal(true);
					confirmarcadastrofunc.setVisible(true);//chama a confirmarcadastrofunc para verificar as informações do úsuario
					if (ConfirmarCadastroFunc.libera == true) {//libera se as informações coincidirem
						
						Funcionario f = new Funcionario();
						f.setNome(nome.getText());
						f.setCelular(celular.getText());
						f.setCPF(cpf.getText());
						f.setSenha(senha.getText());
						f.setTelefone(telefone.getText());
						f.setTipo(tipo.getSelectedItem().toString());
						f.setCargo(cargo.getText());
						f.setRg(rg.getText());//carrega a classe funcionario
						Endereco e1 = new Endereco();
						e1.setBairro(bairro.getText());
						e1.setCEP(cep.getText());
						e1.setCidade(cidade.getText());
						e1.setComplemento(complemento.getText());
						e1.setEstado(comboBox.getSelectedItem().toString());
						e1.setNumero(Integer.parseInt(numero.getText()));
						e1.setRua(endereco.getText());//carrega o endereco
						e1.setTipo("Residencial");
						f.setEndereco(e1);
						
						c.inserir(f);//insere o funcionario no banco de dados
						AcaoRecente a =new AcaoRecente();
						a.setFuncionario(FramePai.usuarioatual);
						a.setAcao("Funcionario "+f.getCPF()+" Adicionado");
						c.inserir(a);
						FramePai.a.add(a);
						Principal.AtualizarAcoes();//insere nas ações recentes

						JOptionPane.showMessageDialog(null,
								"Cadastro realizado com sucesso");
						
						cidade.setText("");
						nome.setText("");
						cpf.setText("");
						rg.setText("");
						telefone.setText("");
						celular.setText("");
						endereco.setText("");
						numero.setText("");
						cep.setText("");
						bairro.setText("");
						comboBox.setSelectedItem(0);
						senha.setText("");
						cargo.setText("");
						tipo.setSelectedItem(0);
					}}
				}
			}
		});

		confirmar.setBounds(469, 487, 103, 27);
		contentPane.add(confirmar);

		lblConfiguracoes = new JLabel("Configurações");
		lblConfiguracoes.setForeground(Color.WHITE);
		lblConfiguracoes.setFont(new Font("Arial", Font.BOLD, 14));
		lblConfiguracoes.setBounds(56, 39, 121, 14);
		contentPane.add(lblConfiguracoes);

		lblAlterarConta = new JLabel("Alterar Conta");
		lblAlterarConta.setForeground(Color.BLACK);
		lblAlterarConta.setFont(new Font("Arial", Font.BOLD, 14));
		lblAlterarConta.setBounds(79, 79, 121, 14);
		contentPane.add(lblAlterarConta);

		lblNovoUsu = new JLabel("Novo Usuário");
		lblNovoUsu.setForeground(Color.WHITE);
		lblNovoUsu.setFont(new Font("Arial", Font.BOLD, 14));
		lblNovoUsu.setBounds(245, 79, 121, 14);
		contentPane.add(lblNovoUsu);

		JLabel lblAcaoAlterar = new JLabel("");
		lblAcaoAlterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				lblAlterarConta.setForeground(new Color(95, 93, 93));
				lblAcaoAlterar.setIcon(new ImageIcon(Principal.class
						.getResource("/FundoMouseAlterarConta.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAlterarConta.setForeground(new Color(0, 0, 0));
				lblAcaoAlterar.setIcon(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				FramePai.AbrirConfiguracaoAlterarConta();
			}
		});
		lblAcaoAlterar.setBounds(43, 73, 165, 26);
		contentPane.add(lblAcaoAlterar);

		JPanel InformacoesPessoais = new JPanel();
		InformacoesPessoais.setBorder(new TitledBorder(null,
				"Informações Pessoais", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		InformacoesPessoais.setBounds(250, 124, 550, 147);
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

		nome = new JTextField("");
		nome.setBounds(89, 31, 411, 18);
		InformacoesPessoais.add(nome);

		cpf = new JTextField("");
		cpf.setBounds(316, 58, 184, 18);
		InformacoesPessoais.add(cpf);

		rg = new JTextField("");
		rg.setBounds(89, 58, 184, 18);
		InformacoesPessoais.add(rg);

		telefone = new JTextField("");
		telefone.setBounds(326, 85, 174, 18);
		InformacoesPessoais.add(telefone);

		celular = new JTextField("");
		celular.setBounds(89, 85, 174, 18);
		InformacoesPessoais.add(celular);
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setBounds(43, 114, 46, 14);
		InformacoesPessoais.add(lblCargo);
		
		cargo = new JTextField();
		cargo.setBounds(89, 114, 411, 20);
		InformacoesPessoais.add(cargo);
		cargo.setColumns(10);

		EnderecoGrupo = new JPanel();
		EnderecoGrupo.setBounds(250, 271, 550, 127);
		contentPane.add(EnderecoGrupo);
		EnderecoGrupo.setBorder(new TitledBorder(null, "Endere\u00E7o",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

		endereco = new JTextField("");
		endereco.setBounds(98, 31, 264, 18);
		EnderecoGrupo.add(endereco);

		numero = new JTextField("");
		numero.setBounds(98, 58, 61, 18);
		EnderecoGrupo.add(numero);

		complemento = new JTextField("");
		complemento.setBounds(258, 58, 126, 18);
		EnderecoGrupo.add(complemento);

		cep = new JTextField("");
		cep.setBounds(403, 31, 97, 18);
		EnderecoGrupo.add(cep);

		bairro = new JTextField("");
		bairro.setBounds(98, 85, 144, 18);
		EnderecoGrupo.add(bairro);

		cidade = new JTextField("");
		cidade.setBounds(298, 85, 202, 18);
		EnderecoGrupo.add(cidade);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "TA", "PB", "PR", "PE", "PI", "RJ", "RM", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		comboBox.setBounds(439, 57, 61, 18);
		EnderecoGrupo.add(comboBox);

		InformacoesConta = new JPanel();
		InformacoesConta.setBorder(new TitledBorder(null,
				"Informações da Conta", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		InformacoesConta.setBounds(250, 397, 550, 79);
		contentPane.add(InformacoesConta);
		InformacoesConta.setLayout(null);

		Tipo = new JLabel("Tipo: ");
		Tipo.setBounds(43, 31, 61, 18);
		InformacoesConta.add(Tipo);

		tipo = new JComboBox();
		tipo.setBounds(98, 31, 140, 20);
		InformacoesConta.add(tipo);

		tipo.setModel(new DefaultComboBoxModel(new String[] {
				"Escolha um tipo", "Administrador", "Usuário" }));

		Senha = new JLabel("Senha: ");
		Senha.setBounds(254, 31, 61, 18);
		InformacoesConta.add(Senha);

		senha = new JTextField("");
		senha.setBounds(294, 31, 205, 18);
		InformacoesConta.add(senha);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CadastroFunc.class
				.getResource("/FundoNovoUsuarioADM.png")));
		lblNewLabel.setBounds(0, 0, 1018, 611);
		contentPane.add(lblNewLabel);
		
	
	}
}
