package com.chinavvv.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import org.apache.cordova.PermissionHelper;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ValleyPhotoView extends CordovaPlugin {
  private CallbackContext callbackContext;
  private int quality;
  String [] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE };

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    this.callbackContext = callbackContext;

    if (action.equals("selectPhotos")) {
      Intent intent = new Intent(cordova.getActivity(), MultiImageSelect.class);

      //Take the values from the arguments
      intent.putExtra("maxImages", args.getInt(0));
      this.quality = args.getInt(1);

      cordova.startActivityForResult(this, intent, 0);
    } else if (action.equals("showPhotos")) {
      Intent intent = new Intent(cordova.getActivity(), MultiImageSelect.class);
      intent.putExtra("images", args.getString(0));
      intent.putExtra("current", args.getInt(1));
      cordova.startActivityForResult(this, intent, 0);
    }else if (action.equals("requestPermission")) {
      if(!hasPermisssion()) {
        PermissionHelper.requestPermissions(this, 100, permissions);
      }
    }
    return true;
  }

  public boolean hasPermisssion() {
    for(String p : permissions)
    {
      if(!PermissionHelper.hasPermission(this, p))
      {
        return false;
      }
    }
    return true;
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK && data != null) {
      final ArrayList<String> photos = data.getStringArrayListExtra("PHOTOS");
      final ArrayList<String> base64Array = new ArrayList<String>();

      cordova.getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          for (String photo : photos) {
            Uri uri = Uri.parse(photo);
            base64Array.add(convertToBase64(uri));
          }

          JSONArray res = new JSONArray(base64Array);

          callbackContext.success(res);
        }
      });
    } else if (resultCode == Activity.RESULT_CANCELED && data != null) {
      String error = data.getStringExtra("MESSAGE");
      this.callbackContext.error(error);
    } else {
      this.callbackContext.error("Unplanned event");
    }
  }

  public void onRequestPermissionResult(int requestCode, String[] permissions,
                                        int[] grantResults) throws JSONException
  {
    PluginResult result;
    if(callbackContext != null) {
      for (int r : grantResults) {
        if (r == PackageManager.PERMISSION_DENIED) {
          result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
          callbackContext.sendPluginResult(result);
          return;
        }

      }
    }
  }

  private String convertToBase64(Uri uri) {
    Bitmap bm = BitmapFactory.decodeFile(String.valueOf(uri));
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    // Log.d("Quality of compression", String.valueOf(this.quality));
    // Log.d("OWidth >>>", String.valueOf(bm.getWidth()));
    // Log.d("OHeight >>>", String.valueOf(bm.getHeight()));

    int nh = (int) (bm.getHeight() * (570.0 / bm.getWidth()));
    Bitmap scaled = Bitmap.createScaledBitmap(bm, 570, nh, true);

    // Log.d("NWidth >>>", String.valueOf(scaled.getWidth()));
    // Log.d("NHeight >>>", String.valueOf(scaled.getHeight()));

    scaled.compress(Bitmap.CompressFormat.JPEG, 95, stream); //bm is the bitmap object

    byte[] byteArrayImage = stream.toByteArray();
    long imageLength = byteArrayImage.length / 1024;

    // Log.d("ImageSize >>> ", String.valueOf(imageLength));

    String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

    // Log.d("encodedImage.length()", String.valueOf(encodedImage.length()));

    return encodedImage;
  }
}
