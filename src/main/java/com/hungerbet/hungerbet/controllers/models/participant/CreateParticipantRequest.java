package com.hungerbet.hungerbet.controllers.models.participant;

import lombok.Data;

@Data
public class CreateParticipantRequest {
    private String firstName;
    private String lastName;
    private int age;
    private int gender;
}
