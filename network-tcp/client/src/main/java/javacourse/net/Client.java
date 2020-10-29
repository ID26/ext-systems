package javacourse.net;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 8; i++) {
            SimpleClient sc = new SimpleClient(i);// создаем отдельный объект (поток-нить (thread))
            sc.start(); // вызываем метод старт java машина запускает поток на отдельном ядре
        }
    }
}

class SimpleClient extends Thread {
public static final String[] COMMAND={"HELLO", "MORNING", "DAY", "EVENING"};
private int cmdNumber;
public SimpleClient (int cmdNumber){
    this.cmdNumber=cmdNumber;
}
    @Override
    public void run() {
        try {
//                System.out.println("Started: " + LocalDateTime.now()); //print start thread
            Socket socket = new Socket("127.0.0.1", 25225);// ip address and port
            // на вход потока, преобразующего символы в слова, подаем поток,
            // преобразующий байты в символ, на вход которого передаем входной поток байтов
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // на выходной поток, преобразующего слова в символы, подаем поток,
            // преобразующий символы в байты, на вход которого передаем выходной поток байтов
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String command=COMMAND[cmdNumber%COMMAND.length];


            String sb = command+" "+"Ivan";
            bw.write(sb);// send result
            bw.newLine();
            bw.flush();//принудительное отправление

            String answer = br.readLine();
            System.out.println("Client got string: " + answer);

            //закрываем потоки
            br.close();
            bw.close();
//                System.out.println("Finished: " + LocalDateTime.now()); //print finish thread

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
