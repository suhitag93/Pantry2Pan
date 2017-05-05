package com.mysampleapp.demo.nosql;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.PantryDO;

import java.util.Set;

public class DemoNoSQLPantryResult implements DemoNoSQLResult {
    private static final int KEY_TEXT_COLOR = 0xFF333333;
    private final PantryDO result;

    DemoNoSQLPantryResult(final PantryDO result) {
        this.result = result;
    }
    @Override
    public void updateItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final double originalValue = result.getID();
        result.setID(DemoSampleDataGenerator.getRandomSampleNumber());
        try {
            mapper.save(result);
        } catch (final AmazonClientException ex) {
            // Restore original data if save fails, and re-throw.
            result.setID(originalValue);
            throw ex;
        }
    }

    @Override
    public void deleteItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        mapper.delete(result);
    }

    private void setKeyTextViewStyle(final TextView textView) {
        textView.setTextColor(KEY_TEXT_COLOR);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(5), dp(2), dp(5), 0);
        textView.setLayoutParams(layoutParams);
    }

    /**
     * @param dp number of design pixels.
     * @return number of pixels corresponding to the desired design pixels.
     */
    private int dp(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    private void setValueTextViewStyle(final TextView textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(15), 0, dp(15), dp(2));
        textView.setLayoutParams(layoutParams);
    }

    private void setKeyAndValueTextViewStyles(final TextView keyTextView, final TextView valueTextView) {
        setKeyTextViewStyle(keyTextView);
        setValueTextViewStyle(valueTextView);
    }

    private static String bytesToHexString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02X", bytes[0]));
        for(int index = 1; index < bytes.length; index++) {
            builder.append(String.format(" %02X", bytes[index]));
        }
        return builder.toString();
    }

    private static String byteSetsToHexStrings(Set<byte[]> bytesSet) {
        final StringBuilder builder = new StringBuilder();
        int index = 0;
        for (byte[] bytes : bytesSet) {
            builder.append(String.format("%d: ", ++index));
            builder.append(bytesToHexString(bytes));
            if (index < bytesSet.size()) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public View getView(final Context context, final View convertView, int position) {
        final LinearLayout layout;
        final TextView resultNumberTextView;
        final TextView userIdKeyTextView;
        final TextView userIdValueTextView;
        final TextView expiryKeyTextView;
        final TextView expiryValueTextView;
        final TextView iDKeyTextView;
        final TextView iDValueTextView;
        final TextView nameKeyTextView;
        final TextView nameValueTextView;
        final TextView quantityKeyTextView;
        final TextView quantityValueTextView;
        final TextView typeKeyTextView;
        final TextView typeValueTextView;
        if (convertView == null) {
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            resultNumberTextView = new TextView(context);
            resultNumberTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(resultNumberTextView);


            userIdKeyTextView = new TextView(context);
            userIdValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(userIdKeyTextView, userIdValueTextView);
            layout.addView(userIdKeyTextView);
            layout.addView(userIdValueTextView);

            expiryKeyTextView = new TextView(context);
            expiryValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(expiryKeyTextView, expiryValueTextView);
            layout.addView(expiryKeyTextView);
            layout.addView(expiryValueTextView);

            iDKeyTextView = new TextView(context);
            iDValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(iDKeyTextView, iDValueTextView);
            layout.addView(iDKeyTextView);
            layout.addView(iDValueTextView);

            nameKeyTextView = new TextView(context);
            nameValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(nameKeyTextView, nameValueTextView);
            layout.addView(nameKeyTextView);
            layout.addView(nameValueTextView);

            quantityKeyTextView = new TextView(context);
            quantityValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(quantityKeyTextView, quantityValueTextView);
            layout.addView(quantityKeyTextView);
            layout.addView(quantityValueTextView);

            typeKeyTextView = new TextView(context);
            typeValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(typeKeyTextView, typeValueTextView);
            layout.addView(typeKeyTextView);
            layout.addView(typeValueTextView);
        } else {
            layout = (LinearLayout) convertView;
            resultNumberTextView = (TextView) layout.getChildAt(0);

            userIdKeyTextView = (TextView) layout.getChildAt(1);
            userIdValueTextView = (TextView) layout.getChildAt(2);

            expiryKeyTextView = (TextView) layout.getChildAt(3);
            expiryValueTextView = (TextView) layout.getChildAt(4);

            iDKeyTextView = (TextView) layout.getChildAt(5);
            iDValueTextView = (TextView) layout.getChildAt(6);

            nameKeyTextView = (TextView) layout.getChildAt(7);
            nameValueTextView = (TextView) layout.getChildAt(8);

            quantityKeyTextView = (TextView) layout.getChildAt(9);
            quantityValueTextView = (TextView) layout.getChildAt(10);

            typeKeyTextView = (TextView) layout.getChildAt(11);
            typeValueTextView = (TextView) layout.getChildAt(12);
        }

        resultNumberTextView.setText(String.format("#%d", + position+1));
        userIdKeyTextView.setText("userId");
        userIdValueTextView.setText(result.getUserId());
        expiryKeyTextView.setText("Expiry");
        expiryValueTextView.setText(result.getExpiry());
        iDKeyTextView.setText("ID");
        iDValueTextView.setText("" + result.getID().longValue());
        nameKeyTextView.setText("Name");
        nameValueTextView.setText(result.getName());
        quantityKeyTextView.setText("Quantity");
        quantityValueTextView.setText(result.getQuantity());
        typeKeyTextView.setText("Type");
        typeValueTextView.setText(result.getType());
        return layout;
    }
}
