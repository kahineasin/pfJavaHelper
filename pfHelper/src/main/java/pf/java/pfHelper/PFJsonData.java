package pf.java.pfHelper;

//前端post的返回结果
public class PFJsonData
{
    //public Object Data ;
    public String Message ;
    public Boolean Result ;
    public String Url ;
    public String HtmlPartial ;
    public String HtmlPartial2 ;
    public String HtmlPartial3 ;
    public static PFJsonData SetSuccess(String message)
    {
    	PFJsonData r =new PFJsonData();
    	r.Message=message;
    	r.Result=true;
        return r;
    }

    ////public static JsonData SetBizResult(BizResult result)
    ////{
    ////    return new JsonData
    ////    {
    ////        Message = result.Message,
    ////        Result = result.Success,
    ////        Data = result.GetData()
    ////    };
    ////}

    //public static PFJsonData SetResult(bool result)
    //{
    //    return new PFJsonData
    //    {
    //        Result = result
    //    };
    //}

    //public static PFJsonData SetResult(bool result, string msg)
    //{
    //    return new PFJsonData
    //    {
    //        Result = result,
    //        Message = msg
    //    };
    //}

    public static PFJsonData SetSuccess()
    {
    	PFJsonData r =new PFJsonData();
    	r.Result=true;
        return r;
    }
//
//    public static PFJsonData SetSuccess(Object data)
//    {
//    	PFJsonData r =new PFJsonData();
//    	r.Result=true;
//    	r.Data=data;
//        return r;
//    }

    //public static PFJsonData SetSuccess(string message, object data)
    //{
    //    return new PFJsonData
    //    {
    //        Data = data,
    //        Message = message,
    //        Result = true
    //    };
    //}

    //public static PFJsonData SetSuccess(string message, string url)
    //{
    //    return new PFJsonData
    //    {
    //        Url = url,
    //        Message = message,
    //        Result = true
    //    };
    //}

    //public static PFJsonData SetSuccess(string message, object data, string url)
    //{
    //    return new PFJsonData
    //    {
    //        Data = data,
    //        Message = message,
    //        Url = url,
    //        Result = true
    //    };
    //}

    public static PFJsonData SetFault(String message)
    {
    	PFJsonData r =new PFJsonData();
    	r.Message=message;
    	r.Result=false;
        return r;
    }

    //public static PFJsonData SetFault()
    //{
    //    return new PFJsonData
    //    {
    //        Result = false
    //    };
    //}

    //public static PFJsonData SetFault(string message, string url)
    //{
    //    return new PFJsonData
    //    {
    //        Url = url,
    //        Message = message,
    //        Result = false
    //    };
    //}
}