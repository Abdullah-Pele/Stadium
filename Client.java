package massenger;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Client extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private  ObjectInputStream input;
    private String message= "";
    private String serverIP;
    private Socket connection;
    
    public Client(String host){
        super("Ahmed");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent event){
                sendMessage(event.getActionCommand());
                userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow),BorderLayout.CENTER);
        setSize(300, 250);
        setVisible(true);
    }
    //connect to Server
     public void startRunning(){
        try{
            connectToServer();
            setupStreams();
            whileChating();
        }catch(EOFException eof){
            showMessage("\n Client terminated connection! ");
        }catch (IOException io){
        io.printStackTrace();
        }finally{
            closeit();
        }
              
     }
   // connect to server
     private void connectToServer() throws IOException{
         showMessage("Attempting connection... \n");
         connection = new Socket(InetAddress.getByName(serverIP), 6789);
         showMessage("Connect to Github");
     }

     private void setupStreams()throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n تقدر تكتب الحين! \n");
    }
     // while chatting with server
      private void whileChating() throws IOException{
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n" + message);
            }catch(ClassNotFoundException notF){
                showMessage("\n wtf that user send!, the stupid computer doesn't know");
            }
        
        }while(!message.equals("Ahmed - END"));
    }
     private void closeit(){
        showMessage("\n Closing down.... \n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException loka){
            loka.printStackTrace();
        }
    }
     //send message to the server
   private void sendMessage(String message) {
        try{
            output.writeObject("Ahmed - " + message);
            output.flush();
            showMessage("\nAhmed - "+ message);
        }catch(IOException no){
            chatWindow.append("\n ERROR: DUDE SOMETHING MESSED UP THE MESSAGE");
        
        }
    }
    //update chat window 
    private void showMessage(final String m) {
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    chatWindow.append(m);
                }
            }
        );
    }
    // let people write to their boxes
    private void ableToType(final boolean tof) {
         SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userText.setEditable(tof);
                }
            }
        );
    }
      
}
