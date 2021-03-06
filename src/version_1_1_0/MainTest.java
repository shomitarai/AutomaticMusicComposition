package version_1_1_0;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void testPrintChord() {
		assertEquals("non_command", Main.printChord("foo"));
		assertEquals("ド ミ ソ", Main.printChord("C"));
		assertEquals("break", Main.printChord("X"));
	}
	
	public void testPlayChord() {
		assertEquals("non_command", Main.printChord("foo"));
		assertEquals("Chord is played", Main.printChord("C"));
		assertEquals("break", Main.printChord("X"));
	}

}
