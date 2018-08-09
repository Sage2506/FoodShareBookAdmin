package com.example.sage.foodsharebook.Tests;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.sage.foodsharebook.R;
import com.example.sage.foodsharebook.adapters.MeasuresListAdapter;
import com.example.sage.foodsharebook.apiFoodShareBookServices.ApiRetrofit;
import com.example.sage.foodsharebook.models.Ingredient;
import com.example.sage.foodsharebook.models.IngredientMeasureResponse;
import com.example.sage.foodsharebook.models.IngredientResponse;
import com.example.sage.foodsharebook.models.Measure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateIngredient extends AppCompatActivity {
    private MeasuresListAdapter mAdapter;
    private static int RESULT_LOAD_IMG = 1;
    private final String TAG = "CreateIngredient";
    private String imgDecodableString;
    private String imageURL;
    private RecyclerView mRecyclerView;
    private EditText name, description;
    private Button submit;
    private ImageButton selectImage;
    private ApiRetrofit api;
    private uploadImage uploadServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredient);
        name = findViewById(R.id.et_name);
        description = findViewById(R.id.et_desc);
        submit= findViewById(R.id.btn_send);
        selectImage = findViewById(R.id.ibtn_ingredient);
        mRecyclerView = findViewById(R.id.rv_measures);

        api = new ApiRetrofit(this);
        uploadServ = new uploadImage();

        mAdapter = new MeasuresListAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CreateIngredient.this));
        mRecyclerView.setAdapter(mAdapter);
        api.getMeasures(mAdapter);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(v);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                enableDisableInputs();
                createIngredient();
            }
        });
    }

    public void loadImageFromGallery(View view){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = findViewById(R.id.ibtn_ingredient );
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG) .show();
        }
    }

    private void createIngredient(){
        api.postIngredient(name.getText().toString().trim(), description.getText().toString().trim(),"", new ApiRetrofit.IngredientCallBack() {
            @Override
            public void response(Boolean bool, IngredientResponse ingredient) {
                if(bool){
                    Log.i(TAG, "Se creó el ingrediente");
                    uploadServ.setIngredient(ingredient);
                    uploadServ.execute();
                    int ingredientId = ingredient.getId();
                    List<Measure> dataset = mAdapter.allSelected();
                    final int idLast = dataset.get(mAdapter.allSelectedCount()-1).getId();
                    for( Measure measure : dataset){
                        Log.i(TAG,measure.toString());
                        api.postIngredientMeasure(ingredientId, measure.getId(), new ApiRetrofit.IngredientMeasureCallBack() {
                            @Override
                            public void response(Boolean bool, IngredientMeasureResponse ingredientMeasure) {
                                if(ingredientMeasure.getMeasure().getId() == idLast){
                                    Log.i(TAG, "Se crearon las relaciones");
                                    clearFields();
                                    Toast.makeText(getApplicationContext(), "Ingrediente agregado exitosamente", Toast.LENGTH_SHORT).show();
                                    enableDisableInputs();
                                }
                            }
                        });
                    }
                }
                else {
                    enableDisableInputs();
                    Toast.makeText(getApplicationContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class uploadImage extends AsyncTask<Void, Void, Void> {
        private Cloudinary cloudinary;
        private IngredientResponse ingredient;

        public uploadImage() {
            super();
            Map config = new HashMap();
            config.put("cloud_name","dbo96sjb");
            config.put("api_key","757447362712211");
            config.put("api_secret","z_F0g_ccUUJG24DDJJjyNdjl0RM");
            this.cloudinary = new Cloudinary(config);
        }

        public void setIngredient(IngredientResponse ingredient){
            this.ingredient = ingredient;
        }

        protected Void doInBackground(Void... params) {
            try {
               Map response = cloudinary.uploader().upload(imgDecodableString, ObjectUtils.asMap("folder","ingredients"));
               imageURL = response.get("url").toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Actualizar la imagen
            Log.i(TAG, "Imagen subida");
            Ingredient toUpdate = new Ingredient(ingredient);
            toUpdate.setImage(imageURL);
            api.putIngredient(ingredient.getId(), toUpdate, new ApiRetrofit.IngredientCallBack() {
                @Override
                public void response(Boolean bool, IngredientResponse ingredient) {
                    if(bool) {
                        Log.i(TAG, "URL de imagen agregada");
                    }
                }
            });
        }
    }

    public void clearFields(){
        name.setText("");
        description.setText("");
        imgDecodableString = "";
        selectImage.setImageResource(android.R.color.transparent);
        mAdapter.clearSelection();
    }

    private void enableDisableInputs(){
        name.setEnabled(!name.isEnabled());
        description.setEnabled(!description.isEnabled());
        selectImage.setEnabled(!selectImage.isEnabled());
        mAdapter.switchEnable();
        submit.setEnabled(!submit.isEnabled());
        mRecyclerView.setLayoutFrozen(!mRecyclerView.isLayoutFrozen());
    }

}
