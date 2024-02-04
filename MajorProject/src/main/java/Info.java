import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.Pin;
import java.util.TimerTask;

public class Info extends TimerTask {

    private final SSD1306 display;
    private final Pin pot;

    public Info (SSD1306 display, Pin pot){
        this.display = display;
        this.pot = pot;
    }
    public void run(){
        display.getCanvas().clear(); // clear contents first

        if((int)pot.getValue() <= 146){
            display.getCanvas().drawString(0,0,"The selected note is G");
        }
        else if((int)pot.getValue() <= 292){
            display.getCanvas().drawString(0,0,"The selected note is F");
        }
        else if((int)pot.getValue() <= 438){
            display.getCanvas().drawString(0,0,"The selected note is E");
        }
        else if((int)pot.getValue() <= 585){
            display.getCanvas().drawString(0,0,"The selected note is D");
        }
        else if((int)pot.getValue() <= 730){
            display.getCanvas().drawString(0,0,"The selected note is C");
        }
        else if((int)pot.getValue() <= 900){
            display.getCanvas().drawString(0,0,"The selected note is B");
        }
        else{
            display.getCanvas().drawString(0,0,"The selected note is A");
        }
        display.display();
    }
}
