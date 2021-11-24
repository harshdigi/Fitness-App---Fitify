/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 4:51 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.helper;

public class UserHelper {
    String name, email, phoneno, password, weight, age, gender;


    public UserHelper() {

    }

    public UserHelper(String name, String email, String phoneno, String weight, String age, String gender) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
