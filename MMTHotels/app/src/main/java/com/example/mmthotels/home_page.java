package com.example.mmthotels;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_page.
     */
    // TODO: Rename and change types and number of parameters
    public static home_page newInstance(String param1, String param2) {
        home_page fragment = new home_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_home_page, container, false);

        final TextView homeTextView=view.findViewById(R.id.homePageTextView);
        Button downloadBtn= view.findViewById(R.id.buttonHomePage);
        Log.d("Download Button","Activated");
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startDownloading();
                Toast.makeText(getContext(), "Download Button Pressed. URL Unreachable. To continue with dummy data - Press Load Data", Toast.LENGTH_SHORT).show();
                Log.d("Download Button", "Button Pressed");
            }
        });
        Button ViewDownloadBtn=view.findViewById(R.id.buttonViewDownload);
        ViewDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                file = new File(file,"getHotels.json");
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    Log.d("Reading",br.toString());
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                    homeTextView.setText("Error, not reachable online. Using dummy data");
                    InputStream is =  getResources().openRawResource(R.raw.gethotels);
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;

                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }

                }

                try {
                    MainActivity.HotelsList=jsonParserFunction(text);
                    Log.d("Parse Json","Parse Successful");
                    homeTextView.setText(homeTextView.getText()+"\n\n"+"Done");
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeTextView.setText(homeTextView.getText()+"\n\n"+"Critical Failure");
                }
                if(MainActivity.HotelsList==null){
                    homeTextView.setText(homeTextView.getText()+"\n\n"+"Failed");
                }else{
                    HotelsClass htemp= (HotelsClass) MainActivity.HotelsList.get(0);
                    homeTextView.setText(homeTextView.getText()+"\n\n"+"Success, Click Load Data and proceed");
                }
            }
        });

        return view;
    }

    private List<HotelsClass> jsonParserFunction(StringBuilder text) throws JSONException {
        JSONArray result1 = new JSONArray(text.toString());
        List<HotelsClass> hotelsList = new LinkedList<>();
        for(int i=0;i<result1.length();i++){
            JSONObject curResult= (JSONObject) result1.get(i);
            String name= (String) curResult.get("name");
            double rating= (double) curResult.get("rating");
            boolean favourite= (boolean) curResult.get("favourite");
            String imageurl= (String) curResult.get("imageurl");
            String location= (String) curResult.get("location");
            String price= (String) curResult.get("price");
            HotelsClass h1=new HotelsClass(name,rating,favourite,imageurl,location,price);
            hotelsList.add(h1);
        }
        return hotelsList;
    }

    private void startDownloading() {

        //URL of api
        //String myURL= "https://cdn.vox-cdn.com/thumbor/BlE1vvjUCAR6k03nbUTN1zZuO4c=/0x0:2040x1360/1200x800/filters:focal(857x517:1183x843)/cdn.vox-cdn.com/uploads/chorus_image/image/56216025/jbareham_170504_1691_0020.0.0.jpg";
        //String myURL="http://localhost:8080/getHotels";
        String myURL="174.254.2.23:8080/getHotels";

        DownloadManager.Request requests=new DownloadManager.Request(Uri.parse(myURL));
        requests.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
        requests.setTitle("Download");
        requests.setDescription("Downloading Files");

        requests.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        requests.setDestinationInExternalFilesDir(getContext(),Environment.DIRECTORY_DOWNLOADS,"getHotels.json");
        Log.d("Download Location",Environment.DIRECTORY_DOWNLOADS+"getHotels.json");
        DownloadManager manager=(DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(requests);
        //"/storage/self/primary/Download/MMTHotels.txt"
    }


}