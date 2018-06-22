package thread_teste;

import java.util.Map.Entry;

public class InicializacaoAlgoritmoBerkley implements Runnable {
	
	private Processo processo;

	public InicializacaoAlgoritmoBerkley(Processo processo) {
		this.processo = processo;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		this.processo.berkley.calcularNovosHorarios();
		
		this.processo.incrementarRelogio(this.processo.berkley.getAjusteLider());
		this.processo.imprimirRelogioLocal();
		for (Entry<Integer, Long> ajusteProcesso : this.processo.berkley.getAjustesEscravos())
			this.processo.enviarAjusteEscravos(ajusteProcesso.getKey(), ajusteProcesso.getValue());
		
	}

}
