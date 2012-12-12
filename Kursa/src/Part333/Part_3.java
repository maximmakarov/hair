package Part333;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ShopException extends Exception {
	public ShopException(String message) {
		super(message);
}}

class VideoTechnikException extends Exception {
	public VideoTechnikException(String message) {
		super(message);
}}


class Shop implements Serializable{
	private String name;
	
	public Shop(String newName) throws ShopException{
		if(newName.equals("")) throw new ShopException("Нельзя создать Shop - нет НАЗВАНИЯ!");
	name=newName;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String newName){
		name=newName;
	}
	
	private ArrayList videoTechnik=new ArrayList();
	
	
	public ArrayList getVideoTechnik(){					
		return videoTechnik;
	}
	public String poisk(String str, int chans){
		String s="";
		int j=1;
		for (int k=0; k<this.videoTechnik.size(); k++){
			VideoTechnik v=(VideoTechnik)this.videoTechnik.get(k);
			switch(chans){
			case 1:{
				if ((v.getName()).equals(str)){
					s=s+"Название: "+v.getName()+"   Производитель: "+v.getProizvod()+"\nЦена: "+v.getPrice()+"   Количество: "+v.getQuantity()+"\n";	
				}
			}
			break;
			case 2:{
				if ((v.getProizvod()).equals(str)){
					s=s+(j++)+") Название: "+v.getName()+"\nЦена: "+v.getPrice()+"   Количество: "+v.getQuantity()+"\n";	
				}
			}
			break;
			case 3:{
				if ((v.getPrice())<=(Integer.parseInt(str))){
					s=s+(j++)+") Название: "+v.getName()+"   Производитель: "+v.getProizvod()+"\nЦена: "+v.getPrice()+"   Количество: "+v.getQuantity()+"\n";	
				}
			}
			break;
			case 4:{
				if ((v.getQuantity())<=(Integer.parseInt(str))){
					s=s+(j++)+") Название: "+v.getName()+"   Производитель: "+v.getProizvod()+"\nЦена: "+v.getPrice()+"   Количество: "+v.getQuantity()+"\n";	
				}
			}
			break;
			}
			}	
		return s;
	}
	public void addVideoTechnik(VideoTechnik newVideoTechnik) throws VideoTechnikException{		
		if((newVideoTechnik.getName().equals("")) || (newVideoTechnik.getName()==null)) throw new VideoTechnikException("Нельзя добавить videoTechnik - нет НАЗВАНИЯ");
		
		for (int k=0; k<this.videoTechnik.size(); k++){
			VideoTechnik s=(VideoTechnik)this.videoTechnik.get(k);
			if ((s.getName())==newVideoTechnik.getName()) throw new VideoTechnikException ("Нельзя добавить videoTechnik - она уже существует!");
		}
	videoTechnik.add(newVideoTechnik);
	}
	public void removeVideoTechnik(VideoTechnik newVideoTechnik){			
		videoTechnik.remove(newVideoTechnik);
}}
	

class VideoTechnik  implements Serializable{
	private Shop shop;
	public Shop getShop(){
		return shop;
	}
	public void setShop(Shop newShop){
		shop=newShop;
	}
	
	public int toRachet(int kol_vo){
		int itog=this.getPrice()*kol_vo;
		return itog;
	}
	
	public void toBuy(int kol_vo){
		int quan=this.getQuantity();
		this.setQuantity(quan-(kol_vo));
	}
	
	public String toString(){
		String s;
		s=this.getName()+"   "+this.getProizvod()+"   "+this.getPrice()+"   "+this.getQuantity();
		return s;
	}

	private String name;
	private String proizvod;
	private int price;
	private int quantity;
	
	public VideoTechnik(String newName, String newProizvod, int newPrice, int newQuantity) throws VideoTechnikException{
		if((newName=="") || (newProizvod=="") || (newPrice==0) || (newQuantity==0)) 
			throw new VideoTechnikException("Нельзя создать VideoTechnik!\n- нет НАЗВАНИЯ или ПРОИЗВОДИТЕЛЯ\n- ЦЕНА=0 или КОЛИЧЕСТВО=0");
		name=newName;
		proizvod=newProizvod;
		price=newPrice;
		quantity=newQuantity;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String newName){
		name=newName;
	}
	public String getProizvod(){
		return proizvod;
	}
	public void setProizvod(String newProizvod){
		proizvod=newProizvod;
	}
	public int getPrice(){
		return price;
	}
	public void setPrice(int newPrice){
		price=newPrice;
	}
	public int getQuantity(){
		return quantity;
	}
	public void setQuantity(int newQuantity){
		quantity=newQuantity;
}}


