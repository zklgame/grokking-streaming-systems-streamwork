package api;

import java.util.List;

public abstract class Operator extends Component {
    public Operator(final String name) {
        super(name);
    }

    /**
     * Apply logic to the incoming event and generate results.
     * The function is abstract and needs to be implemented by users.
     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    public abstract void apply(final Event event, final List<Event> eventCollector);
}
