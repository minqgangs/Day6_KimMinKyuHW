package org.androidtown.calendar.month;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 그리드뷰를 이용해 월별 캘린더를 만드는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class MainActivity extends ActionBarActivity {

    /**
     * 월별 캘린더 뷰 객체
     */
    CalendarMonthView monthView;
    final int REQUEST_CODE = 101;
    String strColor = "#FF00FF";

    /**
     * 월별 캘린더 어댑터
     */
    CalendarMonthAdapter monthViewAdapter;

    /**
     * 월을 표시하는 텍스트뷰
     */
    TextView monthText;
    String Curday,CurYear, CurMonth = "";
    Button btnDel;
    /**
     * 현재 연도
     */
    int curYear;

    /**
     * 현재 월
     */
    int curMonth;
    ListView listView1;
    IconTextListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        listView1 = (ListView) findViewById(R.id.listView1);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);
        // 아이템 데이터 만들기
                Resources res = getResources();

                listView1.setAdapter(adapter);

                // 리스너 설정
                monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
                    public void onDataSelected(AdapterView parent, View v, int position, long id) {
                        // 현재 선택한 일자 정보 표시
                        MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                        int day = curItem.getDay();

                        Log.d("MainActivity", "Selected : " + day);

                        CurYear = Integer.toString(monthViewAdapter.getCurYear());
                        CurMonth = Integer.toString(monthViewAdapter.getCurMonth()+1);
                        Curday = Integer.toString(curItem.getDay());

            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
                for(int i=0;i<adapter.getCount();i++) {
                    String[] result1 = adapter.getItemDay(i).toString().split("-");
                    if (Integer.parseInt(result1[1]) == (curMonth + 1)&&Integer.parseInt(result1[0]) == (curYear + 1)) {
                        monthViewAdapter.setAddDay(Integer.parseInt(result1[2]));
                    }
                    monthViewAdapter.setAddDay(-1);
                }

            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
                for(int i=0;i<adapter.getCount();i++) {
                    String[] result1 = adapter.getItemDay(i).toString().split("-");
                    if (Integer.parseInt(result1[1]) == (curMonth + 1)) {
                        monthViewAdapter.setAddDay(Integer.parseInt(result1[2]));
                    }
                }
            }
        });
    }

    /**
     * 월 표시 텍스트 설정
     */
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth+1) + "월");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add) {
            //return true;
            if(Curday == null || Curday.equals("")){
                Toast.makeText(getApplicationContext(),"날짜를 선택해 주세요.", Toast.LENGTH_SHORT).show();
            }
            else {

                //Toast.makeText(getApplicationContext(),CurYear+"-"+CurMonth+"-"+Curday, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Addschedule.class);
                intent.putExtra("value",CurYear+"-"+CurMonth+"-"+Curday);
                startActivityForResult(intent,REQUEST_CODE);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    //일정등록 intent 종료후
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    if (CurMonth.length() == 1) {
                        CurMonth = "0" + CurMonth;
                    }
                    Resources res = getResources();

                    adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05), CurYear + "-" + CurMonth + "-" + Curday, data.getStringExtra("result")));

                    listView1.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), CurYear + "-" + CurMonth + "-" + Curday + " 일정이 추가되었습니다.", Toast.LENGTH_LONG).show();


                    for(int i=0;i<adapter.getCount();i++) {
                        String[] result1 = adapter.getItemDay(i).toString().split("-");
                        if (Integer.parseInt(result1[1]) == (curMonth + 1)) {
                            monthViewAdapter.setAddDay(Integer.parseInt(result1[2]));
                        }
                    }
                    //MonthItemView mv = new MonthItemView(this);
                   // monthViewAdapter.
                    /*
                    btnDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            int pos = listView1.getCheckedItemPosition();
                            listView1.removeHeaderView(v);
                            listView1.removeFooterView(v);

                            adapter.notifyDataSetChanged();



                        }
                    });
                    */

                }
                break;
        }
    }
/*
    public void onDelete(View v){

        int id = listView1.getCheckedItemPosition();

        adapter.removeItem();
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplication(),"삭제 클릭" + id, Toast.LENGTH_LONG).show();
    }
    */
}
