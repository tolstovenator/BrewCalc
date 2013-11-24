package com.tolstovenator.brewcalc.ui;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.util.Rounder;

import android.R.attr;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class SpinBox extends LinearLayout implements OnClickListener, OnFocusChangeListener {
	
	private ImageButton downButton;
	private ImageButton upButton;
	private EditText textView;
	
	private double currentValue;
	
	private double minValue = Double.MIN_VALUE;
	private double maxValue = Double.MAX_VALUE;
	private double step = 0.1;
	private int rounding = 0;
	
	private View.OnFocusChangeListener listener;

	public SpinBox(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.spinbox_layout, this);
	}
	
	public SpinBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }
 
    public SpinBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context, attrs);
    }
	
    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SpinBox, 0, 0);
 
        TypedArray ab = context.getTheme().obtainStyledAttributes(attrs, new int[]{attr.width}, 0, 0
                );
        try {
            // get the text and colors specified using the names in attrs.xml
        	
        	rounding = a.getInt(R.styleable.SpinBox_rounding, rounding);
        	step = Rounder.round(a.getFloat(R.styleable.SpinBox_step, (float)step), rounding);
        	minValue = Rounder.round(a.getFloat(R.styleable.SpinBox_minValue, (float)minValue), rounding);
        	maxValue = Rounder.round(a.getFloat(R.styleable.SpinBox_maxValue, (float)maxValue), rounding);
        	currentValue = Rounder.round(a.getFloat(R.styleable.SpinBox_currentValue, (float) currentValue), rounding);
 
        } finally {
            a.recycle();
        }
        setCurrentValue(currentValue);
        LayoutInflater.from(context).inflate(R.layout.spinbox_layout, this);
 
        //left button 
        downButton = (ImageButton) this.findViewById(R.id.spinBoxMinus);
        downButton.setOnClickListener(this);
 
        //right text view
        upButton = (ImageButton) this.findViewById(R.id.spinBoxPlus);
        upButton.setOnClickListener(this);
        
        textView = (EditText) this.findViewById(R.id.spinBoxEdit);
        textView.setText(Double.toString(currentValue));
        textView.setOnFocusChangeListener(this);
    }

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		double val = currentValue;
		if (val < minValue) {
			val = minValue;
		} else if (val > maxValue) {
			val = maxValue;
		}
		val = Rounder.round(val, rounding);
		this.currentValue = val;
		if (textView != null) {
			textView.setText(Double.toString(this.currentValue));
		}
		if (listener != null) {
			listener.onFocusChange(this, false);
		}
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public int getRounding() {
		return rounding;
	}

	public void setRounding(int rounding) {
		this.rounding = rounding;
	}

	@Override
	public void onClick(View v) {
		if (v == downButton) {
			decreaseValue();
		} else if (v == upButton) {
			increaseValue();
		}
	}
	
	private void increaseValue() {
		double value = currentValue + step;
		setCurrentValue(value);
	}
	
	private void decreaseValue() {
		double value = currentValue - step;
		setCurrentValue(value);
	}
	
	public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
		this.listener = listener;
	}
	
	public void removeOnFocusChangeListener(View.OnFocusChangeListener listener) {
		this.listener = null;
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus && v == textView) {
			try {
				double val = Double.parseDouble(textView.getText().toString());
				setCurrentValue(val);
			} catch (Exception e) {
				setCurrentValue(currentValue);
			}
		}
		
	}
    
    
    

}
