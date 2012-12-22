package pacman;

public class Material {
	private Integer id_materiala;
	public Integer getId_materiala()
	{	return id_materiala;}
	public void setId_materiala(Integer idMateriala ){
		this.id_materiala = idMateriala;}

	private String nazvanie;
	 public String getNazvanie()
	{	return nazvanie;}
	public void setNazvanie(String nazvanie ){
		this.nazvanie = nazvanie;}
	
	
	private String edinica_izmereniya;
	 public String getEdinica_izmereniya()
	{	return edinica_izmereniya;}
	public void setEdinica_izmereniya(String edinicaIzmereniya ){
		this.edinica_izmereniya = edinicaIzmereniya;}

	
	private Integer price;
	public Integer getPrice()
	{	return price;}
	public void setPrice(Integer price ){
		this.price = price;}
	
	
	private Integer kolvo;
	public Integer getKolvo()
	{	return kolvo;}
	public void setKolvo(Integer kolvo ){
		this.kolvo = kolvo;}
	
	public String toString() {
		return this.getNazvanie() + " (" + this.getEdinica_izmereniya() + ") -- " + this.getPrice() + " руб. -- "+ this.getKolvo()+" шт.";
	}
}
