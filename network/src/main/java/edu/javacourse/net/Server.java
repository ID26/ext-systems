package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000); //Локальный порт передаем, и можем прислать до 2000 запросов

        System.out.println("Server is started!!!");

        while (true) {
            Socket client = socket.accept(); //приходит сокет со входным и выходным потоками
            new SimpleServer(client).start();// создать объект SimpleServer передать ему сокет и запустить
        }
    }
}

class SimpleServer extends Thread{
    private Socket client; // поле сокет

    public SimpleServer(Socket client){
        this.client=client;
    }

    public void run(){
        handleRequest();
    }

    private void handleRequest(){
        try {
            // на вход потока, преобразующего символы в слова, подаем поток,
            // преобразующий байты в символ, на вход которого передаем входной поток байтов
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // на выходной поток, преобразующего слова в символы, подаем поток,
            // преобразующий символы в байты, на вход которого передаем выходной поток байтов
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            //ДЛЯ ТОГО, чтоб видоизменять строку не дорого для памяти,
            // просто стринг каждый раз создает новый объект
            StringBuilder sb = new StringBuilder("Hallow, ");
            String userName = br.readLine();
            System.out.println("Server got string: " + userName);
            Thread.sleep(2000); //статический метод sleep
            sb.append(userName);// к нашей строке прибавляем полученное из потока
            bw.write(sb.toString());// send result
            bw.newLine();
            bw.flush();//принудительное отправление
            //закрываем потоки
            br.close();
            bw.close();
            //закрываем соккет
            client.close();
        } catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }

}