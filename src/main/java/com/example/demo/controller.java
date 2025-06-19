package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class controller {
	 
    @Autowired
    private RoomRepository rm;

    @Autowired
    private UserService userservice;

    @Autowired
    private manage ab;
    
    @Autowired
    private checkrepo cr;
    
    @Autowired
    private EmailService emailService;
   
    
    
    

    @GetMapping("/signup")
    public String signup() {
        return "signup"; 
    }
   
    @GetMapping("/page")
    public String page() {
        return "page"; 
    }
    @GetMapping("/about")
    public String about() {
        return "about"; 
    }



    @PostMapping("/add")
    public String add(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String email,
                      Model model) {

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {

            model.addAttribute("error", "All fields are required.");
            return "signup"; 
        }

        userdata ba = new userdata();
        ba.setUsername(username);
        ba.setPassword(password);
        ba.setEmail(email);
        ab.save(ba); 

        return "redirect:/home";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Optional<userdata> userOptional = ab.findByUsername(username);

        if (userOptional.isPresent()) {
            userdata user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return "redirect:/home"; // Login success
            }
        }

        model.addAttribute("error", "Invalid username or password");
        return "page"; // Login failed, show login page
    }
    @PostMapping("/rooms")
    public String addRoom(
                          @RequestParam String name,
                          @RequestParam int capacity,
                          @RequestParam String location,
                          @RequestParam double price,
                          @RequestParam String imageUrl,
                          Model model) {

        Room room = new Room();
        
        room.setName(name);
        room.setCapacity(capacity);
        room.setLocation(location);
        room.setPrice(price);
        room.setImageUrl(imageUrl);

        rm.save(room);
 
        return "admin";
    }
    @GetMapping("/booking")
    public String booking(Model model) {
//        List<Room> rooms = (List<Room>) rm.findAll();
    	List<Room> rooms = rm.findByBooked(false);

        model.addAttribute("rooms", rooms);
        return "booking";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Room> rooms = (List<Room>) rm.findAll();
        List<checkin> checkins = (List<checkin>) cr.findAll(); // Fetch all check-ins
        model.addAttribute("checkins", checkins);
        model.addAttribute("rooms", rooms);
        return "admin";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Room> rooms = (List<Room>) rm.findByBooked(false);
        model.addAttribute("rooms", rooms);
        return "home";
    }
    
@RequestMapping("/search")
	
	String findby(@RequestParam  String location,int capacity,  Model model)
	{
		List<Room> rooms=(List<Room>) rm.findByLocationAndCapacity(location, capacity);
		model.addAttribute("rooms",rooms);
		return "booking";
	}


    
    @GetMapping("/delete/{id}")
   String delete(@PathVariable Long id,Model model) {
      rm.deleteById(id);
      List<Room> rooms=(List<Room>) rm.findAll();
  	model.addAttribute("rooms",rooms);
  	return "redirect:/admin";
       } 
    @GetMapping("updatepage/{id}")
    String updatepage(@PathVariable("id") int id,Model model)
    {
         Room room=rm.findById(id).orElse(null);
            model.addAttribute("room",room );
            
            return "update";
    }
    @GetMapping("checkedout/{id}")
    String checkedout(@PathVariable("id") int id,Model model)
    {
         Room room=rm.findById(id).orElse(null);
            model.addAttribute("room",room );
            
            return "checkedout";
    }
    @RequestMapping("/update")

    public String update( 
    		@RequestParam int id, 
    		@RequestParam String name,
            @RequestParam int capacity,
            @RequestParam String location,
            @RequestParam double price,
            @RequestParam String imageUrl,
            @RequestParam String booked
            )
    {
    	
    	Room room=rm.findById(id).orElse(null);
    	room.setName(name);
        room.setCapacity(capacity);
        room.setLocation(location);
        room.setPrice(price);
        room.setImageUrl(imageUrl);

        rm.save(room);
    	return "redirect:/admin";
    	
    	
    }
    
    @RequestMapping("/checkedout")

    public String update( @RequestParam boolean booked, @RequestParam Long id)
    {
		Room room=rm.findById(id).orElse(null);
    	room.setBooked(booked);
        rm.save(room);
    	return "redirect:/admin";
    	
    	
    }
    
    
    @PostMapping("/checkins")
    public String checkout(
    		@RequestParam String name,
            @RequestParam String email,
//           @RequestParam String room,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam int zip,
            @RequestParam String cardname,
            @RequestParam String cardnumber,
            @RequestParam String expmonth,
            @RequestParam int expyear,
            @RequestParam int cvv,
            @RequestParam Long roomId,
            Model model) {
    	 
    	
    	Room selectedRoom = rm.findById(roomId).orElse(null);
    	if (selectedRoom == null || selectedRoom.getBooked()) 
            {
            model.addAttribute("error", "Room not available!");
            return "error-page";
            }
    	
checkin check = new checkin();
check.setName(name);
check.setEmail(email);
check.setRoomId(roomId);
//check.setRoom(roomId);
check.setAddress(address);
check.setCity(city);
check.setState(state);
check.setZip(zip);
check.setCardname(cardname);
check.setCardnumber(cardnumber);
check.setExpmonth(expmonth);
check.setExpyear(expyear);
check.setCvv(cvv);

cr.save(check);

selectedRoom.setBooked(true);


rm.save(selectedRoom);

String toEmail = email; // ✅ Email from backend
String subject = "Spring Boot Mail Test";
String body = "Hi " + name + ",\n\nThis is a test email from Spring Boot project.";

emailService.sendSimpleEmail(toEmail, subject, body); // 

model.addAttribute("confirmation", "Booking successful!");
return "confirmation";
}
    
    
    
    
    @GetMapping("/checkout")
    public String checkout(@RequestParam Long roomId, Model model) {
        Room room = rm.findById(roomId).orElse(null);
        if (room == null) {
            model.addAttribute("error", "Room not found!");
            return "error-page"; 
        }
        model.addAttribute("room", room);
        return "checkout";
    }

        
    @GetMapping("/func")
    public String func(Model model) {
        List <userdata> items = (List<userdata>) ab.findAll(); 
        model.addAttribute("items", items); 
        return "func"; 
    }
    
    
    @GetMapping("/bookings/{id}")
    public String bookings(@PathVariable("id") int id ,Model model) {
    	Room room=rm.findById(id).orElse(null);
        model.addAttribute("room",room );
        
        
        return "checkout"; 
    }
    
//    @RequestMapping("sent")   
//    
//    public String sent() {
//        checkin latestCheckin = cr.findTopByOrderByIdDesc(); // Get latest record from DB
//
//        if (latestCheckin != null) {
//            String toEmail = latestCheckin.getEmail(); // ✅ Email from backend
//            String subject = "Spring Boot Mail Test";
//            String body = "Hi " + latestCheckin.getName() + ",\n\nThis is a test email from Spring Boot project.";
//
//            emailService.sendSimpleEmail(toEmail, subject, body); // ✅ Sending email
//        }
//
//        return "demo";
//    } 
}
