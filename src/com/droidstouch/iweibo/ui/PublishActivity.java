package com.droidstouch.iweibo.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.util.myedit_util;
import com.droidstouch.iweibo.util.Base64Coder;
import com.droidstouch.iweibo.util.resizeImage;

public class PublishActivity extends Activity {
	private String filename;

	private Button publish_bt = null;
	private Button write_bt_addphoto1;
	private Button write_bt_addphoto2;
	private myedit_util e;
	private myedit_util abc;
	private Bitmap currentBitmap = null;
	private Bitmap currentBitmap2 = null;
	private ProgressDialog pd;
	private static final int PHOTO_SUCCESS = 1;
	private static final int CAMERA_SUCCESS = 2;
	private static final int PHOTO_SUCCESS2 = 3;
	private static final int CAMERA_SUCCESS2 = 4;

	private String picstring = null;
	private String picstring2 = null;
	private Button write_bt_backone;
	private EditText leaderId_ed=null;
	private EditText leaderPhone_ed=null;
	private EditText itemDescription_ed=null;
	private String itemTitle2 = null;
	private String itemIntro2 = null;
	private String sort11,sort22;
	
	
	Intent intent;

	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.publish2);
		
		
		
		
		intent=this.getIntent();
		bundle=intent.getExtras();
		itemTitle2= bundle.getString("itemName");
		itemIntro2=bundle.getString("itemIntro");
		sort11=bundle.getString("sort11");
		sort22=bundle.getString("sort22");
		
		
		
		
		
		
		
		e = (myedit_util) findViewById(R.id.editdream);
		abc = (myedit_util) findViewById(R.id.editdream2);
		
	    
		pd = new ProgressDialog(this);
		pd.setTitle("请稍候");
		pd.setMessage("梦想正在送达...");

		
		
		
		//后退按钮
		
		write_bt_backone = (Button) findViewById(R.id.write_bt_backone);
		write_bt_backone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				

				Intent intent = new Intent(PublishActivity.this,
						PublishActivity2.class);
				System.out.println("后退按钮");
				startActivity(intent);
				PublishActivity.this.finish();

			}
		});

		leaderId_ed = (EditText) findViewById(R.id.leaderId);

		leaderPhone_ed = (EditText) findViewById(R.id.leaderPhone);
        write_bt_addphoto1=(Button)findViewById(R.id.write_bt_addphoto1);
		write_bt_addphoto1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "相  册", "相  机" };
				AlertDialog dlg = new AlertDialog.Builder(PublishActivity.this)
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
		
		write_bt_addphoto2=(Button)findViewById(R.id.write_addphoto2);
		write_bt_addphoto2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "相  册", "相  机" };
				AlertDialog dlg = new AlertDialog.Builder(PublishActivity.this)
						.setTitle("图 片")
						.setItems(items, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int item) {

								if (item ==1) {

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
											CAMERA_SUCCESS2);
								}
								else{
									Intent getImage = new Intent(
											Intent.ACTION_GET_CONTENT);
									getImage.addCategory(Intent.CATEGORY_OPENABLE);
									getImage.setType("image/*");
									startActivityForResult(getImage,
											PHOTO_SUCCESS2);
								}
							}
						}).create();
				dlg.show();
			}

		});

		
		
		
		//发送服务器
		
		publish_bt = (Button) findViewById(R.id.publish_btn);
		publish_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (e.getText().toString().equals("")) {
					Toast.makeText(PublishActivity.this, "梦想还是要有的",
							Toast.LENGTH_LONG).show();
				} else {

					new Thread(new Runnable() {

						@Override
						public void run() {
							System.out.println("传送中");
							String urlpath = "http://dreamerx.aliapp.com/servlet/LaunchItemAction";

							leaderId_ed=(EditText)findViewById(R.id.leaderId);
							leaderPhone_ed=(EditText)findViewById(R.id.leaderPhone);
							itemDescription_ed=(EditText)findViewById(R.id.itemDescription);
							String leaderId = leaderId_ed.getText().toString();
							String leaderPhone = leaderPhone_ed.getText().toString();
							String itemDescription = itemDescription_ed.getText()
									.toString();
							System.out.println("传送中2");
							String itemTitle = itemTitle2;
							System.out.println("传送中3");
							String itemIntro = itemIntro2;
							System.out.println("传送中4");
							picstring = picturetoString(currentBitmap);
							System.out.println("传送中5");
							picstring2 = picturetoString(currentBitmap2);

							System.out.println("传送中6");
							
							String sort1=sort11;
							String sort2=sort22;
							sendtozhengwei(urlpath, itemTitle, itemIntro,
									itemDescription, picstring, picstring2,
									leaderId, leaderPhone,sort1,sort2);
							System.out.println("传送中7");

						}
					}).start();
					//pd.show();
				}
			}
		});
	}
	
	
	

	// 获取照片

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		ContentResolver resolver = getContentResolver();
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PHOTO_SUCCESS:
				Uri originalUri = intent.getData();
				Bitmap bitmap = null;
				try {
					Bitmap originalBitmap = BitmapFactory.decodeStream(resolver
							.openInputStream(originalUri));
					bitmap = resizeImage.ResizeImage(originalBitmap, 130, 90);// 缩放图片
					if (bitmap != null) {
						ImageSpan imageSpan = new ImageSpan(
								PublishActivity.this, bitmap);
						SpannableString spannableString = new SpannableString(
								"                          ");
						spannableString.setSpan(imageSpan, "111  ".length(),
								"                          ".length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						int index = e.getSelectionStart();
						Editable edit_text = e.getEditableText();
						if (index < 0 || index >= edit_text.length()) {
							edit_text.append(spannableString);
							currentBitmap = bitmap;
							// PictoStrThread();
						} else {
							edit_text.insert(index, spannableString);
							currentBitmap = bitmap;
							// PictoStrThread();
						}
					} else {
						Toast.makeText(PublishActivity.this, "获取图片失败",
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
					bitmap = resizeImage.ResizeImage(originalBitmap, 130, 90);
					ImageSpan imageSpan = new ImageSpan(PublishActivity.this,
							bitmap);
					SpannableString spannableString = new SpannableString(
							"   [local]1[local]   ");
					spannableString.setSpan(imageSpan, 0,
							"   [local]1[local]   ".length(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					int index = e.getSelectionStart();
					Editable edit_text = e.getEditableText();
					if (index < 0 || index >= edit_text.length()) {
						edit_text.append(spannableString);
						currentBitmap = originalBitmap;
					} else {
						edit_text.insert(index, spannableString);
						currentBitmap = bitmap;
					}
				} else {
					showToast("获取图片失败");
				}

			case PHOTO_SUCCESS2:
				Uri originalUri2 = intent.getData();
				Bitmap bitmap2 = null;
				try {
					Bitmap originalBitmap2 = BitmapFactory
							.decodeStream(resolver
									.openInputStream(originalUri2));
					bitmap2 = resizeImage.ResizeImage(originalBitmap2, 130, 90);// 缩放图片
					if (bitmap2 != null) {
						ImageSpan imageSpan = new ImageSpan(
								PublishActivity.this, bitmap2);
						SpannableString spannableString = new SpannableString(
								"                          ");
						spannableString.setSpan(imageSpan, "111  ".length(),
								"                          ".length(),
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						int index = abc.getSelectionStart();
						Editable edit_text = abc.getEditableText();
						if (index < 0 || index >= edit_text.length()) {
							edit_text.append(spannableString);
							currentBitmap2 = bitmap2;
							// PictoStrThread();
						} else {
							edit_text.insert(index, spannableString);
							currentBitmap2 = bitmap2;
							// PictoStrThread();
						}
					} else {
						Toast.makeText(PublishActivity.this, "获取图片失败",
								Toast.LENGTH_LONG).show();
					}
					break;
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
			case CAMERA_SUCCESS2:

				Bitmap originalBitmap2 = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory().getPath()
						+ "/"
						+ filename);
				if (originalBitmap2 != null) {
					bitmap2 = resizeImage.ResizeImage(originalBitmap2, 130, 90);
					ImageSpan imageSpan = new ImageSpan(PublishActivity.this,
							bitmap2);
					SpannableString spannableString = new SpannableString(
							"   [local]1[local]   ");
					spannableString.setSpan(imageSpan, 0,
							"   [local]1[local]   ".length(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					int index = abc.getSelectionStart();
					Editable edit_text = abc.getEditableText();
					if (index < 0 || index >= edit_text.length()) {
						edit_text.append(spannableString);
						currentBitmap2 = originalBitmap2;
					} else {
						edit_text.insert(index, spannableString);
						currentBitmap2 = bitmap2;
					}
				} else {
					showToast("获取图片失败");
				}
				break;
			default:
				break;

			}
		}

	}

	/*
	 * 图片转换成字符串
	 */
	private String picturetoString(Bitmap originalBitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		originalBitmap.compress(CompressFormat.PNG, 30, bStream);
		byte[] bytes = bStream.toByteArray();
		string = new String(Base64Coder.encodeLines(bytes));
		return string;
	}

	/*
	 * 向服务器发送数据
	 */
	public void sendtozhengwei(String urlpath, String itemTitle,
			String itemIntro, String itemDescription, String imageString,
			String imageString2, String leaderId, String leaderPhone,String sort1,String sort2) {
		Message message = Message.obtain();
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		HttpPost httppost = new HttpPost(urlpath);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("itemTitle", itemTitle));
		nameValuePairs.add(new BasicNameValuePair("itemIntro", itemIntro));
		nameValuePairs.add(new BasicNameValuePair("itemDescription",
				itemDescription));
		nameValuePairs.add(new BasicNameValuePair("imageString", imageString));
		nameValuePairs
				.add(new BasicNameValuePair("imageString2", imageString2));
		nameValuePairs.add(new BasicNameValuePair("leaderId", leaderId));
		
		nameValuePairs.add(new BasicNameValuePair("sort1", sort1));
		nameValuePairs.add(new BasicNameValuePair("sort2", sort2));
		nameValuePairs.add(new BasicNameValuePair("leaderPhone", leaderPhone));
		nameValuePairs.add(new BasicNameValuePair("username",
				LoginActivity.user.getUsername()));

		try {

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			response = httpClient.execute(httppost);
			// 连接超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			// 请求超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 3000);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				if (result.equals("successLaunch")) {
					message.what = 1;
					myHandler.sendMessage(message);
				} else {
					message.what = 2;
					myHandler.sendMessage(message);
				}

			} else {
				message.what = 2;
				myHandler.sendMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// 弹出提示信息
	public void showToast(String msg) {
		Toast.makeText(PublishActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				e.setText("");
				pd.dismiss();
				showToast("发送成功");

			} else if (msg.what == 2) {
				pd.dismiss();
				showToast("请检查您的网络");
			}
		};
	};

}