class MyFrame extends JFrame{
	Shop shop;
	JPanel panelLeft=new JPanel();
	JPanel panelButtons=new JPanel();
	JPanel panelPoisk=new JPanel();
	JPanel panelStatistic=new JPanel();
	JPanel panelRight=new JPanel();
	JPanel panel5=new JPanel();
	
	JTabbedPane tabbedPane= new JTabbedPane();
	
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	JLabel label3=new JLabel();
	JLabel label4=new JLabel();
	JLabel labelName=new JLabel();
	JLabel labelProizvod=new JLabel();
	JLabel labelPrice=new JLabel();
	JLabel labelQuantity=new JLabel();
	
	JList listBefore=new JList();
	JList listAfter=new JList();
	
	JTextField textName=new JTextField();
	JTextField textProizvod=new JTextField();
	JTextField textPrice=new JTextField();
	JTextField textQuantity=new JTextField();
	
	JScrollPane scrollPane1=new JScrollPane();
	JScrollPane scrollPane2=new JScrollPane();
	
	JButton buttonRachet=new JButton("Рассчитать стоимость");
	JButton buttonBuy=new JButton("Купить VideoTechnik");
	
	JRadioButton butName=new JRadioButton("Поиск по названию", false);
	JRadioButton butProizvod=new JRadioButton("Поиск по производителю", false);
	JRadioButton butPrice=new JRadioButton("Поиск по цене", false);
	JRadioButton butQuan=new JRadioButton("Поиск по количеству", false);
	JButton buttonPoisk=new JButton("Искать");
	ButtonGroup bg=new ButtonGroup();
	
	JMenuBar menuBar=new JMenuBar();
		JMenu menuVideo=new JMenu("VideoTechnik");
			JMenuItem menuItemNewVideo=new JMenuItem("Создать VideoTechnik");
			JMenuItem menuItemChangeVideo=new JMenuItem("Изменить VideoTechnik");
			JMenuItem menuItemDeleteVideo=new JMenuItem("Удалить VideoTechnik");
			
	JFileChooser fileChooser=new JFileChooser(".");
		JMenu menuFile=new JMenu("File");
			JMenuItem menuItemNewFile= new JMenuItem("Создать Shop");
			JMenuItem menuItemOpenFile= new JMenuItem("Открыть File");
			JMenuItem menuItemSaveFile= new JMenuItem("Сохранить File");
		JMenu menuOtchet= new JMenu("Otchet");
			JMenuItem menuItemOpenOtchet= new JMenuItem("Открыть Отчет");
			JMenuItem menuItemSaveOtchet= new JMenuItem("Сохранить Отчет");
		
	NewFileActionListener newFileActListener=new NewFileActionListener();
	OpenFileActionListener openFileActListener=new OpenFileActionListener();
	SaveFileActionListener saveFileActListener=new SaveFileActionListener();
	
	OpenOtchetActionListener openOtchetActListener=new OpenOtchetActionListener();
	SaveOtchetActionListener saveOtchetActListener=new SaveOtchetActionListener();
	
	NewVideoActionListener newVideoDiaActListener=new NewVideoActionListener();
	ChangeVideoActionListener changeVideoDiaActListener=new ChangeVideoActionListener();
	DeleteVideoActionListener delVideoActListener1=new DeleteVideoActionListener();
		
	ListBeforeListener listBeforeListener=new ListBeforeListener();	
	ListAfterListener listAfterListener=new ListAfterListener();	
	ArrayList prodanVideo=new ArrayList();
	
	ButtonsActionListener buttonsActListener=new ButtonsActionListener();
	ButtonPoiskActionListener butPoiskActListener=new ButtonPoiskActionListener();
	ButtonActionListener1 buttonActionListener1=new ButtonActionListener1();
	ButtonActionListener2 buttonActionListener2=new ButtonActionListener2();
	
