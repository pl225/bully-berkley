package thread_teste;

import java.util.Scanner;

public class Mestre {
	public static void main(String[] args) {
		Processo p = new ProcessoBroadcast(2); // o id do processo deverá ser mudado a cada execução
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pressione um número para começar...");
		scanner.nextInt();
		
		p.iniciarEleicao();
		
		scanner.close();
	}
}
