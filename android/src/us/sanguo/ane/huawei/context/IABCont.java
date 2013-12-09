package us.sanguo.ane.huawei.context;

import java.util.HashMap;
import java.util.Map;

import us.sanguo.ane.huawei.funs.PayMent;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class IABCont extends FREContext
{
	public static final String TAG = "us.sanguo.ane.huawei.context.IABCont";
	
	/**
	 * 记录所有支持的FREFunction
	 */
	public static enum FUNS{PAYMENT};
	
	static private IABCont _instance;
	
	static public IABCont getInstance()
	{
		return _instance;
	}
	
	@Override
	public void dispose() {
		String __info = "dispose";
		Log.d(TAG, __info);
		dispatchStatusEventAsync(__info, getTag());
	}
	
	private void dispatch(String $code, String $level){
        Log.d($code, $level);
		dispatchStatusEventAsync($code, $level);
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		_instance = this;
		dispatch("init","Cont Map");
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put(FUNS.PAYMENT.toString(), new PayMent());
		return functions;
	}
	
	public void destroy()
	{
		dispatch(TAG, "Destroying helper.");
	}

	public String getTag()
	{
		return TAG;
	}
}
