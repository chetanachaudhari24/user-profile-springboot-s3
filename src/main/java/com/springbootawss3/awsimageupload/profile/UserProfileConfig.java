package com.springbootawss3.awsimageupload.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserProfileConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileConfig.class);

    @Autowired
    UserProfileDataAccessService userProfileDataAccessService;

    @Override
    public void run(String... args) throws Exception {
        UserProfile JackJones = new UserProfile(UUID.fromString("d391d96e-5091-441d-b284-155836f7933e"), "Jack Jones", null);
        UserProfile EleneHolt = new UserProfile(UUID.fromString("80da1081-adbd-4cb3-ba09-ceb034d0e93f"), "Elena Holt", null);

        userProfileDataAccessService.saveAll(List.of(JackJones, EleneHolt));

        userProfileDataAccessService.findAll().forEach((userProfile) -> {
            logger.info("{}", userProfile);
        });
    }
}
