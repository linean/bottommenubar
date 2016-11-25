package pl.linean.bottommenubar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maciej Sady on 05.08.2016.
 */
class SingleElement {
    private final Drawable icon;
    private final String name;
    private final Activity activity;
    private TextView nameField;
    private ImageView imageView;
    private View root;

    SingleElement(Drawable drawable, String name, Activity activity){
        this.icon = drawable;
        this.name = name;
        this.activity = activity;
    }

    SingleElement build() throws SingleElementInitializationError {
        initLayout();
        validate();
        setData();
        return this;
    }

    View getElementView(){
        return root;
    }

    public String getName(){
        return name;
    }

    @SuppressLint("InflateParams")
    private void initLayout(){
        root = activity.getLayoutInflater().inflate(R.layout.single_element,null);

        nameField = (TextView) root.findViewById(R.id.name);
        nameField.setTextColor(Params.textColor);
        imageView = (ImageView) root.findViewById(R.id.bottomIcon);
        ColorFilter filter = new LightingColorFilter(Params.iconColor, Params.iconColor);
        imageView.setColorFilter(filter);


        if(Params.textSize > 0)
            nameField.setTextSize(TypedValue.COMPLEX_UNIT_SP, Params.textSize);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 1;

        root.setLayoutParams(lp);
    }

    private void validate() throws SingleElementInitializationError {
        if(nameField == null || imageView == null || root == null) {
            Toast.makeText(activity, "SingleElementException ! Layout init problem", Toast.LENGTH_SHORT).show();
            throw new SingleElementInitializationError("Layout init error");
        }
    }

    private void setData(){
        nameField.setText(name);
        imageView.setImageDrawable(icon);
    }

    void enableSelection(){
        if(Params.selectedTextColor != 0)
            nameField.setTextColor(Params.selectedTextColor);
        if(Params.selectedBackgroundColor != 0)
            root.setBackgroundColor(Params.selectedBackgroundColor);
        if(Params.selectedIconColor != 0) {
            ColorFilter filter = new LightingColorFilter(Params.selectedIconColor, Params.selectedIconColor);
            imageView.setColorFilter(filter);
        }
    }

    void disableSelection(){
        ColorFilter filter = new LightingColorFilter(Params.iconColor, Params.iconColor);
        imageView.setColorFilter(filter);
        nameField.setTextColor(Params.textColor);
        root.setBackgroundResource(0);
    }

    @SuppressWarnings("SameParameterValue")
    private class SingleElementInitializationError extends RuntimeException{
        SingleElementInitializationError(String message) {
            super(message);
        }
    }
}
