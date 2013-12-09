package us.sanguo.ane.huawei.funs
{
import flash.external.ExtensionContext;

import us.sanguo.ane.huawei.enum.HuaweiFunction;

/**
 * 提供Android中Huawei的所有功能
 * @author Zm
 * 创建日期：2013.11.27
 */
public class HuaweiCont extends ToolBase
{
	private var _rsa_private:String;
	private var _userName:String;
	private var _userID:String;
	private var _appID:String;
	private var _notifyUrl:String;
	
	public function HuaweiCont($context:ExtensionContext)
	{
		super($context);
	}
	
	/**
	 * 初始化IAB
	 */	
	public function initIAB(
		$rsa_private:String, 
		$userName:String,
		$userID:String,
		$appID:String,
		$notifyUrl:String):void
	{
		this._rsa_private = $rsa_private;
		this._userName = $userName;
		this._userID = $userID;
		this._appID = $appID;
		this._notifyUrl = $notifyUrl;

	}
	
	/**
	 * 购买产品
	 */	
	public function payMent($productName:String,$productDesc:String, $amount:String, $orderId:String):void
	{
		_extension.call(HuaweiFunction.PAYMENT, _rsa_private, _userName, _userID, _appID, $amount, $productName, $productDesc, $orderId, _notifyUrl);
	}
}
}