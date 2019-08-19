package android.task.metromap.screens.mapscreen;

import android.content.Context;
import android.graphics.Color;
import android.task.metromap.R;
import android.task.metromap.model.Metro;
import android.task.metromap.model.services.JsonResponse;
import android.task.metromap.model.services.RetrofitInterface;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapPresenterImpl  implements MapContract.IPresnter{

    MapContract.IView activity;
    ArrayList<Metro> data;

    public MapPresenterImpl( MapContract.IView activity){
        this.activity = activity;
    }

    public ArrayList<Metro> getData() {
        return data;
    }

    public LatLng getLatLng(String positionStr){
        //convert from string to latlng
        List<String> latLngStr = Arrays.asList(positionStr.split(","));
        LatLng latLng =  new LatLng(Double.valueOf(latLngStr.get(0)),Double.valueOf(latLngStr.get(1)));
        return latLng;
    }
    public void addMarkerToMap(Metro metro) {
        LatLng position = getLatLng(metro.getDestination_Long_Lat().get(0));
        activity.getmMap().addMarker(new MarkerOptions().position(position).title(metro.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_metro)));
    }

    public void connectToPoints(String from , String to){
        //draw line between two points
        LatLng fromLatLang= getLatLng(from);
        LatLng toLatLang= getLatLng(to);
        //Polyline line =
         activity.getmMap().addPolyline(new PolylineOptions()
                .add(fromLatLang, toLatLang)
                .width(5)
                .color(Color.RED));
    }

    public void drawMarkersAndLines(ArrayList<Metro> data) {
        for(int i = 0 ; i< data.size() ; i++){
            addMarkerToMap(data.get(i));
            if(i != data.size()-1){
                connectToPoints(data.get(i).getDestination_Long_Lat().get(0),data.get(i+1).getDestination_Long_Lat().get(0));
            }

            if(i==0){
                LatLng position = getLatLng(data.get(i).getDestination_Long_Lat().get(0));
                activity.getmMap().moveCamera(CameraUpdateFactory.newLatLngZoom(position,12));
            }
        }
    }

    public void loadMetroJsonAndDraw(){
        int cachSize= 5*1024*1024;//5mp
        Cache cache= new Cache(((Context)activity).getCacheDir(),cachSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://atpnet.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<JsonResponse> call = request.getJSON();

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                data = response.body().getMetroRows();

                // Add a marker and move the camera
                drawMarkersAndLines(data);
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Toast.makeText((Context) activity, "Call Fail" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
