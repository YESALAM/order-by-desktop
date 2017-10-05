package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.AcaoRecente;
import model.Funcionario;

import javax.swing.JSeparator;

import bd.Conexao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class FramePai extends JFrame {

	public static boolean resp = true;
	public static boolean controleusuario= false;
	public JPanel contentPane;
    public Principal framePrincipal;
    
    public static FramePai frame;
    
    public static Principal principal;
	public static Cabecalho frameCabecalho;
	public static HA frameha;
	public static CadastroFunc frameConfiguracaoNovaConta;
	public static AlterarFunc frameConfiguracaoAlterarConta;
	public static CadastroHorario frameCadastroHorario;
	public static CadastroProf frameCadastroProf;
	public static CadastroAluno frameCadastroAluno;
	public static CadastroMateria frameCadastroMateria;
	public static CadastroSala frameCadastroSala;
	public static CadastroNoticia frameCadastroNoticia;
	public static Conexao conexao = Login.conexao;
	public static List<AcaoRecente> a;//lista que sera consultada em outras forms para manter as ações recentes funcionando
	
	
	//Objetos
	public static JInternalFrame internalFrameCabecalho;
	public static JInternalFrame internalFrameInfo;
	public static JInternalFrame internalFramePrincipal;
	public static JInternalFrame internalFrameConfiguracao;
	
	public static Funcionario usuarioatual;
	
	public static void Confirma(JFrame f){
		JOptionPane.showMessageDialog(f, "Ação concluída");
	}
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			frame = new FramePai();
			frame.setVisible(true); 
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public static void Controle(JComponent x)
	{
		if(usuarioatual.getTipo().equals("Usuário"))
		{
		
			x.setEnabled(false);
			controleusuario= false;
		
		}
		else
		{
			x.setEnabled(true);
			controleusuario= true;
		}
	}
	
	public static void Controle(JMenuItem x)
	{
		if(usuarioatual.getTipo().equals("Usuário"))
		{
		
			x.setEnabled(false);
			controleusuario= false;
		
		}
		else
		{
			x.setEnabled(true);
			controleusuario= true;
		}
	}
	public FramePai() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Order By");
		
		setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		a=conexao.buscarAcao_Recente();//Lista de ações recente
		
		
		
		
		framePrincipal = new Principal();
		internalFramePrincipal = new JInternalFrame();
		internalFramePrincipal.setContentPane(framePrincipal.getContentPane());
		internalFramePrincipal.pack();
		internalFramePrincipal.setBounds(framePrincipal.getBounds());
		internalFramePrincipal.setBounds(0,-26,1024, 794);
		getContentPane().add(internalFramePrincipal);
		internalFramePrincipal.setBorder(null);
		internalFramePrincipal.getContentPane().setLayout(null);
		BasicInternalFrameTitlePane titlePane = (BasicInternalFrameTitlePane) ((BasicInternalFrameUI) internalFramePrincipal.getUI()).getNorthPane(); 
		internalFramePrincipal.remove(titlePane);
		internalFramePrincipal.setVisible(true);
		
		frameCabecalho = new Cabecalho();
		internalFrameCabecalho = new JInternalFrame();
		internalFrameCabecalho.pack();
		internalFrameCabecalho.setBounds(frameCabecalho.getBounds());
		internalFrameCabecalho.setBounds(0,-26,1024, 134);
		getContentPane().add(internalFrameCabecalho);
		internalFrameCabecalho.setBorder(null);
		BasicInternalFrameTitlePane titlePanecabecalho = (BasicInternalFrameTitlePane) ((BasicInternalFrameUI) internalFrameCabecalho.getUI()).getNorthPane(); 
		internalFrameCabecalho.remove(titlePanecabecalho);
		internalFrameCabecalho.setVisible(false);
		
		internalFrameInfo = new JInternalFrame();
		internalFrameInfo.pack();
		internalFrameInfo.setBounds(0,82,1024, 660);
		getContentPane().add(internalFrameInfo);
		internalFrameInfo.setBorder(null);
		BasicInternalFrameTitlePane titlePanefilho = (BasicInternalFrameTitlePane) ((BasicInternalFrameUI) internalFrameInfo.getUI()).getNorthPane(); 
		internalFrameInfo.remove(titlePanefilho);
		internalFrameInfo.setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPorpina = new JMenu("Navegar");
		menuBar.add(mnPorpina);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Gerenciamento");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				fecharPrincipal();
				internalFrameInfo.dispose();
				frameCadastroHorario = new CadastroHorario();
				internalFrameInfo.setContentPane(frameCadastroHorario.getContentPane());
				internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
				internalFrameCabecalho.setVisible(true);
				internalFrameInfo.setVisible(true);
			}
		});

		mnPorpina.add(mntmNewMenuItem);
		
		
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Publicar Noticias");
		Controle(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AbrirPublicar();
				
			}
		});
		
		mnPorpina.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Horario Automatico");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AbrirHorario();
			}
		});
		mnPorpina.add(mntmNewMenuItem_2);
		
		JSeparator separator = new JSeparator();
		mnPorpina.add(separator);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Configura\u00E7\u00F5es");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AbrirConfiguracaoAlterarConta();
			}
		});
		Controle(mntmNewMenuItem_3);
		mnPorpina.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Sair");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja fechar o software?", "Sair", JOptionPane.YES_NO_OPTION);
				if (opcao == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});
		mnPorpina.add(mntmNewMenuItem_5);		
		
		
		JMenu mnPerdigo = new JMenu("Ajuda");
		menuBar.add(mnPerdigo);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Manual");
		mnPerdigo.add(mntmNewMenuItem_7);
		
		JSeparator separator_1 = new JSeparator();
		mnPerdigo.add(separator_1);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Sobre");
		mnPerdigo.add(mntmNewMenuItem_6);
	}
	
	public static void fecharPrincipal()
	{
		internalFramePrincipal.dispose();
		
		

	}
	
	public static void AbrirHorario()
	{
		fecharPrincipal();
		internalFrameInfo.dispose();
		frameha = new HA();
		internalFrameInfo.setContentPane(frameha.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);

		
	}
	
	public static void AbrirPublicar()
	{
		fecharPrincipal();
		internalFrameInfo.dispose();
		frameCadastroNoticia= new CadastroNoticia();
		internalFrameInfo.setContentPane(frameCadastroNoticia.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);

		
	}
	
	public static void FechaAba()
	{
		internalFrameCabecalho.dispose();
		internalFrameInfo.dispose();
		internalFramePrincipal.setVisible(true);
		resp=true;
	}
	public static void AbrirConfiguracaoNovaConta()
	{
		internalFrameInfo.dispose();
		frameConfiguracaoNovaConta = new CadastroFunc();
		internalFrameInfo.setContentPane(frameConfiguracaoNovaConta.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		resp=false;
		
	}
	public static void AbrirConfiguracaoAlterarConta()
	{
		fecharPrincipal();
		internalFrameInfo.dispose();
		frameConfiguracaoAlterarConta = new AlterarFunc();
		internalFrameInfo.setContentPane(frameConfiguracaoAlterarConta.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		resp=false;
	}
	public static void AbrirDecidirCadastro()
	{
		fecharPrincipal();
		internalFrameInfo.dispose();
		frameCadastroHorario = new CadastroHorario();
		internalFrameInfo.setContentPane(frameCadastroHorario.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
	
	
	
	public static void AbrirCadastroAluno()
	{
		
		internalFrameInfo.dispose();
		frameCadastroAluno = new CadastroAluno();
		internalFrameInfo.setContentPane(frameCadastroAluno.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
	
	public static void AbrirCadastroProfessor()
	{
		
		internalFrameInfo.dispose();
		frameCadastroProf = new CadastroProf();
		internalFrameInfo.setContentPane(frameCadastroProf.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
	
	public static void AbrirCadastroMateria()
	{
		
		internalFrameInfo.dispose();
		frameCadastroMateria = new CadastroMateria();
		internalFrameInfo.setContentPane(frameCadastroMateria.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
	
	public static void AbrirCadastroSala()
	{
		
		internalFrameInfo.dispose();
		frameCadastroSala = new CadastroSala();
		internalFrameInfo.setContentPane(frameCadastroSala.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
	public static void AbrirCadastroHorario()
	{
		
		internalFrameInfo.dispose();
		frameCadastroHorario = new CadastroHorario();
		internalFrameInfo.setContentPane(frameCadastroHorario.getContentPane());
		internalFrameCabecalho.setContentPane(frameCabecalho.getContentPane());
		internalFrameCabecalho.setVisible(true);
		internalFrameInfo.setVisible(true);
		
	}
}
