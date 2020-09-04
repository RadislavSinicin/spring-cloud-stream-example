package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SportEvent {

  String EVENT_OUTPUT = "eventOutput";
  String FILTER_EVENT_OUTPUT = "filterEventOutput";
  String FILTER_EVENT_INPUT = "filterEventInput";
  String EVENT_INPUT = "eventInput";

  @Input(SportEvent.FILTER_EVENT_INPUT)
  SubscribableChannel filterEventInput();

  @Input(SportEvent.EVENT_INPUT)
  SubscribableChannel eventInput();

  @Output(SportEvent.FILTER_EVENT_OUTPUT)
  MessageChannel filterEventOutput();

  @Output(SportEvent.EVENT_OUTPUT)
  MessageChannel eventOutput();
}
