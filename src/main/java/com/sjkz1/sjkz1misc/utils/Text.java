package com.sjkz1.sjkz1misc.utils;

import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

public class Text {

	public static final Gson GSON = new Gson();
    private static final Gson GSON_BUILDER = new GsonBuilder().setPrettyPrinting().create();


    public static void toJson(Object src, Appendable writer)
    {
        if (src != null)
        {
            toJson(src, src.getClass(), writer);
        }
        else
        {
            toJson(JsonNull.INSTANCE, writer);
        }
    }

    private static void toJson(Object src, Type typeOfSrc, Appendable writer)
    {
        try
        {
            JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
            GSON_BUILDER.toJson(src, typeOfSrc, jsonWriter);
        }
        catch (JsonIOException e)
        {
            throw new JsonIOException(e);
        }
    }

    private static JsonWriter newJsonWriter(Writer writer)
    {
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setIndent("    ");
        jsonWriter.setSerializeNulls(GSON_BUILDER.serializeNulls());
        return jsonWriter;
    }
}
