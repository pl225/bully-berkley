package thread_teste;

public class AtualizacaoRelogio implements Runnable {

	private Processo processo;

	public AtualizacaoRelogio(Processo processo) {
		this.processo = processo;
	}
	
	@Override
	public void run() {
		while (this.processo.isAtualizandoRelogio()) {
			try {
				Thread.sleep(this.processo.intervaloAtualizacao);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			this.processo.incrementarRelogio(1);
			//System.out.println("PID: " + Processo.this.pid + ", R: " + Processo.this.relogioLocal);
		}
	}

}
