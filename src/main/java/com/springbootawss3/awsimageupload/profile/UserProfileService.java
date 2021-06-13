package com.springbootawss3.awsimageupload.profile;


import com.springbootawss3.awsimageupload.bucket.BucketName;
import com.springbootawss3.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }


    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.findAll();
    }

    @Transactional
    public void uploadProfileImage(UUID userProfileId, MultipartFile file) {

        //check if file is empty
        isFileEmpty(file);

        //check if upload file is image
        isFileImage(file);

        //check if user is present in database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        //generate metadata for s3 object
        Map<String, String> metadata = extractMetadata(file);

        //generate path and filename for file to be uploaded to s3 bucket
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());


        try {

            //save file to S3 bucket
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());

            //update database with s3 image link

            user.setLink(filename);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                .findAll()
                .stream()
                .filter(userProfile -> userProfile.getId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    private void isFileImage(MultipartFile file) {
        if(Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_GIF).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image" + file.getContentType());
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw  new IllegalStateException("cannot upload empty file [" + file.getSize() + "]");
        }
    }

    @Transactional
    public byte[] downloadUserProfileImage(UUID userProfileId) {
        //check if user exists
        UserProfile user = getUserProfileOrThrow(userProfileId);

        String path = String.format("%s/%s",
                                        BucketName.PROFILE_IMAGE.getBucketName(),
                                        user.getId());

        return user.getLink()
                .map(link -> fileStore.download(path, link))
                .orElse(new byte[0]);
    }
}
