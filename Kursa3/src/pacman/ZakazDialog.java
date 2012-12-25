package pacman;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


@SuppressWarnings("serial")
public class ZakazDialog extends JFrame{

	private JPanel contentPane;
	private JTextField textField_kolvo;
	private JTextField textField_date;

	static JComboBox comboBox_material = new JComboBox();
	static JComboBox comboBox_sotrudnik = new JComboBox();
	static JComboBox comboBox_usluga = new JComboBox();
	static JComboBox comboBox_client = new JComboBox();
	JList list_usluga = new JList();
	JList list_material = new JList();
	JLabel label_id_hidden = new JLabel("\u041D\u043E\u0432\u044B\u0439");
	
	JButton button_insert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0437\u0430\u043A\u0430\u0437");
	JButton add_usluga = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0443\u0441\u043B\u0443\u0433\u0443");
	JButton delete_usluga = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0443\u0441\u043B\u0443\u0433\u0443");
	JButton add_material = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B");
	JButton delete_material = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B");
	JButton button_update = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");
	JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZakazDialog frame = new ZakazDialog();
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
	public ZakazDialog() {
		setTitle("\u041D\u043E\u0432\u044B\u0439 \u0437\u0430\u043A\u0430\u0437");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 440);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton button_add_usluga = new JButton("+");
		JButton button_add_sotrudnik = new JButton("+");
		
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Zakaz z=new Zakaz();
					Client c=(Client)comboBox_client.getSelectedItem();
					z.setId_clienta(c.getId_clienta());
					z.setDate(Date.valueOf(textField_date.getText()));
					z.setId_zakaza(Integer.parseInt(label_id_hidden.getText()));
					
					int sum=0;
					
					if(list_usluga.getModel().getSize()>0){
						DBClass d2=new DBClass();
						d2.content_zakazaDelete(z);
						for(int i=0; i<list_usluga.getModel().getSize(); i++){
							Content_zakaza cz=(Content_zakaza)list_usluga.getModel().getElementAt(i);
							cz.setId_zakaza(z.getId_zakaza());
							
							DBClass dm=new DBClass();
							ArrayList<Usluga> m=dm.uslugaFromDB(cz);
							for(int j=0; j<m.size(); j++){
								Usluga uu=m.get(j);
								sum+=uu.getPrice();
							}
							
							DBClass d3=new DBClass();
							d3.content_zakazaUpsert(cz, "INSERT");
						}
					}
					
					if(list_material.getModel().getSize()>0){
						DBClass d2=new DBClass();
						d2.used_materialDelete(z);
						for(int i=0; i<list_material.getModel().getSize(); i++){
							Used_material um=(Used_material)list_material.getModel().getElementAt(i);
							um.setId_zakaza(z.getId_zakaza());
							
							DBClass dm=new DBClass();
							Material m=dm.materialFromDB(um);
							sum+=m.getPrice()*um.getKolvo();
							
							DBClass d4=new DBClass();
							d4.used_materialUpsert(um, "INSERT");
							
						}
					}
					
