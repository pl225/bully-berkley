package thread_teste;

import java.util.Scanner;

public class Mestre {
	public static void main(String[] args) {
		Processo p = new ProcessoBroadcast(3); // o id do processo deverá ser mudado a cada execução
		p.iniciarAtualizacaoRelogio();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pressione um número para começar...");
		scanner.nextInt();
		
		p.iniciarEleicao();
		
		scanner.close();
	}
}
