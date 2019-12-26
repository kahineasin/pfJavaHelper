package pf.java.pfHelper;

import java.lang.reflect.Type;

import org.dom4j.Element;

import pf.java.pfHelper.config.PFDataHelper;

/// <summary>
/// 字段配置(使用时,一个重要原则是:只要本配置里不为null,就会复盖DataColumn原来的配置)
/// </summary>
public class PFModelConfig
{
    public Boolean Equals(PFModelConfig obj)
    {
        return this.PropertyName == obj.PropertyName && this.DataSet == obj.DataSet && this.FieldName == obj.FieldName;
    }
    //public PFModelConfig(XmlNode fieldNode, String dataSet)
    public PFModelConfig(Element fieldNode, String dataSet)
    {
//        var node = fieldNode.element("FieldId");
    	Element node = fieldNode.element("FieldId");
        if (node != null) { FieldId = node.getText(); }
        node = fieldNode.element("FieldName");
        if (node != null)
        {
            PropertyName = FieldName =
                node.getText();
        }
        node = fieldNode.element("FieldText");
        if (node != null) { FieldText = node.getText(); }
        node = fieldNode.element("FieldType");
        if (node != null) { FieldType = PFDataHelper.GetTypeByString(node.getText()); }
        node = fieldNode.element("Precision");
        if (node != null) { Precision =Integer.parseInt(node.getText()); }
        node = fieldNode.element("FieldSqlLength");
        if (node != null) { FieldSqlLength = Integer.parseInt(node.getText()); }
        node = fieldNode.element("FieldWidth");
        if (node != null) { FieldWidth = node.getText() + "px"; }
        node = fieldNode.element("Visible");
        if (node != null) { setVisible(Boolean.parseBoolean(node.getText())); }
        node = fieldNode.element("Required");
        if (node != null) { Required = Boolean.parseBoolean(node.getText()); }

        LowerFieldName = (FieldName==null?"": FieldName).toLowerCase();
        DataSet = dataSet;
    }

    /// <summary>
    /// Model的属性名(当table里有两个来自不同模块的inv字段时,这两个字段的PropertyName不一样(因为是Model中的属性),但FieldName是一样,所以FieldName对前端其实是无作用.)(现保留这两属性是为了便于以后要区分数据来源)
    /// </summary>
    public String PropertyName ;

    //#region xml里的属性
    public String DataSet ;//DataSet和FieldName是为了使于日后维护xml的对应节点
    public String FieldId ;
    public String FieldName ;
    //public String LowerFieldName { get { return (FieldName??"").ToLower(); } }
    public String LowerFieldName ;//配置文件改为不区分大小写，这样好像更好，为了过渡，不删除FieldName属性--wxj20181012
    public String FieldText ;
    public Type FieldType ;

    /// <summary>
    /// 如果是decimal,可以设置精确度
    /// </summary>
    public Integer Precision ;
    /// <summary>
    /// sql中varchar的长度,便于以后做验证;也可表示decimal(a,b)中的a,可以考虑加一个属性来记录b
    /// </summary>
    public Integer FieldSqlLength ;//用字符串是为了适应decimal

    public String FieldWidth ;//用字符串是为了写单位14px,14dx
    private Boolean _visible = true;
    public Boolean getVisible() {
		return _visible;
	}
	public void setVisible(Boolean visible) {
		this._visible = visible;
	}

	//    public Boolean Visible { get { return _visible; } set { _visible = value; } }
    public Boolean Required ;
    //#endregion

    //#region Method
    //public Boolean IsMatchField(String fieldName)
    //{
    //    if (!PFDataHelper.StringIsNullOrWhiteSpace(fieldName)) {
    //        return fieldName.ToLower() == LowerFieldName;
    //    }
    //    return false;
    //}
    //#endregion
}