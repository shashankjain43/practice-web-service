package com.snapdeal.ums.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This keeps the successful sdCash transactions and used further for avoiding
 * idempotency.
 * 
 * @author shashank
 * 
 */
@Entity
@Table(name = "sd_wallet_txn_history", catalog = "ums")
public class SDWalletTxnHistory implements Serializable {

	private static final long serialVersionUID = -2005940545450015027L;

	private Integer id;
	private String sourceUniqueTxnId;
	private String sourceAppId;
	private Integer sdWalletTxnId;
	private Date created;

	public SDWalletTxnHistory() {
		super();
	}

	public SDWalletTxnHistory(String sourceUniqueTxnId, String sourceAppId,
			Integer sdWalletTxnId, Date created) {
		super();
		this.setSourceUniqueTxnId(sourceUniqueTxnId);
		this.sourceAppId = sourceAppId;
		this.sdWalletTxnId = sdWalletTxnId;
		this.created = created;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "source_unique_txn_id", nullable = false)
	public String getSourceUniqueTxnId() {
		return sourceUniqueTxnId;
	}

	public void setSourceUniqueTxnId(String sourceUniqueTxnId) {
		this.sourceUniqueTxnId = sourceUniqueTxnId;
	}

	@Column(name = "source_app_id", nullable = false)
	public String getSourceAppId() {
		return sourceAppId;
	}

	public void setSourceAppId(String sourceAppId) {
		this.sourceAppId = sourceAppId;
	}

	@Column(name = "ums_sdCash_txn_id", nullable = false)
	public Integer getSdWalletTxnId() {
		return sdWalletTxnId;
	}

	public void setSdWalletTxnId(Integer sdWalletTxnId) {
		this.sdWalletTxnId = sdWalletTxnId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "SDWalletTxnHistory [id=" + id + ", sourceUniqueTxnId="
				+ sourceUniqueTxnId + ", sourceAppId=" + sourceAppId
				+ ", sdWalletTxnId=" + sdWalletTxnId + ", created=" + created
				+ "]";
	}

}