package nix.sinitsyn.scs.springcloudstreamexample;

import nix.sinitsyn.scs.springcloudstreamexample.messaging.EmployeeVacation;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.NumberConvert;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.PolledStream;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.EventStream;
import nix.sinitsyn.scs.springcloudstreamexample.messaging.TimeProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableBinding({
		Processor.class,
		TimeProcessor.class,
		EventStream.class,
		NumberConvert.class,
		EmployeeVacation.class,
		PolledStream.class})
public class SpringCloudStreamExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamExampleApplication.class, args);
	}

}
