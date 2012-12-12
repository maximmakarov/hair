package pacman;

import junit.framework.TestCase;

public class SotrudnikTest extends TestCase {

	public void testSotrudnik() {
		Sotrudnik s=new Sotrudnik();
		s.setId_sotrudnika(1);
		assertEquals(1, s.getId_sotrudnika());
	}

	public void testSetId_sotrudnika() {
		Sotrudnik sotr = new Sotrudnik();
		assertNotNull(sotr);
	}

	public void testSetFamiliya() {
		Sotrudnik sotr = new Sotrudnik();
		sotr.setFamiliya("Ivanov");
		Sotrudnik sotr2 = new Sotrudnik();
		sotr2.setFamiliya("Petrov");
		sotr2 = sotr;
		assertSame(sotr.getFamiliya(), sotr2.getFamiliya());
	}

}
