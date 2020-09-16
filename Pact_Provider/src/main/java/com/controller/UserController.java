package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{

    @GetMapping("/pact")
    public UserDetails getUser()
    {
        return new UserDetails(1, 1, "Bangalore", "Karnataka", "India");
    }

}

class UserDetails
{

    public int id;
    public int name;
    public String city;
    public String state;
    public String country;

    public UserDetails(int id, int name, String city, String state, String country)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}