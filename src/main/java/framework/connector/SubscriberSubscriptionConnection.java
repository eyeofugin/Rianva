package framework.connector;

public class SubscriberSubscriptionConnection {

  Subscriber element;
  Subscription subscription;
  boolean persistant;

  public SubscriberSubscriptionConnection(Subscriber element, Subscription subscription) {
    this(element, subscription, false);
  }

  public SubscriberSubscriptionConnection(Subscriber element, Subscription subscription, boolean persistant) {
    this.element = element;
    this.subscription = subscription;
    this.persistant = persistant;
  }
}
