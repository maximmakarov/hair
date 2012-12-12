package pacman;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class KvalificationDialog extends JDialog{
	private JTextField textField;
	private JTextField textField_1;
	public KvalificationDialog() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 260, 210);
		getContentPane().setLayout(null);
		setModal(true);
		JLabel label = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043A\u0432\u0430\u043B\u0438\u0444\u0438\u043A\u0430\u0446\u0438\u0438");
		label.setBounds(10, 11, 173, 14);
		getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 156, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button_add = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		button_add.setBounds(10, 129, 89, 23);
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().equals("")){
					try {
					
					DBClass dbClass=new DBClass();
					Kvalification d=new Kvalification();
					d.setNazvanie_kvalification(textField.getText());
					if(textField_1.getText().equals("")){
						d.setPercent(1);
					}
					else d.setPercent(Double.parseDouble(textField_1.getText()));
					dbClass.kvalificationUpsert(d, "INSERT");
					SotrudnikDialog.updateKvalification();
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
				}
				else JOptionPane.showMessageDialog(null, "Не введены данные!");
			}
		});
		getContentPane().add(button_add);
		
		JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_cancel.setBounds(109, 129, 89, 23);
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		getContentPane().add(button_cancel);
		
		JLabel label_1 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u0440\u043E\u0446\u0435\u043D\u0442 \u0437/\u043F\u043B\u0430\u0442\u044B");
		label_1.setBounds(10, 67, 156, 14);
		getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(13, 92, 86, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KvalificationDialog frame = new KvalificationDialog();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
