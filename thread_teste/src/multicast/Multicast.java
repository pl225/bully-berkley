package multicast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public abstract class Multicast implements Runnable {
	
	protected static final String ADDRESS = "224.0.0.255"; // endereco multicast valido 224.0.0.0 to 224.0.0.255	
	protected static final int PORT = 6000;
	protected static InetAddress mcastAddr;
	
	protected InetAddress localHost;
	protected MulticastSocket mSocket;
	
	public Multicast () {
		if (Multicast.mcastAddr == null) {
			try {
				Multicast.mcastAddr = InetAddress.getByName(Multicast.ADDRESS);
			} catch (UnknownHostException e) {
				System.out.println("Problems getting the symbolic multicast address");
			    System.exit(0);
			}
		}
	}
	
	protected void juntarAoGrupo () {
		try {
			this.mSocket = new MulticastSocket(Multicast.PORT);
			this.mSocket.joinGroup(Multicast.mcastAddr);
		} catch (UnknownHostException e) {
			System.out.println("Problems identifying local host");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

}
