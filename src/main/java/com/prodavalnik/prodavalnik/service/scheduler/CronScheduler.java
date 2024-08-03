package com.prodavalnik.prodavalnik.service.scheduler;

import com.prodavalnik.prodavalnik.model.entity.BaseEntity;
import com.prodavalnik.prodavalnik.model.entity.Comment;
import com.prodavalnik.prodavalnik.model.entity.Order;
import com.prodavalnik.prodavalnik.repository.CommentRepository;
import com.prodavalnik.prodavalnik.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CronScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(CronScheduler.class);
    private final OrderRepository orderRepository;

    public CronScheduler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "0 0 1 * *")
    public void deleteOrdersDeliveredOnBeforeMoreThanMonth() {

        List<Order> orders = this.orderRepository.findAll();

        LocalDateTime scheduledDate = LocalDateTime.now().minusMonths(1L);

        List<Long> ids = orders.stream()
                .filter(order -> {

                    LocalDateTime deliveredOn = order.getDeliveredOn();

                    if (deliveredOn != null) {
                        return deliveredOn.isBefore(scheduledDate);
                    }

                    return false;
                })
                .map(BaseEntity::getId).toList();

        this.orderRepository.deleteAllById(ids);

        LOGGER.info("Deleted orders delivered on before more than 1 month!");
    }

}
