package ru.demo.socket.inc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Добро пожаловать в игру 'Слово за словом'!");
            while (true) {
                System.out.print("Введите слово: ");
                String clientWord = scanner.nextLine();

                outputStream.write(clientWord.getBytes());
                outputStream.flush();

                byte[] buffer = new byte[1024];
                int read = inputStream.read(buffer);
                String serverWord = new String(buffer, 0, read).trim();

                System.out.println("Сервер: " + serverWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
