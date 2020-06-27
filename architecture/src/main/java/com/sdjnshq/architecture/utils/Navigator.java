package com.sdjnshq.architecture.utils;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Navigator {

    public static void push(View view,@IdRes int resId){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(resId);
    }
    public static void push(View view,@IdRes int resId,Bundle bundle){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(resId,bundle);
    }
}
