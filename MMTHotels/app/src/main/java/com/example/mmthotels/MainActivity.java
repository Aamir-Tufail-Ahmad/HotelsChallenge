package com.example.mmthotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public Context myContext;
    public static List<HotelsClass> HotelsList;
    public static List<HotelsClass> FavouriteHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HotelsList=new LinkedList<HotelsClass>();
        FavouriteHotels=new LinkedList<HotelsClass>();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,new home_page()).commit();

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,1);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,1);

        //https://www.youtube.com/watch?v=UMZZHHJ37bo
        //Bottom Navigation View
        BottomNavigationView botNavView= findViewById(R.id.bottomNavigationView);
        botNavView.setOnNavigationItemSelectedListener(navListener);

        myContext=getApplicationContext();
    }



    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    //Bottom Navigation Listener for changing pages
    BottomNavigationView.OnNavigationItemSelectedListener navListener=new
            BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()) {
                        case R.id.botNavHome:
                            selectedFragment = new home_page();
                            break;
                        case R.id.botNavHotels:
                            selectedFragment = new HotelsFragment();
                            break;
                        case R.id.botNavWishList:
                            selectedFragment = new WishListFragment();
                            CreateWishList();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout,selectedFragment).commit();
                    return true;
                }
            };
    public static void CreateWishList(){
        FavouriteHotels=new LinkedList<>();
        for(HotelsClass hCur:HotelsList){
            if(hCur.isFavourite()) {
                FavouriteHotels.add(hCur);
            }
        }
    }

//    public List<?> getHotelsFromApi() throws JSONException {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = null;
//        final String baseUrl = "http://developer.goibibo.com/api/voyager/?method=hotels.get_hotels_data_by_city&city_id=6771549831164675055&app_id=775a3443&app_key=ccddfd497684f28887653b73bd90e9e6";
//        try {
//            URI uri = new URI(baseUrl);
//            response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        JSONObject result = new JSONObject(response.toString().substring(5)).getJSONObject("data");
//        Iterator myIter=result.keys();
//        List<String> hotelTag=new LinkedList<>();
//        while(myIter.hasNext()==true){
//            hotelTag.add((String) myIter.next());
//        }
//        List<HotelsClass> hotelsList = new LinkedList<>();
//        String name = "";
//        Double rating = 0.0d;
//        String imgurl = "";
//        String location = "";
//        String price = "";
//        int i  =0;
//        for(String tag : hotelTag)
//        {
//            i++;
//            if(i>30)
//            {
//                break;
//            }
//            try {
//                rating = result.getJSONObject(tag).getJSONObject("hotel_data_node").getDouble("rating");
//                name = result.getJSONObject(tag).getJSONObject("hotel_data_node").getString("name");
//                imgurl = result.getJSONObject(tag).getJSONObject("hotel_data_node").getJSONObject("img_selected").getJSONObject("thumb").getString("l");
//                location = result.getJSONObject(tag).getJSONObject("hotel_data_node").getJSONObject("loc").getString("location");
//                try
//                {
//                    price = result.getJSONObject(tag).getJSONObject("hotel_data_node").getJSONObject("stats").getJSONObject("last_bkd").getString("price");
//                }catch (Exception e)
//                {
//                    price = "NA";
//                }
//                hotelsList.add(new HotelsClass(name, rating, false, imgurl, location,price));
//            } catch (Exception e)
//            { }
//        }
//        return hotelsList;
//    }

}
