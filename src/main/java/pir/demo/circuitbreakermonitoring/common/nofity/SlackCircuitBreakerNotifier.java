package pir.demo.circuitbreakermonitoring.common.nofity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.product.application.ProductServiceRouter;

@Component
public class SlackCircuitBreakerNotifier implements CircuitBreakerNotifier{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceRouter.class);

    @Override
    public void opened(String circuitBreakerName, Exception e) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("opened", circuitBreakerName, e));
    }

    @Override
    public void closed(String circuitBreakerName, Exception e) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("closed", circuitBreakerName, e));
    }

    @Override
    public void halfOpened(String circuitBreakerName, Exception e) {
        LOGGER.info(SlackCircuitBreakerErrorMessage.create("halfOpened", circuitBreakerName, e));
    }
}
