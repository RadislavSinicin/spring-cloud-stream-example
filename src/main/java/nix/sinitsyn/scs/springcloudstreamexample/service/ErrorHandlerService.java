package nix.sinitsyn.scs.springcloudstreamexample.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ErrorHandlerService {

  @ServiceActivator(inputChannel = "events.sportEventsGroup.errors")
  public void handleEventError(Message<?> failedMessage) {
    logError("Event error: {}", failedMessage);
  }

  @ServiceActivator(inputChannel = "numberConvert.numberConvertGroup.errors")
  public void handleConvertError(Message<?> failedMessage) {
    logError("Convert error: {}", failedMessage);
  }

  @StreamListener(target = "errorChannel")
  public void globalErrors(Message<?> failedMessage) {
    logError("Global error: {}", failedMessage);
  }

  private void logError(String logMsgFormat, Message<?> failedMessage) {
    Exception throwable = (Exception) failedMessage.getPayload();
    log.error(logMsgFormat, throwable.getMessage());
  }
}
