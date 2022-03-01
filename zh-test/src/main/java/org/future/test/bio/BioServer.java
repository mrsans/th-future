package org.future.test.bio;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String acceptValue = new String(bytes);
            System.out.println(acceptValue);
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("接收成功".getBytes());
            outputStream.flush();
        }

    }

}
