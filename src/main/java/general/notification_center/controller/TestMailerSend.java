package general.notification_center.controller;

import general.notification_center.utils.service.MailService;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "/send")
    public String send() {
        mailService.sendSimpleTextMailActual("发送主题", "发送内容", new String[]{"1791781644@qq.com"}, null, null, null);
        return "1";
    }
}
