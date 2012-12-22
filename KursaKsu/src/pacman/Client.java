package pacman;

public class Client {
	private Integer id_clienta;
	public Integer getId_clienta()
	{	return id_clienta;}
	public void setId_clienta(Integer id_clienta ){
		this.id_clienta = id_clienta;}

	private String familiya;
	 public String getFamiliya()
	{	return familiya;}
	public void setFamiliya(String familiya ){
		this.familiya = familiya;}
	
	
	private String imya;
	 public String getImya()
	{	return imya;}
	public void setImya(String imya ){
		this.imya = imya;}

	
	private String otchestvo;
	public String getOtchestvo()
	{	return otchestvo;}
	public void setOtchestvo(String otchestvo ){
		this.otchestvo = otchestvo;}
	
	
	private String sex;
	public String getSex()
	{	return sex;}
	public void setSex(String sex ){
		this.sex = sex;}

	
	private String phone;
	 public String getPhone()
	{	return phone;}
	public void setPhone(String phone ){
		this.phone = phone;}

	
	private Boolean constant_client;
	public Boolean getConstant_client()
	{	return constant_client;}
	public void setConstant_client(Boolean constant_client ){
		this.constant_client = constant_client;}
	
	public String toString() {
		return this.getFamiliya() + "  " + this.getImya() + "  " + this.getOtchestvo() + " ("+ this.getSex()+")";
	}
}
