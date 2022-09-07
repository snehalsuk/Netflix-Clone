package com.netflixclone.controller;

import com.netflixclone.controller.model.CreateUserInput;
import com.netflixclone.controller.model.VerifyEmailInput;
import com.netflixclone.controller.model.VerifyPhoneNoInput;
import com.netflixclone.exception.InvalidDataException;
import com.netflixclone.security.Roles;
import com.netflixclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public ResponseEntity<String> addNewUser(@RequestBody CreateUserInput createUserInput) {
        String name = createUserInput.getName();
        String email = createUserInput.getEmail();
        String password = createUserInput.getPassword();
        String phoneNo = createUserInput.getPhoneNo();
        try {
            userService.addNewUser(email, name, password, phoneNo);
            return ResponseEntity.status(HttpStatus.OK).body("user created Successfully");
        } catch (InvalidDataException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/user/subscription")
    @Secured({Roles.User})
    public String activateSubscription() {
        userService.activationSubscription();
        return "Subscription activated Successfully";
    }


    @DeleteMapping("/user/subscription")
    @Secured({Roles.Customer})
    public String deleteSubscription() {
        userService.deleteSubscription();
        return "Subscription deleted Successfully";
    }

    @PostMapping("/user/email")
    @Secured({Roles.User, Roles.Customer})
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyEmailInput emailInput) {
        try {
            userService.verifyEmail(emailInput.getOtp());
            return ResponseEntity.status(HttpStatus.OK).body("otp verified successfully");
        } catch (InvalidDataException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @PostMapping("/user/phoneNo")
    @Secured({Roles.User, Roles.Customer})
    public ResponseEntity<String> verifyPhoneNo(@RequestBody VerifyPhoneNoInput phoneNoInput) {
        try {
            userService.verifyPhone((phoneNoInput.getOtp()));
            return ResponseEntity.status(HttpStatus.OK).body("otp verified successfully");
        } catch (InvalidDataException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

//    @GetMapping("/user/email")
//    @Secured({Roles.User, Roles.Customer})
//    public ResponseEntity<String> getEmailOtp() {
//        try {
//
//            userService.sendEmailOtp();
//            return ResponseEntity.status(HttpStatus.OK).body("OTP sent successfully");
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
}