package net.big2.webrunner.core.common;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class WebRunnerObjectMapper extends ObjectMapper {
    public WebRunnerObjectMapper() {
        // TODO: write unit test to confirm we get the desired timestamp format
        super.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