					z.setSum_zakaza(sum);
					DBClass db=new DBClass();
					db.zakazUpsert(z, "UPDATE");
					
					
					MyFrame.updateList();
				}catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}catch (ClassNotFoundException ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, ee.getMessage());
				}finally{
					dispose();
				}
			}
		});
		
		
		panel.add(button_update);
		
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try{
						Zakaz z=new Zakaz();
						Client c=(Client)comboBox_client.getSelectedItem();
						z.setId_clienta(c.getId_clienta());
						z.setDate(Date.valueOf(textField_date.getText()));
						z.setSum_zakaza(0);
						
						DBClass db=new DBClass();
						db.zakazUpsert(z, "INSERT");
						
						int sum=0;
						DBClass d2=new DBClass();
						Zakaz z2=d2.zakaz_ID_FromDB();
						
						
						
						if(list_usluga.getModel().getSize()>0){
						
							for(int i=0; i<list_usluga.getModel().getSize(); i++){
								Content_zakaza cz=(Content_zakaza)list_usluga.getModel().getElementAt(i);
								cz.setId_zakaza(z2.getId_zakaza());
								
								DBClass dm=new DBClass();
								ArrayList<Usluga> m=dm.uslugaFromDB(cz);
								for(int j=0; j<m.size(); j++){
									Usluga uu=m.get(j);
									sum+=uu.getPrice();
								}
								
								DBClass d3=new DBClass();
								d3.content_zakazaUpsert(cz, "INSERT");
							}
						}
						
						if(list_material.getModel().getSize()>0){
													
							for(int i=0; i<list_material.getModel().getSize(); i++){
								Used_material um=(Used_material)list_material.getModel().getElementAt(i);
								um.setId_zakaza(z2.getId_zakaza());
								
								DBClass dm=new DBClass();
								Material m=dm.materialFromDB(um);
								sum+=m.getPrice()*um.getKolvo();
								
								DBClass d3=new DBClass();
								d3.used_materialUpsert(um, "INSERT");
							}
						}
						
						z.setSum_zakaza(sum);
						z.setId_zakaza(z2.getId_zakaza());
						DBClass db7=new DBClass();
						db7.zakazUpsert(z, "UPDATE");
						
						MyFrame.updateList();

					}catch(SQLException e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch (ClassNotFoundException ee) {
						ee.printStackTrace();
						JOptionPane.showMessageDialog(null, ee.getMessage());
					}finally{
						dispose();
					}
			}
		});
		
		panel.add(button_insert);
		
	
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(button_cancel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel zakaz = new JPanel();
//		zakaz.setBackground(new Color(255, 204, 255));
		zakaz.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.add(zakaz);
		zakaz.setLayout(null);
		
		JLabel client_name = new JLabel("\u0418\u043C\u044F \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		client_name.setBounds(10, 51, 105, 14);
		zakaz.add(client_name);
		
		
		comboBox_client.setBounds(10, 76, 253, 20);
		zakaz.add(comboBox_client);
		
		JLabel date = new JLabel("\u0414\u0430\u0442\u0430 \u0437\u0430\u043A\u0430\u0437\u0430");
		date.setBounds(10, 120, 105, 14);
		zakaz.add(date);
		
		JLabel label_zakaz = new JLabel("\u0417\u0430\u043A\u0430\u0437");
		label_zakaz.setBounds(10, 12, 56, 14);
		zakaz.add(label_zakaz);
		
		JButton button_add_client = new JButton("+");
		button_add_client.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				ClientDialog d=new ClientDialog();
				d.setTitle("Добавление клиента");
				d.show();
				d.button_update.setVisible(false);
				d.button_insert.setVisible(true);
				d.clientDiaInsert();
			}
		});
		button_add_client.setBounds(273, 75, 41, 23);
		zakaz.add(button_add_client);
		
		textField_date = new JTextField();
		textField_date.setBounds(10, 145, 86, 20);
		zakaz.add(textField_date);
		textField_date.setColumns(10);
		
		
		label_id_hidden.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_id_hidden.setBounds(88, 11, 79, 14);
		zakaz.add(label_id_hidden);
		
	//	JLabel im=new JLabel((new ImageIcon(getClass().getResource("oily_hair - копия1.gif"))));
	//	im.setBounds(10, 176, 304, 195);
	//	zakaz.add(im);
		
		JPanel panel_3 = new JPanel();
	//	panel_3.setBackground(new Color(255, 204, 255));
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel label_uslugi = new JLabel("\u0423\u0441\u043B\u0443\u0433\u0438");
		label_uslugi.setBounds(64, 11, 77, 14);
		panel_3.add(label_uslugi);
		
		JLabel usluga_name = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0443\u0441\u043B\u0443\u0433\u0438");
		usluga_name.setBounds(10, 36, 131, 14);
		panel_3.add(usluga_name);
		
		
		comboBox_usluga.setBounds(10, 50, 253, 20);
		panel_3.add(comboBox_usluga);
		
		JLabel master_name = new JLabel("\u041C\u0430\u0441\u0442\u0435\u0440");
		master_name.setBounds(10, 81, 103, 14);
		panel_3.add(master_name);
		
		
		comboBox_sotrudnik.setBounds(10, 97, 253, 20);
		panel_3.add(comboBox_sotrudnik);
				
		
		add_usluga.setBounds(10, 135, 183, 23);
		panel_3.add(add_usluga);
		
		
		list_usluga.setBounds(10, 178, 260, 135);
		delete_usluga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Content_zakaza> cc=new ArrayList<Content_zakaza>();
				for(int i=0; i<list_usluga.getModel().getSize(); i++){
					Content_zakaza cz=(Content_zakaza)list_usluga.getModel().getElementAt(i);
					cc.add(cz);
				}
				cc.remove(list_usluga.getSelectedIndex());
				
				list_usluga.setListData(cc.toArray());
				
			}
		});
		

		delete_usluga.setBounds(10, 324, 183, 23);
		panel_3.add(delete_usluga);
		
		
		button_add_usluga.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				UslugaDialog d=new UslugaDialog();
				d.setTitle("Добавление услуги");
				d.show();
				d.button_update.setVisible(false);
				d.button_insert.setVisible(true);
				d.uslugaDiaInsert();
			}
		});
		button_add_usluga.setBounds(273, 50, 41, 23);
		panel_3.add(button_add_usluga);
		
		
		button_add_sotrudnik.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				SotrudnikDialog d=new SotrudnikDialog();
				d.setTitle("Добавление сотрудника");
				d.show();
				d.button_update.setVisible(false);
				d.button_insert.setVisible(true);
				d.sotrDiaInsert();
			}
		});
		button_add_sotrudnik.setBounds(273, 97, 41, 23);
		panel_3.add(button_add_sotrudnik);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list_usluga);
		scrollPane.setBounds(10, 178, 304, 135);
		panel_3.add(scrollPane);
		delete_usluga.setVisible(true);
		
		JPanel panel_4 = new JPanel();
