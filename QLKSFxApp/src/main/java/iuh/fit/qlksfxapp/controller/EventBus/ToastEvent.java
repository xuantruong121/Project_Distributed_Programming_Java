package iuh.fit.qlksfxapp.controller.EventBus;

public class ToastEvent {
    public enum ToastType { SUCCESS, ERROR }

    private final String message;
    private final ToastType type;

    public ToastEvent(String message, ToastType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() { return message; }
    public ToastType getType() { return type; }
}
