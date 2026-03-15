package com.example.confeitariaMacedoFarias.dto;

import com.example.confeitariaMacedoFarias.entities.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    

    public ClientResponseDto(Client entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phoneNumber = entity.getPhoneNumber();
    
    }
    
}