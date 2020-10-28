package edu.javacourse.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            sendRequest();
        }
    }

    private static void sendRequest() throws IOException {
        Socket socket=new Socket("127.0.0.1", 25225);// ip address and port
        // на вход потока, преобразующего символы в слова, подаем поток,
        // преобразующий байты в символ, на вход которого передаем входной поток байтов
        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // на выходной поток, преобразующего слова в символы, подаем поток,
        // преобразующий символы в байты, на вход которого передаем выходной поток байтов
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String sb="Ivan";
        bw.write(sb);// send result
        bw.newLine();
        bw.flush();//принудительное отправление

        String answer = br.readLine();
        System.out.println("Client got string: "+answer);

        //закрываем потоки
        br.close();
        bw.close();
    }
}
