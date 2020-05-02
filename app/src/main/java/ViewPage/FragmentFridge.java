package ViewPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiproject.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class FragmentFridge extends Fragment {
    String TAG = "FragmentFridge";
    View view;
    Context context;
    Activity maintAtivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fridge,container,false);
        context=container.getContext();
        //QR code Scanner Start
        IntentIntegrator.forSupportFragment(FragmentFridge.this).initiateScan();

        return  view;
    }
    /**
     * QR code - Zxing Library
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.v(TAG,"onActivityResult"+resultCode);
        if(resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            Log.v(TAG, "result.getContents() == "+scanResult.getContents());
            Toast.makeText(context,scanResult.getContents(),Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent();
//            ComponentName cname = new ComponentName("com.example.booksearchrecyclerviewkakaoapi", "\""+re+"\"");
//            intent.setComponent(cname);
//            startActivity(intent);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
