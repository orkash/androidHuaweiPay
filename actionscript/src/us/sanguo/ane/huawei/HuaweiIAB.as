package us.sanguo.ane.huawei
{
import flash.external.ExtensionContext;
import flash.system.Capabilities;

import us.sanguo.ane.huawei.funs.HuaweiCont;

/**
 * 定义对Huawei的调用
 * @author Zm
 * 创建日期：2013.11.27
 */
public class HuaweiIAB
{
	/**
	 * 定义本地插件的ID
	 */	
	public static const EXT_ID:String = 'us.sanguo.ane.huawei';
	
	protected static var _huaweiCont:HuaweiCont= null;
	
	/**
	 * 获取当前插件
	 */
	public static function get iab():HuaweiCont
	{
		if(!_huaweiCont)
		{
			checkSuppored();
			_huaweiCont = new HuaweiCont(ExtensionContext.createExtensionContext(EXT_ID, ""));
		}
		return _huaweiCont;
	}
	
	protected static function get isSupported() : Boolean
	{
		return (Capabilities.os.indexOf("Linux") >= 0);
	}
	
	private static function checkSuppored():void
	{
		if(!isSupported) throw new TypeError('The native extension is not supported on this device!');
	}
}
}