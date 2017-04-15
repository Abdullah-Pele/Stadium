package massenger;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private  ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    
    public Server(){
        super("Abdullah Messenger");
        userText = new JTextField();
        userText.setEditable(false); //if you are not conneting to anyone else you arenot allowed to write anything
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
        add(new JScrollPane(chatWindow));
        setSize(300, 250);
        setVisible(true);
    }
    // set up and run the server , because we finished the GUI
    public void startRunning(){
        try{
            server = new ServerSocket(6789, 100);
            while(true){
                try{
                    waitForConnection();
                    setupStreams();
                    whileChating();
                }catch(EOFException eof){
                    showMessage("\n Server ended the connection! ");
                }finally{
                    closeit();
                }
            }   
        }catch(IOException migdala){
            migdala.printStackTrace();
    
    }
    }// outside startrunning method, 
    //method: wait for connection, then display connection information
    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect...\n ");
        connection = server.accept();
        showMessage("Now connected to Github");
        
    }
    // get stream to send and receive data
    private void setupStreams()throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }
    //during the chat conversation
    private void whileChating() throws IOException{
        String message = "You are connected! ";
        sendMessage(message);
        ableToType(true);
        do{
            //have A conversation
            try{
                message = (String) input.readObject();
                showMessage("\n" + message);
            }catch(ClassNotFoundException notF){
                showMessage("\n idk wtf that user send! ");
            }
        
        }while(!message.equals("CLIENT - END"));
    }
    // Method: closeit, close streams snd sockets after you are done chatting 
    private void closeit(){
        showMessage("\n Closing connection.... \n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException loka){
            loka.printStackTrace();
        }
    }
    // send message to freind
    private void sendMessage(String message) {
        try{
            output.writeObject("Abdullah_SERVER - " + message);
            output.flush();
            showMessage("\nAbdullah_SERVER - "+ message);
        }catch(IOException no){
            chatWindow.append("\n ERROR: DUDE I CAN'T SEND THAT MESSAGE");
        
        }
    }
    //update chat window 
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    chatWindow.append(text);
                }
            }
        );
    }
    // let people write to their boxes
    private void ableToType(final boolean b) {
         SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userText.setEditable(b);
                }
            }
        );
    }

    
}