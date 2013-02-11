package robparrett.tip;

import java.text.NumberFormat;

import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class BillAmountTextWatcher implements TextWatcher {
	private EditText billAmountEditText;
	private TextView tipAmountTextView;
	private TextView tipPercentTextView;
	private TextView totalAmountTextView;
	private SeekBar minSeekBar;
	private SeekBar maxSeekBar;

	public BillAmountTextWatcher(EditText e, TextView t, TextView t2, TextView t3, SeekBar s1, SeekBar s2) {
		billAmountEditText = e;
		tipAmountTextView = t;
		totalAmountTextView = t2;
		tipPercentTextView = t3;
		minSeekBar = s1;
		maxSeekBar = s2;
	}
	
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    
	public void afterTextChanged(Editable s) {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		
		String tipAmountText;
		String totalAmountText;
		String tipPercentText;
		
		if (billAmountEditText.getText().length() > 0) {
			int billAmountCents = Integer.parseInt(String.valueOf(billAmountEditText.getText()).replaceAll("[^0-9]", ""));

			int lowTipAmountCents  = (int) Math.ceil(billAmountCents * minSeekBar.getProgress() / 100.0);
			int highTipAmountCents = (int) Math.floor(billAmountCents * (maxSeekBar.getProgress() + 5) / 100.0);

			int tipAmountCents = highTipAmountCents;
			
			for(int i = lowTipAmountCents; i <= highTipAmountCents; i++) {
				int totalCents = i + billAmountCents;
				String totalString = String.valueOf(totalCents);
				
				if (this.isPalindrome(totalString) || this.isCounting(totalString) || this.isPi(totalString)) {
					tipAmountCents = i;
					break;
				}
			}
			
			int totalAmountCents = billAmountCents + tipAmountCents;
		
			tipAmountText   = currency.format(tipAmountCents / 100.0);
			totalAmountText = currency.format(totalAmountCents / 100.0);
			tipPercentText  = String.format("%.1f%%", tipAmountCents * 100.0 / billAmountCents);
		} else {
			tipAmountText   = currency.format(0);
			totalAmountText = currency.format(0);
			tipPercentText  = "0%";
		}
		
		tipPercentTextView.setText(tipPercentText);
		tipAmountTextView.setText(tipAmountText);
		totalAmountTextView.setText(totalAmountText);
	}
	
	public boolean isPalindrome(String someString) {
		int l = someString.length();		
		int half = someString.length() / 2;
		
		for(int i = 0; i < half; i++) {
			if (someString.charAt(i) != someString.charAt(l - 1 - i)) return false;
		}
		
		return true;
	}
	
	public boolean isCounting(String someString) {
		if (someString.length() < 2) return false;
		
		char first   = someString.charAt(0);
		char current = someString.charAt(1);
		
		int d = first - current;
		if (d != 1 && d != -1) return false;
		
		for (int i = 2; i < someString.length(); i++) {
			if (current - someString.charAt(i) != d) return false;
			
			current = someString.charAt(i);
		}
		
		return true;
	}

	public boolean isPi(String someString) {
		String pi = "314159265358979";
		
		return someString == pi.substring(0, someString.length() - 1);
	}
}