	public MyFrame(){
		this.setSize(900, 600);
		this.setDefaultCloseOperation(2);
		this.setTitle("");
		
		panelLeft.setPreferredSize(new Dimension (250,0));
		panelStatistic.setPreferredSize(new Dimension (0, 30));
		panelRight.setPreferredSize(new Dimension (280,0));
		
		panelLeft.setBackground(Color.orange);
		panelRight.setBackground(Color.orange);
		panelButtons.setBackground(Color.yellow);
		panelPoisk.setBackground(Color.yellow);
		panelStatistic.setBackground(Color.cyan);
		
		this.getContentPane().add(panelLeft, BorderLayout.WEST);
	    this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		this.getContentPane().add(panelStatistic, BorderLayout.SOUTH);
		this.getContentPane().add(panelRight, BorderLayout.EAST); 
		
		tabbedPane.add(panelButtons, "Information about Videotechnik");
		tabbedPane.add(panelPoisk, "  Poisk  ");
		
		label1.setText("VideoTechnik в наличии");
		label1.setPreferredSize(new Dimension(150,30));
		panelLeft.add(label1);
		
		label3.setText("Проданная VideoTechnik");
		label3.setPreferredSize(new Dimension(150,30));
		panelRight.add(label3);
		
		label4.setText("Параметры VideoTechnik");
		label4.setPreferredSize(new Dimension(300,30));
		panelButtons.add(label4);
		
		label2.setText("Количество наименований: ");
		label2.setPreferredSize(new Dimension(750,27));
		panelStatistic.add(label2);

		listBefore.setBackground(Color.green);
		listBefore.setPreferredSize(new Dimension(230,450));
		listAfter.setBackground(Color.green);
		listAfter.setPreferredSize(new Dimension(280,450));
		
		scrollPane1.getViewport().add(listBefore);
		scrollPane1.setPreferredSize(new Dimension(220,360));
		scrollPane2.getViewport().add(listAfter);
		scrollPane2.setPreferredSize(new Dimension(250,360));
		panelLeft.add(scrollPane1, BorderLayout.CENTER);
		panelRight.add(scrollPane2);
		
		labelName.setText("Название");
		labelName.setPreferredSize(new Dimension(150,30));
		labelProizvod.setText("Производитель");
		labelProizvod.setPreferredSize(new Dimension(150,30));
		labelPrice.setText("Цена");
		labelPrice.setPreferredSize(new Dimension(150,30));
		labelQuantity.setText("Количество");
		labelQuantity.setPreferredSize(new Dimension(150,30));
		
		textName.setPreferredSize(new Dimension(170,30));
		textProizvod.setPreferredSize(new Dimension(170,30));
		textPrice.setPreferredSize(new Dimension(170,30));
		textQuantity.setPreferredSize(new Dimension(170,30));
		
		panelButtons.add(labelName);
		panelButtons.add(textName);
		panelButtons.add(labelProizvod);
		panelButtons.add(textProizvod);
		panelButtons.add(labelPrice);
		panelButtons.add(textPrice);
		panelButtons.add(labelQuantity);
		panelButtons.add(textQuantity);
		panelButtons.add(buttonRachet);
		panelButtons.add(buttonBuy);
		
		bg.add(butName);
		bg.add(butProizvod);
		bg.add(butPrice);
		bg.add(butQuan);
		
		butName.addActionListener(buttonsActListener);
		butProizvod.addActionListener(buttonsActListener);
		butPrice.addActionListener(buttonsActListener);
		butQuan.addActionListener(buttonsActListener);
		buttonPoisk.addActionListener(butPoiskActListener);
		
		butName.setPreferredSize(new Dimension(250,30));
		butProizvod.setPreferredSize(new Dimension(250,30));
		butPrice.setPreferredSize(new Dimension(250,30));
		butQuan.setPreferredSize(new Dimension(250,30));
		buttonPoisk.setPreferredSize(new Dimension(150,30));
		
		butName.setBackground(Color.yellow);
		butProizvod.setBackground(Color.yellow);
		butPrice.setBackground(Color.yellow);
		butQuan.setBackground(Color.yellow);
		
		panelPoisk.add(butName);
		panelPoisk.add(butProizvod);
		panelPoisk.add(butPrice);
		panelPoisk.add(butQuan);
		panelPoisk.add(buttonPoisk);
		
		this.setJMenuBar(menuBar);
		menuBar.add(menuFile);
		menuBar.add(menuOtchet);
		menuBar.add(menuVideo);
		
		menuFile.add(menuItemNewFile);
			menuItemNewFile.addActionListener(newFileActListener);
		menuFile.add(menuItemOpenFile);
			menuItemOpenFile.addActionListener(openFileActListener);
		menuFile.add(menuItemSaveFile);
			menuItemSaveFile.addActionListener(saveFileActListener);
			
		menuOtchet.add(menuItemOpenOtchet);
			menuItemOpenOtchet.addActionListener(openOtchetActListener);
		menuOtchet.add(menuItemSaveOtchet);
			menuItemSaveOtchet.addActionListener(saveOtchetActListener);
		
		menuVideo.add(menuItemNewVideo);
			menuItemNewVideo.addActionListener(newVideoDiaActListener);
		menuVideo.add(menuItemChangeVideo);
			menuItemChangeVideo.addActionListener(changeVideoDiaActListener);
		menuVideo.add(menuItemDeleteVideo);
			menuItemDeleteVideo.addActionListener(delVideoActListener1);
					
		buttonRachet.setPreferredSize(new Dimension(200, 30));
		buttonRachet.addActionListener(buttonActionListener1);
		buttonBuy.setPreferredSize(new Dimension(200, 30));
		buttonBuy.addActionListener(buttonActionListener2);
	
		listBefore.addListSelectionListener(listBeforeListener);
		listAfter.addListSelectionListener(listAfterListener);
		
		buttonPoisk.setEnabled(false);
		buttonRachet.setEnabled(false);
		buttonBuy.setEnabled(false);
		menuItemSaveFile.setEnabled(false);
		menuVideo.setEnabled(false);
		menuItemSaveOtchet.setEnabled(false);
		
		this.setVisible(true);
}
	
//	Action for list of Before
	class ListBeforeListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent a){
			if(listBefore.getSelectedValue()!=null){
				VideoTechnik v=(VideoTechnik)listBefore.getSelectedValue();
			
				textName.setText(v.getName());
				textProizvod.setText(v.getProizvod());
				textPrice.setText(String.valueOf(v.getPrice()));
				textQuantity.setText(String.valueOf(v.getQuantity()));
			}
			else{
				textName.setText("");
				textProizvod.setText("");
				textPrice.setText("");
				textQuantity.setText("");
			
			}
	}}
	
