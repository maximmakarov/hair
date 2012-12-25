package pacman;

public class Usluga {
	private Integer id_uslugi;
	public Integer getId_uslugi()
	{	return id_uslugi;}
	public void setId_uslugi(Integer id_uslugi ){
		this.id_uslugi = id_uslugi;}

	
	private String nazvanie;
	 public String getNazvanie()
	{	return nazvanie;}
	public void setNazvanie(String nazvanie ){
		this.nazvanie = nazvanie;}
	
	
	private Integer price;
	public Integer getPrice()
	{	return price;}
	public void setPrice(Integer price ){
		this.price = price;}
	
	
	private Integer time;
	public Integer getTime()
	{	return time;}
	public void setTime(Integer time ){
		this.time = time;}
	

	private String length_hair;
	 public String getLength_hair()
	{	return length_hair;}
	public void setLength_hair(String length_hair ){
		this.length_hair = length_hair;}
	
	
	private String sex;
	public String getSex()
	{	return sex;}
	public void setSex(String sex ){
		this.sex = sex;}
	
	public String toString() {
		return this.getNazvanie() + "  " + this.getPrice() + " руб. " + this.getTime() + " мин. -- "+
			this.getLength_hair()+" ("+this.getSex()+")";
	}
}
