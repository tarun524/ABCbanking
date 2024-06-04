package com.example.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerRepository br;

	@GetMapping("/read")
	public List<Customer> read() {
		return br.findAll();
	}

	@GetMapping("/readname/{id}")
	public Optional<Customer> readbyname(@PathVariable long id) {
		return br.findById(id);
	}

	@PostMapping("/add")
	public Customer add(@RequestBody Customer e) {
		return br.save(e);
	}

//	@PutMapping("/update/{id}")
//	public void update(@RequestBody long id, @RequestBody Customer e) {
//		Optional<Customer> Customer = br.findById(id);
//		br.deleteById(id);
//		br.save(Customer.get());
//	}
	@PutMapping("/update/{id}")
    public Customer update(@PathVariable long id, @RequestBody Customer e) {
        Optional<Customer> existingCustomer = br.findById(id);
 
        if (existingCustomer.isPresent()) {
            Customer customerToUpdate = existingCustomer.get();
            customerToUpdate.setName(e.getName());
            customerToUpdate.setBalance(e.getBalance());
            customerToUpdate.setEmail(e.getEmail());
            customerToUpdate.setAadharcardnumber(e.getAadharcardnumber());
            customerToUpdate.setAccoutnumber(e.getAccoutnumber());
            customerToUpdate.setPhonenumber(e.getPhonenumber());
            customerToUpdate.setUsername(e.getUsername());
            customerToUpdate.setPassword(e.getPassword());
            return br.save(customerToUpdate);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		br.deleteById(id);
	}

	@GetMapping("/readbalance/{id}")
	public ResponseEntity<Double> readBalanceById(@PathVariable long id) {
		Optional<Customer> customerOptional = br.findById(id);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			Double balance = customer.getBalance(); // Assuming 'balance' is a field in Customer class
			return ResponseEntity.ok(balance);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}