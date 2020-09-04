package nix.sinitsyn.scs.springcloudstreamexample.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeeVacation {

  String EMPLOYEE_VACATION_INPUT = "employeeVacationInput";
  String EMPLOYEE_VACATION_OUTPUT = "employeeVacationOutput";

  @Input(EmployeeVacation.EMPLOYEE_VACATION_INPUT)
  SubscribableChannel employeeVacationInput();

  @Output(EMPLOYEE_VACATION_OUTPUT)
  MessageChannel employeeVacationOutput();
}
