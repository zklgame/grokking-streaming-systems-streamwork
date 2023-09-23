package api;

import java.util.List;

public abstract class Source extends Component {
    public Source(final String name) {
        super(name);
    }

    /**
     * Accept events from external into the system.
     * The function is abstract and needs to be implemented by users.
     * @param eventCollector The outgoing event collector
     */
    public abstract void getEvents(final List<Event> eventCollector);
}
