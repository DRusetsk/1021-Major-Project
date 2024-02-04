import jm.JMC;
import jm.music.data.Note;
import jm.util.Play;
import org.firmata4j.Pin;

public class PotConverter {
    private Pin pot;

    public int PotConvert(Pin pot) {
        int val=0;
        this.pot = pot;
        if((int)pot.getValue() <= 146){
            val = JMC.G5;//79
        }
        else if((int)pot.getValue() <= 292){
            val = JMC.F5;//77
        }
        else if((int)pot.getValue() <= 438){
            val = JMC.e5;//76
        }
        else if((int)pot.getValue() <= 585){
            val = JMC.d5;//74
        }
        else if((int)pot.getValue() <= 730){
            val = JMC.c5;//72
        }
        else if((int)pot.getValue() <= 900){
            val = JMC.b4;//71
        }
        else if((int)pot.getValue() <= 1023){
            val = JMC.a5;//69
        }
        return val;
    }
}
