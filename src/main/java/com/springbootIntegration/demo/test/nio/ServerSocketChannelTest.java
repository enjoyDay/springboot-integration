package com.springbootIntegration.demo.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.nio.ByteBuffer;

/**
 * @author liukun
 * @description 创建服务器
 * @date 2019/11/5
 */
public class ServerSocketChannelTest {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器创建成功。。。");
        //1、创建服务端通道
        ServerSocketChannel sChannel = ServerSocketChannel.open();
        //2、设置非阻塞
        sChannel.configureBlocking(false);
        //3、绑定端口
        sChannel.bind(new InetSocketAddress(8888));
        //4、获取选择器
        Selector selector = Selector.open();
        //5、将选择器注册到通道中
        sChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6、轮询获取可以获取到的就绪事件
        while (selector.select() > 0) {
            //7、获取所有已经就绪的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                //8、判断是什么事件准备就绪
                if (sk.isAcceptable()) {
                    //9、接收客户端连接
                    SocketChannel socketChannel = sChannel.accept();
                    //10、设置阻塞模式
                    socketChannel.configureBlocking(false);
                    //11、将该通道注册到服务器上,用于读,第一个参数是选择器，第二个是就绪事件，第三个是依附到通道上的对象
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocateDirect(1024));
                } else if (sk.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    ByteBuffer buf = (ByteBuffer)sk.attachment();//获取依附的对象
                    long read = socketChannel.read(buf);
                    while (read > 0) {
                        buf.flip();
                        while(buf.hasRemaining()){
                            System.out.print((char)buf.get());
                        }
                        System.out.println();
                        buf.clear();
                        read = socketChannel.read(buf);
                    }
                    if (read == -1){
                        socketChannel.close();
                    }
                }
            }
            iterator.remove();
        }

        System.out.println("服务器端结束...");
    }
}
