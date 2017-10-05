package Formularios;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Principal extends JFrame
{

	private JPanel contentPane;

	// --------------------------------------------------------------------------------
	// Classes
	public static Principal principal;
	public static Cabecalho frame;
	public static HA framefilho;

	// Objetos
	public JLabel labeldousuario;
	public JLabel botaosair;
	public JLabel tituloCalendario;
	public JLabel descricaoCalendario;
	public JLabel tituloPublica;
	public JLabel descricaoPublicar;
	public JLabel tituloHorario;
	public JLabel descricaoHorario;
	public JLabel acaoHorario;
	public JLabel acoesRecentes;
	public JLabel Programa;
	public JLabel lblPaginaInicial;
	public JLabel fundo;
	public JLabel datasaida;
	public JLabel horasaida;
	public JLabel acaoPublicar;
	
	public static JLabel acao1, acao2, acao3, acao4, acao5, usures1, usures2,usures3,usures4,usures5;

	// --------------------------------------------------------------------------------
	public static void AtualizarAcoes(){//preenche os campos com as ultimas 5 ações registradas
		if(FramePai.a.size()>=1){
			Principal.acao1.setText(FramePai.a.get(FramePai.a.size()-1).getAcao()); 
			}
		if(FramePai.a.size()>=2){
			Principal.acao2.setText(FramePai.a.get(FramePai.a.size()-2).getAcao()); 
			}
		if(FramePai.a.size()>=3){
			Principal.acao3.setText(FramePai.a.get(FramePai.a.size()-3).getAcao());
			}

		if(FramePai.a.size()>=4){
			Principal.acao4.setText(FramePai.a.get(FramePai.a.size()-4).getAcao());
			}
		if(FramePai.a.size()>=5){
			Principal.acao5.setText(FramePai.a.get(FramePai.a.size()-5).getAcao()); 
			}
		if(FramePai.a.size()>=1){
			
			Principal.usures1.setText(FramePai.a.get(FramePai.a.size()-1).getFuncionario().getNome());
			} 
		if(FramePai.a.size()>=2){
			
			Principal.usures2.setText(FramePai.a.get(FramePai.a.size()-2).getFuncionario().getNome());
			} 
		if(FramePai.a.size()>=3){
			
			Principal.usures3.setText(FramePai.a.get(FramePai.a.size()-3).getFuncionario().getNome());
			} 
		if(FramePai.a.size()>=4){
			
			Principal.usures4.setText(FramePai.a.get(FramePai.a.size()-4).getFuncionario().getNome());
			} 
		if(FramePai.a.size()>=5){
			
			Principal.usures5.setText(FramePai.a.get(FramePai.a.size()-5).getFuncionario().getNome());
			} 	
	}
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			principal = new Principal();
			principal.setVisible(true);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Principal()
	{
		setTitle("Order By");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 747);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --------------------------------------------------------------------------------

		JLabel labeldousuario = new JLabel(FramePai.usuarioatual.getNome().toString() + " (" + FramePai.usuarioatual.getTipo().toString() + ")");
		labeldousuario.setFont(new Font("Arial", Font.PLAIN, 14));
		labeldousuario.setBounds(90, 19, 249, 14);
		contentPane.add(labeldousuario);

		JLabel botaosair = new JLabel("");
		botaosair.setBounds(0, 53, 45, 49);
		contentPane.add(botaosair);

		JLabel acaoConfiguracao = new JLabel();
		acaoConfiguracao.setBounds(0, 0, 45, 49);
		contentPane.add(acaoConfiguracao);
		FramePai.Controle(acaoConfiguracao);

		// --------------------------------------------------------------------------------
		// DATA E HORA - fazer metodo depois

		SimpleDateFormat dformatador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		SimpleDateFormat hformatador = new SimpleDateFormat("hh:mm:ss '- ('zzzz')'");

		JLabel datasaida = new JLabel();
		datasaida.setFont(new Font("Arial", Font.PLAIN, 12));
		datasaida.setBounds(60, 47, 234, 14);
		contentPane.add(datasaida);

		JLabel horasaida = new JLabel();
		horasaida.setFont(new Font("Arial", Font.PLAIN, 12));
		horasaida.setBounds(60, 72, 279, 14);
		contentPane.add(horasaida);

		Timer timer = new Timer(500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Calendar currentCalendar = Calendar.getInstance();
				Date currentTime = currentCalendar.getTime();
				datasaida.setText(dformatador.format(currentTime));
				horasaida.setText(hformatador.format(currentTime));
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();

		// --------------------------------------------------------------------------------


		// -------------------------------------------------------------------------------
		//Decidir - icone

		JLabel tituloDecidir = new JLabel("Gerenciar");
		tituloDecidir.setFont(new Font("Arial", Font.PLAIN, 15));
		tituloDecidir.setBounds(148, 249, 146, 14);
		contentPane.add(tituloDecidir);

		JLabel descricaoDecidir = new JLabel("<html>Gerencie o cadastro de professores, alunos e matérias.</html>");
		descricaoDecidir.setBounds(148, 274, 479, 14);
		contentPane.add(descricaoDecidir);
		
		JLabel acaoDecidir = new JLabel("");
		acaoDecidir.setBounds(55, 218, 595, 113);
		contentPane.add(acaoDecidir);

		// --------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------
		// Publicar - icone

		JLabel tituloPublica = new JLabel("Publicar Noticias");
		tituloPublica.setFont(new Font("Arial", Font.PLAIN, 15));
		tituloPublica.setBounds(148, 377, 125, 14);
		contentPane.add(tituloPublica);

		JLabel descricaoPublicar = new JLabel("<html>Acrescente not\u00EDcias diretamente no site facilmente e com diversos recursos de edi\u00E7\u00E3o<br>e seguran\u00E7a.</html>");
		descricaoPublicar.setBounds(148, 402, 479, 28);
		contentPane.add(descricaoPublicar);
		
		JLabel acaoPublicar = new JLabel("");
		acaoPublicar.setBounds(55, 348, 595, 113);
		contentPane.add(acaoPublicar);

		// --------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------
		// Horario Automatico - icone
		JLabel tituloHorario = new JLabel("Hor\u00E1rio Autom\u00E1tico");
		tituloHorario.setFont(new Font("Arial", Font.PLAIN, 15));
		tituloHorario.setBounds(148, 505, 146, 14);
		contentPane.add(tituloHorario);

		JLabel descricaoHorario = new JLabel("<html>Fa\u00E7a hor\u00E1rios escolares f\u00E1cil e rapidamente com grande efic\u00E1cia atr\u00E1ves desse aplicativo<br>que possibilita v\u00E1rios recursos de customiza\u00E7\u00E3o.</html>");
		descricaoHorario.setBounds(148, 530, 479, 28);
		contentPane.add(descricaoHorario);

		JLabel acaoHorario = new JLabel("");
		acaoHorario.setBounds(55, 478, 595, 113);
		contentPane.add(acaoHorario);

		// --------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------
		// Ações Recentes

		JLabel acoesRecentes = new JLabel("A\u00E7\u00F5es Recentes");
		acoesRecentes.setForeground(Color.WHITE);
		acoesRecentes.setFont(new Font("Arial", Font.PLAIN, 16));
		acoesRecentes.setBounds(875, 75, 119, 19);
		contentPane.add(acoesRecentes);

		JLabel Programa = new JLabel("Order By");
		Programa.setFont(new Font("Arial", Font.BOLD, 24));
		Programa.setForeground(Color.WHITE);
		Programa.setBounds(744, 11, 249, 39);
		contentPane.add(Programa);

		// --------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------
		// Design do fundo
		JLabel lblPaginaInicial = new JLabel("P\u00E1gina Inicial");
		lblPaginaInicial.setForeground(Color.WHITE);
		lblPaginaInicial.setFont(new Font("Arial", Font.BOLD, 14));
		lblPaginaInicial.setBounds(47, 171, 106, 14);
		contentPane.add(lblPaginaInicial);


		//Lista de ações recentes
		
			 

			
			
			
		
		
		        acao1 = new JLabel("");
				acao1.setHorizontalAlignment(SwingConstants.RIGHT);
				acao1.setForeground(Color.WHITE);
				acao1.setFont(new Font("Arial", Font.PLAIN, 14));
				acao1.setBounds(755, 105, 234, 14);
				contentPane.add(acao1);
				
				acao2 = new JLabel("");
				acao2.setHorizontalAlignment(SwingConstants.RIGHT);
				acao2.setForeground(Color.WHITE);
				acao2.setFont(new Font("Arial", Font.PLAIN, 14));
				acao2.setBounds(755, 140, 234, 14);
				contentPane.add(acao2);
				
				
				acao3 = new JLabel("");
				acao3.setHorizontalAlignment(SwingConstants.RIGHT);
				acao3.setForeground(Color.WHITE);
				acao3.setFont(new Font("Arial", Font.PLAIN, 14));
				acao3.setBounds(755, 175, 234, 14);
				contentPane.add(acao3);
				
				
				acao4 = new JLabel("");
				acao4.setHorizontalAlignment(SwingConstants.RIGHT);
				acao4.setForeground(Color.WHITE);
				acao4.setFont(new Font("Arial", Font.PLAIN, 14));
				acao4.setBounds(755, 210, 234, 14);
				contentPane.add(acao4);
				
				
				acao5 = new JLabel("");
				acao5.setHorizontalAlignment(SwingConstants.RIGHT);
				acao5.setForeground(Color.WHITE);
				acao5.setFont(new Font("Arial", Font.PLAIN, 14));
				acao5.setBounds(755, 245, 234, 14);
				
				contentPane.add(acao5);
				
				usures1 = new JLabel("");
				usures1.setHorizontalAlignment(SwingConstants.RIGHT);
				usures1.setForeground(Color.WHITE);
				usures1.setFont(new Font("Arial", Font.PLAIN, 11));
				usures1.setBounds(755, 117, 234, 14);
				contentPane.add(usures1);
				
				usures2 = new JLabel("");
				usures2.setHorizontalAlignment(SwingConstants.RIGHT);
				usures2.setForeground(Color.WHITE);
				usures2.setFont(new Font("Arial", Font.PLAIN, 11));
				usures2.setBounds(755, 152, 234, 14);
				contentPane.add(usures2);
				
				usures3 = new JLabel("");
				usures3.setHorizontalAlignment(SwingConstants.RIGHT);
				usures3.setForeground(Color.WHITE);
				usures3.setFont(new Font("Arial", Font.PLAIN, 11));
				usures3.setBounds(755, 187, 234, 14);
				contentPane.add(usures3);
				
				usures4 = new JLabel("");
				usures4.setHorizontalAlignment(SwingConstants.RIGHT);
				usures4.setForeground(Color.WHITE);
				usures4.setFont(new Font("Arial", Font.PLAIN, 11));
				usures4.setBounds(755, 222, 234, 14);
				contentPane.add(usures4);
				
				usures5 = new JLabel("");
				usures5.setHorizontalAlignment(SwingConstants.RIGHT);
				usures5.setForeground(Color.WHITE);
				usures5.setFont(new Font("Arial", Font.PLAIN, 11));
				usures5.setBounds(755, 257, 234, 14); 
				contentPane.add(usures5);

				AtualizarAcoes();

				// --------------------------------------------------------------------------------
				
				
		JLabel fundo = new JLabel("");
		fundo.setBounds(0, 0, 1018, 719);
		fundo.setIcon(new ImageIcon(Principal.class.getResource("/fundo.png")));
		contentPane.add(fundo);

		// --------------------------------------------------------------------------------


		// --------------------------------------------------------------------------------

		// Animação - Saida , Ação - Saida

		botaosair.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja fechar o software?", "Sair", JOptionPane.YES_NO_OPTION);
				if (opcao == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{

				botaosair.setIcon(new ImageIcon(Principal.class.getResource("/fundoMouseSair.png")));
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{

				botaosair.setIcon(null);
			}

		});

		// --------------------------------------------------------------------------------
		// Animação - Horario

		acaoHorario.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{

				FramePai.AbrirHorario();
				FramePai.fecharPrincipal();

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{

				acaoHorario.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseHorario2.png")));
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{

				acaoHorario.setIcon(null);
			}

		});

		// --------------------------------------------------------------------------------
		// Animação - Configuração
		
				acaoConfiguracao.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent arg0)
			{

				acaoConfiguracao.setIcon(new ImageIcon(Principal.class.getResource("/fundoMouseConfiguracao.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(FramePai.controleusuario==true)
				{
				FramePai.AbrirConfiguracaoAlterarConta();
				}
			}
		});

		acaoConfiguracao.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent arg0)
			{

				acaoConfiguracao.setIcon(null);
			}
		});
		
		// --------------------------------------------------------------------------------
		// Animação - Decidir
		acaoDecidir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				acaoDecidir.setIcon(new ImageIcon(Principal.class.getResource("/FundoMouseDecidir.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0)
			{

				acaoDecidir.setIcon(null);
			}
			@Override
			public void mouseClicked(MouseEvent arg0)
			{

				FramePai.AbrirDecidirCadastro();
			}
		});

		// --------------------------------------------------------------------------------
				// Animação - Publicar
				
						acaoPublicar.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseEntered(MouseEvent arg0)
					{

						acaoPublicar.setIcon(new ImageIcon(Principal.class.getResource("/FundoMousePublicar.png")));
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						if(FramePai.controleusuario==true)
						{
						FramePai.AbrirPublicar();
						}
					}
				});

				acaoPublicar.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseExited(MouseEvent arg0)
					{

						acaoPublicar.setIcon(null);
					}
				});
				
				// --------------------------------------------------------------------------------
	}
}
