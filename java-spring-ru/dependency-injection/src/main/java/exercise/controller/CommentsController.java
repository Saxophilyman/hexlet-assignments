package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> index(){
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment show(@PathVariable long id){
        return commentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment){
        //comment.setBody(comment.getBody());
        comment.setCreatedAt(LocalDate.now());
        return commentRepository.save(comment);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment comment){
        var currentComment = commentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Comment with id " + id + " not found"));
        currentComment.setBody(comment.getBody());
        return commentRepository.save(currentComment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        commentRepository.deleteById(id);
    }
}
// END
//GET /comments — список всех комментариев
//GET /comments/{id} – просмотр конкретного комментария
//POST /comments – создание нового комментария. При успешном создании возвращается статус 201
//PUT /comments/{id} – обновление комментария
//DELETE /comments/{id} – удаление комментария
