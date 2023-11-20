package com.hirakurai.warmplacesplugin.utils.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class LocationAdapter extends TypeAdapter<Location> {
    @Override
    public void write(JsonWriter writer, Location location) throws IOException {
        writer.beginObject();
        writer.name("x");
        writer.value(location.getBlockX());
        writer.name("y");
        writer.value(location.getBlockY());
        writer.name("z");
        writer.value(location.getBlockZ());
        writer.name("world");
        writer.value(location.getWorld().getName());
        writer.endObject();
    }

    @Override
    public Location read(JsonReader reader) throws IOException {
        reader.beginObject();
        JsonToken token = reader.peek();
        Double x = null;
        Double y = null;
        Double z = null;
        String worldName = null;
        while (reader.hasNext()) {
            if (token.equals(JsonToken.NAME)) {
                String fieldName = reader.nextName();
                if (fieldName.equalsIgnoreCase("x")) {
                    x = reader.nextDouble();
                } else if (fieldName.equalsIgnoreCase("y")) {
                    y = reader.nextDouble();
                } else if (fieldName.equalsIgnoreCase("z")) {
                    z = reader.nextDouble();
                } else if (fieldName.equalsIgnoreCase("world")) {
                    worldName = reader.nextString();
                }
            }
        }
        if (x == null)
            throw new IllegalStateException("Invalid config on location (x is null) ");
        else if (y == null)
            throw new IllegalStateException("Invalid config on location (y is null) ");
        else if (z == null)
            throw new IllegalStateException("Invalid config on location (z is null) ");
        else if (worldName == null)
            throw new IllegalStateException("Invalid world on location ");

        reader.endObject();
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}
