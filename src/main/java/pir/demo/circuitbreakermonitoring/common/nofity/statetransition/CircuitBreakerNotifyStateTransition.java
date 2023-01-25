package pir.demo.circuitbreakermonitoring.common.nofity.statetransition;

public interface CircuitBreakerNotifyStateTransition {
    void notify(String circuitBreakerName);
}
