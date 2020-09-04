package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TimeProcessor {

  String OUTPUT = "convertTimeOutput";
  String INPUT = "convertTimeInput";

  @Input
  SubscribableChannel convertTimeInput();

  @Output
  MessageChannel convertTimeOutput();
}
