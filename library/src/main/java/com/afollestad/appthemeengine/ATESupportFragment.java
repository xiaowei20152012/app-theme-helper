package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.appthemeengine.util.ATEUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESupportFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null)
            ATEUtil.setInflaterFactory(inflater, getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}