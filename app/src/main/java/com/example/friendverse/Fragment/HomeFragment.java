package com.example.friendverse.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.friendverse.Adapter.StoryAdapter;
import com.example.friendverse.AddPost;
import com.example.friendverse.Model.Story;
import com.example.friendverse.ChatApp.ChatActivity;
import com.example.friendverse.Models.PostModel;
import com.example.friendverse.R;
import com.example.friendverse.Adapter.PostAdapter;
import com.example.friendverse.Model.Post;
import com.example.friendverse.ViewModel.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ImageView post;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postLists;


    private RecyclerView recyclerViewStory;
    private List<Story> storyList;
    private StoryAdapter storyAdapter;

    private int currentPosition;
    private List<String> followingList;
    private ImageView chatBtn;
    public static int position = 0;
    private PostViewModel postViewModel;
    private boolean isLoading = false;
    private Handler handler = new Handler();
    private int countdown = 3;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //New
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        System.out.println("Posts: ");
        System.out.println(postViewModel.getPosts());


        //New
        post = view.findViewById(R.id.post);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postLists);
        recyclerView.setAdapter(postAdapter);


        recyclerViewStory = view.findViewById(R.id.recycler_view_story);
        recyclerViewStory.setHasFixedSize(true);
        storyList = new ArrayList<>();
        storyAdapter = new StoryAdapter(getContext(), storyList);
        recyclerViewStory.setAdapter(storyAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStory.setLayoutManager(linearLayoutManager1);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddPost.class);
                startActivity(i);
            }
        });

        chatBtn = view.findViewById(R.id.chatButton);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                startActivity(i);
            }
        });
        checkFollowing();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(position);
//                return null;
            }
        }, 0);
        setupScrollListener();
        ObserveAnyChange();
        return view;
    }


    private void checkFollowing() {
        followingList = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                    .child(uid)
                    .child("following");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    followingList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        followingList.add(snapshot.getKey());
                    }
                    System.out.println("Check Following Load");
                    getAllPosts();
                    readStory();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (position <= postAdapter.getItemCount()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RecyclerView.LayoutManager tf = new LinearLayoutManager(getContext());
                    tf.scrollToPosition(position);
                    recyclerView.scrollToPosition(position);
//                    return null;
                }
            }, 0);
        }
    }

    //New
    private void ObserveAnyChange() {
        postViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
//                if (postModels != null) {
//                    try {
//                        postLists.clear();
//                        for (PostModel postModel : postModels) {
//                            for (String id : followingList) {
//                                if (postModel.getPublisher() != null && postModel.getPublisher().equals(id)) {
//                                    postLists.add(new Post(postModel.getPostid(), postModel.getImagine(), postModel.getUsername(), postModel.getPostType(), postModel.getSharer(), postModel.isShared(), postModel.getPostimage(), postModel.getPostvid(), postModel.getDescription(), postModel.getPublisher()));
//                                }
//                            }
//                        }
//                        System.out.println("Reload");
//                        postAdapter.notifyDataSetChanged();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
                if (postModels != null) {
                    postLists.addAll(transformPostModels(postModels));
                    postAdapter.notifyDataSetChanged();
                    isLoading = false; // Reset loading flag
                }
            }
        });
    }

    private List<Post> transformPostModels(List<PostModel> postModels) {
        List<Post> transformedPosts = new ArrayList<>();
        for (PostModel postModel : postModels) {
            for (String id : followingList) {
                if (postModel.getPublisher() != null && postModel.getPublisher().equals(id)) {
                    transformedPosts.add(new Post(postModel.getPostid(), postModel.getImagine(), postModel.getUsername(), postModel.getPostType(), postModel.getSharer(), postModel.isShared(), postModel.getPostimage(), postModel.getPostvid(), postModel.getDescription(), postModel.getPublisher()));
                }
            }
        }
        return transformedPosts;
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postLists.size() - 1) {
                    isLoading = true; // Set loading flag
                    System.out.println( "Loading...");
                    // Countdown loading...
                    countdown = 3; // Reset countdown
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (countdown > 0) {
                                System.out.println(countdown);
                                countdown--;
                                handler.postDelayed(this, 1000);
                            } else {
                                postViewModel.getAllPosts();
                            }
                        }
                    }, 1000);
                }
            }
        });
    }

    private void getAllPosts() {
        postViewModel.getAllPosts();
    }

    //New
    @Override
    public void onDestroy() {
        super.onDestroy();
        checkFollowing();
    }

//    private void readPosts() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                postLists.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Post post = snapshot.getValue(Post.class);
//                    for (String id : followingList) {
//                        if (post.getPublisher() != null && post.getPublisher().equals(id)) {
//                            postLists.add(post);
//                        }
//                    }
//                }
//
//                postAdapter.notifyDataSetChanged();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.scrollToPosition(position);
////                        return null;
//                    }
//                }, 0);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

    private void readStory() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long timecurrent = System.currentTimeMillis();
                storyList.clear();
                storyList.add(new Story("", FirebaseAuth.getInstance().getCurrentUser().getUid(), "", 0, 0));
                for (String id : followingList) {
                    int countStory = 0;
                    Story story = null;
                    for (DataSnapshot snapshot : dataSnapshot.child(id).getChildren()) {
                        story = snapshot.getValue(Story.class);
                        if (timecurrent > story.getTimeCreated() && timecurrent < story.getAfter1day()) {
                            countStory++;
                        }
                    }
                    if (countStory > 0)
                        storyList.add(story);
                }
                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
