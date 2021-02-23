package com.lijia.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Configuration
@ComponentScan(basePackages = {"com.lijia.code"})
class SpringTestContext {

    @Bean(name="env1")
    public Env env1(){
        return new Env(false);
    }

    @Bean(name = "notebookPC")
    @ConditionalOnExpression("@env1.isLocal()")
    public Computer computer1(){
        return new Computer("笔记本电脑");
    }

    @ConditionalOnMissingBean(com.lijia.code.Computer.class)
    @Bean("reservePC")
    public Computer computer2(){
        return new Computer("备用电脑");
    }
}

interface Person{
    String say();
}

@Component
@ConditionalOnLocalRun
class Chinese implements  Person{

    @Override
    public String say() {
        return "I am Chinese";
    }
}

@Component
@ConditionalOnMissingBean(Chinese.class)
class English implements  Person{

    @Override
    public String say() {
        return "I am English";
    }
}
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class Computer{
    private String name;
}

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class Env{
    private boolean isLocal;
}

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestContext.class)
public class SpringConditionalTest {
    @Autowired
    Computer computer;

    @Autowired
    Person person;

    @Test
    public void test(){
        System.out.println(computer.getName());
    }

    @Test
    public void testPerson(){
        System.out.println(person.say());
    }
}
