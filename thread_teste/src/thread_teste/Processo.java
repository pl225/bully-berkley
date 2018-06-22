package thread_teste;

import java.util.Map.Entry;
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
	private int portaUnicastLider;
	private Berkley berkley;
	
	protected abstract Thread iniciarThreadRecepcao ();
	protected abstract void enviarMsgProcessos (Mensagem mensagem);
	
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
		this.enviarMsgProcessos(new Mensagem(this.pid, this.portaUnicast, TipoMensagem.ELEICAO));
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
        	this.enviarDefasagem(mensagem);
        } else if (mensagem.getTipoMensagem() == TipoMensagem.BERKLEY_CALCULO) {
        	this.berkley.addHorarioEscravo(mensagem.getPortaPid(), mensagem.getAjuste());
        } else if (mensagem.getTipoMensagem() == TipoMensagem.BERKLEY_FIM) {
        	this.relogioLocal += mensagem.getAjuste();
        	this.imprimirRelogioLocal();
        } else { // COORDENADOR
        	this.portaUnicastLider = mensagem.getPortaPid();
        	this.isAtualizandoRelogio = false;
        }
	}

	private void enviarDefasagem(Mensagem mensagem) {
		this.isAtualizandoRelogio = false;
		Mensagem m = new Mensagem(pid, portaUnicast, TipoMensagem.BERKLEY_CALCULO);
		m.setAjuste(this.relogioLocal - mensagem.getRelogio());
		this.imprimirRelogioLocal();
		new Thread(new UnicastSend(m, this.portaUnicastLider)).start();
	}
	
	private void imprimirRelogioLocal() {
		System.out.println("Processo " + this.pid + ", relógio atual: " + this.relogioLocal);
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
						Processo.this.enviarMsgProcessos(new Mensagem(Processo.this.pid, 
								Processo.this.portaUnicast, TipoMensagem.COORDENADOR));
						System.out.println("Processo:" + Processo.this.pid + " sou o líder.");
						Processo.this.iniciarSincronizacaoBerkley();
					}
				}
				
			});	
			this.aguardandoResultadoBully.start();
		}
	}
	
	protected void iniciarSincronizacaoBerkley() {
		this.isAtualizandoRelogio = false;
		Mensagem msg = new Mensagem(this.pid, this.portaUnicast, TipoMensagem.BERKLEY);
		msg.setRelogio(relogioLocal);
		this.enviarMsgProcessos(msg);
		this.imprimirRelogioLocal();
		this.berkley = new Berkley(this.portaUnicast);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					System.exit(0);
				}
				
				Processo.this.berkley.calcularNovosHorarios();
				
				Processo.this.relogioLocal += Processo.this.berkley.getAjusteLider();
				Processo.this.imprimirRelogioLocal();
				for (Entry<Integer, Long> ajusteProcesso : Processo.this.berkley.getAjustesEscravos())
					Processo.this.enviarAjusteEscravos(ajusteProcesso.getKey(), ajusteProcesso.getValue());
			}

		}).start();
		
	}
	
	protected void enviarAjusteEscravos(Integer key, Long value) {
		Mensagem m = new Mensagem(pid, portaUnicast, TipoMensagem.BERKLEY_FIM);
		m.setAjuste(value);
		new Thread(new UnicastSend(m, key)).start();
	}
	
	public int getPorta() {
		return this.portaUnicast;
	}
}
