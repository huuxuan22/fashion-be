package com.example.projectc1023i1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS().setStreamBytesLimit(512 * 1024)  //  tăng giới hạn bytes cho stream
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(30_000);;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // topic => commnent, queue => chat rieng
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(512 * 1024);       // 🧠 kích thước tối đa 512KB
        registry.setSendBufferSizeLimit(1024 * 1024);   // 🧠 buffer tối đa 1MB
        registry.setSendTimeLimit(20 * 1000);          // 🕒 thời gian gửi tối đa 20s
    }

}
