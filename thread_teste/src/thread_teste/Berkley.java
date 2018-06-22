package thread_teste;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Berkley {
	
	private HashMap<Integer, Long> mapaHorarios;
	private int portaLider;
	
	public Berkley(int portaLider) {
		this.portaLider = portaLider;
		this.mapaHorarios = new HashMap<>();
		this.mapaHorarios.put(portaLider, 0L);
	}

	public void addHorarioEscravo(int portaPid, long relogio) {
		this.mapaHorarios.put(portaPid, relogio);
	}

	public void calcularNovosHorarios() {
		long soma = 0;
		
		for (Map.Entry<Integer, Long> entry : this.mapaHorarios.entrySet())
			soma += entry.getValue();

		final long media = soma / this.mapaHorarios.size();
		
		this.mapaHorarios.replaceAll((k, v) -> {
			return media - v;
		});
	}
	
	public long getAjusteLider () {
		return this.mapaHorarios.remove(this.portaLider);
	}
	
	public Set<Entry<Integer,Long>> getAjustesEscravos () {
		return this.mapaHorarios.entrySet();
	}
	
	/*public static void main(String[] args) {
		Berkley b = new Berkley (1, 0);
		b.addHorarioEscravo(2, -10);
		b.addHorarioEscravo(3, 25);
		
		b.calcularNovosHorarios();
		
		b.mapaHorarios.forEach((k, v) -> {
			System.out.println("Chave: " + k + ", valor: " + v);
		});  
	}*/

}
