package pir.demo.circuitbreakermonitoring.common.nofity;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.common.nofity.statetransition.CircuitBreakerNotifyStateTransition;
import pir.demo.circuitbreakermonitoring.common.nofity.statetransition.CircuitBreakerNotifyStateTransitionFactory;

@Component
public class CircuitBreakerNotifyDecorator {

    private final CircuitBreakerNotifyStateTransitionFactory circuitBreakerNotifyStateTransitionFactory;

    public CircuitBreakerNotifyDecorator(CircuitBreakerNotifyStateTransitionFactory circuitBreakerNotifyStateTransitionFactory) {
        this.circuitBreakerNotifyStateTransitionFactory = circuitBreakerNotifyStateTransitionFactory;
    }

    public void decorate(CircuitBreaker circuitBreaker){
        circuitBreaker.getEventPublisher()
                        .onStateTransition(event -> {
                            CircuitBreakerNotifyStateTransition circuitBreakerNotify = circuitBreakerNotifyStateTransitionFactory.create(event);
                            circuitBreakerNotify.notify(event.getCircuitBreakerName());
                        });
    }
}
