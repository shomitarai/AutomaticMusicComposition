package version_1_1_0;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * 生成したメロディやコードのMIDIを再生するクラス
 * @author Shun Yamashita
 * ゼミ用に一部改変(@author BENJAMIN)
 */	
public class Player {
	private Sequencer sequencer;
	private Sequence  sequence;

	public Player() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 480);
			for(int track = 0; track < 16; track++) { // 16個のトラック生成
				sequence.createTrack();
			}
			setBpm(120); // デフォルトのBPM
			sequencer.setSequence(sequence);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * BPMを指定する
	 * @param bpm : 1分間に打つ拍の回数
	 */
	private void setBpm(int bpm) {
		try {
			MetaMessage bpmChange = new MetaMessage();
			int l = 60 * 1000000 / bpm;
			bpmChange.setMessage(0x51, new byte[]{ (byte)(l / 65536), (byte)(l % 65536 / 256), (byte)(l % 256) }, 3);
			MidiEvent bpmChangeEvent = new MidiEvent(bpmChange, 0);
			for(Track track : sequence.getTracks()) {
				track.add(bpmChangeEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定したトラックに音符を追加する
	 * @param track      : 音符を鳴らすトラック番号
	 * @param program    : 楽器番号
	 * @param note       : 音程番号(MIDI番号)
	 * @param position   : 音符を鳴らす位置
	 * @param duration   : 音符を鳴らす長さ
	 */
	private void addNote(int track, int program, int note, int position, int duration) {
		try {
			// 楽器変更イベントを生成
			ShortMessage programChange = new ShortMessage();
			programChange.setMessage(ShortMessage.PROGRAM_CHANGE, track - 1, program - 1, 0);
			MidiEvent programChangeEvent = new MidiEvent(programChange, position);

			// ノートオンイベントを生成
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, track - 1, note, 100);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// ノートオフイベントを生成
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, track - 1, note, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			// シーケンスにイベント群を追加
			sequence.getTracks()[track - 1].add(programChangeEvent);
			sequence.getTracks()[track - 1].add(noteOnEvent);
			sequence.getTracks()[track - 1].add(noteOffEvent);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * メロディを追加する
	 * @param midi     : メロディのMIDI番号
	 * @param program  : 楽器番号
	 * @param position : コードを鳴らす位置
	 * @param duration : コードを鳴らす長さ
	 */
	private void addMelody(byte midi, int program, int position, int duration) {
		addNote(1, program, midi, position, duration);
	}

	/**
	 * メロディを追加する(発音タイミングを小節や拍, 発音時間長をn分音符で指定する場合に使う)
	 * @param midi       : コード構成音のMIDI番号配列
	 * @param program    : 楽器番号
	 * @param measure    : コードを鳴らす小節
	 * @param beat       : コードを鳴らす拍
	 * @param separation : コードを鳴らす長さ(separation = 4とすれば4分音符分の長さになる)
	 */
	public void addMelody(byte midi, int program, int measure, int beat, int separation) {
		int position = (480 * 4) * (measure - 1) + 480 * (beat - 1);
		int duration = (480 * 4) / separation;
		addMelody(midi, program, position, duration);
	}

	/**
	 * コードを追加する
	 * @param midi     : コード構成音のMIDI番号配列
	 * @param program  : 楽器番号
	 * @param position : コードを鳴らす位置
	 * @param duration : コードを鳴らす長さ
	 */
	private void addChord(byte[] midi, int program, int position, int duration) {
		for(int n = 0; n < midi.length; n++) {
			addNote(2, program, midi[n], position, duration);
		}
	}
		
	/**
	 * コードを追加する(発音タイミングを小節や拍, 発音時間長をn分音符で指定する場合に使う)
	 * @param midi       : コード構成音のMIDI番号配列
	 * @param program    : 楽器番号
	 * @param measure    : コードを鳴らす小節
	 * @param beat       : コードを鳴らす拍
	 * @param separation : コードを鳴らす長さ(separation = 4とすれば4分音符分の長さになる)
	 */
	public void addChord(byte[] midi, int program, int measure, int beat, int separation) {
		int position = (480 * 4) * (measure - 1) + 480 * (beat - 1);
		int duration = (480 * 4) * (1 / separation);
		addChord(midi, program, position, duration);
	}

	public void play() {
		try {
			sequencer.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if(sequencer.isRunning()) {
			sequencer.stop();
		}
		sequencer.setTickPosition(0);
	}

	public void close() {
		sequencer.close();
	}
}