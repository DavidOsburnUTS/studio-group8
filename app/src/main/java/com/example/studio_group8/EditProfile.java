package com.example.studio_group8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.studio_group8.SellProduct.GET_FROM_GALLERY;

public class EditProfile extends AppCompatActivity{

    private EditText mtitle;
    private Button mupdate;

    //  for image
    private Uri image_uri;
    public Bitmap imag;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private String download_uri;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mtitle   = (EditText) findViewById(R.id.edit_name);

        mupdate =(Button) findViewById(R.id.update);

        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditP(mtitle.getText().toString(), image);


            }
        });
    }

    public void EditP(String title, String image){
        if(!title.isEmpty()){

            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("User").child(currentuser).child("username").setValue(mtitle.getText().toString());
            mDatabase.child("Profilepic").child(currentuser).child("image").setValue(image);
            Toast.makeText(this, "Edited successfully" , Toast.LENGTH_SHORT).show();
            finish();
//            Intent order = new Intent(EditProfile.this, Accountfragment.class);
//            startActivity(order);
        }else {
            Toast.makeText(this, "Please enter all details" , Toast.LENGTH_SHORT).show();
            finish();
        }


    }


    public void back( View view) {
        finish();
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
                final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final StorageReference eventRef = storageRef.child("Profile").child(image_uri.getLastPathSegment());
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
