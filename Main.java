import jm.util.Write;
import org.firmata4j.*;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.Pin;
import java.io.IOException;
import java.util.Timer;
import jm.JMC;
import jm.music.data.*;
import jm.util.Play;


public class Main {
    static final byte I2C0 = 0x3C; //OLED Panel

    public static void main(String[] args)
            throws  InterruptedException, IOException {

        Score myScore = new Score("mySong", 120);
        Part guitar = new Part("Guitar", 0, 0);
        Phrase phrase1 = new Phrase();
        double startTime = 0;
        double endTime = 0;

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                guitar.add(phrase1);
                myScore.addPart(guitar);
                Write.midi(myScore, "myScore.mid");
            }
        });
        //initializing the Grove Kit
        String myPort = "COM3";
        IODevice myGroveBoard = new FirmataDevice(myPort);
        myGroveBoard.start();
        myGroveBoard.ensureInitializationIsDone();

        //Initializing the OLED Panel
        I2CDevice i2cObject = myGroveBoard.getI2CDevice((byte) 0x3C);
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        theOledObject.init();

        var pot = myGroveBoard.getPin(14);//defining the Potentiometer
        pot.setMode(Pin.Mode.ANALOG);
        var button = myGroveBoard.getPin(6);//defining the button
        button.setMode(Pin.Mode.INPUT);


        button.addEventListener(new PinEventListener() {//adding an event listener for the button
            int note;

            @Override
            public void onModeChange(IOEvent event) {
                System.out.println("Mode of the pin has been changed");
            }

            @Override
            public void onValueChange(IOEvent event) {
                if(button.getValue()==1){//this ensures that the notes are only played when the button is depressed. Without this loop it would play twice

                    if((int)pot.getValue() <= 146){
                        Play.midi(new Note(JMC.g5, JMC.SIXTEENTH_NOTE));//79
                        note = JMC.g5;
                    }
                    else if((int)pot.getValue() <= 292){
                        Play.midi(new Note(JMC.f5, JMC.SIXTEENTH_NOTE));//77
                        note = JMC.f5;
                    }
                    else if((int)pot.getValue() <= 438){
                        Play.midi(new Note(JMC.e5, JMC.SIXTEENTH_NOTE));//76
                        note = JMC.e5;
                    }
                    else if((int)pot.getValue() <= 585){
                        Play.midi(new Note(JMC.d5, JMC.SIXTEENTH_NOTE));//74
                        note = JMC.d5;
                    }
                    else if((int)pot.getValue() <= 730){
                        Play.midi(new Note(JMC.c5, JMC.SIXTEENTH_NOTE));//72
                        note = JMC.c5;
                    }
                    else if((int)pot.getValue() <= 900){
                        Play.midi(new Note(JMC.b4, JMC.SIXTEENTH_NOTE));//71
                        note = JMC.b4;
                    }
                    else if((int)pot.getValue() <= 1023){
                        Play.midi(new Note(JMC.a4, JMC.SIXTEENTH_NOTE));//69
                        note = JMC.a4;
                    }
                    phrase1.add(new Note(note,JMC.QUARTER_NOTE));
                    guitar.add(phrase1);
                    try {
                        Thread.sleep(100);//when the listener is left to run continuously, it sometimes registers one input as mutliple. Adding a very short pause alleviates this issue
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        //creating a timer that is responsible for managing the screen display. I noticed having 2 event listeners resulted in the program becoming less responsive, and the task much less demanding.
        Timer Info = new Timer();
        var InfoDisplay = new Info(theOledObject,pot);
        new Timer().schedule(InfoDisplay,0,100);
    }
}
