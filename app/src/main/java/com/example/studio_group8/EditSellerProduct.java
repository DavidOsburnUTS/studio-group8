package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditSellerProduct  extends AppCompatActivity {
    private Button submit;
    private Button removeProduct;
    private EditText name,price, description, mquantity;

    private String pname;
    private String productID = "";
    private Button upload;
    private int mquint =1;
    private String selectedItemText, image = "123aa";
    public static final int GET_FROM_GALLERY = 3;

    private Button remove;
    //  for image
    private Uri image_uri;
    public Bitmap imag;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private String download_uri;


    final DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product");
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_edit_product);

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        submit = findViewById(R.id.seller_submit);
        name = findViewById(R.id.product_seller_title);
        price = findViewById(R.id.product_price_seller);
        description = findViewById(R.id.product_seller_desc);
        mquantity   = (EditText) findViewById(R.id.quantity_seller);
        description = findViewById(R.id.product_seller_desc);
        description = findViewById(R.id.product_seller_desc);




        productID = getIntent().getStringExtra("productid");


        displaySpecificProductInfo(productID);



/*
        removeProduct = findViewById(R.id.seller_remove);
        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productRef
                        .child(productID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(EditSellerProduct.this, "Item removed permanently", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }

        });


*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct(productID,  currentuser, name.getText().toString().toUpperCase(), description.getText().toString(), price.getText().toString(), mquint, image, selectedItemText );


            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner2);



        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EditSellerProduct.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void increment (View view) {
        mquint = mquint +1;
        display(mquint);

    }


    public void decrement(View view) {
        if(mquint>1) {
            mquint = mquint - 1;
            display(mquint);
        }
    }

    private void display(int number) {
        EditText quantityText = (EditText) findViewById(R.id.quantity_edit_text);
        String quantityValue = quantityText.getText().toString();
        int intQuantityValue = Integer.parseInt(quantityValue);
        quantityText.setText("" +number);

    }


    private void displaySpecificProductInfo(String productID)
    {
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Product products = dataSnapshot.getValue(Product.class);
                    name.setText(products.getName());
                    price.setText(String.valueOf( products.getprice()));
                    description.setText(products.getDesc());
                    mquantity.setText(products.getQuantity());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void getImage(View view) {

        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

    }


    public void AddProduct(String productid, String seller, String mtitle, String mdesc, String img, int mquantity, String cat, String price){
        double d = Double.parseDouble(price);
        final Product addProduct = new Product(productid, seller, mtitle, mdesc, img, mquantity, cat, d);
        if (mtitle.trim().equals("") || mdesc.trim().equals("") ) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }

        else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Product").child(addProduct.productid).setValue(addProduct);

            Toast.makeText(this, "added successfully" , Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            image_uri = data.getData();
            image = null;
            try {
                imag = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);

                final StorageReference eventRef = storageRef.child(image_uri.getLastPathSegment());
                UploadTask uploadTask = eventRef.putFile(image_uri);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                download_uri = uri.toString();
                                image = download_uri;

                            }
                        });

                    }
                });

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



}

