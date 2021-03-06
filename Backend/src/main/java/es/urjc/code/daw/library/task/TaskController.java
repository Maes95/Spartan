package es.urjc.code.daw.library.task;

import es.urjc.code.daw.library.goal.Goal;
import es.urjc.code.daw.library.goal.GoalRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@RequestMapping(value = "/goal/{id}", method = RequestMethod.POST)
	public ResponseEntity<Task> newTask(@RequestBody Task task, @PathVariable(value="id") String id) {
		long id_i = -1;
		try{
			id_i = Long.parseLong(id);
		}catch(NumberFormatException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Goal goal = goalRepository.findOne(id_i);
		if(goal != null){
			taskRepository.save(task);
			System.out.println("ID1: "+task.getId());
			goal.getTasks().add(task);
			System.out.println("ID2: "+task.getId());
			System.out.println("IDGOAL: "+goal.getId());
			goalRepository.save(goal);
			System.out.println("ID3: "+task.getId());
			return new ResponseEntity<>(task, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<Task> editTask(@RequestBody Task task) {
		if(goalRepository.findOne(task.getId()) != null){
			taskRepository.save(task);
			return new ResponseEntity<>(task, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Task> deleteTask(@PathVariable(value="id") String id) {
		long id_i= -1;
		try{
			id_i = Long.parseLong(id);
		}catch(NumberFormatException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Task task = taskRepository.findOne(id_i);
		taskRepository.delete(task);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	
		
}
