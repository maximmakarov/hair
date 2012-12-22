package pacman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;


@SuppressWarnings("serial")
public class MaterialDialog extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_ed_izm;
	private JTextField textField_price;
	private JTextField textField_kolvo;

	private JLabel label_id_hidden = new JLabel("\u041D\u043E\u0432\u044B\u0439 \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B");

	JButton button_insert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B");
	JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
	JButton button_update = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaterialDialog frame = new MaterialDialog();
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
	public MaterialDialog() {
		setTitle("\u041D\u043E\u0432\u044B\u0439 \u043C\u0430\u0442\u0435\u0440\u0438\u0430\u043B");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 290);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 255));
		panel.setBounds(0, 0, 384, 256);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_name = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		label_name.setBounds(31, 44, 109, 14);
		panel.add(label_name);
		
		JLabel label_ed_izn = new JLabel("\u0415\u0434\u0438\u043D\u0438\u0446\u0430 \u0438\u0437\u043C\u0435\u0440\u0435\u043D\u0438\u044F");
		label_ed_izn.setBounds(31, 81, 120, 14);
		panel.add(label_ed_izn);
		
		JLabel label_price = new JLabel("\u0426\u0435\u043D\u0430");
		label_price.setBounds(31, 124, 79, 14);
		panel.add(label_price);
		
		JLabel label_kolvo = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E");
		label_kolvo.setBounds(31, 163, 96, 14);
		panel.add(label_kolvo);
		
		textField_name = new JTextField();
		textField_name.setBounds(160, 41, 138, 20);
		panel.add(textField_name);
		textField_name.setColumns(10);
		
		textField_ed_izm = new JTextField();
		textField_ed_izm.setBounds(160, 81, 138, 20);
		panel.add(textField_ed_izm);
		textField_ed_izm.setColumns(10);
		
		textField_price = new JTextField();
		textField_price.setBounds(160, 121, 138, 20);
		panel.add(textField_price);
		textField_price.setColumns(10);
		
		textField_kolvo = new JTextField();
		textField_kolvo.setBounds(160, 160, 138, 20);
		panel.add(textField_kolvo);
		textField_kolvo.setColumns(10);
		
		
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField_name.getText().equals("") && !textField_price.getText().equals("")&& !textField_kolvo.getText().equals("")){
					try{
						Material m=new Material();
						m.setNazvanie(textField_name.getText());
						m.setEdinica_izmereniya(textField_ed_izm.getText());
						m.setPrice(Integer.parseInt(textField_price.getText()));
						m.setKolvo(Integer.parseInt(textField_kolvo.getText()));
						
						DBClass db=new DBClass();
						db.materialUpsert(m, "INSERT");
						MyFrame.updateList();
						ZakazDialog.updateMaterial();
					}catch(SQLException e1){
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}catch (ClassNotFoundException ee) {
						ee.printStackTrace();
						JOptionPane.showMessageDialog(null, ee.getMessage());
					}finally{
						dispose();
					}
				} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
			}
		});
		button_insert.setBounds(41, 197, 163, 23);
		panel.add(button_insert);
		
		
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_cancel.setBounds(214, 197, 120, 23);
		panel.add(button_cancel);
		
		
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField_name.getText().equals("") && !textField_price.getText().equals("") &&	!textField_kolvo.getText().equals("")){
					try{
						Material m=new Material();
						m.setId_materiala(Integer.parseInt(label_id_hidden.getText()));
						m.setNazvanie(textField_name.getText());
						m.setEdinica_izmereniya(textField_ed_izm.getText());
						m.setPrice(Integer.parseInt(textField_price.getText()));
						m.setKolvo(Integer.parseInt(textField_kolvo.getText()));
						
						DBClass db=new DBClass();
						db.materialUpsert(m, "UPDATE");
						MyFrame.updateList();
						dispose();
					}catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch(SQLException e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}catch (ClassNotFoundException ee) {
						ee.printStackTrace();
						JOptionPane.showMessageDialog(null, ee.getMessage());
					}
				} else 	JOptionPane.showMessageDialog(null, "Введите обязательные параметры!");
			}
		});
		button_update.setBounds(41, 197, 163, 23);
		panel.add(button_update);
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(Color.RED);
		label.setBounds(308, 41, 12, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(308, 121, 12, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(308, 160, 12, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u041F\u043E\u043B\u044F, \u043E\u0442\u043C\u0435\u0447\u0435\u043D\u043D\u044B\u0435 ");
		label_3.setBounds(31, 231, 133, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(152, 231, 12, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u043E\u0431\u044F\u0437\u0430\u0442\u0435\u043B\u044C\u043D\u044B  \u0434\u043B\u044F \u0437\u0430\u043F\u043E\u043B\u043D\u0435\u043D\u0438\u044F!");
		label_5.setBounds(163, 231, 187, 14);
		panel.add(label_5);
		
		
		label_id_hidden.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_id_hidden.setBounds(153, 11, 122, 14);
		panel.add(label_id_hidden);
	}
	
	//Вид формы при добавлении нового материала
	public void materialDiaInsert() {
		label_id_hidden.setText("Новый материал");
		textField_name.setText("");
		textField_ed_izm.setText("");
		textField_price.setText("");
		textField_kolvo.setText("");
	}
	
	//Вид формы при изменении материала
	public void materialDiaUpdate(Material m) {
		label_id_hidden.setText(Integer.toString(m.getId_materiala()));
		textField_name.setText(m.getNazvanie());
		textField_ed_izm.setText(m.getEdinica_izmereniya());
		textField_price.setText(m.getPrice().toString());
		textField_kolvo.setText(m.getKolvo().toString());
	}
}
