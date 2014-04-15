package com.example.businesscardholder;

import static android.provider.ContactsContract.Data.CONTENT_URI;
import static android.view.LayoutInflater.from;
import static com.example.businesscardholder.R.layout.list_btn;
import static com.example.businesscardholder.R.layout.list_text;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

	public ContactItem(Context context, String name, String phone, Bitmap head)
	{
		this.context = context;
		this.name = name;
		this.phone = phone;
		this.head = head;
	}

	public ContactItem(Context context, String name, String phone)
	{
		this(context, name, phone, null);
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
				Uri personUri = ContentUris.withAppendedId(CONTENT_URI, contactId);

				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("*/*");
				// intent.setData(personUri);
				// intent.putExtra(Intent.EXTRA_STREAM, personUri);
				// intent.putExtra(Intent.EXTRA_TITLE, name);
				// intent.putExtra(Intent.EXTRA_PHONE_NUMBER, phone);
				// 此处添加share的代码
				context.startActivity(intent);

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
