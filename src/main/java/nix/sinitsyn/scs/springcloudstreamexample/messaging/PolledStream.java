package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;

public interface PolledStream {

  String INPUT = "pollableInput";
  String OUTPUT = "pollableOutput";

  @Input(PolledStream.INPUT)
  PollableMessageSource pollableInput();

  @Output(PolledStream.OUTPUT)
  MessageChannel pollableOutput();
}
