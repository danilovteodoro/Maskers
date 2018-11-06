package org.dteodor.maskers.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import org.dteodor.maskers.watcher.CustomTextWatcher;
import org.dteodor.maskers.R;

public class InputMask extends AppCompatEditText {
    private String mask;
    private Context contex;
    private AttributeSet attrs;
    CustomTextWatcher customTextWatcher;

    public InputMask(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.contex = context;
        this.attrs = attrs;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.InputMask,0,0);
        try{
            String mask = a.getString(R.styleable.InputMask_mask);
            this.mask = mask;
            this.customTextWatcher = new CustomTextWatcher(this,mask);
            this.addTextChangedListener(customTextWatcher);
            this.setInputType(InputType.TYPE_CLASS_NUMBER);
            this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mask.length())});
        }finally {
            a.recycle();
        }
    }


    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
        if(customTextWatcher != null){
            this.removeTextChangedListener(customTextWatcher);
        }
        customTextWatcher = new CustomTextWatcher(this,mask);
        this.addTextChangedListener(customTextWatcher);
    }

    public String unmask(){
        return this.getText().toString().replaceAll("[^\\d]+","");
    }
}
