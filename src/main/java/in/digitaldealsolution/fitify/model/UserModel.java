/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 4:51 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.model;

public class UserModel {
    String name, age, email, gender, phoneno, weight;

    public UserModel(String name, String age, String email, String gender, String phoneno, String weight) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.phoneno = phoneno;
        this.weight = weight;
    }

    public UserModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
