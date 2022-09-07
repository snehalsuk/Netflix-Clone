package com.netflixclone.service;

import com.netflixclone.accessor.EmailAccessor;
import com.netflixclone.accessor.OtpAccessor;
import com.netflixclone.accessor.UserAccessor;
import com.netflixclone.accessor.model.*;
import com.netflixclone.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserService {

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private OtpAccessor otpAccessor;

    @Autowired
    EmailAccessor emailAccessor;

    public void addNewUser(final String email, final String name, final String password, final String phoneNo) {

        if (phoneNo.length() != 10) {
            throw new InvalidDataException("phone no" + phoneNo + "is in valid");
        }
        if (password.length() < 6) {
            throw new InvalidDataException("Password is too simple");
        }
        if (name.length() < 5) {
            throw new InvalidDataException("Name is not correct");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +
                                "(?:[a-zA-Z0-9-]+\\.)+ [a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
          if(!pat.matcher(email).matches()){
            throw new InvalidDataException("Email is not correct");
        }
        UserDTO userDTO=userAccessor.getUserByEmail(email);
        if(userDTO!=null){
            throw new InvalidDataException("User with given email already exists");
        }
        userDTO=userAccessor.getUserByPhoneNo(phoneNo);
        if(userDTO!=null){
            throw new InvalidDataException("User with given phoneNo already exists");
        }
        userAccessor.addNewUser(email, name, password, phoneNo, UserState.ACTIVE, UserRole.ROLE_USER);
    }

    public void activationSubscription(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDTO=(UserDTO) authentication.getPrincipal();
        userAccessor.updateRole(userDTO.getUserId(),UserRole.ROLE_CUSTOMER);
    }
    public void deleteSubscription(){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDTO=(UserDTO) authentication.getPrincipal();
        userAccessor.updateRole(userDTO.getUserId(),UserRole.ROLE_USER);


    }

    public void verifyEmail(final String otp){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDto=(UserDTO) authentication.getPrincipal();
        if(userDto.getEmailVerificationStatus().equals(EmailVerificationStatus.UNVERIFIED)){

            OtpDTO otpDTO=otpAccessor.getUnusedOtp(userDto.getUserId(),otp,OtpSentTo.EMAIL);

            if(otpDTO!=null){
                userAccessor.updateEmailVerificationStatus(userDto.getUserId(), EmailVerificationStatus.VERIFIED);
                otpAccessor.updateOtpState(otpDTO.getOtpId(),OtpState.USED);
            }
            else{
                throw new InvalidDataException("otp does not exist");
            }
        }
    }



    public void verifyPhone(final String otp) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDto = (UserDTO) authentication.getPrincipal();
        if (userDto.getPhoneVerificationStatus().equals(PhoneVerificationStatus.UNVERIFIED)) {

            OtpDTO otpDTO = otpAccessor.getUnusedOtp(userDto.getUserId(), otp, OtpSentTo.PHONE);

            if (otpDTO != null) {
                userAccessor.updatePhoneVerificationStatus(userDto.getUserId(), PhoneVerificationStatus.VERIFIED);
                otpAccessor.updateOtpState(otpDTO.getOtpId(), OtpState.USED);
            } else {
                throw new InvalidDataException("otp does not exist");
            }

        }

    }


//    public void sendEmailOtp(){
//        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
//        UserDTO userDto=(UserDTO) authentication.getPrincipal();
//        String otp = generateOtp();
//        otpAccessor.addNewOtp(userDto.getUserId(), otp,OtpSentTo.EMAIL);
//        emailAccessor.sendEmail(userDto.getName(), userDto.getEmail(),
//                "Otp for verifying the email is", "Your otp for verifying the email is" + otp);
//    }
    private String generateOtp(){

        int min=100000;
        int max=999999;
        int otp=(int) (Math.random()*(max-min+1)+min);
        return Integer.toString(otp);

    }
}