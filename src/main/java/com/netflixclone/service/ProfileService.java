package com.netflixclone.service;

import com.netflixclone.accessor.ProfileAccessor;
import com.netflixclone.accessor.model.ProfileType;
import com.netflixclone.accessor.model.UserDTO;
import com.netflixclone.controller.model.ProfileTypeInput;
import com.netflixclone.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {

    @Autowired
    private ProfileAccessor profileAccessor;


    public void activateProfile(final String name,final ProfileTypeInput type){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        if(name.length()<5 || name.length()>20){
            throw new InvalidDataException("Name length should be between 5 to 20");

        }
        profileAccessor.addNewProfile(userDTO.getUserId(), name, ProfileType.valueOf(type.name()));
    }

    public void deactivateProfile(final String profileId){
        profileAccessor.deleteProfile(profileId);
    }
}
