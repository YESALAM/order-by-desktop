package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import bd.Conexao;
import model.AcaoRecente;
import model.Materia;
import model.Professor;
import model.Sala;
import model.Turma;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

public class RelacionaProf_mat_turma_Sala extends JDialog {

	private JPanel contentPane;
	private Conexao conexao=Login.conexao;
	private static RelacionaProf_mat_turma_Sala frame;
	private JComboBox comboBoxprof;
	private JComboBox comboBoxmat;
	private JComboBox comboBoxsala;
	private JComboBox comboBoxturma;
	private JComboBox comboBoxproex;
	private JComboBox comboBoxmatex;
	private JComboBox comboBoxturmax;
	private JComboBox comboBoxsalax;
	private List<Materia> lista_mat;
	private List<Materia> lista_matex;
	private List<Materia> lista_materiav;
	private List<Materia> lista_materiaex;
	private List<Professor> lista_pro;
	private List<Turma> lista_turma;
	private List<Sala> lista_sala;
	private List<Professor> lista_prof_mat_tur_sala;
	private List<Professor> listal=conexao.buscarProf_Mat_Tur_Sal();
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	int b;
	int c;
	int d;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					frame = new RelacionaProf_mat_turma_Sala();
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RelacionaProf_mat_turma_Sala() {
		setTitle("Relacionar");
		setBounds(100, 100, 643, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		
		lista_pro=conexao.buscarProfessores();
		lista_mat=conexao.buscarMaterias();
		lista_matex=conexao.buscarMaterias();
		lista_sala=conexao.buscarSalas();
		lista_turma=conexao.buscarTurmas();
		
		JLabel lblNewLabel = new JLabel("Mat\u00E9ria: ");
		lblNewLabel.setBounds(65, 106, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Professor: ");
		lblNewLabel_1.setBounds(65, 77, 58, 14);
		contentPane.add(lblNewLabel_1);
		
		//------------------------------------------------------
		
		comboBoxmat = new JComboBox();
		comboBoxmat.setBounds(137, 103, 108, 20);
		comboBoxmat.addItem("Selecione uma Matéria...");
		contentPane.add(comboBoxmat);
		
		//------------------------------------------------------
		
		
		
		//------------------------------------------------------
		
		comboBoxprof = new JComboBox();
		comboBoxprof.setModel(new DefaultComboBoxModel(new String[] {"Selecione um Professor..."}));
		comboBoxprof.setBounds(137, 74, 108, 20);
		 
		   	  // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista_pro.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  comboBoxprof.addItem(lista_pro.get(controle).getNome());  // Inseri dados na combobox.
		  }
		contentPane.add(comboBoxprof);
		
		comboBoxturma = new JComboBox();
		comboBoxturma.setBounds(137, 131, 108, 20);
		comboBoxturma.addItem("Selecione uma Turma...");
		contentPane.add(comboBoxturma);
		
		lblNewLabel_2 = new JLabel("Turma: ");
		lblNewLabel_2.setBounds(65, 134, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		comboBoxsala = new JComboBox();
		comboBoxsala.setBounds(137, 162, 108, 20);
		comboBoxsala.addItem("Selecione uma Sala...");   	  // Inseri dados na combobox.
		  
		  for(int controle=0;controle<lista_sala.size();controle++)  	  // Controla a inserção de dados na combobox.
		  {
			  comboBoxsala.addItem(lista_sala.get(controle).getIdSala());  // Inseri dados na combobox.
		  }

		contentPane.add(comboBoxsala);
		
		lblNewLabel_3 = new JLabel("Sala: ");
		lblNewLabel_3.setBounds(65, 165, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(String.valueOf(comboBoxprof.getSelectedItem())=="Selecione um Professor...") //Verifica a seleção.
	    		{
	    			JOptionPane.showMessageDialog(null, "Opção de professor invalida");
	    		}
	    		else
	    		if(String.valueOf(comboBoxmat.getSelectedItem())=="Selecione uma Matéria...")//Verifica a seleção.
	    		{
	    			JOptionPane.showMessageDialog(null, "Opção de matéria invalida");
	    		}
	    		
	    		else
		    		if(String.valueOf(comboBoxturma.getSelectedItem())=="Selecione uma Turma...")//Verifica a seleção.
		    		{
		    			JOptionPane.showMessageDialog(null, "Opção de turma invalida");
		    		}
		    		else
			    
	    		{
	    			
	    		if(String.valueOf(comboBoxsala.getSelectedItem())=="Selecione uma Sala...")//Verifica a seleção.
	    			{
	    				JOptionPane.showMessageDialog(null, "Opção de sala invalida");
	    			}
	    			else
	    			{
	    				
	    				
	    				int a=-1;
	  		  		for(int i=0;i<lista_pro.size();i++)
	  				  {
	  		  			if(lista_pro.get(i).getNome().equals(comboBoxprof.getSelectedItem().toString())) //Obtêm o id da lista que confere com a seleção.
	  		  			{
	  		  				a=i;
	  				    }
	  		  		  }
	  		  		int b=-1;
	  		  		for(int y=0;y<lista_mat.size();y++)
	  				  {
	  		  			if(lista_mat.get(y).getNome().equals(comboBoxmat.getSelectedItem().toString())) //Obtêm o id da lista que confere com a seleção.
	  		  					{
	  		  				
	  				               b=y;
	  				             }
	  		  		   }
	  		  		
	  		  	int c=-1;
  		  		for(int x=0;x<lista_turma.size();x++)
  				  {
  		  			if(lista_turma.get(x).getGrau()==Integer.parseInt(comboBoxturma.getSelectedItem().toString().substring(0, 1)) && 
  		  					lista_turma.get(x).getClasse().equals(comboBoxturma.getSelectedItem().toString().substring(1, 2)) && 
  		  					lista_turma.get(x).getCurso().equals(comboBoxturma.getSelectedItem().toString().substring(2, comboBoxturma.getSelectedItem().toString().length()))) //Obtêm o id da lista que confere com a seleção.
  		  					{
  		  				
  				               c=x;
  				             }
  		  		   }
	  		  	    
  		  	
		  		
  		  		
		  	
	  		  		if(a!=-1&&b!=-1&&c!=-1&&comboBoxsala.getSelectedIndex()!=0) //Caso tudo estaja preenchido corretamente faz as seguintes funções.
	  		  		{
	  		  			List<Professor> lista_pmts;
	  		  			lista_pmts=conexao.buscarProf_Mat_Tur_Sal();
	  		  			int verifica=0;
	  		  				for(int v=0;v<lista_pmts.size();v++)
	  	  				  {
	  		  				
	  	  		  			if(lista_pmts.get(v).getNome().equals(comboBoxprof.getSelectedItem().toString()) &&
	  	  		  					lista_pmts.get(v).getTurma().getIdTurma()==lista_turma.get(c).getIdTurma()  && 
	  	  		  					lista_pmts.get(v).getMateria().getIdMateria()==lista_mat.get(b).getIdMateria()) //Obtêm o id da lista que confere com a seleção.
	  	  		  			{
	  	  		  				
	  	  		  			verifica=verifica+1;	
	  	  				    }
	  	  				  }
	  		  				
	  		  				
	  		  			if(verifica==0)
	  		  			{
	  		  				Sala s = new Sala();
	  		  				s.setIdSala(comboBoxsala.getSelectedItem().toString());
	  		  		    conexao.inserir(lista_pro.get(a),lista_mat.get(b), lista_turma.get(c),s);
	  		  		AcaoRecente a1 =new AcaoRecente();
					a1.setFuncionario(FramePai.usuarioatual);
					a1.setAcao("Relação entre "+lista_pro.get(a).getCPF()+" , "+lista_mat.get(b)+" , "+lista_turma.get(c)+" e "+s.getIdSala()+" Adicionada");
					conexao.inserir(a1);
					FramePai.a.add(a1);
	  		  		    JOptionPane.showMessageDialog(null, "Relação realizada com sucesso");
	    				setVisible(false);
	    				
	  		  			}
	  		  			else
	  		  			{
	  		  			JOptionPane.showMessageDialog(null, "Relação já existente");
	  		  		    setVisible(false);
	  		  			}
	  		  		}
	    			}
	    		
	    		}
			}
		});
		btnNewButton.setBounds(108, 203, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("<html><center>Crie rela\u00E7\u00F5es para que o hor\u00E1rio autom\u00E1tico funcione.</center></html>");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(48, 21, 222, 45);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblexculaRelaesJ = new JLabel("<html><center>Excula rela\u00E7\u00F5es j\u00E1 feitas anteriormente.</center></html>");
		lblexculaRelaesJ.setHorizontalAlignment(SwingConstants.CENTER);
		lblexculaRelaesJ.setForeground(Color.WHITE);
		lblexculaRelaesJ.setBounds(369, 21, 222, 45);
		contentPane.add(lblexculaRelaesJ);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboBoxproex.getSelectedIndex()!=0&&
						comboBoxmatex.getSelectedIndex()!=0&&
						comboBoxturmax.getSelectedIndex()!=0&&
						comboBoxsalax.getSelectedIndex()!=0){
					if(JOptionPane.showConfirmDialog(RelacionaProf_mat_turma_Sala.this, "Tem certeza que deseja deletar?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_NO_OPTION)
					{
					for(int i=0;i<listal.size();i++){
						if(listal.get(i).getNome().equals(comboBoxproex.getSelectedItem().toString())&&
								listal.get(i).getMateria().getNome().equals(comboBoxmatex.getSelectedItem().toString())
										&&String.valueOf(listal.get(i).getTurma().getGrau()).equals(comboBoxturmax.getSelectedItem().toString().substring(0,1))
										&&listal.get(i).getTurma().getClasse().equals(comboBoxturmax.getSelectedItem().toString().substring(1,2))
										&&listal.get(i).getTurma().getCurso().equals(comboBoxturmax.getSelectedItem().toString().substring(2,comboBoxturmax.getSelectedItem().toString().length()))
								&&listal.get(i).getSala().getIdSala().equals(comboBoxsalax.getSelectedItem().toString())){//busca uma relação existente que coincida com as combobox e a exclui
							conexao.Excluir(listal.get(i),listal.get(i).getMateria(),listal.get(i).getTurma(),listal.get(i).getSala());
							JOptionPane.showMessageDialog(null, "Relação excluída com sucesso");
							AcaoRecente a1 =new AcaoRecente();
							a1.setFuncionario(FramePai.usuarioatual);
							a1.setAcao("Relação entre "+listal.get(i).getCPF()+" , "+listal.get(i).getMateria().getNome()+" , "+listal.get(i).getTurma().getIdTurma()+" e "+listal.get(i).getSala().getIdSala()+" Excluída");
							conexao.inserir(a1);
							FramePai.a.add(a1);
		    				setVisible(false);
						}
					}
					}
				}else{
					JOptionPane.showMessageDialog(RelacionaProf_mat_turma_Sala.this, "Dados incompletos");
				}
			}
		});
		btnExcluir.setBounds(438, 203, 89, 23);
		contentPane.add(btnExcluir);
		
