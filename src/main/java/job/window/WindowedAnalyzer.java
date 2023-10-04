package job.window;

import api.EventCollector;
import api.groupingStrategy.GroupingStrategy;
import api.window.EventWindow;
import api.window.WindowedOperator;


public class WindowedAnalyzer extends WindowedOperator {
    private int instanceId;

    public WindowedAnalyzer(final String name, final int parallelism, final GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void apply(final EventWindow window, final EventCollector eventCollector) {
        System.out.println("WindowedAnalyzer:: instance " + instanceId + "  --> " + window);
    }

    @Override
    public void setupInstance(final int instanceId) {
        this.instanceId = instanceId;
    }
}
