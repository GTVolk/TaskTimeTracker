package ru.devvault.tttracker.domain;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public interface JsonItem{

    public JsonObject toJson();
    public void addJson(JsonObjectBuilder builder);

}
