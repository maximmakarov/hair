package pacman;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.util.ArrayList;




@SuppressWarnings("serial")
public class Otchet_zakaz extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private String zapros;

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
		setBounds(100, 100, 570, 330);
		contentPane = new JPanel();
//		contentPane.setBackground(new Color(255, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u0417\u0430\u043A\u0430\u0437\u044B \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		label.setBounds(10, 11, 108, 14);
		contentPane.add(label);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(110, 8, 240, 20);
		contentPane.add(comboBox);
		
		JLabel label_1 = new JLabel("\u0432 \u043C\u0435\u0441\u044F\u0446");
		label_1.setBounds(20, 39, 46, 14);
		contentPane.add(label_1);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(110, 36, 127, 20);
		contentPane.add(comboBox_1);
		
		
		final DatabaseTableModel dbtable=new DatabaseTableModel(false);
		
		
	
		
	//	JList list = new JList();
	//	list.setBounds(20, 84, 395, 199);
	//	contentPane.add(list);
		
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
			btnn.setBounds(385, 35, 152, 23);
			contentPane.add(btnn);
			

			class ButtonOtchetListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					
					int nolik=0;
					int client=0;
					
				 if (comboBox_1.getSelectedIndex()==0){
						if (comboBox.getSelectedIndex()!=0){
							client=((Client)comboBox.getSelectedItem()).getId_clienta();
							zapros="select z.date AS 'Дата заказа', z.sum_zakaza AS 'Сумма заказа', " +
								"u.nazvanie AS 'Услуга', s.familiya AS 'Мастер' " +
								"from Zakaz z, Usluga u, Sotrudnik s, Content_zakaza cz " +
								"WHERE (z.id_zakaza=cz.id_zakaza AND cz.id_uslugi=u.id_uslugi " +
								"AND cz.id_sotrudnika=s.id_sotrudnika) AND z.id_clienta="+client+" ORDER BY 1, 3";
						}
						else{ zapros="select c.familiya AS 'Клиент', z.date AS 'Дата заказа', u.price AS 'Сумма заказа', " +
								"u.nazvanie AS 'Услуга', s.familiya AS 'Мастер'" +
								"from Zakaz z, Usluga u, Sotrudnik s, Content_zakaza cz, Client c " +
								"WHERE (z.id_zakaza=cz.id_zakaza AND cz.id_uslugi=u.id_uslugi AND " +
								"cz.id_sotrudnika=s.id_sotrudnika AND z.id_clienta=c.id_clienta) ORDER BY 1, 2";	}
					} 
				 else {
						nolik=comboBox_1.getSelectedIndex();
					
						if (comboBox.getSelectedIndex()!=0){
							client=((Client)comboBox.getSelectedItem()).getId_clienta();
							zapros="select date AS 'Дата заказа', sum_zakaza AS 'Сумма заказа' from Zakaz where id_clienta="+client+" and DatePart('m', date)="+nolik;
						}
						else{
					
							zapros="select c.familiya AS 'Клиент', z.date AS 'Дата заказа', z.sum_zakaza AS 'Сумма заказа', " +
							"u.nazvanie AS 'Услуга', s.familiya AS 'Мастер' " +
							"from Zakaz z, Usluga u, Sotrudnik s, Content_zakaza cz, Client c " +
							"WHERE (z.id_zakaza=cz.id_zakaza AND cz.id_uslugi=u.id_uslugi " +
							"AND cz.id_sotrudnika=s.id_sotrudnika AND z.id_clienta=c.id_clienta) AND DatePart('m', z.date)="+nolik+" ORDER BY 1";
						}
					}

				 if  (zapros!=null){
						try
						{
							
							DBClass d=new DBClass();
							
							if(d.otchet(zapros)!=null){
								dbtable.setDataSource(d.otchet(zapros));
							}
							else dbtable.setDataSource(null);
						
						}
						catch(Exception ee)
						{
							System.out.println("Error : "+ee);
						}
				}}}
			
			
			
			ButtonOtchetListener alis=new ButtonOtchetListener();
			btnn.addActionListener(alis);
			
			table = new JTable(dbtable);
			
			JScrollPane scrollPane = new JScrollPane(table);
		
			
			scrollPane.setBounds(20, 84, 517, 199);
			contentPane.add(scrollPane);
			
			
	/*	String[] colHead={"Фамилия", "Имя", "Дата", "Сумма", "Название", "Фамилия", "Название", "Количество"};
			DBClass db1=new DBClass();
			ArrayList<String> o=db1.otchetZakaz();
			list.setListData(o.toArray());  */
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}  
}	
}
