package com.example.hossein.quiztest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import teg.com.arusime.AddNewAlbum;
import teg.com.arusime.P4_CaseInSite;
import teg.com.arusime.PACKAlbumView;

public class PACKListViewAdapter extends BaseAdapter {

	// Declare Variables

	private ProgressDialog pDialog;

	String UserName;
	String Password;
	String Token;
	int Status;

	TextView CaseTitle;
	TextView CaseID;
	TextView CaseDetail;
	TextView LikeCounter;
	TextView Date;
	TextView CaseApproved;
	JSONParser jParser = new JSONParser();
	ImageView CaseImg;
	Context context;

	Button Edit;
	Button Delete;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	PACKImageLoader imageLoader;
	String Test;
	private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

	HashMap<String, String> resultp = new HashMap<String, String>();

	public String UrlDeleteAlbum;

	public PACKListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		Getinfo();

		data = arraylist;
		UrlDeleteAlbum = "http://www.arusime.com/api/BusinessApplication/DeleteBusinessProject/"
				+ Token;
		imageLoader = new PACKImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.p4_caseinsite_listview,
				parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		CaseTitle = (TextView) itemView.findViewById(R.id.CaseTitle);
		CaseID = (TextView) itemView.findViewById(R.id.CaseID);
		CaseDetail = (TextView) itemView.findViewById(R.id.CaseDetail);
		LikeCounter = (TextView) itemView.findViewById(R.id.LikeCounter);
		Date = (TextView) itemView.findViewById(R.id.Date);
		CaseApproved = (TextView) itemView.findViewById(R.id.CaseApproved);

		Edit = (Button) itemView.findViewById(R.id.Editbtn);
		Delete = (Button) itemView.findViewById(R.id.Deletebtn);

		// Locate the ImageView in listview_item.xml
		CaseImg = (ImageView) itemView.findViewById(R.id.CaseImg);

		// Capture position and set results to the TextViews
		CaseTitle.setText(resultp.get(P4_CaseInSite.CaseTitle));
		Log.d("CaseTitle", resultp.get(P4_CaseInSite.LikeCounter));
		CaseID.setText(resultp.get(P4_CaseInSite.CaseID));
		CaseDetail.setText(resultp.get(P4_CaseInSite.CaseDetail).toString());

		CaseApproved.setText(resultp.get(P4_CaseInSite.Approved));
		Date.setText(resultp.get(P4_CaseInSite.Date));
		LikeCounter.setText(resultp.get(P4_CaseInSite.LikeCounter));

		Test = Uri.encode(resultp.get(P4_CaseInSite.FLAG), ALLOWED_URI_CHARS);
		Log.d("test", Test);
		imageLoader.DisplayImage(Test, CaseImg);

		Delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				resultp = data.get(position);
				new AlertDialog.Builder(context)
						.setTitle("حذف آلبوم !!!")
						.setMessage("آیا میخواهید این آلبوم را حذف کنید ؟")
						.setPositiveButton("بله",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										// continue with delete
										new DeleteAlbum().execute();

										Intent intent = new Intent(context,
												P4_CaseInSite.class);
										context.startActivity(intent);

									}
								})
						.setNegativeButton("خیر",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
				;

				Log.d("resultp",
						resultp.get(teg.com.arusime.P4_CaseInSite.CaseID));

			}
		});
		Edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				resultp = data.get(position);

				Intent intent = new Intent(context, AddNewAlbum.class);
				intent.putExtra("AlbumID", resultp.get(P4_CaseInSite.CaseID));
				intent.putExtra("AlbumTitle",
						resultp.get(P4_CaseInSite.CaseTitle));
				intent.putExtra("AlbumDetail",
						resultp.get(P4_CaseInSite.CaseDetail));
				intent.putExtra("AlbumApproved",
						resultp.get(P4_CaseInSite.Approved));
				intent.putExtra("AlbumLikeCounter",
						resultp.get(P4_CaseInSite.LikeCounter));
				Test = Uri.encode(resultp.get(P4_CaseInSite.FLAG),
						ALLOWED_URI_CHARS);
				intent.putExtra("AlbumImage", Test);
				((Activity) context).finish();
				context.startActivity(intent);
				

			}
		});

		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				// sending pid to next activity

				Intent intent = new Intent(context, PACKAlbumView.class);
				// Pass all data rank
				intent.putExtra("CaseID",
						resultp.get(teg.com.arusime.P4_CaseInSite.CaseID));
				// Pass all data country
				intent.putExtra("CaseDetail",
						resultp.get(teg.com.arusime.P4_CaseInSite.CaseDetail));
				// Pass all data population
				intent.putExtra("CaseTitle",
						resultp.get(teg.com.arusime.P4_CaseInSite.CaseTitle));
				// Pass all data flag
				intent.putExtra("flag",
						resultp.get(teg.com.arusime.P4_CaseInSite.FLAG));
				Log.d("flag", resultp.get(teg.com.arusime.P4_CaseInSite.FLAG));
				intent.putExtra("LikeCounter",
						resultp.get(teg.com.arusime.P4_CaseInSite.LikeCounter));

				intent.putExtra("Date",
						resultp.get(teg.com.arusime.P4_CaseInSite.Date));
				intent.putExtra("Approved",
						resultp.get(teg.com.arusime.P4_CaseInSite.Approved));

				// Start PACKAlbumView Class
				context.startActivity(intent);

			}
		});
		return itemView;
	}

	class DeleteAlbum extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("درحال ارسال درخواست...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("ID", resultp
					.get(teg.com.arusime.P4_CaseInSite.CaseID)));
			// HashMap<String, String> map = new HashMap<String, String>();
			// .put("ID", CaseID);

			// params.add(new BasicNameValuePair("project", Temp.toString()));
			Log.d("jsonME", params.toString());
			JSONObject json = jParser.makeHttpRequest(UrlDeleteAlbum, "POST",
					params);
			Status = jParser.httpResponse.getStatusLine().getStatusCode();
			try {
				if (Status == 200) {
					Log.d("StatooooooooooooS", "success");

				}
			} catch (Exception e) {
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();

			if (Status == 200) {

			}

		}

	}

	void Getinfo() {

		DbHelper mDbHelper = new DbHelper(context);
		SQLiteDatabase mDb = null;
		try {
			Log.d("test1", "success");
			mDbHelper.createDataBase();
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String sql = "select * FROM UserInfo ";

			Log.d("test2", "success");

			Cursor c = mDb.rawQuery(sql, null);
			if (c != null) {
				while (c.moveToNext()) {

					/*
					 * ManagerFirstName = c.getString(6); ManagerLastName =
					 * c.getString(5); BusinessUnitName = c.getString(4);
					 */
					Token = c.getString(3);

					UserName = c.getString(2);
					Password = c.getString(1);
					// ID= c.getInt(0);

				}
			}
		} catch (SQLException mSQLException) {
			throw mSQLException;
		}

	}

}
