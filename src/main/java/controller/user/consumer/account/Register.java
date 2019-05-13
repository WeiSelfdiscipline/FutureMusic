package controller.user.consumer.account;

import entity.State;
import exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.account.RegisterService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 注册和登录账号
 *
 * @author 5月11日 张易兴创建
 */
@Controller
public class Register {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);
    @Resource(name = "RegisterService")
    private RegisterService registerService;
    /**
     * 点击注册按钮执行此方法，ajax
     *
     * @param userName         接收用户名
     * @param sendMail         接收邮箱号
     * @param password         接收输入的密码
     * @param passwordAgain    接收再次输入的密码
     * @param verificationCode 接收输入的邮箱验证码
     * @param session          获取当前会话的对象
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public State register(String userName, String sendMail, String password, String passwordAgain, String verificationCode,
                          HttpSession session) throws DataBaseException {
        State state = new State();
        // 验证用户名是否合法
        if (registerService.isUserName(userName)) {
            //判断邮箱是否合法
            if (registerService.isMailbox(sendMail)) {
                // 验证密码是否合法
                if (registerService.isPassword(password)) {
                    // 判断两次密码是否相同
                    if (password.equals(passwordAgain)) {
                        // 用于从Session中取出发邮件的邮箱号
                        String mailbox = "mailbox";
                        // 对比邮箱是否相同，判断用户发完邮箱验证后是否又更改了邮箱
                        if (session.getAttribute(mailbox).equals(sendMail)) {
                            // 验证邮箱的验证码是否正确
                            if (registerService.isMailboxVerificationCode(session, verificationCode)) {
                                // 判断是否注册成功
                                if (registerService.registerAccount(userName, sendMail, password)) {
                                    state.setState(1);
                                    logger.debug("邮箱：" + sendMail + "注册成功");
                                } else {
                                    // 如果失败是数据库错误
                                    logger.debug("邮箱：" + sendMail + "注册时，数据库出错");
                                    throw new DataBaseException("邮箱：" + sendMail + "注册时，数据库出错");
                                }
                            } else {
                                logger.debug("邮箱：" + sendMail + "邮箱验证码出错");
                                state.setInformation("邮箱验证码出错");
                            }
                        } else {
                            logger.debug("邮箱：" + sendMail + "请先获取验证码");
                            state.setInformation("请先获取验证码");
                        }
                    } else {
                        logger.debug("邮箱：" + sendMail + "两次密码不相同");
                        state.setInformation("两次密码不相同");
                    }
                } else {
                    logger.debug("邮箱：" + sendMail + "输入的密码不合法");
                    state.setInformation("输入的密码不合法");
                }
            } else {
                logger.debug("邮箱：" + sendMail + "邮箱格式有误");
                state.setInformation("邮箱格式有误");
            }
        } else {
            logger.debug("用户名：" + userName + "用户名格式有误");
            state.setInformation("用户名格式有误");
        }
        return state;
    }

    /**
     * 点击发送验证码执行此方法，ajax
     *
     * @param mailbox 给该邮箱号发送验证码
     * @param session 获取当前会话的对象
     */
    @RequestMapping(value = "/sendMail")
    @ResponseBody
    public State sendMail(String mailbox, HttpSession session) {
        State state = new State();
        //发邮箱前先判断邮箱是否合法
        if (registerService.isMailbox(mailbox)) {
            //判断邮箱是否已存在
            if (registerService.isMailboxExistence(mailbox)) {
                logger.debug("邮箱：" + mailbox + "邮箱已存在");
                state.setInformation("邮箱已存在");
            } else {
                //调用发送邮箱的方法，将返回的验证码存到session中
                session.setAttribute("verificationCode", registerService.sendMail(mailbox));
                session.setAttribute("mailbox", mailbox);
                // 邮箱发送成功
                state.setState(1);
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱格式有误");
            state.setInformation("邮箱格式有误");
        }
        return state;
    }
}