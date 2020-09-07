package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EventStream {

  String EVENT_OUTPUT = "eventOutput";
  String FILTER_EVENT_OUTPUT = "filterEventOutput";
  String FILTER_EVENT_INPUT = "filterEventInput";
  String EVENT_INPUT = "eventInput";

  @Input(EventStream.FILTER_EVENT_INPUT)
  SubscribableChannel filterEventInput();

  @Input(EventStream.EVENT_INPUT)
  SubscribableChannel eventInput();

  @Output(EventStream.FILTER_EVENT_OUTPUT)
  MessageChannel filterEventOutput();

  @Output(EventStream.EVENT_OUTPUT)
  MessageChannel eventOutput();
}
