package com.example.businesscardholder;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;

public class ContactHandler {
	private Context context;
	
	public ContactHandler(Context context) {
		this.context = context;
	}
	
	public void create(ContactItem item) {
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		
		long rawContactId = ContentUris.parseId(resolver.insert(RawContacts.CONTENT_URI, values));
		
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.DISPLAY_NAME, item.getValue(ContactItem.TYPE_NAME));
		resolver.insert(Data.CONTENT_URI, values);
		
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, item.getValue(ContactItem.TYPE_PHONE));
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		resolver.insert(Data.CONTENT_URI, values);
	}
	
	public void delete(ContactItem item) {
		ContentResolver resolver = context.getContentResolver();
		
		String[] args = {item.getValue(ContactItem.TYPE_CONTACT_ID)};
		
		resolver.delete(RawContacts.CONTENT_URI, Data.CONTACT_ID + "=?", args);
		resolver.delete(Data.CONTENT_URI, Data.RAW_CONTACT_ID + "=?", args);
	}
}
