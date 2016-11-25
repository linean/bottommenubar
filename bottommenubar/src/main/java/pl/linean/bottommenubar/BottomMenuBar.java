package pl.linean.bottommenubar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Sady on 05.08.2016.
 */
@SuppressWarnings("unused")
public class BottomMenuBar {

    //REQUIED
    private Activity activity;
    private View root;

    //NOT REQUIED
    int backgroundColor;
    int barHeight = 50;
    int dividerColor;
    int dividerWidth;
    OnBarAction onBarAction;
    int topDividerHeight;
    int topDividerColor;

    //LOCAl
    float scale;
    boolean dividerEnabled = false;
    boolean topDividerEnabled = false;
    private List<SingleElement> elementList = new ArrayList<>();
    private LinearLayout elementsHolder;
    private SingleElement previousSelection;

    public interface OnBarAction{
        void onBarSelection(int index);
    }


    public void smoothHide(){
        if(root.getVisibility() == View.VISIBLE) {
            Animation bottomDown = AnimationUtils.loadAnimation(activity,
                    R.anim.bottom_down);
            root.startAnimation(bottomDown);
            root.setVisibility(View.GONE);
        }
    }

    public void smoothShow(){
        if(root.getVisibility() != View.VISIBLE) {
            Animation bottomUp = AnimationUtils.loadAnimation(activity,
                    R.anim.bottom_up);
            root.startAnimation(bottomUp);
            root.setVisibility(View.VISIBLE);
        }
    }

    public void hide(){
        if(root.getVisibility() == View.VISIBLE)
            root.setVisibility(View.GONE);
    }

    public void show(){
        if(root.getVisibility() != View.VISIBLE)
            root.setVisibility(View.VISIBLE);
    }

    public void setSelection(int index){
        SingleElement element;
        try {
            element = elementList.get(index);
            selectElement(element);
        } catch (IndexOutOfBoundsException ignored){}
    }

    private void selectElement(SingleElement element){
        element.getElementView().startAnimation(AnimationUtils.loadAnimation(activity, R.anim.click_anim));
        element.enableSelection();

        if (previousSelection != null)
            previousSelection.disableSelection();

        previousSelection = element;
    }

    public void removeElement(String name){
        for(int i = 0; i < elementsHolder.getChildCount(); i++){
            View element = elementsHolder.getChildAt(i);

            LinearLayout elementLayout = (LinearLayout) element;
            TextView child = (TextView) elementLayout.getChildAt(1);
            String elementName = child.getText().toString();

            if(elementName.equals(name))
                elementsHolder.removeView(element);
        }

        for(SingleElement element: elementList)
            if(element.getName().equals(name))
                elementList.remove(element);
    }

    public void removeAllElements(){
        elementsHolder.removeAllViews();
        elementList.clear();
    }

    public void addElement(int drawableRes, String name, int iconPadding){
        Drawable icon = ContextCompat.getDrawable(activity, drawableRes);
        SingleElement element = new SingleElement(icon,name,activity).build();

        View elementView = element.getElementView();

        if(elementView == null)
            throw new BottomMenuBarBuilder.BottomBarMenuInitializationError("Unable to set null element!");

        if(elementsHolder.getChildCount() > 0 && dividerEnabled)
            addDivider();

        if(iconPadding > 0)
            elementView.findViewById(R.id.bottomIcon).setPadding(iconPadding,iconPadding,iconPadding,0);


        elementsHolder.addView(elementView);
        setOnClickListener(element);

        elementList.add(element);
    }

    private void addDivider(){
        @SuppressLint("InflateParams")
        View divider = activity.getLayoutInflater().inflate(R.layout.divider_layout, null);

        if(dividerColor != 0)
            divider.setBackgroundColor(dividerColor);
        if(dividerWidth > 0){
            ViewGroup.LayoutParams params = divider.getLayoutParams();
            params.width = (int) (dividerWidth * scale);
            params.height = barHeight;

            divider.setLayoutParams(params);
        }

        elementsHolder.addView(divider);
    }

    private void setOnClickListener(final SingleElement element){
        element.getElementView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(element != previousSelection) {
                    if (onBarAction != null)
                        onBarAction.onBarSelection(elementList.indexOf(element));
                    setSelection(elementList.indexOf(element));
                }
            }
        });
    }

    BottomMenuBar(Activity activity, View root){
        this.activity = activity;
        this.root = root;
    }

    void initLayout(){
        elementsHolder = (LinearLayout) root.findViewById(R.id.elementsHolder);

        View topDivider = root.findViewById(R.id.topDivider);

        if(topDividerEnabled)
            topDivider.setVisibility(View.VISIBLE);

        if(topDividerHeight > 0){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) topDivider.getLayoutParams();
            params.height = (int) (topDividerHeight * scale);
            topDivider.setLayoutParams(params);
        }

        if(topDividerColor != 0)
            topDivider.setBackgroundColor(topDividerColor);

        if(barHeight > 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) root.getLayoutParams();
            params.height = (int) (barHeight * scale);
            root.setLayoutParams(params);
        }
    }

    void setBackgroundColor(){
        if(backgroundColor != 0)
            root.setBackgroundColor(backgroundColor);
    }

    void validate() throws BottomMenuBarBuilder.BottomBarMenuInitializationError {
        if(root == null || elementsHolder == null)
            throw new BottomMenuBarBuilder.BottomBarMenuInitializationError("BottomMenuBar initialization error !");
    }
}
