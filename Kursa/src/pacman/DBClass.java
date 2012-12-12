package pacman;
import java.sql.*;
import java.util.ArrayList;

	public class DBClass {
		private Connection conn;
		private String url;
		private Statement stmt;
		private ResultSet rs;
		private PreparedStatement stat;
		
		//Конструктор, создающий и открывающий соединение
		public DBClass() throws SQLException, ClassNotFoundException {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				url = "jdbc:odbc:MyDataSource";
				conn = DriverManager.getConnection(url, "", "");
			} 
			catch (Exception e) {
				System.out.println("*** Error: " + e.getMessage());
			}
		}
		
		//Метод для закрытия соединения
		public void close() throws SQLException {
			conn.close();
		}

//Сотрудник-----
		//Метод, который берет информацию о сотрудниках из БД и сохраняет ее в класс
		public ArrayList<Sotrudnik> sotrudnikFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT s.id_sotrudnika, s.familiya, s.imya, s.otchestvo, " +
					"s.date, s.phone, s.id_doljnosti, s.id_kvalification,  d.nazvanie " +
					"FROM Sotrudnik s, Doljnost d WHERE s.id_doljnosti=d.id_doljnosti");
					
			ArrayList<Sotrudnik> sotrList=new ArrayList<Sotrudnik>();
			while (rs.next())
			{
				Sotrudnik s=new Sotrudnik();
				s.setId_sotrudnika(rs.getInt(1));
				s.setFamiliya(rs.getString(2));
				s.setImya(rs.getString(3));
				s.setOtchestvo(rs.getString(4));
				s.setData_priema(rs.getDate(5));
				s.setPhone(rs.getString(6));
				s.setId_doljnosti(rs.getInt(7));
				s.setId_kvalification(rs.getInt(8));
				s.setDoljnost(rs.getString(9));
				sotrList.add(s);
			}
			this.close();
			return sotrList;
		}
		
//****************************************************		
		public Sotrudnik sotrudnikFromDB(Content_zakaza cz) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT s.id_sotrudnika, s.familiya, s.imya, s.otchestvo, " +
					"s.date, s.phone, s.id_doljnosti, s.id_kvalification,  d.nazvanie " +
					"FROM Sotrudnik s, Doljnost d " +
					"WHERE s.id_doljnosti=d.id_doljnosti AND s.id_sotrudnika="+cz.getId_sotrudnika()+"");
					
			Sotrudnik s=new Sotrudnik();
			while (rs.next())
			{
				s.setId_sotrudnika(rs.getInt(1));
				s.setFamiliya(rs.getString(2));
				s.setImya(rs.getString(3));
				s.setOtchestvo(rs.getString(4));
				s.setData_priema(rs.getDate(5));
				s.setPhone(rs.getString(6));
				s.setId_doljnosti(rs.getInt(7));
				s.setId_kvalification(rs.getInt(8));
			}
			this.close();
			return s;
		}
