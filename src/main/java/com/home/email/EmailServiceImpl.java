package com.home.email;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
@Service
public class EmailServiceImpl implements EmailService {
	//獲取發件人email
	@Autowired
	private EmailConfig emailconfig;
	//因為我們在pom.xml有依賴spring-boot-starter-mailˋ就可以找到 JavaMailSender class
	@Autowired //也讓他自動注入
	private JavaMailSender mailSender;
	
	//加入FreemarkerConfigurer
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Override
	public void sendSimpleMail(String sendTo, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailconfig.getEmailFrom());
		message.setTo(sendTo);
		message.setSubject(title);
		message.setText(content);
		//實現簡單由件發送 要使用mailSender中的send
		mailSender.send(message);
		

	}
	@Override
	public void sendAttachmentMail(String sendTo, String title, String content, File file) {
		//把發送的內容封裝成MimeMessage類 並使用mailSender創建郵件對象
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			//需要此helper class來幫忙設定內容
			MimeMessageHelper helper = new MimeMessageHelper(msg,true);
			helper.setTo(sendTo);
			helper.setSubject(title);
			helper.setText(content);
			//把附件先鋒裝成FileSystemResource class 的對象
			FileSystemResource r = new FileSystemResource(file); 
			//把這個對象再放到msg郵件對象中 其中前面是attachmentFilename(附檔的檔名)
			helper.addAttachment("附件",r);
			
			mailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void sendTemplateMail(String sendTo, String title, String temName) {
		//先在srv/main/resources下new templates folder新增tem.html 
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			//設定發送人
			helper.setFrom(emailconfig.getEmailFrom());
			helper.setTo(sendTo);
			helper.setSubject(title);
			//new map 來裝template用的data
			Map<String,Object> model = new HashMap();
			model.put("username","John");
			//要把data放到template中 利用FreeMarkerConfigurer取得template
			try {
				Template template = freeMarkerConfigurer.getConfiguration().getTemplate(temName);
				//把template轉成string給此utils
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				helper.setText(html,true);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MessagingException e) {
				e.printStackTrace();
		}
		mailSender.send(msg);
	}

}
