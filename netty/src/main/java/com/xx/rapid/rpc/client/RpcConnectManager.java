package com.xx.rapid.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.protostuff.runtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;

public class RpcConnectManager {
    private static volatile  RpcConnectManager RPC_CONNECT_MANAGER = new RpcConnectManager();
    private static Logger log = LoggerFactory.getLogger(RpcConnectManager.class);

    // 缓存: 对应一个实际的业务处理器 client
    private Map<InetSocketAddress, RpcClientHandler> connectedHandlerMap = new ConcurrentHashMap<>();
    //任务 runnable: 用于异步提交线程池
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16,16, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    //eventLoop:默认 cpu * 2 的大小
    private EventLoopGroup eventloopGroup = new NioEventLoopGroup(4);
    private  RpcConnectManager(){

    }

    public RpcConnectManager getInstance(){
        return RPC_CONNECT_MANAGER;
    }

    //异步连接

    public void connect(final String serverAddress){
        List<String> allServerAddress = Arrays.asList(serverAddress.split(","));
        updateConnectedServer(allServerAddress);

    }

    /**
     * 更新缓存信息, 并发起连接
     * @param allServerAddress
     */
    private void updateConnectedServer(List<String> allServerAddress) {
        if(CollectionUtils.isEmpty(allServerAddress)) {
            log.error("No available server addresses");
        }
        HashSet<InetSocketAddress> newAllServerNodeSet = new HashSet<>();
        for(String serverAddress: allServerAddress){
            String[] array = serverAddress.split(":");
            if(array.length != 2)
                log.error("Illegal server address" + serverAddress);
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            final InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
            newAllServerNodeSet.add(remoteAddress);
        }

        //2. 异步的发起连接, 如何知道它已经连接了, 然后做缓存
        for(InetSocketAddress serverNodeAddress: newAllServerNodeSet){
            if(!connectedHandlerMap.containsKey(serverNodeAddress))
                connectAsync(serverNodeAddress);
        }
        //3. 如果 在缓存中存在 就从缓存中连接要清除
    }

    private void connectAsync(InetSocketAddress remotePeer) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Bootstrap b = new Bootstrap();
                b.group(eventloopGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new RpcClientInitializer());
                connect(b, remotePeer);

            }
        });
    }
    //业务执行器
    private void connect(final Bootstrap b, InetSocketAddress remotePeer) {
        //?channelFuture 加监听
        final ChannelFuture channelFuture = b.connect(remotePeer);
        //2. 连接失败的时候加监听, 清除资源后发起重连操作
        channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("channelFuture.channel close operationComplte, remote peer ="
                future.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        log.warn(" connect fail, to reconnect! ");
                        clearConnected();
                        connect(b, remotePeer);
                    }
                }))
            }
        })
        //3. 连接成功的时候添加监听,
    }

    /**
     * 连接失败的时候
     */
    private void clearConnected() {
    }
}
