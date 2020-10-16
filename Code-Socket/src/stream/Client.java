/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;
import java.net.SocketException;



public class Client {

 
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    public static void main(String[] args) throws IOException {

        Socket clientSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        String name = "";

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }

        try {
      	    // creation socket ==> connexion
      	    clientSocket = new Socket(args[0],new Integer(args[1]).intValue()); 
		    socOut = new PrintStream(clientSocket.getOutputStream());
		    stdIn = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println("Client running");
	    
	        String line;
	        System.out.println("Pour vous déconnectez entrer '.'");
	        System.out.print("Entrez votre nom d'utilisateur: ");
	        ServerListenerThread serverSocket = new ServerListenerThread(clientSocket);
	    	serverSocket.start();
	        while (true) {
	        	line=stdIn.readLine();
	        	socOut.println(line);
	         	if (line.equals(".")) {
	         		serverSocket.closeConnection(); //close the input BufferedReader
	         		break;
	         	}
	        }
	      socOut.close();
	      stdIn.close();
	      clientSocket.close();
	      
        } catch (SocketException exception) {
        	System.out.println("deconnecté(e)");
        	System.exit(1);
        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }
                             
    }
}


