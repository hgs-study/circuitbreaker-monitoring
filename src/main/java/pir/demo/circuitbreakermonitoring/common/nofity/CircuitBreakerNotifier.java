package pir.demo.circuitbreakermonitoring.common.nofity;

public interface CircuitBreakerNotifier {

    void closedToOpen(String circuitBreakerName);

    void openedToHalfOpen(String circuitBreakerName);

    void halfOpenedToClose(String circuitBreakerName);
    void halfOpenedToOpen(String circuitBreakerName);
}
