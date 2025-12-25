package ru.otus;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.RemoteSequenceServiceGrpc;
import ru.otus.protobuf.SequenceRange;
import ru.otus.service.SequenceClientServiceImpl;

public class GRPCClient {

    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int CLIENT_ITERATIONS = 50;
    private static final int CLIENT_SLEEP_MS = 1000;

    public static void main(String[] args) {
        ManagedChannel channel = null;
        try {
            channel = createChannel();
            runClientSequence(channel);
        } catch (Exception e) {
            logger.error("Client error occurred", e);
        } finally {
            shutdownChannel(channel);
        }
    }

    private static ManagedChannel createChannel() {
        return ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
    }

    private static void runClientSequence(ManagedChannel channel) {
        SequenceClientServiceImpl sequenceClientService = new SequenceClientServiceImpl();
        RemoteSequenceServiceGrpc.RemoteSequenceServiceStub stub = RemoteSequenceServiceGrpc.newStub(channel);

        SequenceRange sequenceRange =
                SequenceRange.newBuilder().setFirstValue(0).setLastValue(30).build();

        stub.getSequence(sequenceRange, sequenceClientService);

        processClientValues(sequenceClientService);
    }

    private static void processClientValues(SequenceClientServiceImpl sequenceClientService) {
        int currentValue = 0;

        for (int i = 0; i < CLIENT_ITERATIONS; i++) {
            sleepSafely(CLIENT_SLEEP_MS);

            int increment = sequenceClientService.getCurValueAndClear();
            currentValue = currentValue + 1 + increment;

            logger.info("Iteration {}: currentValue = {}", i + 1, currentValue);
        }

        sequenceClientService.onCompleted();
    }

    private static void sleepSafely(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Thread sleep interrupted", e);
        }
    }

    private static void shutdownChannel(ManagedChannel channel) {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            logger.info("Channel shutdown initiated");
        }
    }
}
