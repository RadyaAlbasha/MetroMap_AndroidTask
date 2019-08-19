package android.task.metromap.model.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("metro.json")
    Call<JsonResponse> getJSON();
}
