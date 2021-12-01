package com.example.lego;

import org.json.JSONException;

public interface IaResultHandler {
    void onResult(CaResult Result) throws JSONException;
}
