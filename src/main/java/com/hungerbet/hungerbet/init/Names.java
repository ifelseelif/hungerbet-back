package com.hungerbet.hungerbet.init;

import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class Names {
    public final static List<Pair<String, String>> mansNames = List.of(new Pair<>("Блеск", "Ритчсон"),
            new Pair<>("Брут", "Ганн"),
            new Pair<>("Бити", "Литье"),
            new Pair<>("Финник", "Одэйр"),
            new Pair<>("Джеймс", "Логан"),
            new Pair<>("Бобби", "Джордан"),
            new Pair<>("Джастин", "Хикс"),
            new Pair<>("Джон", "Касино"),
            new Pair<>("Даниэль", "Бернхардт"),
            new Pair<>("Джексон", "Спиделл"),
            new Pair<>("Роджер", "Митчел"),
            new Pair<>("Пит", "Мелларк"));

    public final static List<Pair<String, String>> womanNames = List.of(new Pair<>("Кашмира", "Ритчсон"),
            new Pair<>("Энобария", "Голдинг"),
            new Pair<>("Вайресс", "Кларк"),
            new Pair<>("Мэгз", "Флэнаган"),
            new Pair<>("Иветта", "Ли-Санчес"),
            new Pair<>("Дайме", "Кёсслер"),
            new Pair<>("Джоанна", "Мейсон"),
            new Pair<>("Цецилия", "Санчез"),
            new Pair<>("Мариан", "Грин"),
            new Pair<>("Тиффани", "Вакслер"),
            new Pair<>("Сидер", "Хоуэлл"),
            new Pair<>("Китнисс", "Эвердин"));

    public final static List<String> items = List.of("антидот", "аптечка", "хлеб", "вода", "лук", "краски", "мачета", "стрелы", "камуфляж");
}
