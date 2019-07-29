package main.java.lotf.util;

public class DoubleValue<L, R> {

	private L left;
	private R right;
	
	public DoubleValue(L left, R right) {
		this.left = left;
		this.right = right;
	}
	
	public DoubleValue<L, R> setL(L left) {
		this.left = left;
		return this;
	}
	
	public DoubleValue<L, R> setR(R right) {
		this.right = right;
		return this;
	}
	
	public DoubleValue<L, R> setLeft(L left) {
		this.left = left;
		return this;
	}
	
	public DoubleValue<L, R> setRight(R right) {
		this.right = right;
		return this;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "(" + left + ", " + right + ")";
	}
}
