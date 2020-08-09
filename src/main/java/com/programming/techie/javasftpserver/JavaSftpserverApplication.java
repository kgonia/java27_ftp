package com.programming.techie.javasftpserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.event.inbound.ApplicationEventListeningMessageProducer;
import org.springframework.integration.ftp.server.ApacheMinaFtpEvent;
import org.springframework.integration.ftp.server.ApacheMinaFtplet;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@EnableConfigurationProperties(value = FtpServerProperties.class)
public class JavaSftpserverApplication {

	private Log log = LogFactory.getLog(JavaSftpserverApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JavaSftpserverApplication.class, args);
		while (true);
	}

	@Bean
	public ApacheMinaFtplet getApacheMinaFtplet(){
		return new ApacheMinaFtplet();
	}

	@Bean
	public ApplicationEventListeningMessageProducer eventsAdapter() {
		ApplicationEventListeningMessageProducer producer =
				new ApplicationEventListeningMessageProducer();
		producer.setEventTypes(ApacheMinaFtpEvent.class);
		producer.setOutputChannel(eventChannel());
		return producer;
	}

	@Bean
	public MessageChannel eventChannel() {
		DirectChannel directChannel =  new DirectChannel();
		directChannel.subscribe(message -> log.info("" + message.getPayload().getClass().getSimpleName()));
		return directChannel;
	}

}
