package broadcast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class Broadcast implements Runnable {
	
	protected static final String ADDRESS = "192.168.200.255";	
	protected static final int PORT = 6000;
	protected static InetAddress bcastAddr;
	protected DatagramSocket dSocket;
	
	public Broadcast () {
		if (Broadcast.bcastAddr == null) {
			try {
				Broadcast.bcastAddr = InetAddress.getByName(Broadcast.ADDRESS);
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
			    System.exit(0);
			}
		}
	}
	
	protected void criarSocket (int porta) {
		try {
			this.dSocket = new DatagramSocket(null);
			this.dSocket.setReuseAddress(true);
			this.dSocket.bind(new InetSocketAddress(Broadcast.PORT));
		} catch (SocketException e) {
			System.out.println(porta);
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

}
