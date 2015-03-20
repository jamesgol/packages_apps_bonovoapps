package com.bonovo.mcuupdate_and_setting;

import android.app.Fragment;
import android.app.Activity;
import android.widget.RadioButton;
import android.content.SharedPreferences;
import android.widget.RadioGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

public class RightFragmentOTGModel extends Fragment {
    private int PRESSHOST;
    private int PRESSSLAVE;
    private CallBackOTG backOTG;
    private Activity context;
    private RadioButton hostBtn;
    private int otgFlag;
    private SharedPreferences preferences;
    private RadioGroup radioGroup;
    private RadioButton slaveBtn;
	
	public static interface CallBackOTG {
        public abstract void switchOTG(int i);
    }
    
    public RightFragmentOTGModel() {
        PRESSHOST = 1;
        PRESSSLAVE = 2;
    }
    
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            backOTG = (CallBackOTG)activity;
        } catch(Exception e) {
        }
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(0x7f03000b, null);
        radioGroup = (RadioGroup)view.findViewById(0x7f080035);
        hostBtn = (RadioButton)view.findViewById(0x7f080036);
        slaveBtn = (RadioButton)view.findViewById(0x7f080037);
        readSharePreConfig();
        checkRadioButton();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == hostBtn.getId()) {
                    otgFlag = PRESSHOST;
                    preferences = context.getSharedPreferences("otg model", 0x1);
                    preferences.edit().putInt("otg checked", otgFlag).commit();
                    backOTG.switchOTG(otgFlag);
                    return;
                }
                if(checkedId == slaveBtn.getId()) {
                    otgFlag = PRESSSLAVE;
                    preferences = context.getSharedPreferences("otg model", 0x1);
                    preferences.edit().putInt("otg checked", otgFlag).commit();
                    backOTG.switchOTG(otgFlag);
                }
            }
        });
        return view;
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public void onDestroy() {
        super.onDestroy();
    }
    
    private void readSharePreConfig() {
        preferences = context.getSharedPreferences("otg model", 0x1);
        otgFlag = preferences.getInt("otg checked", PRESSHOST);
    }
    
    private void checkRadioButton() {
        if(otgFlag == PRESSHOST) {
            hostBtn.setChecked(true);
        } else if(otgFlag == PRESSSLAVE) {
            slaveBtn.setChecked(true);
        }
    }
}