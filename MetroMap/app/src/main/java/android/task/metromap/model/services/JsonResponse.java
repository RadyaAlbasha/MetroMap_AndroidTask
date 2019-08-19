package android.task.metromap.model.services;

import android.task.metromap.model.Metro;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JsonResponse {
    @SerializedName("rows")
    ArrayList<Metro> metroRows;

    public ArrayList<Metro> getMetroRows() {
        return metroRows;
    }
}
