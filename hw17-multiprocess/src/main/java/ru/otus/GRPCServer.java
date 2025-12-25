package ru.otus;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.RemoteSequenceServiceImpl;

public class GRPCServer {

    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) {
        GRPCServer server = new GRPCServer();
        server.start();
    }

    private void start() {
        Server server = null;
        try {
            server = buildServer();
            server.start();
            logServerInfo(server);
            server.awaitTermination();
        } catch (IOException e) {
            logger.error("Failed to start server", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Server interrupted", e);
        } finally {
            shutdownServer(server);
        }
    }

    private Server buildServer() {
        return ServerBuilder.forPort(SERVER_PORT)
                .addService(new RemoteSequenceServiceImpl())
                .build();
    }

    private void logServerInfo(Server server) {
        logger.info("Server started successfully");
        logger.info("Listening on port: {}", SERVER_PORT);
        logger.info("Services registered: {}", server.getServices().size());
        logger.info("Server waiting for client connections...");
    }

    private void shutdownServer(Server server) {
        if (server != null && !server.isShutdown()) {
            server.shutdown();
            logger.info("Server shutdown initiated");
        }
    }
}
