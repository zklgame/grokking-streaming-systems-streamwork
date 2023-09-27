package job.vehicle;

import api.EventCollector;
import api.Source;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SensorReader extends Source {
    private final int portBase;

    private BufferedReader reader;
    private int instanceId;

    public SensorReader(final String name, final int parallelism, final int port) {
        super(name, parallelism);
        this.portBase = port;
    }

    @Override
    public void getEvents(final EventCollector eventCollector) {
        try {
            final String vehicle = reader.readLine();
            if (vehicle == null) {
                // Exit when user closes the server.
                System.exit(0);
            }
            eventCollector.add(new VehicleEvent(vehicle));
            System.out.println("\nSensorReader :: instance " + instanceId + " --> " + vehicle);
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
