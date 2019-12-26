package pf.java.pfHelper;
import java.util.HashMap;
import java.util.Map;

public class PFModelConfigCollection extends HashMap<String, PFModelConfig>
{
	public PFModelConfig Get(String key) {
        String low = key.toLowerCase();
        if (this.containsKey(low))
        {
            return super.get(low);
        }
        return null;
	}
	public void Set(String key,PFModelConfig value) {
		String low = key.toLowerCase();
		super.put(low, value);
	}
//    public new PFModelConfig this[string key]
//    {
//        get
//        {
//            var low = key.ToLower();
//            if (this.ContainsKey(low))
//            {
//                return base[low];
//            }
//            return null;
//        }
//        set
//        {
//            var low = key.ToLower();
//            base[low] = value;
//        }
//    }
}
