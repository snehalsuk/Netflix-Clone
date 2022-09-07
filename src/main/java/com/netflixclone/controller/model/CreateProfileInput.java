package com.netflixclone.controller.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProfileInput {

    private String name;
    private ProfileTypeInput type;
}
