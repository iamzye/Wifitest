package ye.wifitest;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class diaoyong1 extends AppCompatActivity {

    private WifiInfo wifiInfo = null;
    private WifiManager wifiManager = null;

    private Handler handler;

    private TextView tv2;
    private Button mbutton;
    private String otherwifi;
    private String averagewifi;

    private List<ScanResult> results;
    private ArrayList<String> list;



    private int j,k;
    int duration;
    private int data[][];
    int n;
    int index;
    int sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaoyong1);

        //取得Intent中的Bundle对象
        Bundle bundle=this.getIntent().getExtras();

        //取得bundle对象中的数据
        int time=bundle.getInt("Time");

        switch (time)
        {
            case 0 :
                duration=2;
                break;
            case 1 :
                duration=3;
                break;
            case 2 :
                duration=4;
                break;
            case 3 :
                duration=5;
                break;
            case 4:
                duration=8;
                break;
            case 5:
                duration=10;
                break;

            default:
                duration=4;
                break;
        }

        //初始化或者赋实例

        k=2*60*duration;  //1S取2个值
        data=new int[40][k];
        mbutton=(Button) findViewById(R.id.mbutton2);
        tv2=(TextView)findViewById(R.id.mText);
        list=new ArrayList<String>();



        //获得wifimanager
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);



        //使用定时器，每隔1S获得一次信号强度值
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo.getBSSID() != null) {
                    //wifi名称
                    String ssid = wifiInfo.getSSID();
                    //wifi信号强度
                    int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 6);
                    //wifi速度
                    int speed = wifiInfo.getLinkSpeed();
                    //wifi速度单位
                    String units = WifiInfo.LINK_SPEED_UNITS;

                    String text = "We are connecting to " + ssid + " at " + String.valueOf(speed) +
                            " " + String.valueOf(units) + "  Strength : " + signalLevel;


                    results = wifiManager.getScanResults();  //扫描结果
                    otherwifi = "已搜索到" + results.size() + "个信号：\n\n";


                    //打印扫描结果
                    if (j == 0) {
                        for (int i = 0; i < results.size(); i++) {

                            list.add(results.get(i).SSID);
                            data[i][j] = results.get(i).level;
                            otherwifi += results.get(i).SSID + "  (" + (double)
                                    results.get(i).frequency / 1000 + "GHz）:  " + results.get
                                    (i).level + "dBm  " + "\n";

                        }
                    } else if (0 < j & j < k) {


                        n = list.size();
                        averagewifi = "\n \n";
                        averagewifi += "The wifi average power list ("+n+"):"+"\n\n";

                        for(int i=0;i<n;i++){

                            data[i][j]=0;
                        }

                        for (int i = 0; i < results.size(); i++) {

                            otherwifi += results.get(i).SSID + " (" + (double)
                                    results.get(i).frequency / 1000 + "GHz）:   " + results.get
                                    (i).level + "dBm" + "\n";


                            if (list.contains(results.get(i).SSID)) {
                                index = list.indexOf(results.get(i).SSID);
                                data[index][j] = results.get(i).level;

                            } else {
                                list.add(results.get(i).SSID);
                                data[n][j] = results.get(i).level;
                                n++;
                            }

                        }

                            //求平均值
                            for (int i = 0; i < n; i++) {

                                int aver ;
                                int count= 0;
                                sum = 0;
                                for (int s = 0; s < (j + 1); s++) {
                                    if(data[i][s]!=0){
                                        sum = sum + data[i][s];
                                        count++;
                                    }
                                }

                                aver = sum /count;

                                averagewifi += list.get(i) + " : " + aver + "dBm \n";
                            }

                    } else {
                        j = -1;
                        data = new int[40][k];
                        list = new ArrayList<String>();

                    }


                    otherwifi += "\n  " + j + "\n  " + "\n";
                    otherwifi += text + "\n";
                    otherwifi += averagewifi;
                    otherwifi +="\n\n";

                    Message msg = new Message();
                    handler.sendMessage(msg);

                }

            }

        }, 1000, 500);

        //使用Handle实现UI线程与Timer线程之间的信息传递，每1秒告诉UI线程获得WIFIinfo
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                j++;
                tv2.setText(otherwifi);


            }


        };
        mbutton.setOnClickListener(new Button.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //TODO Auto-generated method stub

                data=new int [40][k];
                list=new ArrayList<String>();
                j=0;
                n=0;
            }
        });

    }
}
