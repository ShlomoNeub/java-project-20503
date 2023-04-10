package com.example.demo.controllers.rest;




import com.example.demo.db.entities.Constraint;
import com.example.demo.db.repo.ConstraintRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

    /**
     * Controller that implements the Constraint REST API
     */
    @RestController
    @RequestMapping(path = "/constraints")
    public class ConstraintController extends RestApiAbstract<Constraint, ConstraintRepo, Integer> {
        final Logger logger = LogManager.getLogger(ConstraintController.class);
        final ConstraintRepo repo;

        public ConstraintController(ConstraintRepo repo) {
            this.repo = repo;
        }

        @Override
        public ConstraintRepo getRepo() {
            return repo;
        }

        @Override
        public Logger getLogger() {
            return logger;
        }

        /**
         * Find all constraints with a specific type
         * @param typeId the type ID to search for
         * @return a list of constraints with the given type ID
         */
        @GetMapping("/type/{typeId}")
        public List<Constraint> findByTypeId(@PathVariable Integer typeId) {
            List<Constraint> constraints = repo.findByTypeId(typeId);
            if (constraints.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraints found with the given type ID");
            }
            return constraints;
        }

        /**
         * Find all constraints with a specific user in a given week
         * @param userId the user ID to search for
         * @param weekNumber the week number to search for
         * @return a list of constraints for the specified user in the given week
         */
        @GetMapping("/user/{userId}/week/{weekNumber}")
        public List<Constraint> findByUserIdAndWeekNumber(@PathVariable Integer userId,
                                                          @PathVariable Integer weekNumber) {
            List<Constraint> constraints = repo.findByUserIdAndWeekNumber(userId, weekNumber);
            if (constraints.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraints found with the given user ID AND WEEK NUMBER");
            }
            return constraints;
        }

/*** Find all constraints by type and week
 * @param typeId the type ID to search for
 * @param weekNumber the week number to search for
 * @return a list of constraints with the given type ID and week number
 */
@GetMapping("/type/{typeId}/week/{weekNumber}")
public List<Constraint> findByTypeIdAndWeekNumber(@PathVariable Integer typeId,
                                                  @PathVariable Integer weekNumber) {
    List<Constraint> constraints = repo.findByTypeIdAndWeekNumber(typeId, weekNumber);
    if (constraints.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No constraints found with the given type ID AND WEEK NUMBER");
    }
    return constraints;
}

    }