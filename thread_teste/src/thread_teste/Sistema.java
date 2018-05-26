package thread_teste;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
	
	private List<Processo> piscina;
	private int N;
	
	enum TipoProcesso {
		MULTICAST, BROADCAST 
	}
	
	public Sistema (int N, TipoProcesso tipoProcesso) {
		this.N = N;
		if (tipoProcesso == TipoProcesso.MULTICAST)
			this.piscina = this.gerarProcessosMulticast();
		/*else gerarProcessosBroadcast*/
	}

	private List<Processo> gerarProcessosMulticast() {
		List<Processo> processos = new ArrayList<Processo>();
		for (int i = 0; i < this.N; i++)
			processos.add(new ProcessoMulticast(i));
		return processos;
	}
	
	public List<Processo> getProcessos () {
		return this.piscina;
	}
	
	public void iniciarProcessos () {
		for (Processo p : this.piscina)
			p.iniciarAtualizacaoRelogio();
	}
}
