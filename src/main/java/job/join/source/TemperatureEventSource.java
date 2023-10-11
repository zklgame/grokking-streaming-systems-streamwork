package job.join.source;

import api.EventCollector;
import api.Source;
import job.join.event.TemperatureEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TemperatureEventSource extends Source {
    private final int portBase;

    private BufferedReader reader;
    private int instanceId;

    public TemperatureEventSource(final String name, final int parallelism, final int port) {
        super(name, parallelism);
        this.portBase = port;
    }

    @Override
    public void getEvents(final EventCollector eventCollector) {
        try {
            final String temperatureInfo = reader.readLine();
            if (temperatureInfo == null) {
                // Exit when user closes the server.
                System.exit(0);
            }

            final String[] values = temperatureInfo.split(",");
            final TemperatureEvent temperatureEvent = new TemperatureEvent(Float.parseFloat(values[0]), Integer.parseInt(values[1]));
            eventCollector.add(temperatureEvent);
            System.out.println("\nTemperatureEventSource :: instance " + instanceId + " --> " + temperatureEvent);
        } catch (final IOException e) {
            System.out.println("Temperature input needs to be in this format: {temperature},{zoneId}. For example: 90,3");
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
