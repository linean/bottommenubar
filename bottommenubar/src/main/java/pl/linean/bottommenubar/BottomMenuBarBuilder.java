package pl.linean.bottommenubar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import pl.linean.bottommenubar.widget.BottomMenuBarWidget;


/**
 * Created by Maciej Sady on 05.08.2016.
 */
@SuppressWarnings("unused")
public class BottomMenuBarBuilder extends ContextWrapper{

    private final BottomMenuBar bottomMenuBar;

    public BottomMenuBarBuilder(Context base, BottomMenuBarWidget widget) {
        super(base);
        bottomMenuBar = new BottomMenuBar((Activity) base, widget.getRoot());
    }

    public BottomMenuBarBuilder setOnClickListener(BottomMenuBar.OnBarAction onClickListener){
        bottomMenuBar.onBarAction = onClickListener;
        return this;
    }

    public BottomMenuBarBuilder setCustomBackgroundColor(@ColorRes int colorRes){
        bottomMenuBar.backgroundColor = getColorFromRes(colorRes);
        return this;
    }

    private int getColorFromRes(@ColorRes int colorRes){
        return ContextCompat.getColor(this, colorRes);
    }

    public BottomMenuBarBuilder enableDivider(){
        bottomMenuBar.dividerEnabled = true;
        return this;
    }

    public BottomMenuBarBuilder setCustomDividerWidth(int width){
        if(!bottomMenuBar.dividerEnabled)
            throw new BottomBarMenuInitializationError("To set divider width you must use enableDivider() before !");
        bottomMenuBar.dividerWidth = width;

        return this;
    }

    public BottomMenuBarBuilder setCustomDividerColor(@ColorRes int colorRes){
        if(!bottomMenuBar.dividerEnabled)
            throw new BottomBarMenuInitializationError("To set divider width you must use enableDivider() before !");
        bottomMenuBar.dividerColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBarBuilder setCustomBarHeight(int height){
        bottomMenuBar.barHeight = height;
        return this;
    }

    public BottomMenuBarBuilder setCustomTextSize(int sizeInSp){
        Params.textSize = sizeInSp;
        return this;
    }

    public BottomMenuBarBuilder setCustomTextColor(@ColorRes int colorRes){
        Params.textColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBarBuilder enableTopDivider(){
        bottomMenuBar.topDividerEnabled = true;
        return this;
    }

    public BottomMenuBarBuilder setCustomTopDividerHeight(int height){
        if(!bottomMenuBar.topDividerEnabled)
            throw new BottomBarMenuInitializationError("To set divider width you must use enableTopDivider() before !");
        bottomMenuBar.topDividerHeight = height;
        return this;
    }

    public BottomMenuBarBuilder setCustomTopDividerColor(@ColorRes int colorRes){
        if(!bottomMenuBar.topDividerEnabled)
            throw new BottomBarMenuInitializationError("To set divider width you must use enableTopDivider() before !");
        bottomMenuBar.topDividerColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBarBuilder setSelectedTextColor(@ColorRes int colorRes){
        Params.selectedTextColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBarBuilder setSelectedBackgroundColor(@ColorRes int colorRes){
        Params.selectedBackgroundColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBarBuilder setSelectedIconColor(@ColorRes int colorRes){
        Params.selectedIconColor = getColorFromRes(colorRes);
        return  this;
    }

    public BottomMenuBarBuilder setIconColor(@ColorRes int colorRes){
        Params.iconColor = getColorFromRes(colorRes);
        return this;
    }

    public BottomMenuBar build() throws BottomBarMenuInitializationError {
        bottomMenuBar.scale = getResources().getDisplayMetrics().density;

        bottomMenuBar.initLayout();
        bottomMenuBar.setBackgroundColor();
        bottomMenuBar.validate();

        return bottomMenuBar;
    }

    final static class BottomBarMenuInitializationError extends RuntimeException{
        BottomBarMenuInitializationError(String message)
        {
            super(message);
        }
    }
}