//****************************************************	
		
		//Занесение или изменение данных о сотруднике в БД
		public void sotrudnikUpsert(Sotrudnik s, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Sotrudnik(familiya, imya, otchestvo, phone, id_doljnosti, id_kvalification) VALUES(?, ?, ?, ?, ?, ?)");
					
					stat.setString(1, s.getFamiliya());
					stat.setString(2, s.getImya());
					stat.setString(3, s.getOtchestvo());
					stat.setString(4, s.getPhone());
					stat.setInt(5, s.getId_doljnosti());
					stat.setInt(6, s.getId_kvalification());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Sotrudnik SET familiya = ?, imya = ?, otchestvo = ?, phone = ?, id_doljnosti = ?, id_kvalification = ? WHERE id_sotrudnika = ?");

					stat.setString(1, s.getFamiliya());
					stat.setString(2, s.getImya());
					stat.setString(3, s.getOtchestvo());
					stat.setString(4, s.getPhone());
					stat.setInt(5, s.getId_doljnosti());
					stat.setInt(6, s.getId_kvalification());
					stat.setInt(7, s.getId_sotrudnika());
				} catch (SQLException e) {
					System.out.println("SQL  "+e.getMessage());
					e.printStackTrace();
				}	
				catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Метод для удаления сотрудника из БД
		public void sotrudnikDelete(Sotrudnik s) {
			try {
				stat = conn.prepareStatement("DELETE from Sotrudnik WHERE id_sotrudnika = ? ");
				stat.setInt(1, s.getId_sotrudnika());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Метод поиска сотрудника по фамилии, имени, отчеству или по дате приема на работу
		public ArrayList<Sotrudnik> sotrudnikSearch(String param) throws SQLException{
			stmt = conn.createStatement();
			String ss="LIKE UCASE('%"+param+"%')";
			rs = stmt.executeQuery("SELECT s.id_sotrudnika, s.familiya, s.imya, s.otchestvo, " +
					"s.date, s.phone, d.nazvanie, s.id_doljnosti, s.id_kvalification " +
					"FROM Sotrudnik s, Doljnost d " +
					"WHERE (s.id_doljnosti=d.id_doljnosti AND " +
					"( UCASE(s.familiya) "+ss+" OR UCASE(s.imya) "+ss
					+" OR UCASE(s.otchestvo) "+ss+" OR UCASE(s.date) "+ss+" );");
			
			ArrayList<Sotrudnik> sotrList=new ArrayList<Sotrudnik>();
			while (rs.next())
			{
				Sotrudnik s=new Sotrudnik();
				s.setId_sotrudnika(rs.getInt(1));
				s.setFamiliya(rs.getString(2));
				s.setImya(rs.getString(3));
				s.setOtchestvo(rs.getString(4));
				s.setData_priema(rs.getDate(5));
				s.setPhone(rs.getString(6));
				s.setDoljnost(rs.getString(7));
				s.setId_doljnosti(rs.getInt(8));
				s.setId_kvalification(rs.getInt(9));
				sotrList.add(s);
			}
			this.close();
			return sotrList;
		}
		
//Должность-----
		//Метод, который берет информацию о должностях из БД и сохраняет ее в класс
		public ArrayList<Doljnost> doljnostFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_doljnosti, nazvanie FROM Doljnost");
					
			ArrayList<Doljnost> doljnostList=new ArrayList<Doljnost>();
			while (rs.next())
			{
				Doljnost d=new Doljnost();
				d.setId_doljnosti(rs.getInt(1));
				d.setNazvanie_doljnosti(rs.getString(2));
				doljnostList.add(d);
			}
			this.close();
			return doljnostList;
		}
		
//****************************************************			
		public Doljnost doljnostFromDB(Sotrudnik s) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_doljnosti, nazvanie FROM Doljnost WHERE id_doljnosti="+s.getId_doljnosti()+"");
					
			Doljnost d=new Doljnost();
			while (rs.next())
			{
				d.setId_doljnosti(rs.getInt(1));
				d.setNazvanie_doljnosti(rs.getString(2));
			}
			this.close();
			return d;
		}
