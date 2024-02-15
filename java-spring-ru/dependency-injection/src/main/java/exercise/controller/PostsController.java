package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> index() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post show(@PathVariable long id) {
        return postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        post.setCreatedAt(LocalDate.now());
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable long id, @RequestBody Post post){
        var currentPost = postRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Post with id " + id + " not found"));
        currentPost.setTitle(post.getTitle());
        currentPost.setBody(post.getBody());
        return postRepository.save(currentPost);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}


// END
//GET /posts — список всех постов
//GET /posts/{id} – просмотр конкретного поста
//POST /posts – создание нового поста. При успешном создании возвращается статус 201
//PUT /posts/{id} – обновление поста
//DELETE /posts/{id} – удаление поста. При удалении поста удаляются все комментарии этого поста