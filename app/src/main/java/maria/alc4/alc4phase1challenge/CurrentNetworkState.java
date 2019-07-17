package maria.alc4.alc4phase1challenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CurrentNetworkState {
     
    private Context _context;
     
    public CurrentNetworkState(Context context){
        this._context = context;
    }
 
    public boolean getCurrentConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivityManager != null)
          {
              NetworkInfo[] currentInfo = connectivityManager.getAllNetworkInfo();
              if (currentInfo != null) {
                  for (int i = 0; i < currentInfo.length; i++)
                      if (currentInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                          return true;

                      }
              }
          }
          return false;
    }
	
}