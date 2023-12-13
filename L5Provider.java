package lab5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class L5Provider {
	public static void main(String args[]) {
		ServerSocket providerSocket;
		try {
			providerSocket = new ServerSocket(2004, 10);

			while (true) {

				// 2. Wait for connection
				System.out.println("Waiting for connection");

				Socket connection = providerSocket.accept();
				L5ServerThread T1 = new L5ServerThread(connection);
				T1.start();
			}

			// providerSocket.close();
		}

		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
