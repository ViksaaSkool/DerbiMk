package com.derbi.mk.fragmentdialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.derbi.mk.R;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.utils.SocialUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

;

/**
 * Created by varsovski on 30-Apr-15.
 */
public class AboutDialog extends DialogFragment implements View.OnClickListener {


    @InjectView(R.id.bContactMail)
    ImageView mBContactMail;
    @InjectView(R.id.bRate)
    ImageView mBRate;
    @InjectView(R.id.bIcons8)
    ImageView mBIcons8;

    public AboutDialog() {
        // Empty constructor required for DialogFragment
    }

    public static AboutDialog newInstance(String title) {
        AboutDialog frag = new AboutDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_about, container, false);
        ButterKnife.inject(this, v);
        initDialog();

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bContactMail:
                SocialUtil.sendMailTo(getActivity(), getString(R.string.vMail));
                break;

            case R.id.bRate:
                SocialUtil.openAppInPlay(getActivity());
                break;

            case R.id.bIcons8:
                SocialUtil.openLinkInBrowser(getActivity(), Urlz.ICN8);
                break;

            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void initDialog() {
        //set listeners
        mBContactMail.setOnClickListener(this);
        mBRate.setOnClickListener(this);
        mBIcons8.setOnClickListener(this);

        //dialog settings
        setCancelable(true);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


}
