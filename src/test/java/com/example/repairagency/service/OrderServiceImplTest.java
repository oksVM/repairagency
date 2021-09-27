package com.example.repairagency.service;

import com.example.repairagency.dto.PriceDto;
import com.example.repairagency.model.*;
import com.example.repairagency.repository.OrderRepository;
import com.example.repairagency.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    AppUserService appUserService;

    @InjectMocks
    OrderServiceImpl orderService;


    @Test
    void findOrderByIdSuccessTest() {
        Order order = Order.builder()
                .id(1L)
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertThat(orderService.findOrderById(1L)).isEqualTo(order);
    }

    @Test
    void findOrderByIdExceptionTest() {
        Mockito.when(orderRepository.findById(1L)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, ()->orderService.findOrderById(1L));
    }

    @Test
    void setPriceSuccessTest() {
        PriceDto priceDto = PriceDto.builder()
                .amountOfMoney(100)
                .build();
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.WAIT_FOR_ADMIN_CONFIRMATION)
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertThat(orderService.setPrice(priceDto, 1L).getPrice()).isEqualTo(100);
        assertThat(orderService.setPrice(priceDto, 1L).getOrderStatus()).isEqualTo(OrderStatus.WAIT_FOR_PAYMENT);

    }

    @Test
    void setMasterSuccessTest() {
        AppUser appUser = AppUser.builder()
                .id(1L)
                .role(Role.MASTER)
                .build();
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.WAIT_FOR_ADMIN_CONFIRMATION)
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(appUserService.findById(1L)).thenReturn(appUser);
        assertThat(orderService.setMaster(appUser.getId(),1L).getMaster()).isEqualTo(appUser);
        assertThat(orderService.setMaster(appUser.getId(),1L).getOrderStatus()).isEqualTo(OrderStatus.WAIT_FOR_MASTER_CONFIRMATION);
    }


    @Test
    void takeInWorkSuccessTest() {
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.WAIT_FOR_MASTER_CONFIRMATION)
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertThat(orderService.takeInWork(1L).getOrderStatus()).isEqualTo(OrderStatus.IN_WORK);
    }

    @Test
    void markAsDoneSuccessTest() {
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.IN_WORK)
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertThat(orderService.markAsDone(1L).getOrderStatus()).isEqualTo(OrderStatus.DONE);
    }
}



/*


    @Test
    public void decreaseMoneyByIdSuccess() throws NotEnoughMoneyException, NotFoundException {
        Account account = Account.builder()
                .id(1L)
                .money(300L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThat(accountService.decreaseMoneyById(300L,1L).getMoney()).isEqualTo(0L);
    }

    @Test
    public void decreaseMoneyByIdNotEnoughMoneyException(){
        Account account = Account.builder()
                .id(1L)
                .money(200L)
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(NotEnoughMoneyException.class, () -> accountService.decreaseMoneyById(300L,1L));
    }

    @Test
    public void decreaseMoneyByIdNotFoundException(){
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.decreaseMoneyById(100L,1L));
    }


*/
