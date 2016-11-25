# BottomMenuBar
Simple Android library, that allows you to create bar menu.

It allows to add many elements and it's fully customizable.

<img src="https://github.com/linean/bottommenubar/blob/master/image/sample_gif.gif?raw=true" width="250">

# Add do project 

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.linean:bottommenubar:1.0.1'
	}
  
  
# Usage

Add BottomMenuBarWidget to your layout

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/activity_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!--YOUR LAYOUT-->

        <pl.linean.bottommenubar.widget.BottomMenuBarWidget
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:id="@+id/bottom_menu"
            android:layout_alignParentBottom="true"
            android:layout_alignParentStart="true"/>
           
    </RelativeLayout>

Then set it in your Activity:

    public class MainActivity extends AppCompatActivity implements BottomMenuBar.OnBarAction {

    private BottomMenuBar bottomBar;
    private BottomMenuBarWidget bottomMenuBarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomMenuBarWidget = (BottomMenuBarWidget) findViewById(R.id.bottom_menu);

        initBottomMenuBar();
        setBottomMenuBarIcons();
    }

    private void initBottomMenuBar() {
        bottomBar = new BottomMenuBarBuilder(this, bottomMenuBarWidget)
                .enableTopDivider()
                .setCustomTextSize(10)
                .setCustomBarHeight(55)
                .enableTopDivider()
                .setOnClickListener(this)
                .setSelectedBackgroundColor(R.color.barSelectedBackground)
                .setCustomTextColor(android.R.color.black)
                .setSelectedTextColor(R.color.barSelectedTextColor)
                .setCustomBackgroundColor(R.color.barBackground)
                .setCustomTopDividerColor(R.color.barSelectedBackground)
                .setIconColor(R.color.barIconColor)
                .setSelectedIconColor(R.color.barSelectedIconColor)
                .build();
        
        //For more build option see BottomMenuBarBuiler class
    }

    public void setBottomMenuBarIcons() {
        int iconPaddingInPX = 20;
        bottomBar.removeAllElements();
        bottomBar.addElement(R.mipmap.ic_launcher, "First", iconPaddingInPX);
        bottomBar.addElement(R.mipmap.ic_launcher, "Second", iconPaddingInPX);
        bottomBar.addElement(R.mipmap.ic_launcher, "Third", 0);
        bottomBar.addElement(R.mipmap.ic_launcher, "Fourth", iconPaddingInPX);
        bottomBar.addElement(R.mipmap.ic_launcher, "Fifth", iconPaddingInPX);

        bottomBar.setSelection(2);
        
        
             //If you want to remove element
    //        bottomBar.removeAllElements();
    //        or
    //        bottomBar.removeElement("name");
        }

    @Override
    public void onBarSelection(int index) {
        String text = "Actual selection: " + index;
    }

    }



Feel free to ask question and report issues !
Thanks :) 
