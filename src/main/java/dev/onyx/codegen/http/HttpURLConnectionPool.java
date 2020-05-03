package dev.onyx.codegen.http;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class HttpURLConnectionPool {

    private final int connectionPoolSize;
    private final Semaphore issuedBlock;
    private final ExecutorService executorPool;
    private final Supplier<HttpURLConnection> connectionSupplier;
    private List<HttpURLConnection> connections;

    public HttpURLConnectionPool(
            int connectionPoolSize,
            Supplier<HttpURLConnection> connectionSupplier
    ) {
        this.connectionPoolSize = connectionPoolSize;
        this.connectionSupplier = connectionSupplier;
        this.executorPool = Executors.newFixedThreadPool(connectionPoolSize);
        this.connections = new ArrayList<>(connectionPoolSize);
        this.issuedBlock = new Semaphore(connectionPoolSize);
    }

    public synchronized void acquireConnectionThen(Consumer<HttpURLConnection> action) throws InterruptedException {
        issuedBlock.acquire();

        final HttpURLConnection connection;
        if(!this.connections.isEmpty()) {
            connection = this.connections.remove(0);
        } else {
            connection = connectionSupplier.get();
        }

        executorPool.submit(() -> {
            try {
                action.accept(connection);
            } finally {
                if(connection != null) {
                    connections.add(connection);
                }
                issuedBlock.release();
            }
        });
    }

    public synchronized void flush() {
        while (!connections.isEmpty()) {
            HttpURLConnection connection = connections.remove(0);
            connection.disconnect();
        }

        connections = new ArrayList<>(connectionPoolSize);
    }

}
