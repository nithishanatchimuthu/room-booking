package com.example.demo;

import jakarta.persistence.*;

@Entity
public class checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This will be the primary key and auto-increment

    private String name;
    private String email;
    private Long roomId;
   
//    @JoinColumn(name = "room_id") // FK to Room table
//    private String room;

    private String address;
    private String city;
    private String state;
    private int zip;
    private String cardname;
    private String cardnumber;
    private String expmonth;
    private int expyear;
    private int cvv;

    // getters and setters
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public String getRoom() {
//		return room;
//	}
//	public void setRoom(String room) {
//		this.room = room;
//	}
	public String getAddress() {
	    return address;
	}

	public void setAddress(String address) {
	    this.address = address;
	}

	public String getCity() {
	    return city;
	}

	public void setCity(String city) {
	    this.city = city;
	}

	public String getState() {
	    return state;
	}

	public void setState(String state) {
	    this.state = state;
	}

	public int getZip() {
	    return zip;
	}

	public void setZip(int zip) {
	    this.zip = zip;
	}

	public String getCardname() {
	    return cardname;
	}

	public void setCardname(String cardname) {
	    this.cardname = cardname;
	}

	public String getCardnumber() {
	    return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
	    this.cardnumber = cardnumber;
	}

	public String getExpmonth() {
	    return expmonth;
	}

	public void setExpmonth(String expmonth) {
	    this.expmonth = expmonth;
	}

	public int getExpyear() {
	    return expyear;
	}

	public void setExpyear(int expyear) {
	    this.expyear = expyear;
	}

	public int getCvv() {
	    return cvv;
	}

	public void setCvv(int cvv) {
	    this.cvv = cvv;
	}
	public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