//****************************************************	
		
		//Занесение или изменение данных о должности в БД
		public void doljnostUpsert(Doljnost d, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Doljnost(nazvanie) VALUES(?)");
					stat.setString(1, d.getNazvanie_doljnosti());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Doljnost SET nazvanie = ? WHERE id_doljnost = ?");
					stat.setString(1, d.getNazvanie_doljnosti());
					stat.setInt(2, d.getId_doljnosti());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления должности из БД
		public void doljnostDelete(Doljnost d) {
			try {
				stat = conn.prepareStatement("DELETE from Doljnost WHERE id_doljnosti = ? ");
				stat.setInt(1, d.getId_doljnosti());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
//Квалификация-----
		//Метод, который берет информацию о квалификации из БД и сохраняет ее в класс
		public ArrayList<Kvalification> kvalificationFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_kvalification, nazvanie, pct_salary FROM Kvalification");
					
			ArrayList<Kvalification> kvalificationList=new ArrayList<Kvalification>();
			while (rs.next())
			{
				Kvalification k=new Kvalification();
				k.setId_kvalification(rs.getInt(1));
				k.setNazvanie_kvalification(rs.getString(2));
				k.setPercent(rs.getDouble(3));
				kvalificationList.add(k);
			}
			this.close();
			return kvalificationList;
		}
		
//****************************************************		
		public Kvalification kvalificationFromDB(Sotrudnik s) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_kvalification, nazvanie, pct_salary FROM Kvalification " +
					"WHERE id_kvalification="+s.getId_kvalification()+"");
					
			Kvalification k=new Kvalification();
			while (rs.next())
			{
				k.setId_kvalification(rs.getInt(1));
				k.setNazvanie_kvalification(rs.getString(2));
				k.setPercent(rs.getDouble(3));
			}
			this.close();
			return k;
		}
//****************************************************	
		
		//Занесение или изменение данных о квалификации в БД
		public void kvalificationUpsert(Kvalification k, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Kvalification(nazvanie, pct_salary) VALUES(?, ?)");
					stat.setString(1, k.getNazvanie_kvalification());
					stat.setDouble(2, k.getPercent());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Kvalification SET nazvanie = ?, pct_salary = ? WHERE id_kvalification = ?");
					stat.setString(1, k.getNazvanie_kvalification());
					stat.setDouble(2, k.getPercent());
					stat.setInt(3, k.getId_kvalification());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления квалификации из БД
		public void kvalificationDelete(Kvalification k) {
			try {
				stat = conn.prepareStatement("DELETE from Kvalification WHERE id_kvalification = ? ");
				stat.setInt(1, k.getId_kvalification());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//Клиент-----
		//Метод, который берет информацию о клиенте из БД и сохраняет ее в класс
		public ArrayList<Client> clientFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_clienta, familiya, imya, otchestvo, sex, phone, constant_client FROM Client");
					
			ArrayList<Client> clientList=new ArrayList<Client>();
			while (rs.next())
			{
				Client k=new Client();
				k.setId_clienta(rs.getInt(1));
				k.setFamiliya(rs.getString(2));
				k.setImya(rs.getString(3));
				k.setOtchestvo(rs.getString(4));
				k.setSex(rs.getString(5));
				k.setPhone(rs.getString(6));
				k.setConstant_client((rs.getBoolean(7)));
				clientList.add(k);
			}
			this.close();
			return clientList;
		}
		
//****************************************************
		public Client clientFromDB(Zakaz z) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_clienta, familiya, imya, otchestvo, sex, phone, constant_client " +
					"FROM Client WHERE id_clienta="+z.getId_clienta()+"");
					
			Client c=new Client();
			while (rs.next())
			{
				c.setId_clienta(rs.getInt(1));
				c.setFamiliya(rs.getString(2));
				c.setImya(rs.getString(3));
				c.setOtchestvo(rs.getString(4));
				c.setSex(rs.getString(5));
				c.setPhone(rs.getString(6));
				c.setConstant_client((rs.getBoolean(7)));
			}
			this.close();
			return c;
		}
