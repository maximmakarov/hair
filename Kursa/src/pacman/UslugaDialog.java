package pacman;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class UslugaDialog extends JFrame{
	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_price;
	private JTextField textField_time;

	JButton button_insert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0443\u0441\u043B\u0443\u0433\u0443");
	JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
	JButton button_update = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");
	
	JLabel label_id_hidden = new JLabel("\u041D\u043E\u0432\u0430\u044F \u0443\u0441\u043B\u0443\u0433\u0430");
	
	JComboBox comboBox_length_hair = new JComboBox();
	JComboBox comboBox_sex = new JComboBox();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UslugaDialog frame = new UslugaDialog();
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
	public UslugaDialog() {
		setTitle("\u041D\u043E\u0432\u0430\u044F \u0443\u0441\u043B\u0443\u0433\u0430");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 392, 233);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_price = new JLabel("\u0426\u0435\u043D\u0430");
		label_price.setBounds(40, 67, 126, 14);
		panel.add(label_price);
		
		JLabel label_name = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		label_name.setBounds(40, 42, 126, 14);
		panel.add(label_name);
		
		JLabel label_time = new JLabel("\u0414\u043B\u0438\u0442\u0435\u043B\u044C\u043D\u043E\u0441\u0442\u044C (\u043C\u0438\u043D.)");
		label_time.setBounds(40, 92, 126, 14);
		panel.add(label_time);
		
		JLabel label_length = new JLabel("\u0414\u043B\u0438\u043D\u0430 \u0432\u043E\u043B\u043E\u0441");
		label_length.setBounds(40, 117, 126, 14);
		panel.add(label_length);
		
		JLabel label_sex = new JLabel("\u041F\u043E\u043B");
		label_sex.setBounds(40, 142, 46, 14);
		panel.add(label_sex);
		
		textField_name = new JTextField();
		textField_name.setBounds(176, 42, 158, 20);
		panel.add(textField_name);
		textField_name.setColumns(10);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(176, 67, 158, 20);
		panel.add(textField_price);
		
		textField_time = new JTextField();
		textField_time.setColumns(10);
		textField_time.setBounds(176, 92, 158, 20);
		panel.add(textField_time);
		
		
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField_name.getText().equals("") && !textField_price.getText().equals("")){
					try{
						Usluga u=new Usluga();
						u.setNazvanie(textField_name.getText());
						u.setPrice(Integer.parseInt(textField_price.getText()));
						u.setTime(Integer.parseInt(textField_time.getText()));
						u.setLength_hair(comboBox_length_hair.getSelectedItem().toString());
						u.setSex(comboBox_sex.getSelectedItem().toString());
						
				
						DBClass db=new DBClass();
						db.uslugaUpsert(u, "INSERT");
						MyFrame.updateList();
						ZakazDialog.updateUsluga();
					}catch(SQLException e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch (ClassNotFoundException ee) {
						ee.printStackTrace();
						JOptionPane.showMessageDialog(null, ee.getMessage());
					}finally{
						dispose();
					}
				} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
			}
		});
		button_insert.setBounds(40, 173, 165, 23);
		panel.add(button_insert);
		
		
		comboBox_length_hair.setBounds(177, 117, 157, 20);
		panel.add(comboBox_length_hair);
		
		
		comboBox_sex.setBounds(176, 142, 158, 20);
		panel.add(comboBox_sex);
		

		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_cancel.setBounds(227, 173, 89, 23);
		panel.add(button_cancel);
		
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField_name.getText().equals("") && !textField_price.getText().equals("")){
					try{
						Usluga u=new Usluga();
						u.setId_uslugi(Integer.parseInt(label_id_hidden.getText()));
						u.setNazvanie(textField_name.getText());
						u.setPrice(Integer.parseInt(textField_price.getText()));
						u.setTime(Integer.parseInt(textField_time.getText()));
						u.setLength_hair(comboBox_length_hair.getSelectedItem().toString());
						u.setSex(comboBox_sex.getSelectedItem().toString());
						
						DBClass db=new DBClass();
						db.uslugaUpsert(u, "UPDATE");
						MyFrame.updateList();
						dispose();
					}catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch(SQLException e){
						//e.printStackTrace();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch (ClassNotFoundException ee) {
						//ee.printStackTrace();
						JOptionPane.showMessageDialog(null, ee.getMessage());
					}
					} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
				}
		});
		
		
		button_update.setBounds(40, 173, 165, 23);
		panel.add(button_update);
		
		JLabel label = new JLabel("\u041F\u043E\u043B\u044F \u043E\u0442\u043C\u0435\u0447\u0435\u043D\u043D\u044B\u0435 ");
		label.setBounds(49, 207, 99, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(152, 207, 12, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u043E\u0431\u044F\u0437\u0430\u0442\u0435\u043B\u044C\u043D\u044B  \u0434\u043B\u044F \u0437\u0430\u043F\u043E\u043B\u043D\u0435\u043D\u0438\u044F!");
		label_2.setBounds(163, 207, 171, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(344, 42, 12, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(344, 67, 12, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u041A\u043E\u0434");
		label_5.setBounds(40, 17, 46, 14);
		panel.add(label_5);
		
		
		label_id_hidden.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_id_hidden.setBounds(176, 16, 122, 14);
		panel.add(label_id_hidden);
	}
	
	//Вид формы при добавлении новой услуги
	public void uslugaDiaInsert() {
		label_id_hidden.setText("Новая услуга");
		textField_name.setText("");
		textField_price.setText("");
		textField_time.setText("");
		Object[] o={"Короткие", "Средние", "Длинные", "Свыше 45 см", "Другое"};
		for(int i=0; i<o.length; i++){
			comboBox_length_hair.addItem(o[i]);
		}
		
		Object[] oo={"Ж","М"};
		for(int i=0; i<oo.length; i++){
			comboBox_sex.addItem(oo[i]);
		}
	}
	
	//Вид формы при изменении услуги
	public void uslugaDiaUpdate(Usluga m) {
		label_id_hidden.setText(Integer.toString(m.getId_uslugi()));
		textField_name.setText(m.getNazvanie());
		textField_price.setText(m.getPrice().toString());
		textField_time.setText(m.getTime().toString());
		
		String[] o={"Короткие", "Средние", "Длинные", "Свыше 45 см", "Другое"};
		for(int i=0; i<o.length; i++){
			comboBox_length_hair.addItem(o[i]);
		}
		
		String[] oo={"Ж","М"};
		for(int i=0; i<oo.length; i++){
			comboBox_sex.addItem(oo[i]);
		}
		comboBox_length_hair.setSelectedItem(m.getLength_hair());
		comboBox_sex.setSelectedItem(m.getSex());
	}
	
}
