package com.hungerbet.hungerbet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hungerbet.hungerbet.controllers.models.events.EventRequest;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;

public interface GameEventService {
    HappenedEvent AddEvent(EventRequest request) throws HttpException, JsonProcessingException;
}
