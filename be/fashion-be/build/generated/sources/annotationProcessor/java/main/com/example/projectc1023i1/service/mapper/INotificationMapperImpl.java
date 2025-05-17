package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.notification_maptruck.NotificationMaptruck;
import com.example.projectc1023i1.Dto.get_data.notification_maptruck.OrderMaptruck;
import com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderDetailMaptruck;
import com.example.projectc1023i1.model.Notifcation;
import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.OrderDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T02:26:41+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class INotificationMapperImpl implements INotificationMapper {

    @Override
    public NotificationMaptruck toNotificationMaptruck(Notifcation notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationMaptruck.NotificationMaptruckBuilder notificationMaptruck = NotificationMaptruck.builder();

        notificationMaptruck.notificationId( notification.getNotificationId() );
        notificationMaptruck.order( orderToOrderMaptruck( notification.getOrder() ) );
        notificationMaptruck.deal( notification.getDeal() );
        notificationMaptruck.message( notification.getMessage() );
        notificationMaptruck.createAt( notification.getCreateAt() );
        notificationMaptruck.user( notification.getUser() );
        notificationMaptruck.coupon( notification.getCoupon() );

        return notificationMaptruck.build();
    }

    @Override
    public OrderMaptruck toOrderMaptruck(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderMaptruck.OrderMaptruckBuilder orderMaptruck = OrderMaptruck.builder();

        orderMaptruck.orderId( (int) order.getOrderId() );
        orderMaptruck.orderCode( order.getOrderCode() );
        orderMaptruck.status( order.getStatus() );
        orderMaptruck.paymentType( order.getPaymentType() );
        orderMaptruck.total( order.getTotal() );
        orderMaptruck.address( order.getAddress() );
        orderMaptruck.orderDate( order.getOrderDate() );
        orderMaptruck.note( order.getNote() );

        return orderMaptruck.build();
    }

    protected OrderDetailMaptruck orderDetailsToOrderDetailMaptruck(OrderDetails orderDetails) {
        if ( orderDetails == null ) {
            return null;
        }

        OrderDetailMaptruck.OrderDetailMaptruckBuilder orderDetailMaptruck = OrderDetailMaptruck.builder();

        orderDetailMaptruck.orderDetailsId( orderDetails.getOrderDetailsId() );
        orderDetailMaptruck.quality( orderDetails.getQuality() );
        orderDetailMaptruck.price( orderDetails.getPrice() );
        orderDetailMaptruck.productVariant( orderDetails.getProductVariant() );

        return orderDetailMaptruck.build();
    }

    protected List<OrderDetailMaptruck> orderDetailsListToOrderDetailMaptruckList(List<OrderDetails> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetailMaptruck> list1 = new ArrayList<OrderDetailMaptruck>( list.size() );
        for ( OrderDetails orderDetails : list ) {
            list1.add( orderDetailsToOrderDetailMaptruck( orderDetails ) );
        }

        return list1;
    }

    protected com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck orderToOrderMaptruck(Order order) {
        if ( order == null ) {
            return null;
        }

        com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck.OrderMaptruckBuilder orderMaptruck = com.example.projectc1023i1.Dto.get_data.order_maptruck.OrderMaptruck.builder();

        orderMaptruck.orderId( order.getOrderId() );
        orderMaptruck.orderCode( order.getOrderCode() );
        orderMaptruck.status( order.getStatus() );
        orderMaptruck.paymentType( order.getPaymentType() );
        orderMaptruck.total( order.getTotal() );
        orderMaptruck.orderDate( order.getOrderDate() );
        orderMaptruck.note( order.getNote() );
        orderMaptruck.users( order.getUsers() );
        orderMaptruck.address( order.getAddress() );
        orderMaptruck.orderDetailsList( orderDetailsListToOrderDetailMaptruckList( order.getOrderDetailsList() ) );

        return orderMaptruck.build();
    }
}
