package nix.sinitsyn.scs.springcloudstreamexample.controller;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import nix.sinitsyn.scs.springcloudstreamexample.common.CustomSources;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.EmployeeVacation;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.NumberConvert;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.PolledStream;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.SportEvent;
import nix.sinitsyn.scs.springcloudstreamexample.model.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publish")
@Log4j2
public class PublishController {

  private static final String MESSAGE = "[PUBLISHED] Message: [%s]. Channel: [%s]";

  private final Map<String, String> sportMessages;
  private final Map<Integer, String> notifications;
  private final SportEvent sportEvent;
  private final NumberConvert numberConvert;
  private final EmployeeVacation employeeVacation;
  private final PolledStream polledStream;

  public PublishController(@Qualifier("sportMessages") Map<String, String> sportMessages,
                           @Qualifier("notifications") Map<Integer, String> notifications,
                           SportEvent sportEvent,
                           NumberConvert numberConvert,
                           EmployeeVacation employeeVacation,
                           PolledStream polledStream) {
    this.sportMessages = sportMessages;
    this.notifications = notifications;
    this.sportEvent = sportEvent;
    this.numberConvert = numberConvert;
    this.employeeVacation = employeeVacation;
    this.polledStream = polledStream;
  }

  @GetMapping("/sport/{sport}/{notificationType}")
  public String publishFootball(@PathVariable("sport") String sport,
                                @PathVariable("notificationType") int notificationType) {
    String message = sportMessages.getOrDefault(sport, "empty");
    String notification = notifications.getOrDefault(notificationType, "spam");
    log.info("Posting message {}", message);
    sportEvent.filterEventOutput().send(MessageBuilder
        .withPayload(message)
        .setHeader("notificationType", notification)
        .build());
    return String.format(MESSAGE, message, sportEvent.filterEventOutput().toString());
  }

  @GetMapping("/convert/{number}")
  public String publishConverting(@PathVariable("number") int number) {
    log.info("Number {}", number);
    numberConvert.convertNumberOutput().send(MessageBuilder
        .withPayload(number)
        .setHeader("converting", "true")
        .build());
    return String.format(MESSAGE, number, numberConvert.convertNumberOutput().toString());
  }

  @GetMapping("/vacation")
  public String publishVacation() {
    Employee john = new Employee("john", 26);
    Employee jake = new Employee("jake", 20);
    Employee blake = new Employee("blake", 21);
    List<Employee> employeeList = Arrays.asList(john, jake, blake);
    employeeVacation.employeeVacationOutput().send(
        MessageBuilder.withPayload(employeeList)
            .setHeader("vacation", true)
            .build());
    return String.format(MESSAGE, employeeList,  employeeVacation.employeeVacationOutput().toString());
  }

  @GetMapping("/poll/{message}")
  public String publishPoll(@PathVariable("message") String message) {
    polledStream.pollableOutput().send(new GenericMessage<>(message));
    return String.format(MESSAGE, message,  polledStream.pollableOutput().toString());
  }

  @GetMapping("/poll")
  public List<String> getAllPolledMessages() {
    List<String> messages = new ArrayList<>();
    boolean hasMessage = true;
    while (hasMessage) {
      hasMessage = polledStream.pollableInput().poll(message -> {
        String payload = new String((byte[]) message.getPayload());
        messages.add(payload);
      });
    }
    log.info("Get {} messages, {}", messages.size(), messages);
    return messages;
  }

  @GetMapping
  public String healthCheck() {
    return "ok";
  }
}
