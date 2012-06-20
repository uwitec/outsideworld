package com.model.policy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "qq")
public class QQInfo {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	@OneToMany(targetEntity = QQGroupInfo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "qq_id")
	private Set<QQGroupInfo> elements = new HashSet<QQGroupInfo>();

	@Column(nullable = false, length = 200)
	private String userName;

	@Column(nullable = false,length = 200)
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<QQGroupInfo> getElements() {
		return elements;
	}

	public void setElements(Set<QQGroupInfo> elements) {
		this.elements = elements;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
