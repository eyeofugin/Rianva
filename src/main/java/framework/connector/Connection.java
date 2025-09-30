package framework.connector;


public class Connection {

    Object element;
    String methodName;
    boolean persistant;
    Class<? extends ConnectionPayload> payloadClass;

    public Connection(Object element, Class<? extends ConnectionPayload> payloadClass, String methodName) {
        this(element, payloadClass, methodName, false);
    }
    public Connection(Object element, Class<? extends ConnectionPayload> payloadClass, String methodName, boolean persistant) {
        this.element = element;
        this.methodName = methodName;
        this.payloadClass = payloadClass;
        this.persistant = persistant;
    }
}
