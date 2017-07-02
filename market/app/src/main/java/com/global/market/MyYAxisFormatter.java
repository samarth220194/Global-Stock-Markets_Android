package com.global.market;

        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.formatter.YAxisValueFormatter;

        import java.text.DecimalFormat;

/**
 * Created by Samarth on 12-Jul-16.
 */
public class MyYAxisFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisFormatter() {
        mFormat = new DecimalFormat("#####.00"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information
        return mFormat.format(value); // e.g. append a dollar-sign
    }
}
