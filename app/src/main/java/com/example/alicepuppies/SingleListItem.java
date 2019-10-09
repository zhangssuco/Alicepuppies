package com.example.alicepuppies;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleListItem extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item_view);

        TextView txtProduct = (TextView) findViewById(R.id.product_label);

        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("product");
        // displaying selected product name

        txtProduct.setText(product);

        ImageView img= (ImageView) findViewById(R.id.image1);
        //img.setImageResource(R.drawable.yorkie);


        int id = getResources().getIdentifier(product.toLowerCase(),"drawable",getPackageName());
        img.setImageResource(id);


    }
}