package com.rpll.okeoke.bettingplatform.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Adapter.BetAdapter;
import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private ArrayList<Match> matches = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth auth;
    private TextView txtStats;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.viewBet);
        mRecyclerView.setHasFixedSize(true);
        auth = FirebaseAuth.getInstance();
        final String email = auth.getCurrentUser().getEmail();
        final String encodedEmail = User.encodeUserEmail(email);
        mLayoutManager = new LinearLayoutManager(getActivity());
        txtStats = (TextView) getActivity().findViewById(R.id.txtStats);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myRef = database.getReference("Matches");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // specify an adapter (see also next example)
                long totalBet = dataSnapshot.getChildrenCount();
                matches = new ArrayList<>();
                int totalMatch = 0;
                int win = 0;
                int reward = 0;
                Match match;
                for (int i = 0; i < totalBet; i++) {
                    match = new Match();
                    match.setTeam_1(dataSnapshot.child(Integer.toString(i)).child("team_1").getValue(String.class));
                    match.setTeam_2(dataSnapshot.child(Integer.toString(i)).child("team_2").getValue(String.class));
                    match.setId_match(dataSnapshot.child(Integer.toString(i)).child("idmatch").getValue(String.class));
                    match.setStatus(dataSnapshot.child(Integer.toString(i)).child("status").getValue(String.class));
                    if(dataSnapshot.child(Integer.toString(i)).child("betting").child(encodedEmail).exists())
                    {
                        matches.add(match);
                        totalMatch++;
                        if(dataSnapshot.child(Integer.toString(i)).child("betting").child(encodedEmail).child("selected_team").getValue(int.class)==
                                dataSnapshot.child(Integer.toString(i)).child("winner").getValue(int.class)){
                                win++;
                                reward+=dataSnapshot.child(Integer.toString(i)).child("betting").child(encodedEmail).child("bet_value").getValue(int.class);
                        }
                        else
                        {
                            reward-=dataSnapshot.child(Integer.toString(i)).child("betting").child(encodedEmail).child("bet_value").getValue(int.class);
                        }
                    }
                }
                mAdapter = new BetAdapter(matches, getContext());
                mRecyclerView.setAdapter(mAdapter);
                double winRate = win / totalMatch;
                NumberFormat formatter = new DecimalFormat("#0.00");
                txtStats.setText("Total Match: "+totalMatch+"\n"+"Win Rate: "+formatter.format(winRate)+"%"+"\n"+"All Rewards: "+reward+"\n");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
