package thread_teste;

import java.io.Serializable;

public class Mensagem implements Serializable{
	
	private int pidOrigem;
	private TipoMensagem tipoMensagem;
	private int portaPid;
	private long relogio;
	private Long ajuste;
	
	public enum TipoMensagem {
		ELEICAO, OK, COORDENADOR, BERKLEY, BERKLEY_CALCULO, BERKLEY_FIM
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

	public void setRelogio(long relogioLocal) {
		this.relogio = relogioLocal;
	}

	public long getRelogio() {
		return this.relogio;
	}

	public void setAjuste(Long value) {
		this.ajuste = value;
	}

	public long getAjuste() {
		return this.ajuste;
	}
	
	
}
