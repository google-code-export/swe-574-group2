package com.swe.accessibility.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="Entry")
@Entity
public class Entry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786243773940227607L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="ImageMeta")
	private byte[] imageMeta;
	
	@Column(name="CoordX")
	private BigDecimal coordX;
	
	@Column(name="CoordY")
	private BigDecimal coordY;
	
	@Column(name="Comment")
	private String comment;
	
	@Column(name="UpVoteCount")
	private int upVoteCount;
	
	@Column(name="DownVoteCount")
	private int downVoteCount;
	
	@ManyToOne
	@JoinColumn(name="submittedBy")
	private User user;
	
	@ManyToMany(targetEntity=SubReason.class,mappedBy="entries")
	private Set<SubReason> reasons;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="EntryAbuse",joinColumns={@JoinColumn(name="EntryId")},inverseJoinColumns={@JoinColumn(name="AbuseId")})
	private Set<Abuse> abuses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<SubReason> getReasons() {
		return reasons;
	}

	public void setReasons(Set<SubReason> reasons) {
		this.reasons = reasons;
	}

	public byte[] getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(byte[] imageMeta) {
		this.imageMeta = imageMeta;
	}

	public BigDecimal getCoordX() {
		return coordX;
	}

	public void setCoordX(BigDecimal coordX) {
		this.coordX = coordX;
	}

	public BigDecimal getCoordY() {
		return coordY;
	}

	public void setCoordY(BigDecimal coordY) {
		this.coordY = coordY;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(int upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	public int getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(int downVoteCount) {
		this.downVoteCount = downVoteCount;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
