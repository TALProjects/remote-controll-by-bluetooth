package com.jrq.remoterelaynewway;

import android.app.Application;

import com.jrq.remoterelaynewway.Injection.Components.AppComponent;
import com.jrq.remoterelaynewway.Injection.Components.DaggerAppComponent;
import com.jrq.remoterelaynewway.Injection.Modules.AppModule;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by jrq on 2016-09-12.
 */

@ReportsCrashes(
        mailTo = "jerzyswiech@gmail.com", // my email here
        mode = ReportingInteractionMode.TOAST,
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        resToastText = R.string.crash_toast_text)
public class RemoteApplication extends Application {
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}