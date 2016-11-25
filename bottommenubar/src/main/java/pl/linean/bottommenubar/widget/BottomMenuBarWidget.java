package pl.linean.bottommenubar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import pl.linean.bottommenubar.R;

/**
 * Created by Maciej Sady on 08.08.2016.
*/
public class BottomMenuBarWidget extends LinearLayout{

    private View root;
    public BottomMenuBarWidget(Context context) {
        super(context);
        initialize(context);
    }

    public BottomMenuBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context){
        if(isInEditMode())
            inflate(context, R.layout.bottom_bar_edit_mode, this);
        else
            root = inflate(context, R.layout.bottom_bar, this);
    }

    public View getRoot(){
        return root;
    }


}
