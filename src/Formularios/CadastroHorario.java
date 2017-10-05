package Formularios;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;

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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import bd.Conexao;
import model.AcaoRecente;
import model.Aluno;
import model.Horario;
import model.Turma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.sql.Time;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroHorario extends JFrame {
	public JLabel Numero;
	public JFormattedTextField hi;
	public JLabel Complemento;
	public JLabel CEP;
	public JTextField ad;
	public JLabel Estado;
	public JFormattedTextField ta;
	private JPanel EnderecoGrupo;
	private static JPanel Intervalo;
	private JPanel contentPane;
	private JButton btnRemoverIntervalo;
	public JFormattedTextField intervalo[]= new JFormattedTextField[5];
	public JLabel intervalolabel[]= new JLabel[5];
	public JFormattedTextField intervalofinal[]= new JFormattedTextField[5];
	public JLabel intervalolabelfinal[]= new JLabel[5];
	private JButton btnAdicionarHorrio;
	int numintervalo=1;
	int batata=155;
	int batata2=60;
	int hora;
	int minuto;
	public static CadastroHorario frame;
	private Conexao conexao = Login.conexao;
	private List<Turma> lista_turma;
	private List<Turma> lista_grau;
	private List<Turma> lista_turmaexiste;
	private String grau_selecionado;
	private static String classe_selecionada;
	private static String curso_selecionado;
	private JComboBox grau;
	private JComboBox classe;
	private JComboBox curso;
	private JComboBox periodo;
	private JButton alterar;
	
	
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
					frame = new CadastroHorario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	int x=0;
	private JFormattedTextField primhorariofinal;
	private JFormattedTextField primhorarioini;
	
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
	
	public void CriarNovoBotao()//faz a criação de novos campos de intervalo
	{
		if(numintervalo!=1 )
		{
			btnRemoverIntervalo.setEnabled(true);//se não houver somente um intervalo ele libera o botão "-" 
			
		}
		
		
		
		if(numintervalo!=4)
		{
			numintervalo++;
		
			
			
//se o numero de intervalos não estiver no máximo ele vai adicionando novos intervalos adaptando as devidas alturas e larguras dos componentes
				intervalo[numintervalo]=new JFormattedTextField(Mascara("##:##"));
				intervalolabel[numintervalo]= new JLabel("Início "+numintervalo+": ");
				intervalofinal[numintervalo]=new JFormattedTextField(Mascara("##:##"));
				intervalolabelfinal[numintervalo]= new JLabel("Final "+numintervalo+": ");
				
				intervalolabel[numintervalo].setBounds(batata, 31, 78, 18);
				batata=batata+40;
				intervalo[numintervalo].setBounds(batata, 31, 62, 18);
				
				batata=batata-40;
				
				intervalolabelfinal[numintervalo].setBounds(batata, batata2, 78, 18);
				batata=batata+40;
				intervalofinal[numintervalo].setBounds(batata, batata2, 62, 18);
				
				
				batata=batata+80;
				
				frame.Intervalo.add(intervalo[numintervalo]);
				frame.Intervalo.add(intervalolabel[numintervalo]);
				frame.Intervalo.add(intervalofinal[numintervalo]);
				frame.Intervalo.add(intervalolabelfinal[numintervalo]);//adiciona os novos intervalos ao frame
				
				intervalo[numintervalo].setVisible(true);
				intervalolabel[numintervalo].setVisible(true);
				
				intervalofinal[numintervalo].setVisible(true);
				intervalolabelfinal[numintervalo].setVisible(true);
				
				FramePai.internalFrameInfo.repaint();
		
			
				
			}

			if(numintervalo==4)
			{
				
				if(x==0)
				{
				   //JOptionPane.showMessageDialog(null, "Apenas 5 Intervalos podem ser adicionados");
				   btnAdicionarHorrio.setEnabled(false);
				   x+=1;
				}
				else//verifica se o numero de intervalos chegou ao máximo caso sim bloqueia o botão "+"
				if(x>=1)
				{
				btnAdicionarHorrio.setEnabled(false);
				}
			}
	}
	
	public void DeletaBotao()
	{
		if(numintervalo<=4 )
		{
			//apaga os componetes do intervalo adaptando a variavel que define a largura
			frame.Intervalo.remove(intervalo[numintervalo]);
			frame.Intervalo.remove(intervalolabel[numintervalo]);
			frame.Intervalo.remove(intervalofinal[numintervalo]);
			frame.Intervalo.remove(intervalolabelfinal[numintervalo]);
			batata-=120;
			
			FramePai.internalFrameInfo.repaint();
			numintervalo--;
		
			}

		if(numintervalo<4)//libera o botão "-"
		{
			btnAdicionarHorrio.setEnabled(true);
		}
		
		if(numintervalo==1)//bluqueia o botão "-" caso esteja no número mínimo de intervalos
		{
			btnRemoverIntervalo.setEnabled(false);
		}
	}
	
	public CadastroHorario() {
		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lista_turma=conexao.buscarDistinct_Grau();
		EnderecoGrupo = new JPanel();
		EnderecoGrupo.setBounds(110, 259, 550, 72);
		contentPane.add(EnderecoGrupo);
		EnderecoGrupo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Horario de Aula", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		EnderecoGrupo.setLayout(null);
		
		Numero = new JLabel("Hor\u00E1rio de Início: ");
		Numero.setBounds(43, 31, 90, 18);
		EnderecoGrupo.add(Numero);
		
		CEP = new JLabel("Aulas por dia: ");
		CEP.setBounds(210, 31, 77, 18);
		EnderecoGrupo.add(CEP);
		
		Estado = new JLabel("Tempo da Aula: ");
		Estado.setBounds(334, 31, 92, 18);
		EnderecoGrupo.add(Estado);
		
		ta = new JFormattedTextField (Mascara("##:##"));
		ta.setBounds(417, 31, 77, 18);
		EnderecoGrupo.add(ta);
		
		hi = new JFormattedTextField (Mascara("##:##"));
		hi.setBounds(129, 31, 77, 18);
		hi.setInputVerifier(new InputVerifier() {
			
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// TODO Auto-generated method stub
				boolean inputok = verify(arg0);
				if(inputok)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				JFormattedTextField t = (JFormattedTextField)arg0;
				Matcher m = p.matcher(t.getText());
				return m.matches();
			}
		});
		EnderecoGrupo.add(hi);
		
		ad = new JTextField ("");
		ad.setBounds(283, 31, 46, 18);
		EnderecoGrupo.add(ad);
		
		
		Intervalo= new JPanel();
		Intervalo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Intervalo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Intervalo.setBounds(110, 349, 550, 127);
		contentPane.add(Intervalo);
		Intervalo.setLayout(null);
		
		Complemento = new JLabel("Início: ");
		Complemento.setBounds(46, 31, 40, 18);
		Intervalo.add(Complemento);
		
		
		btnAdicionarHorrio = new JButton("Adicionar Intervalo");
		btnAdicionarHorrio.setBounds(150, 91, 125, 23);
		Intervalo.add(btnAdicionarHorrio);
		
		
		btnAdicionarHorrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CriarNovoBotao();
			}
				
			
	});
		
		
		
		
		btnRemoverIntervalo = new JButton("Remover Intervalo");
		btnRemoverIntervalo.setBounds(291, 91, 125, 23);
		Intervalo.add(btnRemoverIntervalo);
		
		primhorariofinal = new JFormattedTextField(Mascara("##:##"));
		primhorariofinal.setBounds(78, 60, 62, 18);
		primhorariofinal.setInputVerifier(new InputVerifier() {
			
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// TODO Auto-generated method stub
				boolean inputok = verify(arg0);
				if(inputok)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				JFormattedTextField t = (JFormattedTextField)arg0;
				Matcher m = p.matcher(t.getText());
				return m.matches();
			}
		});
		Intervalo.add(primhorariofinal);
		
		JLabel lblFinalDoIntervalo = new JLabel("Final: ");
		lblFinalDoIntervalo.setBounds(46, 60, 66, 18);
		Intervalo.add(lblFinalDoIntervalo);
		
		primhorarioini = new JFormattedTextField(Mascara("##:##"));
		primhorarioini.setInputVerifier(new InputVerifier() {
			
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			
			@Override
			public boolean shouldYieldFocus(JComponent arg0) {
				// TODO Auto-generated method stub
				boolean inputok = verify(arg0);
				if(inputok)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			@Override
			public boolean verify(JComponent arg0) {
				JFormattedTextField t = (JFormattedTextField)arg0;
				Matcher m = p.matcher(t.getText());
				return m.matches();
			}
		});
		primhorarioini.setBounds(78, 31, 62, 18);
		Intervalo.add(primhorarioini);
		btnRemoverIntervalo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeletaBotao();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Informações da Turma", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(110, 139, 550, 105);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Grau:");
		label.setBounds(43, 31, 38, 18);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Curso: ");
		label_1.setBounds(43, 58, 55, 18);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Periodo: ");
		label_2.setBounds(284, 58, 48, 18);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Classe: ");
		label_3.setBounds(284, 31, 48, 18);
		panel.add(label_3);
		
		grau = new JComboBox();
		grau.setModel(new DefaultComboBoxModel(new String[] {"Selecione um Grau...", "1", "2", "3"}));
		grau.setBounds(91, 30, 141, 18);
		panel.add(grau);
		
		classe = new JComboBox();
		classe.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma Classe...", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
		classe.setBounds(343, 31, 150, 18);
		panel.add(classe);
		
		curso = new JComboBox();
		curso.setModel(new DefaultComboBoxModel(new String[] {"Selecione um Curso...", "ETIM", "Ensino M\u00E9dio", "T\u00E9cnico em Inform\u00E1tica", "T\u00E9cnico em Inform\u00E1tica para Internet", "T\u00E9cnico em Qu\u00EDmica", "T\u00E9cnico em Administra\u00E7\u00E3o", "T\u00E9cnico em Eletronica", "T\u00E9cnico em Mecatronica", "Selecione um Curso...", "Selecione um Curso..."}));
		curso.setBounds(91, 58, 141, 18);
		panel.add(curso);
		
		periodo = new JComboBox();
		periodo.setModel(new DefaultComboBoxModel(new String[] {"Selecione um Periodo...", "Integral", "Matutino", "Vespertino", "Noturno"}));
		periodo.setBounds(343, 58, 150, 18);
		panel.add(periodo);
		
		
		//---------------
		
		JLabel lblGerenciamento = new JLabel("Gerenciamento");
		lblGerenciamento.setForeground(Color.WHITE);
		lblGerenciamento.setFont(new Font("Arial", Font.BOLD, 14));
		lblGerenciamento.setBounds(56, 39, 121, 14);
		contentPane.add(lblGerenciamento);
		
		JLabel cadastroHorario = new JLabel("Horario");
		cadastroHorario.setForeground(Color.WHITE);
		cadastroHorario.setFont(new Font("Arial", Font.BOLD, 14));
		cadastroHorario.setBounds(100, 79, 61, 14);
		contentPane.add(cadastroHorario);
		
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
				
				 JLabel cadastromateria = new JLabel("Mat\u00E9rias");
					
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
		//---------------
		
						

						JButton cadastrar = new JButton("Cadastrar");
						cadastrar.setOpaque(false);
						cadastrar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								
								if(
										grau.getSelectedIndex()!=0
										&&curso.getSelectedIndex()!=0
										&&classe.getSelectedIndex()!=0
										&&periodo.getSelectedIndex()!=0
										&&!hi.getText().equals("")
										&&!ad.getText().equals("")
										&&!ta.getText().equals("")
										&&!primhorarioini.getText().equals("")//verifica se os campos estão preenchidos
										&&!primhorariofinal.getText().equals("")){
								lista_turmaexiste=conexao.buscarTurmas();
								
								boolean controleexiste=true;
								for(int i=0;i<lista_turma.size();i++)
								  {
						  			if(lista_turmaexiste.get(i).getGrau()==Integer.parseInt(grau.getSelectedItem().toString()) && 
						  					lista_turmaexiste.get(i).getCurso().equals(curso.getSelectedItem().toString()) && 
						  					lista_turmaexiste.get(i).getClasse().equals(classe.getSelectedItem().toString())) //Obtêm o id da lista que confere com a seleção.
						  					{
						  					  	controleexiste=false;			
								             }
						  		   }
								
								
								int hi_h=Integer.parseInt(hi.getText().substring(0,2));
								int hi_m=Integer.parseInt(hi.getText().substring(3,5));
								int apd=Integer.parseInt(ad.getText());
								int ha_h=Integer.parseInt(ta.getText().substring(0,2));
								int ha_m=Integer.parseInt(ta.getText().substring(3,5));
								int intervaloi_h=Integer.parseInt(primhorarioini.getText().substring(0,2));
								int intervaloi_m=Integer.parseInt(primhorarioini.getText().substring(3,5));
								int intervalof_h=Integer.parseInt(primhorariofinal.getText().substring(0,2));
								int intervalof_m=Integer.parseInt(primhorariofinal.getText().substring(3,5));//pega os horarios na forma de integer
								
								
								hora=hi_h;
								minuto=hi_m;
								
								if(controleexiste==false)
								{
									JOptionPane.showMessageDialog(null, "Turma ja existente.");//verifica se a turma existe senão cria ela
								}
								else
								{
									
									Turma t = new Turma();
									t.setGrau(Integer.parseInt(grau.getSelectedItem().toString()));
									t.setClasse(classe.getSelectedItem().toString());
									t.setCurso(curso.getSelectedItem().toString());
									t.setPeriodo(periodo.getSelectedItem().toString());
									t.setIdTurma(conexao.inserir(t));
								
								
								
								for(int controlenumaula=0;controlenumaula<apd+1;controlenumaula++)
								{
									
									
									
									
									for(int y=1;numintervalo>y;y++)
									{
										for(int controlenumvetoraula=2;controlenumvetoraula<numintervalo+1;controlenumvetoraula++)
										{
											intervaloci_h[controlenumvetoraula]=Integer.parseInt(intervalo[controlenumvetoraula].getText().substring(0,2));
											intervaloci_m[controlenumvetoraula]= Integer.parseInt(intervalo[controlenumvetoraula].getText().substring(3,5));

										    intervalocf_h[controlenumvetoraula]=Integer.parseInt(intervalofinal[controlenumvetoraula].getText().substring(0,2)); 
											intervalocf_m[controlenumvetoraula]= Integer.parseInt(intervalofinal[controlenumvetoraula].getText().substring(3,5));
											
											if(intervaloci_h[controlenumvetoraula]==hora && intervaloci_m[controlenumvetoraula]==minuto)//se fim da aula bate com o inicio do intervalo então adiciona o intervalo
											{
												Horario h = new Horario();
												h.setTurma(t);
												h.setEntrada(Time.valueOf(String.valueOf(intervaloci_h[controlenumvetoraula])+":"+String.valueOf(intervaloci_m[controlenumvetoraula])+":00"));
												h.setSaida(Time.valueOf(String.valueOf(intervalocf_h[controlenumvetoraula])+":"+String.valueOf(intervalocf_m[controlenumvetoraula])+":00"));
												h.setTipo("Intervalo");
												conexao.inserir_t(h);
												if(intervalocf_h[controlenumvetoraula] > intervaloci_h[controlenumvetoraula] && intervalocf_m[controlenumvetoraula]<intervaloci_m[controlenumvetoraula])
												{
													intervalocf_h[controlenumvetoraula]-=1;
													hora+=intervalocf_h[controlenumvetoraula]-intervaloci_h[controlenumvetoraula];
													minuto+=(intervalocf_m[controlenumvetoraula]+60)-intervaloci_m[controlenumvetoraula];
													while(minuto>=60)
													{
														hora+=1;
														minuto-=60;
													}
												}//faz as somas de horas
												else
													
														if(intervalocf_h[controlenumvetoraula] < intervaloci_h[controlenumvetoraula])//verifica se o inicio vem depois do fim
														{
															JOptionPane.showMessageDialog(null, "Intervalo Final menor que Intervalo Inicial");
														}
														else
														{
															hora+=intervalocf_h[controlenumvetoraula]-intervaloci_h[controlenumvetoraula];
															minuto+=intervalocf_m[controlenumvetoraula]-intervaloci_m[controlenumvetoraula];
															while(minuto>=60)
														{
															hora+=1;
															minuto-=60;
														}
													}
											}
										}
									}
									
									//-------------------------------------------------------------------------------------
									if(intervaloi_h==hora && intervaloi_m==minuto)
									{
										Horario h = new Horario();
										h.setTurma(t);
										h.setEntrada(Time.valueOf(String.valueOf(intervaloi_h)+":"+String.valueOf(intervaloi_m)+":00"));
										h.setSaida(Time.valueOf(String.valueOf(intervalof_h)+":"+String.valueOf(intervalof_m)+":00"));
										h.setTipo("Intervalo");
										conexao.inserir_t(h);
										if(intervalof_h > intervaloi_h && intervalof_m<intervaloi_m)
										{
											intervalof_h-=1;
											hora+=intervalof_h-intervaloi_h;
											minuto+=(intervalof_m+60)-intervaloi_m;
											while(minuto>=60)
											{
												hora+=1;
												minuto-=60;
											}
										}
										else
											
												if(intervalof_h < intervaloi_h)
												{
													JOptionPane.showMessageDialog(null, "Intervalo Final menor que Intervalo Inicial");
												}
												else
												{
													hora+=intervalof_h-intervaloi_h;
													minuto+=intervalof_m-intervaloi_m;
													while(minuto>=60)
												{
													hora+=1;
													minuto-=60;
												}
											}
									}
									
									//-------------------------------------------------------------------------------------
									else
									{
									Horario h = new Horario();
									h.setTurma(t);
									h.setEntrada(Time.valueOf(String.valueOf(hora)+":"+String.valueOf(minuto)+":00"));
									hora+=ha_h;
									minuto+=ha_m;
									while(minuto>=60)
									{
										hora+=1;
										minuto-=60;
									}
									h.setSaida(Time.valueOf(String.valueOf(hora)+":"+String.valueOf(minuto)+":00"));
									h.setTipo("Aula");
									conexao.inserir_t(h);
									}
								}
								AcaoRecente a =new AcaoRecente();
								a.setFuncionario(FramePai.usuarioatual);
								a.setAcao("Turma "+t.getGrau()+" "+t.getCurso()+" "+t.getClasse()+" Adicionada");
								conexao.inserir(a);
								FramePai.a.add(a);
								Principal.AtualizarAcoes();
								FramePai.Confirma(CadastroHorario.this);//insere nas ações recentes
								}
								
								grau.setSelectedIndex(0);
								curso.setSelectedIndex(0);
								classe.setSelectedIndex(0);
								periodo.setSelectedIndex(0);
								hi.setText("");
								ad.setText("");
								ta.setText("");
								primhorarioini.setText("");//deixa os campos vazios.
								primhorariofinal.setText("");
								if(numintervalo>1)
								{
									for(int x=2;x<=numintervalo;x++)
									{
										intervalo[x].setText("");
										intervalofinal[x].setText("");
									}
								}
							}
								else
								{
									JOptionPane.showMessageDialog(CadastroHorario.this, "Há campos nulos");
								}
								
							}
						});
						cadastrar.setBounds(780, 332, 114, 23);
						contentPane.add(cadastrar);
		
		JButton excluir = new JButton("Excluir");
		excluir.setOpaque(false);
		excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(CadastroHorario.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
				{
		    List<Turma> tu=conexao.buscarTurmas();
		    Turma t = new Turma();
		    t.setIdTurma(-3);
		    for(int i = 0;i<tu.size();i++){
		     if(tu.get(i).getGrau()==Integer.parseInt(grau.getSelectedItem().toString())
		     &&tu.get(i).getClasse().equals(classe.getSelectedItem().toString())
		     &&tu.get(i).getCurso().equals(curso.getSelectedItem().toString())
		     &&tu.get(i).getPeriodo().equals(periodo.getSelectedItem().toString())){
		      t.setIdTurma(tu.get(i).getIdTurma());
		      break;
		     }
		    }
		    if(t.getIdTurma()!=-3){
		    List<Horario> h=conexao.buscarHorarios_Turma(t);
		    for(int i=0;i<h.size();i++){
		     conexao.Excluir_t(h.get(i));//apaga os horarios
		    }
		    AcaoRecente a =new AcaoRecente();
			a.setFuncionario(FramePai.usuarioatual);
			a.setAcao("Turma "+t.getGrau()+" "+t.getCurso()+" "+t.getClasse()+" Excluida");
			conexao.inserir(a);
			FramePai.a.add(a);
			Principal.AtualizarAcoes();//insere nas ações recentes
			FramePai.Confirma(CadastroHorario.this);
		    }
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
		
		JLabel lblGrau = new JLabel("Grau : ");
		lblGrau.setForeground(Color.WHITE);
		lblGrau.setBounds(728, 174, 46, 18);
		contentPane.add(lblGrau);
		
		JLabel lblNewLabel = new JLabel("Curso: ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(728, 204, 46, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Classe: ");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(728, 234, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBoxgrau = new JComboBox();
		comboBoxgrau.setBounds(772, 175, 142, 18);
		comboBoxgrau.addItem("Selecione um Grau...");
		for(int i=0;i<lista_turma.size();i++)
		  {
		  comboBoxgrau.addItem(lista_turma.get(i).getGrau());
		  }
		contentPane.add(comboBoxgrau);
		
		JComboBox comboBoxcurso = new JComboBox();
		comboBoxcurso.setEnabled(false);
		comboBoxcurso.setBounds(772, 204, 142, 18);
		contentPane.add(comboBoxcurso);
		
		JComboBox comboBoxclasse = new JComboBox();
		comboBoxclasse.setEnabled(false);
		comboBoxclasse.setBounds(772, 233, 142, 18);
		contentPane.add(comboBoxclasse);
		
		comboBoxgrau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				grau_selecionado=String.valueOf(comboBoxgrau.getSelectedItem());  // Pega o grau selecionado.
		  		 lista_turma=conexao.buscarTurmas();  // Cria um lista de turmas.
		  		 comboBoxcurso.setEnabled(true);
		  		 comboBoxcurso.removeAllItems();   
		  		comboBoxcurso.addItem("Selecione um Curso...");
		  		 comboBoxclasse.removeAllItems();
		  		for(int i=0;i<lista_turma.size();i++)      //Controle do tamanho da lista de turmas.
 				  {
 		  			if(lista_turma.get(i).getGrau()==Integer.parseInt(grau_selecionado))
 		  			{
 		  			 comboBoxcurso.addItem(lista_turma.get(i).getCurso()); // adiciona dados na combobox.
 				    }
 		  		  }
		  		comboBoxclasse.removeAllItems();
		  		comboBoxclasse.addItem("Selecione uma Classe...");
		  		comboBoxclasse.setEnabled(false);
			}
		});

		comboBoxcurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				comboBoxcurso.setEnabled(true);
		  		grau_selecionado=String.valueOf(comboBoxgrau.getSelectedItem());
		  		curso_selecionado=String.valueOf(comboBoxcurso.getSelectedItem());
		  		 lista_turma=conexao.buscarTurmas();
		  		 comboBoxclasse.setEnabled(true);
		  		 
		  		 comboBoxclasse.removeAllItems();
		  		comboBoxclasse.addItem("Selecione um Classe...");
		  		for(int x=0;x<lista_turma.size();x++)
 				  {
 		  			if((lista_turma.get(x).getCurso().equals(curso_selecionado)) && (lista_turma.get(x).getGrau()==Integer.parseInt(grau_selecionado)))  // Quando o curso e o grau são obtidos é feita uma realção entre eles
 		  			{
 		  			 comboBoxclasse.addItem(lista_turma.get(x).getClasse()); //Classes adicionadas que batem com a relação acima. 
 				    }
 		  		  }

			}
		});
		
		JLabel lblinsiraUmaTurma = new JLabel("<html>Insira uma turma j\u00E1 cadastrada para visualizar e/ou alterar o registro da mesma.</html>");
		lblinsiraUmaTurma.setForeground(Color.WHITE);
		lblinsiraUmaTurma.setBounds(728, 246, 226, 59);
		contentPane.add(lblinsiraUmaTurma);
		
		alterar = new JButton("Alterar");
		alterar.setOpaque(false);
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(CadastroHorario.this, "Tem certeza que deseja alterar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
				{
				
			    if(grau.getSelectedIndex()!=0&&
			      curso.getSelectedIndex()!=0&&
			      classe.getSelectedIndex()!=0&&
			      periodo.getSelectedIndex()!=0&&
			      !hi.getText().equals("")&&
			      !ad.getText().equals("")&&
			      !ta.getText().equals("")&&
			      !primhorarioini.getText().equals("")&&
			      !primhorariofinal.getText().equals("")){
			    List<Turma> tu=conexao.buscarTurmas();
			    Turma t = new Turma();
			    t.setIdTurma(-3);
			    for(int i = 0;i<tu.size();i++){
			     if(tu.get(i).getGrau()==Integer.parseInt(grau.getSelectedItem().toString())
			     &&tu.get(i).getClasse().equals(classe.getSelectedItem().toString())
			     &&tu.get(i).getCurso().equals(curso.getSelectedItem().toString())
			     &&tu.get(i).getPeriodo().equals(periodo.getSelectedItem().toString())){
			      t.setIdTurma(tu.get(i).getIdTurma());
			      break;
			     }
			    }
			    if(t.getIdTurma()!=-3){
			    List<Horario> h=conexao.buscarHorarios_Turma(t);
			    for(int i=0;i<h.size();i++){
			     conexao.Excluir_t(h.get(i));//apaga os horarios
			    }
			    }
			    int hi_h=Integer.parseInt(hi.getText().substring(0,2));
			    int hi_m=Integer.parseInt(hi.getText().substring(3,5));
			    int apd=Integer.parseInt(ad.getText());
			    int ha_h=Integer.parseInt(ta.getText().substring(0,2));
			    int ha_m=Integer.parseInt(ta.getText().substring(3,5));
			    int intervaloi_h=Integer.parseInt(primhorarioini.getText().substring(0,2));
			    int intervaloi_m=Integer.parseInt(primhorarioini.getText().substring(3,5));
			    int intervalof_h=Integer.parseInt(primhorariofinal.getText().substring(0,2));
			    int intervalof_m=Integer.parseInt(primhorariofinal.getText().substring(3,5));
			    int hf_h;
			    int hf_m;
			    
			    hora=hi_h;
			    minuto=hi_m;
			    Turma t1 = new Turma();
			    t1.setGrau(Integer.parseInt(grau.getSelectedItem().toString()));
			    t1.setClasse(classe.getSelectedItem().toString());
			    t1.setCurso(curso.getSelectedItem().toString());
			    t1.setPeriodo(periodo.getSelectedItem().toString());
			    t1.setIdTurma(t.getIdTurma());
			    
			    
			    
			    for(int controlenumaula=0;controlenumaula<apd+1;controlenumaula++)//cria os horarios novamente
			    {
			     
			     
			     
			     
			     for(int y=1;numintervalo>y;y++)
			     {
			      for(int controlenumvetoraula=2;controlenumvetoraula<numintervalo+1;controlenumvetoraula++)
			      {
			       intervaloci_h[controlenumvetoraula]=Integer.parseInt(intervalo[controlenumvetoraula].getText().substring(0,2));
			       intervaloci_m[controlenumvetoraula]= Integer.parseInt(intervalo[controlenumvetoraula].getText().substring(3,5));

			          intervalocf_h[controlenumvetoraula]=Integer.parseInt(intervalofinal[controlenumvetoraula].getText().substring(0,2)); ;
			       intervalocf_m[controlenumvetoraula]= Integer.parseInt(intervalofinal[controlenumvetoraula].getText().substring(3,5));;
			       
			       if(intervaloci_h[controlenumvetoraula]==hora && intervaloci_m[controlenumvetoraula]==minuto)
			       {
			        Horario h = new Horario();
			        h.setTurma(t1);
			        h.setEntrada(Time.valueOf(String.valueOf(intervaloci_h[controlenumvetoraula])+":"+String.valueOf(intervaloci_m[controlenumvetoraula])+":00"));
			        h.setSaida(Time.valueOf(String.valueOf(intervalocf_h[controlenumvetoraula])+":"+String.valueOf(intervalocf_m[controlenumvetoraula])+":00"));
			        h.setTipo("Intervalo");
			        conexao.inserir_t(h);
			        if(intervalocf_h[controlenumvetoraula] > intervaloci_h[controlenumvetoraula] && intervalocf_m[controlenumvetoraula]<intervaloci_m[controlenumvetoraula])
			        {
			         intervalocf_h[controlenumvetoraula]-=1;
			         hora+=intervalocf_h[controlenumvetoraula]-intervaloci_h[controlenumvetoraula];
			         minuto+=(intervalocf_m[controlenumvetoraula]+60)-intervaloci_m[controlenumvetoraula];
			         while(minuto>=60)
			         {
			          hora+=1;
			          minuto-=60;
			         }
			        }
			        else
			         
			          if(intervalocf_h[controlenumvetoraula] < intervaloci_h[controlenumvetoraula])
			          {
			           JOptionPane.showMessageDialog(null, "Intervalo Final menor que Intervalo Inicial");
			          }
			          else
			          {
			           hora+=intervalocf_h[controlenumvetoraula]-intervaloci_h[controlenumvetoraula];
			           minuto+=intervalocf_m[controlenumvetoraula]-intervaloci_m[controlenumvetoraula];
			           while(minuto>=60)
			          {
			           hora+=1;
			           minuto-=60;
			          }
			         }
			       }
			      }
			     }
			     
			     //-------------------------------------------------------------------------------------
			     if(intervaloi_h==hora && intervaloi_m==minuto)
			     {
			      Horario h = new Horario();
			      h.setTurma(t1);
			      h.setEntrada(Time.valueOf(String.valueOf(intervaloi_h)+":"+String.valueOf(intervaloi_m)+":00"));
			      h.setSaida(Time.valueOf(String.valueOf(intervalof_h)+":"+String.valueOf(intervalof_m)+":00"));
			      h.setTipo("Intervalo");
			      conexao.inserir_t(h);
			      if(intervalof_h > intervaloi_h && intervalof_m<intervaloi_m)
			      {
			       intervalof_h-=1;
			       hora+=intervalof_h-intervaloi_h;
			       minuto+=(intervalof_m+60)-intervaloi_m;
			       while(minuto>=60)
			       {
			        hora+=1;
			        minuto-=60;
			       }
			      }
			      else
			       
			        if(intervalof_h < intervaloi_h)
			        {
			         JOptionPane.showMessageDialog(null, "Intervalo Final menor que Intervalo Inicial");
			        }
			        else
			        {
			         hora+=intervalof_h-intervaloi_h;
			         minuto+=intervalof_m-intervaloi_m;
			         while(minuto>=60)
			        {
			         hora+=1;
			         minuto-=60;
			        }
			       }
			     }
			     
			     //-------------------------------------------------------------------------------------
			     else
			     {
			     Horario h = new Horario();
			     h.setTurma(t1);
			     h.setEntrada(Time.valueOf(String.valueOf(hora)+":"+String.valueOf(minuto)+":00"));
			     hora+=ha_h;
			     minuto+=ha_m;
			     while(minuto>=60)
			     {
			      hora+=1;
			      minuto-=60;
			     }
			     h.setSaida(Time.valueOf(String.valueOf(hora)+":"+String.valueOf(minuto)+":00"));
			     h.setTipo("Aula");
			     conexao.inserir_t(h);
			     }
			    }
			    AcaoRecente a =new AcaoRecente();
				a.setFuncionario(FramePai.usuarioatual);
				a.setAcao("Turma "+t.getGrau()+" "+t.getCurso()+" "+t.getClasse()+" Alterada");
				conexao.inserir(a);
				FramePai.a.add(a);
				Principal.AtualizarAcoes();//insere nas ações recentes
				FramePai.Confirma(CadastroHorario.this);
			    }else{
			    	JOptionPane.showMessageDialog(CadastroHorario.this, "Dados insuficientes");
			    }
				
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
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if(comboBoxgrau.getSelectedIndex()!=0&&
						comboBoxcurso.getSelectedIndex()!=0&&
						comboBoxclasse.getSelectedIndex()!=0){
			 	grau_selecionado=String.valueOf(comboBoxgrau.getSelectedItem());   //Pega o grau selecionado.
	  		   classe_selecionada=String.valueOf(comboBoxclasse.getSelectedItem()); //Pega a classe selecionada.
	  		   curso_selecionado=String.valueOf(comboBoxcurso.getSelectedItem());  //Pega o curso selecionado.
	    	  
	    	   
	    	   
	    		if(grau_selecionado=="Selecione uma Grau...") //Verifica a seleção.
	    		{
	    			JOptionPane.showMessageDialog(null, "Opção de grau invalida");
	    		}
	    		else
	    		if(classe_selecionada=="Selecione uma Classe...")//Verifica a seleção.
	    		{
	    			JOptionPane.showMessageDialog(null, "Opção de classe invalida");
	    		}
	    		
	    		else
		    		if(curso_selecionado=="Selecione um Curso...")//Verifica a seleção.
		    		{
		    			JOptionPane.showMessageDialog(null, "Opção de curso invalida");
		    		}
		    		else
		    		{
		    			if(numintervalo>1)
		    			{
		    				for(int u=1;u<=numintervalo;u++)
		    				{
		    					DeletaBotao();
		    				}
		    			}
		    			
		    			alterar.setEnabled(true);
		    			excluir.setEnabled(true);
		    			cadastrar.setEnabled(false);
		    			int b=-1;
		  		  		for(int i=0;i<lista_turma.size();i++)
		  				  {
		  		  			if(lista_turma.get(i).getGrau()==Integer.parseInt(grau_selecionado) && 
		  		  					lista_turma.get(i).getCurso().equals(curso_selecionado) && 
		  		  					lista_turma.get(i).getGrau()==Integer.parseInt(grau_selecionado)) //Obtêm o id da lista que confere com a seleção.
		  		  					{
		  		  				
		  				               b=i;
		  				             }
		  		  		   }
		  		  		
		  		  		
		  		  		Turma t = new Turma();
		    			t=conexao.buscarTurmas(lista_turma.get(b).getIdTurma());
		    			grau.setSelectedItem(grau_selecionado);
		    			curso.setSelectedItem(t.getCurso());
		    			classe.setSelectedItem(t.getClasse());
		    			periodo.setSelectedItem(t.getPeriodo().toString());
		    			
		    			
		    			List<Horario> h ;
		    			h=conexao.buscarHorarios_Turma(t);
		    			hi.setText(String.valueOf(h.get(0).getEntrada()));
		    			int p=0;
		    			
		    			for(int i=0;i<h.size();i++)
		    			{
		    				if(h.get(i).getTipo().equals("Intervalo"))
		    				{
		    					
		    					p++;
		    				}
		    			}
		    			
		    			
		    			int pegatermino=0;
		    				for(int i=0;i<h.size();i++)
			    			{
			    				if(h.get(i).getTipo().equals("Intervalo"))
			    				{
			    					
			    					primhorarioini.setText(h.get(i).getEntrada().toString());
			    					primhorariofinal.setText(h.get(i).getSaida().toString());
			    					pegatermino=i+1;
			    					break;
			    				}
			    			}
		    			
		    			if(p>1)
		    			{
		    				for(int y=2;y<=p;y++)
		    				{
		    					CriarNovoBotao();
		    					for(;pegatermino<h.size();pegatermino++)
				    			{
				    				if(h.get(pegatermino).getTipo().equals("Intervalo"))
				    				{
				    					
				    					intervalo[y].setText(h.get(pegatermino).getEntrada().toString());
				    					intervalofinal[y].setText(h.get(pegatermino).getSaida().toString());
				    					pegatermino+=1;
				    					break;
				    				}
				    			}
		    					
		    				}
		    			}

		    			
		    			ad.setText(String.valueOf(h.size()-p));	    			
		    			String hpegaentra=h.get(0).getEntrada().toString();
		    			String hpegasai=h.get(0).getSaida().toString();
		    			
		    			
		    			int h_h=Integer.parseInt(hpegaentra.substring(0,2));
		    			int h_m=Integer.parseInt(hpegaentra.substring(3,5));
		    			
		    			int h_h2=Integer.parseInt(hpegasai.substring(0,2));
		    			int h_m2=Integer.parseInt(hpegasai.substring(3,5));
		    			
		    			
		    			
		    			int ho=0;
		    			int min=0;
		    			if(h_h2 > h_h && h_m2<h_m)
						{
							ho=0;
							min+=(h_m2+60)-h_m;
							while(min>=60)
							{
								ho+=1;
								min-=60;
							}
							
						}
						else
						{
						    ho+=h_h2-h_h;
							min+=h_m2-h_m;
							while(min>=60)
							{
								ho+=1;
								min-=60;
							}
						}
		    			
		    			String minf;
		    			String hof;
		    			if(ho<10)
		    			{
		    				hof="0"+String.valueOf(ho);
		    			}
		    			else
		    			{
		    				hof=String.valueOf(ho);
		    			}
		    			
		    			if(min<10)
		    			{
		    				minf="0"+String.valueOf(min);
		    			}
		    			else
		    			{
		    				minf=String.valueOf(min);
		    			}
		    			
		    			ta.setText(hof+":"+minf);
		    			
		    									}
				}else{
					JOptionPane.showMessageDialog(CadastroHorario.this, "Sem parametros para pesquisa");
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
		
		JButton btnNewButton = new JButton("Preencher");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				grau.setSelectedIndex(3);
				curso.setSelectedIndex(2);
				classe.setSelectedIndex(5);
				periodo.setSelectedIndex(2);
				
				hi.setText("0730");
				ad.setText("5");
				ta.setText("0050");
				primhorarioini.setText("1000");
				primhorariofinal.setText("1020");
				
				
			}
		});
		btnNewButton.setBounds(57, 536, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel fundo = new JLabel("");
		fundo.setIcon(new ImageIcon(CadastroAluno.class.getResource("/FundoCadastroHorario2.png")));
		fundo.setBounds(0, 0, 1018, 611);
		contentPane.add(fundo);
		
	}
}