//	Action for list of After
	class ListAfterListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent a){
			if(listAfter.getSelectedValue()!=null){	}
	}}
	
	class ButtonsActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((e.getSource()==butName) || (e.getSource()==butProizvod) || (e.getSource()==butPrice) || (e.getSource()==butQuan)){
				buttonPoisk.setEnabled(true);
			}
		}
	}
	
//Action for button "Poisk"	
	class ButtonPoiskActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(shop!=null & shop.getVideoTechnik().size()!=0){
				try{
				if(butName.isSelected()){
					String string=JOptionPane.showInputDialog(panel5, "Введите название");
					if(string.equals("")){}
					else
					JOptionPane.showMessageDialog(panel5, shop.poisk(string, 1));
				}
				else
				if(butProizvod.isSelected()){
					String string=JOptionPane.showInputDialog(panel5, "Введите производителя");
					if(string.equals("")){}
					else
					JOptionPane.showMessageDialog(panel5, shop.poisk(string, 2));
				}
				else
				if(butPrice.isSelected()){
					String string=JOptionPane.showInputDialog(panel5, "Введите цену");
					JOptionPane.showMessageDialog(panel5, shop.poisk(string, 3));
				}
				else
				if(butQuan.isSelected()){
					String string=JOptionPane.showInputDialog(panel5, "Введите количество");
					JOptionPane.showMessageDialog(panel5, shop.poisk(string, 4));
				}
				}
				catch(NumberFormatException ee){}
				catch(NullPointerException ex){}
				
				butName.setSelected(false);
				butProizvod.setSelected(false);
				butPrice.setSelected(false);
				butQuan.setSelected(false);
				buttonPoisk.setEnabled(false);
			}
	}}
	
