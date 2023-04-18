package edu.northeastern.cs5520groupproject.post;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520groupproject.R;
import edu.northeastern.cs5520groupproject.login.User;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public List<Post> postList;
    public Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final Post post = postList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(post.getImageUrl())
                .placeholder(R.drawable.baseline_cloud_upload_24)
                .into(holder.post_image);
        holder.description.setVisibility(View.VISIBLE);
        holder.description.setText(post.getDescription());

        // Set the user's display name in the profile photo ImageView
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String displayName = firebaseUser.getDisplayName();
            holder.username.setText(displayName);
            holder.image_profile.setText(displayName);
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the post ID
                String postId = postList.get(position).getPostId();

                // Create a clipboard manager and copy the post ID to clipboard
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Post ID", postId);
                clipboard.setPrimaryClip(clip);

                // Show a toast to inform the user that the post ID has been copied
                Toast.makeText(context, "Post ID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        // Handle like button click
        isPostLiked(post.getPostId(), currentUser.getUid(), new LikeStatusListener() {
            @Override
            public void onLikeStatus(boolean isLiked) {
                if (isLiked) {
                    holder.like.setImageResource(R.drawable.ic_liked);
                } else {
                    holder.like.setImageResource(R.drawable.ic_like);
                }
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLike(post.getPostId(), holder.like, holder.likes);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context, post.getPostId());
            }
        });



        // Display the total number of likes
        DatabaseReference likeCountRef = FirebaseDatabase.getInstance().getReference("likes").child(post.getPostId());
        likeCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int likeCount = dataSnapshot.getValue(Integer.class);
                    holder.likes.setText(likeCount + " likes");
                } else {
                    holder.likes.setText("0 likes");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PostAdapter", "Error loading like count: " + databaseError.getMessage());
            }
        });
    }

    private interface LikeStatusListener {
        void onLikeStatus(boolean isLiked);
    }

    private void isPostLiked(String postId, String userId, LikeStatusListener listener) {
        DatabaseReference postLikesRef = FirebaseDatabase.getInstance().getReference("post-likes").child(postId).child(userId);
        postLikesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onLikeStatus(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void toggleLike(final String postId, final ImageView likeImage, final TextView likeCount) {
        DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("post-likes");
        likeRef.child(postId).child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the user has already liked the post, unlike it
                    unlikePost(postId, likeImage, likeCount);
                } else {
                    // If the user hasn't liked the post yet, like it
                    likePost(postId, likeImage, likeCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }


    private void likePost(final String postId, final ImageView likeImage, final TextView likeCount) {
        DatabaseReference postLikesRef = FirebaseDatabase.getInstance().getReference("post-likes").child(postId);
        DatabaseReference likeCountRef = FirebaseDatabase.getInstance().getReference("likes").child(postId);

        postLikesRef.child(currentUser.getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                likeImage.setImageResource(R.drawable.ic_liked);
                int currentLikeCount = Integer.parseInt(likeCount.getText().toString().replaceAll("\\D+", ""));
                likeCount.setText(String.format("%d likes", currentLikeCount + 1));

                // Update the total number of likes in Firebase
                likeCountRef.setValue(currentLikeCount + 1);
            }
        });
    }

    private void unlikePost(final String postId, final ImageView likeImage, final TextView likeCount) {
        DatabaseReference postLikesRef = FirebaseDatabase.getInstance().getReference("post-likes").child(postId);
        DatabaseReference likeCountRef = FirebaseDatabase.getInstance().getReference("likes").child(postId);

        postLikesRef.child(currentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                likeImage.setImageResource(R.drawable.ic_like);
                int currentLikeCount = Integer.parseInt(likeCount.getText().toString().replaceAll("\\D+", ""));
                likeCount.setText(String.format("%d likes", currentLikeCount - 1));

                // Update the total number of likes in Firebase
                likeCountRef.setValue(currentLikeCount - 1);
            }
        });
    }

    public static void showDialog(Context context, String postId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comments_dialog, null);

        RecyclerView recyclerView = view.findViewById(R.id.comment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        EditText commentEditText = view.findViewById(R.id.comment_edit_text);
        Button publishCommentButton = view.findViewById(R.id.publish_comment_button);

        List<Comment> comments = new ArrayList<>();
        CommentAdapter commentAdapter = new CommentAdapter(context, comments);
        recyclerView.setAdapter(commentAdapter);

        // Fetch comments
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("comments").child(postId);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                comments.add(comment);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CommentDialog", "Error loading comments: " + databaseError.getMessage());
            }
        };
        commentsRef.addChildEventListener(childEventListener);

        // Publish a new comment
        publishCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEditText.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    publishComment(context, postId, commentText);
                    commentEditText.setText("");
                } else {
                    Toast.makeText(context, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private static void publishComment(Context context, String postId, String commentText) {
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("comments").child(postId);
        String commentId = commentsRef.push().getKey();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        Comment comment = new Comment(commentId, userId, commentText, firebaseUser.getDisplayName());

        commentsRef.child(commentId).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Comment published", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error publishing comment: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView image_profile;
        public ImageView post_image;
        public ImageView like;
        public ImageView comment;

        public TextView username;
        public TextView likes;
        public TextView publisher;
        public TextView description;
        public TextView comments;
        public ImageView share;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);

            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            comments = itemView.findViewById(R.id.comments);
            share = itemView.findViewById(R.id.share);
        }
    }
}
