package net.big2.webrunner.demo.web;

import net.big2.webrunner.core.common.WrmConfiguration;
import net.big2.webrunner.demo.jpa.model.Person;
import net.big2.webrunner.demo.jpa.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class Demo {
    @Autowired
    ApplicationContext context;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("list")
    Model ok(Model model) {
        Map<String, WrmConfiguration> configMap = context.getBeansOfType(WrmConfiguration.class);
        model.addAttribute(configMap);

        model.addAttribute("personList", personRepository.findAll());

        return model;
    }

    @RequestMapping("add")
    @ResponseBody
    String add() {
        Person person = new Person();
        person.setName("bab " + System.currentTimeMillis());

        personRepository.save(person);

        return "Added";
    }

}
