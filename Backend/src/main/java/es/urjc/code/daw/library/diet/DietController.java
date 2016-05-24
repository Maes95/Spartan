package es.urjc.code.daw.library.diet;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.daw.library.goal.Goal;
import es.urjc.code.daw.library.goal.GoalRepository;
import es.urjc.code.daw.library.task.Task;
import es.urjc.code.daw.library.user.User;
import es.urjc.code.daw.library.user.UserComponent;
import es.urjc.code.daw.library.user.UserRepository;


@RestController
@RequestMapping("/diet")
public class DietController {
	
	@Autowired
	private DietRepository dietRepository;
	@Autowired
	private GoalRepository goalRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserComponent user;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Diet> newGoal(@RequestBody Diet diet) {
			User userLogado =user.getLoggedUser();
			Goal goal = userRepository.findOne(userLogado.getId()).getGoals()
					.stream().filter( g -> g.isActive()).collect(Collectors.toList()).get(0);
			
			goal.setDiet(diet);
			
			dietRepository.save(diet);
			goalRepository.save(goal);
			userRepository.save(userLogado);
	    // LA ÑAPA DE LAS ÑAPAS, HAY QUE VER COMO MIERDAS HACER ESTO BIEN
		//goal = userRepository.findOne(userLogado.getId()).getGoals().get(userLogado.getGoals().size()-1);

		return new ResponseEntity<>(diet, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<Diet> modifyGoal(@RequestBody Diet diet) {
		if(dietRepository.findOne(diet.getId()) != null){
			dietRepository.save(diet);
			return new ResponseEntity<>(diet, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}

}