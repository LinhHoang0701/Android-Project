package com.example.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.Adapter.ReviewAdapter;
import com.example.androidproject.DAL.ProductSQLiteHelper;
import com.example.androidproject.DAL.PurchaseSQLiteHelper;
import com.example.androidproject.DAL.ReviewSQLiteHelper;
import com.example.androidproject.Entity.Product;
import com.example.androidproject.Entity.Review;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailProduct extends MainActivity
        implements  NavigationView.OnNavigationItemSelectedListener {

    private TextView name;
    private TextView price;
    private RatingBar ratingBar;
    private TextView ratingCount;
    private TextView description;
    private TextView category;
    private TextView provider;
    private Button addToCart;
    private ImageView imgCurrent;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView imgFocus;
    private ScrollView scrollView;
    private ImageView back;
    Product product;
    private ListView listView;
    private List<Review> reviews = new ArrayList<>();
    private RatingBar ratingComment;
    private EditText comment;
    private Button commentButton;
    ReviewSQLiteHelper reviewSQLiteHelper = new ReviewSQLiteHelper(this);

    private  void loadComment(){
        ReviewSQLiteHelper reviewSQLiteHelper = new ReviewSQLiteHelper(this);
        reviews = reviewSQLiteHelper.getAllReviewOfProduct(product.getProductId(), null);
        reviewSQLiteHelper.close();
        ReviewAdapter adapter = new ReviewAdapter(this, R.layout.comment_layout, reviews);
        listView.setAdapter(adapter);
    }

    public Product loadProduct(int id){
        ProductSQLiteHelper productSQLiteHelper = new ProductSQLiteHelper(this);
        Product p = productSQLiteHelper.getProductById(id);
        return p;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSession(navigationView);


        initEvent();

        name = findViewById(R.id.txtProductName);
        price = findViewById(R.id.txtCurrentPrize);
        ratingBar = findViewById(R.id.rtbProductRating);
        ratingCount = findViewById(R.id.txtRateNumber);
        description = findViewById(R.id.txtDescription);
        category = findViewById(R.id.txtCategory);
        provider = findViewById(R.id.txtProvider);
        addToCart = findViewById(R.id.btnBuy);
        imgCurrent = findViewById(R.id.imgCurrentShow);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        imgFocus = findViewById(R.id.imgFocus);
        scrollView = findViewById(R.id.scrollView2);
        back = findViewById(R.id.imgBack);
        listView = findViewById(R.id.listComment);

        Intent intent = getIntent();
        int productId = (int)intent.getSerializableExtra("product");
        product = loadProduct(productId);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()) + "$" );
        ratingBar.setRating((float) product.getRating());
        ratingCount.setText("(" + String.valueOf(product.getRatingCount()) + ")");
        description.setText(product.getDescription());
        category.setText(product.getCategory());
        provider.setText(product.getProvider());
        ArrayList<byte[]> listImg = product.getListProductImages();

        ArrayList<ImageView> listImgView = new ArrayList<>();
        listImgView.add(img1);
        listImgView.add(img2);
        listImgView.add(img3);

        for (int i = 0; i < listImgView.size(); i++){
            Bitmap bmp = BitmapFactory.decodeByteArray(listImg.get(i),0, listImg.get(i).length);
            listImgView.get(i).setImageBitmap(bmp);
        }
        BitmapDrawable drawable = (BitmapDrawable) img1.getDrawable();
        imgCurrent.setImageBitmap(drawable.getBitmap());

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap current = ((BitmapDrawable)img1.getDrawable()).getBitmap();
                imgCurrent.setImageBitmap(current);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap current = ((BitmapDrawable)img2.getDrawable()).getBitmap();
                imgCurrent.setImageBitmap(current);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap current = ((BitmapDrawable)img3.getDrawable()).getBitmap();
                imgCurrent.setImageBitmap(current);
            }
        });

        imgCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap current = ((BitmapDrawable)imgCurrent.getDrawable()).getBitmap();
                imgFocus.setImageBitmap(current);
                imgFocus.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFocus.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
            }
        });

        setCommentEnable();
        loadComment();
    }

    public void addToCart(View view){
        int quantity = 1;
        shoppingCart = readPaper();
        if(shoppingCart.containsKey(product.getProductId())){
            quantity = shoppingCart.get(product.getProductId()) + 1;
        }
        shoppingCart.put(product.getProductId(), quantity);
        writePaper();
        Toast.makeText(this, "Added to your cart", Toast.LENGTH_LONG).show();
    }
    
    public void setCommentEnable(){
        ratingComment = findViewById(R.id.rtbRatingComment);
        comment = findViewById(R.id.txtComment);
        commentButton = findViewById(R.id.btnComment);
        if (username == null){
            ratingComment.setIsIndicator(true);
            comment.setEnabled(false);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You must login first", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        if (reviewSQLiteHelper.isOwned(product.getProductId(), username)){
            final Review review = reviewSQLiteHelper.getReviewByUsernameAndProductId(username, product.getProductId());
            reviewSQLiteHelper.close();
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ratingComment.getRating() == 0 || comment.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "You must imput comment and rating to comment", Toast.LENGTH_LONG).show();
                    } else {
                        if (review == null) {
                            reviewSQLiteHelper.insertReview(product.getProductId(), username, comment.getText().toString(), (int) ratingComment.getRating());
                            finish();
                            startActivity(getIntent());
                        } else {
                            reviewSQLiteHelper.updateReview(product.getProductId(), username, comment.getText().toString(), (int) ratingComment.getRating());
                        }
                        reviewSQLiteHelper.close();
                        ratingComment.setRating(0);
                        comment.setText("");
                        comment.clearFocus();
                        loadComment();
                    }
                }
            });
        }
        else {
            ratingComment.setIsIndicator(true);
            comment.setEnabled(false);
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You must buy product before rating", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}

