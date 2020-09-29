package com.home.email;

import java.io.File;

public interface EmailService {
//發送text文件
	void sendSimpleMail(String sendTo, String title, String content);

//發送帶附件的郵件 File類型
	void sendAttachmentMail(String sendTo, String title, String content, File file);

//發送template email
	void sendTemplateMail(String sendTo, String title, String info);

}
