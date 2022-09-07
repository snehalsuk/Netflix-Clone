package com.netflixclone.validator;

import com.netflixclone.accessor.ProfileAccessor;
import com.netflixclone.accessor.VideoAccessor;
import com.netflixclone.accessor.model.ProfileDTO;
import com.netflixclone.accessor.model.VideoDTO;
import com.netflixclone.exception.InvalidProfileException;
import com.netflixclone.exception.InvalidVideoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

@Autowired
    private ProfileAccessor profileAccessor;

@Autowired
private VideoAccessor videoAccessor;


public void validateProfile(final String profileId,final String userId){

    ProfileDTO profileDTO=profileAccessor.getProfileByProfileId(profileId);
    if(profileDTO==null || !profileDTO.getUserId().equals(userId)){
        throw new InvalidProfileException("Profile "+profileId+"is invalid or doesnt exist");
    }
}
public void validateVideoId(final String videoId){
    VideoDTO videoDTO=videoAccessor.getVideoByVideoId(videoId);
    if(videoDTO==null){
        throw new InvalidVideoException("video with videoId"+videoId+"does not exist");

    }
}


}
