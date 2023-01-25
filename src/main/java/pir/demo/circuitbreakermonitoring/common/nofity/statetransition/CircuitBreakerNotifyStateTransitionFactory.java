package pir.demo.circuitbreakermonitoring.common.nofity.statetransition;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.springframework.stereotype.Component;

@Component
public class CircuitBreakerNotifyStateTransitionFactory {

    private final CircuitBreakerNotifyHalfOpenToClosed circuitBreakerNotifyHalfOpenToClosed;
    private final CircuitBreakerNotifyHalfOpenToOpen circuitBreakerNotifyHalfOpenToOpen;
    private final CircuitBreakerNotifyClosedToOpen circuitBreakerNotifyClosedToOpen;
    private final CircuitBreakerNotifyOpenToHalfOpen circuitBreakerNotifyOpenToHalfOpen;
    private final CircuitBreakerNotifyNone circuitBreakerNotifyNone;

    public CircuitBreakerNotifyStateTransitionFactory(CircuitBreakerNotifyClosedToOpen circuitBreakerNotifyClosedToOpen,
                                                      CircuitBreakerNotifyHalfOpenToClosed circuitBreakerNotifyHalfOpenToClosed,
                                                      CircuitBreakerNotifyHalfOpenToOpen circuitBreakerNotifyHalfOpenToOpen,
                                                      CircuitBreakerNotifyOpenToHalfOpen circuitBreakerNotifyOpenToHalfOpen,
                                                      CircuitBreakerNotifyNone circuitBreakerNotifyNone) {
        this.circuitBreakerNotifyClosedToOpen = circuitBreakerNotifyClosedToOpen;
        this.circuitBreakerNotifyHalfOpenToClosed = circuitBreakerNotifyHalfOpenToClosed;
        this.circuitBreakerNotifyHalfOpenToOpen = circuitBreakerNotifyHalfOpenToOpen;
        this.circuitBreakerNotifyOpenToHalfOpen = circuitBreakerNotifyOpenToHalfOpen;
        this.circuitBreakerNotifyNone = circuitBreakerNotifyNone;
    }

    public CircuitBreakerNotifyStateTransition create(CircuitBreakerOnStateTransitionEvent event){
        final CircuitBreaker.StateTransition stateTransition = event.getStateTransition();

        switch (stateTransition){
            case OPEN_TO_HALF_OPEN:
                return circuitBreakerNotifyOpenToHalfOpen;
            case CLOSED_TO_OPEN:
                return circuitBreakerNotifyClosedToOpen;
            case HALF_OPEN_TO_CLOSED:
                return circuitBreakerNotifyHalfOpenToClosed;
            case HALF_OPEN_TO_OPEN:
                return circuitBreakerNotifyHalfOpenToOpen;
            default:
                return circuitBreakerNotifyNone;
        }
    }
}