//		panel_4.setBackground(new Color(255, 204, 255));
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel label_materiali = new JLabel("\u041C\u0430\u0442\u0435\u0440\u0438\u0430\u043B\u044B");
		label_materiali.setBounds(64, 11, 68, 14);
		panel_4.add(label_materiali);
		
		JLabel material_name = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B\u0430");
		material_name.setBounds(10, 36, 149, 14);
		panel_4.add(material_name);
		
		
		comboBox_material.setBounds(10, 50, 253, 20);
		panel_4.add(comboBox_material);
		
		JLabel kolvo_name = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E");
		kolvo_name.setBounds(10, 81, 122, 14);
		panel_4.add(kolvo_name);
		
		textField_kolvo = new JTextField();
		textField_kolvo.setBounds(10, 97, 86, 20);
		panel_4.add(textField_kolvo);
		textField_kolvo.setColumns(10);
		
		add_material.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete_material.setVisible(true);
				
					int vnezapno=Integer.parseInt(textField_kolvo.getText());
					
					if (vnezapno<=0) {JOptionPane.showMessageDialog(contentPane, "Введите положительное число, блин!"); }
					if (vnezapno>0) {
				try{ 
				if(list_material.getModel().getSize()>0){
					ArrayList<Used_material> arListUsedMater=new ArrayList<Used_material>();
					for(int i=0; i<list_material.getModel().getSize(); i++){
						arListUsedMater.add((Used_material)list_material.getModel().getElementAt(i));
					}
					Used_material um=new Used_material();
					Material m=(Material)comboBox_material.getSelectedItem();
					um.setId_materiala(m.getId_materiala());
					um.setMaterial(m.getNazvanie());
					um.setKolvo(Integer.parseInt(textField_kolvo.getText()));
					arListUsedMater.add(um);
					list_material.setListData(arListUsedMater.toArray());
				}
				else {
					Used_material um=new Used_material();
					Material m=(Material)comboBox_material.getSelectedItem();
					um.setId_materiala(m.getId_materiala());
					um.setMaterial(m.getNazvanie());
					um.setKolvo(Integer.parseInt(textField_kolvo.getText()));
					ArrayList<Used_material> arUM=new ArrayList<Used_material>();
					arUM.add(um);
					list_material.setListData(arUM.toArray());
				}
				textField_kolvo.setText("");
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Проверьте количество используемого материала.");
				}}
			}
		});
		
		
		add_material.setBounds(10, 135, 187, 23);
		panel_4.add(add_material);
		delete_material.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Used_material> cc=new ArrayList<Used_material>();
				for(int i=0; i<list_material.getModel().getSize(); i++){
					Used_material cz=(Used_material)list_material.getModel().getElementAt(i);
					cc.add(cz);
				}
				cc.remove(list_material.getSelectedIndex());
				
				list_material.setListData(cc.toArray());
			}
		});
		
		
		delete_material.setBounds(10, 324, 187, 23);
		panel_4.add(delete_material);
		
		JButton button_add_material = new JButton("+");
		button_add_material.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				MaterialDialog d=new MaterialDialog();
				d.setTitle("Добавление материала");
				d.show();
				d.button_update.setVisible(false);
				d.button_insert.setVisible(true);
				d.materialDiaInsert();
			}
		});
		button_add_material.setBounds(273, 49, 41, 23);
		panel_4.add(button_add_material);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(list_material);
		scrollPane_1.setBounds(10, 178, 304, 135);
		panel_4.add(scrollPane_1);
		
		
		list_material.setBounds(10, 178, 157, 135);
		delete_material.setVisible(true);
		
		
		add_usluga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete_usluga.setVisible(true);
				
				if(list_usluga.getModel().getSize()>0){
					
					ArrayList<Content_zakaza> arListContZakaz=new ArrayList<Content_zakaza>();
					for(int i=0; i<list_usluga.getModel().getSize(); i++){
						arListContZakaz.add((Content_zakaza)list_usluga.getModel().getElementAt(i));
					}
					Content_zakaza cz=new Content_zakaza();
					Usluga u=(Usluga)comboBox_usluga.getSelectedItem();
					cz.setId_uslugi(u.getId_uslugi());
					cz.setUsluga(u.getNazvanie());
					Sotrudnik s=(Sotrudnik)comboBox_sotrudnik.getSelectedItem();
					cz.setId_sotrudnika(s.getId_sotrudnika());
					cz.setSotrudnik(s.getFamiliya());
					arListContZakaz.add(cz);
					list_usluga.setListData(arListContZakaz.toArray());
				}
				else {
					Content_zakaza cz=new Content_zakaza();
					Usluga u=(Usluga)comboBox_usluga.getSelectedItem();
					cz.setId_uslugi(u.getId_uslugi());
					cz.setUsluga(u.getNazvanie());
					Sotrudnik s=(Sotrudnik)comboBox_sotrudnik.getSelectedItem();
					cz.setId_sotrudnika(s.getId_sotrudnika());
					cz.setSotrudnik(s.getFamiliya());
					ArrayList<Content_zakaza> arCZ=new ArrayList<Content_zakaza>();
					arCZ.add(cz);
					list_usluga.setListData(arCZ.toArray());
				}
			}
		});
		
		if (list_usluga.getModel().getSize()==0){
			delete_usluga.setVisible(false);
		}
		if (list_material.getModel().getSize()==0){
			delete_material.setVisible(false);
		}
		
	}

	//Вид формы при добавлении нового заказа
	public void zakazDiaInsert() {
		label_id_hidden.setText("Новый");
		textField_kolvo.setText("");
		comboBox_client.removeAllItems();
		comboBox_material.removeAllItems();
		comboBox_sotrudnik.removeAllItems();
		comboBox_usluga.removeAllItems();
		list_material.removeAll();
		list_usluga.removeAll();
		
		Calendar calend=Calendar.getInstance();
		if(calend.get(Calendar.MONTH)<=9){
			textField_date.setText(String.valueOf((calend.get(Calendar.YEAR)+"-"+("0"+(1+calend.get(Calendar.MONTH)))+"-")+(calend.get(Calendar.DATE))));
		}
		else textField_date.setText(String.valueOf(((calend.get(Calendar.YEAR))+"-"+(1+calend.get(Calendar.MONTH))+"-")+(calend.get(Calendar.DATE))));
		textField_date.setEnabled(false);
				
		try{
			DBClass db=new DBClass();
			ArrayList<Client> c=db.clientFromDB();
			for (int i=0; i<c.size(); i++){
				comboBox_client.addItem(c.get(i));
			}
			DBClass db2=new DBClass();
			ArrayList<Usluga> u=db2.uslugaFromDB();
			for (int i=0; i<u.size(); i++){
				comboBox_usluga.addItem(u.get(i));
			}
			DBClass db3=new DBClass();
			ArrayList<Sotrudnik> s=db3.sotrudnikFromDB();
			for (int i=0; i<s.size(); i++){
				comboBox_sotrudnik.addItem(s.get(i));
			}
			DBClass db4=new DBClass();
			ArrayList<Material> k=db4.materialFromDB();
			for (int i=0; i<k.size(); i++){
				comboBox_material.addItem(k.get(i));
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
	
	//Вид формы при изменении заказа
	public void zakazDiaUpdate(Zakaz z) {
		label_id_hidden.setText(Integer.toString(z.getId_zakaza()));
		textField_kolvo.setText("");
		textField_date.setText(z.getDate().toString());
		comboBox_client.removeAllItems();
		comboBox_material.removeAllItems();
		comboBox_sotrudnik.removeAllItems();
		comboBox_usluga.removeAllItems();
		list_material.removeAll();
		list_usluga.removeAll();
		
		try{
			DBClass db=new DBClass();
			ArrayList<Client> c=db.clientFromDB();
			DBClass ddb=new DBClass();
			Client cc=ddb.clientFromDB(z);
			for (int i=0; i<c.size(); i++){
				comboBox_client.addItem(c.get(i));	
				Client ccc=(Client)comboBox_client.getItemAt(i);
				if(cc.getId_clienta()==ccc.getId_clienta())
					cc=ccc;
			}
			comboBox_client.setSelectedItem(cc);
			
			DBClass db2=new DBClass();
			ArrayList<Usluga> u=db2.uslugaFromDB();
			for (int i=0; i<u.size(); i++){
				comboBox_usluga.addItem(u.get(i));
			}
			DBClass db3=new DBClass();
			ArrayList<Sotrudnik> s=db3.sotrudnikFromDB();
			for (int i=0; i<s.size(); i++){
				comboBox_sotrudnik.addItem(s.get(i));
			}
			DBClass db4=new DBClass();
			ArrayList<Material> k=db4.materialFromDB();
			for (int i=0; i<k.size(); i++){
				comboBox_material.addItem(k.get(i));
			}
			
			DBClass ddb5=new DBClass();
			ArrayList<Content_zakaza> ARcz=ddb5.content_zakazaFromDB(z);
			list_usluga.setListData(ARcz.toArray());
			
			
			DBClass db6=new DBClass();
			ArrayList<Used_material> arListUsedMater=db6.used_materialFromDB(z);
			list_material.setListData(arListUsedMater.toArray());
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
	
	public static void updateClient(){
		try{
			DBClass db=new DBClass();
			ArrayList<Client> d=db.clientFromDB();
			comboBox_client.removeAllItems();
			for (int i=0; i<d.size(); i++){
				comboBox_client.addItem(d.get(i));
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
	
	public static void updateUsluga(){
		try{
			DBClass db=new DBClass();
			ArrayList<Usluga> k=db.uslugaFromDB();
			comboBox_usluga.removeAllItems();
			for (int i=0; i<k.size(); i++){
				comboBox_usluga.addItem(k.get(i));
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
	
	public static void updateSotrudnik(){
		try{
			DBClass db=new DBClass();
			ArrayList<Sotrudnik> d=db.sotrudnikFromDB();
			comboBox_sotrudnik.removeAllItems();
			for (int i=0; i<d.size(); i++){
				comboBox_sotrudnik.addItem(d.get(i));
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
	
	public static void updateMaterial(){
		try{
			DBClass db=new DBClass();
			ArrayList<Material> k=db.materialFromDB();
			comboBox_material.removeAllItems();
			for (int i=0; i<k.size(); i++){
				comboBox_material.addItem(k.get(i));
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
}