
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.sound.sampled.Clip;


public class MainClass {
    
    public long pauseLocation=0,SongTotalLength=0;
    public Player player;
    FileInputStream FIS;
    BufferedInputStream BIS;
    public int pauseVal=0;
    public int finished=0;
    public int k=0;
    
            
    public static void main(String[] args) {
        mp3GUI obj=new mp3GUI();
        obj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        obj.setVisible(true);
        obj.setSize(599,415); 
       
    }

    
    public void stop()
    {
        if(player!=null)
        {
            player.close();
            pauseLocation=0;
            SongTotalLength=0;
        }
    }
    
   
    
    
    public void pause()
    {
        if(player!=null)
        {
            try {
                System.out.println("pause val = "+pauseVal);
                if(pauseVal==0)
                {
                pauseLocation=FIS.available();
                player.close();
                pauseVal=1;
                }
                
            } catch (IOException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    /**
     *
     * @param path
     */
    public void play(String path)
    {
        try
        {           
            FIS=new FileInputStream(path);
            BIS=new BufferedInputStream(FIS);
            player=new Player(BIS);
            SongTotalLength=FIS.available();
            k=(int)(((int)SongTotalLength)/1000);
            
            new Thread()
            {
                
                @Override
                public void run()
                {
                    try {
                        player.play(); 
                        finished=1;
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }.start();
            
            
        }catch(JavaLayerException | IOException e)
        {
            System.out.println("error on play!");
        }
    }
    
    
    public void resume(String filepath)
    {
        try
        {    
            FIS=new FileInputStream(filepath);          
            BIS=new BufferedInputStream(FIS);
            System.out.println("Song name = "+filepath);
            player=new Player(BIS);
            FIS.skip(SongTotalLength-pauseLocation);
            
            new Thread()
            {
                
                @Override
                public void run()
                {
                    try {
                        
                        player.play();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }.start();
            
            
        }catch(JavaLayerException | IOException e)
        {
            System.out.println("error on resume!"+e);
        }
    }
    
    
    
    
    
    
}
