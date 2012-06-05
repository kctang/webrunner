package net.big2.webrunner.demo.web;

import model.demo.jpa.User;
import net.big2.webrunner.core.common.WrmConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import repository.demo.jpa.user.UserRepository;

import java.util.Map;

@Controller
public class Demo {
    @Autowired
    ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")
    Model ok(Model model) {
        Map<String, WrmConfiguration> configMap = context.getBeansOfType(WrmConfiguration.class);
        model.addAttribute(configMap);

        model.addAttribute("userList", userRepository.findAll());

        return model;
    }

    @RequestMapping("add")
    @ResponseBody
    String add() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("secret");
        userRepository.save(user);

        return "Added";
    }

}
