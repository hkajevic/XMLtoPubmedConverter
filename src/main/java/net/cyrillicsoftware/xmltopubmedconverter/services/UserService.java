package net.cyrillicsoftware.xmltopubmedconverter.services;

import net.cyrillicsoftware.xmltopubmedconverter.entities.User;
import net.cyrillicsoftware.xmltopubmedconverter.reposotories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "user1", "user1_123"));
        users.add(new User(2, "user2", "user2_123"));
        users.add(new User(3, "user3", "user3_123"));
        users.add(new User(4, "user4", "user4_123"));
        users.add(new User(5, "user5", "user5_123"));
    }

    public User getUserByUsername(String username){
        for(User user: users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
