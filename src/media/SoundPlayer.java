package media;

import javax.media.*;
import java.io.File;
import java.io.IOException;




public class SoundPlayer implements ControllerListener,Cloneable{
	private Player audioPlayer = null;
	private boolean isLoop=false;
	private boolean availiable=false;
	
	public SoundPlayer(String name,boolean loop){
		isLoop=loop;
		availiable=true;
        try {
            if(null==audioPlayer){
                File musicFile;//�õ�һ��MP3�ļ�
				if (loop) {
					musicFile = new File("audio/loop/"+name+".wav");
				}else {
					musicFile = new File("audio/soundEffect/"+name+".wav");
				}
                if(musicFile.exists()){
                    MediaLocator  locator=new MediaLocator("file:"+musicFile.getAbsolutePath());
                    audioPlayer = Manager.createRealizedPlayer(locator);
                    audioPlayer.prefetch();//Ԥ���ļ�
                    audioPlayer.addControllerListener(this);
                }else{
                    System.err.println("�Ҳ����ļ�"+ musicFile);
                }
            }
        } catch (CannotRealizeException ex) {
            ex.printStackTrace();
        } catch (NoPlayerException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
	}

	public void play() {
		audioPlayer.start();
	}
	public void stop() {
		audioPlayer.stop();
		audioPlayer.close();
	}

	public void controllerUpdate(ControllerEvent e) {
		if (e instanceof ControllerClosedEvent) {
			
		} else if (e instanceof EndOfMediaEvent) {
			if (isLoop) { // �������ѭ���������¿�ʼ����
				audioPlayer.setMediaTime(new Time(0)); 
				audioPlayer.start(); 
			}else {
				audioPlayer.setMediaTime(new Time(0)); 
				audioPlayer.prefetch();
			}
		}
	}

	public boolean isAvailiable() {
		return availiable;
	}

	public Player getAudioPlayer() {
		return audioPlayer;
	}
	
}