//	Action for button "Rasshet"	
	class ButtonActionListener1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(listBefore.getSelectedValue()!=null){
				VideoTechnik v=(VideoTechnik)listBefore.getSelectedValue();
				String str=JOptionPane.showInputDialog(panel5, "Количество: ");
				
				try{
					JOptionPane.showMessageDialog(panel5, "Сумма: "+String.valueOf(v.toRachet(Integer.parseInt(str))));
				}
				catch(NumberFormatException a){	}
				catch (NullPointerException aa) {}
			}
			else JOptionPane.showMessageDialog(panel5,
					"Не выбрана VideoTechnik!!!\nPlease, try again.");
	}}
	class ButtonActionListener2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(listBefore.getSelectedValue()!=null){
				try{
					VideoTechnik v=(VideoTechnik)listBefore.getSelectedValue();
					String str=JOptionPane.showInputDialog(panel5, "Количество: ");
					
					String nam=v.getName();
					String proiz=v.getProizvod();
					int pric=v.getPrice();
					int quan=Integer.parseInt(str);
					VideoTechnik vid=new VideoTechnik(nam,proiz,pric,quan);
					
					Calendar calend=Calendar.getInstance();
					String am_pm="";
					
					if(Integer.parseInt(str)>v.getQuantity()){
						JOptionPane.showMessageDialog(panel5, "Нет в наличии "+str+" VideoTechnik");
					}
					else 
						if(v.getQuantity()==Integer.parseInt(str)){
							shop.removeVideoTechnik(v);
							if(calend.get(Calendar.AM_PM)==0)	{am_pm="PM";}
							else am_pm="AM";
							if(calend.get(Calendar.MONTH)<=9){
								prodanVideo.add(String.valueOf((calend.get(Calendar.DATE)+"."+("0"+(1+calend.get(Calendar.MONTH)))+" ")+(7+calend.get(Calendar.HOUR))+":"+calend.get(Calendar.MINUTE)+" "+am_pm)+" - "+vid);
							}
							else prodanVideo.add(String.valueOf((calend.get(Calendar.DATE)+"."+(1+calend.get(Calendar.MONTH))+" ")+(7+calend.get(Calendar.HOUR))+":"+calend.get(Calendar.MINUTE)+" "+am_pm)+" - "+vid);
						}
						else	
							if(v.getQuantity()!=Integer.parseInt(str)){
								v.toBuy(Integer.parseInt(str));	
							if(calend.get(Calendar.AM_PM)==0)	am_pm="PM";
							else am_pm="AM";
							if(calend.get(Calendar.MONTH)<=9){
								prodanVideo.add(String.valueOf((calend.get(Calendar.DATE)+"."+("0"+(1+calend.get(Calendar.MONTH)))+" ")+(7+calend.get(Calendar.HOUR))+":"+calend.get(Calendar.MINUTE)+" "+am_pm)+" - "+vid);
							}
							else prodanVideo.add(String.valueOf((calend.get(Calendar.DATE)+"."+(1+calend.get(Calendar.MONTH))+" ")+(7+calend.get(Calendar.HOUR))+":"+calend.get(Calendar.MINUTE)+" "+am_pm)+" - "+vid);
						
							}
					
					listAfter.setListData(prodanVideo.toArray());
					//textQuantity.setText(String.valueOf(v.getQuantity()));
					listBefore.setListData(shop.getVideoTechnik().toArray());
					label2.setText("Количество наименований: "+shop.getVideoTechnik().size());
					
				}
				catch(VideoTechnikException a){
					JOptionPane.showMessageDialog(panel5, a.getMessage());
				}
				catch(NumberFormatException a){	}
				catch (NullPointerException aa) {}
			}
			else JOptionPane.showMessageDialog(panel5,
					"Не выбрана VideoTechnik!!!\nPlease, try again.");
	}}
	
	
//	Action for menuItem "New VideoTechnik"
	class NewVideoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==menuItemNewVideo){
				VideoDialog videoDia=new VideoDialog();
				
				menuItemChangeVideo.setEnabled(true);
				menuItemDeleteVideo.setEnabled(true);
				menuItemSaveFile.setEnabled(true);
				buttonRachet.setEnabled(true);
				buttonBuy.setEnabled(true);
				menuItemSaveOtchet.setEnabled(true);
			}
	}}	
//	Action for menuItem "Change VideoTechnik"
	class ChangeVideoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {	
			if(e.getSource()==menuItemChangeVideo && listBefore.getSelectedValue()!=null){
				VideoDialog changevideoDia=new VideoDialog((VideoTechnik)listBefore.getSelectedValue());
			}
			else JOptionPane.showMessageDialog(panel5,
				"Не выбрана VideoTechnik!!!\nPlease, try again.");
	}}
