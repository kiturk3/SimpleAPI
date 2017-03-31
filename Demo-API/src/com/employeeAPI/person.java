package com.employeeAPI;
/**
 * 
@author
Radoslaw Choromanski Id nr-14020101
 */

// this tables duplicates the personal information of the employee stored in the database 
public class person {
    private String name;
    private String gender;
    private String nin;
    private String dob;
    private String address;
    private String postcode;
    private String email;
    private String title;
    private String salary;
    
    public person(){
    name = "";
    gender = "M";
    nin = "";
    dob = "";
    address = "";
    postcode = "";
    email = "";
    title ="";
    salary = "";
    
    }
    public person(String _name,String _gender,String _nin,String _dob, String _address,String _postcode, String _email, String _title, String _salary){
    name = _name;
    gender = _gender;
    nin = _nin;
    dob = _dob;
    address = _address;
    postcode = _postcode;
    email = _email;
    salary = _salary;
    title = _title;
    setName(_name);
    setGender(_gender);
    setNin(_nin);
    setDob(_dob);
    setAddress(_address);
    setPostcode(_postcode);
    setEmail(_email);
    setSalary(_salary);
    setTitle(_title);
    
    }
    // all the below function are used to get and set the values to the members of the class.
    

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getPostcode() {
		return postcode;
	}
	void setName(String n) {
		name = n;
	}

	String getGender() {
		return gender;
	}

	void setGender(String g) {
		gender = g;
	}

	String getNin() {
		return nin;
	}

	void setNin(String n) {
		nin = n;
	}

	String getDob() {
		return dob;
	}

	void setDob(String d) {
		dob = d;
	}

	String getAddress() {
		return address;
	}

	void setAddress(String addr) {
		address = addr;
	}

	String getPosotcode() {
		return postcode;
	}

	void setPostcode(String p) {
		postcode = p;
	}
    
//    public String toString(){
//        return "Employee Name: "+ this.getName()
//                +",Gender: "+ this.getGender()+
//                ",National Insurance Number: "+this.getNin()+
//                ",Date Of Birth: "+ this.getDob()+
//                ",Address: "+ this.getAddress()+
//                ",Postcode: "+ this.getPosotcode()+".";
//    }
}
    


/**
 * 
@author
Radoslaw Choromanski Id nr-14020101
 */