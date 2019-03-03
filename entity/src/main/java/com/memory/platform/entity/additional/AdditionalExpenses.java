package com.memory.platform.entity.additional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "additional_expenses")
public class AdditionalExpenses extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7347109387382321245L;
	@Column(name = "name")
	private String name; // 费用名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
