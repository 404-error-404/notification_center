package general.notification_center.controller;

import general.notification_center.config.LoginExcept;
import general.notification_center.utils.HttpResult;
import general.notification_center.utils.service.MailService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 小乐乐
 * @date 2021/1/10 16:07
 */
@RestController
public class TestMailerSend {
    @Resource
    private MailService mailService;

    @LoginExcept
    @GetMapping(value = "email/test")
    public HttpResult send(String email) {
        if (null == email) {
            return HttpResult.error(400, "邮箱不能为空");
        }
        int symbolIndex = email.indexOf('@');
        int dotIndex = email.indexOf('.');
        if (symbolIndex > 0 && dotIndex > symbolIndex + 1 && dotIndex < email.length()-1) {
            String context = "<div style=\"margin: 0 auto; padding: 40px; text-align: center; height: 100%; overflow: hidden; box-sizing: border-box; max-width: 800px\"> <h2 style=\"font-size: 2em\"> 邮件测试 </h2> <div style=\"font-size: 1.2em;\"> 这是一封html测试邮件，整个项目的代码在 <a style=\"color: #177EE6\" href=\"https://github.com/404-error-404/notification_center\"> Github </a> </div> <div style=\"width: 100%; margin: 0 auto;\"> <img style=\"width: 100%; height: auto\" src=\"https://smartpublic-10032816.file.myqcloud.com/custom/20211202202931/22735/20211202202931/--08ef28251a40e164b98dc57caf85c83c.png\" alt=\"装饰图\"/> </div> </div>";
            mailService.sendHtmlMail("测试邮件", context, new String[]{email});
            return HttpResult.ok("发送邮件成功，请前往邮箱查看");
        }
        else {
            return HttpResult.error(400, "邮箱格式不合法");
        }

    }
}
