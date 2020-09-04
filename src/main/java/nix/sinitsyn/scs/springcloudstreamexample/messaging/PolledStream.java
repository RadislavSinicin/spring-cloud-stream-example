package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;

public interface PolledStream {

  String POLLABLE_INPUT = "pollableInput";
  String POLLABLE_OUTPUT = "pollableOutput";

  @Input(PolledStream.POLLABLE_INPUT)
  PollableMessageSource pollableInput();

  @Output(PolledStream.POLLABLE_OUTPUT)
  MessageChannel pollableOutput();
}
