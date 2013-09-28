package poker.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class CreateAccount extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		}
	
	//called when back button is clicked
	public void lastScreen(View view){
		super.finish();
	}	
	
	
}
