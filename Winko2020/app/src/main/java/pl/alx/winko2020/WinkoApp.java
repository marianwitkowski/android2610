package pl.alx.winko2020;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        formUri = "marian.witkowski@gmail.com",
        customReportContent = {
                ReportField.ANDROID_VERSION,
                ReportField.STACK_TRACE,
                ReportField.PHONE_MODEL,
                ReportField.USER_IP
        },
        mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast
)
public class WinkoApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
    }
}
