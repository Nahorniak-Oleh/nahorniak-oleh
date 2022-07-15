package org.epam.nahorniak.spring.internetserviceprovider.utils;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.Role;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.utils.infocontributors.TotalUsersInfoContributor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.info.Info;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TotalUsersInfoContributorTest {

    @InjectMocks
    private TotalUsersInfoContributor infoContributor;

    @Mock
    private UserRepository userRepository;
    @Mock
    private Info.Builder builder;

    private static final Long USER_WITH_ROLE_COUNT = 2L;

    @Test
    void contributeTest(){

        Map<String, Long> userDetails = new HashMap<>();

        userDetails.put(Role.ADMIN.name(),USER_WITH_ROLE_COUNT);
        userDetails.put(Role.CUSTOMER.name(),USER_WITH_ROLE_COUNT);

        when(userRepository.countAllByRole(Role.ADMIN)).thenReturn(USER_WITH_ROLE_COUNT);
        when(userRepository.countAllByRole(Role.CUSTOMER)).thenReturn(USER_WITH_ROLE_COUNT);
        when(builder.withDetail("users",userDetails)).thenReturn(builder);

        infoContributor.contribute(builder);

        verify(builder, times(1)).withDetail("users", userDetails);
    }
}
