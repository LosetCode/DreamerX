package com.droidstouch.iweibo.ui;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.util.resizeImage;

public class UserActivity extends Activity {
	private String filename;
	private ImageView userphoto;
	private static final int PHOTO_SUCCESS = 1;
	private static final int CAMERA_SUCCESS = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_information);
		userphoto = (ImageView)findViewById(R.id.userpic);
		userphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "相  册", "相  机" };
				AlertDialog dlg = new AlertDialog.Builder(UserActivity.this)
						.setTitle("图 片")
						.setItems(items, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int item) {

								if (item == 1) {

									Intent getImageByCamera = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									filename = "DREAMERX"
											+ System.currentTimeMillis()
											+ ".jpg";
									getImageByCamera.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(
													Environment
															.getExternalStorageDirectory(),
													filename)));
									startActivityForResult(getImageByCamera,
											CAMERA_SUCCESS);
								}
								else {
									Intent getImage = new Intent(
											Intent.ACTION_GET_CONTENT);
									getImage.addCategory(Intent.CATEGORY_OPENABLE);
									getImage.setType("image/*");
									startActivityForResult(getImage,
											PHOTO_SUCCESS);
								}
							}
						}).create();
				dlg.show();	
			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent){
		ContentResolver resolver = getContentResolver();
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PHOTO_SUCCESS:
				Uri originalUri = intent.getData();
				Bitmap bitmap = null;
				try {
					Bitmap originalBitmap = BitmapFactory.decodeStream(resolver
							.openInputStream(originalUri));
					bitmap = resizeImage.ResizeImage(originalBitmap, 80, 80);// 缩放图片
					if (bitmap != null) {
						userphoto.setImageBitmap(bitmap);
					} else {
						Toast.makeText(UserActivity.this, "获取图片失败",
								Toast.LENGTH_LONG).show();
					}
					break;
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
			case CAMERA_SUCCESS:

				Bitmap originalBitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory().getPath()
						+ "/"
						+ filename);
				if (originalBitmap != null) {
					bitmap = resizeImage.ResizeImage(originalBitmap, 80, 80);
					if (bitmap != null) {
						userphoto.setImageBitmap(bitmap);
					} else {
						Toast.makeText(UserActivity.this, "获取图片失败",
								Toast.LENGTH_LONG).show();
					}
				} 
			}
		}
	}
}
