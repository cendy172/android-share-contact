package com.example.businesscardholder;

import java.util.List;

import com.example.businesscardholder.ContactAdapter;
import com.example.businesscardholder.ContactGenerator;
import com.example.businesscardholder.ContactItem;
import com.example.businesscardholder.LocalActivity;
import com.example.businesscardholder.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;


public class LocalActivity extends Activity {
		private ExpandableListView contactView;
//		private Button newBtn;
//		private Button dialBtn;
		
		private ContactGenerator generator = null;
		
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.local_activity);
	        
	        contactView = (ExpandableListView) findViewById(R.id.contactView);
	        //newBtn = (Button) findViewById(R.id.newBtn);
	        //dialBtn = (Button) findViewById(R.id.dialBtn);
	        
	        generator = new ContactGenerator(this);
	        
	        List<ContactItem> list = generator.generateList();
	        ContactAdapter adapter = new ContactAdapter(list);
	        contactView.setAdapter(adapter);
	        contactView.setGroupIndicator(null);
	        
	        /*
	        dialBtn.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_DIAL);
					startActivity(intent);
				}
	        });
	        
	        newBtn.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, CreateActivity.class);
					startActivity(intent);
				}
	        }); 
	        */
	    }

		@Override
		protected void onRestart() {
			super.onRestart();
			
	        contactView.setAdapter(new ContactAdapter(generator.generateList()));
		}
	}