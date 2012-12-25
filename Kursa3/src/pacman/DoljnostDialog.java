package pacman;

import java.awt.*;
import java.awt.event.*;

import java.sql.SQLException;



import javax.swing.*;



@SuppressWarnings("serial")
public class DoljnostDialog extends JDialog{
	private JTextField textField;
	public DoljnostDialog() {
//		getContentPane().setBackground(new Color(255, 204, 255));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 260, 150);
		getContentPane().setLayout(null);
		setModal(true);
		JLabel label = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u0438");
		label.setBounds(10, 11, 221, 14);
		getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 156, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button_add = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		button_add.setBounds(10, 67, 106, 23);
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().equals("")){
				try {
					DBClass dbClass=new DBClass();
					Doljnost d=new Doljnost();
					d.setNazvanie_doljnosti(textField.getText());
					dbClass.doljnostUpsert(d, "INSERT");
					SotrudnikDialog.updateDoljnost();
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}}
				else JOptionPane.showMessageDialog(null, "Не введены данные!");
				
			}
		});
		getContentPane().add(button_add);
		
		JButton button_cancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		button_cancel.setBounds(125, 67, 106, 23);
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		getContentPane().add(button_cancel);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoljnostDialog frame = new DoljnostDialog();
					frame.setVisible(true);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
