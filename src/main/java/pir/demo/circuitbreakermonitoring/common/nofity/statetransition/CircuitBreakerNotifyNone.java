package pir.demo.circuitbreakermonitoring.common.nofity.statetransition;

import org.springframework.stereotype.Component;

@Component
public class CircuitBreakerNotifyNone implements CircuitBreakerNotifyStateTransition{

    @Override
    public void notify(String circuitBreakerName) {
        // do not working
    }
}
