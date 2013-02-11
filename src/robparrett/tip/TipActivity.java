package robparrett.tip;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.EditText;


public class TipActivity extends Activity {
	private TextView tipAmount;
	private TextView totalAmount;
	private TextView tipPercent;
	private SeekBar minSeekBar;
	private SeekBar maxSeekBar;
	private EditText billAmount;
	private TextView minSeekBarText;
	private TextView maxSeekBarText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tipAmount      = (TextView) findViewById(R.id.textView4);
        totalAmount    = (TextView) findViewById(R.id.textView5);
        tipPercent     = (TextView) findViewById(R.id.textView6);
        
        minSeekBar     = (SeekBar)  findViewById(R.id.seekBar1);
        
        maxSeekBar     = (SeekBar)  findViewById(R.id.seekBar2);
        
        minSeekBarText = (TextView) findViewById(R.id.textView7);
        maxSeekBarText = (TextView) findViewById(R.id.textView8);
        billAmount     = (EditText) findViewById(R.id.editText1);
        
        minSeekBar.setMax(95);
        maxSeekBar.setMax(95);
        minSeekBar.setProgress(15);
        minSeekBarText.setText("15%");
        maxSeekBar.setProgress(20);
        maxSeekBarText.setText("20%");
        
        NumberFormat currency = NumberFormat.getCurrencyInstance();
		tipAmount.setText(currency.format(0));
		totalAmount.setText(currency.format(0));
		tipPercent.setText("0%");
        
		BillAmountTextWatcher watcher = new BillAmountTextWatcher(billAmount, tipAmount, totalAmount, tipPercent, minSeekBar, maxSeekBar); 
        billAmount.addTextChangedListener(watcher);
        
        minSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				minSeekBarText.setText(String.valueOf(progress) + "%");
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				String tmp;
				tmp = String.valueOf(billAmount.getText());
				billAmount.setText("0");
				billAmount.setText(tmp);
			}
        });
        
        maxSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				maxSeekBarText.setText(String.valueOf(progress) + "%");
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				String tmp;
				tmp = String.valueOf(billAmount.getText());
				billAmount.setText("0");
				billAmount.setText(tmp);
			}
        });
    }
}
