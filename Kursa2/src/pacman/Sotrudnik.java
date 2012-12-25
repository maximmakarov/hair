	package pacman;



	public class Sotrudnik {
		private int id_sotrudnika;
		private String familiya;
		private String imya;
		private String otchestvo;
		private java.sql.Date data_priema;
		private String phone;
		private int id_doljnosti;
		private int id_kvalification;
		private String doljnost;
		
		public Sotrudnik() {
		}

		public int getId_sotrudnika() {
			return id_sotrudnika;
		}

		public void setId_sotrudnika(int id_sotrudnika) {
			this.id_sotrudnika = id_sotrudnika;
		}

		public String getFamiliya() {
			return familiya;
		}

		public void setFamiliya(String familiya) {
			this.familiya = familiya;
		}

		public String getImya() {
			return imya;
		}

		public void setImya(String imya) {
			this.imya = imya;
		}

		public String getOtchestvo() {
			return otchestvo;
		}

		public void setOtchestvo(String otchestvo) {
			this.otchestvo = otchestvo;
		}

		public java.sql.Date getData_priema() {
			return data_priema;
		}

		public void setData_priema(java.sql.Date data_priema) {
			this.data_priema = data_priema;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setId_doljnosti(int id_doljnosti) {
			this.id_doljnosti = id_doljnosti;
		}

		public int getId_doljnosti() {
			return id_doljnosti;
		}

		public void setId_kvalification(int id_kvalification) {
			this.id_kvalification = id_kvalification;
		}

		public int getId_kvalification() {
			return id_kvalification;
		}

		public String toString() {
				return 
				this.getFamiliya() + "  " + this.getImya() + "  " + this.getOtchestvo() + " -- " 
					+ this.getDoljnost() + " ( " + this.getData_priema() + " )";
		}

		public void setDoljnost(String doljnost) {
			this.doljnost = doljnost;
		}

		public String getDoljnost() {
			return doljnost;
		}

}