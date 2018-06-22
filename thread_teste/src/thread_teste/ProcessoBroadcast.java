package thread_teste;

import broadcast.BroadcastReceiver;
import broadcast.BroadcastSend;
import thread_teste.Mensagem.TipoMensagem;

public class ProcessoBroadcast extends Processo {

	public ProcessoBroadcast(int pid) {
		super(pid);
	}

	@Override
	protected Thread iniciarThreadRecepcao() {
		return new Thread(new BroadcastReceiver(this));
	}

	@Override
	protected void enviarMsgProcessos(Mensagem mensagem) {
		new Thread(new BroadcastSend(mensagem)).start();
	}

}
