package Communication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.semiproject.MainActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import ViewPage.FragmentA;
import model.WeatherVO;

public class WeatherService extends Service {
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String keyword = intent.getExtras().getString("keyword");

        //netword연결을 수행하므로 thread를 이용한다
        WeatherSearchRunnable runnable = new WeatherSearchRunnable();
        Thread t = new Thread(runnable);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class WeatherSearchRunnable implements Runnable{

        WeatherSearchRunnable( ){
        }

        @Override
        public void run() {
            String url = "http://70.12.229.165:8080/HomeAutomationServer/Weather";
            try{
                //1. URL 객체 생성
                URL obj = new URL(url);
                //2. URL Connection 을 개방
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                //3. 연결에 대한 설ㅈ어
                //대표적인 호출방식(GET, POST), 인증에 대한 처리
                con.setRequestMethod("GET");
                //데이터 연결통로 만들어 데이터를 읽어드린다
                //InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                Log.i("weather", sb.toString());
                br.close();
                //doucment : [{책1권}, {책1권}, {책1권}, ....]
                //value값을 객체화 시킨다
                ObjectMapper mapper = new ObjectMapper();
                Log.i("test", "야");
                WeatherVO[] resultArr = mapper.readValue(sb.toString(), WeatherVO[].class);
                Log.i("test", resultArr[0].getTemp());


//                ArrayList<String> resultData = new ArrayList<>();
//                for(WeatherVO vo : weathers){
//                    resultData.add(vo.getTemp());
//                    Log.i("test", vo.getTemp());
//                }



                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                Log.i("test", "야");
                resultIntent.putExtra("weatherResult", resultArr);
                Log.i("test", "야");
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(resultIntent);

//                FragmentA fragment = new FragmentA();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("result", resultData);
//                fragment.setArguments(bundle);



            }catch (Exception e){
                Log.i("kakao", e.toString());
            }
        }
    }
}
