package framework.connector;

public class Connection {

  Object element;
  String methodName;
  boolean persistant;

  public Connection(Object element, String methodName) {
    this(element, methodName, false);
  }

  public Connection(Object element, String methodName, boolean persistant) {
    this.element = element;
    this.methodName = methodName;
    this.persistant = persistant;
  }
}
