package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import com.swe.accessibility.domain.Priority;
import com.swe.accessibility.domain.SubReason;

public class SubReasonProxy implements Serializable {

	private static final long serialVersionUID = -1118384270599532798L;

	private int id;

	private String title;

	private int parentReasonId;

	private Extra extra;

	private String priority;

	public SubReasonProxy() {

	}

	public SubReasonProxy(SubReason subReason) {

		this.id = subReason.getId();
		this.parentReasonId = subReason.getParentReasonId();
		this.title = subReason.getTitle();

		if (subReason.getExtra() != null) {
			try {
				JSONObject obj = new JSONObject(subReason.getExtra());
				this.extra = new Extra(obj.getString("key"),
						obj.getString("boundary"), obj.getString("value"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int code = subReason.getPriority();

		switch (code) {
		case 1:
			this.priority = Priority.LOW.getLabel();
			break;
		case 2:
			this.priority = Priority.HIGH.getLabel();
		case 3:
			this.priority = Priority.CRITICAL.getLabel();
			break;
		default:
			this.priority = null;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentReasonId() {
		return parentReasonId;
	}

	public void setParentReasonId(int parentReasonId) {
		this.parentReasonId = parentReasonId;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
