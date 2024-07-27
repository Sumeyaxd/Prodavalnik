package com.example.demo.service.scheduler;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Order;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SchedulerCronTest {
    private SchedulerCron schedulerCron;

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private CommentRepository mockCommentRepository;

    @Captor
    private ArgumentCaptor<List<Long>> captor;

    @BeforeEach
    public void setUp() {
        this.schedulerCron = new SchedulerCron(mockOrderRepository, mockCommentRepository);
    }

    @Test
    public void testDeleteOrdersDeliveredOnBeforeMoreThanOneMonth() {
        //todo
    }

    @Test
    public void testDeleteCommentsAddedOnBeforeMoreThanOneYear() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        List<Comment> comments = Stream.of(
                createComment(1L, oneYearAgo.minusDays(1)),
                createComment(2L, oneYearAgo.minusDays(10)),
                createComment(3L, LocalDateTime.now().minusMonths(11))
        ).collect(Collectors.toList());

        when(mockCommentRepository.findAll()).thenReturn(comments);

        schedulerCron.deleteCommentsAddedOnBeforeMoreThanOneYear();

        verify(mockCommentRepository).deleteAllById(captor.capture());

        List<Long> idsToDelete = captor.getValue();
        assert idsToDelete.contains(1L);
        assert idsToDelete.contains(2L);
        assert !idsToDelete.contains(3L);
    }

    private Order createOrder(Long id, LocalDateTime deliveredOn) {
        Order order = new Order();
        order.setId(id);
        order.setDeliveredOn(deliveredOn);
        return order;
    }

    private Comment createComment(Long id, LocalDateTime addedOn) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setAddedOn(addedOn);
        return comment;
    }
}
