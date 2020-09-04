package nix.sinitsyn.scs.springcloudstreamexample.service;

import lombok.extern.log4j.Log4j2;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.EmployeeVacation;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.NumberConvert;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.SportEvent;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.TimeProcessor;
import nix.sinitsyn.scs.springcloudstreamexample.model.Employee;
import nix.sinitsyn.scs.springcloudstreamexample.model.NotificationMessage;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ConverterService {

  @StreamListener(SportEvent.FILTER_EVENT_INPUT)
  @SendTo(SportEvent.EVENT_OUTPUT)
  public NotificationMessage convertMessageToUpperCase(@Payload String payload, @Header("kafka_receivedTimestamp") Long timestamp) {
    return NotificationMessage.builder()
        .message(payload.toUpperCase())
        .date(new Date(timestamp))
        .build();
  }

  @Transformer(inputChannel = NumberConvert.CONVERT_NUMBER_INPUT, outputChannel = SportEvent.EVENT_OUTPUT)
  public Object convertNumber(int number) {
    Object converterValue;
    switch (number) {
      case 1:
        converterValue = "one";
        break;
      case 2:
        converterValue = "two";
        break;
      case 3:
        converterValue = "three";
        break;
      case 4:
        converterValue = "four";
        break;
      case 5:
        converterValue = "five";
        break;
      default:
        throw new IllegalArgumentException("Not support this value for number");
    }
    log.info("Converting value {} to {}", number, converterValue);
    return converterValue;
  }

  @ServiceActivator(inputChannel = EmployeeVacation.EMPLOYEE_VACATION_INPUT, outputChannel = SportEvent.EVENT_OUTPUT)
  public String[] convertEmployees(List<Employee> employeeList) {
    log.info("Received employees as list: {}", employeeList);
    return employeeList.stream()
        .map(Employee::getName)
        .collect(Collectors.toList())
        .toArray(new String[]{});
  }

  @Transformer(inputChannel = TimeProcessor.INPUT, outputChannel = TimeProcessor.OUTPUT)
  public Object convertTime(Long timestamp) {
   // log.info("Converting time from long = {}", timestamp);
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    return dateFormat.format(timestamp);
  }
}
