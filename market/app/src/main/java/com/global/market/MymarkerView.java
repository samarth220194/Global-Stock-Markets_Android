package com.global.market;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MymarkerView extends MarkerView
{
    private TextView indices;
    ArrayList<String> Xlabels;

    public MymarkerView(Context context, int layoutResource,ArrayList<String> xlabels) {
        super(context, layoutResource);
        Xlabels=xlabels;
        indices = (TextView) findViewById(R.id.indices);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;

//            indices.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
//        } else {
        DecimalFormat df = new DecimalFormat("#0.00");
        String xVal=Xlabels.get(e.getXIndex());
            indices.setText(df.format(e.getVal()) + ", "+ xVal);

        //}
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);

    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
    public void draw(Canvas canvas, float posx, float posy)
    {
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point size=new Point();
//        display.getSize(size);
//        int width=size.x;
             // take offsets into consideration
        //posx += getXOffset(posx);

        posx =getWidth()/2;
        posy=0;
        // AVOID OFFSCREEN
//        if(posx<65)
//            posx=65;
//        if(posx>(width-200))
//            posx=(width-200);

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }

}
