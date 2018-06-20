package thread_teste;

import java.util.Scanner;

import thread_teste.Sistema.TipoProcesso;

public class Mestre {
	public static void main(String[] args) {
		Sistema s = new Sistema(5, TipoProcesso.MULTICAST);
		s.iniciarProcessos();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pressione um número para começar...");
		scanner.nextInt();
		
		s.iniciarEleicao();
		
		scanner.close();
	}
}
