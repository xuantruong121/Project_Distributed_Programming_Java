package iuh.fit.qlksfxapp.controller.EventBus;

public class NavigationEvent {
    private final String fxmlPath;
    private final Object data;

    public NavigationEvent(String fxmlPath, Object data) {
        this.fxmlPath = fxmlPath;
        this.data = data;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public Object getData() {
        return data;
    }
}