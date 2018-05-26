package com.example.hoyeonlee.example.Admin.Reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hoyeonlee.example.Admin.Menu.MenuActivity;
import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.MApplication;
import com.example.hoyeonlee.example.Network.ApiService;
import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hoyeonlee on 2018. 3. 23..
 */

public class ReservationUpdateDialog extends Dialog {
    private static final String TAG = "RESERVATION_DIALOG_LOG";


    ApiService apiService;
    ReservationActivity context;
    public ReservationUpdateDialog(@NonNull Context context) {
        super(context);
        this.context = (ReservationActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addmenu);
        ButterKnife.bind(this);
        apiService = MApplication.getInstance().getApiService();

        //Dialog Size 조정
        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.9); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.9); //set width to 90% of total
        getWindow().setLayout(width, height); //set layout
    }


}
