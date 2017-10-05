package Formularios;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.AcaoRecente;
import model.Materia;
import model.Professor;
import bd.Conexao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.Expression;

import javax.swing.JTextField;

import java.awt.Color;

public class RelacionaMateriaProfessor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Conexao conexao = Login.conexao;
	private List<Materia> lista_materia;
	private List<Professor> lista;
	private List<Materia> lista_matex;
	private List<Professor> lista_proex;
	private List<Materia> lista_materiav;
	private List<Materia> lista_materiavex;
	public static String prof_selecionado;
	public static String mat_selecionada;
	public static String profex_selecionado;
	public static String matex_selecionada;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			RelacionaMateriaProfessor dialog = new RelacionaMateriaProfessor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public RelacionaMateriaProfessor() {
		setTitle("Relacionar");
		setBounds(100, 100, 643, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		lista=conexao.buscarProfessores();
		lista_materia=conexao.buscarMaterias();
		
		lista_proex=conexao.buscarProfessores();
		lista_matex=conexao.buscarMaterias();
		
		JComboBox comboBoxprof = new JComboBox();
		comboBoxprof.setBounds(114, 91, 150, 18);
		
		JComboBox comboBoxmateria = new JComboBox();
		comboBoxmateria.setBounds(114, 137, 150, 18);
		
		 JComboBox comboBoxprofex = new JComboBox();
		 comboBoxprofex.setBounds(438, 89, 150, 20);
		 
		 
		 comboBoxprofex.addItem("Selecione um Professor...");   	  // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  comboBoxprofex.addItem(lista.get(controle).getNome());  // Inseri dados na combobox.
		  }
		  getContentPane().add(comboBoxprofex);
		  
		  JComboBox comboBoxmatex = new JComboBox();
		  comboBoxmatex.setBounds(438, 135, 150, 20);
		  comboBoxmatex.addItem("Selecione uma Matéria...");  
		  getContentPane().add(comboBoxmatex);		  
		  
		 //-----------------------------------------------------
		  comboBoxprof.addItem("Selecione um Professor...");   	    // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista.size();controle++)  	// Controla a inserção de dados na combobox.
		  {
			  comboBoxprof.addItem(lista.get(controle).getNome());  // Inseri dados na combobox.
		  }
		  getContentPane().add(comboBoxprof);                  	    // Adiciona a combo box no contentPane.
		  
		  //-----------------------------------------------------
		  
		 //-----------------------------------------------------
		  
		  comboBoxmateria.addItem("Selecione uma Matéria...");              	//Inseri dados na combobox.
		  
		  for(int controlex=0;controlex<lista_materia.size();controlex++)       //Controla a inserção de dados na combobox.
		  {
			  comboBoxmateria.addItem(lista_materia.get(controlex).getNome());  // Inseri dados na combobox
		  }
		  getContentPane().add(comboBoxmateria);                           	    // Adiciona a combo box no contentPane.
		  
		 //-----------------------------------------------------  
		  
		 //-----------------------------------------------------  
		 comboBoxprof.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
	  	  int a=-1;
		  for(int controle=0;controle<lista.size();controle++)
		  {
			 if(lista.get(controle).getNome().equals(comboBoxprof.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
			 {
			  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
			 }
		  }

		  lista_materiav=conexao.buscarProfessor_Materia(lista.get(a));                                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
			  			
		  for(int p =0;p<lista_materiav.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
		  {
			  for(int controle=0;controle<lista_materia.size();controle++)                              //Controle do tamanho da lista_materia para inserção.
			  {
			  					                                                                        
				   if(lista_materia.get(controle).getIdMateria()==lista_materiav.get(p).getIdMateria()) //Seleciona as materias e verifica se fazem parte da lista_materiav com base no id deles e do professor selecionado.
				   {				
					   
					   comboBoxmateria.removeItem(lista_materia.get(controle).getNome());               //Remove itens da combobox.
					  				     
				   }			 			  						
			  }
		  }
		  
		  comboBoxprof.setEnabled(false);
		  }
		  });
		 
		 //-----------------------------------------------------
	  
		 comboBoxprofex.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
	  	  int a=-1;
		  for(int controle=0;controle<lista_proex.size();controle++)
		  {
			 if(lista_proex.get(controle).getNome().equals(comboBoxprofex.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
			 {
			  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
			 }
		  }

		  lista_materiavex=conexao.buscarProfessor_Materia(lista_proex.get(a));                                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
			  			
		 
		 for(int p =0;p<lista_materiavex.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
		  {
			  for(int controle=0;controle<lista_matex.size();controle++)                              //Controle do tamanho da lista_materia para inserção.
			  {
			  					                                                                        
				   if(lista_matex.get(controle).getIdMateria()==lista_materiavex.get(p).getIdMateria()) //Seleciona as materias e verifica se fazem parte da lista_materiav com base no id deles e do professor selecionado.
				   {				
					   
					   comboBoxmatex.addItem(lista_matex.get(controle).getNome());               //Adiciona itens da combobox.
					  				     
				   }			 			  						
			  }
		  }
		 
		 comboBoxprofex.setEnabled(false);
		  }
		  });
		 
		//-----------------------------------------------------
		 
		  JButton btnNewButton = new JButton("Adicionar");
		  btnNewButton.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		prof_selecionado=String.valueOf(comboBoxprof.getSelectedItem());             //Pega o professor selecionado.
		  		mat_selecionada=String.valueOf(comboBoxmateria.getSelectedItem());           //Pega a matéria selecionada.
		  		if(prof_selecionado=="Selecione um Professor...")                            //Verifica a seleção.
		  		{
		  			JOptionPane.showMessageDialog(null, "Opção de professor invalida");
		  		}
		  		else
		  		{
		  			
		  			if(mat_selecionada=="Selecione uma Matéria...")                          //Verifica a seleção.
		  			{
		  				JOptionPane.showMessageDialog(null, "Opção de materia invalida");
		  			}
		  			else
		  			{
		  				
		  				int a=-1;
				  		for(int i=0;i<lista.size();i++)
						  {
				  			if(lista.get(i).getNome().equals(comboBoxprof.getSelectedItem().toString()))    //Coleta dados da lista que como base o nome selecionado na combobox.
				  			{  
						  a=i;
						  }}
				  		int b=-1;
				  		for(int i=0;i<lista_materia.size();i++)
						  {
				  			if(lista_materia.get(i).getNome().equals(comboBoxmateria.getSelectedItem().toString()))  //Coleta dados da lista que como base o nome selecionado na combobox.
				  			{ 
						  b=i;
						  }}
				  		if(a!=-1&&b!=-1){
				  			boolean controle =true;
				  			lista_materiav=conexao.buscarProfessor_Materia(lista.get(a));  
				  			
				  				
				  				for(int i=0;i<lista_materiav.size();i++)
								  {
				  					
						  			if(lista_materia.get(b).getIdMateria()==lista_materiav.get(i).getIdMateria())
						  					{
						  						controle=false;
						  						JOptionPane.showMessageDialog(null, "Relação ja realizada.");
						  						
						  						
						  					}
						  			}
				  			
				  		
				  			if(controle==true)
				  			{
				  		conexao.inserir(lista.get(a),lista_materia.get(b));  // insere dados na tabela Professor_Materia do BD.
				  		AcaoRecente a1 =new AcaoRecente();
						a1.setFuncionario(FramePai.usuarioatual);
						a1.setAcao("Relação entre "+lista.get(a).getCPF()+" e "+lista_materia.get(b).getNome()+" Adicionada");
						conexao.inserir(a1);
						FramePai.a.add(a1);
						Principal.AtualizarAcoes();//insere nas ações recentes
				  		
		  				JOptionPane.showMessageDialog(null, "Relação realizada com sucesso.");
		  				setVisible(false);
		  				
				  			}
				  		}
		  			}
		  		
		  		}
		  	}
		  });
		  btnNewButton.addMouseListener(new MouseAdapter() {
		  	@Override
		  	public void mouseClicked(MouseEvent e) {
		  		
		  		
		  		
		  	}
		  });
		  
		 //-----------------------------------------------------
		  
		  btnNewButton.setBounds(108, 203, 89, 23);
		  getContentPane().add(btnNewButton);
		  
		  JLabel lblNewLabel_1 = new JLabel("Professor : ");
		  lblNewLabel_1.setBounds(53, 91, 63, 18);
		  getContentPane().add(lblNewLabel_1);
		  
		  JLabel lblNewLabel_2 = new JLabel("Mat\u00E9ria : ");
		  lblNewLabel_2.setBounds(53, 137, 46, 18);
		  getContentPane().add(lblNewLabel_2);
		  
		  JLabel lblNewLabel_3 = new JLabel("<html><center>Atribua mat\u00E9rias para um professor para que o hor\u00E1rio autom\u00E1tico funcione.</center></html>");
		  lblNewLabel_3.setForeground(Color.WHITE);
		  lblNewLabel_3.setBounds(43, 11, 219, 68);
		  getContentPane().add(lblNewLabel_3);
		  
		  JLabel label = new JLabel("Professor : ");
		  label.setBounds(375, 90, 63, 18);
		  getContentPane().add(label);
		  
		  JLabel label_1 = new JLabel("Mat\u00E9ria : ");
		  label_1.setBounds(375, 136, 46, 18);
		  getContentPane().add(label_1);
		  
		  JButton btnNewButton_1 = new JButton("Excluir");
		  btnNewButton_1.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		if(JOptionPane.showConfirmDialog(RelacionaMateriaProfessor.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
				{
		  		profex_selecionado=String.valueOf(comboBoxprofex.getSelectedItem());             //Pega o professor selecionado.
		  		matex_selecionada=String.valueOf(comboBoxmatex.getSelectedItem());           //Pega a matéria selecionada.
		  		if(profex_selecionado=="Selecione um Professor...")                            //Verifica a seleção.
		  		{
		  			JOptionPane.showMessageDialog(null, "Opção de professor invalida");
		  		}
		  		else
		  		{
		  			
		  			if(matex_selecionada=="Selecione uma Matéria...")                          //Verifica a seleção.
		  			{
		  				JOptionPane.showMessageDialog(null, "Opção de materia invalida");
		  			}
		  			else
		  			{
		  		int a=-1;
		  		for(int i=0;i<lista_proex.size();i++)
				  {
		  			if(lista_proex.get(i).getNome().equals(comboBoxprofex.getSelectedItem().toString()))    //Coleta dados da lista que como base o nome selecionado na combobox.
		  			{  
				  a=i;
				  }}
		  		int b=-1;
		  		for(int i=0;i<lista_matex.size();i++)
				  {
		  			if(lista_matex.get(i).getNome().equals(comboBoxmatex.getSelectedItem().toString()))  //Coleta dados da lista que como base o nome selecionado na combobox.
		  			{ 
				  b=i;
				  }}
		  		if(a!=-1&&b!=-1){
		  			conexao.Excluir(lista_proex.get(a),lista_matex.get(b)); // deleta dados da tabela Professor_Materia do BD.
		  			AcaoRecente a1 =new AcaoRecente();
					a1.setFuncionario(FramePai.usuarioatual);
					a1.setAcao("Relação entre "+lista_proex.get(a).getCPF()+" e "+lista_matex.get(b).getNome()+" Excuida");
					conexao.inserir(a1);
					FramePai.a.add(a1);
					Principal.AtualizarAcoes();//insere nas ações recentes
		  			JOptionPane.showMessageDialog(null, "Exclusão de relação realizada com sucesso.");
	  				setVisible(false);
	  				
		  		}
		  			}
		  		}
				}
		  	}
		  });
		  btnNewButton_1.addMouseListener(new MouseAdapter() {
		  	@Override
		  	public void mouseClicked(MouseEvent arg0) {
		  		
		  	}
		  });
		  btnNewButton_1.setBounds(438, 203, 89, 23);
		  getContentPane().add(btnNewButton_1);
		  
		  JLabel lblexcluaRelaesFeitas = new JLabel("<html><center>Exclua rela\u00E7\u00F5es feitas anteriormente.</center></html>");
		  lblexcluaRelaesFeitas.setForeground(Color.WHITE);
		  lblexcluaRelaesFeitas.setBounds(392, 11, 219, 68);
		  getContentPane().add(lblexcluaRelaesFeitas);
		  
		  JLabel lblNewLabel = new JLabel("New label");
		  lblNewLabel.setIcon(new ImageIcon(RelacionaMateriaProfessor.class.getResource("/fundoRela\u00E7\u00E3o2.png")));
		  lblNewLabel.setBounds(0, 0, 637, 272);
		  getContentPane().add(lblNewLabel);
		  
		  
		  
		 
		
		
		
	}
}
