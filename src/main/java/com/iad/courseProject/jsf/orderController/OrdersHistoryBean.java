package com.iad.courseProject.jsf.orderController;

import com.iad.courseProject.authentication.TokenController;
import com.iad.courseProject.data.entities.Order;
import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.types.OrderStatus;
import com.iad.courseProject.data.services.OrderService;
import com.iad.courseProject.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
@ViewScoped
public class OrdersHistoryBean {
    private final UserService userService;
    private final TokenController tokenController;
    private final OrderService orderService;

    private Order selectedOrder;

    public Order getSelectedOrder() {
        return selectedOrder;
    }
    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    @Autowired
    public OrdersHistoryBean(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
        tokenController = new TokenController();
    }

    public List<Order> getUserOrders() {
        String token = tokenController.resolveToken((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()));
        User user = userService.getById(tokenController.getUserId(token));
        return user.getOrders();
    }
    public String cancelOrder() {
        if (selectedOrder != null && selectedOrder.getStatus() == OrderStatus.STARTED) {
            orderService.delete(selectedOrder);
            selectedOrder = null;
            return "canceled";
        }
        return "cancelError";
    }

}
