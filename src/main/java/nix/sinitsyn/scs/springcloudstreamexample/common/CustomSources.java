package nix.sinitsyn.scs.springcloudstreamexample.common;

import lombok.extern.log4j.Log4j2;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.PolledStream;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

@Configuration
@Log4j2
public class CustomSources {

  @Bean
  @InboundChannelAdapter(
      value = Source.OUTPUT,
      poller = @Poller(fixedDelay = "10000", maxMessagesPerPoll = "1"))
  public MessageSource<Long> timeMessageSource() {
    return () -> {
      Long currentTime = new Date().getTime();
      return MessageBuilder.withPayload(currentTime).build();
    };
  }

  @Bean
  @InboundChannelAdapter(
      value = PolledStream.POLLABLE_OUTPUT,
      poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
  public MessageSource<String> randomStringSource() {
    return () -> {
      String sourceMessage = UUID.randomUUID().toString().substring(0, 5);
      return MessageBuilder.withPayload(sourceMessage).build();
    };
  }
}