//****************************************************
		
		//Занесение или изменение данных о клиенте в БД
		public void clientUpsert(Client k, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Client(familiya, imya, otchestvo, sex, phone, constant_client) VALUES(?, ?, ?, ?, ?, ?)");
					stat.setString(1, k.getFamiliya());
					stat.setString(2, k.getImya());
					stat.setString(3, k.getOtchestvo());
					stat.setString(4, k.getSex());
					stat.setString(5, k.getPhone());
					stat.setBoolean(6, k.getConstant_client());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Client SET familiya = ?, imya = ?, otchestvo = ?, sex = ?, phone = ?, constant_client = ? WHERE id_clienta = ?");
					stat.setString(1, k.getFamiliya());
					stat.setString(2, k.getImya());
					stat.setString(3, k.getOtchestvo());
					stat.setString(4, k.getSex());
					stat.setString(5, k.getPhone());
					stat.setBoolean(6, k.getConstant_client());
					stat.setInt(7, k.getId_clienta());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления клиента из БД
		public void clientDelete(Client k) {
			try {
				stat = conn.prepareStatement("DELETE from Client WHERE id_clienta = ? ");
				stat.setInt(1, k.getId_clienta());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Метод поиска клиента по фамилии, имени или отчеству
		public ArrayList<Client> сlientSearch(String param) throws SQLException{
			stmt = conn.createStatement();
			String ss="LIKE UCASE('%"+param+"%')";
			rs = stmt.executeQuery("SELECT id_clienta,familiya,imya,otchestvo,sex,phone,constant_client FROM Client " +
					"WHERE ( UCASE(familiya) "+ss+" OR UCASE(imya) "+ss+" OR UCASE(otchestvo) "+ss+" );");
			
			ArrayList<Client> сlientList=new ArrayList<Client>();
			while (rs.next())
			{
				Client c=new Client();
				c.setId_clienta(rs.getInt(1));
				c.setFamiliya(rs.getString(2));
				c.setImya(rs.getString(3));
				c.setOtchestvo(rs.getString(4));
				c.setSex(rs.getString(5));
				c.setPhone(rs.getString(6));
				c.setConstant_client(rs.getBoolean(7));
				сlientList.add(c);
			}
			this.close();
			return сlientList;
		}
		
//Содержание заказа-----
		//Метод, который берет информацию о Содержании заказа из БД и сохраняет ее в класс
		public ArrayList<Content_zakaza> content_zakazaFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT cz.id_zakaza, cz.id_uslugi, cz.id_sotrudnika, u.nazvanie, s.familiya " +
					"FROM Content_zakaza cz, Usluga u, Sotrudnik s " +
					"WHERE cz.id_uslugi=u.id_uslugi AND cz.id_sotrudnika=s.id_sotrudnika");
					
			ArrayList<Content_zakaza> clientList=new ArrayList<Content_zakaza>();
			while (rs.next())
			{
				Content_zakaza k=new Content_zakaza();
				k.setId_zakaza(rs.getInt(1));
				k.setId_uslugi(rs.getInt(2));
				k.setId_sotrudnika(rs.getInt(3));
				k.setUsluga(rs.getString(4));
				k.setSotrudnik(rs.getString(5));
				clientList.add(k);
			}
			this.close();
			return clientList;
		}
		
//****************************************************
		public ArrayList<Content_zakaza> content_zakazaFromDB(Zakaz z) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT cz.id_zakaza, cz.id_uslugi, cz.id_sotrudnika, u.nazvanie, s.familiya " +
					"FROM Content_zakaza cz, Usluga u, Sotrudnik s " +
					"WHERE (cz.id_uslugi=u.id_uslugi AND cz.id_sotrudnika=s.id_sotrudnika) AND " +
						"id_zakaza="+z.getId_zakaza()+"");
			
			ArrayList<Content_zakaza> contentZakazaList=new ArrayList<Content_zakaza>();
			while (rs.next())
			{
				Content_zakaza k=new Content_zakaza();
				k.setId_zakaza(rs.getInt(1));
				k.setId_uslugi(rs.getInt(2));
				k.setId_sotrudnika(rs.getInt(3));
				k.setUsluga(rs.getString(4));
				k.setSotrudnik(rs.getString(5));
				contentZakazaList.add(k);
			}
			this.close();
			return contentZakazaList;
		}
