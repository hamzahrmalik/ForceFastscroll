package com.hamzah.forcefastscroll; 

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
 
public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit{

	XSharedPreferences pref;
	
	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		findAndHookMethod("android.widget.AbsListView", lpparam.classLoader, "setFastScrollEnabled", boolean.class, new XC_MethodHook(){
			@Override
			protected void beforeHookedMethod(MethodHookParam param)
					throws Throwable {
				pref.reload();
				boolean on = pref.getBoolean(lpparam.packageName, false);
				//XposedBridge.log("FASTSCROLL FOR " + lpparam.packageName  + (on ? "on":"off"));
				if(on)
					param.args[0] = true;
			}
		});
	}

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		pref = new XSharedPreferences(this.getClass().getPackage().getName(), Keys.PREF_NAME);
	} 
 
}