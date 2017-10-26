package com.chinavvv.plugin;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class MultiImageSelect extends AppCompatActivity {
  private int DEFAULT_MAX_IMAGES = 5;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent multiImageIntent = getIntent();
    Integer maxImages = multiImageIntent.getIntExtra("maxImages", DEFAULT_MAX_IMAGES);
    String images = multiImageIntent.getStringExtra("images");


    ArrayList<String> imageList = new ArrayList<String>();

    if(images!=null && images.length()>0 && images.split(",").length>0){
      for(String path : images.split(",")){
        imageList.add(path);
      }
    }
    if(imageList.size()>0){
      int current = multiImageIntent.getIntExtra("current",0);
      PhotoPreview.builder()
        .setCurrentItem(current)
        .setPhotos(imageList)
        .setShowDeleteButton(false)
        .start(this,PhotoPreview.REQUEST_CODE);

    }else{
      PhotoPicker.builder()
        .setPhotoCount(maxImages)
        .setShowCamera(true)
        .setPreviewEnabled(false)
        .start(this, PhotoPicker.REQUEST_CODE);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    Intent data;
    Bundle res;

    if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
      ArrayList<String> photos =
        intent.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

      data = new Intent();
      res = new Bundle();

      res.putInt("SELECTED", photos.size());
      res.putStringArrayList("PHOTOS", photos);
      data.putExtras(res);
      setResult(RESULT_OK, data);
      finish();
    } else {
      data = new Intent();
      res = new Bundle();
      res.putString("MESSAGE", "No images selected");
      data.putExtras(res);
      setResult(RESULT_CANCELED, data);
      finish();
    }
  }
}
