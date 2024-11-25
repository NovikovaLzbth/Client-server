import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Сервер ждет клиента...");

        try (Socket clientSocket = serverSocket.accept();
             InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {

            System.out.println("Новое соединение: " + clientSocket.getInetAddress().toString());
            String lastWord = "";

            while (true) {
                byte[] buffer = new byte[1024]; // Для чтения данных
                int read = inputStream.read(buffer);
                String clientWord = new String(buffer, 0, read).trim();

                // Проверка, является ли слово валидным
                if (!clientWord.isEmpty() && (lastWord.isEmpty() || lastWord.charAt(lastWord.length() - 1) == clientWord.charAt(0))) {
                    System.out.println("Прислал клиент: " + clientWord);
                    lastWord = clientWord;

                    // Генерация случайного слова (вместо этого можно использовать список слов)
                    String serverWord = generateWord(lastWord.charAt(lastWord.length() - 1));
                    System.out.println("Отправлено клиенту: " + serverWord);
                    outputStream.write(serverWord.getBytes());
                    outputStream.flush();

                    lastWord = serverWord;
                } else {
                    String message = "Неверное слово. Попробуйте снова.";
                    System.out.println(message);
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                }
            }
        }
    }

    private static String generateWord(char startChar) {
        // Элементарный список слов. В реальном приложении лучше использовать более обширный набор.
        String[] words = {"арбуз", "зебра", "ананас", "слон", "неделя", "яблоко", "окно", "орех", "игра", "трава", "забор", "водоросли", "абрикос", "бык", "бизон"};
        for (String word : words) {
            if (word.charAt(0) == startChar) {
                return word;
            }
        }
        return "К сожалению, у сервера нет слова на букву " + startChar;
    }
}
