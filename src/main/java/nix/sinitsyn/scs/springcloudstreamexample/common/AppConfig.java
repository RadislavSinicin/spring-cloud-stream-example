package nix.sinitsyn.scs.springcloudstreamexample.common;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.converter.ObjectStringMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
public class AppConfig {

  @Bean
  public Map<String, String> sportMessages() {
    final Map<String, String> messages = new HashMap<>();
    messages.put("f", "Today is UEFA Champions League Final!");
    messages.put("b", "See NBA basketball league tomorrow on Friday!");
    messages.put("w", "Wrestlemania 26 next week!");
    return messages;
  }

  @Bean
  public Map<Integer, String> notifications() {
    Map<Integer, String> notifications = new HashMap<>();
    notifications.put(1, "email");
    notifications.put(2, "sms");
    notifications.put(3, "telegram");
    return notifications;
  }

  @Bean
  @StreamMessageConverter
  public MessageConverter stringConverter() {
    return new ObjectStringMessageConverter();
  }
}
