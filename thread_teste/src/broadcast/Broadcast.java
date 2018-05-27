package broadcast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class Broadcast implements Runnable {
	
	protected static String ADDRESS = "192.168.200.255";	
	protected static int PORT = 6000;
	protected static InetAddress bcastAddr;
	protected DatagramSocket dSocket;
	
	public Broadcast () {
		if (bcastAddr == null) {
			try {
				bcastAddr = InetAddress.getByName(ADDRESS);
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
			    System.exit(0);
			}
		}
	}
	
	protected void criarSocket (int porta) {
		try {
			dSocket = new DatagramSocket(null);
			dSocket.setReuseAddress(true);
			dSocket.bind(new InetSocketAddress(bcastAddr, PORT));
		} catch (SocketException e) {
			System.out.println(porta);
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

}
