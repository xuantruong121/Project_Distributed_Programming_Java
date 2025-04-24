package iuh.fit.qlksfxapp.controller.EventBus;

import com.google.common.base.Supplier;

import java.util.List;

public class ConfirmDialogEvent {
    private final String message;
    private final Supplier<Boolean> onConfirm;
    private final Runnable onCancel;
    private final List<String> messSucAndFail;
    public ConfirmDialogEvent(String message, Supplier<Boolean> onConfirm, Runnable onCancel, List<String> messSucAndFail) {
        this.message = message;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.messSucAndFail = messSucAndFail;
    }
    public String getMessage() { return message; }
    public Supplier<Boolean> getOnConfirm() { return onConfirm; }
    public Runnable getOnCancel() { return onCancel; }
    public List<String> getMessSucAndFail() { return messSucAndFail;}
}
