package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import exercise.model.Task;
import exercise.repository.TaskRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task show(@PathVariable long id) {

        var task =  taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        return task;
    }

    // BEGIN
//    POST /tasks — создание новой задачи
//    PUT /tasks/{id} — обновление задачи

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody Task task){
//        if(taskRepository.findAll().contains(task)){
//            throw new ResourceNotFoundException("Task " + task.getTitle() + " already exist");
//        }
        task.setCreatedAt(LocalDate.now());
        task.setUpdatedAt(LocalDate.now());

        return taskRepository.save(task);
    }


    @PutMapping("/{id}")
    public Task update(@PathVariable long id, @RequestBody Task task){
        var currentTask = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task with id " + id + " not found"));
        currentTask.setTitle(task.getTitle());
        currentTask.setDescription(task.getDescription());
        return taskRepository.save(currentTask);
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
