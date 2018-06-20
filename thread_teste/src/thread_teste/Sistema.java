package thread_teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		else 
			this.piscina = this.gerarProcessosBroadcast();
	}

	private List<Processo> gerarProcessosBroadcast() {
		List<Processo> processos = new ArrayList<Processo>();
		for (int i = 0; i < this.N; i++)
			processos.add(new ProcessoBroadcast(i));
		return processos;
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

	public void iniciarEleicao() {
		Processo p = this.getProcessos().get(new Random().nextInt(this.getProcessos().size()));
		p.iniciarEleicao();
	}
}
