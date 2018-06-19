package unicast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import thread_teste.Mensagem;
import thread_teste.Processo;

public class UnicastReceiver extends Unicast {

	protected Processo processo;

	public UnicastReceiver (Processo processo) {
		this.processo = processo;

		try {
			this.dgramSocket = new DatagramSocket(this.processo.getPorta());
		} catch (IOException ioe){
			System.out.println("problems creating the datagram socket.");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		DatagramPacket packet;
		System.out.println("Unicast receiver set up ");

		while (true) {

			try {

				byte[] buf = new byte[1000];
				packet = new DatagramPacket(buf,buf.length);
				dgramSocket.receive(packet);	        

				ByteArrayInputStream bistream = new ByteArrayInputStream(packet.getData());
				ObjectInputStream ois = new ObjectInputStream(bistream);

				Mensagem mensagem = (Mensagem) ois.readObject();

				if (mensagem.getPidOrigem() != this.processo.getId())
					System.out.println("Processo " + this.processo.getId() + " received unicast packet: " + mensagem + " from: " + packet.getAddress());
				//} this.p

				this.processo.executarAlgoritmo(mensagem);

				ois.close();
				bistream.close();

			} catch(IOException ioe) {
				System.out.println("Trouble reading unicast message");
				System.exit(1);
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Class missing while reading ucast packet");
				System.exit(1);
			}
		}
	}

}
