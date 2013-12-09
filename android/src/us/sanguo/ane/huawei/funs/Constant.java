package us.sanguo.ane.huawei.funs;

import java.util.HashMap;

public class Constant {

	public static final String HuaweiPayPlugin = "HuaweiPaySDK";
	
	public static final HashMap<String, String> allPluginName = new HashMap<String, String>()
	{
		{
			put(HuaweiPayPlugin, "华为支付");
		}
	};
	
}
