package com.example.hoyeonlee.example.Admin.Menu;

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

import com.example.hoyeonlee.example.Admin.Reservation.ReservationActivity;
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

public class MenuUpdateDialog extends Dialog {
    private static final String TAG = "ADD_DIALOG_LOG";

    @BindView(R.id.iv_picture)
    CircleImageView ivProfile;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.spinner_category)
    NiceSpinner spinnerCategory;
    @BindView(R.id.input_price)
    EditText inputPrice;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.ibtn_delete)
    ImageButton deleteImageButton;

    List<String> categoryList;
    MenuActivity context;
    Menu prevMenu;
    ApiService apiService;
    private boolean isFirst = true;
    private boolean isImageUpdated = false;

    public MenuUpdateDialog(@NonNull Context context) {
        super(context);
        this.context = (MenuActivity) context;
    }

    //For Update Constructor
    public MenuUpdateDialog(@NonNull Context context, Menu prevMenu) {
        super(context);
        this.context = (MenuActivity) context;
        this.prevMenu = prevMenu;
        isFirst = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addmenu);
        ButterKnife.bind(this);
        apiService = MApplication.getInstance().getApiService();
        categoryList = new LinkedList<>(Arrays.asList("커피", "차", "요거트", "디저트"));
        spinnerCategory.attachDataSource(categoryList);

        //Dialog Size 조정
        DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.9); //set height to 90% of total
        int width = (int) (metrics.widthPixels * 0.9); //set width to 90% of total
        getWindow().setLayout(width, height); //set layout

        //Update할 때
        if (prevMenu != null) {
            Picasso.get().load(prevMenu.getThumb()).into(ivProfile);
            inputName.setText(prevMenu.getName());
            inputPrice.setText(prevMenu.getPrice() + "");
            deleteImageButton.setVisibility(View.VISIBLE);
            switch (prevMenu.getCategory()){
                case "커피":
                    spinnerCategory.setSelectedIndex(0);
                    break;
                case "차":
                    spinnerCategory.setSelectedIndex(1);
                    break;
                case "요거트":
                    spinnerCategory.setSelectedIndex(2);
                    break;
                case "디저트":
                    spinnerCategory.setSelectedIndex(3);
                    break;
                default:
                    spinnerCategory.setSelectedIndex(1);
            }
        }

    }


    @OnClick({R.id.btn_complete, R.id.iv_picture, R.id.ibtn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_picture:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intent, MenuActivity.PICK_IMAGE_REQUEST);
                break;
            case R.id.btn_complete:
                updateMenu();
                break;
            case R.id.ibtn_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("정말 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setNegativeButton("취소", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("확인", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMenu(prevMenu);
                                dismiss();
                            }
                        }).show();
                break;
        }
    }

    byte[] imagePath;

    public void setImage(byte[] bytes, Uri uri) {
        imagePath = bytes;
        Picasso.get().load(uri).into(ivProfile);
        isImageUpdated = true;
    }

    public void updateMenu(){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", inputName.getText().toString())
                .addFormDataPart("price", inputPrice.getText().toString())
                .addFormDataPart("category", categoryList.get(spinnerCategory.getSelectedIndex()));

        if (isImageUpdated) {
            builder.addFormDataPart("thumb", "photo.jpg", RequestBody.create(MediaType.parse("multipart/form-data"), imagePath));
        }

        RequestBody requestBody = builder.build();
        //at First, Add
        if (isFirst) {
            MApplication.getInstance().getApiService().addMenu(requestBody).enqueue(new Callback<Menu>() {
                @Override
                public void onResponse(Call<Menu> call, Response<Menu> response) {
                    if (response.isSuccessful()) {
                        context.adapter.addMenu(response.body());
                        Log.v(TAG, response.body().toString());
                    } else {
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), response.code()+" Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Menu> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            //update Item
        } else {
            MApplication.getInstance().getApiService().updateMenu(prevMenu.getId() + "", requestBody).enqueue(new Callback<Menu>() {
                @Override
                public void onResponse(Call<Menu> call, Response<Menu> response) {
                    if (response.isSuccessful()) {
                        Menu menu = response.body();
                        context.adapter.updateMenu(prevMenu, menu);
                        Log.v(TAG, response.body().toString());
                    } else {
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Menu> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        dismiss();
    }

    public void deleteMenu(final Menu menu){
        apiService.deleteMenu(menu.getId()+"").enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                try {
                    if (response.isSuccessful()) {
                        context.adapter.deleteMenu(menu);
                    } else {
                        Toast.makeText(context,response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



}
