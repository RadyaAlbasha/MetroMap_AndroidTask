package android.task.metromap.screens.mapscreen;


import android.task.metromap.model.Metro;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface MapContract {
    public interface IPresnter{
        LatLng addMarkerToMap(Metro metro);
        void loadJson();
    }
    public interface IView{
        GoogleMap getmMap();
        void addMarkers( ArrayList<Metro> data);
    }
}
