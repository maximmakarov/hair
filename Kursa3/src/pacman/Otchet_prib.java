package pacman;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class Otchet_prib extends JFrame {

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
					Otchet_prib frame = new Otchet_prib();
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
	public Otchet_prib() {
		setTitle("\u041E\u0442\u0447\u0435\u0442 \u043F\u043E \u043F\u0440\u0438\u0431\u044B\u043B\u0438");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 390);
		contentPane = new JPanel();
//		contentPane.setBackground(new Color(255, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JPanel panel = new JPanel();
	//	panel.setBackground(new Color(255, 204, 255));
	//	contentPane.add(panel, BorderLayout.CENTER);
	//	panel.setLayout(null);
		
		JLabel label = new JLabel("\u041E\u0442\u0447\u0435\u0442 \u0437\u0430 \u043C\u0435\u0441\u044F\u0446");
		label.setBounds(21, 11, 133, 14);
		contentPane.add(label);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(139, 33, 113, 20);
		contentPane.add(comboBox_1);
		
		JLabel label_1 = new JLabel("\u043F\u0440\u0438\u0431\u044B\u043B\u044C");
		label_1.setBounds(21, 36, 104, 14);
		contentPane.add(label_1);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(139, 8, 113, 20);
		contentPane.add(comboBox);
		
		final DatabaseTableModel dbtable=new DatabaseTableModel(true);
		
			comboBox_1.addItem("по услугам");
			comboBox_1.addItem("по материалам");

			comboBox.addItem("Все месяца");
			comboBox.addItem("Январь");
			comboBox.addItem("Февраль");
			comboBox.addItem("Март");
			comboBox.addItem("Апрель");
			comboBox.addItem("Май");
			comboBox.addItem("Июнь");
			comboBox.addItem("Июль");
			comboBox.addItem("Август");
			comboBox.addItem("Сентябрь");
			comboBox.addItem("Октябрь");
			comboBox.addItem("Ноябрь");
			comboBox.addItem("Декабрь");
			
			JButton button = new JButton("\u041F\u043E\u0441\u0442\u0440\u043E\u0438\u0442\u044C \u043E\u0442\u0447\u0435\u0442");
			button.setBounds(290, 32, 156, 23);
			contentPane.add(button);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int i=comboBox.getSelectedIndex();
				if (comboBox.getSelectedIndex()==0){
						if (comboBox_1.getSelectedIndex()==0){
							zapros="select DatePart('m', z.date) AS 'Месяц', u.nazvanie AS 'Услуга', SUM(u.price) AS 'Сумма' " +
								"from Zakaz z, Usluga u, Content_zakaza cz " +
								"WHERE (z.id_zakaza=cz.id_zakaza AND cz.id_uslugi=u.id_uslugi) " +
								"GROUP BY DatePart('m', z.date), u.nazvanie ORDER BY 1, 2";
								
						}
						else{ zapros="select DatePart('m', z.date) AS 'Месяц', m.nazvanie  AS 'Материал', um.kol_vo AS 'Количество', SUM(m.price*um.kol_vo) AS 'Сумма' " +
								"from Zakaz z, Material m, Used_material um " +
								"WHERE (z.id_zakaza=um.id_zakaza AND um.id_materiala=m.id_materiala) " +
								"GROUP BY DatePart('m', z.date), m.nazvanie, um.kol_vo ORDER BY 1,2 ";	}
				} 
				 else {
						if (comboBox_1.getSelectedIndex()==0){
							
							zapros="select u.nazvanie AS 'Услуга', SUM(u.price) AS 'Сумма' " +
							"from Zakaz z, Usluga u, Content_zakaza cz " +
							"WHERE (z.id_zakaza=cz.id_zakaza AND cz.id_uslugi=u.id_uslugi) AND DatePart('m', z.date)=" +i+
							" GROUP BY u.nazvanie ORDER BY 1, 2";
						}
						else{
					
							zapros="select m.nazvanie AS 'Материал', um.kol_vo AS 'Количество', SUM(m.price*um.kol_vo) AS 'Сумма' " +
							"from Zakaz z, Material m, Used_material um " +
							"WHERE (z.id_zakaza=um.id_zakaza AND um.id_materiala=m.id_materiala) AND DatePart('m', z.date)=" +i+
							" GROUP BY m.nazvanie, um.kol_vo ORDER BY 1,2 ";
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
						}}
				}
			});
			
		
			table = new JTable(dbtable);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(20, 84, 450, 199);
			contentPane.add(scrollPane);
	}
}
