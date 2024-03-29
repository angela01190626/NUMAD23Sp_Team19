package edu.northeastern.cs5520groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.northeastern.cs5520groupproject.post.Post;
import edu.northeastern.cs5520groupproject.post.PostAdapter;

public class PostFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private List<Post> postList;

    private ProgressBar progressBar;

    private ImageView newPostButton;

    Switch smartFeedSwitch;

    FirebaseUser currentUser;

    Boolean isChecked;

    DatabaseReference databaseReference;

    DatabaseReference userLikedHashtagsRef;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        recyclerView = view.findViewById(R.id.recycler_view1);
        // recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext() , postList);
        recyclerView.setAdapter(postAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        progressBar = view.findViewById(R.id.progress_circular);
        newPostButton = view.findViewById(R.id.add_new_post);
        smartFeedSwitch = view.findViewById(R.id.smartFeedSwitch);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        userLikedHashtagsRef = FirebaseDatabase.getInstance().getReference("userLikedHashtags").child(currentUser.getUid());

        isChecked = false;

        newPostButton.setOnClickListener((v) -> {
            Intent myIntent = new Intent(getActivity(), AddPostActivity.class);
            startActivity(myIntent);
        });

        smartFeedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            progressBar.setVisibility(View.VISIBLE);
            fetchPosts(isChecked);
        });

        fetchPosts(isChecked);

        return view;
    }

    private void fetchPosts(boolean fetchSmartFeed) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                if (fetchSmartFeed) {
                    userLikedHashtagsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot likedHashtagsSnapshot) {
                            Set<String> likedHashtags = new HashSet<>();
                            for (DataSnapshot snapshot : likedHashtagsSnapshot.getChildren()) {
                                likedHashtags.add(snapshot.getKey());
                            }

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Post post = postSnapshot.getValue(Post.class);
                                String postContent = post.getDescription();
                                Pattern pattern = Pattern.compile("(?<=^|\\s)#\\w+");
                                Matcher matcher = pattern.matcher(postContent);
                                boolean hasLikedHashtags = false;

                                while (matcher.find()) {
                                    if (likedHashtags.contains(matcher.group().substring(1))) {
                                        hasLikedHashtags = true;
                                        break;
                                    }
                                }

                                if (hasLikedHashtags) {
                                    postList.add(post);
                                }
                            }

                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Error fetching liked hashtags: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Post post = postSnapshot.getValue(Post.class);
                        postList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error fetching posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressBar.setVisibility(View.GONE);
    }
}
