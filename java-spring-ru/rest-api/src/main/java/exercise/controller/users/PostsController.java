package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{userId}/posts")
    public List<Post> show(@PathVariable Integer userId) {
        return posts.stream()
                .filter(p -> p.getUserId() == userId).toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/posts")
    public Post create(@PathVariable Integer userId, @RequestBody Post post) {
        post.setUserId(userId);
        posts.add(post);
        return post;
    }


//    public PostsController(List<Post> posts) {
//        this.posts = posts;
//    }
//
//    @GetMapping("/users/{id}/posts")
//    public ResponseEntity<ArrayList<Post>> showUsersPosts(@PathVariable String id) {
//        ArrayList<Post> userPosts = new ArrayList<>();
//        for (Post post : posts) {
//            if (Integer.parseInt(id) == post.getUserId()) {
//                userPosts.add(post);
//            }
//        }
//        return ResponseEntity.ok().body(userPosts);
//
//    }
//
//
//    @PostMapping("/users/{id}/posts")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Post> userCreate(@PathVariable String id, @RequestBody Post data) {
//        var maybeUser = posts.stream()
//                .filter(p -> p.getUserId() == Integer.parseInt(id))
//                .findFirst();
//        Post post = new Post();
//        if (maybeUser.isPresent()) {
//            post.setUserId(data.getUserId());
//            post.setSlug(data.getSlug());
//            post.setTitle(data.getTitle());
//            post.setBody(data.getBody());
//        }
//        posts.add(post);
//        return ResponseEntity.created(URI.create("/posts")).body(post);
//    }
// END
}