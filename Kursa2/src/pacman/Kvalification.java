	package pacman;

	public class Kvalification {
		private int id_kvalification;
		private String nazvanie_kvalification;
		private double percent;

		public Sotrudnik[] sotrudnik;

		public Sotrudnik[] getSotrudnik() {
			return sotrudnik;
		}

		public void setSotrudnik(Sotrudnik[] sotrudnik) {
			this.sotrudnik = sotrudnik;
		}

		public int getId_kvalification() {
			return id_kvalification;
		}

		public void setId_kvalification(int id_kvalification) {
			this.id_kvalification = id_kvalification;
		}

		public String getNazvanie_kvalification() {
			return nazvanie_kvalification;
		}

		public void setNazvanie_kvalification(String nazvanie_kvalification) {
			this.nazvanie_kvalification = nazvanie_kvalification;
		}

		public double getPercent() {
			return percent;
		}

		public void setPercent(double percent) {
			this.percent = percent;
		}

		public String toString() {
			return getNazvanie_kvalification();
		}
	}
