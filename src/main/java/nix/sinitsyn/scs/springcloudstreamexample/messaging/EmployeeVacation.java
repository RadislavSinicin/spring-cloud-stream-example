package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeeVacation {

  String INPUT = "employeeVacationInput";
  String OUTPUT = "employeeVacationOutput";

  @Input(EmployeeVacation.INPUT)
  SubscribableChannel employeeVacationInput();

  @Output(OUTPUT)
  MessageChannel employeeVacationOutput();
}
