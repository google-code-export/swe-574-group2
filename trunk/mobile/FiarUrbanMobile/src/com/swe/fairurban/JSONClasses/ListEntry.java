package com.swe.fairurban.JSONClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class ListEntry implements Parcelable {
//	"id":8,"imageMeta":"http://testpalette.com/null","coordX":41.0020000000,
//	"coordY":29.0100000000,"comment":"Test comment 6","upVoteCount":0,"downVoteCount":0,"userName":"testuser","comments":null
	
	public Integer id;
	public String imageMeta;
	public float coordX;
	public float coordY;
	public String comment;
	public Integer upVoteCount;
	public Integer downVoteCount;
	public String userName;
	
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(imageMeta);
		dest.writeFloat(coordX);
		dest.writeFloat(coordY);
		dest.writeString(comment);
		dest.writeInt(upVoteCount);
		dest.writeInt(downVoteCount);
		dest.writeString(userName);
	}
	
	public static final Parcelable.Creator<ListEntry> CREATOR = new Parcelable.Creator<ListEntry>() {
		 public ListEntry createFromParcel(Parcel in) {
			 return new ListEntry(in);
		 }
	
		 public ListEntry[] newArray(int size) {
		     return new ListEntry[size];
		 }
	};
	
	public ListEntry()
	{
		
	}

	public ListEntry(Parcel in) {
		id = in.readInt();
		imageMeta = in.readString();
		coordX = in.readFloat();
		coordY = in.readFloat();
		comment = in.readString();
		upVoteCount = in.readInt();
		downVoteCount = in.readInt();
		userName = in.readString();
	}
}
