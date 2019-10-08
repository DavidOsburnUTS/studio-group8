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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class SellProduct extends AppCompatActivity {


    private EditText mtitle, mdesc, mquantity, mprice;
    private Button mupload;
    private int mquint =1;
    private String selectedItemText, image = "123aa";
    public static final int GET_FROM_GALLERY = 3;
//  for image
private Uri image_uri;
    public Bitmap imag;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private String download_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product);

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mtitle   = (EditText) findViewById(R.id.title);
        mdesc   = (EditText) findViewById(R.id.desc);
        mquantity   = (EditText) findViewById(R.id.quantity_edit_text);
        mupload = (Button) findViewById(R.id.upload);
        mprice = (EditText) findViewById(R.id.price);
        final String productid = UUID.randomUUID().toString();

        /*String value = mquantity.getText().toString();
        mquint =Integer.parseInt(value);*/
       // final int mquint = Integer.parseInt(mquantity.getText().toString().trim());

        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProduct(productid, currentuser, mtitle.getText().toString().toUpperCase(), mdesc.getText().toString(), image, mquint,  selectedItemText, mprice.getText().toString());

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SellProduct.this,
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


    public void cancelHome(View view) {
        finish();
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

            Toast.makeText(this, "added successfully"+image , Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void getImage(View view) {

        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

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