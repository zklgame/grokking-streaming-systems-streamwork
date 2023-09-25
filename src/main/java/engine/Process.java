package engine;

public abstract class Process {
    private final Thread thread;

    public Process() {
        this.thread = new Thread() {
            @Override
            public void run() {
                while (runOnce());
            }
        };
    }

    public void start() {
        thread.start();
    }

    public abstract boolean runOnce();
}