//****************************************************
		
		//Занесение или изменение данных о Содержании заказа в БД
		public void content_zakazaUpsert(Content_zakaza k, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Content_zakaza(id_zakaza, id_uslugi, id_sotrudnika) VALUES(?, ?, ?)");
					stat.setInt(1, k.getId_zakaza());
					stat.setInt(2, k.getId_uslugi());
					stat.setInt(3, k.getId_sotrudnika());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {

					stat = conn.prepareStatement("UPDATE Content_zakaza SET id_uslugi = ?, id_sotrudnika = ? WHERE id_zakaza = ?");
					stat.setInt(1, k.getId_uslugi());
					stat.setInt(2, k.getId_sotrudnika());
					stat.setInt(3, k.getId_zakaza());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления Содержания заказа из БД
		public void content_zakazaDelete(Zakaz z) {
			try {
				stat = conn.prepareStatement("DELETE from Content_zakaza WHERE id_zakaza = ? ");
				stat.setInt(1, z.getId_zakaza());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//Использованные материалы-----
		//Метод, который берет информацию об Использованных материалах из БД и сохраняет ее в класс
		public ArrayList<Used_material> used_materialFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT um.id_zakaza, um.id_materiala, um.kol_vo, m.nazvanie " +
					"FROM Used_material um, Material m " +
					"WHERE um.id_materiala=m.id_materiala");
					
			ArrayList<Used_material> used_materialList=new ArrayList<Used_material>();
			while (rs.next())
			{
				Used_material m=new Used_material();
				m.setId_zakaza(rs.getInt(1));
				m.setId_materiala(rs.getInt(2));
				m.setKolvo(rs.getInt(3));
				m.setMaterial(rs.getString(4));
				used_materialList.add(m);
			}
			this.close();
			return used_materialList;
		}
		
//****************************************************
		public ArrayList<Used_material> used_materialFromDB(Zakaz z) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT um.id_zakaza, um.id_materiala, um.kol_vo, m.nazvanie " +
					"FROM Used_material um, Material m " +
					"WHERE um.id_materiala=m.id_materiala AND id_zakaza="+z.getId_zakaza()+"");
					
			ArrayList<Used_material> used_materialList=new ArrayList<Used_material>();
			while (rs.next())
			{
				Used_material m=new Used_material();
				m.setId_zakaza(rs.getInt(1));
				m.setId_materiala(rs.getInt(2));
				m.setKolvo(rs.getInt(3));
				m.setMaterial(rs.getString(4));
				used_materialList.add(m);
			}
			this.close();
			return used_materialList;
		}
