package api.window.strategy;


public class FixedWindowStrategy extends SlidingWindowingStrategy {
    public FixedWindowStrategy(final long lengthMills, long watermarkMills) {
        super(lengthMills, lengthMills, watermarkMills);
    }
}
