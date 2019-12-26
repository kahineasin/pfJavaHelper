package pf.java.pfHelper;

public class FuncAuthorityClass extends PFEnumClass{

	public FuncAuthorityClass(String text, int value) {
		super(text, value);
		// TODO Auto-generated constructor stub
	}
	
	public static FuncAuthorityClass Default=new FuncAuthorityClass("Default",0);
	public static FuncAuthorityClass All=new FuncAuthorityClass("All",1);
	public static FuncAuthorityClass Add=new FuncAuthorityClass("Add",1<<1);
	public static FuncAuthorityClass Edit=new FuncAuthorityClass("Edit",1<<2);
	public static FuncAuthorityClass Delete=new FuncAuthorityClass("Delete",1<<3);
	public static FuncAuthorityClass Import=new FuncAuthorityClass("Import",1<<4);
	public static FuncAuthorityClass Export=new FuncAuthorityClass("Export",1<<5);
}
