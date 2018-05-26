package com.example.hoyeonlee.example.ViewHolder.Admin;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoyeonlee.example.R;
import com.squareup.picasso.Picasso;

/**
 * Created by hoyeonlee on 2018. 5. 26..
 */

public class ReservedCustomView extends ConstraintLayout{
        private Context context;

        public ReservedCustomView(Context context) {
            super(context);
            this.context= context;
            init();
        }

        public ReservedCustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context= context;
            init();
        }

        public ReservedCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context= context;
            init();
        }

        private void init() {
            String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
            View view = layoutInflater.inflate(R.layout.view_reservation, ReservedCustomView.this, false);
            addView(view);
        }
        public void setData(String imgUrl, String name, String attr, String number) {
            Picasso.get().load(imgUrl)
                    .resize(60,60)
                    .placeholder(R.drawable.placeholder)
                    .into((ImageView)findViewById(R.id.iv_image));
            ((TextView)findViewById(R.id.tv_name)).setText(name);
            ((TextView)findViewById(R.id.tv_attribute)).setText(attr);
            ((TextView)findViewById(R.id.tv_number)).setText(number+"개");

        }
}
