package version_1_0_0;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void testPrintChord() {
		assertEquals("non_command", Main.printChord("foo"));
		assertEquals("ド ミ ソ", Main.printChord("C"));
		assertEquals("break", Main.printChord("X"));
	}

}
