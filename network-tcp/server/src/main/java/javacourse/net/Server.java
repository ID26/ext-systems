package javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000); //Локальный порт передаем, и можем прислать до 2000 запросов
        Map<String, Greetable> handlers = loadHandlers(); // ключ-строка, объект Greetable

        System.out.println("Server is started!!!");

        while (true) {
            Socket client = socket.accept(); //приходит сокет со входным и выходным потоками
            new SimpleServer(client, handlers).start();// создать объект SimpleServer передать ему сокет и запустить
        }
    }

    private static Map<String, Greetable> loadHandlers() {
        Map<String, Greetable> result = new HashMap<>();
        //загружаем свойсва/property файла имя=значение
        try (InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            for (Object command : properties.keySet()) { //пробегаем по списку свойств
                String className = properties.getProperty(command.toString()); //находим имя класса в свойсвах
                Class<Greetable> cl=(Class<Greetable>) Class.forName(className);//загружаем нужный класс
                Greetable handler = cl.getConstructor().newInstance();//создаем конструктором объект этого класса
                result.put(command.toString(), handler);// и кладем в мап объект с его командой
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return result;
    }
}

class SimpleServer extends Thread {
    private Socket client; // поле сокет
    private Map<String,Greetable> handlers;

    public SimpleServer(Socket client, Map<String, Greetable> handlers) {
        this.client = client;
        this.handlers=handlers;
    }

    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        try {
            // на вход потока, преобразующего символы в слова, подаем поток,
            // преобразующий байты в символ, на вход которого передаем входной поток байтов
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // на выходной поток, преобразующего слова в символы, подаем поток,
            // преобразующий символы в байты, на вход которого передаем выходной поток байтов
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            //ДЛЯ ТОГО, чтоб видоизменять строку не дорого для памяти,
            // просто стринг каждый раз создает новый объект
            String request = br.readLine();
            String[] lines = request.split("\\s+"); //делит строку на подстроки (слова) по указанному метасимволу
            // (изучить regex символы) первый бэкслэш экранирует->метасимвол->+указывает на любое колличество пробелов
            String command = lines[0];
            String userName = lines[1];
            System.out.println("Server got string 1: " + command);
            System.out.println("Server got string 2: " + userName);
//            Thread.sleep(2000); //статический метод sleep
            String response=buildResponse(command, userName);
            bw.write(response);// send response
            bw.newLine();
            bw.flush();//принудительное отправление
            //закрываем потоки
            br.close();
            bw.close();
            //закрываем соккет
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private String buildResponse(String command, String userName) {
        Greetable handler=handlers.get(command);
        if(handler!=null){
            return handler.buildResponse(userName);
        }
        return "Hallo "+ userName;
    }
}

