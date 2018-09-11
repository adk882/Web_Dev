package frameworks_and_pages;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio_Handler implements Runnable, Observer{
	private Media audio_file;
	private MediaPlayer media_player;
	private double master_volume_scale;
	private double music_volume_scale;
	private double sound_effects_volume_scale;
	
	// 0 = music, 1 = sound effects, 2 = N/A ...
	private int classification;
	
	public Audio_Handler(String file_path, Boolean loop, int classification, Settings settings){
		audio_file = new Media(new File(file_path).toURI().toString());
		media_player = new MediaPlayer(audio_file);
		master_volume_scale = (double)settings.get_master_volume() / 100.0;
		music_volume_scale = (double)settings.get_music_volume() / 100.0;
		sound_effects_volume_scale = (double)settings.get_sound_effects_volume() / 100.0;
		this.classification = classification;
		if(loop){
			//media_player.setAudioSpectrumListener(new Audio_Observer());
			media_player.setCycleCount(MediaPlayer.INDEFINITE);
		}
		else{
			media_player.setOnEndOfMedia(new end_of_media_handler(media_player));
		}
		settings.add_audio_observer(this);
	}
	
	private void play_audio_file() {
		// implement pause
		System.out.println("Started playing: " + audio_file.getSource());
		/*
		AudioFormat format = audio_stream.getFormat();
		DataLine.Info info = new DataLine.Info(AudioClip.class, format);
		audio_clip = (AudioClip) AudioSystem.getLine(info);
		audio_clip.open(audio_stream);
		gain_control = (FloatControl) audio_clip.getControl(FloatControl.Type.MASTER_GAIN);
		System.out.println(gain_control.getUnits());
		*/
		switch(classification){
		case 0:
			media_player.setVolume(master_volume_scale * music_volume_scale);
			break;
		case 1:
			media_player.setVolume(master_volume_scale * sound_effects_volume_scale);
			break;
		default:
			media_player.setVolume(master_volume_scale);
		}
			
		
		
		
		media_player.play();
		
		
	}

	@Override
	public void run() {
		play_audio_file();
	}
	
	public void play(){
		run();
	}
	
	public void pause(){
		media_player.pause();
	}
	
	public void stop(){
		media_player.stop();
	}
	
	public void set_volume(){
		switch(classification){
		case 0:
			media_player.setVolume(master_volume_scale * music_volume_scale);
			break;
		case 1:
			media_player.setVolume(master_volume_scale * sound_effects_volume_scale);
			break;
		default:
			media_player.setVolume(master_volume_scale);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// pause song (?) and then update the volume accordingly
		int values[] = (int []) arg1;
		master_volume_scale = (double) values[0] / 100.0;
		music_volume_scale = (double) values[1] / 100.0;
		sound_effects_volume_scale = (double) values[2] / 100.0;
		
		set_volume();
		
		System.out.println("VOLUME UPDATED");
	}

	private static class end_of_media_handler implements Runnable{
		private MediaPlayer mp;
		
		public end_of_media_handler(MediaPlayer mp){
			this.mp = mp;
		}

		@Override
		public void run() {
			mp.stop();
		}
		
	}
}


