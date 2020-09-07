package nix.sinitsyn.scs.springcloudstreamexample.sink;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.PolledStream;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.EventStream;
import nix.sinitsyn.scs.springcloudstreamexample.model.NotificationMessage;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificationService {

  private final PolledStream polledStream;

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['notificationType']=='email'")
  public void emailNotification(Message<NotificationMessage> message) {
    log.info("[○ EMAIL NOTIFICATION ○]: {}", message.getPayload());
  }

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['notificationType']=='sms'")
  public void smsNotification(Message<NotificationMessage> message) {
    log.info("[♦ SMS NOTIFICATION ♦]: {}", message.getPayload());
  }

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['notificationType']=='telegram'")
  public void telegramNotification(Message<NotificationMessage> message) {
    log.info("[☺ TELEGRAM NOTIFICATION ☺]: {}", message.getPayload());
  }

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['notificationType']=='spam'")
  public void spamHandler(Message<String> message) {
    String payload = message.getPayload();
    log.warn("[♀ SPAM ♀]: {}", message.getPayload());
    if (payload.contains("UEFA")) {
      throw new IllegalArgumentException("Football is not a spam! Don't need retry");
    }
    throw new RuntimeException("Spam message. Try to retry");
  }

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['converting']=='true'")
  public void handleConvertedNumber(Object numberAsString) {
    log.info("[↨ SIMPLE NOTIFICATION ↨]: Your result of converting is {}", numberAsString);
  }

  @StreamListener(target = EventStream.EVENT_INPUT, condition = "headers['vacation']==true")
  public void handleEmployeesWithVacation(Message<String[]> message) {
    log.info("These employees want a vacation: {}", Arrays.toString(message.getPayload()));
  }

  @Scheduled(fixedDelay = 15000)
  public void handlePollableMessages() {
    boolean hasMessage = true;
    AtomicInteger messageCount = new AtomicInteger(0);
    while (hasMessage) {
      hasMessage = polledStream.pollableInput().poll(message -> {
        String payload = new String((byte[]) message.getPayload());
        if (payload.length() < 3) {
          throw new IllegalArgumentException("Consumed value length invalid");
        }
        log.info("Retrieved pollable payload: {}",payload);
        messageCount.incrementAndGet();
      });
    }
    log.info("Got {} pollable messages for the last 15 seconds", messageCount.get());
  }

  @StreamListener(Sink.INPUT)
  public void loggerSink(String date) {
    log.info("Current time: {}", date);
  }
}
