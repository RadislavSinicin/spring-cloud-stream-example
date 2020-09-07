package nix.sinitsyn.scs.springcloudstreamexample;

import nix.sinitsyn.scs.springcloudstreamexample.messaging.EventStream;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.NumberConvert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringCloudStreamExampleApplicationTests {

  @Autowired
  private NumberConvert numberConvert;
  @Autowired
  private EventStream eventStream;
  @Autowired
  private MessageCollector messageCollector;

  @Test
  @SuppressWarnings("unchecked")
  void testShouldSuccessfullyConvertNumber() {
    String[] expectedValues = {"one", "two", "three", "four", "five"};
    for (int index = 0; index < 5; index++) {
      Message<Integer> messageAsInt = new GenericMessage<>(index + 1);
      numberConvert.convertNumberInput().send(messageAsInt);
      Message<String> received = (Message<String>) messageCollector.forChannel(eventStream.eventOutput()).poll();
      assertThat(received.getPayload()).isEqualTo(expectedValues[index]);
    }
  }
}
