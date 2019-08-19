package android.task.metromap.screens.mapscreen;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.task.metromap.R;
import android.task.metromap.model.Metro;
import android.task.metromap.model.services.JsonResponse;
import android.task.metromap.model.services.RetrofitInterface;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , MapContract.IView {

    private MapContract.IPresnter presenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        presenter = new MapPresenterImpl(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //loadJson and add markers
        presenter.loadJson();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    @Override
    public void addMarkers(ArrayList<Metro> data) {
        for(int i = 0 ; i< data.size() ; i++){
            if(i==0){
                LatLng position = presenter.addMarkerToMap(data.get(0));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,12));
            }
            else{
                presenter.addMarkerToMap(data.get(i));
            }
        }
    }
/*private void loadJson(){
        int cachSize= 5*1024*1024;//5mp
        Cache cache= new Cache(getCacheDir(),cachSize);
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

                for(int i = 0 ; i< data.size() ; i++){
                    if(i==0){
                        LatLng position = presenter.addMarkersToMap(data.get(0) , mMap);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,12));
                    }
                    else{
                        presenter.addMarkersToMap(data.get(i),mMap);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Call Fail" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/


}
