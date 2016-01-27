package com.afollestad.appthemeenginesample.misc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.appthemeengine.ATESupportFragment;
import com.afollestad.appthemeenginesample.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MiscFragment extends ATESupportFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // this needs to be called first
        return inflater.inflate(R.layout.fragment_misc, container, false);
    }
}