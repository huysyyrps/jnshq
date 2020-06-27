package com.sdjnshq.architecture.utils;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.reflect.Field;

public class BottomNavigationHelper {

    @SuppressLint("RestrictedApi")
    public static void closeAnimation(BottomNavigationView view) {
        BottomNavigationMenuView mMenuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < mMenuView.getChildCount(); i++) {
            BottomNavigationItemView button = (BottomNavigationItemView) mMenuView.getChildAt(i);
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");
            float mSmallLabelSize = mSmallLabel.getTextSize();
            setField(button.getClass(), button, "shiftAmount", 0F);
            setField(button.getClass(), button, "scaleUpFactor", 1F);
            setField(button.getClass(), button, "scaleDownFactor", 1F);
            mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
        }
        mMenuView.updateMenuView();
    }


    private static  <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void setField(Class targetClass, Object instance, String fieldName, Object value) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