//	Action for menuItem "Delete VideoTechnik"
	class DeleteVideoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(listBefore.getSelectedValue()!=null){
				if(JOptionPane.showConfirmDialog(panel5, "You really delete VideoTechnik?", JOptionPane.MESSAGE_PROPERTY, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					VideoTechnik v=(VideoTechnik)listBefore.getSelectedValue();
					shop.removeVideoTechnik(v);
					listBefore.setListData(shop.getVideoTechnik().toArray());
					label2.setText("Количество наименований: "+shop.getVideoTechnik().size());
				}
			}
			else JOptionPane.showMessageDialog(panel5,
			"Не выбрана VideoTechnik!!!\nPlease, try again.");
	}}
	
	class NewFileActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==menuItemNewFile){
				try{
					String str=JOptionPane.showInputDialog(panel5, "Название shop: ");
			
				try{
					shop=new Shop(str);
				}
				catch(ShopException a){
					JOptionPane.showMessageDialog(panel5, a.getMessage());
				}
					MyFrame.this.setTitle(str);
					
					listBefore.setListData(shop.getVideoTechnik().toArray());
					ArrayList arList=new ArrayList();
					listAfter.setListData(arList.toArray());
					label2.setText("Количество наименований: "+shop.getVideoTechnik().size());

					menuVideo.setEnabled(true);
					menuItemChangeVideo.setEnabled(false);
					menuItemDeleteVideo.setEnabled(false);
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(panel5, ex.getMessage());
				}
			}
	}}
	
	class OpenFileActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==menuItemOpenFile){
			if (fileChooser.showOpenDialog(panel5)==JFileChooser.APPROVE_OPTION){
				try{
					FileInputStream fileIS = new FileInputStream(fileChooser.getSelectedFile().getPath());
					ObjectInputStream objectIS = new ObjectInputStream(fileIS);
					shop =(Shop)objectIS.readObject();
					objectIS.close();
					fileIS.close();
					
					MyFrame.this.setTitle("Shop: " + shop.getName());
					listBefore.setListData(shop.getVideoTechnik().toArray());
					ArrayList arList=new ArrayList();
					listAfter.setListData(arList.toArray());
					
					label2.setText("Количество наименований: "+shop.getVideoTechnik().size());
					
					menuVideo.setEnabled(true);
					menuItemSaveFile.setEnabled(true);
					buttonRachet.setEnabled(true);
					buttonBuy.setEnabled(true);
					menuItemSaveOtchet.setEnabled(true);
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(panel5, "I don't know this EXCEPTION\n"+ex.getMessage());}
				}
			else 
				return;
			}
	}}
	class SaveFileActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==menuItemSaveFile){
			if (fileChooser.showSaveDialog(panel5)==JFileChooser.APPROVE_OPTION){
				try{
					FileOutputStream fileOS = new FileOutputStream(fileChooser.getSelectedFile().getPath());
					ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
					objectOS.writeObject(shop);
					objectOS.close();
					fileOS.close();
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(panel5, "I don't know this EXCEPTION\n"+ex.getMessage());
				}
			}
			else 
				return;
			}
	}}
	
	
	class OpenOtchetActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showOpenDialog(panel5)==JFileChooser.APPROVE_OPTION){
				try{
					String s="";	
					s=Zapros.readAllFromFile(fileChooser.getSelectedFile().getPath());
				
					JOptionPane.showMessageDialog(panel5,s);
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(panel5, "I don't know this EXCEPTION\n"+ex.getMessage());
				}
			}
			else 
				return;
	}}
	class SaveOtchetActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showSaveDialog(panel5)==JFileChooser.APPROVE_OPTION){
				try{
					FileOutputStream fileOutput = new FileOutputStream(fileChooser.getSelectedFile().getPath());
					String s="Shop: "+shop.getName()+"\n";
					s=s+"VideoTechnik v nalichii\n";
					for (int i=0; i<shop.getVideoTechnik().size(); i++){	
						VideoTechnik video=(VideoTechnik)shop.getVideoTechnik().get(i);
						s=s+"---";
						s=s+"Name "+video.getName()+"     Proizvoditel: "+video.getProizvod()+"\n";
						s=s+"    Price: "+video.getPrice()+"     Quantity: "+video.getQuantity()+"\n";
					}	
					s=s+"\nProdannaya VideoTechnik\n";
					for(int j=0; j<prodanVideo.size(); j++){
						s=s+prodanVideo.get(j)+"\n";
						}
					
					fileOutput.write(s.getBytes());
					fileOutput.close();
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(panel5, "I don't know this EXCEPTION\n"+ex.getMessage());
				}
			}
			else 
				return;
	}}

	
	class VideoDialog extends JDialog{
		JPanel p1=new JPanel();
		
		VideoTechnik v=null;
		
		JLabel l=new JLabel();
		JLabel label_Name=new JLabel();
		JLabel label_Proizvod=new JLabel();
		JLabel label_Price=new JLabel();
		JLabel label_Quantity=new JLabel();
		
		JTextField text_Name=new JTextField();
		JTextField text_Proizvod=new JTextField();
		JTextField text_Price=new JTextField();
		JTextField text_Quantity=new JTextField();
		
		JButton butOk= new JButton("OK");
		JButton butCancel= new JButton("Cancel");
		
	//Konstructor VideoDialog
		public VideoDialog(){
			this.setSize(400, 300);
			this.setTitle("Создать VideoTechnik");
			this.setModal(true);
			
			p1.setPreferredSize(new Dimension (200,200));
			p1.setBackground(Color.orange);
			this.getContentPane().add(p1);
			
			l.setForeground(Color.red);
			l.setText("Параметры VideoTechnik");
			l.setPreferredSize(new Dimension(300,30));
			label_Name.setText("Название");
			label_Name.setPreferredSize(new Dimension(130,30));
			label_Proizvod.setText("Производитель");
			label_Proizvod.setPreferredSize(new Dimension(130,30));
			label_Price.setText("Цена");
			label_Price.setPreferredSize(new Dimension(130,30));
			label_Quantity.setText("Количество");
			label_Quantity.setPreferredSize(new Dimension(130,30));
			
			text_Name.setPreferredSize(new Dimension(200,30));
			text_Proizvod.setPreferredSize(new Dimension(200,30));
			text_Price.setPreferredSize(new Dimension(200,30));
			text_Quantity.setPreferredSize(new Dimension(200,30));
			
			butCancel.setPreferredSize(new Dimension(110, 30));
			butOk.setPreferredSize(new Dimension(60, 30));
			
		//Action button "Cancel"
			butCancel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}	
			});
			
		//Action button "OK"
			butOk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(shop!=null){
						try{
							String n=text_Name.getText();
							String proiz=text_Proizvod.getText();
							int pric=Integer.parseInt(text_Price.getText());
							int quant=Integer.parseInt(text_Quantity.getText());
							
							VideoTechnik vid=new VideoTechnik(n,proiz,pric,quant);
							
							shop.addVideoTechnik(vid);
							vid.setShop(shop);
							listBefore.setListData(shop.getVideoTechnik().toArray());
							label2.setText("Количество наименований: "+shop.getVideoTechnik().size());
							text_Name.setText("");
							text_Proizvod.setText("");
							text_Price.setText("");
							text_Quantity.setText("");
							dispose();
						}
						catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(panel5, 
								"Неправильный формат!!!\nPlease, try again.");
						}
						catch(VideoTechnikException ee){
							JOptionPane.showMessageDialog(panel5, ee.getMessage());
						}
					}
				}	
			});
			 
			p1.add(l);
			p1.add(label_Name);
			p1.add(text_Name);
			p1.add(label_Proizvod);
			p1.add(text_Proizvod);
			p1.add(label_Price);
			p1.add(text_Price);
			p1.add(label_Quantity);
			p1.add(text_Quantity);
			p1.add(butOk);	
			p1.add(butCancel);	
			this.setVisible(true);
	}
			
	//Konstructor VideoDialog(VideoTechnik vid)
		public VideoDialog(VideoTechnik vid){
			this.setSize(400, 300);
			this.setTitle("Изменение VideoTechnik");
			this.setModal(true);
		
			p1.setPreferredSize(new Dimension (200,200));
			p1.setBackground(Color.orange);
			this.getContentPane().add(p1);
			
			l.setForeground(Color.red);
			l.setText("Параметры VideoTechnik");
			l.setPreferredSize(new Dimension(300,30));
			label_Name.setText("Название");
			label_Name.setPreferredSize(new Dimension(130,30));
			label_Proizvod.setText("Производитель");
			label_Proizvod.setPreferredSize(new Dimension(130,30));
			label_Price.setText("Цена");
			label_Price.setPreferredSize(new Dimension(130,30));
			label_Quantity.setText("Количество");
			label_Quantity.setPreferredSize(new Dimension(130,30));
			
			text_Name.setPreferredSize(new Dimension(200,30));
			text_Proizvod.setPreferredSize(new Dimension(200,30));
			text_Price.setPreferredSize(new Dimension(200,30));
			text_Quantity.setPreferredSize(new Dimension(200,30));
			
			butCancel.setPreferredSize(new Dimension(110, 30));
			butOk.setPreferredSize(new Dimension(60, 30));
			
			p1.add(l);
			p1.add(label_Name);
			p1.add(text_Name);
			p1.add(label_Proizvod);
			p1.add(text_Proizvod);
			p1.add(label_Price);
			p1.add(text_Price);
			p1.add(label_Quantity);
			p1.add(text_Quantity);
			p1.add(butOk);	
			p1.add(butCancel);	
			
			v=vid;
			text_Name.setText(String.valueOf(v.getName()));
			text_Proizvod.setText(String.valueOf(v.getProizvod()));
			text_Price.setText(String.valueOf(v.getPrice()));
			text_Quantity.setText(String.valueOf(v.getQuantity()));
			
		//Action button "Cancel"
			butCancel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}	
			});
			
		//Action button "OK"
			butOk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(shop!=null){
						try{
							String n=text_Name.getText();
							String proiz=text_Proizvod.getText();
							int pric=Integer.parseInt(text_Price.getText());
							int quant=Integer.parseInt(text_Quantity.getText());
							
							VideoTechnik vid=new VideoTechnik(n,proiz,pric,quant);
							
							vid.setShop(shop);
							
							shop.removeVideoTechnik(v);
							shop.addVideoTechnik(vid);
							listBefore.setListData(shop.getVideoTechnik().toArray());
							label2.setText("Количество наименований: "+shop.getVideoTechnik().size());
							dispose();
						}
						
						catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(panel5, 
								"Неправильный формат!!!\nPlease, try again.");
						}
						catch(VideoTechnikException ee){
							JOptionPane.showMessageDialog(panel5, ee.getMessage());
						}
					}
				}	
			});	 
			this.setVisible(true);
	}}
	
}


