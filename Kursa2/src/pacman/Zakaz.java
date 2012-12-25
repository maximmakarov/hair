package pacman;


public class Zakaz {
	private Integer id_zakaza;
	public Integer getId_zakaza()
	{	return id_zakaza;}
	public void setId_zakaza(Integer id_zakaza ){
		this.id_zakaza = id_zakaza;}
	

	private java.sql.Date date;
	 public java.sql.Date getDate()
	{	return date;}
	public void setDate(java.sql.Date date ){
		this.date = date;}
	
	
	private Integer sum_zakaza;
	public Integer getSum_zakaza()
	{	return sum_zakaza;}
	public void setSum_zakaza(Integer sum_zakaza ){
		this.sum_zakaza = sum_zakaza;}
	
//	public void countSum_zakaza()
//	{	
//		this.sum_zakaza = sum_zakaza;
//	}
	
	
	private Integer id_clienta;
	public Integer getId_clienta()
	{	return id_clienta;}
	public void setId_clienta(Integer id_clienta ){
		this.id_clienta = id_clienta;}

	private String client_familiya;
	public void setClient_familiya(String clientFamiliya) {
		this.client_familiya = clientFamiliya;
	}
	public String getClient_familiya() {
		return client_familiya;
	}
	
	private String client_imya;
	public void setClient_imya(String client_imya) {
		this.client_imya = client_imya;
	}
	public String getClient_imya() {
		return client_imya;
	}
	
	public String toString() {
		return this.getId_zakaza() + "  (" + this.getDate() + ")  " + this.getSum_zakaza() + " руб. -- "+ this.getClient_familiya()+"  "+this.getClient_imya();
	}

	//private ArrayList<Content_zakaza> content= new ArrayList<Content_zakaza>();
	//private ArrayList<Used_material> materials= new ArrayList<Used_material>();
}
