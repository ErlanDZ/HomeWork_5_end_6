package com.example.work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddTaskFragment extends Fragment {
    EditText etSecondFragment, etSecondFragmentDescription;
    Button btnAdd;
    TaskModel taskModel;
    TaskModel mod;
    TaskAdapter adapter;
    public static boolean isEdit = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddTaskFragment() {

    }


    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        etSecondFragment = view.findViewById(R.id.et_second_fragment);
        etSecondFragmentDescription = view.findViewById(R.id.et_second_fragment_description);
        btnAdd = view.findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String title = etSecondFragment.getText().toString();
                    String description = etSecondFragmentDescription.getText().toString();
                    taskModel = new TaskModel(title, description);
                    Bundle bundle = new Bundle();
                    if (mod == null){
                        bundle.putSerializable("model", taskModel);
                    }else {
                        mod = new TaskModel(title, description);
                        bundle.putSerializable("mod", mod);
                    }
                    getActivity().getSupportFragmentManager().setFragmentResult("title", bundle);
                    getActivity().getSupportFragmentManager().popBackStack();
                    Log.e("TAG", "AddTaskFragment: btnAdd " + bundle);
            }
        });
        edit();

        return view;
    }

    private void edit() {
        getActivity().getSupportFragmentManager().setFragmentResultListener("edit", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                mod = (TaskModel) result.getSerializable("mod");
                etSecondFragment.setText(mod.getTitle());
                etSecondFragmentDescription.setText(mod.getDescription());
            }
        });

    }
}