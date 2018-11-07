# Maskers
Provide a dynamic mask genaration to Android EditText.

How to use
---
1. Declare InputMask view in your Layout interface and assign the mask attribute with your mask.

```
<org.dteodor.maskers.input.InputMask
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           app:mask="###.###.###-##"
           android:id="@+id/inputMask"
   />
```
2. Get the unmasked text.

````
inputMask.unmask() //Get unmasked text
inputMask.mask //Get mask
