package com.memory.platform.entity.additional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.order.RobOrderConfirm;

@Entity
@Table(name = "additional_expenses_record")
public class AdditionalExpensesRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4186153359755554855L;

	@ManyToOne
	@JoinColumn(name = "additional_expenses_id", nullable = false)
	private AdditionalExpenses additionalExpenses; // 附加费用

	@ManyToOne
	@JoinColumn(name = "rob_order_confirm_id", nullable = false)
	private RobOrderConfirm robOrderConfirm; // 订单确认

	@Column(name = "total")
	private Double total;

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public AdditionalExpenses getAdditionalExpenses() {
		return additionalExpenses;
	}

	public void setAdditionalExpenses(AdditionalExpenses additionalExpenses) {
		this.additionalExpenses = additionalExpenses;
	}

	public RobOrderConfirm getRobOrderConfirm() {
		return robOrderConfirm;
	}

	public void setRobOrderConfirm(RobOrderConfirm robOrderConfirm) {
		this.robOrderConfirm = robOrderConfirm;
	}
}
