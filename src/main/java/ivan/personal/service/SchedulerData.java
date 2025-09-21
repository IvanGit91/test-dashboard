package ivan.personal.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SchedulerData {

    private static final Logger LOG = Logger.getLogger(SchedulerData.class.getName());

    @Scheduled(initialDelay = 0, fixedDelayString = "${fixedDelay.in.milliseconds}")
    @Async("threadPoolSchedulerData")
    public void reportCurrentTime() {
        try {
            LOG.log(Level.INFO, " in progress ... ");
        } finally {
            Thread.currentThread().interrupt();
        }
    }
}

