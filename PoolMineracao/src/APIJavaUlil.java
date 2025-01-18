import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIJavaUlil {
    private static final int PORT = 3333;
    private static final int MAX_THREADS = 10; // Número máximo de mineradores simultâneos
    private static final Random random = new Random();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor Stratum de pool de mineração iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo minerador conectado: " + clientSocket.getInetAddress());
                threadPool.execute(new MinerHandler(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    private static class MinerHandler implements Runnable {
        private final Socket socket;

        public MinerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
            ) {
                writer.println("Bem-vindo ao pool de mineração! Enviando trabalho...");

                // Gera um trabalho para o minerador
                String job = generateJob();
                writer.println("Job: " + job);

                String response;
                while ((response = reader.readLine()) != null) {
                    System.out.println("Solução recebida de " + socket.getInetAddress() + ": " + response);

                    if (validateSolution(job, response)) {
                        writer.println("Solução aceita! Bom trabalho.");
                        break;
                    } else {
                        writer.println("Solução rejeitada. Tente novamente.");
                    }
                }

            } catch (IOException e) {
                System.err.println("Erro ao comunicar com o minerador: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
                System.out.println("Conexão encerrada com: " + socket.getInetAddress());
            }
        }

        // Gera um job (um hash alvo simples)
        private String generateJob() {
            int target = random.nextInt(1000000); // Número aleatório como alvo
            return String.valueOf(target);
        }

        // Valida a solução: verifica se o hash da solução é menor que o alvo
        private boolean validateSolution(String job, String solution) {
            try {
                int target = Integer.parseInt(job);
                int submittedValue = Integer.parseInt(solution);

                // Simulação: a solução é válida se for menor ou igual ao alvo
                return submittedValue <= target;

            } catch (NumberFormatException e) {
                System.err.println("Formato inválido na solução.");
                return false;
            }
        }
    }
}







/*
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiningPoolServer {

    private static final int PORT = 7733;
    private static final int MAX_THREADS = 10; // Limite de conexões simultâneas
    private static final Random random = new Random();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de pool de mineração iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo minerador conectado: " + clientSocket.getInetAddress());

                threadPool.execute(new MinerHandler(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    private static class MinerHandler implements Runnable {
        private final Socket socket;

        public MinerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
            ) {
                String challenge = generateChallenge();
                writer.println("Desafio: " + challenge);

                String response = reader.readLine();
                System.out.println("Solução recebida de " + socket.getInetAddress() + ": " + response);

                if (response != null && response.contains(challenge)) {
                    writer.println("Solução aceita!");
                } else {
                    writer.println("Solução rejeitada.");
                }

            } catch (IOException e) {
                System.err.println("Erro ao comunicar com o minerador: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
                System.out.println("Conexão encerrada com: " + socket.getInetAddress());
            }
        }

        // Gera um desafio simples (número aleatório em formato de string)
        private String generateChallenge() {
            int number = random.nextInt(9000) + 1000; // Número entre 1000 e 9999
            return String.valueOf(number);
        }
    }
}
*/
