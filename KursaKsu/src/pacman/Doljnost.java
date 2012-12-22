	package pacman;

	public class Doljnost {
		private int id_doljnosti;
		private String nazvanie_doljnosti;

		public Sotrudnik[] sotrudnik;

		public Sotrudnik[] getSotrudnik() {
			return sotrudnik;
		}

		public void setSotrudnik(Sotrudnik[] sotrudnik) {
			this.sotrudnik = sotrudnik;
		}

		public int getId_doljnosti() {
			return id_doljnosti;
		}

		public void setId_doljnosti(int id_doljnosti) {
			this.id_doljnosti = id_doljnosti;
		}

		public String getNazvanie_doljnosti() {
			return nazvanie_doljnosti;
		}

		public void setNazvanie_doljnosti(String nazvanie_doljnosti) {
			this.nazvanie_doljnosti = nazvanie_doljnosti;
		}

		public String toString() {
			return getNazvanie_doljnosti();
		}
	}