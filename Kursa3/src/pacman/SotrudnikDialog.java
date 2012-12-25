package pacman;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SotrudnikDialog extends JFrame{
		private JPanel contentPane;
		private JTextField textField_otchestvo;
		private JTextField textField_imya;
		private JTextField textField_familiya;
		private JTextField textField_phone;
		private JTextField textField_date = new JTextField();
		
		static JComboBox comboBox_doljnost = new JComboBox();
		static JComboBox comboBox_kvalification = new JComboBox();
		
		private JLabel label_id_hidden = new JLabel("Новый сотрудник");

		JButton button_insert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043E\u0442\u0440\u0443\u0434\u043D\u0438\u043A\u0430");
		private JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		JButton button_update = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");

		private JPanel panelException = new JPanel();			
		
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SotrudnikDialog frame = new SotrudnikDialog();
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
		public SotrudnikDialog() {
			setTitle("\u041D\u043E\u0432\u044B\u0439 \u0441\u043E\u0442\u0440\u0443\u0434\u043D\u0438\u043A");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			setBounds(100, 100, 450, 330);
			contentPane = new JPanel();
//			contentPane.setBackground(new Color(255, 204, 255));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			
			JLabel label_familiya = new JLabel("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");
			label_familiya.setBounds(10, 37, 110, 14);
			contentPane.add(label_familiya);
			
			JLabel label_imya = new JLabel("\u0418\u043C\u044F");
			label_imya.setBounds(10, 62, 110, 14);
			contentPane.add(label_imya);
			
			JLabel label_otchestvo = new JLabel("\u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
			label_otchestvo.setBounds(10, 87, 110, 14);
			contentPane.add(label_otchestvo);
			
			textField_otchestvo = new JTextField();
			textField_otchestvo.setBounds(167, 84, 186, 20);
			contentPane.add(textField_otchestvo);
			textField_otchestvo.setColumns(10);
			
			textField_imya = new JTextField();
			textField_imya.setBounds(167, 58, 186, 20);
			contentPane.add(textField_imya);
			textField_imya.setColumns(10);
			
			textField_familiya = new JTextField();
			textField_familiya.setBounds(167, 33, 186, 20);
			contentPane.add(textField_familiya);
			textField_familiya.setColumns(10);
			
			JLabel label_data_priema = new JLabel("\u0414\u0430\u0442\u0430 \u043F\u0440\u0438\u0435\u043C\u0430 \u043D\u0430 \u0440\u0430\u0431\u043E\u0442\u0443");
			label_data_priema.setBounds(10, 112, 147, 14);
			contentPane.add(label_data_priema);
			
			JLabel label_phone = new JLabel("\u0422\u0435\u043B\u0435\u0444\u043E\u043D");
			label_phone.setBounds(10, 137, 134, 14);
			contentPane.add(label_phone);
			
			JLabel label_doljnost = new JLabel("\u0414\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u044C");
			label_doljnost.setBounds(10, 162, 140, 14);
			contentPane.add(label_doljnost);
			
			JLabel label_kvalification = new JLabel("\u041A\u0432\u0430\u043B\u0438\u0444\u0438\u043A\u0430\u0446\u0438\u044F");
			label_kvalification.setBounds(10, 190, 147, 14);
			contentPane.add(label_kvalification);
			
			textField_phone = new JTextField();
			textField_phone.setBounds(167, 134, 186, 20);
			contentPane.add(textField_phone);
			textField_phone.setColumns(10);
			
			textField_date.setBounds(167, 109, 186, 20);
			contentPane.add(textField_date);
			
			label_id_hidden.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_id_hidden.setBounds(143, 11, 122, 14);
			contentPane.add(label_id_hidden);

			button_insert.setBounds(83, 230, 160, 23);
			contentPane.add(button_insert);
			
			button_cancel.setBounds(264, 230, 104, 23);
			contentPane.add(button_cancel);

			button_update.setBounds(83, 230, 160, 23);
			contentPane.add(button_update);
			
			JButton button_plus_doljnost = new JButton("+");
			button_plus_doljnost.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent arg0) {
					DoljnostDialog d=new DoljnostDialog();
					d.setTitle("Добавление должности");
					d.show();
				}
			});
			button_plus_doljnost.setBounds(359, 159, 41, 23);
			contentPane.add(button_plus_doljnost);
			
			JButton button_plus_kvalification = new JButton("+");
			button_plus_kvalification.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent arg0) {
					KvalificationDialog d=new KvalificationDialog();
					d.setTitle("Добавление квалификации");
					d.show();
				}
			});
			button_plus_kvalification.setBounds(359, 184, 41, 23);
			contentPane.add(button_plus_kvalification);
			
			JLabel label = new JLabel("\u041F\u043E\u043B\u044F, \u043E\u0442\u043C\u0435\u0447\u0435\u043D\u043D\u044B\u0435 ");
			label.setBounds(60, 264, 122, 14);
			contentPane.add(label);
			
			JLabel label_3 = new JLabel("*");
			label_3.setForeground(Color.RED);
			label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
			label_3.setBounds(186, 264, 12, 14);
			contentPane.add(label_3);
			
			JLabel label_4 = new JLabel("\u043E\u0431\u044F\u0437\u0430\u0442\u0435\u043B\u044C\u043D\u044B  \u0434\u043B\u044F \u0437\u0430\u043F\u043E\u043B\u043D\u0435\u043D\u0438\u044F!");
			label_4.setBounds(197, 264, 203, 14);
			contentPane.add(label_4);
			
			JLabel label_5 = new JLabel("*");
			label_5.setForeground(Color.RED);
			label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
			label_5.setBounds(356, 37, 12, 14);
			contentPane.add(label_5);
			
			JLabel label_1 = new JLabel("*");
			label_1.setForeground(Color.RED);
			label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
			label_1.setBounds(356, 62, 12, 14);
			contentPane.add(label_1);
			
			
			comboBox_doljnost.setBounds(167, 159, 186, 20);
			contentPane.add(comboBox_doljnost);
			

			comboBox_kvalification.setBounds(167, 187, 186, 20);
			contentPane.add(comboBox_kvalification);

			//Actions for button "Добавить"
			button_insert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!textField_familiya.getText().equals("") && !textField_imya.getText().equals("")){
						try{
							Sotrudnik s=new Sotrudnik();
							s.setFamiliya(textField_familiya.getText());
							s.setImya(textField_imya.getText());
							s.setOtchestvo(textField_otchestvo.getText());
							s.setData_priema(Date.valueOf(textField_date.getText()));
							s.setPhone(textField_phone.getText());
							Doljnost d=(Doljnost)comboBox_doljnost.getSelectedItem();
							s.setId_doljnosti(d.getId_doljnosti());
							Kvalification k=(Kvalification)comboBox_kvalification.getSelectedItem();
							s.setId_kvalification(k.getId_kvalification());
							
							
							DBClass db=new DBClass();
							db.sotrudnikUpsert(s, "INSERT");
							MyFrame.updateList();
							ZakazDialog.updateSotrudnik();
						}catch(SQLException e){
							e.printStackTrace();
							JOptionPane.showMessageDialog(panelException, e.getMessage());
						}catch (ClassNotFoundException ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}finally{
							dispose();
						}
					} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
				}
			});
			
			//Actions for button "Отмена"
			button_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			
			//Actions for button "Сохранить изменения"
			button_update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!textField_familiya.getText().equals("") && !textField_imya.getText().equals("") &&
							!textField_date.getText().equals("")){
						if(textField_date.getText().length()==10){
							try{
							Integer.parseInt((textField_date.getText().substring(0,3)));
							Integer.parseInt((textField_date.getText().substring(5,6)));
							Integer.parseInt((textField_date.getText().substring(8,9)));

							Sotrudnik s=new Sotrudnik();
							s.setId_sotrudnika(Integer.parseInt(label_id_hidden.getText()));
							s.setFamiliya(textField_familiya.getText());
							s.setImya(textField_imya.getText());
							s.setOtchestvo(textField_otchestvo.getText());
							s.setPhone(textField_phone.getText());
							
							Doljnost d=(Doljnost)comboBox_doljnost.getSelectedItem();
							s.setId_doljnosti(d.getId_doljnosti());
							
							Kvalification k=(Kvalification)comboBox_kvalification.getSelectedItem();
							s.setId_kvalification(k.getId_kvalification());
							
							DBClass db=new DBClass();
							db.sotrudnikUpsert(s, "UPDATE");
							MyFrame.updateList();
							dispose();
						}catch(NumberFormatException e){
							JOptionPane.showMessageDialog(panelException, e.getMessage());
						}catch(SQLException e){
							//e.printStackTrace();
							JOptionPane.showMessageDialog(panelException, e.getMessage());
						}catch (ClassNotFoundException ee) {
							//ee.printStackTrace();
							JOptionPane.showMessageDialog(panelException, ee.getMessage());
						}
					}else JOptionPane.showMessageDialog(null, "Введите дату в правильном формате!");
						} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
				}
			});
		}
		
		//Вид формы при добавлении нового сотрудника
		public void sotrDiaInsert() {
			label_id_hidden.setText("Новый сотрудник");
			textField_familiya.setText("");
			textField_imya.setText("");
			textField_otchestvo.setText("");
			textField_phone.setText("");
			comboBox_doljnost.removeAllItems();
			comboBox_kvalification.removeAllItems();
			
			Calendar calend=Calendar.getInstance();
			if(calend.get(Calendar.MONTH)<=9){
				textField_date.setText(String.valueOf((calend.get(Calendar.YEAR)+"-"+("0"+(1+calend.get(Calendar.MONTH)))+"-")+(calend.get(Calendar.DATE))));
			}
			else textField_date.setText(String.valueOf(((calend.get(Calendar.YEAR))+"-"+(1+calend.get(Calendar.MONTH))+"-")+(calend.get(Calendar.DATE))));
			textField_date.setEnabled(false);
			
			try{
				DBClass db=new DBClass();
				ArrayList<Doljnost> d=db.doljnostFromDB();
				for (int i=0; i<d.size(); i++){
					comboBox_doljnost.addItem(d.get(i));
				}
				DBClass db2=new DBClass();
				ArrayList<Kvalification> k=db2.kvalificationFromDB();
				for (int i=0; i<k.size(); i++){
					comboBox_kvalification.addItem(k.get(i));
				}
			}catch (ClassNotFoundException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(panelException, e.getMessage());
			} catch (SQLException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(panelException, ee.getMessage());
			}
		}
		
		//Вид формы при изменении сотрудника
		public void sotrDiaUpdate(Sotrudnik s) {
			label_id_hidden.setText(Integer.toString(s.getId_sotrudnika()));
			textField_familiya.setText(s.getFamiliya());
			textField_imya.setText(s.getImya());
			textField_otchestvo.setText(s.getOtchestvo());
			textField_phone.setText(s.getPhone());
			textField_date.setText(s.getData_priema().toString());
			comboBox_doljnost.removeAllItems();
			comboBox_kvalification.removeAllItems();
			
			try{
				DBClass db=new DBClass();
				ArrayList<Doljnost> d=db.doljnostFromDB();
				DBClass db2=new DBClass();
				Doljnost dd=db2.doljnostFromDB(s);
				for (int i=0; i<d.size(); i++){
					comboBox_doljnost.addItem(d.get(i));
					Doljnost ddd=(Doljnost)comboBox_doljnost.getItemAt(i);
					if(dd.getNazvanie_doljnosti().equals(ddd.getNazvanie_doljnosti()))
						dd=ddd;
					
				}
				comboBox_doljnost.setSelectedItem(dd);
				
				DBClass db3=new DBClass();
				ArrayList<Kvalification> k=db3.kvalificationFromDB();
				DBClass db4=new DBClass();
				Kvalification kk=db4.kvalificationFromDB(s);
				for (int i=0; i<k.size(); i++){
					comboBox_kvalification.addItem(k.get(i));
					Kvalification kkk=(Kvalification)comboBox_kvalification.getItemAt(i);
					if(kk.getNazvanie_kvalification().equals(kkk.getNazvanie_kvalification()))
						kk=kkk;
				}
				
				comboBox_kvalification.setSelectedItem(kk);
				
				
			}catch (ClassNotFoundException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(panelException, e.getMessage());
			} catch (SQLException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(panelException, ee.getMessage());
			}	
		}
		
		public static void updateDoljnost(){
			try{
				DBClass db=new DBClass();
				ArrayList<Doljnost> d=db.doljnostFromDB();
				comboBox_doljnost.removeAllItems();
				for (int i=0; i<d.size(); i++){
					comboBox_doljnost.addItem(d.get(i));
				}
				
			}catch (ClassNotFoundException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (SQLException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(null, ee.getMessage());
			}
		}
		
		public static void updateKvalification(){
			try{
				
				DBClass db2=new DBClass();
				ArrayList<Kvalification> k=db2.kvalificationFromDB();
				comboBox_kvalification.removeAllItems();
				for (int i=0; i<k.size(); i++){
					comboBox_kvalification.addItem(k.get(i));
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
