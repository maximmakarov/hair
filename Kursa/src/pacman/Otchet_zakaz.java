package pacman;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;



public class Otchet_zakaz extends JFrame {
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Otchet_zakaz frame = new Otchet_zakaz();
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
	public Otchet_zakaz() {
		setTitle("\u041E\u0442\u0447\u0435\u0442 \u043F\u043E \u0437\u0430\u043A\u0430\u0437\u0430\u043C \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u0417\u0430\u043A\u0430\u0437\u044B \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		label.setBounds(10, 11, 108, 14);
		contentPane.add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(110, 8, 200, 20);
		contentPane.add(comboBox);
		
		JLabel label_1 = new JLabel("\u0432 \u043C\u0435\u0441\u044F\u0446");
		label_1.setBounds(20, 39, 46, 14);
		contentPane.add(label_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(110, 36, 127, 20);
		contentPane.add(comboBox_1);
		
		JList list = new JList();
		list.setBounds(20, 84, 395, 199);
		contentPane.add(list);
		
		try{
			comboBox.addItem("Все клиенты");
			DBClass db=new DBClass();
			ArrayList<Client> c=db.clientFromDB();
			for (int i=0; i<c.size(); i++){
				comboBox.addItem(c.get(i));
			}

			comboBox_1.addItem("Все месяца");
			comboBox_1.addItem("Январь");
			comboBox_1.addItem("Февраль");
			comboBox_1.addItem("Март");
			comboBox_1.addItem("Апрель");
			comboBox_1.addItem("Май");
			comboBox_1.addItem("Июнь");
			comboBox_1.addItem("Июль");
			comboBox_1.addItem("Август");
			comboBox_1.addItem("Сентябрь");
			comboBox_1.addItem("Октябрь");
			comboBox_1.addItem("Ноябрь");
			comboBox_1.addItem("Декабрь");
			
			JButton btnn = new JButton("\u041F\u043E\u0441\u0442\u0440\u043E\u0438\u0442\u044C \u043E\u0442\u0447\u0435\u0442");
			btnn.setBounds(272, 39, 152, 23);
			contentPane.add(btnn);
			
		//	table = new JTable();
			
		//	JScrollPane scrollPane = new JScrollPane(table);
		//	scrollPane.setBounds(20, 84, 395, 199);
		//	contentPane.add(scrollPane);
			
		String[] colHead={"Фамилия", "Имя", "Дата", "Сумма", "Название", "Фамилия", "Название", "Количество"};
			DBClass db1=new DBClass();
			ArrayList<String> o=db1.otchetZakaz();
			list.setListData(o.toArray());
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
}	
}
