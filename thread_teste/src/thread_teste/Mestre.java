package thread_teste;

import thread_teste.Sistema.TipoProcesso;

public class Mestre {
	public static void main(String[] args) {
		new Sistema(5, TipoProcesso.BROADCAST).iniciarProcessos();
	}
}
