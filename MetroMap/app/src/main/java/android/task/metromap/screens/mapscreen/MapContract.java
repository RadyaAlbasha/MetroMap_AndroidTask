package android.task.metromap.screens.mapscreen;


import android.task.metromap.model.Metro;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface MapContract {
    interface IPresnter{
        void loadMetroJsonAndDraw();
        ArrayList<Metro> getData();
        void addMarkerToMap(Metro metro);
        LatLng getLatLng(String positionStr);
        void connectToPoints(String from , String to);
        void drawMarkersAndLines( ArrayList<Metro> data);
    }
    interface IView{
        GoogleMap getmMap();
    }
}
