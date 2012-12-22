	package pacman;

	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;
	import java.util.*;

import javax.swing.*;

	@SuppressWarnings("serial")
	public class MyFrame extends JFrame {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuMain = new JMenu("Главное меню");
		JMenuItem menuItemZakaz = new JMenuItem("Заказы");
		JMenuItem menuItemMaterial = new JMenuItem("Материалы");
		JMenuItem menuItemUsluga = new JMenuItem("Услуги");
		JMenuItem menuItemClient = new JMenuItem("Клиенты");
		JMenuItem menuItemSotrudnik = new JMenuItem("Сотрудники");
		JMenuItem menuItemExit = new JMenuItem("Выход");
		JMenu menuOtchet = new JMenu("Отчеты");
		JMenuItem menuItemOtchetZakaz = new JMenuItem("Отчет по заказам");
		JMenuItem menuItemOtchetPribul = new JMenuItem("Отчет по прибыли");
		
		
		ImageIcon image1 = new ImageIcon(getToolkit().createImage("bye00000.gif"));
		ImageIcon image2 = new ImageIcon(getToolkit().createImage("images/play.gif"));
		ImageIcon image3 = new ImageIcon(getToolkit().createImage("images/pause.gif"));
		ImageIcon image4 = new ImageIcon(getToolkit().createImage("images/delete.gif"));
		ImageIcon image5 = new ImageIcon(getToolkit().createImage("images/play.gif"));
		ImageIcon image6 = new ImageIcon(getToolkit().createImage("images/pause.gif"));
		ImageIcon image7 = new ImageIcon(getToolkit().createImage("images/delete.gif"));

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
		
		JLabel im=new JLabel((new ImageIcon(getClass().getResource("oily_hair копия.gif"))));
		
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
				
				//Action for menuItem "Выход"
				if (e.getSource() == menuItemExit) {
					Object[] options = {"Да",
		                    "Нет",
		                    "Отмена"};

					int d = JOptionPane.showOptionDialog(MyFrame.this,
					"Вы действительно хотите выйти? ", null, JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE, null, options, options[2]);// переменная отвечает за
											// //константу, возвращаемую
											// нажатием кнопок в диалоге
											// ConfirmDialog
			if (d == JOptionPane.YES_OPTION && d == JOptionPane.OK_OPTION) {// если
																// нажать
																// ОК
																// или
																// YES
				MyFrame.this.dispose();
	
			}
			if (d == JOptionPane.CANCEL_OPTION && d == JOptionPane.NO_OPTION)// если
																// нажаты
																// NO
																// или
																// CANCEL
				dispose();// закрывание диалога
		
	
				}
			}
		}

		//Actions for Menu "Отчеты"
		class OtchetsActionListener implements ActionListener {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				// Action for menuItem "Отчет по заказам"
				if (e.getSource() == menuItemOtchetZakaz) {
					Otchet_zakaz o=new Otchet_zakaz();
					o.show();
					o.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				
				// Action for menuItem "Отчет по прибыли"
				if (e.getSource() == menuItemOtchetPribul) {
					Otchet_prib o=new Otchet_prib();
					o.show();
					o.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
			}
		}

		class ButtonsMainActionListener implements ActionListener{
			@SuppressWarnings("deprecation")
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
			this.setSize(740, 510);
			this.setDefaultCloseOperation(2);
			this.setTitle("Парикмахерская");

			panelRight.setPreferredSize(new Dimension(200, 0));
			panelCenter.setPreferredSize(new Dimension(0, 0));
			panelButtons.setForeground(new Color(255, 153, 204));
			panelButtons.setPreferredSize(new Dimension(0, 80));
			
			panelCenter.setBackground(new Color(255, 204, 255));
			panelRight.setBackground(SystemColor.desktop);
			panelButtons.setBackground(new Color(0, 0, 0));


			//Right Panel (Insert, Update, Delete, Details)
			this.getContentPane().add(panelRight, BorderLayout.EAST);
			panelRight.setLayout(null);

			
			button_insert.setIcon(new ImageIcon(getClass().getResource("добавить1 копия.gif"))); 
			button_insert.setRolloverIcon(new ImageIcon(getClass().getResource("добавить2 копия.gif")));
			button_insert.setPressedIcon(new ImageIcon(getClass().getResource("добавить3 копия.gif"))); 
			button_insert.setBorderPainted(false);
			button_insert.setFocusPainted(false); 
			button_insert.setContentAreaFilled(false);
			
			button_insert.setBounds(30, 40, 153, 38);
			button_insert.addActionListener(butMainActListener);
			panelRight.add(button_insert);
			
			button_update.setBounds(30, 106, 153, 40);
			button_update.addActionListener(butMainActListener);
			panelRight.add(button_update);

			
			button_update.setIcon(new ImageIcon(getClass().getResource("изменить1 копия.gif"))); 
			button_update.setRolloverIcon(new ImageIcon(getClass().getResource("изменить2 копия.gif")));
			button_update.setPressedIcon(new ImageIcon(getClass().getResource("изменить3 копия.gif"))); 
			button_update.setBorderPainted(false);
			button_update.setFocusPainted(false); 
			button_update.setContentAreaFilled(false);
			
			
			button_delete.setIcon(new ImageIcon(getClass().getResource("удалить1 копия.gif"))); 
			button_delete.setRolloverIcon(new ImageIcon(getClass().getResource("удалить2 копия.gif")));
			button_delete.setPressedIcon(new ImageIcon(getClass().getResource("удалить3 копия.gif"))); 
			button_delete.setBorderPainted(false);
			button_delete.setFocusPainted(false); 
			button_delete.setContentAreaFilled(false);
			
			button_delete.setBounds(30, 169, 153, 36);
			button_delete.addActionListener(butMainActListener);
			panelRight.add(button_delete);

			im.setBounds(30, 243, 145, 100);
			panelRight.add(im);
			
			//Buttons Panel (Search)
			this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
			panelButtons.setLayout(null);
			label_search.setForeground(new Color(255, 153, 204));
			
			label_search.setBounds(225, 34, 41, 14);
			panelButtons.add(label_search);
			
			text_search = new JTextField();
			text_search.setBounds(274, 30, 238, 23);
			text_search.setColumns(10);
			panelButtons.add(text_search);
			
			button_search.setBounds(560, 0, 147, 23);
			button_search.addActionListener(butMainActListener);
			panelButtons.add(button_search);
			button_search.setForeground(new Color(0, 0, 0));
			button_search.setBackground(UIManager.getColor("Button.light"));
			
			JButton button_show_all = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0432\u0441\u0435");
			button_show_all.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateList();
				}
			});
			button_show_all.setBounds(560, 30, 147, 23);
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
			menuBar.setBackground(new Color(0, 0, 0));
			
			//Menu Bar
			this.setJMenuBar(menuBar);
			menuMain.setForeground(new Color(255, 51, 102));
			menuMain.setBackground(new Color(0, 0, 0));
			menuBar.add(menuMain);
			menuOtchet.setForeground(new Color(255, 51, 102));
			menuOtchet.setBackground(new Color(0, 0, 0));
			menuBar.add(menuOtchet);
			menuItemZakaz.setBackground(new Color(0, 0, 0));
			menuItemZakaz.setForeground(new Color(255, 153, 204));

			menuMain.add(menuItemZakaz);
			menuItemZakaz.addActionListener(mainMenuActListener);
			menuItemMaterial.setForeground(new Color(255, 153, 204));
			menuItemMaterial.setBackground(new Color(0, 0, 0));
			menuMain.add(menuItemMaterial);
			menuItemMaterial.addActionListener(mainMenuActListener);
			menuItemUsluga.setForeground(new Color(255, 153, 204));
			menuItemUsluga.setBackground(new Color(0, 0, 0));
			menuMain.add(menuItemUsluga);
			menuItemUsluga.addActionListener(mainMenuActListener);
			menuItemClient.setForeground(new Color(255, 153, 204));
			menuItemClient.setBackground(new Color(0, 0, 0));
			menuMain.add(menuItemClient);
			menuItemClient.addActionListener(mainMenuActListener);
			menuItemSotrudnik.setBackground(new Color(0, 0, 0));
			menuItemSotrudnik.setForeground(new Color(255, 153, 204));
			menuMain.add(menuItemSotrudnik);
			menuItemSotrudnik.addActionListener(mainMenuActListener);
			menuItemOtchetZakaz.setForeground(new Color(255, 153, 204));
			menuItemOtchetZakaz.setBackground(new Color(0, 0, 0));
			menuMain.add(menuItemExit);
			menuItemExit.addActionListener(mainMenuActListener);
			menuItemExit.setBackground(new Color(0, 0, 0));
			menuItemExit.setForeground(new Color(255, 153, 204));
			
			menuItemExit.setIcon(new ImageIcon(getClass().getResource("bye00000.gif")));
			menuItemZakaz.setIcon(new ImageIcon(getClass().getResource("m-various.png")));
			menuItemMaterial.setIcon(new ImageIcon(getClass().getResource("some.png")));
			menuItemUsluga.setIcon(new ImageIcon(getClass().getResource("love.png")));
			menuItemClient.setIcon(new ImageIcon(getClass().getResource("blonde.png")));
			menuItemSotrudnik.setIcon(new ImageIcon(getClass().getResource("people.png")));
			menuItemOtchetZakaz.setIcon(new ImageIcon(getClass().getResource("important.png")));
			menuItemOtchetPribul.setIcon(new ImageIcon(getClass().getResource("expensive.png")));
			

			menuOtchet.add(menuItemOtchetZakaz);
			menuItemOtchetZakaz.addActionListener(otchetsActListener);
			menuItemOtchetPribul.setBackground(new Color(0, 0, 0));
			menuItemOtchetPribul.setForeground(new Color(255, 153, 204));
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
