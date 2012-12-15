package com.swe.accessibility.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Table(name="Entry")
@Entity
public class Entry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786243773940227607L;
	
	
	private int id;
	

	private String imageMeta;
	
	
	private BigDecimal coordX;
	
	
	private BigDecimal coordY;
	
	
	private String comment;
	
	
	private int upVoteCount;
	
	
	private int downVoteCount;
	
	
	private User user;
	
	
	private boolean fixed;
	
	private int priority;
	
	
	private Set<EntryReason> entryReasons = new HashSet<EntryReason>();
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="EntryAbuse",joinColumns={@JoinColumn(name="EntryId")},inverseJoinColumns={@JoinColumn(name="AbuseId")})
	private Set<Abuse> abuses;
	
	@OneToMany(mappedBy="entry",fetch=FetchType.LAZY)
	private List<Comment> comments;

	public Entry() {
		this.fixed = false;
		this.priority = 1;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="ImageMeta")
	public String getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(String imageMeta) {
		this.imageMeta = imageMeta;
	}

	@Column(name="CoordX")
	public BigDecimal getCoordX() {
		return coordX;
	}

	public void setCoordX(BigDecimal coordX) {
		this.coordX = coordX;
	}

	@Column(name="CoordY")
	public BigDecimal getCoordY() {
		return coordY;
	}

	public void setCoordY(BigDecimal coordY) {
		this.coordY = coordY;
	}

	@Column(name="Comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name="UpVoteCount")
	public int getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(int upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	@Column(name="DownVoteCount")
	public int getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(int downVoteCount) {
		this.downVoteCount = downVoteCount;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="submittedBy")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy="entry",cascade=CascadeType.ALL)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "er.entry", cascade = 
	    {CascadeType.PERSIST, CascadeType.MERGE})
	    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, 
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	public Set<EntryReason> getEntryReasons() {
		return entryReasons;
	}

	
	public void setEntryReasons(Set<EntryReason> entryReasons) {
		this.entryReasons = entryReasons;
	}
	
	@Column(name="fixed")
	public boolean isFixed() {
		return fixed;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	@Column(name="priority")
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

}
