package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import bd.Conexao;
import model.AcaoRecente;
import model.Materia;
import model.Turma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Color;

public class RelacionaMateriaTurma extends JDialog {

	private JPanel contentPane;
	private Conexao conexao=Login.conexao;
	public List<Materia> lista_mat;
	public List<Turma> lista_turma;
	private JTextField limite;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					RelacionaMateriaTurma frame = new RelacionaMateriaTurma();
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Boolean verificarnumerico (String a){
		Boolean c=false;
		int d;
		try{
			
			d=Integer.parseInt(a);  //Tenta transforma a variavel em integer.
			c=true;                 //Devolve true se conseguir.
		}catch(Exception e){
			c=false;      // Devolve false se não conseguir.
			
		}finally{
		return c;  //Retorna o resultado.
		}
	}
	public RelacionaMateriaTurma() {
		setTitle("Relacionar");
		setBounds(100, 100, 643, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Não", "Sim"}));
		comboBox.setBounds(141, 153, 46, 18);
		contentPane.add(comboBox);
		
		
		lista_mat=conexao.buscarMaterias();
		lista_turma=conexao.buscarTurmas();
		
		JComboBox comboBoxmat = new JComboBox();
		comboBoxmat.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma Mat\u00E9ria..."}));
		comboBoxmat.setBounds(141, 72, 124, 18);	  // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista_mat.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  comboBoxmat.addItem(lista_mat.get(controle).getNome());  // Inseri dados na combobox.
		  }
		contentPane.add(comboBoxmat);
		
		JLabel lblMateria = new JLabel("Materia: ");
		lblMateria.setBounds(62, 72, 46, 18);
		contentPane.add(lblMateria);
		
		JComboBox comboBoxturma = new JComboBox();
		comboBoxturma.setBounds(141, 99, 124, 18);
		comboBoxturma.addItem("Selecione uma Turma...");   	  // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista_turma.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  int grau;
			   String curso;
			   String classe;
			   grau=lista_turma.get(controle).getGrau();
			   curso=lista_turma.get(controle).getCurso();
			   classe=lista_turma.get(controle).getClasse();	
			  comboBoxturma.addItem(grau+classe+" "+curso);  // Inseri dados na combobox.
		  }
		contentPane.add(comboBoxturma);
		
