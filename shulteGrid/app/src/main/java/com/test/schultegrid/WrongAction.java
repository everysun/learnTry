package com.test.schultegrid;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class WrongAction extends Animation{

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        t.getMatrix().setTranslate((float)Math.sin(interpolatedTime*20)*30, 0);
    }
}