//****************************************************
		
		//Занесение или изменение данных об Использованных материалах в БД
		public void used_materialUpsert(Used_material m, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Used_material(id_zakaza, id_materiala, kol_vo) VALUES(?, ?, ?)");
					stat.setInt(1, m.getId_zakaza());
					stat.setInt(2, m.getId_materiala());
					stat.setInt(3, m.getKolvo());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {

					stat = conn.prepareStatement("UPDATE Used_material SET id_materiala = ?, kol_vo = ? WHERE id_zakaza = ?");
					stat.setInt(1, m.getId_materiala());
					stat.setInt(2, m.getKolvo());
					stat.setInt(3, m.getId_zakaza());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления Использованных материалов из БД
		public void used_materialDelete(Zakaz z) {
			try {
				stat = conn.prepareStatement("DELETE from Used_material WHERE id_zakaza = ? ");
				stat.setInt(1, z.getId_zakaza());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//Материал-----
		//Метод, который берет информацию о Материале из БД и сохраняет ее в класс
		public ArrayList<Material> materialFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_materiala, nazvanie, edinica_izmereniya, price, kol_vo FROM Material");
					
			ArrayList<Material> materialList=new ArrayList<Material>();
			while (rs.next())
			{
				Material m=new Material();
				m.setId_materiala(rs.getInt(1));
				m.setNazvanie(rs.getString(2));
				m.setEdinica_izmereniya(rs.getString(3));
				m.setPrice(rs.getInt(4));
				m.setKolvo(rs.getInt(5));
				materialList.add(m);
			}
			this.close();
			return materialList;
		}
		
//****************************************************		
		public Material materialFromDB(Used_material um) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_materiala, nazvanie, edinica_izmereniya, price, kol_vo " +
					"FROM Material WHERE id_materiala="+um.getId_materiala()+"");
					
			Material m=new Material();
			while (rs.next())
			{
				m.setId_materiala(rs.getInt(1));
				m.setNazvanie(rs.getString(2));
				m.setEdinica_izmereniya(rs.getString(3));
				m.setPrice(rs.getInt(4));
				m.setKolvo(rs.getInt(5));
			}
			this.close();
			return m;
		}
//****************************************************
		
		//Занесение или изменение данных о Материале в БД
		public void materialUpsert(Material m, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Material(nazvanie, edinica_izmereniya, price, kol_vo) VALUES(?, ?, ?, ?)");
					stat.setString(1, m.getNazvanie());
					stat.setString(2, m.getEdinica_izmereniya());
					stat.setInt(3, m.getPrice());
					stat.setInt(4, m.getKolvo());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Material SET nazvanie = ?, edinica_izmereniya = ?, price = ?, kol_vo = ? WHERE id_materiala = ?");
					stat.setString(1, m.getNazvanie());
					stat.setString(2, m.getEdinica_izmereniya());
					stat.setInt(3, m.getPrice());
					stat.setInt(4, m.getKolvo());
					stat.setInt(5, m.getId_materiala());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления Материала из БД
		public void materialDelete(Material m) {
			try {
				stat = conn.prepareStatement("DELETE from Material WHERE id_materiala = ? ");
				stat.setInt(1, m.getId_materiala());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Метод поиска Материала по названию, единице измерения или цене
		public ArrayList<Material> materialSearch(String param) throws SQLException{
			stmt = conn.createStatement();
			String ss="LIKE UCASE('%"+param+"%')";
			rs = stmt.executeQuery("SELECT id_materiala, nazvanie, edinica_izmereniya, price, kol_vo FROM Material " +
					"WHERE ( UCASE(nazvanie) "+ss+" OR UCASE(edinica_izmereniya) "+ss+" OR UCASE(price) "+ss+" );");
			
			ArrayList<Material> materialList=new ArrayList<Material>();
			while (rs.next())
			{
				Material m=new Material();
				m.setId_materiala(rs.getInt(1));
				m.setNazvanie(rs.getString(2));
				m.setEdinica_izmereniya(rs.getString(3));
				m.setPrice(rs.getInt(4));
				m.setKolvo(rs.getInt(5));
				materialList.add(m);
			}
			this.close();
			return materialList;
		}
		
//Услуга-----
		//Метод, который берет информацию об Услуге из БД и сохраняет ее в класс
		public ArrayList<Usluga> uslugaFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_uslugi, nazvanie, price, time, length_hair, sex FROM Usluga");
					
			ArrayList<Usluga> uslugaList=new ArrayList<Usluga>();
			while (rs.next())
			{
				Usluga u=new Usluga();
				u.setId_uslugi(rs.getInt(1));
				u.setNazvanie(rs.getString(2));
				u.setPrice(rs.getInt(3));
				u.setTime(rs.getInt(4));
				u.setLength_hair(rs.getString(5));
				u.setSex(rs.getString(6));
				uslugaList.add(u);
			}
			this.close();
			return uslugaList;
		}
		
//****************************************************	
		public ArrayList<Usluga> uslugaFromDB(Content_zakaza k) throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_uslugi, nazvanie, price, time, length_hair, sex " +
					"FROM Usluga WHERE id_uslugi="+k.getId_uslugi()+"");
					
			ArrayList<Usluga> uslugaList=new ArrayList<Usluga>();
			while (rs.next())
			{
				Usluga u=new Usluga();
				u.setId_uslugi(rs.getInt(1));
				u.setNazvanie(rs.getString(2));
				u.setPrice(rs.getInt(3));
				u.setTime(rs.getInt(4));
				u.setLength_hair(rs.getString(5));
				u.setSex(rs.getString(6));
				uslugaList.add(u);
			}
			this.close();
			return uslugaList;
		}
//****************************************************	
		
		//Занесение или изменение данных об Услуге в БД
		public void uslugaUpsert(Usluga u, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Usluga(nazvanie, price, time, length_hair, sex) VALUES(?, ?, ?, ?, ?)");
					
					stat.setString(1, u.getNazvanie());
					stat.setInt(2, u.getPrice());
					stat.setInt(3, u.getTime());
					stat.setString(4, u.getLength_hair());
					stat.setString(5, u.getSex());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Usluga SET nazvanie = ?, price = ?, time = ?, length_hair = ?, sex = ? WHERE id_uslugi = ? ");
					
					stat.setString(1, u.getNazvanie());
					stat.setInt(2, u.getPrice());
					stat.setInt(3, u.getTime());
					stat.setString(4, u.getLength_hair());
					stat.setString(5, u.getSex());
					stat.setInt(6, u.getId_uslugi());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления Услуги из БД
		public void uslugaDelete(Usluga u) {
			try {
				stat = conn.prepareStatement("DELETE from Usluga WHERE id_uslugi = ? ");
				stat.setInt(1, u.getId_uslugi());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Метод поиска Услуги по названию, цене или времени выполнения
		public ArrayList<Usluga> uslugaSearch(String param) throws SQLException{
			stmt = conn.createStatement();
			String ss="LIKE UCASE('%"+param+"%')";
			rs = stmt.executeQuery("SELECT id_uslugi, nazvanie, price, time, length_hair, sex FROM Usluga " +
					"WHERE ( UCASE(nazvanie) "+ss+" OR UCASE(price) "+ss+" OR UCASE(time) "+ss+" );");
			
			ArrayList<Usluga> uslugaList=new ArrayList<Usluga>();
			while (rs.next())
			{
				Usluga u=new Usluga();
				u.setId_uslugi(rs.getInt(1));
				u.setNazvanie(rs.getString(2));
				u.setPrice(rs.getInt(3));
				u.setTime(rs.getInt(4));
				u.setLength_hair(rs.getString(5));
				u.setSex(rs.getString(6));
				uslugaList.add(u);
			}
			this.close();
			return uslugaList;
		}
		
//Заказ-----
		//Метод, который берет информацию о заказе из БД и сохраняет ее в класс
		public ArrayList<Zakaz> zakazFromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT z.id_zakaza, z.date, z.sum_zakaza, z.id_clienta, c.familiya, c.imya" +
					" FROM Zakaz z, Client c WHERE z.id_clienta=c.id_clienta");
					
			ArrayList<Zakaz> zakazList=new ArrayList<Zakaz>();
			while (rs.next())
			{
				Zakaz z=new Zakaz();
				z.setId_zakaza(rs.getInt(1));
				z.setDate(rs.getDate(2));
				z.setSum_zakaza(rs.getInt(3));
				z.setId_clienta(rs.getInt(4));
				z.setClient_familiya(rs.getString(5));
				z.setClient_imya(rs.getString(6));
				zakazList.add(z);
			}
			this.close();
			return zakazList;
		}

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^		
		public Zakaz zakaz_ID_FromDB() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT MAX(id_zakaza) FROM Zakaz");
			int id=0;
			while(rs.next()){
				id=rs.getInt(1);
			}
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT z.id_zakaza, z.date, z.sum_zakaza, z.id_clienta, c.familiya, c.imya" +
					" FROM Zakaz z, Client c WHERE z.id_clienta=c.id_clienta AND z.id_zakaza="+id+"");
					
			Zakaz z=new Zakaz();
			while (rs.next())
			{
				z.setId_zakaza(rs.getInt(1));
				z.setDate(rs.getDate(2));
				z.setSum_zakaza(rs.getInt(3));
				z.setId_clienta(rs.getInt(4));
				z.setClient_familiya(rs.getString(5));
				z.setClient_imya(rs.getString(6));
			}
			this.close();
			return z;
		}
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^	
		
		//Занесение или изменение данных о заказе в БД
		public void zakazUpsert(Zakaz z, String paramUpsert){
			if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Insert"))){
				try {
					stat = conn.prepareStatement("INSERT INTO Zakaz(sum_zakaza, id_clienta) VALUES(?, ?)");
										
					stat.setInt(1, z.getSum_zakaza());
					stat.setInt(2, z.getId_clienta());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if((paramUpsert!= null) && (paramUpsert.equalsIgnoreCase("Update"))){
				try {
					stat = conn.prepareStatement("UPDATE Zakaz SET sum_zakaza = ?, id_clienta = ? WHERE id_zakaza = ? ");
					
					stat.setInt(1, z.getSum_zakaza());
					stat.setInt(2, z.getId_clienta());
					stat.setInt(3, z.getId_zakaza());
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			try{
				stat.executeUpdate();
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		//Метод для удаления заказа из БД
		public void zakazDelete(Zakaz z) {
			try {
				stat = conn.prepareStatement("DELETE from Zakaz WHERE id_zakaza = ? ");
				stat.setInt(1, z.getId_zakaza());
				stat.executeUpdate();	
				this.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Метод поиска заказа по дате, сумме или клиенту
		public ArrayList<Zakaz> zakazSearch(String param) throws SQLException{
			stmt = conn.createStatement();
			String ss="LIKE UCASE('%"+param+"%')";
			rs = stmt.executeQuery("SELECT z.id_zakaza, z.date, z.sum_zakaza, z.id_clienta, c.familiya, c.imya FROM Zakaz z, Client c " +
					"WHERE z.id_clienta=c.id_clienta AND" +
					"( UCASE(z.date) "+ss+" OR UCASE(z.sum_zakaza) "+ss+" OR UCASE(c.familiya) "+ss
					+" OR UCASE(c.imya) "+ss+" );");
			
			ArrayList<Zakaz> zakazList=new ArrayList<Zakaz>();
			while (rs.next())
			{
				Zakaz z=new Zakaz();
				z.setId_zakaza(rs.getInt(1));
				z.setDate(rs.getDate(2));
				z.setSum_zakaza(rs.getInt(3));
				z.setId_clienta(rs.getInt(4));
				z.setClient_familiya(rs.getString(5));
				z.setClient_imya(rs.getString(6));
				zakazList.add(z);
			}
			this.close();
			return zakazList;
		}
		
		
		public ArrayList<String> otchetZakaz() throws SQLException{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT c.familiya, c.imya, z.date, z.sum_zakaza, " +
					"u.nazvanie, s.familiya, m.nazvanie, um.kol_vo " +
					"FROM Zakaz z, Client c, Content_zakaza cz, Usluga u, Sotrudnik s, Material m, Used_material um  " +
					"WHERE (z.id_clienta=c.id_clienta) AND (z.id_zakaza=cz.id_zakaza AND " +
					"cz.id_uslugi=u.id_uslugi AND cz.id_sotrudnika=s.id_sotrudnika) AND " +
					"(z.id_zakaza=um.id_zakaza AND um.id_materiala=m.id_materiala) ");
					
			ArrayList<String> ar=new ArrayList<String>();
			Object[] y={"Фамилия","Имя", "Дата","Сумма", "Название","Фамилия", "Название","Количество"};
			ar.add(y.toString());
			while (rs.next())
			{
				String[] o={rs.getString(1), rs.getString(2), rs.getDate(3).toString(), String.valueOf(rs.getInt(4)), rs.getString(5), rs.getString(6), rs.getString(7), String.valueOf(rs.getInt(8))};
				ar.add(o.toString());
			}
			this.close();
			return ar;
		}
	}