class Zapros{
    public static void poisk(String str, int chans, String fileName) throws ClassNotFoundException,IOException{
        RandomAccessFile raf=new RandomAccessFile(fileName, "r");
        String s=null;
        int j=1;
        int f=0;
        while ((s=raf.readLine())!=null) {
            StringTokenizer st=new StringTokenizer(s, "\t");
            while (st.hasMoreTokens()) {
            	String  name=st.nextToken();
            	String proizvoditel=st.nextToken();	
            	int price=Integer.parseInt(st.nextToken());	
            	int quantity=Integer.parseInt(st.nextToken());	
            	
            	switch (chans) {
				case 1:
					if (str.equalsIgnoreCase(name)){
						f=1;
						System.out.println("Производитель: "+proizvoditel);
	            		System.out.println("Цена: "+price+"\tКоличество: "+quantity);	
					}
					break;
				case 2:
					if (str.equalsIgnoreCase(proizvoditel)){
						f=1;
						System.out.println((j++)+") Название: "+name);
						System.out.println("Цена: "+price+"\tКоличество: "+quantity);
					}
					break;
            	}
            } 	
		}
		raf.close();
		if(f==0){
			System.out.println("Не найдена видеотехника: "+str);
		}
    }
  
    
    public static void change(String nameOfPoisk, String editZapis, int chans, String fileName) throws Exception{
    	FileReader fr=new FileReader(fileName);
    	BufferedReader br=new BufferedReader(fr);
    	int f=0;
    	String str="";
    	String s="";
    	while ((str=br.readLine())!=null) {
    		StringTokenizer st=new StringTokenizer(str, "\t");
    		while (st.hasMoreTokens()) {
    			String  name=st.nextToken();
    			String proizvoditel=st.nextToken();	
            	int price=Integer.parseInt(st.nextToken());	
            	int quantity=Integer.parseInt(st.nextToken());
            	
    			if (nameOfPoisk.equalsIgnoreCase(name)) {
    				f=1;
                switch (chans){
    				case 1:{
    						name=editZapis;
    					}
    				break;
    				case 2:{
    						proizvoditel=editZapis;	
    					}
    				break;
    				case 3:{
    						price=Integer.parseInt(editZapis);
    					}
    				break;
    				case 4:{
    						quantity=Integer.parseInt(editZapis);
    					}
    				break;
                }
    			}
    			s=s+"\n"+name+"\t"+proizvoditel+"\t"+price+"\t"+quantity;
    		}
    	}
    	br.close();
    	fr.close();
    	
    	if(f==0){
			System.out.println("Не найдена видеотехника: "+nameOfPoisk);
			return;
		}
    	
    	FileOutputStream fos=new FileOutputStream(fileName);
    	fos.write(s.getBytes());
    	fos.close();	
    }
    
