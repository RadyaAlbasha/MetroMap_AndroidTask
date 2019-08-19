package android.task.metromap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Metro {
    @SerializedName("title")
    private String title;
    @SerializedName("destination_Long_Lat")
    private List<String> destination_Long_Lat;

    public Metro() {
    }

    public Metro(String title, List<String> destination_Long_Lat) {
        this.title = title;
        this.destination_Long_Lat = destination_Long_Lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDestination_Long_Lat() {
        return destination_Long_Lat;
    }

    public void setDestination_Long_Lat(List<String> destination_Long_Lat) {
        this.destination_Long_Lat = destination_Long_Lat;
    }

    @Override
    public String toString() {
        return  title + '\n' +
                destination_Long_Lat.get(0);
    }
}
