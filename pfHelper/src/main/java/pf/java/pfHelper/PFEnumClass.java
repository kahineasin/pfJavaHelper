package pf.java.pfHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pf.java.pfHelper.config.PFDataHelper;

public abstract class PFEnumClass
//<T extends IPFEnumClass>
//implements IPFEnumClass
//implements Comparable<PFEnumClass>
{
//	private int _value;
//	private String _text;
	private Map<Integer,String> _values;
	
	/*
	 * 根据所有子类的名保存所有enum的可能值,便于知道enum一共有多少种选项
	 */
	protected static Map<String,Map<Integer,String>> _AllTypeValues=new HashMap<String,Map<Integer,String>>();

	protected PFEnumClass() {
		
	}
	protected PFEnumClass(String text,Integer value) {
		_values=new HashMap<Integer,String>();
		_values.put(value,text);
		
		String typeName=this.getClass().getName();
		if(!_AllTypeValues.containsKey(typeName)) {
			_AllTypeValues.put(typeName, new HashMap<Integer,String>());
		}
		_AllTypeValues.get(typeName).put(value,text);
		
		String aa="aa";
//		_value=value;
//		_text=text;		
	}
	
	public int getValue() {
		int result=0;
		Iterator<Integer> iter = _values.keySet().iterator();
		  while(iter.hasNext()){
		   result|=iter.next();
		  }
		return result;
	}

	public String getText() {
		return String.join(",", _values.values());
	}
	
	public <T extends PFEnumClass> T Or(T other) {
		_values.put(other.getValue(),other.getText());
		return (T)this;
	}
	
	public <T extends PFEnumClass> Boolean HasFlag(T other) {
		return PFDataHelper.EnumHasFlag(getValue(),other.getValue());
	}
	public String[] GetAllTexts() {
		String typeName=this.getClass().getName();
		return _AllTypeValues.get(typeName).values().toArray(new String[_AllTypeValues.get(typeName).size()]);
	}

	public static <T extends PFEnumClass> T EnumParse(Class<T> cl,String text) {
		String typeName=cl.getName();
		Integer value=new Integer(0);
		Map<Integer,String> values=_AllTypeValues.get(typeName);
		Iterator<Map.Entry<Integer,String>> iter = values.entrySet().iterator();
		  while(iter.hasNext()){
			  Map.Entry<Integer,String> tmpEntry=iter.next();
			  if(tmpEntry.getValue().equals(text)) {
				  value=tmpEntry.getKey();
				  break;
			  }
		  }
		  
		try {
			return (T)cl.getDeclaredConstructor(String.class,int.class).newInstance(text,value.intValue());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
		    PFDataHelper.WriteError(e);
		}
		return null;
	}
	
	
	
//	@Override
//	public int compareTo(PFEnumClass o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	@Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof PFEnumClass) {
        	PFEnumClass anotherString = (PFEnumClass)anObject;
            return this.getValue()==anotherString.getValue();
        }
        return false;
    }
}
