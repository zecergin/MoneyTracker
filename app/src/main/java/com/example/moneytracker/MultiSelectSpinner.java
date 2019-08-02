package com.example.moneytracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultiSelectSpinner extends android.support.v7.widget.AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {

    String[] mItems = null;
    boolean[] mSelect = null;


    ArrayAdapter<String> simple_adapter;

    public MultiSelectSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelect != null && which < mSelect.length) {
            boolean isEmpty = true;
            for (boolean is : mSelect) {
                if (is) {
                    isEmpty = false;
                }
            }
            if (isEmpty == true) {
                mSelect[0] = true;
                simple_adapter.clear();
                simple_adapter.add(buildSelectedItemString());
                mSelect[0] = false;
            } else if (mSelect[0] == true) {
                boolean[] mSelectionHelper = new boolean[mSelect.length];
                System.arraycopy(mSelect, 0, mSelectionHelper, 0, mSelect.length);
                for (int i = 1; i < mSelect.length; i++) {
                    mSelect[i] = false;
                }
                simple_adapter.clear();
                simple_adapter.add(buildSelectedItemString());
                System.arraycopy(mSelectionHelper, 0, mSelect, 0, mSelectionHelper.length);
            } else {
                mSelect[which] = isChecked;
                simple_adapter.clear();
                simple_adapter.add(buildSelectedItemString());
            }


        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(mItems, mSelect, this);
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItemList(String[] items) {
        mItems = items;
        mSelect = new boolean[mItems.length];
        simple_adapter.clear();
        simple_adapter.add(mItems[0]);
        Arrays.fill(mSelect, false);
    }

    public void setItemList(List<String> items) {
        mItems = items.toArray(new String[items.size()]);
        mSelect = new boolean[mItems.length];
        simple_adapter.clear();
        simple_adapter.add(mItems[0]);
        Arrays.fill(mSelect, false);
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < mItems.length; ++j) {
                if (mItems[j].equals(cell)) {
                    mSelect[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelect.length; i++) {
            mSelect[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < mItems.length; ++j) {
                if (mItems[j].equals(sel)) {
                    mSelect[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelect.length; i++) {
            mSelect[i] = false;
        }
        if (index >= 0 && index < mSelect.length) {
            mSelect[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndicies) {
        for (int i = 0; i < mSelect.length; i++) {
            mSelect[i] = false;
        }
        for (int index : selectedIndicies) {
            if (index >= 0 && index < mSelect.length) {
                mSelect[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < mItems.length; ++i) {
            if (mSelect[i]) {
                selection.add(mItems[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < mItems.length; ++i) {
            if (mSelect[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < mItems.length; ++i) {
            if (mSelect[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(mItems[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < mItems.length; ++i) {
            if (mSelect[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(mItems[i]);
            }
        }
        return sb.toString();
    }

}