package com.springbootawss3.awsimageupload.bucket;

//Enum which takes bucket name , we can use this enum to get bucket name in our code
public enum BucketName {
    PROFILE_IMAGE("spring-boot-image-upload-bucket");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
