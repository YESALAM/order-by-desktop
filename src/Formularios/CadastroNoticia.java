package Formularios;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import net.atlanticbb.tantlinger.shef.HTMLEditorPane;
import bd.Conexao;
import bd.NoticiasBD;
import model.AcaoRecente;
import model.Funcionario;
import model.Horario;
import model.Noticia;
import model.Tag;
import model.Turma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.BorderFactory;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class CadastroNoticia extends JFrame {
	private JPanel contentPane;
	public JFormattedTextField intervalo[]= new JFormattedTextField[5];
	public JLabel intervalolabel[]= new JLabel[5];
	public JFormattedTextField intervalofinal[]= new JFormattedTextField[5];
	public JLabel intervalolabelfinal[]= new JLabel[5];
	int numintervalo=1;
	int batata=155;
	int batata2=60;
	int hora;
	int minuto;
	public static CadastroNoticia frame;
	private NoticiasBD conexao = Login.conexaonoticia;
	private List<Turma> lista_turma;
	private List<Turma> lista_grau;
	private List<Turma> lista_turmaexiste;
	private String grau_selecionado;
	private static String classe_selecionada;
	private static String curso_selecionado;
	private JButton alterar;
	
	private HTMLEditorPane conteudo;
	private JLabel fotonoticia;
	private Image imgtemp;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList list;
	private JCheckBox chckbxDestaque;
	private JComboBox comboBoxnoticias;
	private JButton cadastrar;
	private JButton excluir;
	private Conexao conexao2=Login.conexao;
	
	private List<Noticia> noticias = new ArrayList<Noticia>();
	
	
	public int intervaloci_h[]= new int[5];
	public int intervaloci_m[]= new int[5];

    public int intervalocf_h[]= new int[5];
	public int intervalocf_m[]= new int[5];
	
	int y=0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					frame = new CadastroNoticia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	int x=0;
	private JTextField txtTitulo;
	private JTextField txtTag;
	
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
	
	
	public CadastroNoticia() {
		
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		noticias = conexao.ListarNoticias();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(518, 313, 171, 180);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 146, 151, 23);
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton button_1 = new JButton("Novo");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtTag.getText().equals(""))
				{
					model.addElement(txtTag.getText());
					txtTag.setText("");
					txtTag.requestFocus();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "O campo tag não foi preenchido!","Erro",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_2.add(button_1);
		
		JButton button_3 = new JButton("Remover");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex()!=-1)
				{
					model.remove(list.getSelectedIndex());
					txtTag.requestFocus();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Selecione uma tag para remover!","Erro",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_2.add(button_3);
		
		txtTag = new JTextField();
		txtTag.setColumns(10);
		txtTag.setBounds(10, 120, 151, 20);
		panel_1.add(txtTag);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 15, 151, 94);
		panel_1.add(scrollPane);
		
		list = new JList();
		list.setModel(model);
		scrollPane.setViewportView(list);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Foto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(518, 106, 171, 196);
		contentPane.add(panel);
		
		fotonoticia = new JLabel("");
		fotonoticia.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		fotonoticia.setBounds(10, 15, 151, 144);
		panel.add(fotonoticia);
		
		JButton btnEscolher = new JButton("Escolher...");
		btnEscolher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnEscolher.getText().equals("Escolher..."))
				{
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes()));
					if (jFileChooser.showOpenDialog(CadastroNoticia.this) == JFileChooser.APPROVE_OPTION)
					{
						ImageIcon ii = new ImageIcon(jFileChooser.getSelectedFile().getPath());
						Image im = ii.getImage();
						Image myImg = im.getScaledInstance(fotonoticia.getWidth(), fotonoticia.getHeight(), Image.SCALE_SMOOTH);

						fotonoticia.setIcon(new ImageIcon(myImg));
						imgtemp = im;
						btnEscolher.setText("Remover");
					}
				}
				else
				{
					fotonoticia.setIcon(null);
					imgtemp=null;
					btnEscolher.setText("Escolher...");
				}
			}
		});
		btnEscolher.setBounds(10, 162, 151, 23);
		panel.add(btnEscolher);
		
		conteudo = new HTMLEditorPane();
		conteudo.setBounds(56, 134, 452, 359);
		contentPane.add(conteudo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(91, 106, 417, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo:");
		lblTtulo.setBounds(56, 109, 46, 14);
		contentPane.add(lblTtulo);
		
		
		//---------------
		
		JLabel lblGerenciamento = new JLabel("Publicar Not\u00EDcia");
		lblGerenciamento.setForeground(Color.WHITE);
		lblGerenciamento.setFont(new Font("Arial", Font.BOLD, 14));
		lblGerenciamento.setBounds(56, 39, 121, 14);
		contentPane.add(lblGerenciamento);
		
		//---------------
		
						

						cadastrar = new JButton("Cadastrar");
						cadastrar.setOpaque(false);
						cadastrar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(!txtTitulo.getText().equals("") && !conteudo.getText().equals("") && !conteudo.getText().equals("<p></p>") && model.getSize()>=1)
								{
									Noticia n = new Noticia();
									n.setTitulo(txtTitulo.getText());
									n.setConteudo(conteudo.getText());
									n.setDestaque(chckbxDestaque.isSelected());//monta a noticia
									if(imgtemp!=null)
										n.setImagem(imgtemp);
									List<Tag> tags = new ArrayList<Tag>();
									for(int i=0;i<model.getSize();i++)
									{
										Tag t = new Tag();
										t.setTag(model.get(i));
										
										tags.add(t);
									}
									n.setTags(tags);//adiciona as tags e titulos
									n.setTipo("Notícia");
									
									n.setCriador(FramePai.usuarioatual);
									
									conexao.NovaNoticia(n);//insere a noticia
									AcaoRecente a =new AcaoRecente();
									a.setFuncionario(FramePai.usuarioatual);
									a.setAcao("Noticia "+n.getTitulo()+" Adicionada");
									conexao2.inserir(a);
									FramePai.a.add(a);
									Principal.AtualizarAcoes();//insere nas ações recentes
									FramePai.Confirma(CadastroNoticia.this);
									
									txtTitulo.setText("");
									conteudo.setText("");
									fotonoticia.setIcon(null);
									imgtemp=null;
									chckbxDestaque.setSelected(false);
									model.clear();
									
									noticias = conexao.ListarNoticias();
									((DefaultComboBoxModel) comboBoxnoticias.getModel()).removeAllElements();
									((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement("Selecione uma Notícia...");
									for(Noticia n1 : noticias)
										((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement(n1.getTitulo());
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco.","Erro",JOptionPane.ERROR_MESSAGE);
								}
								
							}
						});
						cadastrar.setBounds(780, 332, 114, 23);
						contentPane.add(cadastrar);
		
		excluir = new JButton("Excluir");
		excluir.setOpaque(false);
		excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Noticia n = noticias.get(comboBoxnoticias.getSelectedIndex()-1);
				if(JOptionPane.showConfirmDialog(CadastroNoticia.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
				{
					conexao.DeletarNoticia(n);//apaga a noticia
					AcaoRecente a =new AcaoRecente();
					a.setFuncionario(FramePai.usuarioatual);
					a.setAcao("Noticia "+n.getTitulo()+" Excluida");
					conexao2.inserir(a);
					FramePai.a.add(a);
					Principal.AtualizarAcoes();//insere nas ações recentes
					
					txtTitulo.setText("");
					conteudo.setText("");
					fotonoticia.setIcon(null);
					imgtemp=null;
					chckbxDestaque.setSelected(false);
					model.clear();
					
					cadastrar.setEnabled(true);
					alterar.setEnabled(false);
					excluir.setEnabled(false);
					FramePai.Confirma(CadastroNoticia.this);
					
					noticias = conexao.ListarNoticias();
					((DefaultComboBoxModel) comboBoxnoticias.getModel()).removeAllElements();
					((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement("Selecione uma Notícia...");
					for(Noticia n1 : noticias)
						((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement(n1.getTitulo());
				}
			}
		});
		excluir.setEnabled(false);
		excluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		excluir.setBounds(780, 398, 114, 23);
		contentPane.add(excluir);
		
		JLabel lblNoticias = new JLabel("Not\u00EDcias:");
		lblNoticias.setForeground(Color.WHITE);
		lblNoticias.setBounds(728, 174, 46, 18);
		contentPane.add(lblNoticias);
		
		comboBoxnoticias = new JComboBox();
		comboBoxnoticias.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma Notícia..."}));
		comboBoxnoticias.setBounds(772, 175, 142, 18);
		for(Noticia n: noticias)
			comboBoxnoticias.addItem(n.getTitulo());
		contentPane.add(comboBoxnoticias);
		
		comboBoxnoticias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		JLabel lblinsiraUmaTurma = new JLabel("<html>Insira uma not\u00EDcia j\u00E1 cadastrada para visualizar e/ou alterar o registro da mesma.</html>");
		lblinsiraUmaTurma.setForeground(Color.WHITE);
		lblinsiraUmaTurma.setBounds(728, 246, 226, 59);
		contentPane.add(lblinsiraUmaTurma);
		
		alterar = new JButton("Alterar");
		alterar.setOpaque(false);
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtTitulo.getText().equals("") && !conteudo.getText().equals("") && !conteudo.getText().equals("<p></p>") && model.getSize()>=1)
				{
					Noticia n = noticias.get(comboBoxnoticias.getSelectedIndex()-1);
					n.setTitulo(txtTitulo.getText());
					n.setConteudo(conteudo.getText());
					n.setDestaque(chckbxDestaque.isSelected());
					if(imgtemp!=null)
						n.setImagem(imgtemp);
					n.setImagem(n.getImagem());
					List<Tag> tags = new ArrayList<Tag>();
					for(int i=0;i<model.getSize();i++)
					{
						Tag t = new Tag();
						t.setTag(model.get(i));
						
						tags.add(t);
					}
					n.setTags(tags);
					n.setTipo("Notícia");
					
					conexao.EditarNoticia(n);//altera a noticia existente
					AcaoRecente a =new AcaoRecente();
					a.setFuncionario(FramePai.usuarioatual);
					a.setAcao("Noticia "+n.getTitulo()+" Alterada");
					conexao2.inserir(a);
					FramePai.a.add(a);
					Principal.AtualizarAcoes();//insere nas ações recentes
					
					model.clear();
					txtTitulo.setText("");
					conteudo.setText("");
					fotonoticia.setIcon(null);
					imgtemp=null;
					chckbxDestaque.setSelected(false);
					model.clear();
					
					cadastrar.setEnabled(true);
					alterar.setEnabled(false);
					excluir.setEnabled(false);
					FramePai.Confirma(CadastroNoticia.this);
					
					noticias = conexao.ListarNoticias();
					((DefaultComboBoxModel) comboBoxnoticias.getModel()).removeAllElements();
					((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement("Selecione uma Notícia...");
					for(Noticia n1 : noticias)
						((DefaultComboBoxModel) comboBoxnoticias.getModel()).addElement(n1.getTitulo());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Existe(m) campo(s) em branco.","Erro",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		alterar.setEnabled(false);
		alterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		alterar.setBounds(780, 364, 114, 23);
		contentPane.add(alterar);
		
		chckbxDestaque = new JCheckBox("Destaque");
		chckbxDestaque.setBounds(56, 500, 97, 23);
		contentPane.add(chckbxDestaque);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(comboBoxnoticias.getSelectedIndex()!=0)
				{
					cadastrar.setEnabled(false);
					alterar.setEnabled(true);
					excluir.setEnabled(true);
					
					Noticia n = noticias.get(comboBoxnoticias.getSelectedIndex()-1);
					txtTitulo.setText(n.getTitulo());
					conteudo.setText(n.getConteudo());
					model.clear();
					for(Tag t : n.getTags())
						model.addElement(t.getTag());
					chckbxDestaque.setSelected(n.isDestaque());
					fotonoticia.setIcon(null);
					
					btnEscolher.setText("Escolher...");
					if(n.getImagem()!=null)
					{
						Image myImg = n.getImagem().getScaledInstance(fotonoticia.getWidth(), fotonoticia.getHeight(), Image.SCALE_SMOOTH);
	
						fotonoticia.setIcon(new ImageIcon(myImg));
						btnEscolher.setText("Remover...");
					}
					
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
		lblNewLabel_3.setBounds(924, 167, 32, 32);
		contentPane.add(lblNewLabel_3);
		
		JButton btnPreencher = new JButton("Preencher");
		btnPreencher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtTitulo.setText("TCC de Informática");
				model.addElement("tcc");
				model.addElement("informática");
				model.addElement("etim");
				model.addElement("etec");
				conteudo.setText("<p><b><font color=\"#ff0000\">TCC de Inform&#225;tica - Dia 27/11 11h</font></b></p><p>Venha ver os Trabalhos de Conclus&#227;o de Curso do 3E ETIM.</p>");
				
				ImageIcon ii = new ImageIcon(new File("C:/noticia.jpg").getPath());
				Image im = ii.getImage();
				Image myImg = im.getScaledInstance(fotonoticia.getWidth(), fotonoticia.getHeight(), Image.SCALE_SMOOTH);

				fotonoticia.setIcon(new ImageIcon(myImg));
				imgtemp = im;
				btnEscolher.setText("Remover");
			}
		});
		btnPreencher.setBounds(57, 536, 89, 23);
		contentPane.add(btnPreencher);
		
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroNoticia.class.getResource("/FundoInformacao2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
		
	}
}
