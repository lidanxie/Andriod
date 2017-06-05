package com.example.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.FriendAdapter;
import com.example.common.MyURL;
import com.example.entity.Friend;
import com.example.mychat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class FrdFragment extends Fragment {
	private ListView listView;
	private LinearLayout newfriend;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private SharedPreferences sp;
	private TextView returnview;
	private Button findnewfriend_button;
	private ListView newfriendlist;
	private EditText findfriend_textview;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//�������ǵĲ���
		View v = inflater.inflate(R.layout.friend, container, false);
		return v;
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		context = this.getActivity().getApplicationContext();
		listView=(ListView) view.findViewById(R.id.friends);
		newfriend = (LinearLayout) view.findViewById(R.id.newfriend);
		layout1 = (LinearLayout) view.findViewById(R.id.include1);  
		layout2 = (LinearLayout) view.findViewById(R.id.include2);
		LayoutInflater inflater =  LayoutInflater.from(this.getActivity());
		returnview = (TextView) this.getActivity().findViewById(R.id.returnview);
		findnewfriend_button = (Button) view.findViewById(R.id.findnewfriend_button);
		newfriendlist = (ListView) view.findViewById(R.id.newfriendlist);
		findfriend_textview = (EditText) view.findViewById(R.id.findfriend_textview);
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		
		AsyncHttpClient client = new AsyncHttpClient();
		   RequestParams params = new RequestParams();
		   String userid = sp.getString("USERID", "");
		   params.put("userid", userid);
		   client.post(MyURL.FriendsURL, params, new JsonHttpResponseHandler(){
			   ArrayList<Friend>  result = new ArrayList<Friend>();
			   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				   try {
					    int returnCode =  (Integer) response.get("returnCode");
					    if (returnCode == 1) {
					    	JSONArray jsonArray = (JSONArray) response.get("data");
						    for(int i=0;i<jsonArray.length();i++){
						    	JSONObject ob = (JSONObject) jsonArray.get(i);
						    	Friend f = new Friend();
						    	f.setImg(R.drawable.tab_find_frd_normal);
						    	if (ob.has("frename")) {
						    		f.setName(ob.getString("frename"));
						    	} else {
						    		f.setName(ob.getString("username"));
						    	}
						        result.add(f);
						    }
					    }
					    FriendAdapter adapter = new FriendAdapter(context,result);
						listView.setAdapter(adapter);  
				} catch (JSONException e) {
					e.printStackTrace();
				}
				};
			});
		
		newfriend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {			
				layout1.setVisibility(View.GONE);  
				layout2.setVisibility(View.VISIBLE);  
				returnview.setVisibility(View.VISIBLE);
				
			}
		});
		returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {			
				layout1.setVisibility(View.VISIBLE);  
				layout2.setVisibility(View.GONE);  
				returnview.setVisibility(View.GONE);
				
			}
		});
		
		findnewfriend_button.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {	
				
				String findName = findfriend_textview.getText().toString();
				//û�����������Ϣ
				if(findName.isEmpty()||findName==null||findName==""){
					Toast.makeText(context, "��������Ϣ",
						     Toast.LENGTH_SHORT).show();
				}else{
					  
					AsyncHttpClient client = new AsyncHttpClient();
					   RequestParams params = new RequestParams();
					   String userid = sp.getString("USERID", "");
					   params.put("userid", userid);
					   params.put("searchname", findName);
					   client.post(MyURL.FindfriendURL, params, new JsonHttpResponseHandler(){
						   ArrayList<Friend>  result = new ArrayList<Friend>();
						   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
							   try {
								    int returnCode =  (Integer) response.get("returnCode");
								    
								    if (returnCode == 1) {
								    	String user = response.getString("data");
									    Friend f = new Friend();
									    f.setImg(R.drawable.tab_find_frd_normal);
									    f.setName(user);
									    result.add(f);
								    } else {
								    	Toast.makeText(context, "δ�������κν��",
											     Toast.LENGTH_SHORT).show();
								    }
								    FriendAdapter adapter = new FriendAdapter(context,result);
									newfriendlist.setAdapter(adapter);  
							   } catch (JSONException e) {
								   e.printStackTrace();
							   }
							};
						});
				}
				 
			}
		});	
	}
	   
}