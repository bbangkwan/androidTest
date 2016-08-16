package com.playnd.okb.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playnd.okb.R;

/**
 * Created by ByeongKwan on 2016-07-18.
 */
public class TraceFriendFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
//public class TraceFriendFragment extends Fragment implements OnMapReadyCallback {
    private static String TAG = TraceFriendFragment.class.getName();

    GoogleMap googleMap;
    View view;
    GoogleMapOptions googleMapOption;
    MapView googleMapView;

    static final LatLng tmp_position = new LatLng(37.56, 126.97);

    private GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Location locationInfo;
    private static final int REQUEST_CODE_LOCATION = 2;

    LatLng initlatlng;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            view = inflater.inflate(R.layout.fragment_tracefriend_main, container, false);

            MapsInitializer.initialize(this.getActivity());

            googleMapView = (MapView) view.findViewById(R.id.googleMap);
            googleMapView.onCreate(savedInstanceState);
            googleMapView.onResume();

            googleMapView.getMapAsync(this);//onMapReady랑 연결 시키는 부분분
        }catch (InflateException e){
            Log.e(TAG, "Inflate exception");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        Button start_gps_trace = (Button) view.findViewById(R.id.start_real_trace);
        Button stop_gps_trace = (Button) view.findViewById(R.id.stop_real_trace);

        start_gps_trace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realTimeTraceStart();
            }
        });

        stop_gps_trace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realTimeTraceStop();
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            CheckPermission();

            return;
        }
        Log.d(TAG, "onMapReady");

        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setMyLocationEnabled(true);

        /*Marker seoul = googleMap.addMarker(new MarkerOptions().position(tmp_position).title("테스트!!!"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp_position, 20));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);*/

        /*googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener(){
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng location_center = cameraPosition.target;
                Log.d(TAG, location_center+"");
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, requestCode+"");
        switch(requestCode){
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    Toast.makeText(getActivity(), "설정 완료", Toast.LENGTH_LONG).show();
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                    Toast.makeText(getActivity(), "권한 설정을 동의하세요.", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    Toast.makeText(getActivity(), "ACCESS_COARSE_LOCATION 설정 완료", Toast.LENGTH_LONG).show();
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                    Toast.makeText(getActivity(), "ACCESS_COARSE_LOCATION 권한 설정을 동의하세요.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        googleMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMapView.onLowMemory();
    }

    /* 현재 위치 검색 (GPS + Network) */

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setSmallestDisplacement(5000);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CheckPermission();
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        Log.d(TAG, "PendingResult : "+result);

        if(result != null){
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    Log.d(TAG, "status : "+status);
                    final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                    Log.d(TAG, "locationSettingsStates : "+locationSettingsStates);
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // 모든 위치 설정이 잘 되어있다.
                            // client는 위치 요청을 여기서 초기화할 수 있다.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // 위치 설정이 잘못되었다.
                            // 하지만, 보여지는 사용자 다이얼로그로 변경할 수 있다.
                            try {
                                // startResolutionForResult() 메소드를 호출하여 다이얼로그를 보여주고,
                                // onActivityResult() 메소드에서 결과를 확인하자.
                                status.startResolutionForResult(getActivity(), 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // 에러는 무시하자.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // 위치 설정이 잘못되었다.
                            // 하지만, 설정을 변경할 방법이 없기에 다이얼로그를 보여주지 않는다.
                            break;
                    }
                }
            });
        }

        getGPSTraceInfo();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
        Log.i(TAG, "onConnectionFailed - "+connectionResult.getErrorMessage());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    private void CheckPermission() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }

        hasWriteContactsPermission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }
        ;
    }

    public void getGPSTraceInfo(){
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            CheckPermission();

            return;
        }

        locationInfo = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(locationInfo != null){
            initlatlng = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
            Log.d(TAG, "initlatlng : "+initlatlng);
        }

        //realTimeTraceSet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult : " + requestCode+" / "+ resultCode+" / "+ data);
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        LatLng CURRENT_LOCATION = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.clear();
        Log.e("onMyLocationChange", CURRENT_LOCATION+"");

        /*double d1=location.getLatitude();
        double d2=location.getLongitude();
        Log.e("onMyLocationChange", d1 + "," + d2);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(d1, d2), 18));*/

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 14));
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 14), 2000, null);
        CameraPosition cp = new CameraPosition.Builder().target((CURRENT_LOCATION)).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 2000, null);
    }

    public void realTimeTraceStart(){
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            CheckPermission();

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    public void realTimeTraceStop(){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
}
