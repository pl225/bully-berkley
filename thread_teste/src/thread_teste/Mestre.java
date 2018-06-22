package thread_teste;

import java.util.Scanner;

public class Mestre {
	public static void main(String[] args) {
		Processo p = new ProcessoBroadcast(15); // o id do processo deverá ser mudado a cada execução
		System.out.println("O processo " + p.getId() + " está rodando...");
		
		p.iniciarAtualizacaoRelogio();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pressione um número para começar...");
		scanner.nextInt();
		
		p.iniciarEleicao();
		
		scanner.close();
	}
}
