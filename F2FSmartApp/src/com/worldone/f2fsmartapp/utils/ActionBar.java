package com.worldone.f2fsmartapp.utils;

import com.worldone.f2fsmartapp.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ActionBar extends LinearLayout{

	private  ViewGroup parent;
    private  View view;

	
	public ActionBar(Context context) {
		super(context);
	}

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Method which provides ability to create dynamic action bar.
	 * @param a - container activity
	 * @param ctx - activity context
	 * @param viewId - layout, which has been added
	 * @return
	 */
	public View addElement(Activity a, Context ctx, int viewId){
		parent = (ViewGroup) a.findViewById(R.id.action_bar_container);	
		view = LayoutInflater.from(ctx).inflate(viewId,
	                getParentView(), false);
			return view;
	}
	
	
	/**
	 * Adds view element to correct action bar position.
	 * @param position
	 */
	public void addToPosition(int position){
		getParentView().addView(getView(),position);
	}
	
	public  ViewGroup getParentView() {
		return parent;
	}


	public View getView() {
		return view;
	}
	
	public Button getButton(){
		return (Button) view;
	}
	
	public TextView getTextView(){
		return (TextView) view;
	}

	
	public View getViewChild(int position){
		return getChildAt(position);
	}
	
	
		
	
}
