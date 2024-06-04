package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminRepository ar;

	@Autowired
	RestTemplate rs;

	@GetMapping("/read")
	public List<Admin> read() {
		return ar.findAll();
	}

	@GetMapping("/readname/{name}")
	public Optional<Admin> readbyname(@PathVariable long id) {
		return ar.findById(id);
	}

	@PostMapping("/add")
	public Admin add(@RequestBody Admin e) {
		return ar.save(e);
	}

	@PutMapping("/update/{id}")
	public void update(@RequestBody long id, @RequestBody Admin e) {
		Optional<Admin> Admin = ar.findById(id);
		ar.deleteById(id);
		ar.save(Admin.get());
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		ar.deleteById(id);
	}

	@GetMapping("/readname2/{id}")
	public String readbal(@PathVariable Long id) {
		String baseUrl = "http://localhost:8888/customer/readname/" + id;
		String response = rs.getForObject(baseUrl, String.class);
		return response;
	}

	@DeleteMapping("/deletename1/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		String baseUrl = "http://localhost:8888/customer/delete/" + id;
		rs.delete(baseUrl);
		return "Deleted successfully";
	}

	@GetMapping("/readbal/{id}")
	public String readBalanceById(@PathVariable Long id) {
		String baseUrl = "http://localhost:8888/customer/readbalance/" + id;
		ResponseEntity<Double> response = rs.getForEntity(baseUrl, Double.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			Double balance = response.getBody();
			return "Balance for customer with ID " + id + " is: " + balance;
		} else {
			return "Failed to fetch balance for customer with ID " + id;
		}
	}

}
