package ye.wifitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String [] countriesStr={"   时间间隔 2S ","   时间间隔 3S  ","   时间间隔 4S  ","   时间间隔 5S  ","   时间间隔 8S","   时间间隔 10S "};
    private int numbertime;
    private Spinner myspinner;
    private Button mybutton;
    private TextView tv2;
    private ArrayAdapter<String> adapter;
    Animation myAnimation;
    private String a;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myspinner=(Spinner) findViewById(R.id.mspinner);
        mybutton=(Button) findViewById(R.id.mbutton1);
        tv2=(TextView)findViewById(R.id.mText1);

        adapter=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, countriesStr);
        adapter.setDropDownViewResource(R.layout.myspinnre_dropdown);

        myspinner.setAdapter(adapter);


        myspinner.setOnItemSelectedListener
                (new Spinner.OnItemSelectedListener()
                 {
                     @Override
                     public void onItemSelected
                      (AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                         //countriesStr[arg2] 为所选的值
                         numbertime=arg2;

                         a="您选择的是:"+countriesStr[arg2]+"\n        (默认为4S)";
                         tv2.setText(a);


                         arg0.setVisibility(View.VISIBLE);
                 }

                     public void onNothingSelected(AdapterView<?> arg0)
                    {
                        //TODO Auto-generated method stub
                         numbertime=2;
                        a="您选择的是:"+numbertime+"\n          (默认为4S)";
                        tv2.setText(a);

                    }
                 } );
         myAnimation= AnimationUtils.loadAnimation(this, R.anim.my_anim);

        myspinner.setOnTouchListener(new Spinner.OnTouchListener()
                                     {

                                         @Override
                                         public boolean onTouch(View v, MotionEvent event)
                                         {
                                             v.startAnimation(myAnimation);
                                             v.setVisibility(View.INVISIBLE);
                                             return  false;

                                         }

                                     } );

        myspinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //TODO Auto-generated method stub
            }
        });





        mybutton.setOnClickListener(new Button.OnClickListener()
         {
            public void onClick(View v)
            {
                //创建一个Intent对象，并指定要启动的class
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, diaoyong1.class);

                //new一个Bundle对象，并将要传递的数据传入
                Bundle bundle = new Bundle();
                bundle.putInt("Time", numbertime);

                //将Bundle对象assign给Intent
                intent.putExtras(bundle);

                //调用Activity2
                startActivity(intent);

                //关闭原来的Activity
                //MainActivity.this.finish();
            }


            });
    }
}
