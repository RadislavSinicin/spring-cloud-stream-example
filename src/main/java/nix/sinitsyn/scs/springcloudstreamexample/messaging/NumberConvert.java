package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NumberConvert {

  String CONVERT_NUMBER_OUTPUT = "convertNumberOutput";
  String CONVERT_NUMBER_INPUT = "convertNumberInput";

  @Input(NumberConvert.CONVERT_NUMBER_INPUT)
  SubscribableChannel convertNumberInput();

  @Output(NumberConvert.CONVERT_NUMBER_OUTPUT)
  MessageChannel convertNumberOutput();

}
