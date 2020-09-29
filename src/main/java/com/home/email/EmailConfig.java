package com.home.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//取application.properties中的設定
//要用此類的值 所以要創建此類的對象 要用@Component
@Component
public class EmailConfig {
	@Value("${spring.mail.username}")
	private String emailFrom;

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

}
