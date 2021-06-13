package com.springbootawss3.awsimageupload.profile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


//POJO class for user profile

@Entity
@Table
public class UserProfile {

    @Id
    private UUID Id;

    private String name;

    public UserProfile() {
    }

    //s3 key
    private String link;



    public UserProfile(UUID Id, String name, String profileImageLink) {
        this.Id = Id;
        this.name = name;
        this.link = profileImageLink;
    }


    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getLink() {
        return Optional.ofNullable(link);
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(Id, that.Id) && Objects.equals(name, that.name) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, link);
    }
}
