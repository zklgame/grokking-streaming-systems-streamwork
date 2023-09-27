package job.transaction;

import api.EventCollector;
import api.Source;
import job.transaction.event.TransactionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;

public class TransactionSource extends Source {
    private final int portBase;

    private BufferedReader reader;
    private int instanceId;

    public TransactionSource(final String name, final int parallelism, final int port) {
        super(name, parallelism);
        this.portBase = port;
    }

    @Override
    public void getEvents(final EventCollector eventCollector) {
        try {
            final String transaction = reader.readLine();
            if (transaction == null) {
                // Exit when user closes the server.
                System.exit(0);
            }

            final float amount;
            final long merchandiseId;
            try {
                final String[] values = transaction.split(",");
                amount = Float.parseFloat(values[0]);
                merchandiseId = Long.parseLong(values[1]);
            } catch (final Exception e) {
                System.out.println("Input needs to be in this format: {amount},{merchandiseId}. For example: 1.0,2");
                return;
            }

            final TransactionEvent transactionEvent = new TransactionEvent(UUID.randomUUID().toString(), amount, new Date(), merchandiseId, 1);

            // add event to channel
            eventCollector.add(transactionEvent);
            eventCollector.add("channel1", transactionEvent);

            System.out.println("\nTransactionSource :: instance " + instanceId + " --> " + transactionEvent);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
        reader = setupSocketReader(portBase + instanceId);
    }

    private BufferedReader setupSocketReader(final int port) {
        try {
            final Socket socket = new Socket("localhost", port);
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
