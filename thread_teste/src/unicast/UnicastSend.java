package unicast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import thread_teste.Mensagem;

public class UnicastSend extends Unicast {

	private int portaDestino;
	private Mensagem mensagem;
	protected static final String ADDRESS = "127.0.0.1";
	protected static InetAddress ucastAddr;

	public UnicastSend (Mensagem mensagem, int portaDestino) {
    
		if (UnicastSend.ucastAddr == null) {
			try {
				UnicastSend.ucastAddr = InetAddress.getByName(UnicastSend.ADDRESS);
			} catch (UnknownHostException e) {
				System.out.println("Problems getting the symbolic multicast address");
			    System.exit(0);
			}
		}
    
		try {
			this.dgramSocket = new DatagramSocket();
			this.mensagem = mensagem;
			this.portaDestino = portaDestino;
		} catch (IOException ioe){
			System.out.println("problems creating the datagram socket.");
			System.exit(1);
		}
	}

	@Override
	public void run() {

		DatagramPacket packet = null; 

		try {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream (bos);
			out.writeObject(this.mensagem);
			out.flush();
			out.close();

			packet = new DatagramPacket(bos.toByteArray(), bos.size(), UnicastSend.ucastAddr, this.portaDestino);

			this.dgramSocket.send(packet);
			System.out.println("Processo " + this.mensagem.getPidOrigem() + " sending unicast message to processo porta " + this.portaDestino);

		} catch (IOException ioe) {
			System.out.println("error sending multicast");
			System.exit(1);
		}

	}

}

