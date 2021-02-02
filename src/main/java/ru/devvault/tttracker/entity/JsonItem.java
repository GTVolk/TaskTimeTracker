package ru.devvault.tttracker.entity;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public interface JsonItem{

    JsonObject toJson();
    void addJson(JsonObjectBuilder builder);
}
