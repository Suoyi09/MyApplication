package com.example.lhz.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lhz.myapplication.DeliveryMessages.Message;

public class DeliveryActivity extends AppCompatActivity implements DeliveryMessageGetter.DeliveryMessageGetterListener {

    private List<Map<String, String>> mQueryData = new ArrayList<>();
    private SimpleAdapter mQueryAdapter;

    private Spinner mDeliveryCompanySpinner;
    private EditText mDeliveryNoEditText;

    private ProgressDialog mQueryWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        mDeliveryCompanySpinner = (Spinner) findViewById(R.id.delivery_company_spinner);
        mDeliveryNoEditText = (EditText) findViewById(R.id.delivery_no_edit_text);
        Button queryButton = (Button) findViewById(R.id.query_button);
        ListView messagesListView = (ListView) findViewById(R.id.messages_list_view);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //没有输入快递单号
                if (0 == mDeliveryNoEditText.getText().length()) {
                    Toast.makeText(DeliveryActivity.this, "请输入快递单号", Toast.LENGTH_SHORT).show();
                    return;
                }

                //创建ProgressDialog对象
                mQueryWaitDialog = new ProgressDialog(DeliveryActivity.this);
                mQueryWaitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mQueryWaitDialog.setMessage("正在查询...");
                mQueryWaitDialog.show();

                //准备请求参数
                int selectedPosition = mDeliveryCompanySpinner.getSelectedItemPosition();
                String deliveryCompanyName =
                        getResources().getStringArray(R.array.delivery_company)[selectedPosition];
                Map<String, String> params = new HashMap<>();
                params.put("type",
                        ((DeliveryApplication)getApplication()).getDeliveryCompanyNo(deliveryCompanyName));
                params.put("postid", mDeliveryNoEditText.getText().toString());

                //清空数据
                mQueryData.clear();

                //发送请求
                DeliveryMessageGetter getter = new DeliveryMessageGetter();
                getter.getAsync(getResources().getString(R.string.query_url),
                        params, DeliveryActivity.this);
            }
        });

        mQueryAdapter = new SimpleAdapter(this,
                mQueryData,
                R.layout.item_list,
                new String[] {"time", "context"},
                new int[] {R.id.time_text_view, R.id.context_text_view});
        messagesListView.setAdapter(mQueryAdapter);
    }

    @Override
    public void onSuccess(DeliveryMessages deliveryMessages) {
        List<Message> messages = deliveryMessages.getData();

        for (Message message : messages) {
            Map<String, String> map = new HashMap<>();
            map.put("time", message.getTime());
            map.put("context", message.getContext());
            mQueryData.add(map);
        }

        queryComplete("查询完成");
    }

    @Override
    public void onFailure(String errorStr) {
        final String hint = errorStr;
        queryComplete("查询失败");
    }

    private void queryComplete(final String toast) {
        DeliveryActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mQueryAdapter.notifyDataSetChanged();
                mQueryWaitDialog.dismiss();
                Toast.makeText(DeliveryActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
