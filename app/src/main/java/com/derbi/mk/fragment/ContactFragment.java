package com.derbi.mk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.derbi.mk.R;
import com.derbi.mk.utils.SocialUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 27-May-15.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.thumbTabak)
    BootstrapCircleThumbnail thumbTabak;
    @InjectView(R.id.redak1)
    BootstrapCircleThumbnail redak1;
    @InjectView(R.id.redak2)
    BootstrapCircleThumbnail redak2;
    @InjectView(R.id.redak3)
    BootstrapCircleThumbnail redak3;
    @InjectView(R.id.redak4)
    BootstrapCircleThumbnail redak4;
    @InjectView(R.id.redak5)
    BootstrapCircleThumbnail redak5;
    @InjectView(R.id.redak6)
    BootstrapCircleThumbnail redak6;
    @InjectView(R.id.bbDerbiFacebook)
    BootstrapButton bbDerbiFacebook;
    @InjectView(R.id.bbDerbiTwitter)
    BootstrapButton bbDerbiTwitter;

    private String defaultMail;
    private String tabakMail;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.inject(this, v);

        initUI();

        return v;
    }

    public void initUI() {

        defaultMail = getBaseActivity().getResources().getString(R.string.defaultkMail);
        tabakMail = getBaseActivity().getResources().getString(R.string.tabakMail);

        thumbTabak.setOnClickListener(this);
        bbDerbiFacebook.setOnClickListener(this);
        bbDerbiTwitter.setOnClickListener(this);

        redak1.setOnClickListener(this);
        redak2.setOnClickListener(this);
        redak3.setOnClickListener(this);
        redak4.setOnClickListener(this);
        redak5.setOnClickListener(this);
        redak6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.thumbTabak:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(thumbTabak);
                SocialUtil.sendMailTo(getBaseActivity(), tabakMail);
                break;

            case R.id.redak1:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak1);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.redak2:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak2);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.redak3:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak3);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.redak4:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak4);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.redak5:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak5);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.redak6:
                YoYo.with(Techniques.Pulse).duration(1500).playOn(redak6);
                SocialUtil.sendMailTo(getBaseActivity(), defaultMail);
                break;
            case R.id.bbDerbiFacebook:
                SocialUtil.openFacebookIntent(getBaseActivity());
                break;
            case R.id.bbDerbiTwitter:
                SocialUtil.openTwitterAccount(getBaseActivity());
                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
