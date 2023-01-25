package pir.demo.circuitbreakermonitoring.common.nofity.statetransition;

import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.common.nofity.CircuitBreakerNotifier;

@Component
public class CircuitBreakerNotifyHalfOpenToOpen implements CircuitBreakerNotifyStateTransition{

    private final CircuitBreakerNotifier circuitBreakerNotifier;

    public CircuitBreakerNotifyHalfOpenToOpen(CircuitBreakerNotifier circuitBreakerNotifier) {
        this.circuitBreakerNotifier = circuitBreakerNotifier;
    }

    @Override
    public void notify(String circuitBreakerName) {
        circuitBreakerNotifier.halfOpenedToOpen(circuitBreakerName);
    }
}
