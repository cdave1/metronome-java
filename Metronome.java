import sun.audio.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.*;


class Metronome
{
    static int mod = 0;
    
    public static void main (String args[])
    {
        try
        {
            SetBPM(140);
            
            while(true)
            {
                if (MetronomeWillPlay() == true)
                {
                    playSound("sounds/pop.wav");
                }
            }
        }
        catch (Exception ex)
        {
            
        }
    }
    
    public static void SetBPM(int bpm)
    {
        if (bpm == 0) 
        {
            mod = 1000;
        }
        else 
        {
            mod = 60000 / bpm;
        }
    }
    
    public static boolean MetronomeWillPlay()
    {
        if ((System.currentTimeMillis() % mod) == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static synchronized void playSound(final String url) 
    {
        new Thread(new Runnable() 
                   { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
            public void run() 
            {
                try 
                {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Metronome.class.getResourceAsStream(url));
                    clip.open(inputStream);
                    clip.start(); 
                } 
                catch (Exception e) 
                {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
