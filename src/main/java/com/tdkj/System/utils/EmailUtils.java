package com.tdkj.System.utils;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/17 15:30
 */
@Slf4j
public class EmailUtils {

    private static String mailFrom = null;// 指明邮件的发件人
    private static String password_mailFrom = null;// 指明邮件的发件人登陆密码
    private static String mailTo = null; // 指明邮件的收件人
    private static String mailTittle = null;// 邮件的标题
    private static String mailText = null; // 邮件的文本内容
    private static String mail_host = null; // 邮件的服务器域名
    private static String photoSrc = null; // 发送图片的路径



    /**
     * @Author houxuyang
     * @Description //发送单人邮件
     * @Date 9:30 2020/7/21
     * @Param [mail_host, mailFrom, password_mailFrom, mailTo, mailTittle, mailText]
     * @return int
     **/
    public static int sandEmail(String mail_host,String mailFrom,String password_mailFrom,String mailTo,String mailTittle,String mailText)throws Exception{
        Properties properties =new Properties();
        properties.setProperty("mail.transport.protocol","SMTP");
        properties.setProperty("mail.host",mail_host); //smtp.qq.com  2331438941      kddktudsocxdebdg  smtp.qq.com
        properties.setProperty("mail.smtp.auth","true");
        //创建验证器验证发送者
        Authenticator auth =new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {   //houxuyang0801 KXIRPGFLESJBQYAO
                //验证邮箱用户和授权码
                return new PasswordAuthentication(mailFrom,password_mailFrom);
            }
        };
        //设置发送对象及配置信息
        Session session =Session.getInstance(properties,auth);
        Message message =new MimeMessage(session);
        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
        //邮件标题
        message.setSubject(mailTittle);
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        // 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mailText, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        // 将multipart对象放到message中
        message.setContent(multipart);
        // 保存邮件
        message.saveChanges();
        //发送邮件
        Transport.send(message);
        return  1;
    }

    /**
     * @Author houxuyang
     * @Description //普通方式发送
     * @Date 16:33 2020/7/17
     * @Param [mail_host, mailFrom, password_mailFrom, mailTo, mailBcc, mailcc, mailTittle, mailText]
     * @return int
     **/
    public static int sandEmail(String mail_host,String mailFrom,String password_mailFrom,String mailTo,String mailBcc,String mailcc,String mailTittle,String mailText)throws Exception{
        Properties properties =new Properties();
        properties.setProperty("mail.transport.protocol","SMTP");
        properties.setProperty("mail.host",mail_host); //smtp.qq.com  2331438941      kddktudsocxdebdg  smtp.qq.com
        properties.setProperty("mail.smtp.auth","true");
        //创建验证器验证发送者
        Authenticator auth =new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {   //houxuyang0801 KXIRPGFLESJBQYAO
                //验证邮箱用户和授权码
                return new PasswordAuthentication(mailFrom,password_mailFrom);
            }
        };
        //设置发送对象及配置信息
        Session session =Session.getInstance(properties,auth);
        Message message =new MimeMessage(session);
        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
        if (mailBcc!=null&&mailBcc!=""){
            message.setRecipient(Message.RecipientType.BCC,new InternetAddress(mailBcc));//抄送人 2924332408@qq.com
        }
        if (mailcc!=null&&mailcc!=""){
            message.setRecipient(Message.RecipientType.CC,new InternetAddress(mailcc));//密件人 //changhr2012@163.com
        }
        //邮件标题
        message.setSubject(mailTittle);
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        // 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mailText, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        // 将multipart对象放到message中
        message.setContent(multipart);
        // 保存邮件
        message.saveChanges();
        //发送邮件
        Transport.send(message);
        return  1;
    }
    /**
     * @Author houxuyang
     * @Description //带附件发送
     * @Date 16:33 2020/7/17
     * @Param [mail_host, mailFrom, password_mailFrom, mailTo, mailBcc, mailcc, mailTittle, mailText, FileUrl]
     * @return int
     **/
    public static int sandEmail(String mail_host,String mailFrom,String password_mailFrom,String mailTo,String mailBcc,String mailcc,String mailTittle,String mailText,String FileUrl)throws Exception{
        //获取邮箱服务器地址连接
        Properties properties =new Properties();
        properties.setProperty("mail.transport.protocol","SMTP");
        properties.setProperty("mail.host",mail_host); //"smtp.163.com"   smtp.qq.com  2331438941      kddktudsocxdebdg
        properties.setProperty("mail.smtp.auth","true"); //"houxuyang0801@163.com","KXIRPGFLESJBQYAO"
        //创建验证器验证发送者
        Authenticator auth =new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //验证邮箱用户和授权码
                return new PasswordAuthentication(mailFrom,password_mailFrom); //发件人的账号以及授权码 在邮箱设置内获得
            }
        };
        Session session =Session.getInstance(properties,auth);

        Message message =new MimeMessage(session);
        message.setFrom(new InternetAddress(mailFrom)); //邮件发送人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));//收件人
        if (mailBcc!=null&&mailBcc!=""){
            message.setRecipient(Message.RecipientType.BCC,new InternetAddress(mailBcc));//抄送人 2924332408@qq.com
        }
        if (mailcc!=null&&mailcc!=""){
            message.setRecipient(Message.RecipientType.CC,new InternetAddress(mailcc));//密件人 //changhr2012@163.com
        }
        //邮件标题
        message.setSubject(mailTittle);
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        // 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mailText, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);

        File attachment = new File(FileUrl);
        //File attachment = new File("");

        // 添加附件的内容
        if (attachment != null) {
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            //MimeUtility.encodeWord可以避免文件名乱码
            attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
            multipart.addBodyPart(attachmentBodyPart);
        }
        // 将multipart对象放到message中
        message.setContent(multipart);
        // 保存邮件
        message.saveChanges();
        //发送邮件
        Transport.send(message);
        return  1;
    }

}
