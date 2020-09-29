package com.home.email;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {
	@Autowired
	private EmailService emailService;
	@RequestMapping("/simple")
	public String sendSimpleEmail() {
		emailService.sendSimpleMail("windyjen4433@gmail.com","Hi" , "JAVA HERE ");
		return "success";
	}
	
	@RequestMapping("/attach")
	@ResponseBody
	public String sendAttacmentEmail() {
		//先在src/main/resources下創一個file放檔案
		File file = new File("src/main/resources/file/project.txt");
		emailService.sendAttachmentMail("windyjen4433@gmail.com", "file","look", file);
		return "success";
	}
	
	
	@RequestMapping("/sendtem")
	@ResponseBody
	public String sendTemplateEmail() {
		emailService.sendTemplateMail("windyjen4433@gmail.com", "template","tem.html");
		return "success";
	}
	
}
