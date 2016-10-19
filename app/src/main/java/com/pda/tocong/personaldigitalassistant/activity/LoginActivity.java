package com.pda.tocong.personaldigitalassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.pda.tocong.personaldigitalassistant.R;
import com.pda.tocong.personaldigitalassistant.util.Constants;
import com.pda.tocong.personaldigitalassistant.util.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.ib_delete)
    ImageButton ib_delete;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.ib_visible)
    ImageButton ib_visible;
    @Bind(R.id.button_login)
    Button button_login;
    @Bind(R.id.activity_login)
    RelativeLayout relativeLayout;
    public boolean password_visible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String username = et_username.getText().toString() == null ? "" : et_username.getText().toString();
                    if (!username.equals("")) {
                        ib_delete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ib_delete.setVisibility(View.VISIBLE);
                } else {
                    ib_delete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.ib_delete, R.id.ib_visible, R.id.button_login})
    public void listener(View v) {
        switch (v.getId()) {
            case R.id.ib_delete:
                et_username.setText("");
                break;
            case R.id.ib_visible:
                if (!password_visible) {
                    ib_visible.setBackgroundResource(R.drawable.visible);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_visible = true;
                } else {
                    ib_visible.setBackgroundResource(R.drawable.unvisible);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_visible = false;
                }
                break;
            case R.id.button_login:
                CharSequence username = et_username.getText();
                CharSequence password = et_password.getText();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    if (username.toString().trim().length() == 0 || password.toString().trim().length() == 0) {
                        Snackbar.make(relativeLayout,"账户或密码不能为空",Snackbar.LENGTH_SHORT).setDuration(3000).show();
                    }
                    checkLogin(username.toString().trim(), password.toString().trim());
                } else {
                    Snackbar.make(relativeLayout,"账户或密码不能为空",Snackbar.LENGTH_SHORT).setDuration(3000).show();
                }
                break;


        }
    }

    private void checkLogin(String username, String password) {
        Map<String,String>  params=new WeakHashMap<>();
        params.put(Constants.PARA_account_user,username);
        params.put(Constants.PARA_account_pwd,password);
        RetrofitUtil.asynPost(Constants.MD_adminLoginByPda,params)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, JSONObject>() {
                    @Override
                    public JSONObject call(String s) {
                        JSONObject jsonObject=null;
                        try {
                            jsonObject=new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(relativeLayout,Constants.ERRORMSG,3000).show();
                        }
                        return jsonObject;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Snackbar.make(relativeLayout,Constants.ERRORMSG,3000).show();
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getInt(Constants.STATUS)==0){
                                Snackbar.make(relativeLayout,"登录成功",3000).show();
                                String area=jsonObject.getJSONObject(Constants.RETURNDATA).getString(Constants.ID_CENTER);
                                if(!TextUtils.isEmpty(area)){
                                    area=area.equals("1")?"浦东":"浦西";
                                }else {
                                    area="";
                                }
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class).putExtra("area",area));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(relativeLayout,Constants.ERRORMSG,3000).show();
                        }
                    }
                });



    }

}
