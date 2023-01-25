package pir.demo.circuitbreakermonitoring.common.nofity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.product.application.ProductServiceRouter;

@Component
public class SlackCircuitBreakerNotifier implements CircuitBreakerNotifier{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceRouter.class);

    @Override
    public void closedToOpen(String circuitBreakerName) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("closed to open", circuitBreakerName));
    }

    @Override
    public void openedToHalfOpen(String circuitBreakerName) {
        LOGGER.error(SlackCircuitBreakerErrorMessage.create("opened to half-open", circuitBreakerName));
    }

    @Override
    public void halfOpenedToClose(String circuitBreakerName) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("half-opened to close", circuitBreakerName));
    }

    @Override
    public void halfOpenedToOpen(String circuitBreakerName) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("half-opened to open", circuitBreakerName));
    }
}
