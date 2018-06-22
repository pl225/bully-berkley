package thread_teste;

import thread_teste.Mensagem.TipoMensagem;

public class InicializacaoAlgoritmoBully implements Runnable {
	
	private Processo processo;

	public InicializacaoAlgoritmoBully(Processo processo) {
		this.processo = processo;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2500); // tempo arbitrario, decidir
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		if (this.processo.isEstouNaDisputaBully()) { // dps do tempo, se ainda estou na disputa, sou o lider
			this.processo.enviarMsgProcessos(new Mensagem(this.processo.pid, 
					this.processo.portaUnicast, TipoMensagem.COORDENADOR));
			System.out.println("Eu, o processo " + this.processo.pid + " sou o líder.");
			this.processo.iniciarSincronizacaoBerkley();
		}		
	}

}
