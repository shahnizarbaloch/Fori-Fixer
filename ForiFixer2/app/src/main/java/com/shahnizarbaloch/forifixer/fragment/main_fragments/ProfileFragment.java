package com.shahnizarbaloch.forifixer.fragment.main_fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shahnizarbaloch.forifixer.R;
import com.squareup.picasso.Picasso;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    private static final int PREQCODE = 1;
    private static final int SELECT_IMAGE = 100;
    private TextView tvName, tvMobileNumber;
    private CircularImageView imgDP;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile,container,false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        currentUser = mAuth.getCurrentUser();
        ImageView btnChangeDp = view.findViewById(R.id.btn_changeDp);
        btnChangeDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndAskForPermission();
                }
                else{
                    openGallery();
                }
            }


        });
        tvName = view.findViewById(R.id.tv_name);
        progressBar=view.findViewById(R.id.progressBar);
        tvMobileNumber = view.findViewById(R.id.tv_mobile_number);
        getProfileInfo();
        imgDP =view.findViewById(R.id.img_dp);
        if(currentUser.getPhotoUrl()!=null){
            Picasso.get().load(currentUser.getPhotoUrl()).fit().centerCrop().into(imgDP);
        }
        return view;

    }

    private void getProfileInfo(){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userid=user.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                @SuppressLint({"NewApi", "LocalSuppress"}) String name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                @SuppressLint({"NewApi", "LocalSuppress"}) String number = Objects.requireNonNull(dataSnapshot.child("number").getValue()).toString();
                tvName.setText(name);
                tvMobileNumber.setText("+92"+number);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    /**
     * This Method is For Openning Gallery and other type of apps which contains images
     */
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    /**
     * This Method checks the Permission of getting Access to Images from the memory of the Phone
     *
     */
    @SuppressLint("NewApi")
    private void checkAndAskForPermission() {
    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE)!=
            PackageManager.PERMISSION_GRANTED){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(getContext(), "Allow Read Pictures Permission from Settings", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PREQCODE);
        }
    }
    else{
        openGallery();
    }
    }


    private Uri selectedImageUri;
    /**
     * This is Build in Method when it get any response from the user then this method will run
     * like when we select any image then what should happen after selection of the image,
     * like that
     * @param requestCode //
     * @param resultCode //
     * @param data //
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                selectedImageUri = data.getData();
                updateProfilePicture();
                // original code
//                String selectedImagePath = getPath(selectedImageUri);
//                selectedImagePreview.setImageURI(selectedImageUri);
            }

        } else {
            // report failure
            Toast.makeText(getContext(), "You Have Not Selected Any Image!!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This Method is for updating User photo in the Firebase
     */
    private void updateProfilePicture(){
        progressBar.setVisibility(View.VISIBLE);
        assert currentUser != null;
        String userid=currentUser.getUid();
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos/"+userid);
        @SuppressLint({"NewApi", "LocalSuppress"}) final StorageReference imageFilePath = mStorage.child(Objects.requireNonNull(selectedImageUri.getLastPathSegment()));
        if(selectedImageUri!=null){
            imageFilePath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //If Image is uploaded Successfully

                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()

                                    .setPhotoUri(uri)
                                    .build();
                            assert currentUser != null;
                            currentUser.updateProfile(userProfileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Picasso.get().load(selectedImageUri).fit().centerCrop().into(imgDP);
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                    });


                }
            });
        }

    }


}
