package version_1_1_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	private static byte[] chord_C = {60, 64, 67};
	private static byte[] chord_Dm = {62, 65, 69};
	private static byte[] chord_Em = {64, 67, 71};
	private static byte[] chord_F = {65, 69, 72};
	private static byte[] chord_G = {67, 71, 74};
	private static byte[] chord_Am = {69, 72, 76};
	private static byte[] chord_Bmb5 = {71, 74, 77};
	
	public static void main(String[] args) throws IOException {
		
		String str = "";
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while(true){
			Player player = new Player();
			System.out.println("Please input Chord.");
			str = playChord(player, new String(in.readLine()));
			if(!str.equals("break")){
				System.out.println(str);
				player.play();
			}
			else{
				break;
			}
		}
		System.out.println("This program finished.");
		System.exit(0);
	}

	
	public static String printChord(String str){
		if(str.equals("C"))
			return "ド ミ ソ";
		else if(str.equals("Dm"))
			return "レ ファ ラ";
		else if(str.equals("Em"))
			return "ミ ソ シ";
		else if(str.equals("F"))
			return "ファ ラ ド";
		else if(str.equals("G"))
			return "ソ シ レ";
		else if(str.equals("Am"))
			return "ラ ド ミ";
		else if(str.equals("Bmb5"))
			return "シ レ ファ";
		else if(str.equals("X"))
			return "break";
		else
			return "non_command";
	}
	
	
	public static String playChord(Player player, String str){
		if(str.equals("C"))
			player.addChord(chord_C, 1, 1, 1, 1);
		else if(str.equals("Dm"))
			player.addChord(chord_Dm, 1, 1, 1, 1);
		else if(str.equals("Em"))
			player.addChord(chord_Em, 1, 1, 1, 1);
		else if(str.equals("F"))
			player.addChord(chord_F, 1, 1, 1, 1);
		else if(str.equals("G"))
			player.addChord(chord_G, 1, 1, 1, 1);
		else if(str.equals("Am"))
			player.addChord(chord_Am, 1, 1, 1, 1);
		else if(str.equals("Bmb5"))
			player.addChord(chord_Bmb5, 1, 1, 1, 1);
		else if(str.equals("X"))
			return "break";
		else
			return "non_command";
		return "Chord is Played";
	}
	
//	public static String convert_MIDI_to_Chord(byte[] midiArray){	
//		return "";
//	}
}
