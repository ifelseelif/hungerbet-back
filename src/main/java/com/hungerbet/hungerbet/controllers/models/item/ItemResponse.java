package com.hungerbet.hungerbet.controllers.models.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemResponse {
    private String name;

    public ItemResponse(String name) {
        this.name = name;
    }
}
