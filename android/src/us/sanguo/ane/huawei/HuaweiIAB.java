package us.sanguo.ane.huawei;

import us.sanguo.ane.huawei.context.IABCont;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class HuaweiIAB implements FREExtension
{
	public static final String TAG = "us.sanguo.ane.huawei.HuaweiIAB";
	
	@Override
	public FREContext createContext(String $type)
	{
		//if(ANEContext.IAB.toString().equals($type)) return new IABCont();
		return new IABCont();
	}
	    
	@Override
	public void initialize()
	{
		Log.i(TAG, "HuaweiIAB initialize");
	}
	    
	@Override
	public void dispose()
	{
		Log.i(TAG, "HuaweiIAB dispose");
	}
}
