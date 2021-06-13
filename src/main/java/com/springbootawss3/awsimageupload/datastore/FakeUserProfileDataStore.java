package com.springbootawss3.awsimageupload.datastore;

import com.springbootawss3.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("3e60e367-6a83-48cc-8754-44a2c3f751fc"), "Jack Jones", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("cfebba1e-af55-4bc1-a07d-82a4eac46999"), "Elena Holt", null));

    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
