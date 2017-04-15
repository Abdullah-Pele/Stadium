package massenger;
import javax.swing.JFrame;

public class ClientTest {
    public static void main(String[] args) {
        Client lingo = new  Client("127.0.0.1");
        lingo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lingo.startRunning();
    }
}
