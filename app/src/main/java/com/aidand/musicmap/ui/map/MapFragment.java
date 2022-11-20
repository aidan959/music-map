package com.aidand.musicmap.ui.map;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import com.aidand.musicmap.MainActivity;
import com.aidand.musicmap.database.AppDatabase;
import com.aidand.musicmap.database.ListenerInitializer;
import com.aidand.musicmap.database.models.CameraLocation;
import com.aidand.musicmap.database.models.Listen;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aidand.musicmap.R;

import com.aidand.musicmap.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private List<Listen> mDataset;
    private MapView mMapView;
    GoogleMap map;
    CameraLocation cameraLocation;
    AppDatabase db;
    private Bundle savedInstanceState = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        // TODO USE ASYNC IN FUTURE
        db = ((MainActivity) getActivity()).globalDb;;
        //Log.println(String.valueOf(db.isOpen()));
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        this.savedInstanceState=savedInstanceState;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

        if(db.cameraDao().isRowIsExist(0)){
            cameraLocation = db.cameraDao().get();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraLocation.createCamera()));
        }
        else {
            cameraLocation = new CameraLocation(0, map.getCameraPosition());
            db.cameraDao().insertAll(cameraLocation);
        }

        map.setOnCameraMoveListener(() -> saveLocation());

        mDataset = ListenerInitializer.initLocationDataset(db);
        for (Listen listen : mDataset){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(listen._location.getLatitude(),listen._location.getLongitude()))
                    .title(listen._timeRecorded.toString()));

        }
        // value is populated in ListensAdapter onclick of album art
        if(getArguments()!=null&&getArguments().containsKey("listen_id")){
            int selectedListen = getArguments().getInt("listen_id");
            Listen listen = mDataset.get(selectedListen);
            LatLng latLng = new LatLng(listen._location.getLatitude(),listen._location.getLongitude());

            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.moveCamera(CameraUpdateFactory.zoomTo(10));

        }
        enableMyLocation();
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if(getContext() != null)
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getContext(), permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
                return;
            }

        PermissionUtils.requestPermission((AppCompatActivity) (getActivity()), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION ,  true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    public void saveLocation(){
        if(!db.cameraDao().isRowIsExist(0)) {
            cameraLocation = new CameraLocation(0, map.getCameraPosition());
            db.cameraDao().insertAll(cameraLocation);
        } else {
            cameraLocation.populateFromCameraPosition(map.getCameraPosition());
            db.cameraDao().update(cameraLocation);
        }

    }

    @Override
    public void onResume() {
        mMapView.onResume();
        // cameraLocation = db.cameraDao().get();
        // map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraLocation.createCamera()));
        super.onResume();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        db.close();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}