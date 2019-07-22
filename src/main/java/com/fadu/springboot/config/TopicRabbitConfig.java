package com.fadu.springboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableConfigurationProperties(RabbitMQConstant.class)
public class TopicRabbitConfig {

    @Autowired
    private RabbitMQConstant rabbitMQConstant;

    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.evn.connectionFactory.host}")
    private String host;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.evn.connectionFactory.port}")
    private int port;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.evn.connectionFactory.username}")
    private String username;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.evn.connectionFactory.password}")
    private String password;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.evn.connectionFactory.virtualHost}")
    private String virtualHost;

    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    /**
     * 定义rabbitmq工厂bean
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     * RabbitTemplate:用来发送消息
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(new Jackson2JsonMessageConverter());// 发送端类型转化pojo,需要序列化
        return template;
    }

    //-----------指定交换机[开始]----------------------
    @Bean
    public DirectExchange smsExchange() {
        return new DirectExchange(rabbitMQConstant.getSmsExchangeName());
    }

    @Bean
    public DirectExchange smsDelayedExchange() {
        return new DirectExchange(rabbitMQConstant.getSmsDelayedExchangeName());
    }

    @Bean
    public DirectExchange monitorScheduleExchange() {
        return new DirectExchange("monitorScheduleDirectExchange");
    }

    @Bean
    public DirectExchange monitorDelayedScheduleExchange() {
        return new DirectExchange("monitorDelayedScheduleExchange");
    }

    @Bean
    public DirectExchange lawyerCooperationMessageExchange() {
        return new DirectExchange(rabbitMQConstant.getLawyerCooperationMessageExchangeName());
    }

    @Bean
    public DirectExchange delayedLawyerCooperationMessageExchange() {
        return new DirectExchange(rabbitMQConstant.getDelayedLawyerCooperationMessageExchangeName());
    }
    //-----------指定交换机[结束]----------------------

    //-----------指定Queue[开始]Queue:构建队列，名称，是否持久化之类，默认sms短信服务----------------------
    @Bean
    public Queue smsImmediateQueue() {
        return new Queue(rabbitMQConstant.getSmsImmediateQueue(), true);
    }

    @Bean
    public Queue deadSMSLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        //参考：{@link https://blog.csdn.net/qq_41143507/article/details/80132915}
        arguments.put("x-dead-letter-exchange", rabbitMQConstant.getSmsDelayedExchangeName());// 死信：DLX,dead
        arguments.put("x-dead-letter-routing-key", rabbitMQConstant.getSmsDelayedAfterRepeatQueue());
        Queue queue = new Queue(rabbitMQConstant.getSmsDelayedQueue(), true, false, false, arguments);
        return queue;
    }

    @Bean
    public Queue repeatSMSLetterQueue() {
        return new Queue(rabbitMQConstant.getSmsDelayedAfterRepeatQueue(), true, false, false);
    }

    @Bean
    public Queue monitorScheduleQueue() {
        return new Queue("monitorScheduleQueue", true);
    }

    @Bean
    public Queue deadMonitorScheduleQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "monitorDelayedScheduleExchange");// 死信：DLX,dead
        arguments.put("x-dead-letter-routing-key", "repeatMonitorScheduleQueue");
        Queue queue = new Queue("deadMonitorScheduleQueue", true, false, false, arguments);
        return queue;
    }

    @Bean
    public Queue repeatMonitorScheduleQueue() {
        return new Queue("repeatMonitorScheduleQueue", true, false, false);
    }

    @Bean
    public Queue appMessageQueue() {
        return new Queue(rabbitMQConstant.getAppMessageQueue(), true, false, false);
    }

    @Bean
    public Queue delayedAppMessageQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", rabbitMQConstant.getDelayedLawyerCooperationMessageExchangeName());// 死信：DLX,dead
        arguments.put("x-dead-letter-routing-key", rabbitMQConstant.getDelayedAppMessageRepeatKey());
        return new Queue(rabbitMQConstant.getDelayedAppMessageQueue(), true, false, false, arguments);
    }


    @Bean
    public Queue delayedAppMessageRepeatQueue(){
        return new Queue(rabbitMQConstant.getDelayedAppMessageRepeatQueue(), true, false, false);
    }
    //-----------指定Queue[结束]----------------------

    //-----------指定Binding[开始]----------------------
    @Bean
    public Binding smsImmediateBinding() {
        return BindingBuilder.bind(smsImmediateQueue()).to(smsExchange()).with(rabbitMQConstant.getSmsRootingKey());
    }

    @Bean
    public Binding deadSMSLetterBind() {
        return BindingBuilder.bind(deadSMSLetterQueue()).to(smsDelayedExchange()).with(rabbitMQConstant.getSmsDelayedQueue());
    }

    @Bean
    public Binding repeatSMSLetterBind() {
        return BindingBuilder.bind(repeatSMSLetterQueue()).to(smsDelayedExchange()).with(rabbitMQConstant.getSmsDelayedAfterRepeatQueue());
    }

    @Bean
    public Binding deadMonitorScheduleBind() {
        return BindingBuilder.bind(deadMonitorScheduleQueue()).to(monitorDelayedScheduleExchange()).with("deadMonitorScheduleQueue");
    }

    @Bean
    public Binding repeatMonitorScheduleBind() {
        return BindingBuilder.bind(repeatMonitorScheduleQueue()).to(monitorDelayedScheduleExchange()).with("repeatMonitorScheduleQueue");
    }

    @Bean
    public Binding monitorScheduleBind() {
        return BindingBuilder.bind(monitorScheduleQueue()).to(monitorScheduleExchange()).with("monitorScheduleQueue");
    }

    @Bean
    public Binding appMessageBind() {
        return BindingBuilder.bind(appMessageQueue()).to(lawyerCooperationMessageExchange()).with(rabbitMQConstant.getAppMessageKey());
    }

    @Bean
    public Binding delayedAppMessageBind() {
        return BindingBuilder.bind(delayedAppMessageQueue()).to(delayedLawyerCooperationMessageExchange()).with(rabbitMQConstant.getDelayedAppMessageKey());
    }

    @Bean
    public Binding delayedAppMessageRepeatBind() {
        return BindingBuilder.bind(delayedAppMessageRepeatQueue()).to(delayedLawyerCooperationMessageExchange()).with(rabbitMQConstant.getDelayedAppMessageRepeatKey());
    }

    //-----------指定Binding[结束]----------------------

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(0);//这个参数设置，接收消息端，接收的最大消息数量（包括使用get、consume）,一旦到达这个数量，客户端不在接收消息。0为不限制。默认值为3.
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);// 确认模式：自动，手动确认消息已经收到
        factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
        return factory;
    }

}
