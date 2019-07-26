package com.ais.brm.study.brmTest.hadoop.mapreduce.order.topn.grouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean>{

	private String orderId;
	private String userId;
	private String pdtName;
	private float price;
	private int number;
	private float amountFee;

	public void set(String orderId, String userId, String pdtName, float price, int number) {
		this.orderId = orderId;
		this.userId = userId;
		this.pdtName = pdtName;
		this.price = price;
		this.number = number;
		this.amountFee = price * number;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(float amountFee) {
		this.amountFee = amountFee;
	}

	@Override
	public String toString() {

		return this.orderId + "," + this.userId + "," + this.pdtName + "," + this.price + "," + this.number + ","
				+ this.amountFee;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.orderId);
		out.writeUTF(this.userId);
		out.writeUTF(this.pdtName);
		out.writeFloat(this.price);
		out.writeInt(this.number);

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.orderId = in.readUTF();
		this.userId = in.readUTF();
		this.pdtName = in.readUTF();
		this.price = in.readFloat();
		this.number = in.readInt();
		this.amountFee = this.price * this.number;
	}

	// 比较规则：先比总金额，如果相同，再比商品名称
	@Override
	public int compareTo(OrderBean o) {
		
		return this.orderId.compareTo(o.getOrderId())==0
				?Float.compare(o.getAmountFee(),
				this.getAmountFee()):this.orderId.compareTo(o.getOrderId());
		
	}

}
