package com.netflixclone.controller;

import com.netflixclone.controller.model.CreateProfileInput;
import com.netflixclone.service.ProfileService;
import com.netflixclone.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {


@Autowired
ProfileService profileService;

    @PostMapping("/user/profile")
    @Secured({Roles.Customer})
    public ResponseEntity<Void> activatedNewProfile(@RequestBody CreateProfileInput createProfileInput){
    try{
        profileService.activateProfile(createProfileInput.getName(),createProfileInput.getType());

         return ResponseEntity.status(HttpStatus.OK).build();
    }
    catch(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @DeleteMapping("/user/profile/{profileId}")
    @Secured({Roles.Customer})
    public ResponseEntity<Void> deactivateProfile(@PathVariable("profileId") String profileId){

    try{
        profileService.deactivateProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    catch(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    }


}
