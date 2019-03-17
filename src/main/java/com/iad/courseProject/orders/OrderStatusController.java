package com.iad.courseProject.orders;

import com.iad.courseProject.MessageController;
import com.iad.courseProject.data.entities.Order;
import com.iad.courseProject.data.entities.types.OrderStatus;
import com.iad.courseProject.data.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

@Named
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class OrderStatusController {
    private final OrderService orderService;
    private final MessageController messageController;

    private final static String TRACK_URL = "http://localhost:9090/track";

    @Autowired
    public OrderStatusController(OrderService orderService, MessageController messageController) {
        this.orderService = orderService;
        this.messageController = messageController;
    }

    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        if (order.getUser().isReceivingNotifications()) {
            sendNotification(order);
        }
    }
    private void sendNotification(Order order) {
        try {
            MimeMessage msg = messageController.getBaseMessage();
            msg.setText(String.format("Status of order №%s has changed to %s. You can track order by following link: %s?orderId=%s", order.getOrderLinkId(), order.getStatus().toString(), TRACK_URL, order.getOrderLinkId()));
            msg.setRecipients(Message.RecipientType.TO, order.getUser().getEmail());
            msg.setSubject("Notification");
            Transport.send(msg);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