    public static void addNewZapis(String newName, String newProizvoditel, int newPrice, int newQuantity, String fileName) throws IOException{
        RandomAccessFile raf=new RandomAccessFile(fileName, "rw");
        String s=null;
        s ="\n"+newName+"\t"+newProizvoditel+"\t"+newPrice+"\t"+newQuantity;
        raf.seek(raf.length());
        raf.write(s.getBytes());
        raf.close();
}

	public static String readAllFromFile(String fileName) throws IOException{
		String s="";	
		String str=null;	
		RandomAccessFile raf=new RandomAccessFile(fileName, "r");
		while((str=raf.readLine())!=null){
			s=s+str+"\n";
		}
		raf.close();	
		return s;
}}


class Vvod{
	public static String vvodStroki(){
		byte x[]=new byte[30];
		try{System.in.read(x);}
		catch(IOException ex){
			System.out.println("Warning! IOException!");
		}
		String s=new String(x);
		s=s.trim();
		return s;		
	}
	
	public static int vvodChisla(){
		byte x[]=new byte[30];
		try{System.in.read(x);}
		catch(IOException ex){
			System.out.println("Warning! IOException!");
		}
		String s=new String(x);
		s=s.trim();
		int y=0;
		
		try{y=Integer.parseInt(s);}
		catch(NumberFormatException ex){
			System.out.println("Введены неверные значения");
			System.out.println("Please, try again :)");
			y=vvodChisla();
		}	
		return y;	
}}


public class Part_3 {
public static void main(String args[]){
	try{
		MyFrame frame=new MyFrame();	
	}
	catch(Exception xx){
		System.out.println(xx.getMessage());
	}
}}