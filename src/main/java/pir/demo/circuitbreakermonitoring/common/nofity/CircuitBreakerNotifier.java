package pir.demo.circuitbreakermonitoring.common.nofity;

public interface CircuitBreakerNotifier {

    void opened(String circuitBreakerName, Exception exception);

    void closed(String circuitBreakerName, Exception exception);

    void halfOpened(String circuitBreakerName, Exception exception);
}
