package api;

import java.io.Serializable;

public abstract class Source extends Component implements Serializable {
    public Source(final String name, final int parallelism) {
        super(name, parallelism);
    }

    /**
     * Accept events from external into the system.
     * The function is abstract and needs to be implemented by users.
     * @param eventCollector The outgoing event collector
     */
    public abstract void getEvents(final EventCollector eventCollector);
}
