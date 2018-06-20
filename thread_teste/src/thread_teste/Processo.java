package thread_teste;

import java.util.Random;

import thread_teste.Mensagem.TipoMensagem;
import unicast.UnicastReceiver;
import unicast.UnicastSend;

public abstract class Processo {
	
	protected int pid; // identificador do processo
	private boolean isAtualizandoRelogio; // flag para atualizar o relogio ou nao
	private long relogioLocal; // contador do relogio local
	private int intervaloAtualizacao; // intervalo aleatorio de atualizacao do relogio
	private Thread threadRecepcao; // thread sempre ativa para a recepção de msgs 
	private boolean estouNaDisputaBully; // flag que diz se o processo esta na disputa do bully
	private Thread aguardandoResultadoBully; // thread ativa para aguardar resultado da disputa do bully
	protected int portaUnicast = 7000; // porta para envio/recepcao de mensagens especificas para esse processo
	
	protected abstract Thread iniciarThreadRecepcao ();
	protected abstract void enviarMsgProcessos (TipoMensagem tipoMensagem);
	
	public boolean isEstouNaDisputaBully() {
		return estouNaDisputaBully;
	}

	public void setEstouNaDisputaBully(boolean estouNaDisputaBully) {
		this.estouNaDisputaBully = estouNaDisputaBully;
	}
	
	public int getId () {
		return this.pid;
	}

	public Processo (int pid) {
		this.pid = pid;
		this.isAtualizandoRelogio = false;
		this.relogioLocal = 0;
		this.intervaloAtualizacao = new Random().nextInt(1000);
		this.portaUnicast += pid;
		this.threadRecepcao = this.iniciarThreadRecepcao();
		this.threadRecepcao.start();
		new Thread(new UnicastReceiver(this)).start();
	}
	
	public synchronized void iniciarAtualizacaoRelogio () {
		this.isAtualizandoRelogio = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				while (Processo.this.isAtualizandoRelogio) {
					try {
						Thread.sleep(Processo.this.intervaloAtualizacao);
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						System.exit(0);
					}
					Processo.this.relogioLocal++;
					//System.out.println("PID: " + Processo.this.pid + ", R: " + Processo.this.relogioLocal);
				}
			}
		}).start();
	}
	
	public void iniciarEleicao () {
		this.enviarMsgProcessos(TipoMensagem.ELEICAO);
		this.iniciarEsperaBully();
	}
	
	public synchronized void enviarOK (Mensagem mensagem) {
		new Thread(new UnicastSend(new Mensagem(this.pid, this.portaUnicast, TipoMensagem.OK), mensagem.getPortaPid())).start();
	}
	
	public synchronized void executarAlgoritmo(Mensagem mensagem) {
		if (mensagem.getTipoMensagem() == TipoMensagem.ELEICAO) { // verificar mais seguranca para nao executar bully mais de uma vez
        	if (this.pid > mensagem.getPidOrigem()) {
        		this.iniciarEsperaBully();
        		this.enviarOK(mensagem);
        		this.iniciarEleicao();
        	} else {
        		this.setEstouNaDisputaBully(false);
        	}
        } else if (mensagem.getTipoMensagem() == TipoMensagem.OK) {
        	System.out.println("Processo: " + this.pid + " não estou mais na disputa");
        	this.setEstouNaDisputaBully(false);
        } else if (mensagem.getTipoMensagem() == TipoMensagem.BERKLEY) {
        	
        } else { // COORDENADOR
        	// conheco o lider
        }
	}

	private void iniciarEsperaBully() {
		if (this.aguardandoResultadoBully == null) {
			this.setEstouNaDisputaBully(true);
			this.aguardandoResultadoBully = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(2500); // tempo arbitrario, decidir
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						System.exit(0);
					}
					if (Processo.this.estouNaDisputaBully) { // dps do tempo, se ainda estou na disputa, sou o lider
						Processo.this.enviarMsgProcessos(TipoMensagem.COORDENADOR);
						System.out.println("Processo:" + Processo.this.pid + " sou o líder.");
					}
				}
				
			});	
			this.aguardandoResultadoBully.start();
		}
	}
	public int getPorta() {
		return this.portaUnicast;
	}
}
