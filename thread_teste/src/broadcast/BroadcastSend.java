package broadcast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import thread_teste.Mensagem;

public class BroadcastSend extends Broadcast {

	private DatagramSocket dgramSocket;
	private Mensagem mensagem;

	public BroadcastSend(Mensagem mensagem) {
		this.mensagem = mensagem;
		try {
			this.dgramSocket = new DatagramSocket();
			this.dgramSocket.setBroadcast(true);
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

			packet = new DatagramPacket(bos.toByteArray(), bos.size(), Broadcast.bcastAddr, Broadcast.PORT);

			this.dgramSocket.send(packet);
			System.out.println("Processo " + this.mensagem.getPidOrigem() + " sending broadcast message");

		} catch (IOException ioe) {
    	  System.out.println("error sending multicast");
    	  System.exit(1);
		}
	}


}
