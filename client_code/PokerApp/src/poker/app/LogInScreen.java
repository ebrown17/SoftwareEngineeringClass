package poker.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class LogInScreen extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_screen);
		
		}
	
	//called when back button is clicked
	public void lastScreen(View view){
		super.finish();
	}
	
	//called when next button is clicked
	public void nxtScreen(View view){
		Intent intent = new Intent (this, Menu.class );
    	startActivity(intent);
	}
	
	public void createAccount (View view){
    	Intent intent = new Intent (this, CreateAccount.class );
    	startActivity(intent);
    	
    }
	
	
	
	
}
