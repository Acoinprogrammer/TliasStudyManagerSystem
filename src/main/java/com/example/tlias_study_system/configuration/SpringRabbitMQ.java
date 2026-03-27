package com.example.tlias_study_system.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringRabbitMQ {
    @Bean
    public Queue TestMQ(){
        return new Queue("TestMQ");
    }
    @Bean
    public TopicExchange TestMQTopic(){
        return new TopicExchange("TestMQ.topic");
    }
    @Bean
    public Binding TestMQBinding(Queue TestMQ,TopicExchange TestMQTopic){
        return BindingBuilder.bind(TestMQ).to(TestMQTopic).with("TestMQKey");
    }
    @Bean
    public Queue EmpMQ(){return new Queue("EmpMQ");}
    @Bean
    public Queue StudentMQ(){return new Queue("StudentMQ");}

    @Bean
    public TopicExchange ViewImageTopic(){return new TopicExchange("ViewImage.topic");}

    @Bean
    public Binding EmpMQBinding(Queue EmpMQ,TopicExchange ViewImageTopic){return BindingBuilder.bind(EmpMQ).to(ViewImageTopic).with("EmpKey");}
    @Bean
    public Binding StudentMQBinding(Queue StudentMQ,TopicExchange ViewImageTopic){return BindingBuilder.bind(StudentMQ).to(ViewImageTopic).with("StudentKey");}
}
