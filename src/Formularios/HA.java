package Formularios;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.ListSelectionModel;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;
import javax.swing.JPanel;

import model.AcaoRecente;
import model.Aula;
import model.Horario;
import model.Turma;
import bd.Conexao;

import javax.swing.JComboBox;


public class HA extends JFrame
{

	// --------------------------------------------------------------------------------
	// Classes
	static Conexao conexao = Login.conexao; //Inst�ncia a classe conexao
	// --------------------------------------------------------------------------------
	// Variaveis
	int col, lin, col1, col2;// variaveis de linhas e colunas da tabela
	int numtabela = conexao.contar("turmas", "idturma"); //N� de turmas
	boolean mouseapertado = false;//Indica se o mouse est� apertado
	boolean travarmouse = false;//Indica se � necess�rio que os m�todos envolvendo o mouse n�o sejam efetuados
	boolean carregou = false;//Indica se todos os requisitos para gerar-se um hor�rio foram atendidos
	boolean salvando = false;//Indica se est� salvando
	public List<Turma> turmas;//Lista de Turmas
	String mat[][] = new String[numtabela][100]; // Vetor de mat�rias
	String sala[][] = new String[numtabela][100]; // Vetor de salas
	String prof[][] = new String[numtabela][100]; // Vetor de professores
	String matx[][] = new String[numtabela][100]; // Vetor de mat�rias em posi��es da tabela que ser�o definidas
	String profx[][] = new String[numtabela][100]; // Vetor de professores em posi��es da tabela que ser�o definidas
	String salax[][] = new String[numtabela][100]; // Vetor de salas em posi��es da tabela que ser�o definidas
	String profdisp[][][] = new String[numtabela][100][5]; // Vetor de hor�rios de disponibilidade dos professores
	String profdispx[][][] = new String[numtabela][100][5];	// Vetor de hor�rios de disponibilidade dos professores em posi��es da tabela que ser�o definidas
	
	boolean prio[][] = new boolean[numtabela][100]; // Vetor de prioridade sobre a mat�ria ter que ser aula dupla ou n�o
	int limiteaula[][] = new int[numtabela][100]; // limite de aulas de uma mat�ria de uma turma por semana 
	int materiarandom; // Mat�ria selecionada aleatoriamente
	int conta = 0; // N�mero de vezes que foi feita a sele��o aleat�ria de mat�ria
	int t[] = new int[numtabela]; // N�mero de aulas que uma turma tem por semana
	int num[] = new int[numtabela]; // N�mero de mat�rias que uma turma tem
	int volta; // Vari�vel de armazenamento
	int dia=0; // Dia da semana
	int idturma[] = new int[conexao.buscarTurmas().size()]; // Id da turma no banco de dados
	boolean erro = false; //Indica se foi encontrado um erro
	boolean erro1 = false; // Indica se foi encontrado um erro
	List<Horario> horaprof; // Pega os hor�rios de disponibilidade de todos os professores

