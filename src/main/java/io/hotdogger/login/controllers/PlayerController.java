package io.hotdogger.login.controllers;

import io.catalyte.training.tbm.exceptions.ResourceNotFound;
import io.catalyte.training.tbm.exceptions.ServiceUnavailable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller class for the Customers domain. It helps in taking in requests from the client and
 * sending back responses.
 */
@RestController
@RequestMapping("/customers")
public class PlayerController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);

    /**
     * Sets up the dependency for the Customer Controller which is dependent on Customer Service.
     */
    @Autowired
    private CustomerService customerService;

    /**
     * Takes in a POST request and calls the Customer service to perform the createCustomer method,
     * and returns a response and CREATED 201 status code.
     *
     * @param newCustomer -type Customer- a JSON object of the new Customer passed by the client.
     * @return a response of the new created Customer (in JSON), and a status code of 201.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // for swagger to only display the responses listed here
    @ApiOperation("Add a new customer into a database.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer newCustomer) {
        logger.info("Post customer request received " + newCustomer.toString());

        return new ResponseEntity<>(customerService.createCustomer(newCustomer), HttpStatus.CREATED);
    }

    /**
     * Takes in a GET request and calls the Customer service to perform the getAllCustomers method,
     * which returns a response and 0K 200 status code.
     *
     * @return a response which contains a list of all the customers (in JSON), and a 200 status code.
     */
    @GetMapping
    @ApiOperation("Gets back all customers from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Get all customers request received");

        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    /**
     * Takes in a GET request and calls the Customer service to perform the getCustomerById method,
     * which returns a response and 0K 200 status code.
     *
     * @param customerId -type Long- the id of an existing Customer object.
     * @return a response that is the Customer (in JSON) specified from user, and a 200 status code.
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("Gets a customer from the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Customer.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long customerId) {
        logger.info("Get customer by id request received: customer id: " + customerId);

        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * Takes in a PUT request and calls the Customer service to perform updateCustomerById method,
     * then returns a response and 0K 200 status code.
     *
     * @param customerId      -type Long- the id of an existing Customer object.
     * @param updatedCustomer -type Customer- the Customer object that contains the updated
     *                        data/version sent by the client.
     * @return a response that is the updated version of an existing Customer (in JSON), and a 200
     * status code.
     */
    @PutMapping(value = "/{id}")
    @ApiOperation("Updates a customer in the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Customer.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") Long customerId,
                                                       @Valid @RequestBody Customer updatedCustomer) {
        logger.info("Update customer by id request received: customer id: " + customerId);

        return new ResponseEntity<>(customerService.updateCustomerById(customerId, updatedCustomer),
                HttpStatus.OK);
    }

    /**
     * Takes in a DELETE request and calls the Customer service to perform deleteCustomerById method,
     * and returns a NO CONTENT status code if performed successfully.
     *
     * @param customerId -type Long- the id of an existing Customer the client wants to delete.
     * @return a NO CONTENT 204 status code when the Customer has been deleted.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // for swagger to display only the status codes listed here
    @ApiOperation("Deletes a customer in the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content", response = Customer.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") Long customerId) {
        logger.info("Delete customer by id request received: customer id: " + customerId);

        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
