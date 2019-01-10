package context;

import java.util.HashMap;

public class ContextMap extends HashMap {
    private ContextMap() {}
    private static class InstanceHolder {
        private static final ContextMap INSTANCE = new ContextMap();
    }
    public static ContextMap getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
