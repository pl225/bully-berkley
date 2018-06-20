package thread_teste;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Berkley {
	
	private HashMap<Integer, Long> mapaHorarios;
	private long relogioLider;
	private int portaLider;
	
	public Berkley(int portaLider, long relogio) {
		this.relogioLider = relogio;
		this.portaLider = portaLider;
		this.mapaHorarios = new HashMap<>();
		this.mapaHorarios.put(portaLider, relogio);
	}

	public void addHorarioEscravo(int portaPid, long relogio) {
		this.mapaHorarios.put(portaPid, relogio);
	}

	public void calcularNovosHorarios() {
		long soma = 0;
		
		for (Map.Entry<Integer, Long> entry : this.mapaHorarios.entrySet())
			soma += entry.getValue() - this.relogioLider;

		long media = soma / this.mapaHorarios.size();
		
		final long horarioFinal = this.relogioLider + media;
		
		this.mapaHorarios.replaceAll((k, v) -> {
			return horarioFinal - v;
		});
	}
	
	public long getAjusteLider () {
		return this.mapaHorarios.remove(this.portaLider);
	}
	
	public Set<Entry<Integer,Long>> getAjustesEscravos () {
		return this.mapaHorarios.entrySet();
	}
	
	/*public static void main(String[] args) {
		Berkley b = new Berkley (1, 60);
		b.addHorarioEscravo(2, 50);
		b.addHorarioEscravo(3, 85);
		
		b.calcularNovosHorarios();
		
		b.mapaHorarios.forEach((k, v) -> {
			System.out.println("Chave: " + k + ", valor: " + v);
		});  
	}*/

}
