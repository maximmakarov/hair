package pacman;

public class Content_zakaza {
	private Integer id_zakaza;
	public Integer getId_zakaza()
	{	return id_zakaza;}
	public void setId_zakaza(Integer id_zakaza ){
		this.id_zakaza = id_zakaza;}
	
	private Integer id_uslugi;
	public Integer getId_uslugi()
	{	return id_uslugi;}
	public void setId_uslugi(Integer id_uslugi ){
		this.id_uslugi = id_uslugi;}
	
	private Integer id_sotrudnika;
	public Integer getId_sotrudnika()
	{	return id_sotrudnika;}
	public void setId_sotrudnika(Integer id_sotrudnika ){
		this.id_sotrudnika = id_sotrudnika;}
	
	private String sotrudnik;
	public void setSotrudnik(String sotrudnik) {
		this.sotrudnik = sotrudnik;
	}
	public String getSotrudnik() {
		return sotrudnik;
	}

	private String usluga;
	public void setUsluga(String usluga) {
		this.usluga = usluga;
	}
	public String getUsluga() {
		return usluga;
	}

	public String toString() {
		return this.getUsluga() + " - - " + this.getSotrudnik();
	}
}
