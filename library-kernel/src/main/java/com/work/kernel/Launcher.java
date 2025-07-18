package com.work.kernel;

/**
 * Esta classe serve como o ponto de entrada principal para o JAR empacotado.
 * Ela resolve um problema de inicialização do JavaFX em "uber-jars".
 */
public class Launcher {
    public static void main(String[] args) {
        // Simplesmente chama o método main da nossa verdadeira classe de aplicação JavaFX.
        MainApp.main(args);
    }
}