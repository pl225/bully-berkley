package multicast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

import thread_teste.Mensagem;
import thread_teste.Processo;

public class MulticastReceiver extends Multicast {
	
	private Processo processo;

	public MulticastReceiver(Processo p) {
		super();
		this.processo = p;
		this.juntarAoGrupo();
	}

	@Override
	public void run() {
		DatagramPacket packet;
	    System.out.println("Multicast receiver set up ");

	    while (true) {
	
	      try {
	
	        byte[] buf = new byte[1000];
	        packet = new DatagramPacket(buf,buf.length);
	        this.mSocket.receive(packet);	        
	
	        ByteArrayInputStream bistream = new ByteArrayInputStream(packet.getData());
	        ObjectInputStream ois = new ObjectInputStream(bistream);
	
	        Mensagem mensagem = (Mensagem) ois.readObject();
	
	        //if (!(packet.getAddress().equals(localHost))) {
	          System.out.println("Processo " + this.processo + " received multicast packet: " + mensagem + " from: " + packet.getAddress());
	        //} 
	          
	        //this.processo.executarAlgoritmo(mensagem);
	
	        ois.close();
	        bistream.close();
	
	      } catch(IOException ioe) {
	        System.out.println("Trouble reading multicast message");
	        System.exit(1);
	      } catch (ClassNotFoundException cnfe) {
	        System.out.println("Class missing while reading mcast packet");
	        System.exit(1);
	     }
	   }
	}

}
