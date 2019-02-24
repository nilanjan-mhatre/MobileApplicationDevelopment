package mad.nil.mygeo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    int PLACE_PICKER_REQUEST = 1;
    ArrayList<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(this);


        //AIzaSyBnZ1snzCa3z1LSa2v8pU258vjWWbo-wpw
    }

    private void showPathFromJson() {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getResources().openRawResource(R.raw.trip)));


            Points points = gson.fromJson(br, Points.class);
            plotPoints(points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void plotPoints(Points points) {

        markers = new ArrayList<>();
        for (int i = 0; i < points.points.size() - 1; i++) {
            LatLng latLng1 = new LatLng(points.points.get(i).getLatitude(),
                    points.points.get(i).getLongitude());
            LatLng latLng2 = new LatLng(points.points.get(i + 1).getLatitude(),
                    points.points.get(i + 1).getLongitude());
            plotPolyline(latLng1, latLng2);

            Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng1));
            markers.add(marker);

            if (i == 0) {
                marker.setTitle(getString(R.string.start_location));
            } else if (i == points.points.size() - 2) {
                Marker marker1 = googleMap.addMarker(new MarkerOptions()
                        .position(latLng2)
                        .title(getString(R.string.ending_location)));
                markers.add(marker1);
                marker.remove();
            } else {
                marker.remove();
            }
        }

//        centerMap(points.points);

    }

    private void centerMap(ArrayList<Points.Point> points) {

        double markerLat = 0.0;
        double markerLong = 0.0;

        for (Points.Point point : points) {
            markerLat += point.getLatitude();
            markerLong += point.getLongitude();
        }

        LatLng latLng = new LatLng(markerLat / points.size(), markerLong / points.size());
//        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void plotPolyline(LatLng latLng1, LatLng latLng2) {
        googleMap.addPolyline(new PolylineOptions()
                .add(latLng1, latLng2)
                .width(5)
                .color(Color.RED));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                googleMap.moveCamera(cu);
            }
        });
        showPathFromJson();
    }
}