		JLabel label = new JLabel("Professor: ");
		label.setBounds(392, 77, 58, 14);
		contentPane.add(label);
		
		
		comboBoxproex = new JComboBox();
		
		
		comboBoxproex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int a=-1;
				  for(int controle=0;controle<lista_pro.size();controle++)
				  {
					 if(lista_pro.get(controle).getNome().equals(comboBoxproex.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
					 {
					  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
					 }
				  }

				  String o="";
				  for(int i=0;i<listal.size();i++){
					  if(listal.get(i).getNome().equals(lista_pro.get(a).getNome())){
						  if(o.contains(listal.get(i).getMateria().getNome())){//verifica se a maesma matéria já foi adicionada para não haver repetição
							  
				  }else{
					  comboBoxmatex.addItem(listal.get(i).getMateria().getNome());
					  o+=listal.get(i).getMateria().getNome()+" ";
				  }
					  }
				  }                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
					  			
				 
			}
		});
		comboBoxproex.setModel(new DefaultComboBoxModel(new String[] {"Selecione um Professor..."}));
		comboBoxproex.setBounds(464, 74, 108, 20);
		for(int i=0;i<lista_pro.size();i++){
			comboBoxproex.addItem(lista_pro.get(i).getNome());
			}
		
		contentPane.add(comboBoxproex);
		
		comboBoxturmax = new JComboBox();
		comboBoxturmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<listal.size();i++){
					if(listal.get(i).getNome().equals(comboBoxproex.getSelectedItem().toString())&&
							listal.get(i).getMateria().getNome().equals(comboBoxmatex.getSelectedItem().toString())
							&&String.valueOf(listal.get(i).getTurma().getGrau()).equals(comboBoxturmax.getSelectedItem().toString().substring(0,1))
							&&listal.get(i).getTurma().getClasse().equals(comboBoxturmax.getSelectedItem().toString().substring(1,2))
							&&listal.get(i).getTurma().getCurso().equals(comboBoxturmax.getSelectedItem().toString().substring(2,comboBoxturmax.getSelectedItem().toString().length()))){
						comboBoxsalax.addItem(listal.get(i).getSala().getIdSala());
					}
					
				}
			}
		});
		comboBoxturmax.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma Turma..."}));
		comboBoxturmax.setBounds(464, 131, 108, 20);
		contentPane.add(comboBoxturmax);
		
		JLabel label_2 = new JLabel("Turma: ");
		label_2.setBounds(392, 134, 46, 14);
		contentPane.add(label_2);
		
		comboBoxmatex = new JComboBox();
		comboBoxmatex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String o="";
				  for(int p =0;p<listal.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
				  {
					  		  						
					  if(listal.get(p).getNome().equals(comboBoxproex.getSelectedItem().toString())&&
							  listal.get(p).getMateria().getNome().equals(comboBoxmatex.getSelectedItem().toString())){
						  
					  if(o.contains(String.valueOf(listal.get(p).getTurma().getGrau())+listal.get(p).getTurma().getClasse()+
							  listal.get(p).getTurma().getCurso())){
						  
					  }else{
						  comboBoxturmax.addItem(String.valueOf(listal.get(p).getTurma().getGrau())+listal.get(p).getTurma().getClasse()+
								  listal.get(p).getTurma().getCurso());
						  o+=(String.valueOf(listal.get(p).getTurma().getGrau())+listal.get(p).getTurma().getClasse()+
								  listal.get(p).getTurma().getCurso());
					  }
						  
					  }
				  }
				
			}
		});
		comboBoxmatex.addItem("Selecione uma Mat\u00E9ria...");
		comboBoxmatex.setBounds(464, 103, 108, 20);
		contentPane.add(comboBoxmatex);
		
		JLabel label_1 = new JLabel("Mat\u00E9ria: ");
		label_1.setBounds(392, 106, 46, 14);
		contentPane.add(label_1);
		
		JLabel label_3 = new JLabel("Sala: ");
		label_3.setBounds(392, 165, 46, 14);
		contentPane.add(label_3);
		
		comboBoxsalax = new JComboBox();
		comboBoxsalax.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma Sala..."}));
		comboBoxsalax.setBounds(464, 162, 108, 20);
		contentPane.add(comboBoxsalax);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon(RelacionaProf_mat_turma_Sala.class.getResource("/fundoRela\u00E7\u00E3o2.png")));
		lblNewLabel_4.setBounds(0, 0, 637, 272);
		contentPane.add(lblNewLabel_4);
		
		//------------------------------------------------------
		
		comboBoxprof.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
  	  int a=-1;
	  for(int controle=0;controle<lista_pro.size();controle++)
	  {
		 if(lista_pro.get(controle).getNome().equals(comboBoxprof.getSelectedItem().toString()))        //Coleta dados da lista que como base o nome selecionado na combobox.
		 {
		  	a=controle;                                                                             //Atribui o valor achado na lista na varialvel a.
		 }
	  }

	  lista_materiav=conexao.buscarProfessor_Materia(lista_pro.get(a));                                 //Cria uma lista de relação entre professor e matérias baseada na consulta passa.
		  			
	 
	 for(int p =0;p<lista_materiav.size();p++)                                                     //Controle do tamanho da lista_materiav para inserção.
	  {
		  for(int controle=0;controle<lista_matex.size();controle++)                              //Controle do tamanho da lista_materia para inserção.
		  {
		  					                                                                        
			   if(lista_mat.get(controle).getIdMateria()==lista_materiav.get(p).getIdMateria()) //Seleciona as materias e verifica se fazem parte da lista_materiav com base no id deles e do professor selecionado.
			   {				
				   
				   comboBoxmat.addItem(lista_mat.get(controle).getNome());               //Adiciona itens da combobox.
				  				     
			   }			 			  						
		  }
	  }
	  }
	  });
		//------------------------------------------------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		comboBoxmat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
							   
							  
							   comboBoxturma.addItem(grau+classe+curso);

							 															 		  				
						   }		 			  						
					  }
				  }
				  
				  }
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {    	   
		    	   
		    		
			}
		});
		
		
	}
}
