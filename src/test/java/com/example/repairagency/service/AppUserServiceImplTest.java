package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.dto.DepositDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import com.example.repairagency.repository.AppUserRepository;
import com.example.repairagency.service.impl.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    AppUserRepository appUserRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    
    @InjectMocks
    AppUserServiceImpl appUserService;

    @Test
    void saveNewCustomerSuccessTest() throws UserAlreadyExistAuthenticationException {
        AppUserRegistrationDto appUserRegistrationDto = AppUserRegistrationDto.builder()
                .email("oksana2021@gmail.com")
                .build();
        AppUser appUser = AppUser.builder()
                .email(appUserRegistrationDto.getEmail())
                .role(Role.CUSTOMER).build();

        Mockito.when(appUserRepository.save(any(AppUser.class))).then(returnsFirstArg());
        assertEquals(appUserService.saveNewCustomer(appUserRegistrationDto),appUser);
    }


    @Test
    public void saveNewCustomerExceptionTest() {
        AppUserRegistrationDto appUserRegistrationDto = AppUserRegistrationDto.builder()
                .email("oksana2021@gmail.com")
                .build();

        Mockito.when(appUserRepository.save(any(AppUser.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> appUserService.saveNewCustomer(appUserRegistrationDto));
    }


    @Test
    void findByIdSuccessTest() {
        AppUser appUser = AppUser.builder()
                .id(1L)
                .build();

        Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        assertThat(appUserService.findById(1L)).isEqualTo(appUser);
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(appUserRepository.findById(1L)).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, ()->appUserService.findById(1L));
    }

    @Test
    void updateDepositSuccess(){
        DepositDto depositDTO = DepositDto.builder()
                .amountOfMoney(100)
                .build();

        AppUser appUser = AppUser.builder()
                .id(1L)
                .amountOfMoney(0)
                .build();

        Mockito.when(appUserRepository.save(any(AppUser.class))).then(returnsFirstArg());
        Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        assertEquals(appUserService.updateDeposit(depositDTO,1L).getAmountOfMoney(),100);
    }

    @Test
    void saveNewMasterSuccessTest() throws UserAlreadyExistAuthenticationException {
        AppUserRegistrationDto appUserRegistrationDto = AppUserRegistrationDto.builder()
                .email("oksana2021@gmail.com")
                .build();
        AppUser appUser = AppUser.builder()
                .email(appUserRegistrationDto.getEmail())
                .role(Role.MASTER).build();

        Mockito.when(appUserRepository.save(any(AppUser.class))).then(returnsFirstArg());
        assertEquals(appUserService.saveNewMaster(appUserRegistrationDto),appUser);
    }

    @Test
    public void saveNewMasterExceptionTest() {
        AppUserRegistrationDto appUserRegistrationDto = AppUserRegistrationDto.builder()
                .email("oksana2021@gmail.com")
                .build();

        Mockito.when(appUserRepository.save(any(AppUser.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> appUserService.saveNewCustomer(appUserRegistrationDto));
    }

    @Test
    void findAllCustomersPaginatedTest() {
        AppUser appUser1 = AppUser.builder()
                .id(1L)
                .role(Role.CUSTOMER)
                .build();
        AppUser appUser2 = AppUser.builder()
                .id(2L)
                .role(Role.CUSTOMER)
                .build();

        Page<AppUser> page = new PageImpl<>(Arrays.asList(appUser1,appUser2));
        Pageable pageable = PageRequest.of(0, 2);
        Mockito.when(appUserRepository.findAllByRole(Role.CUSTOMER,pageable)).thenReturn(page);
        assertThat(appUserService.findAllCustomersPaginated(1,2)).isEqualTo(page);
    }

    @Test
    void findAllMastersPaginatedTest() {
        AppUser appUser1 = AppUser.builder()
                .id(1L)
                .role(Role.MASTER)
                .build();
        AppUser appUser2 = AppUser.builder()
                .id(2L)
                .role(Role.MASTER)
                .build();

        Page<AppUser> page = new PageImpl<>(Arrays.asList(appUser1,appUser2));
        Pageable pageable = PageRequest.of(0, 2);
        Mockito.when(appUserRepository.findAllByRole(Role.MASTER,pageable)).thenReturn(page);
        assertThat(appUserService.findAllMastersPaginated(1,2)).isEqualTo(page);
    }

    @Test
    void findAllMastersTest() {
        AppUser appUser1 = AppUser.builder()
                .id(1L)
                .role(Role.MASTER)
                .build();
        AppUser appUser2 = AppUser.builder()
                .id(2L)
                .role(Role.MASTER)
                .build();

        List<AppUser> list = Arrays.asList(appUser1,appUser2);
        Mockito.when(appUserRepository.findAllByRole(Role.MASTER)).thenReturn(list);
        assertThat(appUserService.findAllMasters()).isEqualTo(list);
    }

    @Test
    void loadUserByUsernameSuccessTest() {
        AppUser appUser = AppUser.builder()
                .email("oksana2021@gmail.com")
                .build();

        Mockito.when(appUserRepository.findByEmail("oksana2021@gmail.com")).thenReturn(Optional.ofNullable(appUser));
        assertThat(appUserService.loadUserByUsername("oksana2021@gmail.com")).isEqualTo(appUser);
    }

    @Test
    void loadUserByUsernameExceptionTest() {
        Mockito.when(appUserRepository.findByEmail("oksana2021@gmail.com")).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class,()->appUserService.loadUserByUsername("oksana2021@gmail.com"));
    }
}

