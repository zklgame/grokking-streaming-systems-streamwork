package job.join.source;

import api.EventCollector;
import api.Source;
import job.join.event.VehicleEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class VehicleEventSource extends Source {
    private final int portBase;

    private BufferedReader reader;
    private int instanceId;

    public VehicleEventSource(final String name, final int parallelism, final int port) {
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

            final String[] values = vehicle.split(",");
            final VehicleEvent vehicleEvent = new VehicleEvent(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), System.currentTimeMillis());
            eventCollector.add(vehicleEvent);
            System.out.println("\nVehicleEventSource :: instance " + instanceId + " --> " + vehicleEvent);
        } catch (final IOException e) {
            System.out.println("Vehicle input needs to be in this format: {make},{model},{year},{zoneId}. For example: XXX,AA,2020,3");
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
