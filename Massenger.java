package massenger;

import javax.swing.JFrame;

public class Massenger {
    public static void main(String[] args) {
        Server pingo = new Server();
        pingo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pingo.startRunning();
    }
    
}
