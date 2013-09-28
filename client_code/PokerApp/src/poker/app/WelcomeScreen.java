package poker.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class WelcomeScreen extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
 // Called when the User clicks on Send Button
    public void nxtScreen (View view){
    	Intent intent = new Intent (this, LogInScreen.class );
    	startActivity(intent);
    }
    
    
}
