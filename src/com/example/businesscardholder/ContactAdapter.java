package com.example.businesscardholder;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class ContactAdapter extends BaseExpandableListAdapter {
	private List<ContactItem> contactList;
	
	private static final int[] TYPES = {ContactItem.TYPE_PHONE, ContactItem.TYPE_BUTTON};
	
	public ContactAdapter(List<ContactItem> contactList) {
		this.contactList = contactList;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return contactList.get(groupPosition).getValue(TYPES[childPosition]);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		return contactList.get(groupPosition).generateView(TYPES[childPosition]);
	}

	public int getChildrenCount(int groupPosition) {
		return TYPES.length;
	}

	public Object getGroup(int groupPosition) {
		return contactList.get(groupPosition).getValue(ContactItem.TYPE_NAME);
	}

	public int getGroupCount() {
		return contactList.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		return contactList.get(groupPosition).generateView(ContactItem.TYPE_NAME);
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
