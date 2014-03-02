package media;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class AudioProvider{
	public static AudioClip  ses[][];
	public static AudioClip  mes[];
	public static AudioClip  systemSes[][];
	public static int seNow[];
	public static int sysNow[];
	private static final int seNum = 3;
	private static final int seType = 11;
	private static final int meType = 2;
	private static final int sysType = 3;
	
	
	public static void audioSetup() {
		int i=0;    
	    ses = new AudioClip[seType][seNum];
	    for (i=0; i<seType; i++) {
	    	for (int j = 0; j < seNum; j++) {
				try {
					ses[i][j] = java.applet.Applet.newAudioClip(new File(
							"audio/SE/se" + (i + 1) + ".wav").toURI().toURL());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    }
	    seNow=new int[seType];
	    for (i = 0;  i< seType; i++) {
	    	seNow[i]=0;
		}
	    sysNow=new int[sysType];
	    for (i = 0;  i< sysType; i++) {
	    	sysNow[i]=0;
		}
	    systemSes = new AudioClip[sysType][seNum];
	    for (i = 0; i < seNum; i++) {
			try {
				systemSes[0][i] = java.applet.Applet.newAudioClip(new File(
						"audio/SE/curser.wav").toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				systemSes[1][i] = java.applet.Applet.newAudioClip(new File(
						"audio/SE/decision.wav").toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				systemSes[2][i] = java.applet.Applet.newAudioClip(new File(
						"audio/SE/cancel.wav").toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    mes = new AudioClip[meType];
	    try {
			mes[0]=java.applet.Applet.newAudioClip(new File("audio/SE/gameOver.mid").toURI().toURL());
			mes[1]=java.applet.Applet.newAudioClip(new File("audio/SE/win.mid").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void playSE(int hash) {
		ses[hash][seNow[hash]].play();
		seNow[hash]++;
		if (seNow[hash]>=seNum) {
			seNow[hash]=0;
		}
	}
	public static void playSystemSE(int hash) {
		systemSes[hash][sysNow[hash]].play();
		sysNow[hash]++;
		if (sysNow[hash]>=seNum) {
			sysNow[hash]=0;
		}
	}

}
