package com.netflixclone.security;

import com.netflixclone.accessor.UserAccessor;
import com.netflixclone.accessor.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserAccessor userAccessor;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


            UserDTO userDTO = userAccessor.getUserByEmail(username);

            if(userDTO!=null){
                return new User(userDTO.getEmail(), userDTO.getPassword(),
            Arrays.asList(new SimpleGrantedAuthority(userDTO.getRole().name())));

        }


            throw new UsernameNotFoundException("User with email" + username + "not found");
        }

    }

