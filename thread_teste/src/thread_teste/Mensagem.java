package thread_teste;

import java.io.Serializable;

public class Mensagem implements Serializable{
	
	private int pidOrigem;
	private TipoMensagem tipoMensagem;
	private int portaPid;
	
	public enum TipoMensagem {
		ELEICAO, OK, COORDENADOR, BERKLEY
	}
	
	public Mensagem (int pid, int portaPid, TipoMensagem tipoMensagem) {
		this.pidOrigem = pid;
		this.tipoMensagem = tipoMensagem;
		this.portaPid = portaPid;
	}
	
	public int getPidOrigem () {
		return this.pidOrigem;
	}
	
	public TipoMensagem getTipoMensagem () {
		return this.tipoMensagem;
	}

	@Override
	public String toString() {
		return "Mensagem [pidOrigem=" + pidOrigem + ", tipoMensagem=" + tipoMensagem + "]";
	}

	public int getPortaPid() {
		return this.portaPid;
	}
	
	
}
