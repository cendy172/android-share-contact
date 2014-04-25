package com.example.businesscardholder;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Contacts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactGenerator
{
	private Context context;

	private static final String[] CONTACT_INFO =
	{ Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID, Phone.LOOKUP_KEY};
	private static final int DISPLAY_NAME_INDEX = 0;
	private static final int PHONE_NUMBER_INDEX = 1;
	private static final int PHOTO_ID_INDEX = 2;
	private static final int CONTACT_ID_INDEX = 3;

	public ContactGenerator(Context context)
	{
		this.context = context;
	}

	public List<ContactItem> generateList()
	{
		List<ContactItem> list = new ArrayList<ContactItem>();

		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Phone.CONTENT_URI, CONTACT_INFO, null, null, CONTACT_INFO[DISPLAY_NAME_INDEX]
				+ " ASC");
		while (cursor.moveToNext())
		{
			String name = cursor.getString(DISPLAY_NAME_INDEX);
			String phone = cursor.getString(PHONE_NUMBER_INDEX);
			long photoId = cursor.getLong(PHOTO_ID_INDEX);
			long contactId = cursor.getLong(CONTACT_ID_INDEX);
            String lookupKey = cursor.getString(cursor.getColumnIndex(Contacts.LOOKUP_KEY));

            Bitmap head = null;
			if (photoId > 0)
			{
				Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
				InputStream input = Contacts.openContactPhotoInputStream(resolver, uri);
				head = BitmapFactory.decodeStream(input);
			}

			ContactItem item = new ContactItem(context, name, phone, head, lookupKey);
			item.setContactId(contactId);
			list.add(item);
		}

        cursor.close();

		return list;
	}
}
