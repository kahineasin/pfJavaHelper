package pf.java.pfHelper;

/*
 * 因为java没有ref关键字,这样用吧
 * 使用方法:
 * PFRef<Integer> ti = new PFRef<Integer>(0);
 */
public class PFRef<T> {
	private T _value;
	public PFRef(T value) {
		
		_value=value;
	}
	public PFRef() {
		
	}
	public T GetValue() {
		return _value;		
	}
	public void SetValue(T value) {
		_value=value;
	}
}
