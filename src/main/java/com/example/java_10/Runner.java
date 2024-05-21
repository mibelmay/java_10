package com.example.java_10;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Sending message...");
            rabbitTemplate.convertAndSend(Java10Application.exchangeName, "foo.bar.baz", scanner.nextLine());
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }
    }
}
