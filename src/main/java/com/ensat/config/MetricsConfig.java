package com.ensat.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;

@Component
public class MetricsConfig implements MeterBinder {

    private static final String FAILED_REQUESTS_METRIC_NAME = "custom.failed.requests";

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        meterRegistry.gauge(FAILED_REQUESTS_METRIC_NAME, Tags.of(Tag.of("status", "failed")), new AtomicInteger(0));
    }
}
