package org.aksw.vaadin.yasqe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;


@SpringBootApplication
@EnableVaadin
@Push
public class MainAppVaadinYasqeDemo
    extends SpringBootServletInitializer
    implements AppShellConfigurator
{
    private static final long serialVersionUID = 1L;

    public static void main(final String[] args) {
        SpringApplication.run(MainAppVaadinYasqeDemo.class, args);
    }
}

