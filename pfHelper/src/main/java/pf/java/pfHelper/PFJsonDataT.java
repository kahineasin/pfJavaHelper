package pf.java.pfHelper;

public class  PFJsonDataT<T> extends PFJsonData{
	public T Data ;
     public  PFJsonDataT<T> SetSuccess(T data) {
     	Result=true;
     	Data=data;
         return this;
	}
}
