/*
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.entity.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.AuditableEntity;
import in.gagan.base.framework.entity.base.BaseEntity;
import in.gagan.base.framework.enums.Gender;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * Entity representing the USERS table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="USERS")
public class User extends AuditableEntity implements BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", nullable = false, unique = true, length = 10)
	private long userId;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false, length = 20)
	private String lastName;
	
	@Column(name = "EMAIL", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "DOB")
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER")
	private Gender gender;
	
	@Column(name="PHONE_NUMBER")
	private long phoneNumber;
	
	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;
	
	@Column(name = "PASSWORD_EXPIRE_DATE", nullable = false)
	private LocalDateTime passwordExpireDate;

	@Column(name = "FAILED_LOGIN_ATTEMPTS", nullable = false)
	private short failedLoginAttempts = 0;

	@Column(name = "ACCOUNT_LOCKED", nullable = false)
	private char accountLocked = ApplicationConstants.CHAR_N;
	
	@Column(name = "ADDRESS_LINE_1")
	private String addressLine1;
	
	@Column(name = "ADDRESS_LINE_2")
	private String addressLine2;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "ZIPCODE")
	private long zipcode;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="USER_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Set<Role> userRole = new HashSet<>();
	
	public User() {
		super(ApplicationConstants.CHAR_N);
	}
	
	/*
	 * Bare minimum user details. This constructor contains required fields.
	 */
	public User(String firstName, String lastName, String email, String password) {
		this(firstName, lastName, email, null, Gender.NULL, password, 0, null, null, null, 0, null, null);
	}
	
	public User(String firstName, String lastName, String email, LocalDate dob, Gender gender, String password, 
			long phoneNumber, String country, String state, String city, long zipCode, String addressLine1, 
			String addressLine2) {
		super(ApplicationConstants.CHAR_N);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipcode = zipCode;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
	}
	
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	public long getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getPasswordExpireDate() {
		return passwordExpireDate;
	}

	public void setPasswordExpireDate(LocalDateTime passwordExpireDate) {
		this.passwordExpireDate = passwordExpireDate;
	}

	public short getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(short failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public char isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(char accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Set<Role> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<Role> userRole) {
		this.userRole = userRole;
	}
	
	public void addRole(Role role) {
		this.userRole.add(role);
	}
	
	public int getAge() {
		return UserHelperUtil.calculateAge(getDob());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null) {
			return false;
		} else if(getClass() != obj.getClass()) {
			return false;
		}
		
		User other = (User) obj;
		
		return userId == other.userId;
	}
}
