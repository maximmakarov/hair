	package pacman;

	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;
	import java.util.*;
import javax.swing.*;

	public class MyFrame extends JFrame {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuMain = new JMenu("Главное меню");
		JMenuItem menuItemZakaz = new JMenuItem("Заказы");
		JMenuItem menuItemMaterial = new JMenuItem("Материалы");
		JMenuItem menuItemUsluga = new JMenuItem("Услуги");
		JMenuItem menuItemClient = new JMenuItem("Клиенты");
		JMenuItem menuItemSotrudnik = new JMenuItem("Сотрудники");
		JMenu menuOtchet = new JMenu("Отчеты");
		JMenuItem menuItemOtchetZakaz = new JMenuItem("Отчет по заказам");
		JMenuItem menuItemOtchetPribul = new JMenuItem("Отчет по прибыли");

		JPanel panelRight = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelButtons = new JPanel();
		JPanel panelException = new JPanel();
		JPanel panel = new JPanel();
	
		
		static JList list = new JList();
		JScrollPane scrollPane = new JScrollPane();

		static JLabel label_name = new JLabel();
		JLabel label_search = new JLabel("Поиск");

		JTextField text_search=new JTextField();;
		
		JButton button_search = new JButton("Искать");
		JButton button_insert = new JButton("Добавить");
		JButton button_update = new JButton("Изменить");
		JButton button_delete = new JButton("Удалить");
		JButton button_details = new JButton("Подробная информация");

		ButtonsMainActionListener butMainActListener = new ButtonsMainActionListener();
		MainMenuActionListener mainMenuActListener = new MainMenuActionListener();
		OtchetsActionListener otchetsActListener = new OtchetsActionListener();
		
		//Actions for Menu "Главное меню"
		class MainMenuActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// Action for menuItem "Заказы"
				if (e.getSource() == menuItemZakaz) {
					label_name.setText("Заказ");
					updateList();
				}
				
				// Action for menuItem "Материалы"
				if (e.getSource() == menuItemMaterial) {
					label_name.setText("Материал");
					updateList();
				}
				
				// Action for menuItem "Услуга"
				if (e.getSource() == menuItemUsluga) {
					label_name.setText("Услуга");
					updateList();
				}
			
				// Action for menuItem "Клиент"
				if (e.getSource() == menuItemClient) {
					label_name.setText("Клиент");
					updateList();
				}
				
				// Action for menuItem "Сотрудник"
				if (e.getSource() == menuItemSotrudnik) {
					label_name.setText("Сотрудник");
					updateList();
				}
			}
		}

		//Actions for Menu "Отчеты"
		class OtchetsActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// Action for menuItem "Отчет по заказам"
				if (e.getSource() == menuItemOtchetZakaz) {
					label_name.setText("Отчет по заказам");
					Otchet_zakaz o=new Otchet_zakaz();
					o.show();
					o.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				
				// Action for menuItem "Отчет по прибыли"
				if (e.getSource() == menuItemOtchetPribul) {
					label_name.setText("Отчет по прибыли");
				
				}
			}
		}

		//Actions for buttons (Insert, Update, Delete, Details, Search)
		class ButtonsMainActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
		//-----------------------------------------------
				if(label_name.getText().equalsIgnoreCase("Сотрудник")){ 
				//Actions for button "Удалить"
				if (e.getSource() == button_delete){
					if(list.getSelectedValue()!=null) {
						if(JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить сотрудника?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					Sotrudnik s=(Sotrudnik)list.getSelectedValue();
					try{
						DBClass dbclass = new DBClass(); 
						dbclass.sotrudnikDelete(s);
						updateList();
					} catch(SQLException ee){
						ee.getStackTrace();
						JOptionPane.showMessageDialog(panelException, ee.getMessage());
					}
					catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(panelException, e1.getMessage());
					}
					}}
					else 
						JOptionPane.showMessageDialog(panelException, "Для удаления необходимо выбрать сотрудника.");
					
				}
				//Actions for button "Поиск"
				if (e.getSource() == button_search){
					if(text_search.getText()!=""){
						try {
							DBClass dbclass = new DBClass();
							ArrayList<Sotrudnik> s = dbclass.sotrudnikSearch(text_search.getText());
							list.setListData(s.toArray());
							
						} catch (SQLException ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						} catch (ClassNotFoundException ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
					}
				}
				//Actions for button "Изменить"
				if (e.getSource() == button_update){
					if(list.getSelectedValue()!=null){
						SotrudnikDialog sd=new SotrudnikDialog();
						sd.setTitle("Изменение работника: "+ list.getSelectedValue());
						sd.show();
						sd.button_update.setVisible(true);
						sd.button_insert.setVisible(false);
						sd.sotrDiaUpdate((Sotrudnik)list.getSelectedValue());
						sd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					}
					else 
						JOptionPane.showMessageDialog(panelException, "Для изменения необходимо выбрать сотрудника.");
				}
				//Actions for button "Добавить"
				if (e.getSource() == button_insert){
					SotrudnikDialog sd=new SotrudnikDialog();
					sd.setTitle("Добавление работника");
					sd.show();
					sd.button_update.setVisible(false);
					sd.button_insert.setVisible(true);
					sd.sotrDiaInsert();
				}
			}
		//-----------------------------------------------
			else if(label_name.getText().equalsIgnoreCase("Заказ")) {
				//Actions for button "Удалить"
				if (e.getSource() == button_delete){
					if(list.getSelectedValue()!=null) {
						if(JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить заказ?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					
					Zakaz s=(Zakaz)list.getSelectedValue();
					try{
						DBClass dbclass = new DBClass(); 
						dbclass.zakazDelete(s);
						updateList();
					} catch(SQLException ee){
						ee.getStackTrace();
						JOptionPane.showMessageDialog(panelException, ee.getMessage());
					}
					catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(panelException, e1.getMessage());
					}
					}}
					else 
						JOptionPane.showMessageDialog(panelException, "Для удаления необходимо выбрать заказ.");
					
				}
				//Actions for button "Поиск"
				if (e.getSource() == button_search){
					if(text_search.getText()!=""){
						try {
							DBClass dbclass = new DBClass();
							ArrayList<Zakaz> s = dbclass.zakazSearch(text_search.getText());
							list.setListData(s.toArray());
							
						} catch (SQLException ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						} catch (ClassNotFoundException ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
					}
				}
				//Actions for button "Изменить"
				if (e.getSource() == button_update){
					if(list.getSelectedValue()!=null){
						ZakazDialog sd=new ZakazDialog();
						sd.setTitle("Изменение заказа: "+ list.getSelectedValue());
						sd.show();
						sd.button_update.setVisible(true);
						sd.button_insert.setVisible(false);
						sd.zakazDiaUpdate((Zakaz)list.getSelectedValue());
						sd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					}
					else 
						JOptionPane.showMessageDialog(panelException, "Для изменения необходимо выбрать заказ.");
				}
				//Actions for button "Добавить"
				if (e.getSource() == button_insert){
					ZakazDialog sd=new ZakazDialog();
					sd.setTitle("Добавление заказа");
					sd.show();
					sd.button_update.setVisible(false);
					sd.button_insert.setVisible(true);
					sd.zakazDiaInsert();
					sd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
			}
		//-----------------------------------------------
				else if(label_name.getText().equalsIgnoreCase("Клиент")) {
					//Actions for button "Удалить"
					if (e.getSource() == button_delete){
						if(list.getSelectedValue()!=null) {
							if(JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить клиента?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						Client s=(Client)list.getSelectedValue();
						try{
							DBClass dbclass = new DBClass(); 
							dbclass.clientDelete(s);
							updateList();
						} catch(SQLException ee){
							ee.getStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
						catch (ClassNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(panelException, e1.getMessage());
						}}}
						else 
							JOptionPane.showMessageDialog(panelException, "Для удаления необходимо выбрать клиента.");
						
					}
					//Actions for button "Поиск"
					if (e.getSource() == button_search){
						if(text_search.getText()!=""){
							try {
								DBClass dbclass = new DBClass();
								ArrayList<Client> s = dbclass.сlientSearch(text_search.getText());
								list.setListData(s.toArray());
								
							} catch (SQLException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							} catch (ClassNotFoundException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							}
						}
					}
					//Actions for button "Изменить"
					if (e.getSource() == button_update){
						if(list.getSelectedValue()!=null){
							ClientDialog sd=new ClientDialog();
							sd.setTitle("Изменение клиента: "+ list.getSelectedValue());
							sd.show();
							sd.button_update.setVisible(true);
							sd.button_insert.setVisible(false);
							sd.clientDiaUpdate((Client)list.getSelectedValue());
							sd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						}
						else 
							JOptionPane.showMessageDialog(panelException, "Для изменения необходимо выбрать клиента.");
					}
					//Actions for button "Добавить"
					if (e.getSource() == button_insert){
						ClientDialog sd=new ClientDialog();
						sd.setTitle("Добавление клиента");
						sd.show();
					    sd.button_update.setVisible(false);
						sd.button_insert.setVisible(true);
						sd.clientDiaInsert();
						sd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					}
				}
		//-----------------------------------------------			
				else if(label_name.getText().equalsIgnoreCase("Материал")) {
					//Actions for button "Удалить"
					if (e.getSource() == button_delete){
						if(list.getSelectedValue()!=null) {
							if(JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить материал?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						Material m=(Material)list.getSelectedValue();
						try{
							DBClass dbclass = new DBClass(); 
							dbclass.materialDelete(m);
							updateList();
						} catch(SQLException ee){
							ee.getStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
						catch (ClassNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(panelException, e1.getMessage());
						}}}
						else 
							JOptionPane.showMessageDialog(panelException, "Для удаления необходимо выбрать материал.");
					}
					//Actions for button "Поиск"
					if (e.getSource() == button_search){
						if(text_search.getText()!=""){
							try {
								DBClass dbclass = new DBClass();
								ArrayList<Material> s = dbclass.materialSearch(text_search.getText());
								list.setListData(s.toArray());
								
							} catch (SQLException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							} catch (ClassNotFoundException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							}
						}
					}
					//Actions for button "Изменить"
					if (e.getSource() == button_update){
						if(list.getSelectedValue()!=null){
							MaterialDialog md=new MaterialDialog();
							md.setTitle("Изменение материала: "+ list.getSelectedValue());
							md.show();
							md.button_update.setVisible(true);
							md.button_insert.setVisible(false);
							md.materialDiaUpdate((Material)list.getSelectedValue());
							md.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						}
						else 
							JOptionPane.showMessageDialog(panelException, "Для изменения необходимо выбрать материал.");
					}
					//Actions for button "Добавить"
					if (e.getSource() == button_insert){
						MaterialDialog md=new MaterialDialog();
						md.setTitle("Добавление материала");
						md.show();
						md.button_update.setVisible(false);
						md.button_insert.setVisible(true);
						md.materialDiaInsert();
					}
				}
		//-----------------------------------------------			
				else if(label_name.getText().equalsIgnoreCase("Услуга")) {
					//Actions for button "Удалить"
					if (e.getSource() == button_delete){
						if(list.getSelectedValue()!=null) {
							if(JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить услугу?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						Usluga m=(Usluga)list.getSelectedValue();
						try{
							DBClass dbclass = new DBClass(); 
							dbclass.uslugaDelete(m);
							updateList();
						} catch(SQLException ee){
							ee.getStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
						catch (ClassNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(panelException, e1.getMessage());
						}}}
						else 
							JOptionPane.showMessageDialog(panelException, "Для удаления необходимо выбрать услугу.");
					}
					//Actions for button "Поиск"
					if (e.getSource() == button_search){
						if(text_search.getText()!=""){
							try {
								DBClass dbclass = new DBClass();
								ArrayList<Usluga> s = dbclass.uslugaSearch(text_search.getText());
								list.setListData(s.toArray());
								
							} catch (SQLException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							} catch (ClassNotFoundException ee) {
								ee.printStackTrace();
								JOptionPane.showMessageDialog(panelException, ee.getMessage());
							}
						}
					}
					//Actions for button "Изменить"
					if (e.getSource() == button_update){
						if(list.getSelectedValue()!=null){
							UslugaDialog md=new UslugaDialog();
							md.setTitle("Изменение услуги: "+ list.getSelectedValue());
							md.show();
							md.button_update.setVisible(true);
							md.button_insert.setVisible(false);
							md.uslugaDiaUpdate((Usluga)list.getSelectedValue());
							md.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						}
						else 
							JOptionPane.showMessageDialog(panelException, "Для изменения необходимо выбрать услугу.");
					}
					//Actions for button "Добавить"
					if (e.getSource() == button_insert){
						UslugaDialog md=new UslugaDialog();
						md.setTitle("Добавление услуги");
						md.show();
						md.button_update.setVisible(false);
						md.button_insert.setVisible(true);
						md.uslugaDiaInsert();
					}
				}
		}}
		
		//Конструктор главной формы
		public MyFrame() throws SQLException, ClassNotFoundException {
			this.setSize(740, 500);
			this.setDefaultCloseOperation(2);
			this.setTitle("Парикмахерская");

			panelRight.setPreferredSize(new Dimension(200, 0));
			panelCenter.setPreferredSize(new Dimension(0, 0));
			panelButtons.setPreferredSize(new Dimension(0, 80));

			panelRight.setBackground(Color.LIGHT_GRAY);
			panelCenter.setBackground(Color.LIGHT_GRAY);
			panelButtons.setBackground(Color.LIGHT_GRAY);

			//Right Panel (Insert, Update, Delete, Details)
			this.getContentPane().add(panelRight, BorderLayout.EAST);
			panelRight.setLayout(null);
			
			button_insert.setBounds(23, 58, 153, 38);
			button_insert.addActionListener(butMainActListener);
			panelRight.add(button_insert);
			
			button_update.setBounds(23, 118, 153, 40);
			button_update.addActionListener(butMainActListener);
			panelRight.add(button_update);
			
			button_delete.setBounds(23, 180, 153, 36);
			button_delete.addActionListener(butMainActListener);
			panelRight.add(button_delete);
			
			//Buttons Panel (Search)
			this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
			panelButtons.setLayout(null);
			
			label_search.setBounds(44, 30, 30, 14);
			panelButtons.add(label_search);
			
			text_search = new JTextField();
			text_search.setBounds(98, 27, 211, 20);
			text_search.setColumns(10);
			panelButtons.add(text_search);
			
			button_search.setBounds(319, 26, 89, 23);
			button_search.addActionListener(butMainActListener);
			panelButtons.add(button_search);
			
			JButton button_show_all = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0432\u0441\u0435");
			button_show_all.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateList();
				}
			});
			button_show_all.setBounds(438, 26, 109, 23);
			panelButtons.add(button_show_all);
			
			//Center Panel (List Information About Clients, Zakazu, Sotrudniki, etc.)
			this.getContentPane().add(panelCenter, BorderLayout.CENTER);
			
			label_name.setBounds(186, 5, 129, 30);
			label_name.setPreferredSize(new Dimension(150, 30));
			panelCenter.add(label_name);
			
			scrollPane.setBounds(11, 40, 500, 323);
			scrollPane.setViewportView(list);
			scrollPane.setPreferredSize(new Dimension(500, 360));
			panelCenter.add(scrollPane);
			
			//Menu Bar
			this.setJMenuBar(menuBar);
			menuBar.add(menuMain);
			menuBar.add(menuOtchet);

			menuMain.add(menuItemZakaz);
			menuItemZakaz.addActionListener(mainMenuActListener);
			menuMain.add(menuItemMaterial);
			menuItemMaterial.addActionListener(mainMenuActListener);
			menuMain.add(menuItemUsluga);
			menuItemUsluga.addActionListener(mainMenuActListener);
			menuMain.add(menuItemClient);
			menuItemClient.addActionListener(mainMenuActListener);
			menuMain.add(menuItemSotrudnik);
			menuItemSotrudnik.addActionListener(mainMenuActListener);

			menuOtchet.add(menuItemOtchetZakaz);
			menuItemOtchetZakaz.addActionListener(otchetsActListener);
			menuOtchet.add(menuItemOtchetPribul);
			menuItemOtchetPribul.addActionListener(otchetsActListener);
			panelCenter.setLayout(null);
			
			panel.setBounds(0, 0, 724, 443);
			//	panelCenter.add(panel);
			
			this.setVisible(true);
		}

		//Метод инициализации главной формы (задание свойств для label, list, etc.)
		public void initialization(){
			label_name.setText("Заказ");
			updateList();	
		}
		
		public static void updateList() {
			
			try {
				DBClass d = new DBClass();
				if(label_name.getText().equalsIgnoreCase("Сотрудник")) 
					list.setListData(d.sotrudnikFromDB().toArray());
				else if(label_name.getText().equalsIgnoreCase("Заказ")) 
					list.setListData(d.zakazFromDB().toArray());
				else if(label_name.getText().equalsIgnoreCase("Клиент")) 
					list.setListData(d.clientFromDB().toArray());
				else if(label_name.getText().equalsIgnoreCase("Материал")) 
					list.setListData(d.materialFromDB().toArray());
				else if(label_name.getText().equalsIgnoreCase("Услуга")) 
					list.setListData(d.uslugaFromDB().toArray());
			} catch (SQLException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(null, "---Нет соединения с базой данных---\n"+ee.getMessage());
				Object[] o={"---Нет соединения с базой данных---"};
				list.setListData(o);
			} catch (ClassNotFoundException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(null, "---Нет соединения с базой данных---\n"+ee.getMessage());
				Object[] o={"---Нет соединения с базой данных---"};
				list.setListData(o);
			}		
			
		}
		public static void main(String args[]) {
			try {
				MyFrame frame = new MyFrame();
				frame.initialization();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
