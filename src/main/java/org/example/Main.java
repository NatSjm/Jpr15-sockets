package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        int port = 8081;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
            writer.println("Привіт");

            String greeting = reader.readLine();
            System.out.println(greeting);

            if (containsRussianLetters(greeting)) {
                writer.println("Що таке паляниця?");
                String answer = reader.readLine();
                System.out.println(answer);
                if (answer.equalsIgnoreCase("хліб")) {
                    writer.println("Поточна дата і час: " + LocalDateTime.now());
                } else {
                    writer.println("Неправильна відповідь. Відключення.");
                    clientSocket.close();
                    return;
                }
            }

            writer.println("Дякую за спілкування. До побачення!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean containsRussianLetters(String text) {
        return text.matches(".*[ыёъэ].*");
    }
}