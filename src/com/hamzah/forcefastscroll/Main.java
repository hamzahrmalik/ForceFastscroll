package com.hamzah.forcefastscroll;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage{

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		findAndHookMethod("android.widget.AbsListView", lpparam.classLoader, "setFastScrollEnabled", boolean.class, new XC_MethodHook(){
			@Override
			protected void beforeHookedMethod(MethodHookParam param)
					throws Throwable {
				param.args[0] = false;
			}
		});
	}

}
