package thread_teste;

import java.util.Scanner;

public class Mestre {
	public static void main(String[] args) {
		Processo p = new ProcessoMulticast(23); // o id do processo deverá ser mudado a cada execução
		System.out.println("O processo " + p.getId() + " está rodando...");
		
		p.iniciarAtualizacaoRelogio();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pressione um número para começar...");
		try {
			scanner.nextInt();
		} catch(Exception e) {}
		
		p.iniciarEleicao();
		
		scanner.close();
	}
}
