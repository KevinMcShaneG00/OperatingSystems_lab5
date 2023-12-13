package lab5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class L5Requester {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	Scanner input;
	int choice = 0;
	
	L5Requester() {
		input = new Scanner(System.in);
	}

	void run() {
		try {
			// 1. creating a socket to connect to the server

			requestSocket = new Socket("127.0.0.1", 2004);
			System.out.println("Connected to localhost in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server

			// Client communications
			// read message 1
			message = (String) in.readObject();
			System.out.println(message);

			// read message 2
			menu = (String) in.readObject();

			while (choice != -1) {
				System.out.println(menu);
				choice = input.nextInt();

				if (choice == 1) {
					// tell the server choice = 1
					numMessage = 1;
					sendMessage(numMessage);

					// both in if(1) now
					System.out.println("option 1");

					// give book name
					System.out.println("Please enter the book's name:");
					message = input.nextLine();// consume the whitespace skipping input
					message = input.nextLine();// user input
					sendMessage(message);// send input to the server to process

					// give book author
					System.out.println("Please enter the book's author:");
					message = input.nextLine();// user input
					sendMessage(message);// send input to the server to process

				}

				else if (choice == 2) {
					// tell the server choice = 2
					numMessage = 2;
					sendMessage(numMessage);

					// both in if(2) now
					System.out.println("option 2");

					// give book ID
					System.out.println("Please enter the book's ID:");
					numMessage = input.nextInt();
					sendMessage(numMessage);

					// receive ID
					numMessage = (int) in.readObject();
					System.out.println("ID: " + numMessage);

					// receive name
					message = (String) in.readObject();
					System.out.println("Name: " + message);

					// receive author
					message = (String) in.readObject();
					System.out.println("Author: " + message);
				}

				else if (choice == 3) {
					// tell the server choice = 3
					numMessage = 3;
					sendMessage(numMessage);

					// both in if(3) now
					System.out.println("option 3");

					// get library size so the loops run the same number of times
					librarySize = (int) in.readObject();

					System.out.println("Printing library content");
					for (int i = 0; i < librarySize; i++) {
						// receive ID
						numMessage = (int) in.readObject();
						System.out.println("ID: " + numMessage);

						// receive name
						message = (String) in.readObject();
						System.out.println("Name: " + message);

						// receive author
						message = (String) in.readObject();
						System.out.println("Author: " + message);

					}
				}

				else if (choice == -1) {
					break;
				}

				else {
					System.out.println("error");
				}
			}

			System.out.println("Closing library");

		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {// sends String
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendMessage(int msg) {// sends integer
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		L5Requester client = new L5Requester();
		client.run();
	}
}
