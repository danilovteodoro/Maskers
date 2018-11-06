package org.dteodor.maskers.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomTextWatcher implements TextWatcher {
    private String mask ;
    private Map<Integer,String> map;
    private EditText editText;
    boolean flag;
    private String maskPattern;
    int lastSize;

    public CustomTextWatcher(EditText editText,String mask){
        this.editText = editText;
        this.mask = mask;
        lastSize = 0;
        this.flag = false;
        map = new HashMap<>();
        processKeys(map,mask);
        this.maskPattern = getPattern();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if(!flag){
            int size = this.editText.getText().length();
            if(size >= lastSize){
                String text = this.editText.getText().toString().replaceAll(this.maskPattern,"");
                String str = "";
                int index = 0;
                for(int i :getSortedKeys()){
                    if(text.length() >= i){
                        str = str.concat(text.substring(index,i)).concat(this.map.get(i));
                        index = i;
                    }

                }
                if(index != 0){
                    str = str.concat(text.substring(index));
                }

                if(!str.isEmpty()){
                    flag = true;
                    this.editText.setText(str);
                }
            }else if(containsMask(this.editText.getText().toString(),this.maskPattern)){
                final String text = this.editText.getText().toString().replaceAll(this.maskPattern,"");
                this.editText.setText(text);
            }

        }else {
            flag = false;
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
        this.lastSize = this.editText.getText().length();

    }

    private void processKeys(Map<Integer,String> map,String mask){
        Pattern pattern = Pattern.compile("[^#]+");
        Matcher matcher = pattern.matcher(mask);
        int charIndex = 0;
        while (matcher.find()){
            charIndex = matcher.end() -1 -this.map.size();
            map.put(charIndex,matcher.group());
        }
    }

    private String getPattern(){
        StringBuilder builder = new StringBuilder();
        for(String value :this.map.values()){
            if(builder.length() > 0){
                builder.append("|");
            }
            builder.append(Pattern.quote(value));
        }
        return builder.toString();
    }

    private boolean containsMask(String text,String regex){
        return Pattern.compile(regex).matcher(text).find();
    }

    private List<Integer> getSortedKeys(){
        List<Integer> lst = new ArrayList<>();
        lst.addAll(this.map.keySet());
        Collections.sort(lst);
        return lst;
    }
}
