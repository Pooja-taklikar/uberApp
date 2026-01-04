package com.codingshuttle.uber.uberApp;

import com.codingshuttle.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"naxatic933@emaxasp.com",
				"This is the testing email",
				"Body of my email");
	}

	@Test
	void sentEmailMultiple() {
		String emails[] = {
				"naxatic933@emaxasp.com",
				"poojataklik0@gmail.com",
				"taklikarpooja11@gmail.com",
				"ptaklikar980@gmail.com"
		};
		emailSenderService.sendEmail(
				emails,
				"This is the testing email",
				"Hello from uber demo application");
	}

}