		JLabel lblTurma = new JLabel("Turma: ");
		lblTurma.setBounds(62, 99, 46, 18);
		contentPane.add(lblTurma);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxmat.getSelectedItem().equals("Selecione um Professor..."))                            //Verifica a seleção.
		  		{
		  			JOptionPane.showMessageDialog(null, "Opção de professor invalida");
		  		}
		  		else
		  		{
		  			
		  			if(comboBoxturma.getSelectedItem().equals("Selecione uma Matéria..."))                          //Verifica a seleção.
		  			{
		  				JOptionPane.showMessageDialog(null, "Opção de materia invalida");
		  			}
		  			else
		  			{
				int a=-1;
  		  		for(int i=0;i<lista_mat.size();i++)
  				  {
  		  			if(lista_mat.get(i).getNome().equals(comboBoxmat.getSelectedItem().toString())) //Obtêm o id da lista que confere com a seleção.
  		  			{
  		  				a=i;
  				    }
  		  		  }
  		  		int b=-1;
  		  		for(int i=0;i<lista_turma.size();i++)
  				  {
  		  			if(lista_turma.get(i).getGrau()==Integer.parseInt(comboBoxturma.getSelectedItem().toString().substring(0, 1)) && 
  		  					lista_turma.get(i).getClasse().equals(comboBoxturma.getSelectedItem().toString().substring(1, 2)) && 
  		  					lista_turma.get(i).getCurso().equals(comboBoxturma.getSelectedItem().toString().substring(3, comboBoxturma.getSelectedItem().toString().length())))
  		  					{
  		  				
  				               b=i;
  				               
  				             
  				             }
  		  		   }
  		  	    
  		  		if(a!=-1&&b!=-1&&verificarnumerico(limite.getText())) //Caso tudo estaja preenchido corretamente faz as seguintes funções.
  		  		{
  		  	
  		  		    conexao.inserir(lista_mat.get(a), lista_turma.get(b), Integer.parseInt(limite.getText()), comboBox.getSelectedIndex());  //Insere no banco de dados a relação turma e materia e indicando a prioridade (aulas duplas) e limites de aulas.
  		  		AcaoRecente a1 =new AcaoRecente();
				a1.setFuncionario(FramePai.usuarioatual);
				a1.setAcao("Relação entre "+lista_mat.get(a).getNome()+" e "+comboBoxturma.getSelectedItem().toString()+" Adicionada");
				conexao.inserir(a1);
				FramePai.a.add(a1);
				Principal.AtualizarAcoes();//insere nas ações recentes
  		  		    JOptionPane.showMessageDialog(null,"Relação realizada com sucesso");
    				setVisible(false);
    				
  		  		}
		  			}
		  		}
			}
		});
		
		btnNewButton.setBounds(105, 200, 89, 23);
		contentPane.add(btnNewButton);
		
		limite = new JTextField();
		limite.setBounds(141, 126, 46, 18);
		contentPane.add(limite);
		limite.setColumns(10);
		
		
		
		JLabel label = new JLabel("Turma: ");
		label.setBounds(394, 129, 46, 18);
		contentPane.add(label);
		
		JLabel lblMatria = new JLabel("Mat\u00E9ria: ");
		lblMatria.setBounds(394, 86, 46, 18);
		contentPane.add(lblMatria);
		
		
		
		comboBoxmat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<Materia> lista_mat = conexao.buscarMaterias();
		  		List<Turma> lista_turma = conexao.buscarTurmas();
		  		List<Turma> lista_turmav;
		  		int a=-1;
				  for(int controle=0;controle<lista_mat.size();controle++)
				  {
					 if(lista_mat.get(controle).getNome().equals(comboBoxmat.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
					 {
					  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
					 }
				  }
				  
		  		 lista_turmav=conexao.buscarMaterias_Turmas(lista_mat.get(a));                                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
	  		
		  		 
				  for(int p =0;p<lista_turmav.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
				  {
					  for(int controle=0;controle<lista_turma.size();controle++)                              //Controle do tamanho da lista_materia para inserção.
					  {
					  					                                                                        
						   if(lista_turma.get(controle).getIdTurma()==lista_turmav.get(p).getIdTurma()) //Seleciona as materias e verifica se fazem parte da lista_materiav com base no id deles e do professor selecionado.
						   {		
							   int grau;
							   String curso;
							   String classe;
							   grau=lista_turmav.get(p).getGrau();
							   curso=lista_turmav.get(p).getCurso();
							   classe=lista_turmav.get(p).getClasse();		
							   
							  
							   comboBoxturma.removeItem(grau+classe+" "+curso);

							 															 		  				
						   }		 			  						
					  }
				  }
				  
				  
				  comboBoxmat.setEnabled(false);
			}
		});
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		JComboBox comboBoxturma2 = new JComboBox();
		comboBoxturma2.setBounds(438, 129, 124, 18);
		comboBoxturma2.addItem("Selecione uma Turma...");
		contentPane.add(comboBoxturma2);
		
		
		JComboBox comboBoxmat2 = new JComboBox();
		comboBoxmat2.setBounds(438, 86, 124, 18);
		comboBoxmat2.addItem("Selecione uma Matéria...");
		 for(int controle=0;controle<lista_mat.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  comboBoxmat2.addItem(lista_mat.get(controle).getNome());  // Inseri dados na combobox.
		  }
		contentPane.add(comboBoxmat2);
		
		JButton btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxmat2.getSelectedItem().equals("Selecione um Professor..."))                            //Verifica a seleção.
		  		{
		  			JOptionPane.showMessageDialog(null, "Opção de professor invalida");
		  		}
		  		else
		  		{
		  			
		  			if(comboBoxturma2.getSelectedItem().equals("Selecione uma Matéria..."))                          //Verifica a seleção.
		  			{
		  				JOptionPane.showMessageDialog(null, "Opção de materia invalida");
		  			}
		  			else
		  			{
		  				if(JOptionPane.showConfirmDialog(RelacionaMateriaTurma.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
						{
				int a=-1;
  		  		for(int i=0;i<lista_mat.size();i++)
  				  {
  		  			if(lista_mat.get(i).getNome().equals(comboBoxmat2.getSelectedItem().toString())) //Obtêm o id da lista que confere com a seleção.
  		  			{
  		  				a=i;
  				    }
  		  		  }
  		  		int b=-1;
  		  		for(int i=0;i<lista_turma.size();i++)
  				  {
  		  			if(lista_turma.get(i).getGrau()==Integer.parseInt(comboBoxturma2.getSelectedItem().toString().substring(0, 1)) && 
  		  					lista_turma.get(i).getClasse().equals(comboBoxturma2.getSelectedItem().toString().substring(1, 2)) && 
  		  					lista_turma.get(i).getCurso().equals(comboBoxturma2.getSelectedItem().toString().substring(3, comboBoxturma2.getSelectedItem().toString().length())))
  		  					{
  		  				
  				               b=i;
  				               
  				             
  				             }
  		  		   }
  		  	    
  		  		if(a!=-1&&b!=-1) //Caso tudo estaja preenchido corretamente faz as seguintes funções.
  		  		{
  		  	
  		  		    conexao.Excluir(lista_mat.get(a), lista_turma.get(b));
  		  		AcaoRecente a1 =new AcaoRecente();
				a1.setFuncionario(FramePai.usuarioatual);
				a1.setAcao("Relação entre "+lista_mat.get(a).getNome()+" e "+comboBoxturma2.getSelectedItem().toString()+" Excluida");
				conexao.inserir(a1);
				FramePai.a.add(a1);
				Principal.AtualizarAcoes();//insere nas ações recentes
  		  		    JOptionPane.showMessageDialog(null,"Relação excluida com sucesso");
    				setVisible(false);
    				
  		  		}
		  			}}
		  		}
			}
		});
		btnNewButton_1.setBounds(438, 200, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblatribuaMateriasPara = new JLabel("<html><center>Atribua mat\u00E9rias para uma turma para que o hor\u00E1rio autom\u00E1tico funcione.</center></html>");
		lblatribuaMateriasPara.setForeground(Color.WHITE);
		lblatribuaMateriasPara.setBounds(46, 1, 219, 68);
		contentPane.add(lblatribuaMateriasPara);
		
		JLabel lblexcluaRelaesFeitas = new JLabel("<html><center>Exclua rela\u00E7\u00F5es feitas anteriormente.</center></html>");
		lblexcluaRelaesFeitas.setForeground(Color.WHITE);
		lblexcluaRelaesFeitas.setBounds(394, 3, 219, 68);
		contentPane.add(lblexcluaRelaesFeitas);
		
		JLabel lblAulasDuplas = new JLabel("Aulas duplas:");
		lblAulasDuplas.setBounds(62, 153, 72, 18);
		contentPane.add(lblAulasDuplas);
		
		JLabel lblNewLabel_1 = new JLabel("Limite: ");
		lblNewLabel_1.setBounds(62, 126, 46, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(RelacionaMateriaTurma.class.getResource("/fundoRela\u00E7\u00E3o2.png")));
		lblNewLabel.setBounds(0, 1, 627, 261);
		contentPane.add(lblNewLabel);
				
		
		comboBoxmat2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				List<Materia> lista_mat = conexao.buscarMaterias();
		  		List<Turma> lista_turma = conexao.buscarTurmas();
		  		List<Turma> lista_turmav;
		  		int a=-1;
				  for(int controle=0;controle<lista_mat.size();controle++)
				  {
					 if(lista_mat.get(controle).getNome().equals(comboBoxmat2.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
					 {
					  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
					 }
				  }
				  
		  		 lista_turmav=conexao.buscarMaterias_Turmas(lista_mat.get(a));                                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
				
		  		 
				  for(int p =0;p<lista_turmav.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
				  {
					  for(int controle=0;controle<lista_turma.size();controle++)                              //Controle do tamanho da lista_materia para inserção.
					  {
					  					                                                                        
						   if(lista_turma.get(controle).getIdTurma()==lista_turmav.get(p).getIdTurma()) //Seleciona as materias e verifica se fazem parte da lista_materiav com base no id deles e do professor selecionado.
						   {		
							   int grau;
							   String curso;
							   String classe;
							   grau=lista_turmav.get(p).getGrau();
							   curso=lista_turmav.get(p).getCurso();
							   classe=lista_turmav.get(p).getClasse();		
							   
							  
							   comboBoxturma2.addItem(grau+classe+" "+curso);

							 															 		  				
						   }		 			  						
					  }
				  }
				  
				  
				  comboBoxmat2.setEnabled(false);
				
			}
		});
		
		
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		
		}
}
