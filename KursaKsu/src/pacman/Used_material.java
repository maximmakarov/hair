package pacman;

public class Used_material {
	private Integer id_zakaza;
	public Integer getId_zakaza()
	{	return id_zakaza;}
	public void setId_zakaza(Integer id_zakaza ){
		this.id_zakaza = id_zakaza;}
	
	private Integer id_materiala;
	public Integer getId_materiala()
	{	return id_materiala;}
	public void setId_materiala(Integer idMateriala ){
		this.id_materiala = idMateriala;}
	
	private Integer kolvo;
	public Integer getKolvo()
	{	return kolvo;}
	public void setKolvo(Integer kolvo ){
		this.kolvo = kolvo;}
	
	private String material;
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getMaterial() {
		return material;
	}


	public String toString() {
		return this.getMaterial() + " -- " + this.getKolvo();
	}
}
