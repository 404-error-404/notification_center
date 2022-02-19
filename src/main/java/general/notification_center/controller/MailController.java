package general.notification_center.controller;

import general.notification_center.utils.HttpResult;
import general.notification_center.utils.service.MailService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发邮件相关操作
 * @author 小乐乐
 * @date 2022/2/19 20:29
 */
@RestController
@RequestMapping("/email")
public class MailController {
    @Resource
    private MailService mailService;

    /**
     * 发送简单的纯文本邮件
     * @param receiver  收件人
     * @param subject   邮件标题
     * @param context   邮件内容
     * @return  发送邮件结果
     */
    @PostMapping(value = "/text")
    public HttpResult sendTextEmail(@NonNull String[] receiver, @NonNull String subject, @NonNull String context) {
        mailService.sendSimpleTextMailActual(subject, context, receiver, null, null, null);
        return HttpResult.ok("发送邮件成功");
    }

    /**
     * 发送html邮件
     * @param receiver  收件人
     * @param subject   邮件标题
     * @param context   邮件内容
     * @return  发送邮件结果
     */
    @PostMapping(value = "/html")
    public HttpResult sendHtmlEmail(@NonNull String[] receiver, @NonNull String subject, @NonNull String context) {
        mailService.sendHtmlMail(subject, context, receiver);
        return HttpResult.ok("发送邮件成功");
    }
}
