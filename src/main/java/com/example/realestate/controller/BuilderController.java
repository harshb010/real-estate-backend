package com.example.realestate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.realestate.entity.Builder;
import com.example.realestate.exception.CustomException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.service.BuilderService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/builders")
public class BuilderController {

    @Autowired
    private BuilderService builderService;

    @PostMapping
    public ResponseEntity<Builder> addBuilder(@RequestBody Builder builder) {
        Builder newBuilder = builderService.addBuilder(builder);
        return ResponseEntity.ok(newBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Builder> getBuilderById(@PathVariable Long id) {
        Builder builder = builderService.findById(id);
        return ResponseEntity.ok(builder);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Builder>> getAllBuilders() {
        List<Builder> builders = builderService.findAll();
        return ResponseEntity.ok(builders);
    }

    @GetMapping("/getbyemail")
	public ResponseEntity<?> getByEmail(@RequestHeader("Authorization") String token) {
		try {
			Builder builder = builderService.findBuilderProfileByJwt(token);
			String email = builder.getEmail();
			return new ResponseEntity<>(builderService.GetByEmail(email), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
    @PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Builder builderRef) {
		try {
			return builderService.updateBuilder(builderRef);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBuilder(@PathVariable Long id) {
		try {
			return builderService.deleteBuilder(id);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
   }

