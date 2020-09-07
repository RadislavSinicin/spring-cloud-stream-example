package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NumberConvert {

  String INPUT = "convertNumberOutput";
  String OUTPUT = "convertNumberInput";

  @Input(NumberConvert.INPUT)
  SubscribableChannel convertNumberInput();

  @Output(NumberConvert.OUTPUT)
  MessageChannel convertNumberOutput();

}
