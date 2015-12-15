package com.meeladsd.memoriesapplication;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by Hamster on 15/12/2015.
 */
public class Converter {

    public JSONObject ClassToJSON(Object obj)
    {
        try {

            JSONObject object = new JSONObject();

            Class<?> objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof SerializedName) {
                        SerializedName myAnnotation = (SerializedName) annotation;
                        String name = myAnnotation.value();
                        Object value = field.get(obj);

                        if (value == null)
                            value = new String("");

                        object.put(name, value);
                    }
                }
            }

            return object;
        }
        catch (Exception ex)
        {
            Log.e("Convertor", ex.getMessage());
            return null;
        }

    }
}
