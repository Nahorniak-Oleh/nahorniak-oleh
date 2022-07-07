package org.epam.nahorniak.spring.internetserviceprovider.infocontributors;

import lombok.RequiredArgsConstructor;
import org.epam.nahorniak.spring.internetserviceprovider.model.Role;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TotalUsersInfoContributor implements InfoContributor {

    private final UserRepository userRepository;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Long> userDetails = new HashMap<>();

        Arrays.stream(Role.values())
                .forEach(role ->
                        userDetails.put(role.toString(), userRepository.countByRole(role)));
        
        builder.withDetail("users",userDetails);
    }
}
