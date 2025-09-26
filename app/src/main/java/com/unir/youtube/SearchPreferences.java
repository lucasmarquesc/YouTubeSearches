package com.unir.youtube;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

public class SearchPreferences {
    private static final String SEARCHES = "buscas_tags";
    private final SharedPreferences sharedPreferences;
    public SearchPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SEARCHES, Context.MODE_PRIVATE);
    }
    // Adicionar ou atualizar busca
    public void saveSearch(String tag, String query) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag, query);
        editor.apply();
    }
    // Obter busca salva por tag
    public String getSearch(String tag) {
        return sharedPreferences.getString(tag, "");
    }
    // Remover busca
    public void removeSearch(String tag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(tag);
        editor.apply();
    }
    // Obter todas as tags
    public ArrayList<String> getAllTags() {
        ArrayList<String> tags = new ArrayList<>(sharedPreferences.getAll().keySet());
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
        return tags;
    }
}

