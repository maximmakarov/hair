package pacman;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class ClientDialog extends JFrame{
	private JPanel contentPane;
	private JTextField textField_familiya;
	private JTextField textField_imya;
	private JTextField textField_otchestvo;
	private JTextField textField_phone;

	JCheckBox checkBox = new JCheckBox("");
	
	JComboBox comboBox = new JComboBox();
	
	JLabel label_id_hidden = new JLabel("\u041D\u043E\u0432\u044B\u0439 \u043A\u043B\u0438\u0435\u043D\u0442");
	
	JButton button_insert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
	JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
	JButton button_update = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientDialog frame = new ClientDialog();
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
	public ClientDialog() {
		setTitle("\u041D\u043E\u0432\u044B\u0439 \u043A\u043B\u0438\u0435\u043D\u0442");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
//		panel.setBackground(new Color(255, 204, 255));
		panel.setBounds(0, 0, 392, 267);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_name = new JLabel("\u0418\u043C\u044F");
		label_name.setBounds(40, 54, 108, 14);
		panel.add(label_name);
		
		JLabel label_surname = new JLabel("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");
		label_surname.setBounds(40, 29, 126, 14);
		panel.add(label_surname);
		
		JLabel label_otchestvo = new JLabel("\u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
		label_otchestvo.setBounds(40, 79, 126, 14);
		panel.add(label_otchestvo);
		
		JLabel label_sex = new JLabel("\u041F\u043E\u043B");
		label_sex.setBounds(40, 104, 126, 14);
		panel.add(label_sex);
		
		JLabel label_phone = new JLabel("\u0422\u0435\u043B\u0435\u0444\u043E\u043D");
		label_phone.setBounds(40, 132, 126, 14);
		panel.add(label_phone);
		
		JLabel label_constant = new JLabel("\u041F\u043E\u0441\u0442\u043E\u044F\u043D\u043D\u044B\u0439 \u043A\u043B\u0438\u0435\u043D\u0442");
		label_constant.setBounds(40, 157, 126, 14);
		panel.add(label_constant);
		
		textField_familiya = new JTextField();
		textField_familiya.setBounds(176, 29, 158, 20);
		panel.add(textField_familiya);
		textField_familiya.setColumns(10);
		
		textField_imya = new JTextField();
		textField_imya.setColumns(10);
		textField_imya.setBounds(176, 54, 158, 20);
		panel.add(textField_imya);
		
		textField_otchestvo = new JTextField();
		textField_otchestvo.setColumns(10);
		textField_otchestvo.setBounds(176, 79, 158, 20);
		panel.add(textField_otchestvo);
		
		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(176, 132, 158, 20);
		panel.add(textField_phone);
		
		
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField_imya.getText().equals("")){
					try{
						Client s=new Client();
						s.setFamiliya(textField_familiya.getText());
						s.setImya(textField_imya.getText());
						s.setOtchestvo(textField_otchestvo.getText());
						s.setPhone(textField_phone.getText());
						s.setSex(comboBox.getSelectedItem().toString());
						s.setConstant_client(checkBox.isSelected());
						
						DBClass db=new DBClass();
						db.clientUpsert(s, "INSERT");
						MyFrame.updateList();
						ZakazDialog.updateClient();

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
		button_insert.setBounds(85, 187, 149, 23);
		panel.add(button_insert);
	//	checkBox.setBackground(new Color(255, 204, 255));
		
		
		checkBox.setBounds(172, 153, 97, 23);
		panel.add(checkBox);
		
		
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_cancel.setBounds(250, 187, 89, 23);
		panel.add(button_cancel);
		
		JLabel label = new JLabel("\u041F\u043E\u043B\u044F, \u043E\u0442\u043C\u0435\u0447\u0435\u043D\u043D\u044B\u0435 ");
		label.setBounds(51, 221, 122, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(172, 221, 12, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u043E\u0431\u044F\u0437\u0430\u0442\u0435\u043B\u044C\u043D\u044B  \u0434\u043B\u044F \u0437\u0430\u043F\u043E\u043B\u043D\u0435\u043D\u0438\u044F!");
		label_2.setBackground(new Color(255, 204, 255));
		label_2.setBounds(183, 221, 199, 14);
		panel.add(label_2);
		
		
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField_imya.getText().equals("")){
						try{
					
						Client s=new Client();
						s.setId_clienta(Integer.parseInt(label_id_hidden.getText()));
						
						s.setFamiliya(textField_familiya.getText());
						s.setImya(textField_imya.getText());
						s.setOtchestvo(textField_otchestvo.getText());
						s.setPhone(textField_phone.getText());
						s.setSex(comboBox.getSelectedItem().toString());
						s.setConstant_client(checkBox.isSelected());
						
						DBClass db=new DBClass();
						db.clientUpsert(s, "UPDATE");
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
		button_update.setBounds(85, 187, 149, 23);
		panel.add(button_update);
		
		
		label_id_hidden.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_id_hidden.setBounds(176, 3, 122, 14);
		panel.add(label_id_hidden);
		
		
		comboBox.setBounds(176, 104, 158, 20);
		panel.add(comboBox);
		
		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setBounds(344, 54, 12, 14);
		panel.add(label_5);
	}
	
	//Вид формы при добавлении нового клиента
	public void clientDiaInsert() {
		label_id_hidden.setText("Новый клиент");
		textField_familiya.setText("");
		textField_imya.setText("");
		textField_otchestvo.setText("");
		textField_phone.setText("");
		Object[] o={"Ж", "М"};
		for(int i=0; i<o.length; i++){
			comboBox.addItem(o[i]);
		}
	}
	
	//Вид формы при изменении клиента
	public void clientDiaUpdate(Client s) {
		label_id_hidden.setText(Integer.toString(s.getId_clienta()));
		textField_familiya.setText(s.getFamiliya());
		textField_imya.setText(s.getImya());
		textField_otchestvo.setText(s.getOtchestvo());
		textField_phone.setText(s.getPhone());
		Object[] o={"Ж", "М"};
		for(int i=0; i<o.length; i++){
			comboBox.addItem(o[i]);
		}		
		comboBox.setSelectedItem(s.getSex());
		checkBox.setSelected(s.getConstant_client());
	}
	}