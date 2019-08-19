package android.task.metromap.screens.mapscreen;

import android.content.Context;
import android.task.metromap.R;
import android.task.metromap.model.Metro;
import android.task.metromap.model.services.JsonResponse;
import android.task.metromap.model.services.RetrofitInterface;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    public LatLng addMarkerToMap(Metro metro) {
        List<String> latLngStr = Arrays.asList(metro.getDestination_Long_Lat().get(0).split(","));
        LatLng position =  new LatLng(Double.valueOf(latLngStr.get(0)),Double.valueOf(latLngStr.get(1)));
        activity.getmMap().addMarker(new MarkerOptions().position(position).title(metro.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_metro)));
        return position;
    }

    public void loadJson(){
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
                activity.addMarkers(data);
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Toast.makeText((Context) activity, "Call Fail" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
