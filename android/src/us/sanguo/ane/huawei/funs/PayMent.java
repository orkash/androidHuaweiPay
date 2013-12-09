package us.sanguo.ane.huawei.funs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.android.huawei.pay.plugin.HuaweiPay;
import com.android.huawei.pay.plugin.IHuaweiPay;
import com.android.huawei.pay.util.HuaweiPayUtil;
import com.android.huawei.pay.util.Rsa;
import com.huawei.deviceCloud.microKernel.core.MicroKernelFramework;

import us.sanguo.ane.huawei.context.IABCont;

public class PayMent extends IABFunctionBase {
	
	public  static final  int PAY_RESULT = 1000;
	IHuaweiPay payHelper = null;
	//支付插件
	String HuaweiPayPlugin = "HuaweiPaySDK";
	//环境
	public static final String environment = HuaweiPayUtil.environment_live;

	private MicroKernelFramework framework;
	
	@Override
	public FREObject call(FREContext $context, FREObject[] $args)
	{
		_context = $context;
		try
		{
			dispatch("init", getTag());
			callFun(getActivity(),$args[0].getAsString(),$args[1].getAsString(),$args[2].getAsString(),$args[3].getAsString(),$args[4].getAsString(),$args[5].getAsString(),$args[6].getAsString(),$args[7].getAsString(),$args[8].getAsString());
		}
		catch (Exception $e)
		{
			dispatch($e.getMessage());
		}
		return null;
	}
	
	public void callFun(Activity $context, String $rsa_private, String $userName, String $userID, String $appID, String $amount, String $productName, String $productDesc, String $orderID, String $notifyUrl)
	{
		dispatch("callFun", getTag());
		
		if(!initMicroKernel($context)) return;

		dispatch("8", getTag());
		try
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("userID", $userID);
			params.put("applicationID", $appID);
			params.put("amount", $amount);
			params.put("productName", $productName);
			params.put("productDesc", $productDesc);
			params.put("requestId", $orderID);
			
			String noSign = HuaweiPayUtil.getSignData(params);
			String	sign = Rsa.sign(noSign, $rsa_private);
			
			Log.e("rsa sign", "pre noSign: "+noSign + "  sign: "+sign);
			
			
			Map<String, Object> payInfo = new HashMap<String, Object>();
			payInfo.put("amount", $amount);
			payInfo.put("productName", $productName);
			payInfo.put("requestId", $orderID);
			payInfo.put("productDesc", $productDesc);
			payInfo.put("userName", $userName);
			payInfo.put("applicationID", $appID);
			payInfo.put("userID", $userID);
			payInfo.put("sign", sign);
			payInfo.put("notifyUrl", $notifyUrl);
			payInfo.put("environment", environment);
			payInfo.put("accessToken","BFIUhdi0ZQZ44trTDvARG4hnBu7K9SLnqTAG2bYRU15N/kJGMUM=");
//			调试期可打开日志，发布时注释掉
//			payInfo.put("showLog", true);
		
			
			Log.e(getTag() , "all parameters : "+payInfo.toString());
			
			/**
			 * 开始支付
			 */
			payHelper.startPay($context, payInfo, handler, PAY_RESULT);
		}
		catch (Exception e)
		{
			Log.e(getTag(), "call exception:"+e.toString());
			e.printStackTrace();
			dispatch(e.getMessage());
		}
	}
	
	 /**
     * 初始化，加载支付插件
     * @return
     */
    private boolean initMicroKernel(Activity $context){
		try {
			dispatch("1", getTag());
	        framework = MicroKernelFramework.getInstance($context);
			framework.start();
			dispatch("2", getTag());

		    //检查插件是否有更新时调用的方法，同时传递handler,框架SDK会用此handler发送状态信息
            framework.checkSinglePlugin(HuaweiPayPlugin, new UpdateNotifierHandler($context, framework));

    		dispatch("3", getTag());
            List<Object> services = framework.getService(HuaweiPayPlugin);
			if(null != services){
				Log.e(getTag(), "get " + HuaweiPayPlugin + " services size:" + services.size());
			} else {
				Log.e(getTag(), "get empty " + HuaweiPayPlugin + " services");
			}
			if(null == services || services.size() == 0){
				Log.e(getTag(), "begin to load " + HuaweiPayPlugin);
				framework.loadPlugin(HuaweiPayPlugin);
			}

			
			dispatch("4", getTag());
			
			
			if(null != framework.getPluginContext())
			{
				dispatch("A", getTag());
				if(null != framework.getPluginContext().getService(IHuaweiPay.interfaceName))//HuaweiPaySDK
					{
						dispatch("B", getTag());
						if(null != framework.getPluginContext().getService(IHuaweiPay.interfaceName).get(0))
							dispatch("C", getTag());
						else
							dispatch("D", getTag());
					}
				else
					dispatch("E", getTag());
			}
			else
				dispatch("F", getTag());
			
			HuaweiPay p = new HuaweiPay();
			p.start(framework.getPluginContext());

			Object payObject =  framework.getPluginContext().getService(IHuaweiPay.interfaceName).get(0);

			dispatch("5", getTag());
			if(payObject == null)
			{
				Log.e(getTag(), "no huaweipay  interface " + IHuaweiPay.interfaceName);
				return false;
			}

//			payHelper = (IHuaweiPay)services.get(0);
			dispatch("6", getTag());
			payHelper = (IHuaweiPay)payObject;
		
			return true;
		} catch (Exception e) {
			dispatch("7", getTag());
			Log.e(getTag(), e.toString(), e);
			return false;
		}
    }
    
    private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case PAY_RESULT: {
					//处理支付结果
					String payResult = (String)msg.obj;
					Log.e(getTag(), "GET PAY RESULT "+ payResult);
					JSONObject jsonObject = new JSONObject(payResult);
					String returnCode = jsonObject.getString("returnCode");
					if(returnCode.equals("0"))
					{
						dispatch("success", getTag());
					}else if(returnCode.equals("30002"))
					{
						dispatch("timeout", getTag());
					}
				}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	};

	@Override
	public String getTag() {
		return IABCont.FUNS.PAYMENT.toString();
	}

}
