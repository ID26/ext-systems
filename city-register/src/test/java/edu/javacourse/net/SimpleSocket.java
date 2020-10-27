package edu.javacourse.net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleSocket {
    @Test
    public void simpleSocket() throws IOException{
        Socket socket=new Socket("java-course.ru", 80);

        InputStream is=socket.getInputStream();
        OutputStream os= socket.getOutputStream();

        String command="GET /sitemap.xml HTTP/1.1\r\nHost: java-course.ru\r\n\r\n";
        os.write(command.getBytes()); //выбираем вариант с массивом байтов, т. к. сторкахорошо преобразовыается в байты
        os.flush();// чтобне ждать накопления данных, а отпралять их по мере поступления

        int c   =0;
        while ((c=is.read())!=-1){//читаем данные пока они есть нет данных = -1, есть данные = число больше нуля
            System.out.print((char)c);
        }
        socket.close();
    }
}
