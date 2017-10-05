package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import bd.Conexao;

import java.awt.Color;

public class Cabecalho extends JFrame
{

	private JPanel contentPane;
	private Conexao conexao = Login.conexao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Cabecalho frame = new Cabecalho();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cabecalho()
	{
		setResizable(false);
		setBounds(100, 100, 1024, 136);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		SimpleDateFormat dformatador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		SimpleDateFormat hformatador = new SimpleDateFormat("hh:mm:ss '- ('zzzz')'");
		
		
		JLabel datasaida = new JLabel();
		datasaida.setForeground(Color.BLACK);
		datasaida.setFont(new Font("Arial", Font.PLAIN, 12));
		datasaida.setBounds(60, 47, 234, 14);
		contentPane.add(datasaida);

		JLabel horasaida = new JLabel();
		horasaida.setForeground(Color.BLACK);
		horasaida.setFont(new Font("Arial", Font.PLAIN, 12));
		horasaida.setBounds(60, 72, 279, 14);
		contentPane.add(horasaida);

		Timer timer = new Timer(500, new ActionListener()//cria timer
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Calendar currentCalendar = Calendar.getInstance();
				Date currentTime = currentCalendar.getTime();
				datasaida.setText(dformatador.format(currentTime));//preenche a hora atual
				horasaida.setText(hformatador.format(currentTime));
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();

		JLabel labeldousuario = new JLabel(FramePai.usuarioatual.getNome() + " (" + FramePai.usuarioatual.getTipo() + ")");
		labeldousuario.setForeground(Color.BLACK);
		labeldousuario.setFont(new Font("Arial", Font.PLAIN, 14));
		labeldousuario.setBounds(90, 19, 249, 14);
		contentPane.add(labeldousuario);

		JLabel botaosair = new JLabel("");
		botaosair.setBounds(0, 53, 45, 49);
		contentPane.add(botaosair);

		
		JLabel acaoConfiguracao = new JLabel();
		acaoConfiguracao.setBounds(0, 0, 45, 49);
		contentPane.add(acaoConfiguracao);
		
		

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
		
		
		
		
		
		//if(FramePai.usuarioatual.getTipo().equals("Administrador"))
		//{

		acaoConfiguracao.addMouseListener(new MouseAdapter()
		{
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{

				acaoConfiguracao.setIcon(new ImageIcon(Principal.class.getResource("/fundoMouseConfiguracao.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(FramePai.resp==true)
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
		
		//}
		/*else
		{
			acaoConfiguracao.setToolTipText("Apenas Administradores podem acessar essa função");
		}*/
		


		
		
		

		JLabel paginainicial = new JLabel("");
		paginainicial.setBounds(973, 0, 45, 104);

		paginainicial.setIcon(new ImageIcon(Principal.class.getResource("/paginainicial.png")));
		contentPane.add(paginainicial);

		paginainicial.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				FramePai.FechaAba();

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				paginainicial.setBounds(973, 0, 45, 104);
				paginainicial.setIcon(new ImageIcon(Principal.class.getResource("/fundoMousePaginaInicial.png")));

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{

				paginainicial.setIcon(new ImageIcon(Principal.class.getResource("/paginainicial.png")));
			}
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Cabecalho.class.getResource("/FundodoCabecalho.png")));
		lblNewLabel.setBounds(0, 0, 1018, 108);
		contentPane.add(lblNewLabel);

	}
}
