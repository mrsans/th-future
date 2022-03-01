package org.future.test.nio;

import cn.hutool.core.io.BufferUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

    @Test
    public void selectorTest_1() throws IOException, InterruptedException {
        Selector selector = Selector.open();
// 2.建立channel对象，并绑定在8080端口上
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        ssc.socket().bind(address);
// 3.将channel设定为非阻塞方式
        ssc.configureBlocking(false);
// 向selector注册channel以及我们感兴趣的事件
        SelectionKey skey = ssc.register(selector, SelectionKey.OP_ACCEPT);// 这边注册了accept，服务器接受到client连接事件
// 4、简单模拟下轮询过程
        while (true) {

        }
    }

    @Test
    public void client() throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8881));
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        sc.read(allocate);
        byte[] array = allocate.array();
        System.out.println(new String(array));
    }


}
