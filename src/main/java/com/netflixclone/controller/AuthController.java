package com.netflixclone.controller;

import com.netflixclone.controller.model.LoginInput;
import com.netflixclone.exception.InvalidCredentialException;
import com.netflixclone.service.AuthService;
import com.netflixclone.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInput loginInput){

        String email= loginInput.getEmail();
        String password = loginInput.getPassword();
        System.out.println("email ="+email+"password ="+password);
        try{
            String token= authService.login(email,password);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        catch(InvalidCredentialException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @PostMapping("/logoutCurrentUser")
    @Secured({Roles.User,Roles.Customer})
    public void logout(@RequestHeader(name="Authorization", required=false) String authorizationHeader){
        String token=authorizationHeader.replace("Bearer"," ");
        authService.logout(token);
    }


}
