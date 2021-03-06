package thread_teste;

import multicast.MulticastReceiver;
import multicast.MulticastSend;
import thread_teste.Mensagem.TipoMensagem;

public class ProcessoMulticast extends Processo {

	public ProcessoMulticast(int pid) {
		super(pid);
	}

	@Override
	protected Thread iniciarThreadRecepcao() {
		return new Thread(new MulticastReceiver(this));
	}

	@Override
	protected void enviarMsgProcessos(TipoMensagem tipoMensagem) {
		new Thread(new MulticastSend(new Mensagem(this.pid, this.portaUnicast, tipoMensagem))).start();
	}
	
}
