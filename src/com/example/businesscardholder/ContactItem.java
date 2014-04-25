package com.example.businesscardholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;

import static android.view.LayoutInflater.from;
import static com.example.businesscardholder.R.layout.list_btn;
import static com.example.businesscardholder.R.layout.list_text;

public class ContactItem
{
	private String name;
	private String phone;
	private Bitmap head;
	private long contactId;

	private Context context;

	public static final int TYPE_NAME = 0;
	public static final int TYPE_PHONE = 1;
	public static final int TYPE_BUTTON = 2;
	public static final int TYPE_CONTACT_ID = 3;
	private String lookupKey;

	public ContactItem(Context context, String name, String phone, Bitmap head)
	{
		this.context = context;
		this.name = name;
		this.phone = phone;
		this.head = head;
	}

	public ContactItem(Context context, String name, String phone, Bitmap head, String lookupKey)
	{
		this(context, name, phone, null);
		this.lookupKey = lookupKey;
	}

	public View generateView(int typeId)
	{
		View view = null;

		switch (typeId)
		{
		case TYPE_NAME:
			view = getGroupView();
			break;
		case TYPE_PHONE:
			view = getTextView(phone);
			break;
		case TYPE_BUTTON:
			view = getButtonView();
			break;
		}
		return view;
	}

	private View getGroupView()
	{
		View view = from(context).inflate(R.layout.list_head, null);
		TextView txt = (TextView) view.findViewById(R.id.nameTxt);
		txt.setText(name);
		/*
		 * if (head != null) { ImageView headImg = (ImageView) view.findViewById(R.id.headImg);
		 * headImg.setImageBitmap(head); }
		 */
		return view;
	}

	private View getTextView(String text)
	{
		View view = from(context).inflate(list_text, null);
		TextView txt = (TextView) view.findViewById(R.id.listTxt);
		txt.setText(text);
		return view;
	}

	private View getButtonView()
	{
		View view = from(context).inflate(list_btn, null);
		Button emailBtn = (Button) view.findViewById(R.id.emailBtn);
		Button bluetoothBtn = (Button) view.findViewById(R.id.bluetoothBtn);
		Button delBtn = (Button) view.findViewById(R.id.delBtn);

		emailBtn.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				Uri personUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
                File vCard = getVCard(personUri);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(vCard));
				intent.putExtra(Intent.EXTRA_SUBJECT, name);

				context.startActivity(Intent.createChooser(intent, "send contact"));
                vCard.deleteOnExit();
			}
		});
		bluetoothBtn.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				// Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
				// context.startActivity(intent);
			}
		});
		delBtn.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				// ContactHandler handler = new ContactHandler(context);
				// handler.delete(ContactItem.this);

				Intent intent = new Intent(context, MainActivity.class);
				context.startActivity(intent);
				((Activity) context).finish();
			}
		});
		return view;
	}

	private File getVCard(Uri uri)
	{
		String storage_path = Environment.getExternalStorageDirectory().toString() + "/" + name + ".vcf";
		File file = new File(storage_path);
		try
		{
			AssetFileDescriptor fd = context.getContentResolver().openAssetFileDescriptor(uri, "r");
			FileInputStream fis = fd.createInputStream();
			byte[] buf = new byte[(int) fd.getDeclaredLength()];
			if (0 < fis.read(buf))
			{
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
				outputStreamWriter.write(new String(buf));
				outputStreamWriter.flush();
				outputStreamWriter.close();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return file;
	}

	public String getValue(int typeId)
	{
		String value = null;

		switch (typeId)
		{
		case TYPE_NAME:
			value = name;
			break;
		case TYPE_PHONE:
			value = phone;
			break;
		case TYPE_BUTTON:
			value = "button";
			break;
		case TYPE_CONTACT_ID:
			value = "" + contactId;
			break;
		}

		return value;
	}

	public void setContactId(long contactId)
	{
		this.contactId = contactId;
	}
}
