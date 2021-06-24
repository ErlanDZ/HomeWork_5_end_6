package com.example.work;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton btn_FAB;
    TaskAdapter adapter;
    int pos;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ListFragment() {

    }


    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        adapter = new TaskAdapter(requireContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        btn_FAB = view.findViewById(R.id.btn_FAB);
        recyclerView = view.findViewById(R.id.recycler_view_fragment_list);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));





        //принимает данные с вотого фрагмента с условиями редактирования и без
        getActivity().getSupportFragmentManager().setFragmentResultListener("title", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                TaskModel taskModel = (TaskModel) result.getSerializable("model");
                TaskModel update = (TaskModel) result.getSerializable("mod");

                if (taskModel != null){
                    adapter.addTask(taskModel);
                }else {
                    adapter.dataTry(pos, update);
                }
                //Toast.makeText(requireContext(), taskModel.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        // открываем второй фрагмент
        btn_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack("AddTaskFragment")
                        .replace(R.id.container_fragment, new AddTaskFragment()).commit();
            }
        });
// нажатие на item
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(int position, TaskModel model) {
                pos = position;
                Bundle bundle = new Bundle();
                TaskModel mod = new TaskModel(adapter.list.get(position).getTitle(),adapter.list.get(position).getDescription());
                bundle.putSerializable("mod", mod);
                getActivity().getSupportFragmentManager().setFragmentResult("edit",bundle);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack("AddTaskFragment")
                        .replace(R.id.container_fragment, new AddTaskFragment()).commit();

            }
        });

        return view;

    }
}
