package com.jm.mvp.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.R;
import com.jm.utils.DensityUtils;

/**
 * @author pc 张立男
 * @Description SearchDialogFragment 搜索的弹框
 * @date 2018/6/23 20:39
 * o(＞﹏＜)o
 */

public class SearchDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_search, null);
        final EditText etSearch = (EditText) view.findViewById(R.id.et_pop_search);
        TextView tvSearch = (TextView) view.findViewById(R.id.tv_pop_select);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchDialog != null) {
                    String tel = etSearch.getText().toString().trim();
                    mSearchDialog.search(tel);
                    dismiss();
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.hotel_star_dialog_top_style);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getScreenHeight(getActivity()) - getResources().getDimension(R.dimen.y180)));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private SearchDialog mSearchDialog;

    public SearchDialog getSearchDialog() {
        return mSearchDialog;
    }

    public void setSearchDialog(SearchDialog searchDialog) {
        mSearchDialog = searchDialog;
    }

    interface SearchDialog {
        void search(String tel);
    }
}
