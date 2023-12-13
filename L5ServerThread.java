package lab5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class L5ServerThread extends Thread {

	Socket myConnection;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	ArrayList<Book> library = new ArrayList<>();
	Book b = new Book();
	int choice = 0;
	int index;
	int numMessage;

	public L5ServerThread(Socket s) {
		myConnection = s;
	}

	public void run() {
		try {
			out = new ObjectOutputStream(myConnection.getOutputStream());

			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());

			// Server communications
			// send 2 messages
			sendMessage("Welcome to the library");
			sendMessage(
					"Press 1 to add a book to the library, press 2 to search for a book\npress 3 to display all books, press -1 to exit:");

			while (choice != -1) {
				// get choice from user
				choice = (int) in.readObject();

				// System.out.println(message);

				if (choice == 1) {
					// give the temporary obj a new obj id to keep entries separate
					b = new Book();

					// read name from client
					message = (String) in.readObject();
					b.setName(message);// set name

					// read author from client
					message = (String) in.readObject();
					b.setAuthor(message);

					// add to the library
					library.add(b);

					// make the index of the entry it's ID
					b.setId(library.indexOf(b));

				}

				else if (choice == 2) {

					// receive book ID
					index = (int) in.readObject();

					b = library.get(index);

					// send ID
					numMessage = b.getId();
					sendMessage(numMessage);

					// send name
					message = b.getName();
					sendMessage(message);

					// send author
					message = b.getAuthor();
					sendMessage(message);

				}

				else if (choice == 3) {
					// send library.size()
					numMessage = library.size();
					sendMessage(numMessage);

					for (int i = 0; i < library.size(); i++) {
						b = library.get(i);

						// send ID
						numMessage = b.getId();
						sendMessage(numMessage);

						// send name
						message = b.getName();
						sendMessage(message);

						// send author
						message = b.getAuthor();
						sendMessage(message);

					}
				}

				else if (choice == -1) {
					break;
				}

				else {
					System.out.println("error");
				}

			}

			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void sendMessage(String msg) {// sends String
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("server>" + msg);
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

}
