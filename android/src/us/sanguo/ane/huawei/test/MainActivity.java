package us.sanguo.ane.huawei.test;

import us.sanguo.ane.huawei.funs.PayMent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LinearLayout layout;
//    private EditText inputEdit;
    private Button payBtn;
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	  	  	layout = new LinearLayout(this);
	        
	        payBtn = new Button(this);
	        payBtn.setText("pay");
	        layout.addView(payBtn);
	        
	        this.setContentView(layout);
	        
	        payBtn.setOnClickListener(onPayBTNClick);
	    }
		
		private OnClickListener onPayBTNClick = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PayMent __pay = new PayMent();
				__pay.callFun(MainActivity.this, 
						"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgv/vOAb0oKObkh4YXOryhVrEKoUn36b/esYxdTan0r5scs9HWbdz1MxlEaORltNnQb9Us87oHGI/7D5Uz5nd8QIDAQABAkATVcUwJs6qext2KJz98euTxT7Y68hj2Vkx/NjF7Sg+EYgQouZ5/yMmwQDESaJgTzrmq8KuclhFnxoCiAzhmiYFAiEA3cd5ujeP3xNJ6kU4pTgcGn8It4BAEt8BerwCCo/OND8CIQCXNpHWJ3wAeRihdPBYKkWgE4w1Byy7rNBie2wTXn6hzwIhAJIWrgaORwUozY22H0QmG80QVQubPZmwsGbKpYWTiL89AiBduq6VLy5W4Lkaw3CDRdiYi+VZrVPWFR2qHdT1AJq/0wIgL4lKdiI6fXV0hO8KLQ4KF/WTwtk7pXJWvDuOMtK/GL4=",
						"Demo",
						"10086000000000293",
						"10064121",
						"0.10",
						"金宝箱",
						"120元宝",
						"123123123123123",
						"http://www.xxx.com/callback_url.php");
			}
		};
}