	// --------------------------------------------------------------------------------
	// Objetos
	private JButton btnGerarHorario; // Bot�o de gerar o hor�rio
	private JButton btnSalvarHorrio; // Bot�o de salvar o hor�rio no banco de dados
	JPanel panel[] = new JPanel[numtabela]; // Onde s�o colocadas as tabelas e as JLabels
	JTable tabelas[] = new JTable[numtabela]; // Tabelas dos hor�rios
	JLabel seg[] = new JLabel[numtabela]; // Label segunda
	JLabel ter[] = new JLabel[numtabela]; // Label ter�a
	JLabel qua[] = new JLabel[numtabela]; // Label quarta
	JLabel qui[] = new JLabel[numtabela]; // Label quinta
	JLabel sex[] = new JLabel[numtabela]; // Label sexta
	JLabel hora[] = new JLabel[numtabela]; // Label hor�rio
	JButton btnCarregarHorario = new JButton(); // Bot�o de carregar o hor�rio do banco de dados
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP); // Onde s�o colocados os JPanels



	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
		{

			public void run()
			{

				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // Designa a apar�ncia da interface
					HA frame = new HA(); // Inst�ncia o HA
					frame.setVisible(true); // Torna o HA vis�vel
				}
				catch (Exception e)
				{
					e.printStackTrace(); // Mostra o relat�rio de erros
				}
			}
		});
	}

	public void getDados()
	{

		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir um valor �s vari�veis mat, prof, sala e limiteaula
		{
			for (int ze = 0; ze < 100; ze++) // La�o de repeti��o para atribuir valores � todos os espa�os das vari�veis mat,prof, sala e limiteaula
			{
				limiteaula[tab][ze] = -1;
				if (ze >= 1) // Se ze � maior ou igual a 1
				{
					mat[tab][ze] = "a" + mat[tab][ze - 1];
					prof[tab][ze] = "a" + prof[tab][ze - 1];
					sala[tab][ze] = "a" + sala[tab][ze - 1];
				}
				else
				{
					mat[tab][ze] = "a";
					prof[tab][ze] = "a";
					sala[tab][ze] = "a";
				}

			}
		}
		
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir um valor � vari�vel profdisp
		{
			for (int ze = 0; ze < 100; ze++)  // La�o de repeti��o para atribuir valores � todos os espa�os da vari�vel profdisp
			{
				profdisp[tab][ze][0]="";
				profdisp[tab][ze][1]="";
				profdisp[tab][ze][2]="";
				profdisp[tab][ze][3]="";
				profdisp[tab][ze][4]="";
			}
		}

		List<Turma> turma = conexao.buscarTurmas(); // Lista de turmas
		List<String> profmat =conexao.buscarProfessores(true); // Lista de rela��es
		List<Horario> horaprof=conexao.buscarHorarios_Professor(); // Lista de hor�rios de disponibilidade dos professores
		for (int co = 0; co < turma.size(); co++) // La�o de repeti��o para atribuir os ids das turmas na vari�vel idturma
		{
			idturma[co] = turma.get(co).getIdTurma();
		}
		try
		{
			for (int tur = 0; tur < idturma.length; tur++) // La�o de repeti��o para atribuir os valores das vari�veis mat, prof, profdisp, sala, limiteaula, prio e numprio
			{
				int lol = 0;
				for (int co = 0; co < profmat.size(); co = co + 6) // La�o de repeti��o para atribuir os valores das vari�veis mat, prof, profdisp, sala, limiteaula, prio e numprio
				{
					if (profmat.get(co + 2).equals(String.valueOf(idturma[tur]))) // Se o valor pego � igual ao idturma
					{
						mat[tur][lol] = profmat.get(co + 1);
						prof[tur][lol] = profmat.get(co);
						for(int pro=0;pro<horaprof.size();pro++){ // La�o de repeti��o para verificar todos os hor�rios de disponibilidade dos professores
							if(horaprof.get(pro).getProfessor().getSigla().equals(prof[tur][lol]) // Se o professor pego � o mesmo que o do prof e o dia � o mesmo, o hor�rio de disponibilidade dele � designado
									&& horaprof.get(pro).getDia().toLowerCase().equals("segunda")){
								profdisp[tur][lol][0]=horaprof.get(pro).getEntrada().toString().substring(0, 5)
										+"-"+horaprof.get(pro).getSaida().toString().substring(0, 5);
							}
							if(horaprof.get(pro).getProfessor().getSigla().equals(prof[tur][lol]) // Se o professor pego � o mesmo que o do prof e o dia � o mesmo, o hor�rio de disponibilidade dele � designado
									&& horaprof.get(pro).getDia().toLowerCase().equals("ter�a")){
								profdisp[tur][lol][1]=horaprof.get(pro).getEntrada().toString().substring(0, 5)
										+"-"+horaprof.get(pro).getSaida().toString().substring(0, 5);
							}
							if(horaprof.get(pro).getProfessor().getSigla().equals(prof[tur][lol]) // Se o professor pego � o mesmo que o do prof e o dia � o mesmo, o hor�rio de disponibilidade dele � designado
									&& horaprof.get(pro).getDia().toLowerCase().equals("quarta")){
								profdisp[tur][lol][2]=horaprof.get(pro).getEntrada().toString().substring(0, 5)
										+"-"+horaprof.get(pro).getSaida().toString().substring(0, 5);
							}
							if(horaprof.get(pro).getProfessor().getSigla().equals(prof[tur][lol]) // Se o professor pego � o mesmo que o do prof e o dia � o mesmo, o hor�rio de disponibilidade dele � designado
									&& horaprof.get(pro).getDia().toLowerCase().equals("quinta")){
								profdisp[tur][lol][3]=horaprof.get(pro).getEntrada().toString().substring(0, 5)
										+"-"+horaprof.get(pro).getSaida().toString().substring(0, 5);
							}
							if(horaprof.get(pro).getProfessor().getSigla().equals(prof[tur][lol]) // Se o professor pego � o mesmo que o do prof e o dia � o mesmo, o hor�rio de disponibilidade dele � designado
									&& horaprof.get(pro).getDia().toLowerCase().equals("sexta")){
								profdisp[tur][lol][4]=horaprof.get(pro).getEntrada().toString().substring(0, 5)
										+"-"+horaprof.get(pro).getSaida().toString().substring(0, 5);
							}
						}
						sala[tur][lol] = profmat.get(co + 3).replace(".0", ""); // Atribui o valor da sala
						limiteaula[tur][lol] = Integer.valueOf(profmat.get(co + 4).replace("[", "").replace("]", "")); // Atribui o valor do limiteaula
						
						if(Integer.valueOf(profmat.get(co + 5).replace("[", "").replace("]", ""))==1){
							prio[tur][lol]=true;
						}else if(Integer.valueOf(profmat.get(co + 5).replace("[", "").replace("]", ""))==0){
							prio[tur][lol]=false;
						}
						lol++;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(); // Mostra o relat�rio de erros
		}
		carregou = true; // Atribuiu todos os valores necess�rios
	}

	public void gerar()
	{
		dia=0; // Atribui um valor ao dia
		String materiasverificadas=""; // Armazena quais mat�rias j� foram selecionadas aleat�riamente
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir valores iniciais para as vari�veis num e t
		{
			num[tab] = 0;
			t[tab] = 0;
		}
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir os valores do vetor num
		{
			for (int ze = 0; ze < limiteaula[tab].length; ze++) // La�o de repeti��o para atribuir o valor do vetor num
			{
				if (limiteaula[tab][ze] != -1)
				{
					num[tab]++;
				}
			}
		}
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir os valores do vetor t
		{
			for (int ze = 0; ze < 100; ze++) // La�o de repeti��o para atribuir o valor do vetor t
			{
				if (limiteaula[tab][ze] != -1) // Se o limiteaula � diferente de -1
				{
					t[tab] += limiteaula[tab][ze]; // Adiciona o n�mero de aulas para t
				}
			}
		}

		int ix[][] = new int[numtabela][100]; // N�mero de vezes que a mat�ria deve ser selecionada
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para atribuir os valores de ix e iniciar as vari�veis profx, matx e salax
		{
			for (int z = 0; z < ix[tab].length; z++) // La�o de repeti��o para atribuir os valores de ix
			{
				ix[tab][z] = limiteaula[tab][z]; 
				profx[tab][z]="";
				matx[tab][z]="";
				salax[tab][z]="";
			}
		}
		try
		{
			for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para gerar o hor�rio de todas as turmas
			{
				int row = 1, colu = 1; // Atribui os valores iniciais de row e colu
				int volta=0; // Atribui o valor inicial de volta
				conta=0; // Atribui o valor inicial de conta
				for (int z = 0; z < ((tabelas[tab].getColumnCount() - 1) * (tabelas[tab].getRowCount() - 1)) / 3; z++) // La�o de repeti��o que ocorre em cada grupo de 3 c�lulas da tabela
				{
					
					volta = z; // Atribui o valor de volta como a posi��o atual
					boolean repete = true; // Se deve realizar a pesquisa novamente sem que o contador do la�o de repeti��o aumente
					if(matx[tab][z].equals("")){
					while(repete==true){ // enquanto n�o � necess�rio repetir sele��o de mat�ria
						materiarandom = (int) (Math.random() * num[tab]); // Seleciona uma mat�ria aleat�ria
						if(conta>=num[tab]){ // Se a conta for maior que o numero de mat�rias
							repete=false; // N�o repete
						}
						if(!materiasverificadas.contains(" "+String.valueOf(materiarandom)+" ")) // Se a mat�ria selecionada n�o foi selecionada anteriormente
						{
							repete=false; // N�o repete
							materiasverificadas=materiasverificadas+" "+materiarandom+" "; // Adiciona essa mat�ria na lista de mat�rias j� selecionadas
							conta++; // Aumenta a conta
						}
					}
					
					
						for (int mtab = tab - 1; mtab >= 0; mtab--) // La�o de repeti��o para cada tabela que j� foi gerada
						{
							if (z == -1) // Se z � igual a -1
							{
							}
							else
							{
								for (int rowx = 1; rowx < tabelas[mtab].getRowCount(); rowx++) // La�o de repeti��o para cada linha da tabela
								{
									if(repete==false){
									if (((Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >=
											Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
											&& Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
											|| (Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
											&& Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
											||((Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >= Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
													&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
													|| (Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
													&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
											) // Se a hora da linha for igual a hora da linha da outra tabela
										{
										if ((prof[tab][materiarandom].equals(tabelas[mtab].getValueAt(rowx, colu + 1)) && prof[tab][materiarandom].length()>0) || (sala[tab][materiarandom].equals(tabelas[mtab].getValueAt(rowx, colu + 2)) && sala[tab][materiarandom].length()>0)) // Se o professor da mat�ria selecionada � igual ao professor da mesma posi��o na outra tabela
										{
											repete = true; // Repete a sele��o de mat�ria
												if(conta>=num[tab]) // Se a conta for maior que num
											{
												matx[tab][z] = "";
												profx[tab][z] = "";
												salax[tab][z] = "";
												colu = colu + 3;
												materiasverificadas="";
												if (colu == 16)
												{
													colu = 1;
													row++;
												}
												conta = 0;
											}
											else
											{
												z = volta - 1;
											}
										}
									}
									if(prio[tab][materiarandom]==true && repete==false && ix[tab][materiarandom]>1){
										if(row+1<tabelas[tab].getRowCount()){
											if (((Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >= Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
													&& Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
													|| (Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
													&& Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
													||((Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >= Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
															&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
															|| (Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
															&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
													){
												if ((prof[tab][materiarandom].equals(tabelas[mtab].getValueAt(rowx, colu + 1)) && prof[tab][materiarandom].length()>0) || (sala[tab][materiarandom].equals(tabelas[mtab].getValueAt(rowx, colu + 2)) && sala[tab][materiarandom].length()>0)) // Se o professor da mat�ria selecionada � igual ao professor da mesma posi��o na outra tabela
												{
													repete = true;
													if(conta>=num[tab]) // Se a conta for maior que num
													{
														matx[tab][z] = "";
														profx[tab][z] = "";
														salax[tab][z] = "";
														colu = colu + 3;
														materiasverificadas="";
														if (colu == 16)
														{
															colu = 1;
															row++;
														}
														conta = 0;
													}
													else
													{
														z = volta - 1;
													}
												}
											}
										}else{
											repete = true;
											if(conta>=num[tab]) // Se a conta for maior que num
											{
												matx[tab][z] = "";
												profx[tab][z] = "";
												salax[tab][z] = "";
												colu = colu + 3;
												materiasverificadas="";
												if (colu == 16)
												{
													colu = 1;
													row++;
												}
												conta = 0;
											}
											else
											{
												z = volta - 1;
											}
										}
										
									}
								}
								}
							}
						}
						if(repete==false && ix[tab][materiarandom]>0){ // Se n�o precisa repetir e se o limite de aulas dessa mat�ria ainda n�o foi atingido
						if(colu==1){ // Se a coluna for 1, 4, 7, 10, 13, o dia � atribuido
							dia=0;
						}
						if(colu==4){
							dia=1;
						}
						if(colu==7){
							dia=2;
						}
						if(colu==10){
							dia=3;
						}
						if(colu==13){
							dia=4;
						}
						if(profdisp[tab][materiarandom][dia].equals("")){ // Se o professor n�o est� disponivel nesse dia
							repete=true;
								if(conta>=num[tab]) // Se a conta for maior ou igual ao num
							{
								matx[tab][z] = "";
								profx[tab][z] = "";
								salax[tab][z] = "";
								colu = colu + 3;
								materiasverificadas="";
								if (colu == 16)
								{
									colu = 1;
									row++;
								}
								conta = 0;
							}else{
							z=volta-1;
							}	
						}
						else{
							if(Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))<
								Integer.parseInt(profdisp[tab][materiarandom][dia].substring(0,5).replace(":", ""))
								|| Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))>
								Integer.parseInt(profdisp[tab][materiarandom][dia].substring(6,11).replace(":", ""))){ // Se o professor est� disponivel nessa hora
								repete=true;
									if(conta>=num[tab]) // Se a conta for maior ou igual ao num
								{
									matx[tab][z] = "";
									profx[tab][z] = "";
									salax[tab][z] = "";
									colu = colu + 3;
									materiasverificadas="";
									if (colu == 16)
									{
										colu = 1;
										row++;
									}
									conta = 0;
								}else{
								z=volta-1;
								}	
							}
							if(prio[tab][materiarandom]==true && ix[tab][materiarandom]>1 && repete==false){
								if(row+1<tabelas[tab].getRowCount()){
								if(Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))<
										Integer.parseInt(profdisp[tab][materiarandom][dia].substring(0,5).replace(":", ""))
										|| Integer.parseInt(tabelas[tab].getValueAt(row+1, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))>
										Integer.parseInt(profdisp[tab][materiarandom][dia].substring(6,11).replace(":", ""))){
									repete=true;
									if(conta>=num[tab]) // Se a conta for maior ou igual ao num
									{
										matx[tab][z] = "";
										profx[tab][z] = "";
										salax[tab][z] = "";
										colu = colu + 3;
										materiasverificadas="";
										if (colu == 16)
										{
											colu = 1;
											row++;
										}
										conta = 0;
									}else{
									z=volta-1;
									}
								}else{
									profx[tab][z+5] = prof[tab][materiarandom];
									salax[tab][z+5] = sala[tab][materiarandom];
									matx[tab][z+5] = mat[tab][materiarandom];
									ix[tab][materiarandom]--;
									t[tab]--;
									
								}
								}else {
									repete=true;
								if(conta>=num[tab]) // Se a conta for maior ou igual ao num
								{
									repete=true;
									matx[tab][z] = "";
									profx[tab][z] = "";
									salax[tab][z] = "";
									colu = colu + 3;
									materiasverificadas="";
									if (colu == 16)
									{
										colu = 1;
										row++;
									}
									conta = 0;
								}else{
								z=volta-1;
								}	
									
								}
							}
						}
						}

						if (repete == false) // Se n�o precisa repetir
						{
							if (ix[tab][materiarandom] > 0) // Se o limite de aulas da mat�ria selecionada n�o foi atingido, os valores das c�lulas da tabela s�o atribuidos
							{
								profx[tab][z] = prof[tab][materiarandom];
								salax[tab][z] = sala[tab][materiarandom];
								matx[tab][z] = mat[tab][materiarandom];
								materiasverificadas="";
								conta = 0;
								ix[tab][materiarandom]--;
								t[tab]--;
								colu = colu + 3;
								if (colu == 16) // Se a coluna for 16
								{
									colu = 1;
									row++;
								}
							}
							else if (t[tab] == 0) // Se todas as mat�rias foram colocadas, janelas ir�o preencher a tabela
							{
								matx[tab][z] = "";
								profx[tab][z] = "";
								salax[tab][z] = "";
								materiasverificadas="";
								conta = 0;
								colu = colu + 3;
								if (colu == 16) // Se a coluna for 16
								{
									colu = 1;
									row++;
								}
							}
							else
							{
								z = volta - 1;
							}
						}
					}else{
						colu = colu + 3;
						if (colu == 16) // Se a coluna for 16
						{
							colu = 1;
							row++;
						}
					}
				}
				
				int b = 0, c = 1, d = b + 15; // Vari�veis de posi��es da tabela
				for (int a = 0; a < ((tabelas[tab].getColumnCount() - 1) * (tabelas[tab].getRowCount() - 1)) / 3; a++) // La�o de repeti��o que coloca todos os valores nas c�lulas da tabela
				{
					b++;
					tabelas[tab].setValueAt(matx[tab][a], c, b);
					b++;
					tabelas[tab].setValueAt(profx[tab][a], c, b);
					b++;
					tabelas[tab].setValueAt(salax[tab][a], c, b);
					if (b == d) // Se b � igual a d
					{
						b = 0;
						c++;
						d = b + 15;
					}
					
					
				}
				
			}
			limpar(); // M�todo limpar
			for(int tab=0;tab<numtabela;tab++){ // La�o de repeti��o para cada tabela
				if(t[tab]!=0){ // Se o n�mero de mat�rias que devem ser inseridas na tabela n�o foi atingido, repete o m�todo gerar
					gerar();
				}
			}		
		}	
		catch (Exception e)
		{
			e.printStackTrace(); // Mostra o relat�rio de erros
		}
	}

	public void verificar() // M�todo que verifica as c�lulas da tabela
	{
		try
		{
		erro = false; // Se deu erro
		erro1 = false; // Se deu erro
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para cada tabela
		{
			int row = 1, colu = 1; // Coluna e linha da tabela
			for (int z = 0; z < ((tabelas[tab].getColumnCount() - 1) * (tabelas[tab].getRowCount() - 1)) / 3; z++) // La�o de repeti��o para grupo de 3 c�lulas da tabela
			{
				matx[tab][z] = tabelas[tab].getValueAt(row, colu).toString(); // Mat�ria da posi��o
				colu++;
				profx[tab][z] = tabelas[tab].getValueAt(row, colu).toString(); // Professor da pos��o
				for(int cont=0;cont<prof[tab].length;cont++){ // La�o de repeti��o para cada professor da turma
					if(profx[tab][z].equals(prof[tab][cont])){ // Se o professor da posi��o for igual ao professor
						profdispx[tab][z][0]=profdisp[tab][cont][0]; // O hor�rio de disponibilidade de cada dia do professor da posi��o ser� atribuido
						profdispx[tab][z][1]=profdisp[tab][cont][1];
						profdispx[tab][z][2]=profdisp[tab][cont][2];
						profdispx[tab][z][3]=profdisp[tab][cont][3];
						profdispx[tab][z][4]=profdisp[tab][cont][4];
					}
				}
				colu++;
				salax[tab][z] = tabelas[tab].getValueAt(row, colu).toString(); // Atribui a sala da posi��o
				colu++;
				if (colu == 16) // Se a coluna for 16
				{
					colu = 1;
					row++;
				}
			}
		}
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para cada tabela
		{
			int row = 1, colu = 1; // Linha e coluna da tabela
			for (int z = 0; z < ((tabelas[tab].getColumnCount() - 1) * (tabelas[tab].getRowCount() - 1)) / 3; z++) // La�o de repeti��o para cada grupo de 3 c�lulas da tabela
			{
				erro1=false; // N�o tem erro
				for (int mtab = tab - 1; mtab >= 0; mtab--) // La�o de repeti��o para cada tabela anterior da atual
				{
					
						for (int rowx = 1; rowx < tabelas[mtab].getRowCount(); rowx++) // La�o de repeti��o para cada coluna da tabela anterior
						{
							if (((Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >= Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
									&& Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
									|| (Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
									&& Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
									||((Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) >= Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))
											&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) < Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", "")))
											|| (Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) > Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", "")) 
											&& Integer.parseInt(tabelas[mtab].getValueAt(rowx, 0).toString().substring(6, 11).replace("-", "").replace(":", "")) <= Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))))
									) 
							{ // Se a hora da linha for igual a hora da linha da outra tabela
								if ((profx[tab][z].equals(tabelas[mtab].getValueAt(rowx, colu + 1).toString().replace("<html><div style='width:50px; background-color: #f25d5d'>", "").replace("</div></html>", "")) && profx[tab][z].length() > 0) || (salax[tab][z].equals(tabelas[mtab].getValueAt(rowx, colu + 2).toString().replace("<html><div style='width:50px; background-color: #f25d5d'>", "").replace("</div></html>", "")) && salax[tab][z].length() > 0)) // Se o professor da posi��o ou sala da posi��o for igual aos dois da mesma posi��o na tabela anterior
								{
									tabelas[tab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[tab].getValueAt(row, colu) + "</div></html>", row, colu); // Faz com que as c�lulas tenham fundo vermelho
									tabelas[tab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[tab].getValueAt(row, colu + 1) + "</div></html>", row, colu + 1);
									tabelas[tab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[tab].getValueAt(row, colu + 2) + "</div></html>", row, colu + 2);
									tabbedPane.setTitleAt(tab, "<html><div style='background-color: #f25d5d'>" + tabbedPane.getTitleAt(tab) + "</div></html>"); // Faz com que o titulo da tabbedpane fique com o fundo vermelho

									erro = true; // Deu erro
									erro1 = true; // Deu erro
									
									tabelas[mtab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[mtab].getValueAt(rowx, colu) + "</div></html>", rowx, colu); // Faz com que as c�lulas da tabela outra tenham fundo vermelho
									tabelas[mtab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[mtab].getValueAt(rowx, colu + 1) + "</div></html>", rowx, colu + 1);
									tabelas[mtab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[mtab].getValueAt(rowx, colu + 2) + "</div></html>", rowx, colu + 2);
									tabbedPane.setTitleAt(mtab, "<html><div style='background-color: #f25d5d'>" + tabbedPane.getTitleAt(mtab) + "</div></html>"); // Faz com que o titulo da tabbedpane da outra tabela fique com o fundo vermelho
								}

							}
								
						}
						

					
				}
				if(erro1==false && !tabelas[tab].getValueAt(row, colu+1).equals("")){ // Se n�o deu erro e o valor da tabela n�o � vazio
				if(colu==1){ // Define o dia de acordo com a coluna
					dia=0;
				}
				if(colu==4){
					dia=1;
				}
				if(colu==7){
					dia=2;
				}
				if(colu==10){
					dia=3;
				}
				if(colu==13){
					dia=4;
				}
				if(profdispx[tab][z][dia].equals("")){ // Se o professor n�o est� disponivel no dia
					tabelas[tab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[tab].getValueAt(row, colu + 1) + "</div></html>", row, colu + 1); // Deixa o fundo da c�lula do professor vermelho
					tabbedPane.setTitleAt(tab, "<html><div style='background-color: #f25d5d'>" + tabbedPane.getTitleAt(tab) + "</div></html>"); // Faz com que o titulo da tabbedpane fique com o fundo vermelho
					erro=true; // Deu erro
				}else if(Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "").replace(":", ""))<
						Integer.parseInt(profdispx[tab][z][dia].substring(0,5).replace(":", ""))
						|| Integer.parseInt(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "").replace(":", ""))>
						Integer.parseInt(profdispx[tab][z][dia].substring(6,11).replace(":", ""))){	// Se o professor n�o est� disponivel nessa hora
					tabelas[tab].setValueAt("<html><div style='width:50px; background-color: #f25d5d'>" + tabelas[tab].getValueAt(row, colu + 1) + "</div></html>", row, colu + 1); // Deixa o fundo da c�lula do professor vermelho
					tabbedPane.setTitleAt(tab, "<html><div style='background-color: #f25d5d'>" + tabbedPane.getTitleAt(tab) + "</div></html>"); // Faz com que o titulo da tabbedpane fique com o fundo vermelho
					erro=true; // Deu erro
				}
				}
					colu = colu + 3;
				if (colu == 16) // Se a coluna for 16
				{
					colu = 1;
					row++;
				}
			}
		}
		btnSalvarHorrio.setEnabled(!erro); // A disponibilidade do hor�rio � inversamente proporcional ao erro
	}
	catch (Exception e)
	{
		e.printStackTrace(); // Mostra o relat�rio de erros
	}
	}
	public void limpar() // M�todo para limpar o fundo das c�lulas que est�o com fundo vermelho
	{

		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para cada tabela
		{
			if (tabbedPane.getTitleAt(tab).toString().contains("<html><div style='background-color: #f25d5d'>")) // Verifica se o titulo da tabbedpane est� com fundo vermelho
			{
				String va = tabbedPane.getTitleAt(tab); // va � o titulo da tabbedpane
				va = va.replace("<html><div style='background-color: #f25d5d'>", ""); // Limpa o fundo
				va = va.replace("</div></html>", "");
				tabbedPane.setTitleAt(tab, va); // Torna o titulo da tabbedpane como va
			}
			for (int row = 1; row < tabelas[tab].getRowCount(); row++) // La�o de repeti��o para cada linha da tabela
			{
				for (int col = 1; col < tabelas[tab].getColumnCount(); col++) // La�o de repeti��o para cada coluna da tabela
				{
					if (tabelas[tab].getValueAt(row, col).toString().contains("<html><div style='width:50px; background-color: #f25d5d")) // Verifica se o fundo da c�lula est� vermelho
					{
						String va = tabelas[tab].getValueAt(row, col).toString(); // va � o texto da c�lula
						va = va.replace("<html><div style='width:50px; background-color: #f25d5d'>", ""); // Limpa o fundo
						va = va.replace("</div></html>", "");
						tabelas[tab].setValueAt(va, row, col); // Torna o texto da c�lula como va
					}
				}
			}
		}
	}

	public void carregar() // M�todo para carregar o hor�rio salvo do banco de dados
	{

		List<Aula> aula = new ArrayList<Aula>(); // Lista de aula
		List<Turma> turma = conexao.buscarTurmas(); // Lista de turma
		List<String> professor = conexao.buscarProfessor_Aula(); // Lista de professor
		int aux = 0; // Vari�vel de armazenamento
		for (int co = 0; co < turma.size(); co++) // La�o de repeti��o para atribuir os valores de idturma
		{
			idturma[co] = turma.get(co).getIdTurma();
		}
		aula = conexao.buscarAulas(); // Lista de aula
		Boolean achou = false; // Se achou a aula certa
		for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para cada tabela
		{
			int row = 1, colu = 1; // Linha e coluna da tabela
			for (int au = 0; au < aula.size(); au++) // La�o de repeti��o para cada aula
			{
				if (aula.get(au).getTurma().getIdTurma() == idturma[tab]) // Se id da turma da aula encontrada for o mesmo que o da tabela atual
				{
					if (colu == 16) // Se a coluna for 16
					{
						colu = 1;
						row++;
					}
					switch (colu) // Verifica o valor de colu que corresponde a cada dia
					{
					case 1:
					{
						if (aula.get(au).getDia().equals("SEGUNDA") && aula.get(au).getInicio().equals(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00"))) // Se for o dia da coluna e o hor�rio de inicio da aula for o mesmo que o da linha atual
						{
							tabelas[tab].setValueAt(aula.get(au).getMateria().getSigla(), row, colu); // Atribui a mat�ria na c�lula
							tabelas[tab].setValueAt(professor.get(au), row, colu + 1); // Atribui o professor na c�lula
							tabelas[tab].setValueAt(String.valueOf(aula.get(au).getSala().getIdSala()).replace(".0", ""), row, colu + 2); // Atribui a sala na c�lula
							achou = true; // Achou a aula certa
						}
						if (achou == false) // Se n�o achou a aula
						{
							tabelas[tab].setValueAt("", row, colu);
							tabelas[tab].setValueAt("", row, colu + 1);
							tabelas[tab].setValueAt("", row, colu + 2);
						}
						break;
					}
					case 4:
					{
						if (aula.get(au).getDia().equals("TER�A") && aula.get(au).getInicio().equals(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00")))
						{
							tabelas[tab].setValueAt(aula.get(au).getMateria().getSigla(), row, colu);
							tabelas[tab].setValueAt(professor.get(au), row, colu + 1);
							tabelas[tab].setValueAt(String.valueOf(aula.get(au).getSala().getIdSala()).replace(".0", ""), row, colu + 2);
							achou = true;
						}
						if (achou == false)
						{
							tabelas[tab].setValueAt("", row, colu);
							tabelas[tab].setValueAt("", row, colu + 1);
							tabelas[tab].setValueAt("", row, colu + 2);
						}
						break;
					}
					case 7:
					{
						if (aula.get(au).getDia().equals("QUARTA") && aula.get(au).getInicio().equals(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00")))
						{
							tabelas[tab].setValueAt(aula.get(au).getMateria().getSigla(), row, colu);
							tabelas[tab].setValueAt(professor.get(au), row, colu + 1);
							tabelas[tab].setValueAt(String.valueOf(aula.get(au).getSala().getIdSala()).replace(".0", ""), row, colu + 2);
							achou = true;
						}
						if (achou == false)
						{
							tabelas[tab].setValueAt("", row, colu);
							tabelas[tab].setValueAt("", row, colu + 1);
							tabelas[tab].setValueAt("", row, colu + 2);
						}
						break;
					}
					case 10:
					{
						if (aula.get(au).getDia().equals("QUINTA") && aula.get(au).getInicio().equals(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00")))
						{
							tabelas[tab].setValueAt(aula.get(au).getMateria().getSigla(), row, colu);
							tabelas[tab].setValueAt(professor.get(au), row, colu + 1);
							tabelas[tab].setValueAt(String.valueOf(aula.get(au).getSala().getIdSala()).replace(".0", ""), row, colu + 2);
							achou = true;
						}
						if (achou == false)
						{
							tabelas[tab].setValueAt("", row, colu);
							tabelas[tab].setValueAt("", row, colu + 1);
							tabelas[tab].setValueAt("", row, colu + 2);
						}
						break;
					}
					case 13:
					{
						if (aula.get(au).getDia().equals("SEXTA") && aula.get(au).getInicio().equals(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00")))
						{
							tabelas[tab].setValueAt(aula.get(au).getMateria().getSigla(), row, colu);
							tabelas[tab].setValueAt(professor.get(au), row, colu + 1);
							tabelas[tab].setValueAt(String.valueOf(aula.get(au).getSala().getIdSala()).replace(".0", ""), row, colu + 2);
							achou = true;
						}
						if (achou == false)
						{
							tabelas[tab].setValueAt("", row, colu);
							tabelas[tab].setValueAt("", row, colu + 1);
							tabelas[tab].setValueAt("", row, colu + 2);
						}
						break;
					}
					}
						if (achou == false) // Se n�o achou a aula
						{
							au--;
						}
					achou = false;
					colu = colu + 3;
				}
				aux = au;
			}
			if (colu == 16) // Se a coluna for 16
			{
				colu = 15;
			}
			for (aux = aux; colu != (tabelas[tab].getColumnCount() - 1) || row != (tabelas[tab].getRowCount() - 1); aux++) // La�o de repeti��o para cada espa�o restante da tabela
			{

				tabelas[tab].setValueAt("", row, colu); // Deixa o valor da c�lula como nada
				colu++;
				if (colu == 16) // Se a coluna for 16
				{
					colu = 1;
					row++;
				}
				tabelas[tab].setValueAt("", row, colu); // Deixa o valor da c�lula como nada
			}
		}
		limpar(); // Limpa os erros
		verificar(); // Verifica se tem erros
	}

	public HA()
	{

		setBounds(100, 100, 1024, 660);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		btnGerarHorario = new JButton("Gerar Hor\u00E1rio");

		JTextPane txt1 = new JTextPane();
		txt1.setEditable(false);
		txt1.setText("a");
		txt1.setBounds(241, 343, 43, 17);
		txt1.setBorder(BorderFactory.createLineBorder(Color.gray));
		txt1.setVisible(false);

		btnSalvarHorrio = new JButton("Salvar Hor\u00E1rio");

		JButton btnCarregarHorario = new JButton("Carregar Hor\u00E1rio");

		btnCarregarHorario.setBounds(818, 251, 120, 23);
		getContentPane().add(btnCarregarHorario);
		btnSalvarHorrio.setBounds(818, 211, 120, 23);
		getContentPane().add(btnSalvarHorrio);
		getContentPane().add(txt1);

		btnGerarHorario.setBounds(818, 171, 120, 23);
		getContentPane().add(btnGerarHorario);

		JLabel lblNewLabel_1 = new JLabel("Hor\u00E1rio Autom\u00E1tico");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setBounds(41, 39, 140, 14);
		getContentPane().add(lblNewLabel_1);

		JTextPane txt = new JTextPane();
		txt.setEditable(false);
		txt.setText("");
		txt.setBounds(241, 343, 43, 17);
		txt.setBorder(BorderFactory.createLineBorder(Color.gray));
		txt.setVisible(false);
		getContentPane().add(txt);

		JTextPane txt2 = new JTextPane();
		txt2.setEditable(false);
		txt2.setText("a");
		txt2.setBounds(241, 343, 43, 17);
		txt2.setBorder(BorderFactory.createLineBorder(Color.gray));
		txt2.setVisible(false);
		getContentPane().add(txt2);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent arg0)
			{

			}
		});
		tabbedPane.setBounds(64, 89, 710, 243);
		getContentPane().add(tabbedPane);
		
		JButton btnNewButton = new JButton("Relacionar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carregou=false;
				RelacionaProf_mat_turma_Sala relacionaptms = new RelacionaProf_mat_turma_Sala();
				relacionaptms.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				relacionaptms.setModal(true);
				relacionaptms.setVisible(true);
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		btnNewButton.setBounds(818, 289, 120, 23);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(HA.class.getResource("/FundoInformacao.png")));
		lblNewLabel.setBounds(0, 0, 1018, 611);

		getContentPane().add(lblNewLabel);

		turmas = conexao.buscarTurmas();
		for (int i = 0; i < numtabela; i++) // La�o de repeti��o para cada turma, adicionando labels, tabelas e panels para cada turma
		{
			Font font = lblNewLabel.getFont();
			Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
			panel[i] = new JPanel();
			panel[i].setBounds(0, 0, 704, 243);
			panel[i].setLayout(null);

			hora[i] = new JLabel();
			hora[i].setBounds(0, 0, 72, 20);
			hora[i].setHorizontalAlignment(SwingConstants.CENTER);
			hora[i].setText("HOR�RIO");
			hora[i].setFont(boldFont);
			hora[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(hora[i]);

			seg[i] = new JLabel();
			seg[i].setBounds(71, 0, 127, 20);
			seg[i].setHorizontalAlignment(SwingConstants.CENTER);
			seg[i].setText("SEGUNDA");
			seg[i].setFont(boldFont);
			seg[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(seg[i]);

			ter[i] = new JLabel();
			ter[i].setBounds(197, 0, 127, 20);
			ter[i].setHorizontalAlignment(SwingConstants.CENTER);
			ter[i].setText("TER�A");
			ter[i].setFont(boldFont);
			ter[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(ter[i]);

			qua[i] = new JLabel();
			qua[i].setBounds(323, 0, 127, 20);
			qua[i].setHorizontalAlignment(SwingConstants.CENTER);
			qua[i].setText("QUARTA");
			qua[i].setFont(boldFont);
			qua[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(qua[i]);

			qui[i] = new JLabel();
			qui[i].setBounds(449, 0, 128, 20);
			qui[i].setHorizontalAlignment(SwingConstants.CENTER);
			qui[i].setText("QUINTA");
			qui[i].setFont(boldFont);
			qui[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(qui[i]);

			sex[i] = new JLabel();
			sex[i].setBounds(576, 0, 128, 20);
			sex[i].setHorizontalAlignment(SwingConstants.CENTER);
			sex[i].setText("SEXTA");
			sex[i].setFont(boldFont);
			sex[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			panel[i].add(sex[i]);

			
			tabelas[i] = new JTable();
			tabelas[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
			List<Turma> turma = conexao.buscarTurmas(); // Lista de turma
					List<Horario> hora = conexao.buscarHorarios_Turma(turma.get(i)); // Lista dos horarios de aula da turma
					List<Horario> horareal=new ArrayList<Horario>(); // Lista de hor�rio de aula
					for(int ba=0;ba<hora.size();ba++){ // La�o de repeti��o para cada hora de aula da turma
						if(hora.get(ba).getTipo().equals("Aula")){ // Se for uma aula
							horareal.add(hora.get(ba)); // Adiciona o hor�rio de aula a horareal
						}
					}
					String[][] batotal = new String[horareal.size()+1][16]; // Vetor que ter� todas as linhas e colunas
					for(int ba=0;ba<=horareal.size();ba++){ // La�o de repeti��o para cada hor�rio de aula
						if(ba==0) // Se ba for 0
						{
							String bat_t[] = { "", "<html><b>DISC</center></b></html>", "<html><b>PROF</b></html>", "<html><b>SALA</b></html>", "<html><b>DISC</b></html>", "<html><b>PROF</b></html>", "<html><b>SALA</b></html>", "<html><b>DISC</b></html>", "<html><b>PROF</b></html>", "<html><b>SALA</b></html>", "<html><b>DISC</b></html>", "<html><b>PROF</b></html>", "<html><b>SALA</b></html>", "<html><b>DISC</b></html>", "<html><b>PROF</b></html>", "<html><b>SALA</b></html>" }; // Adiciona o DISC PROF SALA para cada dia da semana
							batotal[ba] = bat_t; // Adiciona essa linha ao batotal
						}
						else
						{
							String bat[]= { horareal.get(ba-1).getEntrada().toString().substring(0,5)+"-"+horareal.get(ba-1).getSaida().toString().substring(0, 5), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" }; // Adiciona a linha que a hora da aula e todos os espa�os de DISC PROF e SALA
							batotal[ba] = bat; // Adiciona essa linha ao batotal
						}						
					}
					tabelas[i].setModel(new DefaultTableModel(batotal, new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" } // Faz com que o modelo da tabela tenha as linhas e as colunas do batotal

					)
					{

						private static final long serialVersionUID = 1L;

						@Override
						public boolean isCellEditable(int row, int column)
						{

							return false; // N�o deixa a c�lula editavel
						};
					});
				



			tabelas[i].setBounds(0, 20, 704, 250);
			panel[i].add(tabelas[i]);

			tabelas[i].addMouseMotionListener(new MouseMotionAdapter()
			{

				@Override
				public void mouseDragged(MouseEvent arg0) // Ao clicar e arrastar com o mouse
				{
				if(((JTable) arg0.getSource()).getSelectedColumn() != -1){
					if (salvando == false) // Se n�o est� salvando
					{
						if (((JTable) arg0.getSource()).getSelectedColumn() == 0 || ((JTable) arg0.getSource()).getSelectedRow() == 0) // Se a linha ou a coluna forem 0
						{
							mouseapertado = true;
							travarmouse = true;
						}
						else if (travarmouse == true && txt.isVisible() == true) // Se o mouse tem que ser travado e o textfield est� visivel
						{
							travarmouse = false;
						}
						txt.setLocation(arg0.getX() + 10, arg0.getY() + 120); // Move o txt para a localiza��o do mouse
						txt1.setLocation(arg0.getX() + 53, arg0.getY() + 120);
						txt2.setLocation(arg0.getX() + 96, arg0.getY() + 120);
						if (mouseapertado == false) // Se o mouse n�o est� apertado
						{
							limpar(); // Limpa os erros
							txt.setVisible(true); // Deixa os textos visiveis
							txt1.setVisible(true);
							txt2.setVisible(true);
							if (((JTable) arg0.getSource()).getSelectedColumn() == 1 || ((JTable) arg0.getSource()).getSelectedColumn() == 4 || ((JTable) arg0.getSource()).getSelectedColumn() == 7 || ((JTable) arg0.getSource()).getSelectedColumn() == 10 || ((JTable) arg0.getSource()).getSelectedColumn() == 13) // Atribui as colunas e as linhas que foram clicadas
							{
								col = ((JTable) arg0.getSource()).getSelectedColumn(); // Coluna da mat�ria
								lin = ((JTable) arg0.getSource()).getSelectedRow(); // Linha
								col1 = ((JTable) arg0.getSource()).getSelectedColumn() + 1; // Coluna do professor 
								col2 = ((JTable) arg0.getSource()).getSelectedColumn() + 2; // Coluna da sala
							}
							if (((JTable) arg0.getSource()).getSelectedColumn() == 2 || ((JTable) arg0.getSource()).getSelectedColumn() == 5 || ((JTable) arg0.getSource()).getSelectedColumn() == 8 || ((JTable) arg0.getSource()).getSelectedColumn() == 11 || ((JTable) arg0.getSource()).getSelectedColumn() == 14)
							{
								col = ((JTable) arg0.getSource()).getSelectedColumn() - 1;
								lin = ((JTable) arg0.getSource()).getSelectedRow();
								col1 = ((JTable) arg0.getSource()).getSelectedColumn();
								col2 = ((JTable) arg0.getSource()).getSelectedColumn() + 1;
							}
							if (((JTable) arg0.getSource()).getSelectedColumn() == 3 || ((JTable) arg0.getSource()).getSelectedColumn() == 6 || ((JTable) arg0.getSource()).getSelectedColumn() == 9 || ((JTable) arg0.getSource()).getSelectedColumn() == 12 || ((JTable) arg0.getSource()).getSelectedColumn() == 15)
							{
								col = ((JTable) arg0.getSource()).getSelectedColumn() - 2;
								lin = ((JTable) arg0.getSource()).getSelectedRow();
								col1 = ((JTable) arg0.getSource()).getSelectedColumn() - 1;
								col2 = ((JTable) arg0.getSource()).getSelectedColumn();
							}

							txt.setText(((JTable) arg0.getSource()).getValueAt(lin, col).toString()); // Deixa os textos como os valores clicados
							txt1.setText(((JTable) arg0.getSource()).getValueAt(lin, col1).toString());
							txt2.setText(((JTable) arg0.getSource()).getValueAt(lin, col2).toString());
							mouseapertado = true; // O mouse est� apertado
						}
					}
				}
				}
			});

			tabelas[i].addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					if (mouseapertado == true) // Se o mouse est� apertado
					{
						if (travarmouse == true) // Se o mouse deve fazer nada
						{
							mouseapertado = false; // O mouse n�o est� mais apertado
							txt.setVisible(false); // Deixa os textos invisiveis
							txt1.setVisible(false);
							txt2.setVisible(false);
							travarmouse = false; // O mouse n�o precisa ser travado
							verificar(); // Verifica por erros
						}
						else
						{
							int co = 0, co1 = 0, co2 = 0, li = 0; // Linha e colunas da mat�ria, professor e da sala

							if (((JTable) arg0.getSource()).getSelectedColumn() == 1 || ((JTable) arg0.getSource()).getSelectedColumn() == 4 || ((JTable) arg0.getSource()).getSelectedColumn() == 7 || ((JTable) arg0.getSource()).getSelectedColumn() == 10 || ((JTable) arg0.getSource()).getSelectedColumn() == 13) // Se for uma dessas colunas
							{
								co = ((JTable) arg0.getSource()).getSelectedColumn(); // Coluna do lugar da mat�ria onde se soltou o mouse
								li = ((JTable) arg0.getSource()).getSelectedRow(); // Linha do lugar onde se soltou o mouse
								co1 = ((JTable) arg0.getSource()).getSelectedColumn() + 1; // Coluna do lugar do professor onde se soltou o mouse
								co2 = ((JTable) arg0.getSource()).getSelectedColumn() + 2; // Coluna do lugar da sala onde se soltou o mouse
							}
							if (((JTable) arg0.getSource()).getSelectedColumn() == 2 || ((JTable) arg0.getSource()).getSelectedColumn() == 5 || ((JTable) arg0.getSource()).getSelectedColumn() == 8 || ((JTable) arg0.getSource()).getSelectedColumn() == 11 || ((JTable) arg0.getSource()).getSelectedColumn() == 14)
							{
								co = ((JTable) arg0.getSource()).getSelectedColumn() - 1;
								li = ((JTable) arg0.getSource()).getSelectedRow();
								co1 = ((JTable) arg0.getSource()).getSelectedColumn();
								co2 = ((JTable) arg0.getSource()).getSelectedColumn() + 1;
							}
							if (((JTable) arg0.getSource()).getSelectedColumn() == 3 || ((JTable) arg0.getSource()).getSelectedColumn() == 6 || ((JTable) arg0.getSource()).getSelectedColumn() == 9 || ((JTable) arg0.getSource()).getSelectedColumn() == 12 || ((JTable) arg0.getSource()).getSelectedColumn() == 15)
							{
								co = ((JTable) arg0.getSource()).getSelectedColumn() - 2;
								li = ((JTable) arg0.getSource()).getSelectedRow();
								co1 = ((JTable) arg0.getSource()).getSelectedColumn() - 1;
								co2 = ((JTable) arg0.getSource()).getSelectedColumn();
							}
							String texto = ((JTable) arg0.getSource()).getValueAt(li, co).toString(); // Guarda o texto da mat�ria onde se soltou o mouse 
							String texto1 = ((JTable) arg0.getSource()).getValueAt(li, co1).toString(); // Guarda o texto do professor onde se soltou o mouse
							String texto2 = ((JTable) arg0.getSource()).getValueAt(li, co2).toString(); // Guarda o texto da sala onde se soltou o mouse
							((JTable) arg0.getSource()).setValueAt(((JTable) arg0.getSource()).getValueAt(lin, col).toString(), li, co); // Troca os professores, as mat�rias e as salas em que se clicou e onde se soltou de lugar
							((JTable) arg0.getSource()).setValueAt(((JTable) arg0.getSource()).getValueAt(lin, col1).toString(), li, co1);
							((JTable) arg0.getSource()).setValueAt(((JTable) arg0.getSource()).getValueAt(lin, col2).toString(), li, co2);
							((JTable) arg0.getSource()).setValueAt(texto, lin, col);
							((JTable) arg0.getSource()).setValueAt(texto1, lin, col1);
							((JTable) arg0.getSource()).setValueAt(texto2, lin, col2);
							mouseapertado = false; // O mouse n�o est� apertado
							txt.setVisible(false); // Deixa os textfields invisiveis
							txt1.setVisible(false);
							txt2.setVisible(false);
							verificar(); // Verifica por erros
						}
					}
				}
			});
			tabelas[i].getTableHeader().setReorderingAllowed(false); // N�o deixa as colunas serem mov�veis
			tabelas[i].getTableHeader().setResizingAllowed(false); // N�o permite alterar o tamanho das colunas
			tabelas[i].setRowSelectionAllowed(false);
			tabelas[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelas[i].getColumnModel().getColumn(0).setPreferredWidth(45);
			tabelas[i].getColumnModel().getColumn(0).setMinWidth(45);
			for (int co = 1; co < tabelas[i].getColumnCount(); co++) // La�o de repeti��o para cada coluna da tabela
			{
				tabelas[i].getColumnModel().getColumn(co).setPreferredWidth(10);
				tabelas[i].getColumnModel().getColumn(co).setMinWidth(10);
			}
			tabbedPane.addTab(turmas.get(i).getGrau() + turmas.get(i).getClasse() + " - " + turmas.get(i).getCurso()+" "+turmas.get(i).getPeriodo(), panel[i]); // Adiciona o hor�rio na tabbedpane com o titulo como o nome da turma, o seu curso e o seu per�odo

		}
		btnGerarHorario.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				btnGerarHorario.setEnabled(false); // Deixa o bot�o indisponivel
				btnSalvarHorrio.setEnabled(false); // Deixa o bot�o salvar hor�rio indisponivel 
				btnCarregarHorario.setEnabled(false); // Deixa o bot�o carregar hor�rio indisponivel
				btnGerarHorario.setText("Gerando..."); // Deixa o texto do bot�o como gerando
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() // Worker
				{

					@Override
					protected Void doInBackground() throws Exception // Realiza em segundo plano
					{

						if (!carregou) getDados(); // Se n�o pegou os dados do banco de dados, ele pega

						return null;
					}

					protected void done() // Ao acabar o segundo plano
					{

						try
						{
							gerar(); // Gera o hor�rio
							btnGerarHorario.setEnabled(true); // Deixa os bot�es disponiveis
							btnSalvarHorrio.setEnabled(true);
							btnCarregarHorario.setEnabled(true);
							btnGerarHorario.setText("Gerar Hor�rio"); // Muda o texto de volta
						}
						catch (Exception e)
						{
							e.printStackTrace(); // Mostra o relat�rio de erros
						}
					};

				};
				worker.execute(); // Executa o worker

			}
		});

		btnSalvarHorrio.addActionListener(new ActionListener() // Bot�o salvar hor�rio
		{

			public void actionPerformed(ActionEvent arg0)
			{

				btnSalvarHorrio.setEnabled(false); // Deixa os bot�es indisponiveis
				btnCarregarHorario.setEnabled(false);
				btnGerarHorario.setEnabled(false);
				btnSalvarHorrio.setText("Salvando...");
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() // Worker
				{

					@Override
					protected Void doInBackground() throws Exception // Realiza em segundo plano
					{

						salvando = true; // Est� salvando
						Aula aula = new Aula(); // Inst�ncia a classe aula
						try
						{
							conexao.limpar(); // Limpa as tabelas aula e aulas_professores
							limpar(); // Limpa os erros
							int idaula = 1; // Id da aula
							for (int tab = 0; tab < numtabela; tab++) // La�o de repeti��o para cada tabela
							{
								int row = 1, colu = 1; // Linha e coluna
								for (int au = 0; au < ((tabelas[tab].getColumnCount() - 1) * (tabelas[tab].getRowCount() - 1)); au++) // La�o de repeti��o para cada c�lula da tabela
								{
									switch (colu)
									{
									case 1: // Caso colu seja 1
									{
										aula.setDia("SEGUNDA"); // O dia � segunda
										aula.setMateria(conexao.buscarMaterias(tabelas[tab].getValueAt(row, colu).toString())); // A mat�ria � o texto da c�lula atual
										break; // Para o switch
									}
									case 3:
									{
										if (!tabelas[tab].getValueAt(row, colu).equals("")) // Se o texto da c�lula n�o for vazio
										{
											aula.setIdAula(idaula);  // O id da aula � o idaula
											aula.setSala(conexao.buscarSalas(tabelas[tab].getValueAt(row, colu).toString())); // Deixa a sala como o texto da c�lula atual
											conexao.inserir(aula); // Insere a aula na tabela aula do banco de dados
											conexao.inserir(aula,conexao.buscarProfessores(tabelas[tab].getValueAt(row, colu - 1).toString(), true)); // Insere a aula do professor na tabela aulas_professores do banco de dados
											idaula++;
										}
										break;
									}
									case 4:
									{
										aula.setDia("TER�A");
										aula.setMateria(conexao.buscarMaterias(tabelas[tab].getValueAt(row, colu).toString()));
										break;
									}
									case 6:
									{

										if (!tabelas[tab].getValueAt(row, colu).equals(""))
										{
											aula.setIdAula(idaula);
											aula.setSala(conexao.buscarSalas(tabelas[tab].getValueAt(row, colu).toString()));
											conexao.inserir(aula);
											conexao.inserir(aula,conexao.buscarProfessores(tabelas[tab].getValueAt(row, colu - 1).toString(), true));
											idaula++;
										}
										break;
									}
									case 7:
									{
										aula.setDia("QUARTA");
										aula.setMateria(conexao.buscarMaterias(tabelas[tab].getValueAt(row, colu).toString()));
										break;
									}
									case 9:
									{
										if (!tabelas[tab].getValueAt(row, colu).equals(""))
										{
											aula.setIdAula(idaula);
											aula.setSala(conexao.buscarSalas(tabelas[tab].getValueAt(row, colu).toString()));
											conexao.inserir(aula);
											conexao.inserir(aula,conexao.buscarProfessores(tabelas[tab].getValueAt(row, colu - 1).toString(), true));
											idaula++;
										}
										break;
									}
									case 10:
									{
										aula.setDia("QUINTA");
										aula.setMateria(conexao.buscarMaterias(tabelas[tab].getValueAt(row, colu).toString()));
										break;
									}
									case 12:
									{
										if (!tabelas[tab].getValueAt(row, colu).equals(""))
										{
											aula.setIdAula(idaula);
											aula.setSala(conexao.buscarSalas(tabelas[tab].getValueAt(row, colu).toString()));
											conexao.inserir(aula);
											conexao.inserir(aula,conexao.buscarProfessores(tabelas[tab].getValueAt(row, colu - 1).toString(), true));
											idaula++;
										}
										break;
									}
									case 13:
									{
										aula.setDia("SEXTA");
										aula.setMateria(conexao.buscarMaterias(tabelas[tab].getValueAt(row, colu).toString()));
										break;
									}
									case 15:
									{
										if (!tabelas[tab].getValueAt(row, colu).equals(""))
										{
											aula.setIdAula(idaula);
											aula.setSala(conexao.buscarSalas(tabelas[tab].getValueAt(row, colu).toString()));
											conexao.inserir(aula);
											conexao.inserir(aula,conexao.buscarProfessores(tabelas[tab].getValueAt(row, colu - 1).toString(), true));
											idaula++;
										}

										break;
									}

									}
									aula.setInicio(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(0, 5).replace("-", "") + ":00")); // Deixa o inicio da aula como o inicio da hora da linha da c�lula
									aula.setFim(Time.valueOf(tabelas[tab].getValueAt(row, 0).toString().substring(6, 11).replace("-", "") + ":00")); // Deixa o fim da aula como o fim hora da linha da c�lula
									aula.setTurma(conexao.buscarTurmas(idturma[tab])); // Deixa a turma da aula como a turma atual
									colu++;
									if (colu == 16)
									{
										colu = 1;
										row++;
									}
								}
							}
							verificar(); // Verifica por erros
							AcaoRecente a =new AcaoRecente();
							a.setFuncionario(FramePai.usuarioatual);
							a.setAcao("Hor�rio salvo");
							conexao.inserir(a);
							FramePai.a.add(a);
							Principal.AtualizarAcoes();//insere nas a��es recentes
						}
						catch (Exception e)
						{
							e.printStackTrace(); // Mostra o relat�rio de erros
						}
						return null;
					}

					protected void done()
					{

						try
						{
							btnSalvarHorrio.setEnabled(true); // Deixa os bot�es disponiveis
							btnCarregarHorario.setEnabled(true);
							btnGerarHorario.setEnabled(true);
							btnSalvarHorrio.setText("Salvar Hor�rio"); // Muda o texto de volta
							salvando = false; // N�o est� salvando
						}
						catch (Exception e)
						{
							e.printStackTrace(); // Mostra o relat�rio de erros
						}
					};

				};
				worker.execute(); // Executa o worker

			}
		});

		btnCarregarHorario.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				btnCarregarHorario.setEnabled(false); // Deixa os bot�es indisponiveis
				btnSalvarHorrio.setEnabled(false);
				btnGerarHorario.setEnabled(false);
				btnCarregarHorario.setText("Carregando..."); // Muda o texto para carregando
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() // worker
				{

					@Override
					protected Void doInBackground() throws Exception // Faz em segundo plano
					{

						carregar(); // Carrega o hor�rio do banco de dados

						return null;
					}

					protected void done()
					{

						try
						{
							btnCarregarHorario.setEnabled(true); // Deixa os bot�es disponiveis
							btnSalvarHorrio.setEnabled(true);
							btnGerarHorario.setEnabled(true);
							btnCarregarHorario.setText("Carregar Hor�rio"); // Muda o texto de volta
							verificar();
						}
						catch (Exception e)
						{
							e.printStackTrace(); // Mostra o relat�rio de erros
						}
					};

				};
				worker.execute(); // executa o worker
			}
		});
		getDados(); // Pega os dados do banco de dados
		carregar(); // Carrega o hor�rio do banco de dados
	}
}