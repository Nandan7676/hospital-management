package com.meddibuddy.doctor;
import jakarta.persistence.*;
@Entity @Table(name="doctors")
public class Doctor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false) private String specialty;
 @Column(nullable=false) private String experience;
 @Column(nullable=false) private int fee;
 @Column(nullable=false) private String rating="New";
 @Column(nullable=false) private int reviews=0;
 @Column(nullable=false) private String location;
 @Column(nullable=false,length=1000) private String image;
 @Column(nullable=false,length=1000) private String about;
 protected Doctor(){}
 public Doctor(String name,String specialty,String experience,int fee,String rating,int reviews,String location,String image,String about){this.name=name;this.specialty=specialty;this.experience=experience;this.fee=fee;this.rating=rating;this.reviews=reviews;this.location=location;this.image=image;this.about=about;}
 public void setName(String v){name=v;} public void setSpecialty(String v){specialty=v;} public void setExperience(String v){experience=v;} public void setFee(int v){fee=v;} public void setLocation(String v){location=v;} public void setImage(String v){image=v;} public void setAbout(String v){about=v;} public Long getId(){return id;} public String getName(){return name;} public String getSpecialty(){return specialty;} public String getExperience(){return experience;} public int getFee(){return fee;} public String getRating(){return rating;} public int getReviews(){return reviews;} public String getLocation(){return location;} public String getImage(){return image;} public String getAbout(){return about;}
 public DoctorDto toDto(){return new DoctorDto(id,name,specialty,experience,fee,rating,reviews,location,image,about);}
}


